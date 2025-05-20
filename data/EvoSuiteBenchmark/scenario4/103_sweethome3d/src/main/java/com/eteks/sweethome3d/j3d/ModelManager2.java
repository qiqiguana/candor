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

    /**
     * <code>Shape3D</code> user data prefix for window pane shapes.
     */
    public static final String WINDOW_PANE_SHAPE_PREFIX = "sweethome3d_window_pane";

    /**
     * <code>Shape3D</code> user data prefix for mirror shapes.
     */
    public static final String MIRROR_SHAPE_PREFIX = "sweethome3d_window_mirror";

    /**
     * <code>Shape3D</code> user data prefix for lights.
     */
    public static final String LIGHT_SHAPE_PREFIX = "sweethome3d_light";

    private static final TransparencyAttributes WINDOW_PANE_TRANSPARENCY_ATTRIBUTES = new TransparencyAttributes(TransparencyAttributes.NICEST, 0.5f);

    private static final Material DEFAULT_MATERIAL = new Material();

    private static final float MINIMUM_SIZE = 0.001f;

    private static final String ADDITIONAL_LOADER_CLASSES = "com.eteks.sweethome3d.j3d.additionalLoaderClasses";

    private static ModelManager instance;

    // Map storing loaded model nodes
    private Map<Content, BranchGroup> loadedModelNodes;

    // Map storing model nodes being loaded
    private Map<Content, List<ModelObserver>> loadingModelObservers;

    // Executor used to load models
    private ExecutorService modelsLoader;

    // List of additional loader classes
    private Class<Loader>[] additionalLoaderClasses;

    // SVG path Shapes
    private final Map<String, Shape> parsedShapes;

    private ModelManager() {
    }

    /**
     * Returns the class of name <code>loaderClassName</code>.
     */
    @SuppressWarnings("unchecked")
    private Class<Loader> getLoaderClass(String loaderClassName);

    /**
     * Returns an instance of this singleton.
     */
    public static ModelManager getInstance();

    /**
     * Shutdowns the multithreaded service that load textures.
     */
    public void clear();

    /**
     * Returns the minimum size of a model.
     */
    float getMinimumSize();

    /**
     * Returns the size of 3D shapes of <code>node</code>.
     * This method computes the exact box that contains all the shapes,
     * contrary to <code>node.getBounds()</code> that returns a bounding
     * sphere for a scene.
     */
    public Vector3f getSize(Node node);

    /**
     * Returns the size of 3D shapes of <code>node</code> after an additional <code>transformation</code>.
     * This method computes the exact box that contains all the shapes,
     * contrary to <code>node.getBounds()</code> that returns a bounding
     * sphere for a scene.
     */
    public Vector3f getSize(Node node, Transform3D transformation);

    /**
     * Returns the bounds of the 3D shapes of <code>node</code>.
     * This method computes the exact box that contains all the shapes,
     * contrary to <code>node.getBounds()</code> that returns a bounding
     * sphere for a scene.
     */
    public BoundingBox getBounds(Node node);

    /**
     * Returns the bounds of the 3D shapes of <code>node</code> with an additional <code>transformation</code>.
     * This method computes the exact box that contains all the shapes, contrary to <code>node.getBounds()</code>
     * that returns a bounding sphere for a scene.
     */
    public BoundingBox getBounds(Node node, Transform3D transformation);

    /**
     * Returns <code>true</code> if the rotation matrix matches only rotations of
     * a multiple of 90� degrees around x, y or z axis.
     */
    private boolean isOrthogonalRotation(Transform3D transformation);

    private void computeBounds(Node node, BoundingBox bounds, Transform3D parentTransformations, boolean transformShapeGeometry);

    private Bounds computeTransformedGeometryBounds(Shape3D shape, Transform3D transformation);

    private void updateBounds(Point3f vertex, Transform3D transformation, Point3d lower, Point3d upper);

    /**
     * Returns a transform group that will transform the model <code>node</code>
     * to let it fill a box of the given <code>width</code> centered on the origin.
     * @param node     the root of a model with any size and location
     * @param modelRotation the rotation applied to the model before normalization
     *                 or <code>null</code> if no transformation should be applied to node.
     * @param width    the width of the box
     */
    public TransformGroup getNormalizedTransformGroup(Node node, float[][] modelRotation, float width);

    /**
     * Returns a transform that will transform the model <code>node</code>
     * to let it fill a box of the given <code>width</code> centered on the origin.
     * @param node     the root of a model with any size and location
     * @param modelRotation the rotation applied to the model before normalization
     *                 or <code>null</code> if no transformation should be applied to node.
     * @param width    the width of the box
     */
    public Transform3D getNormalizedTransform(Node node, float[][] modelRotation, float width);

    /**
     * Reads asynchronously a 3D node from <code>content</code> with supported loaders
     * and notifies the loaded model to the given <code>modelObserver</code> once available.
     * @param content an object containing a model
     * @param modelObserver the observer that will be notified once the model is available
     *    or if an error happens
     * @throws IllegalStateException if the current thread isn't the Event Dispatch Thread.
     */
    public void loadModel(Content content, ModelObserver modelObserver);

    /**
     * Reads a 3D node from <code>content</code> with supported loaders
     * and notifies the loaded model to the given <code>modelObserver</code> once available.
     * @param content an object containing a model
     * @param synchronous if <code>true</code>, this method will return only once model content is loaded
     * @param modelObserver the observer that will be notified once the model is available
     *    or if an error happens. When the model is loaded synchronously, the observer will be notified
     *    in the same thread as the caller, otherwise the observer will be notified in the Event
     *    Dispatch Thread and this method must be called in Event Dispatch Thread too.
     * @throws IllegalStateException if synchronous is <code>false</code> and the current thread isn't
     *    the Event Dispatch Thread.
     */
    public void loadModel(final Content content, boolean synchronous, ModelObserver modelObserver);

    /**
     * Returns a clone of the given <code>node</code>.
     * All the children and the attributes of the given node are duplicated except the geometries
     * and the texture images of shapes.
     */
    public Node cloneNode(Node node);

    private Node cloneNode(Node node, Map<SharedGroup, SharedGroup> clonedSharedGroups);

    /**
     * Returns the node loaded synchronously from <code>content</code> with supported loaders.
     * This method is threadsafe and may be called from any thread.
     * @param content an object containing a model
     */
    public BranchGroup loadModel(Content content) throws IOException;

    /**
     * Updates the name of scene shapes and transparency window panes shapes.
     */
    @SuppressWarnings("unchecked")
    private void updateShapeNamesAndWindowPanesTransparency(Scene scene);

    /**
     * Turns off light nodes of <code>node</code> children,
     * and modulates textures if needed.
     */
    private void turnOffLightsShareAndModulateTextures(Node node);

    /**
     * Returns the 2D area of the 3D shapes children of the given <code>node</code>
     * projected on the floor (plan y = 0).
     */
    public Area getAreaOnFloor(Node node);

    /**
     * Returns the total count of vertices in all geometries.
     */
    private int getVertexCount(Node node);

    /**
     * Computes the vertices coordinates projected on floor of the 3D shapes children of <code>node</code>.
     */
    private void computeVerticesOnFloor(Node node, List<float[]> vertices, Transform3D parentTransformations);

    /**
     * Computes the 2D area on floor of the 3D shapes children of <code>node</code>.
     */
    private void computeAreaOnFloor(Node node, Area nodeArea, Transform3D parentTransformations);

    /**
     * Computes the area on floor of a 3D geometry.
     */
    private void computeGeometryAreaOnFloor(Geometry geometry, Transform3D parentTransformations, Area nodeArea);

    /**
     * Adds to <code>nodePath</code> the triangle joining vertices at
     * vertexIndex1, vertexIndex2, vertexIndex3 indices.
     */
    private void addIndexedTriangleToPath(IndexedGeometryArray geometryArray, int vertexIndex1, int vertexIndex2, int vertexIndex3, float[] vertices, GeneralPath geometryPath, int triangleIndex, Area nodeArea);

    /**
     * Adds to <code>nodePath</code> the quadrilateral joining vertices at
     * vertexIndex1, vertexIndex2, vertexIndex3, vertexIndex4 indices.
     */
    private void addIndexedQuadrilateralToPath(IndexedGeometryArray geometryArray, int vertexIndex1, int vertexIndex2, int vertexIndex3, int vertexIndex4, float[] vertices, GeneralPath geometryPath, int quadrilateralIndex, Area nodeArea);

    /**
     * Adds to <code>nodePath</code> the triangle joining vertices at
     * vertexIndex1, vertexIndex2, vertexIndex3 indices,
     * only if the triangle has a positive orientation.
     */
    private void addTriangleToPath(GeometryArray geometryArray, int vertexIndex1, int vertexIndex2, int vertexIndex3, float[] vertices, GeneralPath geometryPath, int triangleIndex, Area nodeArea);

    /**
     * Adds to <code>nodePath</code> the quadrilateral joining vertices at
     * vertexIndex1, vertexIndex2, vertexIndex3, vertexIndex4 indices,
     * only if the quadrilateral has a positive orientation.
     */
    private void addQuadrilateralToPath(GeometryArray geometryArray, int vertexIndex1, int vertexIndex2, int vertexIndex3, int vertexIndex4, float[] vertices, GeneralPath geometryPath, int quadrilateralIndex, Area nodeArea);

    /**
     * Returns the convex polygon that surrounds the given <code>vertices</code>.
     * From Andrew's monotone chain 2D convex hull algorithm described at
     * http://softsurfer.com/Archive/algorithm%5F0109/algorithm%5F0109.htm
     */
    private float[][] getSurroundingPolygon(float[][] vertices);

    private float isLeft(float[] vertex0, float[] vertex1, float[] vertex2);

    /**
     * Returns the area on the floor of the given staircase.
     */
    public Area getAreaOnFloor(HomePieceOfFurniture staircase);

    /**
     * Returns the mirror area of the given <code>area</code>.
     */
    private Area getMirroredArea(Area area);

    /**
     * Returns the AWT shape matching the given <a href="http://www.w3.org/TR/SVG/paths.html">SVG path shape</a>.
     */
    private Shape parseShape(String svgPathShape);

    /**
     * Separated static class to be able to exclude Batik library from classpath.
     */
    private static class SVGPathSupport {

        public static Shape parsePathShape(String svgPathShape) {
            try {
                AWTPathProducer pathProducer = new AWTPathProducer();
                PathParser pathParser = new PathParser();
                pathParser.setPathHandler(pathProducer);
                pathParser.parse(svgPathShape);
                return pathProducer.getShape();
            } catch (ParseException ex) {
                // Fallback to default square shape if shape is incorrect
                return new Rectangle2D.Float(0, 0, 1, 1);
            }
        }
    }

    /**
     * An observer that receives model loading notifications.
     */
    public static interface ModelObserver {

        public void modelUpdated(BranchGroup modelRoot);

        public void modelError(Exception ex);
    }
}
