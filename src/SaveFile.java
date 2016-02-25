import javafx.stage.FileChooser;

public class SaveFile {

	FileChooser fileChooser;
	
	public void saveFile() {
		
		fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        //Fichier à stocker
        //File file = fileChooser.showOpenDialog(primaryStage);
	}
}
