package Usinage;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Percage extends SettingsMachines{

	public Percage() {
		this.title = "Per�age";
	}

	@Override
	public VBox DisplayPopUp() {
		Label label = new Label("Param�tres du cycle de per�age : ");
		
		this.popUpVBox.getChildren().add(label);
		
		return this.popUpVBox;
	}
	
}
