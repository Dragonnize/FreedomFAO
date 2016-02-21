import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

class ActionCamera extends Information {
        
        Cam camOffset = new Cam();
        Cam cam = new Cam();
        
        Shear shear = new Shear();
        
        public Shear getShear() {
        	return this.shear;
        }
            
        class Cam extends Group {
            Translate t  = new Translate();
	        Translate p  = new Translate();
	        Translate ip = new Translate();
	        Rotate rx = new Rotate();
	        { rx.setAxis(Rotate.X_AXIS); }
	        Rotate ry = new Rotate();
	        { ry.setAxis(Rotate.Y_AXIS); }
	        Rotate rz = new Rotate();
	        { rz.setAxis(Rotate.Z_AXIS); }
	        Scale s = new Scale();
	        
            public Cam() { 
            	super(); 
            	getTransforms().addAll(t, p, rx, rz, ry, s, ip); 
            }
        }
        
        //=========================================================================
        // CubeSystem.frameCam
        //=========================================================================
        public void frameCam(final Stage stage, final Scene scene) {
            setCamOffset(camOffset, scene);
            // cam.resetTSP();
            setCamPivot(cam);
            setCamTranslate(cam);
            setCamScale(cam, scene);
        }

        //=========================================================================
        // CubeSystem.setCamOffset
        //=========================================================================
        public void setCamOffset(final Cam camOffset, final Scene scene) {
            double width = scene.getWidth();
            double height = scene.getHeight();
            camOffset.t.setX(width/2.0);
            camOffset.t.setY(height/2.0);
        }
        
        //=========================================================================
        // setCamScale
        //=========================================================================
        public void setCamScale(final Cam cam, final Scene scene) {
            final Bounds bounds = cam.getBoundsInLocal();
            final double pivotX = bounds.getMinX() + bounds.getWidth()/2;
            final double pivotY = bounds.getMinY() + bounds.getHeight()/2;
            final double pivotZ = bounds.getMinZ() + bounds.getDepth()/2;

            double width = scene.getWidth();
            double height = scene.getHeight();

            double scaleFactor = 1.0;
            double scaleFactorY = 1.0;
            double scaleFactorX = 1.0;
            if (bounds.getWidth() > 0.0001) {
                scaleFactorX = width / bounds.getWidth(); // / 2.0;
            }
            if (bounds.getHeight() > 0.0001) {
                scaleFactorY = height / bounds.getHeight(); //  / 1.5;
            }
            if (scaleFactorX > scaleFactorY) {
                scaleFactor = scaleFactorY;
            } else {
                scaleFactor = scaleFactorX;
            }
            cam.s.setX(scaleFactor);
            cam.s.setY(scaleFactor);
            cam.s.setZ(scaleFactor);
        }

        //=========================================================================
        // setCamPivot
        //=========================================================================
        public void setCamPivot(final Cam cam) {
            final Bounds bounds = cam.getBoundsInLocal();
            final double pivotX = bounds.getMinX() + bounds.getWidth()/2;
            final double pivotY = bounds.getMinY() + bounds.getHeight()/2;
            final double pivotZ = bounds.getMinZ() + bounds.getDepth()/2;
            cam.p.setX(pivotX);
            cam.p.setY(pivotY);
            cam.p.setZ(pivotZ);
            cam.ip.setX(-pivotX);
            cam.ip.setY(-pivotY);
            cam.ip.setZ(-pivotZ);
        }
        
        //=========================================================================
        // setCamTranslate
        //=========================================================================
        public void setCamTranslate(final Cam cam) {
            final Bounds bounds = cam.getBoundsInLocal();
            final double pivotX = bounds.getMinX() + bounds.getWidth()/2;
            final double pivotY = bounds.getMinY() + bounds.getHeight()/2;
            cam.t.setX(-pivotX);
            cam.t.setY(-pivotY);
        }
        
        public void resetCam() {
            cam.t.setX(0.0);
            cam.t.setY(0.0);
            cam.t.setZ(0.0);
            cam.rx.setAngle(45.0);
            cam.ry.setAngle(-7.0);
            cam.rz.setAngle(0.0);
            cam.s.setX(1.25);
            cam.s.setY(1.25);
            cam.s.setZ(1.25);


            cam.p.setX(0.0);
            cam.p.setY(0.0);
            cam.p.setZ(0.0);

            cam.ip.setX(0.0);
            cam.ip.setY(0.0);
            cam.ip.setZ(0.0);

            final Bounds bounds = cam.getBoundsInLocal();
            final double pivotX = bounds.getMinX() + bounds.getWidth() / 2;
            final double pivotY = bounds.getMinY() + bounds.getHeight() / 2;
            final double pivotZ = bounds.getMinZ() + bounds.getDepth() / 2;

            cam.p.setX(pivotX);
            cam.p.setY(pivotY);
            cam.p.setZ(pivotZ);

            cam.ip.setX(-pivotX);
            cam.ip.setY(-pivotY);
            cam.ip.setZ(-pivotZ);
        }
}