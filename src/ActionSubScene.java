import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;

public class ActionSubScene extends Information{
	
	final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
	final PerspectiveCamera subSceneCamera = new PerspectiveCamera(true);
	final SubScene subScene;
	final double cameraDistance = 450;
    final Xform moleculeGroup = new Xform();
    private Timeline timeline;
    boolean timelinePlaying = false;
    double ONE_FRAME = 1.0 / 24.0;
    double DELTA_MULTIPLIER = 200.0;
    double CONTROL_MULTIPLIER = 0.1;
    double SHIFT_MULTIPLIER = 0.1;
    double ALT_MULTIPLIER = 0.5;
    
    Color colorBackground = Color.BEIGE;

	public ActionSubScene(Parent root, double width, double height) {
		subScene = new SubScene(root, width, height);
		subScene.setFill(colorBackground);
		subScene.setCamera(subSceneCamera);
	}

	public ActionSubScene(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
		subScene = new SubScene(root, width, height, depthBuffer, antiAliasing);
		subScene.setFill(colorBackground);
		subScene.setCamera(subSceneCamera);
	}
	
	public SubScene getSubScene() { return this.subScene; }
	
	public PerspectiveCamera getSubSceneCamera() { return this.subSceneCamera; }
	
    public void buildCamera() {
    	
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(getSubSceneCamera());
        cameraXform3.setRotateZ(180.0);

        getSubSceneCamera().setNearClip(0.1);
        getSubSceneCamera().setFarClip(10000.0);
        getSubSceneCamera().setTranslateZ(-cameraDistance);
        
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);

    }
    
    public Group getCamera() {
    	return this.cameraXform;
    }
	
	public void handleMouse(Scene scene, final Node root, MeshView mesh) {
    	
        subScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	setMouseOldX(mousePosX);
                setMouseOldY(mousePosY);
                setMouseX(event.getSceneX());
                setMouseY(event.getSceneY());
                event.consume();
            }
        });
        
        subScene.setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				setGetX(event.getX());
				setGetY(event.getY());
				setGetZ(event.getZ());
				event.consume();
			}
	    	
	    });
        
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	setMouseOldX(mousePosX);
                setMouseOldY(mousePosY);
                setMouseX(event.getSceneX());
                setMouseY(event.getSceneY());
                setDeltaX(getMouseX(), getMouseOldX());
                setDeltaY(getMouseY(), getMouseOldY());

                double modifier = 1.0;
                double modifierFactor = 0.1;

                if (event.isControlDown()) { modifier = 0.1; }
                if (event.isShiftDown()) { modifier = 10.0; }
                
                if (event.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - getMouseDeltaX() * modifierFactor * modifier * 2.0);  // +
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + getMouseDeltaY() * modifierFactor * modifier * 2.0);  // -
                } else if (event.isSecondaryButtonDown()) {
                	
                    double z = getSubSceneCamera().getTranslateZ();
                    System.out.println(z);
                    if(MODEL_MIN_Z < z) {
                    	double newZ = z + mouseDeltaX * modifierFactor * modifier;
                    	getSubSceneCamera().setTranslateZ(newZ);
                    }else if(z < MODEL_MAX_Z) {
                    	double newZ = z + mouseDeltaX * modifierFactor * modifier;
                    	getSubSceneCamera().setTranslateZ(newZ);
                    }
                    
                } else if (event.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
                }
            }
        });
    }
	
	public void handleKeyboard(final Node root, Stage window, PerspectiveCamera camera, Group axisGroup) {
        final boolean moveCamera = true;
        subScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Z:
                        if (event.isShiftDown()) {
                            cameraXform.ry.setAngle(0.0);
                            cameraXform.rx.setAngle(0.0);
                            camera.setTranslateZ(-300.0);
                        }
                        cameraXform2.t.setX(0.0);
                        cameraXform2.t.setY(0.0);
                        break;
                    case X:
                        if (event.isControlDown()) {
                            if (axisGroup.isVisible()) {
                                axisGroup.setVisible(false);
                            } else {
                                axisGroup.setVisible(true);
                            }
                        }
                        break;
                    case S:
                        if (event.isControlDown()) {
                            if (moleculeGroup.isVisible()) {
                                moleculeGroup.setVisible(false);
                            } else {
                                moleculeGroup.setVisible(true);
                            }
                        }
                        break;
                    case SPACE:
                        if (timelinePlaying) {
                            timeline.pause();
                            timelinePlaying = false;
                        } else {
                            timeline.play();
                            timelinePlaying = true;
                        }
                        break;
                    case UP:
                        if (event.isAltDown() && event.isShiftDown()) {
                            cameraXform2.t.setY(cameraXform2.t.getY() - 10.0 * CONTROL_MULTIPLIER);
                        } else if (event.isControlDown() && event.isShiftDown()) {
                            cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 10.0 * ALT_MULTIPLIER);
                        } else if (event.isAltDown()) {
                            cameraXform2.t.setY(cameraXform2.t.getY() - 1.0 * CONTROL_MULTIPLIER);
                        } else if (event.isShiftDown()) {
                            cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 2.0 * ALT_MULTIPLIER);
                        } else if (event.isControlDown()) {
                            double z = camera.getTranslateZ();
                            double newZ = z + 5.0 * SHIFT_MULTIPLIER;
                            camera.setTranslateZ(newZ);
                        }
                        break;
                    case DOWN:
                        if (event.isAltDown() && event.isShiftDown()) {
                            cameraXform2.t.setY(cameraXform2.t.getY() + 10.0 * CONTROL_MULTIPLIER);
                        } else if (event.isControlDown() && event.isShiftDown()) {
                            cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 10.0 * ALT_MULTIPLIER);
                        } else if (event.isAltDown()) {
                            cameraXform2.t.setY(cameraXform2.t.getY() + 1.0 * CONTROL_MULTIPLIER);
                        } else if (event.isShiftDown()) {
                            cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 2.0 * ALT_MULTIPLIER);
                        } else if (event.isControlDown()) {
                            double z = camera.getTranslateZ();
                            double newZ = z - 5.0 * SHIFT_MULTIPLIER;
                            camera.setTranslateZ(newZ);
                        }
                        break;
                    case RIGHT:
                        if (event.isControlDown() && event.isShiftDown()) {
                            cameraXform2.t.setX(cameraXform2.t.getX() + 10.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown() && event.isShiftDown()) {
                            cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 10.0 * ALT_MULTIPLIER);
                        } else if (event.isControlDown()) {
                            cameraXform2.t.setX(cameraXform2.t.getX() + 1.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown()) {
                            cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 2.0 * ALT_MULTIPLIER);
                        }
                        break;
                    case LEFT:
                        if (event.isControlDown() && event.isShiftDown()) {
                            cameraXform2.t.setX(cameraXform2.t.getX() - 10.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown() && event.isShiftDown()) {
                            cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 10.0 * ALT_MULTIPLIER);  // -
                        } else if (event.isControlDown()) {
                            cameraXform2.t.setX(cameraXform2.t.getX() - 1.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown()) {
                            cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 2.0 * ALT_MULTIPLIER);  // -
                        }
                        break;
                        
                    case F11:
    	                if (window.isFullScreen()) {
    	                    window.setFullScreen(false);
    	                } else {
    	                    window.setFullScreen(true);
    	                }
                        break;
                }
            }
        });
    }
}
