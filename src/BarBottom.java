import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BarBottom extends Information{

	Label labelCoordonates = new Label();
   
	public Label getLabelCoordonates() {
		return this.labelCoordonates;
	}
	
	public BorderPane init(Stage primaryStage) {
		
		BorderPane BorderPaneBottom = new BorderPane();
		
		labelCoordonates.setText("X : " + this.getDisplayX() + ", Y : "+this.getDisplayY()+", Z : "+this.getDisplayZ());
        
        BorderPaneBottom.setRight(labelCoordonates);
        
        BorderPaneBottom.prefWidthProperty().bind(primaryStage.widthProperty());
        
        return BorderPaneBottom;
	}
}
