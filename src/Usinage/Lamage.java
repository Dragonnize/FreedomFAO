package Usinage;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Lamage extends SettingsMachines{

	public Lamage() {
		this.title = "Lamage";
	}

	public VBox DisplayPopUp() {
		Label label = new Label("Paramétres du cycle de lamage : ");
		
		this.popUpVBox.getChildren().add(label);
		
		return this.popUpVBox;
	}
	
}
