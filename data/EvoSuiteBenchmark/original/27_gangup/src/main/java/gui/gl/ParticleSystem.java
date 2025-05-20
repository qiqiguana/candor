/* $Id: ParticleSystem.java,v 1.6 2004/05/05 11:55:12 emill Exp $
 *
 *
 * @author Emil Lundström <emill@kth.se>
 */

package gui.gl;

import javax.vecmath.*;

import com.xith3d.scenegraph.*;
import com.xith3d.datatypes.*;
import com.xith3d.loaders.texture.TextureLoader;
import com.xith3d.image.*;

import java.util.Random;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * A particle system in the partice engine. One particle system is the
 * same as one source of particles, e.g. one puff of smoke, one
 * explosion, etc.
 */

public class ParticleSystem extends TransformGroup {

    private ParticleManager manager;

    private int particle_count;

    private Particle[] particles;

    private Shape3D shape;

    // this is used to build a shape3d
    private TriangleStripArray particleGeometry;

    // facing stuff for each particle
    Coord3f sharedGeometryData[] =
    { new Coord3f(-1.0f,-1.0f, 0.0f ),
      new Coord3f( 1.0f,-1.0f, 0.0f ),
      new Coord3f(-1.0f, 1.0f, 0.0f ),
      new Coord3f( 1.0f, 1.0f, 0.0f ) };

    // default geometry data, used only to recalculate
    // sharedGeometryData when billboarding
    private static final Coord3f defaultGeometryData[] =
    { new Coord3f(-1.0f,-1.0f, 0.0f ),
      new Coord3f( 1.0f,-1.0f, 0.0f ),
      new Coord3f(-1.0f, 1.0f, 0.0f ),
      new Coord3f( 1.0f, 1.0f, 0.0f ) };

    private TexCoord2f sharedTextureData[] =
    { new TexCoord2f(0,0),
      new TexCoord2f(1,0),
      new TexCoord2f(0,1),
      new TexCoord2f(1,1) };

    // holds geometry coords for all particles (4 for each)
    private Point3f[] geometryCoordinates;

    private Color4f[] appearanceColors;

    // particle system specific data
    private int lifetime = 100000;
    private int age = 0;
    private int type = 0;

    public static final int TYPE_FLAME = 0;
    public static final int TYPE_SMOKE = 1;
    public static final int TYPE_EXPLOSION = 2;
    public static final int TYPE_MUZZLE = 3;
    public static final int TYPE_BLOOD = 4;
    public static final int TYPE_SUPERNOVA = 5;

    // IMPORTANT: Update this if another system type is added.
    static final int PARTICLE_SYSTEM_COUNT = 6;
    
    // default values for newly spawned particles for this particle
    // system.
    private Coord3f  particleSpawnLocation = new Coord3f();
    private Vector3f particleSpeed         = new Vector3f();
    private Vector3f particleAcceleration  = new Vector3f(); 
    private Color4f  particleColor         = new Color4f();
    private int      particleLifetime      = 0;
    private float    particleFadeSpeed     = 1f;

    // values used by the particles of this system
    public float  particleSpeedRandomness        = 0f;
    public float  particleAccelerationRandomness = 0f;
    public float  particleLocationRandomness     = 0.1f;
    public float  particleLifetimeRandomness     = 0.1f;
    
    public float  particleSize                   = 1f;
    public Random random = new Random();

    
    /**
     * Creates a new particle system using the given preset.
     *
     * @param m The particle manager.
     * @param type The preset type for this particle system.
     * @param loc The world coordinate where new particles spawn.
     * @param pc Number of particles.
     * @param lt The lifetime of this system.
     */
    public ParticleSystem(ParticleManager m, int t, Coord3f loc,
			  int pc, int lt) {

	particle_count = pc;
	manager = m;

	setUpSystem();
	newSystem(t,loc,lt);
    }

