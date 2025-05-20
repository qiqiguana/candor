package com.eteks.sweethome3d.j3d;

import java.awt.EventQueue;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GeometryStripArray;
import javax.media.j3d.Group;
import javax.media.j3d.IndexedGeometryArray;
import javax.media.j3d.IndexedGeometryStripArray;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.media.j3d.IndexedTriangleFanArray;
import javax.media.j3d.IndexedTriangleStripArray;
import javax.media.j3d.Light;
import javax.media.j3d.Link;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.QuadArray;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.SharedGroup;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.TriangleFanArray;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathParser;
import com.eteks.sweethome3d.model.Content;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.tools.TemporaryURLContent;
import com.eteks.sweethome3d.tools.URLContent;
import com.microcrowd.loader.java3d.max3ds.Loader3DS;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.lw3d.Lw3dLoader;

/**
 * Singleton managing 3D models cache.
 * This manager supports 3D models with an OBJ, DAE, 3DS or LWS format by default.
 * Additional classes implementing Java 3D <code>Loader</code> interface may be
 * specified in the <code>com.eteks.sweethome3d.j3d.additionalLoaderClasses</code>
 * (separated by a space or a colon :) to enable the support of other formats.<br>
 * Note: this class is compatible with Java 3D 1.3.
 *
 * @author Emmanuel Puybaret
 */
public class ModelManager {

    public Transform3D getNormalizedTransform(Node node, float[][] modelRotation, float width) {
        // Get model bounding box size
        BoundingBox modelBounds = getBounds(node);
        Point3d lower = new Point3d();
        modelBounds.getLower(lower);
        Point3d upper = new Point3d();
        modelBounds.getUpper(upper);
        // Translate model to its center
        Transform3D translation = new Transform3D();
        translation.setTranslation(new Vector3d(-lower.x - (upper.x - lower.x) / 2, -lower.y - (upper.y - lower.y) / 2, -lower.z - (upper.z - lower.z) / 2));
        Transform3D modelTransform;
        if (modelRotation != null) {
            // Get model bounding box size with model rotation
            Matrix3f modelRotationMatrix = new Matrix3f(modelRotation[0][0], modelRotation[0][1], modelRotation[0][2], modelRotation[1][0], modelRotation[1][1], modelRotation[1][2], modelRotation[2][0], modelRotation[2][1], modelRotation[2][2]);
            modelTransform = new Transform3D();
            modelTransform.setRotation(modelRotationMatrix);
            modelTransform.mul(translation);
            BoundingBox rotatedModelBounds = getBounds(node, modelTransform);
            rotatedModelBounds.getLower(lower);
            rotatedModelBounds.getUpper(upper);
        } else {
            modelTransform = translation;
        }
        // Scale model to make it fill a 1 unit wide box
        Transform3D scaleOneTransform = new Transform3D();
        scaleOneTransform.setScale(new Vector3d(width / Math.max(getMinimumSize(), upper.x - lower.x), width / Math.max(getMinimumSize(), upper.y - lower.y), width / Math.max(getMinimumSize(), upper.z - lower.z)));
        scaleOneTransform.mul(modelTransform);
        return scaleOneTransform;
    }
}
