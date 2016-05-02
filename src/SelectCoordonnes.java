import java.util.List;
import java.util.stream.Collectors;

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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class SelectCoordonnes extends StackPane{
	
	private double scaleX, scaleY, scaleZ;
	private Point3D selectedPoint;
	private Double selectedValue;
	private final PickingEvtHandler evtHandler = new PickingEvtHandler();

	Point3D point = new Point3D(0, 0, 0);
	Circle circle;
	Color colorCircle = Color.RED;
	GeometryContainer geometry;
	public MeshView MeshView;
	Group Group;
	boolean booleanTarget = false;
	
	public MeshView getMeshView() { return this.MeshView; }
	
	public SelectCoordonnes(TriangleMesh mesh, MeshView meshView) {
		
		this.init();
        
		Group.getChildren().add(meshView);
		
		meshView.addEventHandler(MouseEvent.ANY, evtHandler);
		
		Group.getChildren().add(evtHandler.getGroup());
		
		//groupSelector.getChildren().add(evtHandler.getCircle());
		
		// la variable evtHandler sert stocker la valeur de la variable function qui va être utiliser ensuite pour générer un affichage.
		
		//geometry = meshToParent(prevView, mesh, MouseEvent.ANY, evtHandler);
		
		//evtHandler.updateValue(null, true);
		
        this.MeshView = meshView;
    }
	
	public void init() {
		
		Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        setClip(clip);

        Group = new Group();
        
        //getChildren().add(getGroup());

        // add rotation behavior
        //VFX3DUtil.addMouseBehavior(getGroup(), getGroup(), MouseButton.PRIMARY);

        setMinSize(0, 0);
	}

    /**
     * group that contains the mesh view and optionally other nodes for
     * additional visualizations (e.g. ray picking circle)
     */
    private Group group;
    
    /**
     * @param view the view to set
     */
    public final void setView(MeshView view) {

        // remove all previously added mesh views
        List<Node> nodesToRemove
                = getGroup().getChildren().stream().filter(
                        e -> e instanceof MeshView).
                collect(Collectors.toList());

        getGroup().getChildren().removeAll(nodesToRemove);
        
        // finally add the new view
        getGroup().getChildren().add(view);

    }

    /**
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Clears this container (removes all children).
     */
    public void clear() {
        getChildren().clear();

        group = new Group();
        getChildren().add(getGroup());

        // add rotation behavior
        VFX3DUtil.addMouseBehavior(getGroup(), getGroup(), MouseButton.PRIMARY);
    }
    
    
    
    
	
	public Group getGroupSelector() { return Group; }
	
	public static GeometryContainer meshToParent(GeometryContainer prev,
            TriangleMesh mesh, EventType<MouseEvent> type, EventHandler<MouseEvent> evtHandler) {

        // convert mesh to node (via meshview)
        final GeometryContainer meshNode = meshToNode(prev, mesh);

        // add the specified eventhandler (if any)
        if (type != null && evtHandler != null) {
            meshNode.getGroup().addEventHandler(type, evtHandler);
        }

        return meshNode;
    }
	
	public static GeometryContainer meshToNode(GeometryContainer prev,
            TriangleMesh mesh) {

        // material that shall be used to visualize the mesh
        MeshView meshView;

        // reuse mesh view from prev container if avalable
        if (prev != null && prev.getView() != null) {
            meshView = prev.getView();
            meshView.setMesh(mesh);
        } else {
            meshView = new MeshView(mesh);
        }
        
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

        public Circle c = new Circle(10);
        public Group groupSelector = new Group();

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
        
        public Group getGroup() {
        	return this.groupSelector;
        }

        @Override
        public void handle(MouseEvent event) {
        	
        	if(event.getClickCount() == 1) {
        		this.updateValue();
        	}
        	
        	if(event.getClickCount() == 2) {
        		
	            selectedPoint = event.getPickResult().getIntersectedPoint();
	            
	            if (event.getEventType() != MouseEvent.MOUSE_CLICKED) {
	                return;
	            }
	
	            if (event.getButton() != MouseButton.PRIMARY) {
	                return;
	            }
	            
	            groupSelector.getChildren().add(c);
	            
	            c.toFront();
	
	            if (selectedPoint == null) {
	                return;
	            }
	                
	            c.setTranslateX(selectedPoint.getX());
	            c.setTranslateY(selectedPoint.getY());
	            c.setTranslateZ(selectedPoint.getZ());
	            
	            selectedPoint = new Point3D(
	                    selectedPoint.getX() / scaleX,
	                    selectedPoint.getY() / scaleY,
	                    selectedPoint.getZ() / scaleZ);
	        }
        	
        	event.consume();
        }
        
        public void updateValue(){
        	if(groupSelector.getChildren().size() > 0) {
        		groupSelector.getChildren().remove(0);
        	}
        }
    }
}