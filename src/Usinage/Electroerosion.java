package Usinage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Electroerosion extends SettingsMachines{
	
	public Electroerosion() {
		this.title = "Electroerosion";
	}

	@Override
	public VBox DisplayPopUp() {
		
		Label label = new Label("Paramétres du cycle d'Electroérosion : ");
		
		this.popUpVBox.getChildren().add(label);
		
		return this.popUpVBox;
	}
	
}
