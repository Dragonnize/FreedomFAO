package Usinage;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Contournage extends SettingsMachines {
		
	public Contournage() {
		this.title = "Contournage";
	}

	public VBox DisplayPopUp() {
		
		Label label = new Label("Paramétres du cycle de contournage : ");
		
		this.popUpVBox.getChildren().add(label);
		
		return this.popUpVBox;
	}
}