/*
    - only could see right eye in vr compositor...
    - 5089's gui mode isn't working, quad is being rotated just by looking around
    - getScreenCoordinates should kinda sorta work in VR
    - should use new compositor submit feature for custom distortion mesh *IN PROGRESS, SEE TODO IN VR_VIEW_MANAGER*
    - mouse cursor still visible in VR JFrame...
 */
package jmevr;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.MagFilter;
import com.jme3.texture.Texture.MinFilter;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import jmevr.app.VRApplication;
import jmevr.input.OpenVR;
import jmevr.post.CartoonSSAO;
import jmevr.util.VRGuiManager;
import jmevr.util.VRGuiManager.POSITIONING_MODE;
import jopenvr.JOpenVRLibrary;

/**
 *
 * @author reden
 */
public class TestOpenVR extends VRApplication {

    public static void main(String[] args){
        TestOpenVR test = new TestOpenVR();
        //test.preconfigureVRApp(PRECONFIG_PARAMETER.USE_STEAMVR_COMPOSITOR, false);
        //test.preconfigureVRApp(PRECONFIG_PARAMETER.USE_CUSTOM_DISTORTION, true);
        //test.preconfigureVRApp(PRECONFIG_PARAMETER.USE_JFRAME_EXTENDED_BACKUP, true);        
        test.preconfigureVRApp(PRECONFIG_PARAMETER.FORCE_VR_MODE, true);
        test.setRenderer(AppSettings.LWJGL_OPENGL3);
        test.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_CURVED_SURFACE, true);
        test.preconfigureVRApp(PRECONFIG_PARAMETER.FLIP_EYES, true);
        test.preconfigureVRApp(PRECONFIG_PARAMETER.SET_GUI_OVERDRAW, true);
        test.setFrustrumNearFar(0.5f, 512f);
        test.start();
    }
    
    Node boxes = new Node("");
    Spatial observer;
    boolean moveForward, moveBackwards, rotateLeft, rotateRight;
    Material mat;
    Node mainScene;
    
    @Override
    public void simpleInitApp() {        
        initTestScene();
    }
    
    private void initTestScene(){
        mainScene = new Node("scene");
        observer = new Node("observer");
        mainScene.attachChild(SkyFactory.createSky(
                    assetManager, "Textures/Sky/Bright/spheremap.png", SkyFactory.EnvMapType.EquirectMap));
        
        Geometry box = new Geometry("", new Box(5,5,5));
        mat = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture noise = getAssetManager().loadTexture("Textures/noise.png");
        noise.setMagFilter(MagFilter.Nearest);
        noise.setMinFilter(MinFilter.Trilinear);
        noise.setAnisotropicFilter(16);
        mat.setTexture("ColorMap", noise);
                       
        // gui element
        Vector2f guiCanvasSize = VRGuiManager.getCanvasSize();
        Picture test = new Picture("testpic");
        test.setImage(assetManager, "Textures/crosshair.png", true);
        test.setWidth(192f);
        test.setHeight(128f);
        test.setPosition(guiCanvasSize.x * 0.5f - 192f * 0.5f, guiCanvasSize.y * 0.5f - 128f * 0.5f);
        guiNode.attachChild(test);
        
        // test any positioning mode here (defaults to AUTO_CAM_ALL)
        VRGuiManager.setPositioningMode(POSITIONING_MODE.AUTO_OBSERVER_ALL);
        VRGuiManager.setGuiScale(0.4f);
        
        box.setMaterial(mat);
        
        Geometry box2 = box.clone();
        box2.move(15, 0, 0);
        box2.setMaterial(mat);
        Geometry box3 = box.clone();
        box3.move(-15, 0, 0);
        box3.setMaterial(mat);        
        
        boxes.attachChild(box);
        boxes.attachChild(box2);
        boxes.attachChild(box3);
        rootNode.attachChild(boxes);
        
        observer.setLocalTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        
        VRApplication.setObserver(observer);
        mainScene.attachChild(observer);
        rootNode.attachChild(mainScene);
        
        addAllBoxes();

        initInputs();
        
        // use magic VR mouse cusor (same usage as non-VR mouse cursor)
        inputManager.setCursorVisible(true);
        
        // filter test (can be added here like this)
        // but we are going to save them for the F key during runtime
        /*
        CartoonSSAO cartfilt = new CartoonSSAO();
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(cartfilt);
        viewPort.addProcessor(fpp);        
        */
    }

     private void initInputs() {
        inputManager.addMapping("toggle", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("incShift", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("decShift", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("filter", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addMapping("dumpImages", new KeyTrigger(KeyInput.KEY_I));
        ActionListener acl = new ActionListener() {

            public void onAction(String name, boolean keyPressed, float tpf) {
                if(name.equals("incShift") && keyPressed){
                    VRGuiManager.adjustGuiDistance(-0.1f);
                }else if(name.equals("decShift") && keyPressed){
                    VRGuiManager.adjustGuiDistance(0.1f);
                }else if(name.equals("filter") && keyPressed){
                    // adding filters in realtime
                    CartoonSSAO cartfilt = new CartoonSSAO();
                    FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
                    fpp.addFilter(cartfilt);
                    viewPort.addProcessor(fpp);
                    // filters added to main viewport during runtime,
                    // move them into VR processing
                    // (won't do anything if not in VR mode)
                    VRApplication.moveScreenProcessingToVR();
                }
                if( name.equals("toggle") ) {
                    VRGuiManager.positionGui();
                }                
                if(name.equals("forward")){
                    if(keyPressed){
                        moveForward = true;
                    } else {
                        moveForward = false;
                    }
                } else if(name.equals("back")){
                    if(keyPressed){
                        moveBackwards = true;
                    } else {
                        moveBackwards = false;
                    }
                } else if( name.equals("dumpImages") ) {
                    JOpenVRLibrary.VR_IVRCompositor_CompositorDumpImages(OpenVR.getVRCompositorInstance());
                }else if(name.equals("left")){
                    if(keyPressed){
                        rotateLeft = true;
                    } else {
                        rotateLeft = false;
                    }
                } else if(name.equals("right")){
                    if(keyPressed){
                        rotateRight = true;
                    } else {
                        rotateRight = false;
                    }
                }
                
                
            }
        };
        inputManager.addListener(acl, "forward");
        inputManager.addListener(acl, "back");
        inputManager.addListener(acl, "left");
        inputManager.addListener(acl, "right");
        inputManager.addListener(acl, "toggle");
        inputManager.addListener(acl, "incShift");
        inputManager.addListener(acl, "decShift");
        inputManager.addListener(acl, "filter");
        inputManager.addListener(acl, "dumpImages");
    }
     
     private float distance = 100f;
     private float prod = 0f;
     
     @Override
     public void simpleUpdate(float tpf){
         prod+=tpf;
         distance = 100f * FastMath.sin(prod);
         boxes.setLocalTranslation(0, 0, 200f+ distance);
         
         if(moveForward){
             observer.move(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(tpf*8f));
         }
         if(moveBackwards){
             observer.move(VRApplication.getFinalObserverRotation().getRotationColumn(2).mult(-tpf*8f));
         }
         if(rotateLeft){
             observer.rotate(0, 0.75f*tpf, 0);
         }
         if(rotateRight){
             observer.rotate(0, -0.75f*tpf, 0);
         }
     }
     
     private void addAllBoxes() {
        float distance = 8;
        for (int x = 0; x < 35; x++) {
            float cos = FastMath.cos(x * FastMath.PI / 16f) * distance;
            float sin = FastMath.sin(x * FastMath.PI / 16f) * distance;
            Vector3f loc = new Vector3f(cos, 0, sin);
            addBox(loc);
            loc = new Vector3f(0, cos, sin);
            addBox(loc);
        }

    }

    private void addBox(Vector3f location) {
        Box b = new Box(0.3f, 0.3f, 0.3f);

        Geometry leftQuad = new Geometry("Box", b);
        leftQuad.rotate(0.5f, 0, 0);
        leftQuad.setMaterial(mat);
        leftQuad.setLocalTranslation(location);
        mainScene.attachChild(leftQuad);
    }
}
