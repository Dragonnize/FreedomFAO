import java.awt.Component;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.stl.StlImportOption;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.print.DocFlavor.URL;
import javax.swing.JFileChooser;
	
public class FileImport extends Information{
	
	StlMeshImporter stlImporter = new StlMeshImporter();
	TriangleMesh meshSTL3D;
	JFileChooser dialogue;
	boolean importFile = false;
	TriangleMesh file;
	FileChooser fileChooser = new FileChooser();
	
	public void setFile3D(TriangleMesh file) { this.meshSTL3D = file; }
	public TriangleMesh getFile3D() { return this.meshSTL3D; }
	
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

	public void saveFile() {
		
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        //Fichier � stocker
        //File file = fileChooser.showOpenDialog(primaryStage);
	}
}
