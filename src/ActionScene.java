
import java.util.ArrayList;

import org.scenicview.ScenicView;

import com.interactivemesh.jfx.importer.stl.StlImportOption;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;
import com.sun.j3d.utils.geometry.Triangulator;
import com.sun.j3d.utils.picking.PickResult;

import Math.GeometryContainer;
import Math.VFX3DUtil;
import javafx.animation.Timeline;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableFloatArray;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.ObservableFaceArray;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ActionScene extends Information {
	
	final Group root = new Group();
    final Group axisGroup = new Group();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(false);
    final PerspectiveCamera subSceneCamera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    final double cameraDistance = 450;
    final Xform moleculeGroup = new Xform();
    private Timeline timeline;
    boolean timelinePlaying = false;
    double ONE_FRAME = 1.0 / 24.0;
    double DELTA_MULTIPLIER = 200.0;
    double CONTROL_MULTIPLIER = 0.1;
    double SHIFT_MULTIPLIER = 0.1;
    double ALT_MULTIPLIER = 0.5;
    
    SubScene subScene;
	Scene scene;
	MeshView meshViews;
	Group FileEnd;
	GeneralSettings gl = new GeneralSettings();

	//private Group root;
	private PointLight pointLight;
	
	public Camera getCamera() { return this.camera; }
	public Group getFileEnd() { return this.FileEnd; }
	public void setFileEnd(Group file) { this.FileEnd = file; }
	public Scene getScene() { return this.scene; }
	
	public ActionScene(Stage window, BorderPane rootLayout, TriangleMesh fileSTL) throws Exception {
		
		initInformations();
		
		this.buildScene();
		VizualisationObject vizu3D = new VizualisationObject(fileSTL, world, axisGroup);
		world.getChildren().add(vizu3D.buildObject());
		world.getChildren().addAll(vizu3D.buildAxes());
		
        this.buildCamera();
        
		window.setTitle(this.getTITLE());
		window.getIcons().add(new Image(this.getICON()));
		
		subScene = new SubScene(root, gl.getWidthScene(), gl.getHeightScene(), true, SceneAntialiasing.DISABLED);
		subScene.setFill(Color.BEIGE);
        subScene.setCamera(subSceneCamera);
        // modifier par subScene.setLayout
        //subScene
        
        this.handleKeyboard(subScene, this.world, window);
        
        
        root.getChildren().addAll(subScene);
        
        rootLayout.setCenter(subScene);
        
        rootLayout.prefHeightProperty().bind(subScene.heightProperty());
        rootLayout.prefWidthProperty().bind(subScene.widthProperty());
		
		scene = new Scene(rootLayout);
		this.handleMouse(subScene, scene, this.world, meshViews);
		
		window.setScene(scene);
		
		// Pour la version de développement
       //	ScenicView.show(scene);
		
        window.show();
        
        scene.setCamera(camera);

    }
	
	private void buildScene() {
        root.getChildren().add(world);
    }

    private void buildCamera() {
    	
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(subSceneCamera);
        cameraXform3.setRotateZ(180.0);

        subSceneCamera.setNearClip(0.1);
        subSceneCamera.setFarClip(10000.0);
        subSceneCamera.setTranslateZ(-cameraDistance);
        
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);
    }

    private void handleMouse(SubScene subScene, Scene scene, final Node root, MeshView mesh) {
    	
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

                if (event.isControlDown()) {
                    modifier = 0.1;
                }
                if (event.isShiftDown()) {
                    modifier = 10.0;
                }
                if (event.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - getMouseDeltaX() * modifierFactor * modifier * 2.0);  // +
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + getMouseDeltaY() * modifierFactor * modifier * 2.0);  // -
                } else if (event.isSecondaryButtonDown()) {
                	
                    double z = subSceneCamera.getTranslateZ();
                    System.out.println(z);
                    if(MODEL_MIN_Z < z) {
                    	double newZ = z + mouseDeltaX * modifierFactor * modifier;
                    	subSceneCamera.setTranslateZ(newZ);
                    }else if(z < MODEL_MAX_Z) {
                    	double newZ = z + mouseDeltaX * modifierFactor * modifier;
                    	subSceneCamera.setTranslateZ(newZ);
                    }
                    
                } else if (event.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
                }
            }
        });
    }
    
	private void handleKeyboard(SubScene subScene, final Node root, Stage window) {
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
