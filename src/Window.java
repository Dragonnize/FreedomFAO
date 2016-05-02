import Usinage.Contournage;
import Usinage.Electroerosion;
import Usinage.Lamage;
import Usinage.Percage;
import Usinage.Pointage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import observer.Observer;

public class Window implements Observer{
	
	Stage Window;
	
	StackPane AjustVizu = new StackPane();
	
	DoubleProperty currentX; // cordonnée actuel en X.
	DoubleProperty currentY = new SimpleDoubleProperty(); // cordonnée actuel en Y.
	DoubleProperty currentZ = new SimpleDoubleProperty(); // cordonnée actuel en Z.
	
	public Stage getWindow() { return this.Window; }

    ActionSubScene GlobalSubScene;

	// Instance de l'objet qui va créer la scéne
	ActionScene actionScene = new ActionScene();
	BorderPane rootLayout = new BorderPane();
	
	private AbstractControler controler;
	
	public Window(AbstractControler controler, Stage window) {
		
		// Génére le titre du logiciel.
		window.setTitle(controler.getTitle()+" - "+controler.getProgrammeName());
		window.getIcons().add(new Image("file:freedom-icon.png"));
		window.setHeight(600);
		window.setWidth(1000);
		this.controler = controler;
		
		this.Window = window;
		this.createScene(window);
	}
	
	public void createScene(Stage window) {
		
		VBox top = new VBox();
		top.getChildren().add(init(controler, window));
		
		ToolBarDisplay toolBar = new ToolBarDisplay();
		top.getChildren().add(toolBar.init());
		
		this.rootLayout.setTop(top);
	    
	    this.createSubScene();
	    
	    actionScene.createScene(rootLayout);
        //rootLayout.prefHeightProperty().bind(subScene.heightProperty());
        //rootLayout.prefWidthProperty().bind(subScene.widthProperty());
	    
	    actionScene.render();
	    
	    GlobalSubScene.handleKeyboard(this.getWindow(), actionScene.getCamera(), controler.getGroupAxes());
		GlobalSubScene.handleMouse(actionScene.getScene());

		window.setScene(actionScene.getScene());
		
		window.show();
	}
	
	public void createSubScene() {
		
		// Instance de l'object pour la création de la sous scéne.
		GlobalSubScene = new ActionSubScene(this.controler.getRoot(), 1000.00, 600.00, true, SceneAntialiasing.DISABLED);
		
		GlobalSubScene.buildCamera();
		
		this.controler.getRoot().getChildren().add(GlobalSubScene.getCamera());
        
        this.rootLayout.setCenter(GlobalSubScene.getSubScene());
        
        BarBottom barBottom = new BarBottom();
	    this.rootLayout.setBottom(barBottom.init(Window));
	    
        this.rootLayout.setLeft(controler.tree);
        
        GlobalSubScene.getSubScene().heightProperty().bind(this.rootLayout.heightProperty());
        GlobalSubScene.getSubScene().widthProperty().bind(this.rootLayout.widthProperty());
	}
	
	public DataTree getDataTree() { return controler.getDataTree(); }	
	
