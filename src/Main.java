import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	ObjectProperty ImportSTL2 = new SimpleObjectProperty(new ImportFile(),"FileImport");
	MenuTop menuTop = new MenuTop();
	ImportFile ImportSTL = new ImportFile();
	BarBottom barBottom = new BarBottom();
	ToolBarDisplay toolBar = new ToolBarDisplay();
	public Stage Window;
	public final String TITLE = "Freedom FAO";
		
	double factor = 0.50;
	double RotateX = 0, RotateY = 0, RotateZ = 0;
		
	BorderPane rootLayout = new BorderPane();
		
	public static void main(String[] args) {
	    Application.launch(args);
	}
	    
	public void start(Stage window) throws Exception {
	    
		window.setTitle(TITLE);
		
		VBox top = new VBox();
		
		top.getChildren().add(menuTop.init(window));
		
		top.getChildren().add(toolBar.init());
		
		rootLayout.setTop(top);
	        
	    rootLayout.setBottom(barBottom.init(window));
	        
	    ImportSTL.choiceFile();
	        
	    ActionScene actionScene = new ActionScene(window, rootLayout, ImportSTL.getFile3D());
	        
	}
}
