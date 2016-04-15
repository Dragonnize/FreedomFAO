import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BarBottom extends Information{
	
	public DoubleProperty cordDisplayX = new SimpleDoubleProperty();
	public DoubleProperty cordDisplayY = new SimpleDoubleProperty();
	public DoubleProperty cordDisplayZ = new SimpleDoubleProperty();

	Label labelCoordonates = new Label();
	
	public void setBindCords(DoubleProperty X, DoubleProperty Y, DoubleProperty Z) {
		cordDisplayX.bind(X);
		cordDisplayY.bind(Y);
		cordDisplayZ.bind(Z);
	}
   
	public Label getLabelCoordonates() {
		return this.labelCoordonates;
	}
	
	public BorderPane init(Stage primaryStage) {
		
		BorderPane BorderPaneBottom = new BorderPane();
		
		labelCoordonates.setText("X : " + this.getDisplayX() + ", Y : "+this.getDisplayY()+", Z : "+this.getDisplayZ());
        
        BorderPaneBottom.setRight(labelCoordonates);
        
       // BorderPaneBottom.prefWidthProperty().valueProperty().bind(primaryStage.widthProperty());
        
        return BorderPaneBottom;
	}
}
