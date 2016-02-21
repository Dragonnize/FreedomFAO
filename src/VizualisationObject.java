import Math.Function2D;
import Math.GeometryContainer;
import Math.SelectionResult;
import Math.VFX3DUtil;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.NodeUtil;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class VizualisationObject extends Information{
	
	Group axisGroup, world;
	MeshView meshViews, meshView;
	PhongMaterial material = new PhongMaterial();
	
	public Color diffuse = Color.BLUE;
	public Color specular = Color.RED;
	
	public TriangleMesh mesh;
	public GeometryContainer prev;
	private final PickingEvtHandler evtHandler = new PickingEvtHandler();
    private VNode node;
    private Point3D selectedPoint;
    private Double selectedValue;
    private VNode selectionReceiver;

	public VizualisationObject(TriangleMesh file, Xform world, Group axisGroup) {
		this.mesh = file;
		this.meshViews = new MeshView(file);
		this.world = world;
		this.axisGroup = axisGroup;
		this.prev = new GeometryContainer(meshViews);
		
		// Voir cette fonction pour développer la fonctionnalité requise.
		// plot(this.prev);
	}

	public MeshView buildObject() {
	      
	    meshViews.setScaleX(MODEL_SCALE_FACTOR);
	    meshViews.setScaleY(MODEL_SCALE_FACTOR);
	    meshViews.setScaleZ(MODEL_SCALE_FACTOR);
	    meshViews.setRotationAxis(Rotate.Z_AXIS);
	    
        // setup material
        material.setDiffuseColor(diffuse);
        material.setSpecularColor(specular);
        
        // reuse mesh view from prev container if avalable
        if (prev != null && prev.getView() != null) {
            meshView = prev.getView();
            meshView.setMesh(mesh);
        } else {
            meshView = new MeshView(mesh);
        }
        
        // setup material & draw mode
        meshView.setMaterial(material);
        meshView.setDrawMode(DrawMode.FILL); // fill the mesh (wire also possible)
        meshView.setCullFace(CullFace.NONE); // show both sides of the mesh
        
        meshView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	
                event.consume();
            }
        });

        GeometryContainer result;

        // reuse previous container if available
        if (prev != null) {
            result = prev;
            result.setView(meshView);
        } else {
            result = new GeometryContainer(meshView);
        }
        
        return result.getView();
	}
	
	public Group buildAxes() {
    	
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(240.0, 1, 1);
        final Box yAxis = new Box(1, 240.0, 1);
        final Box zAxis = new Box(1, 1, 240.0);
        
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
        
		axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		
		return axisGroup;
    }

	//rotation behavior implementation
	class MouseBehaviorImpl1 implements EventHandler<MouseEvent> {
	
		 private double anchorAngleX;
		 private double anchorAngleY;
		 private double anchorX;
		 private double anchorY;
		 private final Rotate rotateX = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		 private final Rotate rotateZ = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
		 private MouseButton btn;
		
		 public MouseBehaviorImpl1(Node n, MouseButton btn) {
		     n.getTransforms().addAll(rotateX, rotateZ);
		     this.btn = btn;
		
		     if (btn == null) {
		         this.btn = MouseButton.MIDDLE;
		     }
		 }
		
		 @Override
		 public void handle(MouseEvent t) {
		     if (!btn.equals(t.getButton())) {
		         return;
		     }
		
		     t.consume();
		
		     if (MouseEvent.MOUSE_PRESSED.equals(t.getEventType())) {
		         anchorX = t.getSceneX();
		         anchorY = t.getSceneY();
		         anchorAngleX = rotateX.getAngle();
		         anchorAngleY = rotateZ.getAngle();
		         t.consume();
		     } else if (MouseEvent.MOUSE_DRAGGED.equals(t.getEventType())) {
		         rotateZ.setAngle(anchorAngleY + (anchorX - t.getSceneX()) * 0.7);
		         rotateX.setAngle(anchorAngleX - (anchorY - t.getSceneY()) * 0.7);
		
		     }
	
		 }
	}

    public GeometryContainer plot(GeometryContainer prevView) {

        if (prevView != null) {
            prevView.removeEventHandler(MouseEvent.ANY, evtHandler);
        }

		GeometryContainer geometry = VFX3DUtil.meshToParent(prevView, mesh,
                MouseEvent.ANY, evtHandler);

        evtHandler.updateValue(null, true);

        return geometry;
    }

    /**
     * @return the node
     */
    public VNode getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(VNode node) {
        this.node = node;
    }

    void setSelectionReceiver(VNode node) {

        if (this.selectionReceiver != null) {
            this.selectionReceiver.getValueObject().
                    setValue(new SelectionResult());
        }

        this.selectionReceiver = node;
    }

    class PickingEvtHandler implements EventHandler<MouseEvent> {

        private Circle c = new Circle(10);
        private Function2D function;

        public PickingEvtHandler() {
            c.setFill(new Color(0.0, 1.0, 0.2, 0.5));
            c.setStroke(new Color(1.0, 1.0, 1.0, 0.4));
            c.setStrokeWidth(2);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(c.radiusProperty(), c.getRadius())),
                    new KeyFrame(Duration.millis(800),
                            new KeyValue(c.radiusProperty(), c.getRadius() * 2.5)));
            
            timeline.setAutoReverse(true);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }

        @Override
        public void handle(MouseEvent event) {
            selectedPoint = event.getPickResult().getIntersectedPoint();

            if (event.getEventType() != MouseEvent.MOUSE_CLICKED) {
                return;
            }

            if (event.getButton() != MouseButton.SECONDARY) {
                return;
            }

            if (!(event.getSource() instanceof Group)) {
                return;
            }

            final Group group = (Group) event.getSource();

            if (group.getParent() == null) {
                return;
            }

            updateValue(group, false);
        }

        /**
         * @return the function
         */
        public Function2D getFunction() {
            return function;
        }

        /**
         * @param function the function to set
         */
        public void setFunction(Function2D function) {
            this.function = function;
        }

        private void updateValue(Group group, boolean scaled) {
            
            c.toFront();

            if (selectedPoint == null) {
                return;
            }                  

            if (c.getParent() != group && group != null) {
                NodeUtil.addToParent(group, c);
            }

            if (!scaled) {
                
                c.setTranslateX(selectedPoint.getX());
                c.setTranslateY(selectedPoint.getY());
                c.setTranslateZ(selectedPoint.getZ());
                
                selectedPoint = new Point3D(
                        selectedPoint.getX() / scaleX,
                        selectedPoint.getY() / scaleY,
                        selectedPoint.getZ() / scaleZ);
            }

//            System.out.println("point: " + selectedPoint);
            if (selectedPoint != null) {
                selectedValue = getFunction().run(selectedPoint.getX(),
                        selectedPoint.getY());

                if (selectionReceiver != null) {
                    selectionReceiver.getValueObject().setValue(
                            new SelectionResult(
                                    selectedPoint, selectedValue));
                }

//                System.out.println("value = " + selectedValue);
            } else {
                selectedValue = null;
            }
        }
    }
}
