import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;

public class SplashScreen extends JWindow {
	
    public SplashScreen(BranchGroup scene, int width, int height) {
        setSize(new Dimension(width, height));
        createCanvas3D(scene);
    }
    protected void createCanvas3D(BranchGroup scene) {
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas3D.setSize(getWidth(), getHeight());
        BufferedImage backgroundImage = createBackground();
        if (backgroundImage != null) {
            Background background = new Background(
                  new ImageComponent2D(ImageComponent2D.FORMAT_RGB,
                                       backgroundImage));
            BoundingSphere bounds = new BoundingSphere();
            bounds.setRadius(100.0);
            background.setApplicationBounds(bounds);
            scene.addChild(background);
        }
        SimpleUniverse universe = new SimpleUniverse(canvas3D);
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);
        View view = universe.getViewer().getView();
        view.setSceneAntialiasingEnable(true);
        getContentPane().add(canvas3D);
    }
    protected BufferedImage createBackground() {
        setLocationRelativeTo(null);
        Rectangle windowRect = getBounds();
        try {
            Robot robot = new Robot(getGraphicsConfiguration().getDevice());
            BufferedImage capture = robot.createScreenCapture(
                                      new Rectangle(windowRect.x,
                                                    windowRect.y,
                                                    windowRect.width,
                                                    windowRect.height));
            return capture;
        } catch (AWTException e) { }
        return null;
    }
}
