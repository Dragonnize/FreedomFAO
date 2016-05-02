package Usinage;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Percage extends SettingsMachines{

	public Percage() {
		this.title = "Perçage";
	}

	@Override
	public VBox DisplayPopUp() {
		Label label = new Label("Paramétres du cycle de perçage : ");
		
		this.popUpVBox.getChildren().add(label);
		
		return this.popUpVBox;
	}
	
}