    /**
     * Sets up variables for a new system. If an old particle system
     * needs to be reused, calling this function should be
     * enough. I.e., there's no need to call setUpSystem().
     *
     * @param type The preset type for this particle system.
     * @param loc The world coordinate where new particles spawn.
     * @param lt The lifetime of this system.
     */
    public void newSystem(int t, Coord3f loc, int lt) {

	setTranslation(loc);
	lifetime = lt;
	age = 0;
	type = t;

	switch (type) {
	case TYPE_FLAME:
	    particleSpawnLocation.set(0f,0f,0f);
	    particleSpeed.set(0f,0.03f,0f);
	    particleAcceleration.set(0f,0f,0f);
	    particleColor.set(1f,0.4f,0.1f,1f);
	    particleLifetime = 30;
	    particleFadeSpeed = 1f;
	    particleSize = 0.2f;
	    particleSpeedRandomness = 0.001f;
	    particleAccelerationRandomness = 0f;
	    particleLocationRandomness = 0.05f;
	    particleLifetimeRandomness = 30f;
	    break;
	case TYPE_BLOOD:
	    particleSpawnLocation.set(0f,0f,0f);
	    particleSpeed.set(0f,0.01f,0f);
	    particleAcceleration.set(0f,-0.002f,0f);
	    particleColor.set(0.5f,0.01f,0.01f,1f);
	    particleLifetime = 40;
	    particleFadeSpeed = 2f;
	    particleSize = 0.03f;
	    particleSpeedRandomness = 0f;
	    particleAccelerationRandomness = 0f;
	    particleLocationRandomness = 0.01f;
	    particleLifetimeRandomness = 30f;
	    break;
	case TYPE_MUZZLE:
	    particleSpawnLocation.set(0f,0f,0f);
	    particleSpeed.set(0f,0.01f,0f);
	    particleAcceleration.set(0f,-0.001f,0f);
	    particleColor.set(0.7f,0.4f,0.03f,1f);
	    particleLifetime = 5;
	    particleFadeSpeed = 1f;
	    particleSize = 0.03f;
	    particleSpeedRandomness = 0.01f;
	    particleAccelerationRandomness = 0f;
	    particleLocationRandomness = 0.01f;
	    particleLifetimeRandomness = 0f;
	    break;
	case TYPE_SMOKE:
	    particleSpawnLocation.set(0f,0f,0f);
	    particleSpeed.set(0f,0.02f,0f);
	    particleAcceleration.set(0f,0f,0f);
	    particleColor.set(0.1f,0.1f,0.1f,1f);
	    particleLifetime = 150;
	    particleFadeSpeed = 1f;
	    particleSize = 0.4f;
	    particleSpeedRandomness = 0.001f;
	    particleAccelerationRandomness = 0f;
	    particleLocationRandomness = 0.001f;
	    particleLifetimeRandomness = 150f;
	    break;
	case TYPE_EXPLOSION:
	    particleSpawnLocation.set(0f,0f,0f);
	    particleSpeed.set(0f,0f,0f);
	    particleAcceleration.set(0f,0f,0f);
	    particleColor.set(0.5f,0.3f,0.1f,1f);
	    particleLifetime = 100;
	    particleFadeSpeed = 0.5f;
	    particleSize = 0.1f;
	    particleSpeedRandomness = 0f;
	    particleAccelerationRandomness = 0.1f;
	    particleLocationRandomness = 0.02f;
	    particleLifetimeRandomness = 0f;
	    break;
	case TYPE_SUPERNOVA:
	    particleSpeed.set(0f,0f,0f);
	    particleAcceleration.set(0f,0f,0f);
	    particleColor.set(0.8f,0.8f,0.6f,1f);
	    particleLifetime = 200;
	    particleFadeSpeed = 1f;
	    particleSize = 5f;
	    particleSpeedRandomness = 0f;
	    particleAccelerationRandomness = 0.1f;
	    particleLocationRandomness = 0.5f;
	    particleLifetimeRandomness = 0f;
	    break;
	}

	updateAllParticles();
    }

    /**
     * Sets this particle system's translation.
     */
    public void setTranslation(Vector3f loc) {
	Transform3D t = new Transform3D();
	t.setTranslation(loc);
	setTransform(t);
    }

    /**
     * Returns this particle system's type.
     */
    public int getType() {
	return type;
    }

    /**
     * Returns the number of particles in this particle system.
     */
    public int getParticleCount() {
	return particle_count;
    }

    private void setUpSystem() {
	// set up so each triangle strip consists of 4 vertice, i.e.
	// two triangles.
	int[] strips = new int[particle_count];
	for (int i=0; i < particle_count; i++) {
	    strips[i] = 4;
	}
	
	particles = new Particle[particle_count];
	geometryCoordinates = new Point3f[particle_count*4];
	appearanceColors = new Color4f[particle_count*4];

	// borrowed from javacooldude's particle engine example.
	// creates a trianglestriparray with 4 vertice per particle
	particleGeometry = new TriangleStripArray
	    (particle_count*4,
	     GeometryArray.TEXTURE_COORDINATE_2 |
	     GeometryArray.COORDINATES |
	     GeometryArray.COLOR_4,
	     strips);


	particleGeometry.setCapability
	    (GeometryArray.ALLOW_COORDINATE_WRITE);
	particleGeometry.setCapability
	    (GeometryArray.ALLOW_COLOR_WRITE);

	createShape3D();
    }

