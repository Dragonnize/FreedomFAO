package Usinage;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Pointage extends SettingsMachines {
		
	public Pointage() {
		this.title = "Pointage";
	}

	public VBox DisplayPopUp() {
		Label label = new Label("Paramétres du cycle de pointage : ");
		
		this.popUpVBox.getChildren().add(label);
		
		return this.popUpVBox;
	}
}
