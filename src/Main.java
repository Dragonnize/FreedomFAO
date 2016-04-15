import org.scenicview.ScenicView;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;

public class Main extends Application{
	
	public void start(Stage window) throws Exception {
		
		FreedomFAOModel model = new FreedomFAOModel();
		
		FreedomFAOControler controller = new FreedomFAOControler(model);
		
		Window Window = new Window(controller, window);
		
		model.addObserver(Window);
	}

	public static void main(String[] args) {
	    Application.launch(args);
	}

}