    /**
     * Returns a Shape3D object for the particles in this particle
     * system.
     */
    private void createShape3D() {
	ParticleAppearanceManager am =
	    new ParticleAppearanceManager("./dat/gfx/star2.png");
	shape = new Shape3D(particleGeometry,am.texturedAppearance);
	addChild(shape);
    }

    private void updateAllParticles() {
	for (int i=0; i < particle_count; i++) {
	    particles[i] = new Particle();
	    particles[i].newParticle(this,
				     particleSpawnLocation,
				     particleSpeed,
				     particleAcceleration,
				     particleColor,
				     particleLifetime,
				     particleFadeSpeed);
	    for (int j=0; j < 4; j++) {
		particleGeometry.setTextureCoordinate
		    (0,i*4+j,sharedTextureData[j]);
		geometryCoordinates[i*4+j] =
		    particles[i].particleCoordinates[j];
		appearanceColors[i*4+j] =
		    particles[i].color;
	    }
	    particles[i].updateParticle();
	}
    }
    
    /**
     * Makes the particles in this particle system face the correct
     * way. The correct way in this system is a plane perpendicular to
     * the camera direction vector, i.e. the particles use the same
     * transform as the camera.
     *
     * @param camera The camera transform.
     */
    public void billboardParticles(Transform3D camera) {
	Transform3D t = new Transform3D(camera);
	t.setTranslation(new Vector3f(0f,0f,0f));
	for (int i=0; i<4; i++) {
	    sharedGeometryData[i].set(defaultGeometryData[i]);
	    t.transform(sharedGeometryData[i]);
	}
    }

    /**
     * Updates this system and all particles in it.
     */
    public boolean updateSystem(Transform3D c) {
	
	for (int i=0; i < particle_count; i++) {
	    if (!particles[i].updateParticle()) {
		particles[i].newParticle(this,
					 particleSpawnLocation,
					 particleSpeed,
					 particleAcceleration,
					 particleColor,
					 particleLifetime,
					 particleFadeSpeed);
	    }
	}
	
	particleGeometry.setCoordinates(0,geometryCoordinates);
	particleGeometry.setColors(0,appearanceColors);

	billboardParticles(c);

	if (lifetime == 0 || age++ < lifetime)
	    return true;
	return false;
    }


    private static class ParticleAppearanceManager {
	
	public TransparencyAttributes transparencyAttributes =
	    new TransparencyAttributes();
	public RenderingAttributes    renderingAttributes =
	    new RenderingAttributes();
	public TextureAttributes      textureAttributes =
	    new TextureAttributes();
	public Appearance             texturedAppearance =
	    new Appearance();
	
	public Texture2D texture;

	/**
	 * Creates a new appearance manager for particle systems.
	 *
	 * @param textureFilename The initial texture file name.
	 */
	public ParticleAppearanceManager(String textureFilename) {

	    renderingAttributes.setDepthBufferEnable(false);

	    transparencyAttributes.setTransparencyMode
		(transparencyAttributes.BLENDED);
	    transparencyAttributes.setTransparency(0);
	    transparencyAttributes.setDstBlendFunction
		(transparencyAttributes.BLEND_ONE);
	    transparencyAttributes.setSrcBlendFunction
		(transparencyAttributes.BLEND_SRC_ALPHA);

	    textureAttributes.setTextureMode
		(TextureAttributes.MODULATE);
	    textureAttributes.setPerspectiveCorrectionMode
		(TextureAttributes.FASTEST);

	    texturedAppearance.setTransparencyAttributes
		(transparencyAttributes);
	    texturedAppearance.setRenderingAttributes
		(renderingAttributes);
	    texturedAppearance.setTextureAttributes
		(textureAttributes);

	    loadTexture(textureFilename);
	}

	/**
	 * Loads a texture to this manager's texturedAppearance.
	 *
	 * @param textureFilename Path to the texture file image.
	 */
	public void loadTexture(String textureFilename) {

	    File textureFile = new File(textureFilename);
	    BufferedImage textureImage = null;

	    try {
		textureImage = ImageIO.read(textureFile);

	    texture = (Texture2D)TextureLoader.getInstance().constructTexture
		(DirectBufferedImage.make(textureImage,false),
		 "RGB",
		 false,
		 Texture.BASE_LEVEL_LINEAR,
		 Texture.BASE_LEVEL_LINEAR,
		 Texture.WRAP,
		 false,
		 TextureLoader.SCALE_DRAW_BEST);

	    texturedAppearance.setTexture(texture);

	    } catch (IOException e) {
		System.err.println("error loading texture");
	    }
	}
    }
}
