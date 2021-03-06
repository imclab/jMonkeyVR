<b>Commercial games on Steam using jMonkeyVR: <a href="http://store.steampowered.com/app/414510/">5089</a> *
<a href="http://store.steampowered.com/app/363460">Spermination</a> * <a href="http://store.steampowered.com/app/329770/">4089</a></b>

Phr00t's jMonkeyEngine build is required: https://github.com/phr00t/jmonkeyengine (the main jME3 build might work, but VR-specific changes will be made first on Phr00t's build) -- you can just use the JAR/libs under the dist/ and lib/ folders

SteamVR is required. You can download & install it free with the Steam client (under Tools).

See <a href="https://github.com/phr00t/jMonkeyVR/blob/master/test/jmevr/TestjMonkeyVR.java">TestOpenVR.java</a> to see a full example of how to set up an application to use VR hardware & jME3.

<b>OSVR Support is a work-in-progress!</b>

<b>Adding OpenVR support to your application:</b>

(0) Use jme3-lwjgl3 in your project, not jme3-lwjgl (needs GLFW in LWJGL 3).

(1) Add the latest jna-x.x.x & JMonkeyVR.jar to the project.

(2) Instead of extending SimpleApplication/BaseApplication for your Main class, extend VRApplication. This class will handle starting your application in VR mode if VR hardware is detected, or normal mode otherwise. If you want to configure a few things, use the preconfigureVRApp function as follows.

```
public class Main extends VRApplication {
     
     public static Main MyApp;
 
    public static void main(String[] args) {
         MyApp = new Main();
         MyApp.preconfigureVRApp(PRECONFIG_PARAMETER.xxx, [true|false]); // optional
         MyApp.setFrustrumNearFar(0.5f, 512f); // optional, set near/far rendering of cameras
         MyApp.start();
     }
 }
```

(3) To attach headset view to a spatial (if not done, will try to attach to default camera instead):

```
 Spatial observer = new Node("Observer");
 VRApplication.setObserver(observer);
 rootNode.attachChild(observer);
```

<i>PRO-TIP: Use anisotropic filtering & do NOT directly access the VRApplication "cam", use getCamera() instead!</i>

<b>How do I use VR instancing?</b>

Rendering with instancing can improve performance significantly, since there is only one viewport & rendering pass. However, it requires a little work by modifying vertex shaders & material definitions. Take a look at the Unshaded files here (near the bottom) to see what needs to be included:

<a href="https://github.com/phr00t/jMonkeyVR/tree/master/src/jmevr/shaders">https://github.com/phr00t/jMonkeyVR/tree/master/src/jmevr/shaders</a>

You also need to enable VR instancing with a preconfigure parameter:

    MyApp.preconfigureVRApp(PRECONFIG_PARAMETER.INSTANCE_VR_RENDERING, true);
    
As of now, many core jMonkeyEngine materials are not ready for VR instancing. You will likely need to write your own shaders to include VR instancing support (or copy & paste your own modified ones from core). You can use the Unshaded.j3md ones linked above, but they are very basic (no lighting, etc).

Using instancing elsewhere in your scene will conflict with jMonkeyVR's automated instancing system. I recommend not using it.

Using BitmapText someplace within the scene, and not part of the GUI? That is OK! Just make sure you have another BitmapFont loaded for 3D scene use, since it will be automatically instanced. If you try and use the same BitmapFont, GUI text won't be visible. Remember, your BitmapFont loader will need to use a shader that supports instancing (like Unshaded linked above).

Shadows & CartoonSSAO should now work with VR instancing. Use InstancedDirectionalShadowFilter for simple directional shadows. This filter will automatically use special shadows when in VR instancing, and normal shadows when not in VR mode.

<b>Using the GUI</b>

Get the size of the GUI "canvas" by calling VRGuiManager.getCanvasSize(). This will return the screen resolution when not in VR mode, and the virtual resolution size of the GUI canvas in VR mode. 

The GUI system has two options: automatic & manual positioning. Automatic positioning will always keep the GUI elements floating infront of the view, while manual will let you center it manually, where it will stay as the player moves their head. The default is automatic positioning.

You can change options like so:

```
 VRGuiManager.setPositioningMode(POSITIONING_MODE.AUTO);
 VRGuiManager.setGuiDistance(0.8f);
 VRGuiManager.setGuiScale(0.5f);
```

To center the GUI's position manually, where it will stay until another manual position update is called (as long as the positioning mode is set to MANUAL):

```
 VRGuiManager.positionGui();
```

<b>Adding Filters during runtime (not needed with VR instancing)</b>

If you add filters during application intialization, they will be automatically moved to each eye <i>if</i> VR mode gets started. However, if after initialization, you add some filters, you will need to move them to the VR scene with this function:

     VRApplication.moveScreenProcessingToVR();
     
This handles moving them out of the VR scene & cloning them for each eye. It is safe to call this function even if you are not in VR mode (it will do nothing & return immediately in that case).
     
See the TestOpenVR.java example for more usage information.

<b>Don't be afraid to use the mouse!</b> Using inputManager.setCursorVisislbe(true) will work fine! This library will monitor mouse usage & replace the cursor with a 3D version at the GUI distance automatically. Make sure to get the cursor position using VRMouseManager.getCursorPosition(), and to render GUI elements within the screen size returned by VRGuiManager.getCanvasSize(). These functions will automatically return correct values, whether you are in VR or not.
