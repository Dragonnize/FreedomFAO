import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import observer.Observer;

public class Window implements Observer{
	
	MenuTop menuTop;
	Stage Window;
    
    VizualisationObject vizu3DObject;
    VizualisationAxes vizu3DAxes;
    ActionSubScene GlobalSubScene;

	// Instance de l'objet qui va créer la scène
	ActionScene actionScene = new ActionScene();
	BorderPane rootLayout = new BorderPane();
	
	private AbstractControler controler;
	
	public Window(AbstractControler controler, Stage window) {
		
		// Génére le titre du logiciel.
		window.setTitle("Freedom FAO");
		window.getIcons().add(new Image("file:freedom-icon.png"));
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
		
		BarBottom barBottom = new BarBottom();
		
	    this.rootLayout.setBottom(barBottom.init(window));
	    
	    this.createSubScene();
	    
	    actionScene.createScene(rootLayout);
        //rootLayout.prefHeightProperty().bind(subScene.heightProperty());
        //rootLayout.prefWidthProperty().bind(subScene.widthProperty());
	    
	    actionScene.render();

		window.setScene(actionScene.getScene());
		
		window.show();
	}
	
	public void createSubScene() {
		
		// Instance de l'object pour la création de la sous scéne.
		GlobalSubScene = new ActionSubScene(this.controler.getRoot(), 1000.00, 600.00, true, SceneAntialiasing.DISABLED);
		
		GlobalSubScene.buildCamera();
		
		this.controler.getRoot().getChildren().add(GlobalSubScene.getCamera());
        
        this.controler.getRoot().getChildren().addAll(GlobalSubScene.getSubScene());
        
        this.rootLayout.setCenter(GlobalSubScene.getSubScene());
	}
	
	public MenuBar init(AbstractControler controller, Stage window) {
    	
		//this.menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		
		MenuBar menuBar = new MenuBar();
		
        // Menu Fichier
		Menu File = new Menu("_Fichier");
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
        
        // Procédure de tournage
    	MenuItem Edit_shooting = new MenuItem("Tournage");
        Edit_Choice_Procedure.getItems().add(Edit_shooting);
        Edit_shooting.setOnAction(event -> {
			// TODO Auto-generated method stub
			Tournage tour = new Tournage();
			tour.PopUp();
		});
        
        // Procédure de Fraisage
    	MenuItem Edit_drilling = new MenuItem("Fraisage");
     	Edit_Choice_Procedure.getItems().add(Edit_drilling);
     	Edit_drilling.setOnAction(event -> {
			// TODO Auto-generated method stub
			Fraisage frais = new Fraisage();
			frais.PopUp();
		});
     	
     	// Procédure d'électroérosion
    	MenuItem Edit_electroerosion = new MenuItem("Electroérosion");
     	Edit_Choice_Procedure.getItems().add(Edit_electroerosion);
     	Edit_electroerosion.setOnAction(event -> {
			// TODO Auto-generated method stub
			Electroerosion elec = new Electroerosion();
			elec.PopUp();
		});
     	Edit.setMnemonicParsing(true);
		
		// Menu Affichage
    	Menu Display = new Menu("_Affichage"), Display_Axe = new Menu("Afficher les axes de référence");
    	CheckMenuItem Display_Axe_X = new CheckMenuItem("Axe X"), 
    				  Display_Axe_Y = new CheckMenuItem("Axe Y"), 
    				  Display_Axe_Z = new CheckMenuItem("Axe Z");
		Display.getItems().add(Display_Axe);
		Display_Axe.getItems().add(Display_Axe_X);
		Display_Axe.getItems().add(Display_Axe_Y);
		Display_Axe.getItems().add(Display_Axe_Z);
		Display.setMnemonicParsing(true);
		
		// Menu Autres
		Menu Others = new Menu("_Autres");
		MenuItem help = new MenuItem("aide"), version = new MenuItem("version");
		Others.getItems().add(help);
		help.setOnAction(event -> {
			// TODO Auto-generated method stub
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Aide");
			alert.setHeaderText("un peu d'aide");
			alert.setContentText("Une aide futur.");

			alert.showAndWait();
		});
		
		Others.getItems().add(version);
		version.setOnAction(arg0 -> {
			// TODO Auto-generated method stub
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
		GlobalSubScene.handleKeyboard(controler.getWorld(), Window, actionScene.getCamera(), vizu3DAxes.getAxisGroup());
		GlobalSubScene.handleMouse(actionScene.getScene(), controler.getWorld(), controler.getFile().getMeshView());
	}

}
