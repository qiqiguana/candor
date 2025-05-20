/**
 * Fps370Panel
 * 3D game template panel
 *
 * Created for cpsc370 at Chapman University
 * http://www.gamedev370.com
 * (c) copyright 2006 - W. Wood Harter
 *
 * Licensed under GNU General Public License
 * http://www.gnu.org
 *
 * This is the main panel for fps370.
 * This needs to be refactored into a bunch of subclasses.
 **
 * In order to run this you will have to download the Milkshap model loader
 * http://home.earthlink.net/~kduling/Milkshape/
 * Place the MS3DLoader-1.0.8.jar in the fps370/lib/ext directory.
 */
package fps370;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;

import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.picking.*;

import com.sun.j3d.loaders.*;
//import com.mnstarfire.loaders3d.Loader3DS; 
import com.glyphein.j3d.loaders.milkshape.MS3DLoader;
import teder.*;

public class Fps370Panel extends JPanel implements KeyListener
  {
  // parent window
  Fps370 parent;
  
  // 3d frame and universe
  SimpleUniverse su;
  Canvas3D c3d;
 
  // scene graph to display
  BranchGroup bgMain;
  
  TransformGroup tgFloor;
  AxisAngle4f    aaFloor;
  Transform3D    t3dFloorRotate;
  
  // temporary holding variables
  Transform3D t3dTmp;
  Transform3D t3dTmp2;
  Vector3f v3fTmp;
  Vector3f v3fTmp2;
  Vector3f v3fTmp3;
  Matrix4f m3fTmp1;
  Matrix4f m3fTmp2;
  
  BoundingSphere boundsMain;
  
  Sphere         sphere;
  TransformGroup tgSphere;
  Transform3D t3dSphereTranslate;
  Vector3f vSphereVel = new Vector3f(0.0f,0.0f,0.0f);
  Vector3f vSpherePos = new Vector3f(6.5f,3.2f,5.0f);
            
  Vector3f vViewPos = new Vector3f(0.0f,0.5f,5.0f);
  Vector3f vViewOri = new Vector3f(0.0f,1.0f,1.0f);

  private static final float TILE_WIDTH = 1.0f;
  private static final int NUM_TILE_TEXTURES = 10;
  Appearance tileTextures[];
  Appearance textureStone;
  TederLayer tl;
  TederData td;

  TransformGroup tgView;
  Transform3D t3dYPR; // the current YPR rotation
  AxisAngle4f aaYPR; // temporary calculatiosn for YPR changes
  float lookUpAngle=5.0f; // the angle to look in the viewport
  private static final float TURNDELTA = 0.5f;
  
  boolean keyLeft;
  boolean keyRight;
  boolean keyForward;
  boolean keyBackward;
  boolean keyStrafeLeft;
  boolean keyStrafeRight;
  boolean keyLookUp;
  boolean keyLookDown;
  
  // mouse movement
  int diffx,diffy;
  int lastMouseX,lastMouseY;
  int offcenterX,offcenterY;
  Robot robot;
  
  // collision detection with map
  BranchGroup bgMap;
  PickCylinderRay pickCylinder;
  PickTool pickTool;
  
  
  // HUD
  PlatformGeometry bgHud;
  Point3f hudPlane[];
  Appearance appHud;
  Shape3D shapeHud;
  Texture2D textureHud;
  ImageComponent2D icHud; // the 2d texture image 
  HudUpdater hudUpdater;
  Image imgHudBack;
  BufferedImage imgHud;
  
  public Fps370Panel(Fps370 parentInit)
    {
    parent = parentInit;
    
    setLayout(new BorderLayout());
    
    setPreferredSize(new Dimension(800,600));
    
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    c3d = new Canvas3D(config);
    add("Center", c3d);
   
    // allow the canvas to get focus and give it focus
    c3d.setFocusable(true);
    c3d.requestFocus();
   
    c3d.addKeyListener(this);
    
    su = new SimpleUniverse(c3d);
   
    t3dTmp = new Transform3D();
    t3dTmp2 = new Transform3D();
    v3fTmp = new Vector3f(0,0,0);
    v3fTmp2 = new Vector3f(0,0,0);
    v3fTmp3 = new Vector3f(0,0,0);
    m3fTmp1 = new Matrix4f();
    m3fTmp2 = new Matrix4f();
    //m3fYPR = new Matrix4f();
    t3dYPR = new Transform3D();
    aaYPR = new AxisAngle4f();
    
    try
      {
      robot = new Robot();
      }
    catch (AWTException e)
      {
      e.printStackTrace();
      }
    
    td = new TederData(30,30,64,64,"images/floor/tiles.gif",this);
    loadTileTextures();
    
    createSceneGraph();
    
    initViewPosition();
    
    }
  
  // create appearances with each individual tile
  // used to texture the individual floor tiles
  public void loadTileTextures()
    {
    tileTextures = new Appearance[NUM_TILE_TEXTURES];
    
    int i;
    for (i=0;i<NUM_TILE_TEXTURES;i++)
      {
      tileTextures[i] = new Appearance();

      // mix the texture and the material colour
      TextureAttributes ta = new TextureAttributes();
      //ta.setTextureMode(TextureAttributes.MODULATE);
      ta.setTextureMode(TextureAttributes.REPLACE);
      tileTextures[i].setTextureAttributes(ta);

      // load and set the texture
      TextureLoader loader = new TextureLoader("images/floor/tile"+i+".jpg", null);
      //TextureLoader loader = new TextureLoader("images/floor/stone.jpg", null);
      Texture2D texture = (Texture2D) loader.getTexture();
      tileTextures[i].setTexture(texture);      // set the texture

      // set a default white material
      Material mat = new Material();
      mat.setLightingEnable(true);    // lighting switched on
      tileTextures[i].setMaterial(mat);
      }
    
    textureStone = new Appearance();

    // the default texture if one isn't given (ie. -1)
      // mix the texture and the material colour
      TextureAttributes ta = new TextureAttributes();
      //ta.setTextureMode(TextureAttributes.MODULATE);
      ta.setTextureMode(TextureAttributes.REPLACE);
      textureStone.setTextureAttributes(ta);

      // load and set the texture
      TextureLoader loader = new TextureLoader("images/floor/stone.jpg", null);
      //TextureLoader loader = new TextureLoader("images/floor/stone.jpg", null);
      Texture2D texture = (Texture2D) loader.getTexture();
      textureStone.setTexture(texture);      // set the texture

      // set a default white material
      Material mat = new Material();
      mat.setLightingEnable(true);    // lighting switched on
      textureStone.setMaterial(mat);
    }
 
  public Scene loadModel(String fname)
  {
    Scene theScene = null;
    // setup a file name "fileName"
    try
    {

      //Loader3DS loader = new Loader3DS();
      // optional options to be used
      //loader.setLogging(true); // turns on writing a log file
      //loader.setDetail(7); // sets level of detail of report log
      //loader.setTextureLightingOn(); // turns on texture modulate mode
      //loader.setTexturePath("models"); // optional alternate path to find texture files
      // loader.noTextures(); // if you do not want to load textures
      //theScene = loader.load(fname);
      
      // milkshape loader
      File file = new java.io.File(fname);
      if (file.getParent().length() > 0) // figure out the base path
      {
        Loader loader = new MS3DLoader(MS3DLoader.LOAD_ALL);
        loader.setBasePath(file.getParent() + java.io.File.separator);
        theScene = loader.load(file.getName());
        //BranchGroup group = scene.getSceneGroup();
      }
      
      // load a sphere model twice for testing
      
    }
    catch(FileNotFoundException fnf)
    {
      // Couldn't find the file you requested - deal with it!
      fnf.printStackTrace();
    }
    
    return theScene;
  }
  
  public void createSceneGraph()
    {
    // create a new scene branch
    bgMain = new BranchGroup();
    boundsMain = new BoundingSphere(new Point3d(0,0,0),300);
    
    createLights();

    // create the temporary location for sphere translation - used in tick
    
    // create a sphere
    t3dSphereTranslate = new Transform3D();
    t3dSphereTranslate.set(vSpherePos);
    tgSphere = new TransformGroup(t3dSphereTranslate);
    tgSphere.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    tgSphere.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    sphere = new Sphere(1.0f);
    tgSphere.addChild(sphere);
    
    // allow picking on the sphere
    PickTool.setCapabilities(sphere.getShape(),PickTool.INTERSECT_COORD);

    bgMain.addChild(tgSphere);
    
    // create the floor
    /*
     * I sort of decided to only use models to build the map
    aaFloor = new AxisAngle4f(0.0f, 1.0f, 0.0f, 0.0f);
    t3dFloorRotate = new Transform3D();
    t3dFloorRotate.setRotation(aaFloor);
    tgFloor = new TransformGroup();
    tgFloor.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    tgFloor.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tgFloor.setTransform(t3dFloorRotate);
    

    // add a floor tile
    addFloorTiles(tgFloor);
    bgMain.addChild(tgFloor);
    
    addFloorTiles(tgFloor);
    */
 
    ViewingPlatform vp = su.getViewingPlatform();
    tgView = vp.getViewPlatformTransform();   // view point TG

    KeyUpBehavior bup = new KeyUpBehavior(this);   
    bup.setSchedulingBounds( boundsMain );
    bgMain.addChild( bup );

    KeyDownBehavior bdn = new KeyDownBehavior(this);   
    bdn.setSchedulingBounds( boundsMain );
    bgMain.addChild( bdn );
    
    MouseMoveBehavior mmb = new MouseMoveBehavior(this);   
    mmb.setSchedulingBounds( boundsMain );
    bgMain.addChild( mmb );
      
    // create the ticker behavior for the scene
    Fps370Ticker tt = new Fps370Ticker(20,this);
    tt.setSchedulingBounds(boundsMain);
    bgMain.addChild(tt);
    
    // get the temple model
    Scene sc = loadModel("./models/map2.ms3d");
    // need to scale the model some, then slide it up and towards the origin
    
    // allow picking on the cube for collision detection
    bgMap = sc.getSceneGroup();
    //bgModel.setPickable(true);
    pickableModel(bgMap);
    //PickTool.setCapabilities(bgModel,PickTool.INTERSECT_COORD);
    bgMain.addChild(bgMap);
    
    // used to see aim location during debugging
    //addPickCylinder();
    
    // add the hud to the display
    addHud();
    
    bgMain.compile();
    
    su.addBranchGraph(bgMain);
    }
  
  // changes all the faces on a give model to allow picking
  private void pickableModel(BranchGroup bg)
  {
    // go through the model and set each Shape3D to pickable
    Enumeration e = bg.getAllChildren();
    while (e.hasMoreElements())
    {
      Object o = e.nextElement();
      if (o instanceof Shape3D)
      {
        PickTool.setCapabilities((Shape3D)o,PickTool.INTERSECT_COORD);
        
        // need to also get normals for collision detection
        ((Shape3D)o).getGeometry().setCapability(GeometryArray.ALLOW_NORMAL_READ);
      }
    }
  }
  
  // adds a hud image to the viewing platform
  public void addHud()
  {
    ViewingPlatform vp = su.getViewingPlatform();

    bgHud = new PlatformGeometry();

    // create a plane to add the hud image to
    if (hudPlane==null)
    {
      hudPlane = new Point3f[4];
      
      // these coordinates were created with trial and error
      hudPlane[0] = new Point3f(-0.29f,-0.22f,-0.7f);
      hudPlane[1] = new Point3f(0.304f,-0.22f,-0.7f);
      hudPlane[2] = new Point3f(0.304f,-0.1f,-0.7f);
      hudPlane[3] = new Point3f(-0.29f,-0.1f,-0.7f);
      
      // create a 4 point quad array (a plane)
      QuadArray qaHud = new QuadArray(4,       GeometryArray.COORDINATES | 
                                               GeometryArray.TEXTURE_COORDINATE_2 |
                                               GeometryArray.NORMALS);

      qaHud.setCoordinates(0, hudPlane);
      
      // assign texture coords to each quad
      // counter-clockwise, from bottom left
      TexCoord2f[] tcoords = new TexCoord2f[4];
      tcoords[0] = new TexCoord2f(0.0f, 0.0f);   // for 1 point
      tcoords[1] = new TexCoord2f(1.0f, 0.0f);
      tcoords[2] = new TexCoord2f(1.0f, 1.0f);
      tcoords[3] = new TexCoord2f(0.0f, 1.0f);
      qaHud.setTextureCoordinates(0, 0, tcoords);
      
      // set the surface normal
      Vector3f upNorm = new Vector3f(0.0f, 1.0f, 0.0f);   // pointing up
      for(int i=0; i < 4; i++)
        qaHud.setNormal(i, upNorm);
      
      shapeHud = new Shape3D();
      shapeHud.setGeometry(qaHud);

      appHud = new Appearance();

      // the texture blends with color on shape
      TransparencyAttributes  ta = new TransparencyAttributes ();
      ta.setTransparencyMode(TransparencyAttributes.BLENDED);
      appHud.setTransparencyAttributes(ta);

      // need to load the hud background
      Toolkit tk = Toolkit.getDefaultToolkit();
      imgHudBack = tk.getImage("images/hud/hud2.gif");
      // set up the media tracker to wait for the image
      MediaTracker tracker = new MediaTracker(this);
      tracker.addImage(imgHudBack,0);

      // wait for the image to load
      try
        {
        tracker.waitForID(0);
        }
      catch (InterruptedException e)
        {
        e.printStackTrace();
        }
    
      // create the hud offscreen image
      imgHud = new BufferedImage(imgHudBack.getWidth(this),imgHudBack.getHeight(this),BufferedImage.TYPE_4BYTE_ABGR);
      drawHudImage();
      
      // load and set the texture
      // we specify by reference so we can update the image dynamically
      TextureLoader loader = new TextureLoader(imgHud,TextureLoader.BY_REFERENCE);
      textureHud = (Texture2D) loader.getTexture();
      icHud = (ImageComponent2D) textureHud.getImage(0);
      appHud.setTexture(textureHud);      // set the texture
      hudUpdater = new HudUpdater(this);
      
      // set a default white material
      //Material mat = new Material();
      //mat.setLightingEnable(true);    // lighting switched on
      //appHud.setMaterial(mat);
      
      shapeHud.setAppearance(appHud);
      icHud.setCapability(ImageComponent.ALLOW_IMAGE_WRITE);
      bgHud.addChild(shapeHud);
      vp.setPlatformGeometry(bgHud);
    }
  }
  
  // draw the hud background image
  public void drawHudImage()
  {
      // a test
      //imgHud = new BufferedImage(imgHudBack.getWidth(this),imgHudBack.getHeight(this),BufferedImage.TYPE_4BYTE_ABGR);
      // a test
      
      
      Graphics gtmp = imgHud.getGraphics();
      gtmp.drawImage(imgHudBack,0,0,this);
      //gtmp.drawLine(0,0,100,100);
      String stmp = "X="+vViewPos.x+" Y="+vViewPos.y+" Z="+vViewPos.z;
      gtmp.drawString(stmp,10,imgHudBack.getHeight(this)-20);
      //System.out.println("strtmp="+stmp);
  }
  
  public void updateHud()
  {
    icHud.updateData(hudUpdater,0,0,imgHudBack.getWidth(this),imgHudBack.getHeight(this));
  }
  
  // test routine which adds a cylinder to the view platform to
  // give an idea as to where a "weapon" is pointing.
  public void addPickCylinder()
  {
    ViewingPlatform vp = su.getViewingPlatform();
    tgView = vp.getViewPlatformTransform();   // view point TG

    PlatformGeometry bgPickCyl = new PlatformGeometry();
    t3dTmp.rotX(Math.toRadians(90.0f));
    v3fTmp.set(0.0f,0.0f,1.0f);
    t3dTmp2.set(v3fTmp);
    t3dTmp.mul(t3dTmp2);
    TransformGroup tgPickCyl = new TransformGroup(t3dTmp);
    tgPickCyl.addChild(new Cylinder(0.05f,300.0f));
    bgPickCyl.addChild(tgPickCyl);

    vp.setPlatformGeometry(bgPickCyl);
    
  }

 /*
  public void loadFloorData()
    {
    tl = new TederLayer(30,30);
    td.doLoadSingleLayer(tl,"tiles.dat");
    }
    
  public void addFloorTiles(TransformGroup tgParent)
    {
    loadFloorData();
    
    ArrayList al = new ArrayList(4);
    float tilex = 0.0f;
    float tilez = 0.0f;
    Point3f p1 = new Point3f(tilex,      0.0f,   tilez+TILE_WIDTH);
    Point3f p2 = new Point3f(tilex+TILE_WIDTH, 0.0f,   tilez+TILE_WIDTH);
    Point3f p3 = new Point3f(tilex+TILE_WIDTH, 0.0f,   tilez);
    Point3f p4 = new Point3f(tilex,      0.0f,   tilez);   
    al.add(p1); al.add(p2);  
    al.add(p3); al.add(p4);  
   
    // width is the x direction
    // height is the +z direction
    int i,j;
    int idx = 0;
    for (i=0;i<tl.wid;i++)
      {
      tilex = 0.0f;
      for (j=0;j<tl.hei;j++)
        {
        // change the points, they are already inside the array list
        p1.set(tilex,0.0f,tilez+TILE_WIDTH);
        p2.set(tilex+TILE_WIDTH,0.0f,tilez+TILE_WIDTH);
        p3.set(tilex+TILE_WIDTH,0.0f,tilez);
        p4.set(tilex,0.0f,tilez);
        
        // only add the tile if it has a texture
        if (tl.tiles[idx]>=0)
          tgFloor.addChild(new Tile(al,tl.tiles[idx]));
        else
          tgFloor.addChild(new Tile(al,-1));
        tilex = tilex + TILE_WIDTH;
        idx++;
        }
      tilez = tilez + TILE_WIDTH;
      }
    
    }
  public void addFloorCoords(ArrayList al)
    {
    float x = -0.5f;
    float z = -0.5f;
    // points created in counter-clockwise order
    Point3f p1 = new Point3f(x,      0.0f,   z+5.0f);
    Point3f p2 = new Point3f(x+5.0f, 0.0f,   z+5.0f);
    Point3f p3 = new Point3f(x+5.0f, 0.0f,   z);
    Point3f p4 = new Point3f(x,      0.0f,   z);   
    al.add(p1); al.add(p2);  
    al.add(p3); al.add(p4);  
    }
  
      class Tile extends Shape3D
        {
        private QuadArray tile;
        private int itexture;
        
        public Tile(ArrayList alCoords,int itextureInit)
          {
          itexture = itextureInit;
          Vector3f upNorm = new Vector3f(0.0f, 1.0f, 0.0f);   // pointing up
          
          tile = new QuadArray(alCoords.size(),GeometryArray.COORDINATES | 
                                               GeometryArray.TEXTURE_COORDINATE_2 |
                                               GeometryArray.NORMALS);
          Point3f[] points = new Point3f[alCoords.size()];
          alCoords.toArray( points );
          tile.setCoordinates(0, points);
 
          // assign texture coords to each quad
          // counter-clockwise, from bottom left
          TexCoord2f[] tcoords = new TexCoord2f[alCoords.size()];
          for(int i=0; i < alCoords.size(); i=i+4) 
            {
            tcoords[i] = new TexCoord2f(0.0f, 0.0f);   // for 1 point
            tcoords[i+1] = new TexCoord2f(1.0f, 0.0f);
            tcoords[i+2] = new TexCoord2f(1.0f, 1.0f);
            tcoords[i+3] = new TexCoord2f(0.0f, 1.0f);
            }
          tile.setTextureCoordinates(0, 0, tcoords);
         
          // set the surface normal
          for(int i=0; i < alCoords.size(); i++)
            tile.setNormal(i, upNorm);
            
          setGeometry(tile);
          
          Appearance app = new Appearance();

          if (itexture>=0)
            setAppearance(tileTextures[itexture]);
          else
            setAppearance(textureStone);

          }
        }
*/    
  public void createLights()
    {
    // simple ambient white light
    Color3f cWhite = new Color3f(1.0f,1.0f,1.0f);
    
    AmbientLight amb = new AmbientLight(cWhite);
    amb.setInfluencingBounds(boundsMain);
    bgMain.addChild(amb);
    
    // add one directional light pointing straight down
    Vector3f vLight1 = new Vector3f(-1.0f,1.0f,-1.0f);
    DirectionalLight light = new DirectionalLight(cWhite,vLight1);
    light.setInfluencingBounds(boundsMain);
    light.setDirection(new Vector3f(1.0f,-1.0f,1.0f));
    bgMain.addChild(light);
    }
  
  public void initViewPosition()
    {
    ViewingPlatform vp = su.getViewingPlatform();
    TransformGroup tgView = vp.getViewPlatformTransform();
    
    Transform3D t3d = new Transform3D();
    
    // setup the initial yaw pitch roll
    t3d.rotY(Math.toRadians(-90.0));
    t3dYPR.mul(t3d);

    // set the initial view position and orientation
    t3d.set(t3dYPR);
    t3dTmp2.set(vViewPos);
    t3d.mul(t3dTmp2);
    
    //tgView.getTransform(t3d);
    
    // look at the origin, orient up
    //t3d.lookAt(vViewPos,new Point3d(0,0,0),vViewOri);
    //t3d.invert();
    
    tgView.setTransform(t3d);
    }
  
  public boolean adjustMoveDistance(Vector3f vMove)
  {
    // NOTE: I do not like creating the point3d and v3d everytime in this method
    // it will have problems with performance and will need to be addressed
    // WWH
    
    // create the pick tool on first distance check, reuse for all later checks
    if (pickTool==null)
    {
      // create the picktool to always use the map
      pickTool = new PickTool(bgMap);
      /*
      pickCylinder = new PickCylinderRay(new Point3d(vViewPos.x,vViewPos.y,vViewPos.z), 
                                         new Vector3d(vMove.x,vMove.y,vMove.z), 
                                         0.10d);
      */
    }
      
    // set the shape of the pick tool as a ray from the current location
    // in the direction of the move
    pickTool.setShapeRay(new Point3d(vViewPos.x,vViewPos.y,vViewPos.z), 
                       new Vector3d(vMove.x,vMove.y,vMove.z));
    
    // get a pick to the closest shape
    PickResult res = pickTool.pickClosest();
    
    // if we got a result, check for the closest intersection distance
    if (res!=null)
    {
      int j;
      float closestIntersect = 10000000.0f; // a big number just in case
      for (j=0;j<res.numIntersections();j++)
      {
        if ((j==0) || (res.getIntersection(j).getDistance()<closestIntersect))
          closestIntersect = (float) res.getIntersection(j).getDistance();
      }
    
      //System.out.println("closestIntersect = "+closestIntersect);        
      if (closestIntersect<2.0)
      {
        // collision, adjust accordingly
        vMove.set(0.0f,0.0f,0.0f);

        return true;
      }
      
    }    

    return false;
  }
  
  public void checkSphereHit()
  {
    boolean didHit = true;
    
    // we keep checking because one bounce may lead to another until we
    // are headed away from all the walls
    while (didHit)
    {
      didHit = false;
      
      // we are sharing the pick tool
      if (pickTool==null)
      {
        // create the picktool to always use the map
        pickTool = new PickTool(bgMap);
      }
      // check for collision with a wall
      pickTool.setShapeRay(new Point3d(vSpherePos.x,vSpherePos.y,vSpherePos.z), 
                         new Vector3d(vSphereVel.x,vSphereVel.y,vSphereVel.z));

      // get a pick to the closest shape
      PickResult res = pickTool.pickClosest();

      // if we got a result, check for the closest intersection distance
      if (res!=null)
      {
        int j;
        float closestIntersect = 10000000.0f; // a big number just in case
        for (j=0;j<res.numIntersections();j++)
        {

          if ((j==0) || (res.getIntersection(j).getDistance()<closestIntersect))
            closestIntersect = (float) res.getIntersection(j).getDistance();
        }

        // bounce if we are going to move beyond the wall
        float magVel = vSphereVel.length();
        //System.out.println("closestIntersect = "+closestIntersect+" velLen="+magVel);    
        if (closestIntersect<magVel)
        {
          //System.out.println("sphere hit dist="+closestIntersect+" node="+res.getObject());

          // need the surface normal for the object it just hit
          Node nd = res.getObject();

          // we have it as a node, make sure it is a shape 3d
          if (nd instanceof Shape3D)
          {
            Shape3D sh = (Shape3D) nd;
            Geometry geo = sh.getGeometry();

            // we have the geometry, make sure it is a GeometryArray we can get a surface normal from
            if (geo instanceof GeometryArray)
            {
              // finally!, after all that, we can get the surface normal
              // just use the normal from the first vertex.
              ((GeometryArray)geo).getNormal(0,v3fTmp);

              // get a unit vector from the velocity
              //v3fTmp3.normalize(vSphereVel);
              // I had to double normalize here because a 180 degree vector with value
              // = 0.99999994 caused the normal to not be unit length - very odd
              v3fTmp3.set(vSphereVel);
              v3fTmp3.normalize();
              v3fTmp3.normalize();

              // get the dot product between the two
              float f = (float) v3fTmp.dot(v3fTmp3);

              // -1 for the dot product means the vectors are parallel and 180 in direction
              if (f==-1.0f)
              {
                // direct hit, the vectors face each other exactly, just reverse the velocity
                //System.out.println("negate the velocity");
                vSphereVel.negate();

                // subtract a little friction to make up for the fact that the
                // sphere didn't actually touch the wall
                vSphereVel.scale(0.90f);

                didHit = true;

                // set the sphere to that intersection point
                // WWH - exercise for later

              }
              else  if (f<0.0f)  // obtuse angle dot product - headed towards object
              {
                /* now for the not so simple case of an arbitrary hit */
                /* dot < 0.0 means we are headed towards the object (obtuse angle) */

                /* what we do here is rotate the velocity vector around the
                 * cross product of the two vectors
                 */
                v3fTmp2.cross(v3fTmp,v3fTmp3);            
                //System.out.println("Normal = "+v3fTmp+"  vel="+vSphereVel+" normvel="+v3fTmp3+"  dot="+v3fTmp.dot(v3fTmp3)+" cross="+v3fTmp2);

                // convert it to an angle
                f = (float) Math.toDegrees(Math.acos(f));

                // ret ang is the angle from the surface normal and the new bounce/return angle
                // draw some pictures to decide why 180-f
                float retang = 180.0f - f;

                // reflect the velocity as we will rotate that through the surface normal
                vSphereVel.negate();
                
                // rotate that the angle between the normal *2
                float frot = retang * 2.0f;

                // reusing the temporary AxisAngle that YPR uses
                //System.out.println("rot ang = "+frot);
                aaYPR.set(v3fTmp2,(float)Math.toRadians(frot));

                // create a matrix then transform with this rotate
                m3fTmp1.set(aaYPR);
                t3dTmp.set(m3fTmp1);

                // multiply the current velocity by that rotation
                t3dTmp.transform(vSphereVel);

                // subtract a little friction to make up for the fact that the
                // sphere didn't actually touch the wall
                // otherwise the bounce gets larger and larger. This is a kludge
                vSphereVel.scale(0.90f);

                //System.out.println(" post bounce vel="+vSphereVel);

                didHit = true;
              }
            }
            //System.out.println("is a shape3d "+((Shape3D)nd).getGeometry());
          }
        } // closest intersect

      } // check pick result
    } // while didHit
  }
  
  public void tickSphere()
  { 
    // add some gravity to the sphere
    vSphereVel.y = vSphereVel.y - 0.05f;

    checkSphereHit();
    
    // add the current velocity to the sphere
    vSpherePos.add(vSphereVel);

    // create a location translation transform for the sphere
    t3dTmp.setTranslation(vSpherePos);
        
    // set the changed transform
    tgSphere.setTransform(t3dTmp);    
 }
    
  public void tick()
    {
    tickSphere();
    updateHud();
 
    if ((keyForward==true) || (keyBackward==true))
      {
      if (keyForward)
        v3fTmp.set(0.0f,0.0f,-1.0f);
      else
        v3fTmp.set(0.0f,0.0f,1.0f);
     
      // transform the unit vector
      t3dYPR.transform(v3fTmp);
      
      // don't want a full unit, or it goes too fast
      v3fTmp.scale(0.1f);
     
      // map collision detection
      adjustMoveDistance(v3fTmp);

      // add that direction to the current position
      vViewPos.add(v3fTmp);
      }
      
    if (keyLeft == true)
      {
      // create a vector in the y direction
      v3fTmp.set(0.0f,1.0f,0.0f);
      
      // rotate that vector into the current orientation
      t3dYPR.transform(v3fTmp);
      
      // create axis to rotate around that y axis in our orientation coordinate space
      aaYPR.set(v3fTmp,(float)Math.toRadians(TURNDELTA));
      
      // set a temporary transform
      m3fTmp1.set(aaYPR);
      t3dTmp.set(m3fTmp1);
      
      // multiply the current view by that rotation
      t3dYPR.mul(t3dTmp);
      t3dYPR.normalize();
      }
    if (keyRight == true) 
      {
      // create a vector in the y direction
      v3fTmp.set(0.0f,1.0f,0.0f);
      
      // rotate that vector into the current orientation
      t3dYPR.transform(v3fTmp);
      
      // create axis to rotate around that y axis in our orientation coordinate space
      aaYPR.set(v3fTmp,(float)-Math.toRadians(TURNDELTA));
      
      // set a temporary transform
      m3fTmp1.set(aaYPR);
      t3dTmp.set(m3fTmp1);
      
      // multiply the current view by that rotation
      t3dYPR.mul(t3dTmp);
      t3dYPR.normalize();
      }
    if (keyStrafeLeft == true)
      {
      // movement in the -x direction
      v3fTmp.set(-1.0f,0.0f,0.0f);
      
      // rotate that vector into the current orientation
      t3dYPR.transform(v3fTmp);
      
      // don't want a full unit, or it goes too fast
      v3fTmp.scale(0.1f);

      // map collision detection
      adjustMoveDistance(v3fTmp);

      // add that direction to the current position
      vViewPos.add(v3fTmp);
     }
     
    if (keyStrafeRight == true)
      {
      // movement in the x direction
      v3fTmp.set(1.0f,0.0f,0.0f);
      
      // rotate that vector into the current orientation
      t3dYPR.transform(v3fTmp);
      
      // don't want a full unit, or it goes too fast
      v3fTmp.scale(0.1f);
      
      // map collision detection
      adjustMoveDistance(v3fTmp);

      // add that direction to the current position
      vViewPos.add(v3fTmp);
     }
   
    if (keyLookDown == true)
      {
      lookUpAngle = lookUpAngle+0.5f;
      if (lookUpAngle>45.0f)
        lookUpAngle=45.0f;
      }
    if (keyLookUp == true)
      {
      lookUpAngle = lookUpAngle-0.5f;
      if (lookUpAngle<-45.0f)
        lookUpAngle = -45.0f;
      }
   
    // set the temporary transform to the view/camera location
    t3dTmp.setIdentity();
    t3dTmp.setTranslation(vViewPos);

    // add the y lookup angle
    // create a vector in the x direction
    v3fTmp.set(1.0f,0.0f,0.0f);
      
    // rotate that vector into the current orientation
    t3dYPR.transform(v3fTmp);
      
    // create axis to rotate around that y axis in our orientation coordinate space
    aaYPR.set(v3fTmp,(float)-Math.toRadians(lookUpAngle));
      
    // set a temporary transform
    m3fTmp1.set(aaYPR);
    t3dTmp2.set(m3fTmp1);
      
    // multiply the current view by that rotation
    t3dTmp.mul(t3dTmp2);
    
    // multiply the transform by the current view orientation
    t3dTmp2.set(t3dYPR);
    t3dTmp.mul(t3dTmp2);
   
    tgView.setTransform(t3dTmp);
    }
  
  public void doFire()
  {
    // pick
    v3fTmp.set(0.0f,0.0f,-1.0f);
    
    // rotate the pick vector into the current view
    t3dYPR.transform( v3fTmp );
    
    PickCanvas pickCanvas = new PickCanvas(c3d, bgMain);
    pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
    pickCanvas.setTolerance(4.0f);
        
    pickCanvas.setShapeLocation(getWidth()/2,getHeight()/2);
    PickResult[] results = pickCanvas.pickAll();
    if (results!=null)
    {
      // find the closest object that we clicked on
      int i,j;
      int idx=0;
      float dst=100000000.0f; // a big number just in case
      for (i=0;i<results.length;i++)
      {
        float closestIntersect = 10000000.0f; // a big number just in case
        for (j=0;j<results[i].numIntersections();j++)
        {
          if ((j==0) || (results[i].getIntersection(j).getDistance()<closestIntersect))
            closestIntersect = (float) results[i].getIntersection(j).getDistance();
        }
        
        if ((i==0) || (closestIntersect<dst))
        {
          idx = i;
          dst = closestIntersect;
        }
      }
      
      // is the closest object the sphere
      if (results[idx].getObject()==sphere.getShape())
      {
        // v3fTmp now contains the ray that we hit the sphere with
        // we need to change the ball's velocity with it
        v3fTmp.normalize();
        
        vSphereVel.add(v3fTmp);
      }
    }
    
  }
  
  public void keyPressed(KeyEvent e)
    { 
    int keyCode = e.getKeyCode();
    if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
       ((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) 
      {
      parent.dispose();
      System.exit(0);    // exit() alone isn't sufficient most of the time
      }
    }
  public void keyReleased(KeyEvent e)
    {
    }
  public void keyTyped(KeyEvent e)
    {
    }
  
  protected void processKeyDown(int keycode)
    {
    if(keycode == KeyEvent.VK_W )
      keyForward = true;
    else if (keycode == KeyEvent.VK_S)
      keyBackward = true;
    else if (keycode == KeyEvent.VK_LEFT)
      keyLeft = true;
    else if (keycode == KeyEvent.VK_RIGHT)
      keyRight = true;
    else if (keycode == KeyEvent.VK_A)
      keyStrafeLeft = true;
    else if (keycode == KeyEvent.VK_D)
      keyStrafeRight = true;
    else if (keycode == KeyEvent.VK_UP)
      keyLookUp = true;
    else if (keycode == KeyEvent.VK_DOWN)
      keyLookDown = true;
    else if (keycode == KeyEvent.VK_SPACE)
      doFire();
    } 
    
  protected void processKeyUp(int keycode)
    {
    if(keycode == KeyEvent.VK_W )
      keyForward = false;
    else if (keycode == KeyEvent.VK_S)
      keyBackward = false;
    else if (keycode == KeyEvent.VK_LEFT)
      keyLeft = false;
    else if (keycode == KeyEvent.VK_RIGHT)
      keyRight = false;
    else if (keycode == KeyEvent.VK_A)
      keyStrafeLeft = false;
    else if (keycode == KeyEvent.VK_D)
      keyStrafeRight = false;
    else if (keycode == KeyEvent.VK_UP)
      keyLookUp = false;
    else if (keycode == KeyEvent.VK_DOWN)
      keyLookDown = false;
    }
  protected void processMouseMove(MouseEvent me)
    {
    //System.out.println("mex = "+me.getX());
    //System.out.println("mey = "+me.getY());
    diffx = lastMouseX - me.getX();
    diffy = lastMouseY - me.getY();
    
    //offcenterX = me.getX() - (getWidth()/2);
    //offcenterY = me.getY() - (getHeight()/2);
    offcenterX = me.getX() - lastMouseX;
    offcenterY = me.getY() - lastMouseY;
    
    lastMouseX = me.getX();
    lastMouseY = me.getY();
 
    lastMouseX = getWidth()/2;
    lastMouseY = getHeight()/2;
    // put the mouse back in the center
    Point p = getLocationOnScreen();
    robot.mouseMove(p.x+lastMouseX, p.y+lastMouseY);
    
    // probably too much processing to use a float for this check
    // fix later
    float abs = Math.abs(offcenterX);
    if (abs>0.0f)
      {
      // create a vector in the y direction
      v3fTmp.set(0.0f,1.0f,0.0f);
      // rotate that vector into the current orientation
      t3dYPR.transform(v3fTmp);
    
      // muck with the turn delta
      // bigger offsets make bigger turns
      float td = (float)-offcenterX*0.0005f;
      // maximum turns
      if (Math.abs(td)>0.02)
        {
        if (td>0.0f)
          td = 0.02f;
        else
          td = -0.02f;
        }
      
      // create axis to rotate around that y axis in our orientation coordinate space
      aaYPR.set(v3fTmp,td);
      
      // set a temporary transform
      m3fTmp1.set(aaYPR);
      t3dTmp.set(m3fTmp1);
      
      // multiply the current view by that rotation
      t3dYPR.mul(t3dTmp);
      t3dYPR.normalize();
      }
     
    abs = Math.abs(offcenterY);
    if ((abs>0.0f) && (abs<120.0))
      {
      float rotAng = offcenterY * 0.05f;
      //System.out.println("rotAng = "+rotAng);
      lookUpAngle = lookUpAngle + rotAng;
      if (lookUpAngle>45.0f)
        lookUpAngle=45.0f;
      else if (lookUpAngle<-45.0f)
        lookUpAngle=-45.0f;
      }
    }
  
  protected void processMouseClicked(MouseEvent me)
    {
    doFire();
    
    
    }

  } // Fps370Panel
  
class KeyUpBehavior extends Behavior
  {
  Fps370Panel t3p;
  WakeupCondition keyRelease;
  
  public KeyUpBehavior(Fps370Panel t3pInit)
    {
    t3p = t3pInit;
    
    keyRelease = new WakeupOnAWTEvent( KeyEvent.KEY_RELEASED );
    }
  
  public void initialize()
    {
    wakeupOn(keyRelease);
    }
    
  public void processStimulus(Enumeration criteria)
    {
    WakeupCriterion wakeup;
    AWTEvent[] event;
    boolean resetKeyDown = false;
    boolean resetKeyUp = false;

    //System.out.println("process stimulus: "+criteria);
    while( criteria.hasMoreElements() ) 
      {
      wakeup = (WakeupCriterion) criteria.nextElement();
      if( wakeup instanceof WakeupOnAWTEvent ) 
        {
        event = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
        for( int i = 0; i < event.length; i++ ) 
          {
          if ( event[i].getID() == KeyEvent.KEY_RELEASED )
            {
            t3p.processKeyUp(((KeyEvent)event[i]).getKeyCode());
            }
          }
        }
      }
    wakeupOn( keyRelease );
    } // end of processStimulus()
  }
 
class KeyDownBehavior extends Behavior
  {
  Fps370Panel t3p;
  WakeupCondition keyPress;
  
  public KeyDownBehavior(Fps370Panel t3pInit)
    {
    t3p = t3pInit;
    
    keyPress = new WakeupOnAWTEvent( KeyEvent.KEY_PRESSED);
    }
  
  public void initialize()
    {
    wakeupOn(keyPress);
    }
    
  public void processStimulus(Enumeration criteria)
    {
    WakeupCriterion wakeup;
    AWTEvent[] event;
    boolean resetKeyDown = false;
    boolean resetKeyUp = false;

    //System.out.println("process stimulus: "+criteria);
    while( criteria.hasMoreElements() ) 
      {
      wakeup = (WakeupCriterion) criteria.nextElement();
      if( wakeup instanceof WakeupOnAWTEvent ) 
        {
        event = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
        for( int i = 0; i < event.length; i++ ) 
          {
          if ( event[i].getID() == KeyEvent.KEY_PRESSED )
            {
            t3p.processKeyDown(((KeyEvent)event[i]).getKeyCode());
            }
          }
        }
      }
    wakeupOn( keyPress );
    } // end of processStimulus()
  }

class MouseMoveBehavior extends MouseBehavior
  {
  Fps370Panel t3p;
  WakeupCondition mouseMove;
  
  public MouseMoveBehavior(Fps370Panel t3pInit)
    {
    super(t3pInit,MouseEvent.MOUSE_MOVED);
    
    t3p = t3pInit;
    
    mouseMove = new WakeupOnAWTEvent( (long)(MouseEvent.MOUSE_MOVED | MouseEvent.MOUSE_CLICKED));
    }
  public void initialize()
    {
    wakeupOn(mouseMove);
    }
    
  public void processStimulus(Enumeration criteria)
    {
    WakeupCriterion wakeup;
    AWTEvent[] event;
    boolean resetKeyDown = false;
    boolean resetKeyUp = false;

    //System.out.println("process stimulus: "+criteria);
    while( criteria.hasMoreElements() ) 
      {
      wakeup = (WakeupCriterion) criteria.nextElement();
      if( wakeup instanceof WakeupOnAWTEvent ) 
        {
        event = ((WakeupOnAWTEvent)wakeup).getAWTEvent();
        for( int i = 0; i < event.length; i++ ) 
          {
          if ( event[i].getID() == MouseEvent.MOUSE_MOVED )
            {
            //System.out.println("Mouse moved");
            t3p.processMouseMove(((MouseEvent)event[i]));
            }
          else if ( event[i].getID() == MouseEvent.MOUSE_CLICKED)
          {
            //System.out.println("mouse clicked");
            t3p.processMouseClicked((MouseEvent)event[i]);
          }
          }
        }
      }
    wakeupOn( mouseMove );
    } // end of processStimulus()
  
  }

  class HudUpdater implements ImageComponent2D.Updater
  {
    private Fps370Panel parent;
    public HudUpdater(Fps370Panel parentInit)
    {
      parent = parentInit;
    }
    public void updateData(ImageComponent2D icUpdate, int x, int y, int width, int height)
    {
      //System.out.println("HUD Update Data x="+x+" y="+y+" w="+width+" h="+height);
      parent.drawHudImage();
      //sicUpdate.set(parent.imgHud);
      
    }
  }
  
