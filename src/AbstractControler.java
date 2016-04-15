import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;

public abstract class AbstractControler {
	
	protected AbstractModel Model;
	ImportFile file;

	public AbstractControler(AbstractModel model) {
		this.Model = model;
		init();
		Model.getRoot().getChildren().add(this.getWorld());
		
	}
	
	public void init() {
		this.Model.root = new Group();
		this.Model.world = new Xform();
	}
	
	public void reset() {
		this.Model.reset();
	}
	
	public void setNameFile(String file) { this.Model.nameFile = file; }
	public void setNameAxes(String axe) { this.Model.nameAxes = axe; }
	
	public String getNameFile() { return this.Model.nameFile; }
	public String getNameAxes() { return this.Model.nameAxes; }
	
	public void newFile() {
		
		if(this.getNameFile() != null && this.getNameAxes() != null) {
			getWorld().getChildren().remove(0);
			getWorld().getChildren().remove(0);
		}
	
		ImportFile importFile = new ImportFile();
		importFile.choiceFile();
		this.file = importFile;
		
		VizualisationObject vizu3DObject = new VizualisationObject(importFile.getTriangleMesh());
		MeshView meshTarget = vizu3DObject.buildObject(Color.GREY, Color.WHITE, 0.5);
		this.setNameFile(meshTarget.toString());
		
		// La target qui va être séléctionné.
		SelectCoordonnes select = new SelectCoordonnes(importFile.getTriangleMesh(), meshTarget);
		getWorld().getChildren().add(select.getMeshView());
		
		VizualisationAxes vizu3DAxes = new VizualisationAxes();
		getWorld().getChildren().add(vizu3DAxes.buildAxes());
		this.setNameAxes(vizu3DAxes.buildAxes().toString());
		
		
	}
	
	public void initCordsVizualisation(MouseEvent event) {
		Point3D cords = event.getPickResult().getIntersectedPoint();
		Model.cordX.set(cords.getX());
		Model.cordY.set(cords.getY());
		Model.cordZ.set(cords.getZ());
	}
	
	public Group getContent() {
		return this.getWorld();
	}
	
	public ImportFile getFile() {
		return this.file;
	}
	
	public Xform getWorld() { return this.Model.world; }
	public Group getRoot() { return this.Model.root; }
	
}
