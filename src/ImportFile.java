import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;

import javafx.scene.shape.TriangleMesh;
import javafx.stage.FileChooser;

public class ImportFile {

	StlMeshImporter stlImporter = new StlMeshImporter();
	TriangleMesh meshSTL3D;
	JFileChooser dialogue;
	boolean importFile = false;
	TriangleMesh file;
	FileChooser fileChooser = new FileChooser();
	
	public void setFile3D(TriangleMesh file) { this.meshSTL3D = file; }
	public TriangleMesh getFile3D() { return this.meshSTL3D; }
	
	public void choiceFile() {
		
		// Boîte de sélection de fichier à partir du répertoire courant
        File repertoireCourant = null;
        try {
            // obtention d'un objet File qui désigne le répertoire courant. Le
            // "getCanonicalFile" n'est pas absolument nécessaire mais permet
            // d'éviter les /Truc/./Chose/ ...
            repertoireCourant = new File(".").getCanonicalFile();
        } catch(IOException e) {}
         
        // création de la boîte de dialogue dans ce répertoire courant
        // (ou dans "home" s'il y a eu une erreur d'entrée/sortie, auquel
        // cas repertoireCourant vaut null)
        dialogue = new JFileChooser(repertoireCourant);
         
        // affichage
        dialogue.showOpenDialog(null);
        
        if(dialogue.getSelectedFile() != null) {
		
			try {
				stlImporter.read(dialogue.getSelectedFile());
				
			}
			catch (ImportException e) {
				e.printStackTrace();
				return;
			}
			
			this.setFile3D(stlImporter.getImport());
			
			this.stlImporter.close();
        }
	}
}
