import java.util.ArrayList;

import Usinage.Contournage;
import Usinage.Electroerosion;
import Usinage.Lamage;
import Usinage.Percage;
import Usinage.Pointage;
import Usinage.Tool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;

public abstract class AbstractControler extends Information{
	
	Tool tool = new Tool();
    DataTree dataTree = new DataTree();
	protected AbstractModel Model;
	ImportFile file;
	TreeView<String> tree;
	VizualisationAxes vizu3DAxes;
	
	protected Stage PopupWindow;
	protected BorderPane borderPane;
	protected int width = 300;
	protected int height = 300;

	public AbstractControler(AbstractModel model) {
		this.Model = model;
		init();
		Model.getRoot().getChildren().add(this.getWorld());
	}
	
	public void init() {
		tree = dataTree.dataTree();
		this.Model.root = new Group();
		this.Model.world = new Xform();
	}
	
	public void setProgrammeName(String name) { Model.ProgrammeName.set(name); }
	public String getProgrammeName() { return Model.ProgrammeName.get(); }
	
	public void reset() {
		this.Model.reset();
	}
	
	public DataTree getDataTree() { return dataTree; }
	
	public void setNameFile(String file) { this.Model.nameFile = file; }
	public void setNameAxes(String axe) { this.Model.nameAxes = axe; }
	
	public String getNameFile() { return this.Model.nameFile; }
	public String getNameAxes() { return this.Model.nameAxes; }
	
	public Group getGroupAxes() { return this.Model.groupAxes; }
	
	public boolean getVisibleAxeX() { return this.Model.VisibleAxeX; }
	public boolean getVisibleAxeY() { return this.Model.VisibleAxeY; }
	public boolean getVisibleAxeZ() { return this.Model.VisibleAxeZ; }
	
	public void setVisibleAxeX(boolean value) { this.Model.VisibleAxeX = value; }
	public void setVisibleAxeY(boolean value) { this.Model.VisibleAxeY = value; }
	public void setVisibleAxeZ(boolean value) { this.Model.VisibleAxeZ = value; }
	
	public void newFile() {
		
		if(this.getNameFile() != null && this.getNameAxes() != null) {
			getWorld().getChildren().remove(0); 
			getWorld().getChildren().remove(0);
		}
		
		ImportFile importFile = new ImportFile();
		importFile.choiceFile();
		this.setProgrammeName(importFile.getFileName());
		this.file = importFile;
		
		VizualisationObject vizu3DObject = new VizualisationObject(importFile.getTriangleMesh());
		MeshView meshTarget = vizu3DObject.buildObject(Color.GREY, Color.WHITE, 0.5);
		getWorld().getChildren().add(vizu3DObject.getMeshView());
		this.setNameFile(meshTarget.toString());
		
		// La target qui va être séléctionné.
		SelectCoordonnes select = new SelectCoordonnes(importFile.getTriangleMesh(), meshTarget);
		getWorld().getChildren().add(select.getGroupSelector());
		
		vizu3DAxes = new VizualisationAxes();
		vizu3DAxes.buildAxes();
		
		Model.groupAxes = vizu3DAxes.getAxisGroup();
		getWorld().getChildren().add(getGroupAxes());
		this.setNameAxes(getGroupAxes().toString());
	}
	
	public VizualisationAxes getVizu3DAxes(){
		return this.vizu3DAxes;
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
	
	public void newProject() {
		
		PopupWindow = new Stage();
		VBox Vbox = new VBox();
		borderPane = new BorderPane();
		
		PopupWindow.setTitle(this.getTitle());
		
		Label labelNewProject = new Label("Choisir la correction d'outil : ");
		Button open = new Button("importer un fichier");
		open.setOnAction(actionEvent -> this.newFile());
		
		Vbox.getChildren().add(labelNewProject);
	    Vbox.getChildren().add(new Separator());
	    Vbox.getChildren().add(open);
		
	    Vbox.getChildren().add(new Separator());
	    
	    Vbox.getChildren().add(new Separator());
		
		borderPane.setCenter(Vbox);
		
		borderPane.setBottom(buttonConfirm());
		
		Scene scene = new Scene(borderPane, this.width, this.height);
		PopupWindow.setScene(scene);
		PopupWindow.show();
		
	}
	
	// Les boutons qui permettent de valider ou d'annuler
	public Node buttonConfirm() {
		HBox Hbox = new HBox();
		
		Button create = new Button("créer");
		create.setOnAction(actionEvent -> PopupWindow.hide());
		Hbox.getChildren().add(create);
		
		Button reset = new Button("annuler");
		reset.setOnAction(actionEvent -> PopupWindow.hide());
		Hbox.getChildren().add(reset);
		
		return Hbox;
	}
	
}
