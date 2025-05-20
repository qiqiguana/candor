package org.heal.module.upload;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetadataBean;
import org.heal.module.metadata.ThumbnailBean;
import org.heal.util.FileLocator;

import javax.media.Buffer;
import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Duration;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.ResourceUnavailableEvent;
import javax.media.Time;
import javax.media.control.FrameGrabbingControl;
import javax.media.control.FramePositioningControl;
import javax.media.format.VideoFormat;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.protocol.DataSource;
import java.awt.Dimension;
import java.awt.image.DataBuffer;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Seth Wright
 * @version 0.1
 */
public class UploadServicesBean implements Serializable {
    public static final int MAX_THUMB_WIDTH = 85;
    public static final int MAX_THUMB_HEIGHT = 85;
    private static final String JPEG_EXTENSION = ".jpg";
    private static final String THUMB_PREFIX = "thb_";

    private FileLocator fileLocator = null;

    public String getUploadPath() {
        if (fileLocator == null) return null;
        return fileLocator.getUploadURL();
    }

    public void setFileLocator(FileLocator newFileLocator) {
        fileLocator = newFileLocator;
    }

    public FileLocator getFileLocator() {
        return fileLocator;
    }

    /**
     * Assumes that the metadata property in the CompleteMetadataBean parameter
     * exists and already has a metadataId set.  (This implies that the
     * metadata has already been validated and stored in the database at least
     * once).  This method will then update the metadata's height and width
     * parameters and generate a thumbnail if it can.  If it cannot generate a
     * thumbnail, none will be set.  Either way, the database should be
     * updated with the complete metadata bean after calling this method.
     * The sourceFilePath is the location on disk of the source image
     * we are trying to process.  If either parameter or the metadata property
     * of the complete parameter is null, this method will return false.  Also,
     * if the location setting in the metadata is null, false will be returned.
     * If the method successfully determines the width and height of the
     * image, true is returned.  Otherwise false is returned.  Whether or
     * not a thumbnail is generated is not a factor in the return value of this
     * method.
     */
    public boolean processImage(CompleteMetadataBean complete,
                                String sourceFilePath)
            throws IOException {
        MetadataBean metadata;
        String sourceLocation;
        String metadataId;
        if (complete == null ||
                (metadata = complete.getMetadata()) == null ||
                (sourceLocation = metadata.getLocation()) == null ||
                sourceFilePath == null) {

            return false;
        }
        //metadataId may or may not be null
        metadataId = metadata.getMetadataId();

        if (sourceLocation == null) {
            return false;
        }
        try {
            PlanarImage image = JAI.create("fileload", sourceFilePath);
            metadata.setFileHeight("" + image.getHeight());
            metadata.setFileWidth("" + image.getWidth());
            ThumbnailBean thumbnail = null;
            try {
                thumbnail = generateThumbnail(sourceLocation,
                        metadataId,
                        image);
            } catch (Exception ex) {
                ex.printStackTrace();
                thumbnail = null;
            }
            complete.setThumbnail(thumbnail);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Inner class used to wait for the media players used in
     * the audio and video processing to realize.
     */
    private class RealizeListener implements ControllerListener {
        Object waitSync = new Object();
        boolean stateTransitionOK = true;

        /**
         * Block until the player has transitioned to the given state.
         * Return false if the transition failed.
         */
        boolean waitForState(int state, Player p) {
            synchronized (waitSync) {
                try {
                    while (p.getState() < state && stateTransitionOK)
                        waitSync.wait();
                } catch (Exception e) {
                }
            }
            return stateTransitionOK;
        }

        /**
         * Controller Listener.
         */
        public void controllerUpdate(ControllerEvent evt) {

            if (evt instanceof ConfigureCompleteEvent ||
                    evt instanceof RealizeCompleteEvent ||
                    evt instanceof PrefetchCompleteEvent) {
                synchronized (waitSync) {
                    stateTransitionOK = true;
                    waitSync.notifyAll();
                }
            } else if (evt instanceof ResourceUnavailableEvent) {
                synchronized (waitSync) {
                    stateTransitionOK = false;
                    waitSync.notifyAll();
                }
            }
        }
    }

    /**
     * Extracts the width and height of the video media represented by
     * the player and stores that information in the Metadata bean.
     * If successful, true is returned, otherwise false is returned.
     * The player needs to be in the realized state before this method
     * is called.
     */
    private boolean readWidthAndHeight(Player player, MetadataBean metadata) {
        FramePositioningControl fpc = (FramePositioningControl) player.getControl("javax.media.control.FramePositioningControl");
        FrameGrabbingControl fgc = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");

        if (fpc == null || fgc == null) {
            System.err.println("Unable to get a positioning or grabbing control.");
            return false;
        }
        fpc.seek(1);
        Buffer image = fgc.grabFrame();
        VideoFormat format = (VideoFormat) image.getFormat();
        if (format != null) {
            System.err.println("Unable to get video format for initial frame of movie.");
            Dimension size = format.getSize();
            metadata.setFileWidth("" + size.width);
            metadata.setFileHeight("" + size.height);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Takes the video file stored at the given location and
     * extracts the duration, width, and height of the video
     * and stores that information in the metadata bean
     * provided.  If an error occurs, false is returned, otherwise
     * true is returned.  False will also be returned if either
     * parameter is null, or the complete metadata bean's metadata bean
     * is null.
     */
    public boolean processVideo(CompleteMetadataBean complete,
                                String sourceFilePath) {
        MetadataBean metadata;
        if (complete == null ||
                (metadata = complete.getMetadata()) == null ||
                sourceFilePath == null) {

            return false;
        }
        try {
            Player player = getAndRealizePlayer(sourceFilePath);
            if (player == null) return false;
            if (!readDuration(player, metadata)) return false;
            //we got duration, so we should be able to find a frame
            return readWidthAndHeight(player, metadata);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Given a source file path, creates a data source/media locator
     * for that file, creates a new player, and waits for the
     * player to be realized.  If no errors occur, the created player
     * (in the realized state), is returned and ready for use.
     * If an error occurs, null is returned.
     */
    private Player getAndRealizePlayer(String sourceFilePath)
            throws MalformedURLException {
        if (sourceFilePath == null) return null;
        File file = new File(sourceFilePath);
        URL url = file.toURL();

        MediaLocator ml;
        Player player;

        if ((ml = new MediaLocator(url)) == null) {
            System.err.println("Unable to load media file.");
            return null;
        }

        DataSource ds = null;

        // Create a DataSource given the media locator.
        try {
            ds = Manager.createDataSource(ml);
        } catch (Exception e) {
            System.err.println("added here1: " + e);

            e.printStackTrace();
            return null;
        }

        try {
            player = Manager.createPlayer(ds);
        } catch (Exception e) {
            System.err.println("Failed to create a player from the given DataSource: " + e);
            return null;
        }
        RealizeListener realizeListener = new RealizeListener();
        player.addControllerListener(realizeListener);
        player.realize();
        if (!realizeListener.waitForState(player.Realized, player)) {
            System.err.println("Failed to realize the player.");
            return null;
        }
        return player;
    }

    /**
     * Given a source file path, this method
     * gleans the duration (in milliseconds) of the audio.
     * This information is then stored in the Duration field
     * of the MetadataBean contained within the passed in
     * CompleteMetadataBean.  If either of the parameters is null
     * then false is returned.
     * If the duration is successfully acquired, true is returned.
     * Otherwise, false is returned.
     */
    public boolean processAudio(CompleteMetadataBean complete,
                                String sourceFilePath) {
        MetadataBean metadata;
        if (complete == null ||
                (metadata = complete.getMetadata()) == null ||
                sourceFilePath == null) {

            return false;
        }
        try {
            Player player = getAndRealizePlayer(sourceFilePath);
            if (player == null) return false;
            return readDuration(player, metadata);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the duration of the media loaded by the Player and
     * stores that information (in milliseconds) in the metadata
     * bean's duration field.
     * The player must be in the realized state for this to work
     * correctly, and the metadata must not be null.  If an error occurs,
     * false is returned.  Otherwise true is returned, meaning that
     * the duration was successfully stored in the metadata bean.
     */
    private boolean readDuration(Player player, MetadataBean metadata) {
        if (player == null || metadata == null) return false;
        Time t = player.getDuration();
        if (t != Duration.DURATION_UNKNOWN) {
            int milliseconds = (int) (t.getSeconds() * 1000);
            metadata.setDuration("" + milliseconds);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Given the path to an image file, opens that image and dynamically
     * generates a thumbnail scaled to fit within the specified dimensions.
     * The scaling means that the thumbnail's height will be no greater than
     * the maxHeight parameter and the thumbnail's width will be no greater
     * than the maxWidth parameter.  The unit of measurement for both
     * maxWidth and maxHeight are pixels.
     * It is important that the provided CompleteMetadataBean already have
     * a MetadataBean set with a metadataId assigned.  If not, the
     * ThumbnailBean generated will not contain a metadataId setting and
     * the caller is responsible for setting it.
     * KNOWN PROBLEM: DUE TO A BUG IN THE JAVA ADVANCED IMAGING GIF DECODER
     * GIFS WITH TRANSPARENT BACKGROUNDS MAY NOT BE READ PROPERLY.
     */
    public ThumbnailBean generateThumbnail(String sourceLocation,
                                           String metadataId,
                                           PlanarImage image)
            throws IOException {
        ThumbnailBean thumbnail = null;

        if (sourceLocation == null) {
            return null;  //failed because there was no metadata bean
        }
        String thumbLocation = getThumbnailLocationFromSource(sourceLocation,
                THUMB_PREFIX,
                JPEG_EXTENSION);

        String thumbFilePath = fileLocator.getThumbnailFilePath(thumbLocation);
        if (thumbFilePath != null) {
            File thumbFile = new File(thumbFilePath);
            File parentDir = thumbFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
        }

        ParameterBlock parameters = getThumbnailParameterBlock(image);

        PlanarImage thumb = JAI.create("scale", parameters, null);

        thumbnail = new ThumbnailBean();
        thumbnail.setFileWidth("" + thumb.getWidth());
        thumbnail.setFileHeight("" + thumb.getHeight());
        thumbnail.setLocation(thumbLocation);
        thumbnail.setMetadataId(metadataId);

        //This is a workaround for a bug in the jpeg encoder, to read more
        //about the bug, see:
        //http://www.sun.com/software/imaging/JAI/11/bugs_codec.html
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(thumb);
        pb.add(DataBuffer.TYPE_BYTE);
        PlanarImage thumbByte = JAI.create("format", pb);


        FileOutputStream dst = null;
        try {
            dst = new FileOutputStream(thumbFilePath);
            ImageEncoder enc = ImageCodec.createImageEncoder("JPEG",
                    dst,
                    null);
            enc.encode(thumbByte);
        } finally {
            try {
                if (dst != null) {
                    dst.close();
                }
            } catch (Exception ex) {
                //ignore for now, we just want the stream closed.
            }
        }
        return thumbnail;
    }

    /**
     * Given a location for a content file (e.g. /seth/images/brain.gif)
     * This method will return a string indicating the thumbnail
     * location to use (e.g. /seth/images/thb_brain.jpg).  Please note
     * that this method assumes that all thumbnails will be jpgs.
     */
    private String getThumbnailLocationFromSource(String sourceLocation,
                                                  String thumbPrefix,
                                                  String thumbSuffix) {
        int index = sourceLocation.lastIndexOf('/');
        String prefix;
        String tempname;
        String finalname;
        if (index == -1) {
            prefix = "";
            tempname = sourceLocation;
        } else {
            prefix = sourceLocation.substring(0, index + 1);
            tempname = sourceLocation.substring(index + 1);
        }
        int extensionIndex = tempname.lastIndexOf('.');
        if (extensionIndex == -1) {
            finalname = thumbPrefix + tempname + thumbSuffix;
        } else {
            finalname = thumbPrefix +
                    tempname.substring(0, extensionIndex) +
                    thumbSuffix;
        }
        return prefix + finalname;
    }


    /**
     * This method determines sets up the parameter block for the
     * scaling function used to create the thumbnail.  Given an
     * image, it calculates the scaling required to gain the desired
     * size of thumbnail.
     * Okay, so we're trying to constrain the thumbnail image as such:
     * We want the maximum dimension of either height or width to
     * be MAX_THUMB_HEIGHT or MAX_THUMB_WIDTH.  So, we figure out the
     * float to multiply the image height by in order to make it fit within
     * the specified maximum.  We do the same with the width parameter.
     * The equation for this is: 1/(dimension/max) = max/dimension
     * This will give us a scalar that is less than one (e.g. 0.5), or if
     * the dimension is already less than the maximum, we set the
     * dimension's scalar to 1.0F.
     * Then, once we have determined the scalar for each, we take the
     * smaller of the two because that is the dimension that requires the
     * most scaling to be brought within it's max dimension.
     */
    private ParameterBlock getThumbnailParameterBlock(PlanarImage image) {
        ParameterBlock parameters = new ParameterBlock();

        int height = image.getHeight();
        int width = image.getWidth();
        float heightscalar;
        float widthscalar;

        if (height > MAX_THUMB_HEIGHT) {
            heightscalar = ((float) MAX_THUMB_HEIGHT) / ((float) height);
        } else {
            heightscalar = 1.0F; //no scaling involved
        }

        if (width > MAX_THUMB_WIDTH) {
            widthscalar = ((float) MAX_THUMB_WIDTH) / ((float) width);
        } else {
            widthscalar = 1.0F; //no scaling involved
        }

        float scalar = (widthscalar < heightscalar ? widthscalar : heightscalar);
        parameters.addSource(image);
        parameters.add(scalar); //x scalar
        parameters.add(scalar); //y scalar
        parameters.add(0.0F);
        parameters.add(0.0F);

        //these offer different algorithms for doing the scaling.
        Interpolation interp;
        interp = Interpolation.getInstance(Interpolation.INTERP_NEAREST);
        // interp = Interpolation.getInstance(Interpolation.INTERP_BILINEAR);
        // interp = Interpolation.getInstance(Interpolation.INTERP_BICUBIC);
        parameters.add(interp);

        return parameters;
    }
}
