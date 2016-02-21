import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;

public class MenuTop extends Information {
	
	Menu File = new Menu("_Fichier");
	MenuItem open_file = new MenuItem("importer un fichier STL"), save_file = new MenuItem("enregistrer sous"), leave = new MenuItem("Quitter");
	
	Menu Edit = new Menu("_Edition");
	Menu Edit_Choice_Procedure = new Menu("Choix de l'opération");
	// Procédure de tournage
	MenuItem Edit_shooting = new MenuItem("Tournage");
	// Procédure de Fraisage
	MenuItem Edit_drilling = new MenuItem("Fraisage");
	// Procédure d'électroérosion
	MenuItem Edit_electroerosion = new MenuItem("Electroérosion");

	Menu Display = new Menu("_Affichage"), Display_Axe = new Menu("Afficher les axes de référence");
	CheckMenuItem Display_Axe_X = new CheckMenuItem("Axe X"), Display_Axe_Y = new CheckMenuItem("Axe Y"), Display_Axe_Z = new CheckMenuItem("Axe Z");
	
	Menu Others = new Menu("_Autres");
	MenuItem help = new MenuItem("aide"), version = new MenuItem("version");
	
	MenuBar menuBar = new MenuBar();
	
	public MenuBar getMenuBar() { return this.menuBar; }
	
	public MenuBar init(Stage primaryStage) {
    	
		//this.menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		
        // Menu Fichier
        File.getItems().add(open_file);
        open_file.setOnAction(event -> {
			// TODO Auto-generated method stub
		});

        File.getItems().add(save_file);
        File.getItems().add(new SeparatorMenuItem());
        File.getItems().add(leave);
        leave.setOnAction(actionEvent -> Platform.exit());
        File.setMnemonicParsing(true);
        
        // Menu Edition
        Edit.getItems().add(Edit_Choice_Procedure);
        Edit_Choice_Procedure.getItems().add(Edit_shooting);
        Edit_shooting.setOnAction(event -> {
			// TODO Auto-generated method stub
			Tournage tour = new Tournage();
			tour.PopUp();
		});
     	Edit_Choice_Procedure.getItems().add(Edit_drilling);
     	Edit_drilling.setOnAction(event -> {
			// TODO Auto-generated method stub
			Fraisage frais = new Fraisage();
			frais.PopUp();
		});
     	Edit_Choice_Procedure.getItems().add(Edit_electroerosion);
     	Edit_electroerosion.setOnAction(event -> {
			// TODO Auto-generated method stub
			Electroerosion elec = new Electroerosion();
			elec.PopUp();
		});
     	Edit.setMnemonicParsing(true);
		
		// Menu Affichage
		Display.getItems().add(Display_Axe);
		Display_Axe.getItems().add(Display_Axe_X);
		Display_Axe.getItems().add(Display_Axe_Y);
		Display_Axe.getItems().add(Display_Axe_Z);
		Display.setMnemonicParsing(true);
		
		// Menu Autres
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
			alert.setContentText("version actuelle : "+getVERSION());

			alert.showAndWait();
		});
		
		Others.setMnemonicParsing(true);
		
		menuBar.getMenus().add(File);
		menuBar.getMenus().add(Edit);
		menuBar.getMenus().add(Display);
		menuBar.getMenus().add(Others);
	
		return menuBar;
	}
	
}
