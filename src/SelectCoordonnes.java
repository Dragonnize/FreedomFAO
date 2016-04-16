

import com.sun.prism.paint.Color;

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
import javafx.event.EventType;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class SelectCoordonnes{
	
	private double scaleX, scaleY, scaleZ;
	private Point3D selectedPoint;
	private Double selectedValue;
	private VNode selectionReceiver;
	private VNode node;
	private final PickingEvtHandler evtHandler = new PickingEvtHandler();

	Point3D point = new Point3D(0, 0, 0);
	Circle circle;
	Color colorCircle = Color.RED;
	GeometryContainer geometry;
	public MeshView MeshView;
	
	public MeshView getMeshView() { return this.MeshView; }
	
	public SelectCoordonnes(TriangleMesh mesh, MeshView meshView) {
		
		//meshView.addEventHandler(MouseEvent.ANY, evtHandler);
	
		
		GeometryContainer prevView = new GeometryContainer(meshView);
		
		// voit si la variable preview est différente de vide et si c'est le cas il supprime ça valeur.
		if (prevView != null) {
		    prevView.removeEventHandler(MouseEvent.ANY, evtHandler);
		}
		
		// la variable evtHandler sert stocker la valeur de la variable function qui va être utiliser ensuite pour générer un affichage.
		
		
		
		geometry = meshToParent(prevView, mesh,
		        Color.BLUE, Color.WHITE, MouseEvent.ANY, evtHandler);
		
		
		evtHandler.updateValue(null, true);
		
        this.MeshView = meshView;
    }
	
	public static GeometryContainer meshToParent(GeometryContainer prev,
            TriangleMesh mesh, Color diffuse, Color specular,
            EventType<MouseEvent> type, EventHandler<MouseEvent> evtHandler) {

        // convert mesh to node (via meshview)
        final GeometryContainer meshNode = meshToNode(prev, mesh, diffuse, specular);

        // add the specified eventhandler (if any)
        if (type != null && evtHandler != null) {
            meshNode.getGroup().addEventHandler(type, evtHandler);
        }

        return meshNode;
    }
	
	public static GeometryContainer meshToNode(GeometryContainer prev,
            TriangleMesh mesh, Color diffuse, Color specular) {

        // material that shall be used to visualize the mesh
        PhongMaterial material = new PhongMaterial();
        
        // setup material
        material.setDiffuseColor(diffuse);
        material.setSpecularColor(specular);
        MeshView meshView;

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

        GeometryContainer result;

        // reuse previous container if available
        if (prev != null) {
            result = prev;
            result.setView(meshView);
        } else {
            result = new GeometryContainer(meshView);
        }

        return result;
    }

	//rotation behavior implementation
	class MouseBehaviorImpl1 implements EventHandler<MouseEvent> {
	
		 private double originAngleX;
		 private double originAngleY;
		 private double originX;
		 private double originY;
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
		         originX = t.getSceneX();
		         originY = t.getSceneY();
		         originAngleX = rotateX.getAngle();
		         originAngleY = rotateZ.getAngle();
		         t.consume();
		     } else if (MouseEvent.MOUSE_DRAGGED.equals(t.getEventType())) {
		         rotateZ.setAngle(originAngleY + (originX - t.getSceneX()) * 0.7);
		         rotateX.setAngle(originAngleX - (originY - t.getSceneY()) * 0.7);
		
		     }
	
		 }
	}
	
	class PickingEvtHandler implements EventHandler<MouseEvent> {

        private Circle c = new Circle(10);
        private Function2D function;

        public PickingEvtHandler() {
           // c.setFill(new Color(0.0, 1.0, 0.2, 0.5));
            //c.setStroke(new Color(1.0, 1.0, 1.0, 0.4));
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