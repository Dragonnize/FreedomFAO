import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.FileChooser;

public class ImportFile {

	StlMeshImporter stlImporter = new StlMeshImporter();
	
	TriangleMesh triangleMesh;
	JFileChooser dialogue;
	FileChooser fileChooser = new FileChooser();
	MeshView meshView;
	
	// Les format d'entr�es des donn�es.
	String inputTab[];
	
	public TriangleMesh getTriangleMesh() { return this.triangleMesh; }
	public MeshView getMeshView() { return this.meshView; }
	
	public String getFileName() { return dialogue.getName(); }
	
	public void choiceFile() {
		
		// Bo�te de s�lection de fichier � partir du r�pertoire courant
        File repertoireCourant = null;
        try {
            // obtention d'un objet File qui d�signe le r�pertoire courant. Le
            // "getCanonicalFile" n'est pas absolument n�cessaire mais permet
            // d'�viter les /Truc/./Chose/ ...
            repertoireCourant = new File(".").getCanonicalFile();
        } catch(IOException e) {}
         
        // cr�ation de la bo�te de dialogue dans ce r�pertoire courant
        // (ou dans "home" s'il y a eu une erreur d'entr�e/sortie, auquel
        // cas repertoireCourant vaut null)
        dialogue = new JFileChooser(repertoireCourant);
         
        // affichage
        dialogue.showOpenDialog(null);
        
        dialogue.getName();
        
        if(dialogue.getSelectedFile() != null) {
		
			try {
				stlImporter.read(dialogue.getSelectedFile());
				
			}
			catch (ImportException e) {
				e.printStackTrace();
				return;
			}
			
			this.triangleMesh = stlImporter.getImport();
			
			this.stlImporter.close();
        }
        
        meshView = new MeshView(this.getTriangleMesh());
	}
}
