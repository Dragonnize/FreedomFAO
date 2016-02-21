import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class SettingsMachines {
	
	protected String title;
	
	protected Stage PopupWindow = new Stage();
	protected VBox Vbox_general = new VBox();
	protected BorderPane borderPane = new BorderPane();
	protected int width = 300;
	protected int height = 300;
	
	public enum Langage {
		
		ISO("code iso"),
		FAGOR("code Fagor"),
		GCODE("imprimante 3d code");
		
		codeIso codeIso;
		public String langageMachine; // Sert à définir le langage du programme en fonction de la machine qui va recevoir le programme.
		
		public String getLangage() {
			return this.langageMachine;
		}
		
		private String name = "";
		
		Langage(String langage) {
			this.name = langage;
			switch(this.name) {
				case "ISO":
					codeIso codeIso = new codeIso();
					this.langageMachine = "ISO";
				break;
			}
		}
		
	}
	
	public double F;
	public double Vc;
	public int d; // le diamétre de l'outil.
	public int Z; // Le nombre de dents de l'outil.
	public int S; // La vitesse de rotation en tr/min
	
	// La vitesse de coupe en fonction du matériaux.
	// Type de matériau
	// Vcc ARS
	// Vcc Outil carbure
	// Vcc UGV
	String[][] Vcc = {
				{"Acier (résilient)", "Acier doux", "Fonte (moyenne)", "Bronze", "Laiton (recuit)", "Aluminium", "Titane"},
				{"16","36","22","35","52","200","30"},
				{"65","130","80","80","80","600","65"},
				{"2000"}
			};
	
	public boolean SB = true; //Le sens de rotation de la broche si c'est true c'est horaire et anti-trigo sinon c'est l'inverse.
	public boolean correctToolProfil = true; // Si c'est à true la correction du rayon de l'outil se ferat à droite du profil à usiner sinon elle se fera à gauche.
	public boolean lubrification = true; // Pour activer ou non la lubrification, si c'est est à true elle est activée.
	public boolean typeData = true;
	
	/********************************
	 * FONCTIONS CONCERNANT L'OUTIL *
	 ********************************/
	
	// vitesse de rotation
	public void setVitesseRotation(int vr) { this.S = vr; }
	public int getVitesseRotation() { return this.S; }
	
	// sens de rotation
	public void setSensRotationBroche(boolean SRB) { this.SB = SRB; }
	public boolean getSensRotationBroche() { return this.SB; }
	
	/************************************
	 * FONCTONS CONCERNANT LE PROGRAMME *
	 ************************************/
	
	// Les données seront soit métrique si le boolean est à true sinon en pouce.
	public void setTypeData(boolean data) { this.typeData = data; }
	public boolean isTypeData() { return this.typeData; }
	
	public Node ChoiceMetrique() {
		ToggleGroup group = new ToggleGroup();
		Label label = new Label("Choisir le système : ");
	 		
	    RadioButton button1 = new RadioButton("métrique");
	    button1.setToggleGroup(group);
	    button1.setSelected(true);
	    RadioButton button2 = new RadioButton("pouce");
	    button2.setToggleGroup(group);
	    
	    HBox HBox = new HBox();
	    HBox.getChildren().add(button1);
	    HBox.getChildren().add(button2);
		
		VBox Vbox = new VBox();
		
		Vbox.getChildren().add(label);
	    Vbox.getChildren().add(new Separator());
	    Vbox.getChildren().add(HBox);
	    
	    return Vbox;
	}
	
	// lubrification
	public void setLubrification(boolean lub) { this.lubrification = lub; }
	public boolean getLubrification() { return this.lubrification; }
	
	public Node ChoiceLubrification() {
		ToggleGroup group = new ToggleGroup();
		Label label = new Label("Choisir si la lubrification sera active : ");
	 		
	    RadioButton button1 = new RadioButton("oui");
	    button1.setToggleGroup(group);
	    button1.setSelected(true);
	    RadioButton button2 = new RadioButton("non");
	    button2.setToggleGroup(group);
	    
	    HBox HBox = new HBox();
	    HBox.getChildren().add(button1);
	    HBox.getChildren().add(button2);
		
		VBox Vbox = new VBox();
		
		Vbox.getChildren().add(label);
	    Vbox.getChildren().add(new Separator());
	    Vbox.getChildren().add(HBox);
	    
	    return Vbox;
	}
	
	// Fréquence d'avance en m/min.
	public void setFrequency(double f) { this.F = f; }
	public double getFrequency() { return this.F; }
	
	// Vitesse de rotation de la broche.
	public void setVitesseCoupe(double vc) { this.Vc = vc; }
	public double getVitesseCoupe() { return this.Vc; }
	
	// Correction d'outil.
	public void setCorrectionOutilProfil(boolean correcteur) { this.correctToolProfil = correcteur; }
	public boolean isCorrectionOutilProfil() { return this.correctToolProfil; }
	
	public VBox ChoiceCorrectTool() {
		
		ToggleGroup group = new ToggleGroup();
		Label labelCorrectTool = new Label("Choisir la correction d'outil : ");
			
		RadioButton button1 = new RadioButton("à droite");
		button1.setToggleGroup(group);
		button1.setSelected(true);
		RadioButton button2 = new RadioButton("à gauche");
		button2.setToggleGroup(group);
		    
		HBox HBoxChoiceCorrectTool = new HBox();
		HBoxChoiceCorrectTool.getChildren().add(button1);
		HBoxChoiceCorrectTool.getChildren().add(button2);
		
		VBox Vbox = new VBox();
		
		Vbox.getChildren().add(labelCorrectTool);
	    Vbox.getChildren().add(new Separator());
	    Vbox.getChildren().add(HBoxChoiceCorrectTool);
	    
	    return Vbox;
	}
	
	public double calculFrequenceRotation() {
		return ((1000 * this.Vc) / (3.14 * this.d));
	}
	
	/*********
	 * POPUP 
	 * @return *
	 *********/
	
	abstract public void DisplayPopUp();
	
	public void PopUp() {
		
		PopupWindow.setTitle(this.title);
		
		Vbox_general.getChildren().add(ChoiceCorrectTool());
	    Vbox_general.getChildren().add(new Separator());
	    Vbox_general.getChildren().add(ChoiceLubrification());
	    Vbox_general.getChildren().add(new Separator());
	    Vbox_general.getChildren().add(ChoiceMetrique());
		
		this.DisplayPopUp();
		
		borderPane.setCenter(Vbox_general);
		
		borderPane.setBottom(buttonConfirm());
		
		Scene scene = new Scene(borderPane, this.width, this.height);
		PopupWindow.setScene(scene);
		PopupWindow.show();
		
	}
	
	// Les boutons qui permettent de valider ou d'annuler
	public Node buttonConfirm() {
		HBox Hbox = new HBox();
		Hbox.getChildren().add(new Button("créer"));
		Hbox.getChildren().add(new Button("annuler"));
		return Hbox;
	}
}