	public MenuBar init(AbstractControler controller, Stage window) {
		
		MenuBar menuBar = new MenuBar();
		
        // Menu Fichier
		Menu File = new Menu("_Fichier");
		
		MenuItem open_project = new MenuItem("nouveau projet");
        File.getItems().add(open_project);
        open_project.setOnAction(event -> {
        	controler.newProject();
		});
		
		MenuItem open_file = new MenuItem("importer un fichier");
        File.getItems().add(open_file);
        open_file.setOnAction(event -> {
        	controler.newFile();
		});
        
        MenuItem save_file = new MenuItem("enregistrer sous");
        File.getItems().add(save_file);
        File.getItems().add(new SeparatorMenuItem());
        
        MenuItem leave = new MenuItem("Quitter");
        File.getItems().add(leave);
        leave.setOnAction(actionEvent -> Platform.exit());
        File.setMnemonicParsing(true);
        
        // Menu Edition
        Menu Edit = new Menu("_Edition");
        Menu Edit_Choice_Procedure = new Menu("Choix de l'opération");
        Edit.getItems().add(Edit_Choice_Procedure);
        
        // Procédure de contournage
    	MenuItem Edit_shooting = new MenuItem("Contournage");
        Edit_Choice_Procedure.getItems().add(Edit_shooting);
        Edit_shooting.setOnAction(event -> {
        	Contournage contournage = new Contournage();
        	contournage.PopUp();
			getDataTree().addProcedure(contournage.getTitle());
			getDataTree().getProcedure().add(contournage);
		});
        
        // Procédure de Pointage
    	MenuItem Edit_bore = new MenuItem("Pointage");
        Edit_Choice_Procedure.getItems().add(Edit_bore);
        Edit_bore.setOnAction(event -> {
    		Pointage pointage = new Pointage();
			pointage.PopUp();
			getDataTree().addProcedure(pointage.getTitle());
			getDataTree().getProcedure().add(pointage);
		});
        
        // Procédure de Perçage
    	MenuItem Edit_drilling = new MenuItem("Perçage");
     	Edit_Choice_Procedure.getItems().add(Edit_drilling);
     	Edit_drilling.setOnAction(event -> {
    		Percage percage = new Percage();
    		percage.PopUp();
			getDataTree().addProcedure(percage.getTitle());
			getDataTree().getProcedure().add(percage);
		});
     	
     	// Procédure de Lamage
    	MenuItem Edit_countersink = new MenuItem("Lamage");
     	Edit_Choice_Procedure.getItems().add(Edit_countersink);
     	Edit_countersink.setOnAction(event -> {
    		Lamage lamage = new Lamage();
     		lamage.PopUp();
			getDataTree().addProcedure(lamage.getTitle());
			getDataTree().getProcedure().add(lamage);
		});
     	
     	// Procédure d'électroérosion
    	MenuItem Edit_electroerosion = new MenuItem("Electroérosion");
     	Edit_Choice_Procedure.getItems().add(Edit_electroerosion);
     	Edit_electroerosion.setOnAction(event -> {
    		Electroerosion electroerosion = new Electroerosion();
			electroerosion.PopUp();
			getDataTree().addProcedure(electroerosion.getTitle());
			getDataTree().getProcedure().add(electroerosion);
		});
     	Edit.setMnemonicParsing(true);
		
		// Menu Affichage
    	Menu Display = new Menu("_Affichage"), Display_Axe = new Menu("Afficher les axes de référence");
    	CheckMenuItem Display_Axe_X = new CheckMenuItem("Axe X"), 
    				  Display_Axe_Y = new CheckMenuItem("Axe Y"), 
    				  Display_Axe_Z = new CheckMenuItem("Axe Z");
		Display.getItems().add(Display_Axe);
		
		Display_Axe.getItems().add(Display_Axe_X);
		Display_Axe_X.setOnAction(event -> {
			if(controler.getVisibleAxeX()) {
				Display_Axe_X.setSelected(true);
				controler.getVizu3DAxes().removeAxe(0);
				controler.setVisibleAxeX(true);
			}else{
				Display_Axe_X.setSelected(false);
				controler.setVisibleAxeX(false);
			}
		});
		
		Display_Axe.getItems().add(Display_Axe_Y);
		Display_Axe_Y.setOnAction(event -> {
			if(controler.getVisibleAxeY()) {
				Display_Axe_Y.setSelected(true);
				controler.getVizu3DAxes().removeAxe(1);
				controler.setVisibleAxeX(true);
			}else{
				Display_Axe_Y.setSelected(false);
				controler.setVisibleAxeX(false);
			}
		});
		
		Display_Axe.getItems().add(Display_Axe_Z);
		Display_Axe_Z.setOnAction(event -> {
			if(controler.getVisibleAxeZ()) {
				Display_Axe_Z.setSelected(true);
				controler.getVizu3DAxes().removeAxe(2);
				controler.setVisibleAxeZ(true);
			}else{
				Display_Axe_Z.setSelected(false);
				controler.setVisibleAxeX(false);
			}
		});
		
		Display.setMnemonicParsing(true);
		
		// Menu Autres
		Menu Others = new Menu("_Autres");
		MenuItem help = new MenuItem("aide"), version = new MenuItem("version");
		Others.getItems().add(help);
		help.setOnAction(event -> {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Aide");
			alert.setHeaderText("un peu d'aide");
			alert.setContentText("Une aide futur.");

			alert.showAndWait();
		});
		
		Others.getItems().add(version);
		version.setOnAction(event -> {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Version");
			alert.setHeaderText("Quelques infos");
			alert.setContentText("version actuelle : 1.0");

			alert.showAndWait();
		});
		
		Others.setMnemonicParsing(true);
		
		menuBar.getMenus().add(File);
		menuBar.getMenus().add(Edit);
		menuBar.getMenus().add(Display);
		menuBar.getMenus().add(Others);
	
		return menuBar;
	}

	public void update(String str) {
		//GlobalSubScene.handleKeyboard(controler.getWorld(), Window, actionScene.getCamera(), vizu3DAxes.getAxisGroup());
		//GlobalSubScene.handleMouse(actionScene.getScene(), controler.getWorld(), controler.getFile().getMeshView());
	}

}
