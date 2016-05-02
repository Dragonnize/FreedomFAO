package Usinage;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Tool extends SettingsMachines{
	
	public int Id;
	public int nbDents;
	public double diametre;
	
	public Tool() {
		
	}
	
	public Tool(int id, int nbDents, double diametre) {
		this.Id = id;
		this.nbDents = nbDents;
		this.diametre = diametre;
	}
	
	public int getId() { return this.Id; }
	public int getTaille() { return this.nbDents; }
	public double getDiametre() { return this.diametre; }

	public VBox DisplayPopUp() {
		
		Label label = new Label("Paramétres de l'outil : ");
		this.popUpVBox.getChildren().add(label);
		
		Label label1 = new Label("diamétre de l'outil : ");
		this.popUpVBox.getChildren().add(label1);
		
		Label label2 = new Label("ARS, carbure ou autres : ");
		this.popUpVBox.getChildren().add(label2);
		
		Label label3 = new Label("type d'outil : ");
		this.popUpVBox.getChildren().add(label3);
		
		return this.popUpVBox;
	}
	
}