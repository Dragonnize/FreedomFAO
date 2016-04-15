
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

public class ActionScene {

    final PerspectiveCamera camera = new PerspectiveCamera(false);
    
    ActionSubScene GlobalSubScene;
	Scene scene;
	MeshView meshViews;
	Group FileEnd;
	Stage Window;

	//private Group root;
	private PointLight pointLight;
	
	public PerspectiveCamera getCamera() { return this.camera; }
	public Group getFileEnd() { return this.FileEnd; }
	public void setFileEnd(Group file) { this.FileEnd = file; }
	public Scene getScene() { return this.scene; }
	public Stage getWindow() { return this.Window; }
	
	public ActionScene(){
        
        //rootLayout.prefHeightProperty().bind(subScene.heightProperty());
        //rootLayout.prefWidthProperty().bind(subScene.widthProperty());
    }
	
	public void createScene(BorderPane rootLayout) {
		scene = new Scene(rootLayout);
	}
	
	public void render() {
		// Pour la version de développement
		// ScenicView.show(scene);
        
        scene.setCamera(camera);
    }
	
}
