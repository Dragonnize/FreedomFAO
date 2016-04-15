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

public class VizualisationObject {
	
	public MeshView meshView;
	public TriangleMesh mesh;
	public GeometryContainer prev;

	public VizualisationObject(TriangleMesh file) {
		this.meshView = new MeshView(file);
	}
	
	public MeshView getMeshView() { return this.meshView; }

	public MeshView buildObject(Color diffuse, Color specular, double ScaleFactor) {
		
		PhongMaterial material = new PhongMaterial();
	      
	    meshView.setScaleX(ScaleFactor);
	    meshView.setScaleY(ScaleFactor);
	    meshView.setScaleZ(ScaleFactor);
	    
	    meshView.setRotationAxis(Rotate.Z_AXIS);
	    
        // setup material
        material.setDiffuseColor(diffuse);
        material.setSpecularColor(specular);
        
        // setup material & draw mode
        meshView.setMaterial(material);
        meshView.setDrawMode(DrawMode.FILL); // fill the mesh (wire also possible)
        meshView.setCullFace(CullFace.NONE); // show both sides of the mesh
        
        return meshView;
	}
}
