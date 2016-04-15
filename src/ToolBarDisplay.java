import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.shape.DrawMode;

public class ToolBarDisplay {
	
	ToolBar toolBar = new ToolBar();
	Button process = new Button("Procédure");
	
	ToolBar init() {
		
	    toolBar.getItems().add(new Button("Grille"));
	    CheckBox wireframe = new CheckBox("Wireframe");
	   /* meshView.drawModeProperty().bind(
	        Bindings.when(
	            wireframe.selectedProperty())
	              .then(DrawMode.LINE)
	              .otherwise(DrawMode.FILL)
	    );*/
	    toolBar.getItems().add(new Button("Plein"));
	    toolBar.getItems().add(new Separator());
	    toolBar.getItems().add(new Button("Origine"));
	    toolBar.getItems().add(process);
	    /*
	    final String[] greetings = new String[] { "Fraisage", "Electroérosion", "Tournage" };
	    final ChoiceBox<String> cb = new ChoiceBox<String>(
	        FXCollections.observableArrayList("a", "b", "c", "d", "e"));

	    cb.getSelectionModel().selectedIndexProperty()
	        .addListener(new ChangeListener<Number>() {
	          public void changed(ObservableValue ov, Number value, Number new_value) {
	            label.setText(greetings[new_value.intValue()]);
	          }
	        });

	    cb.setTooltip(new Tooltip("Select the language"));
	    cb.setValue("English");
	    HBox hb = new HBox();
	    hb.getChildren().addAll(cb, label);
	    hb.setSpacing(30);
	    hb.setAlignment(Pos.CENTER);
	    hb.setPadding(new Insets(10, 0, 0, 10));

	    ((Group) scene.getRoot()).getChildren().add(hb);
	    */
	    process.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				
			}
	    	
	    });
	    
	    toolBar.setOrientation(Orientation.HORIZONTAL);
	    
	    return toolBar;
	}
}
