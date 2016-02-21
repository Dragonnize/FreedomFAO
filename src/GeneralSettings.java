import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GeneralSettings {
	
	DoubleProperty ws = new SimpleDoubleProperty(); // longueur de la scéne.
	DoubleProperty hs = new SimpleDoubleProperty(); // hauteur de la scéne.
	
	GeneralSettings() {
		ws.set(1100); 
		hs.set(600); 
	}
	
	public DoubleProperty ws() { return this.ws; }
	public DoubleProperty hs() { return this.hs; }
 	
	public double getWidthScene() { return this.ws.get(); } // Permet de récupérer la longueur de la scéne.
	public double getHeightScene() { return this.hs.get(); } // Permet de récupérer la hauteur de la scéne.
	
	public void setWidthScene(double Ws) { this.ws.set(Ws); } // Permet d'entrer la longueur de la scéne.
	public void setHeightScene(double Hs) { this.hs.set(Hs); } // Permet d'entrer la hauteur de la scéne.
}
