import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GeneralSettings {
	
	DoubleProperty ws = new SimpleDoubleProperty(); // longueur de la sc�ne.
	DoubleProperty hs = new SimpleDoubleProperty(); // hauteur de la sc�ne.
	
	GeneralSettings() {
		ws.set(1100); 
		hs.set(600); 
	}
	
	public DoubleProperty ws() { return this.ws; }
	public DoubleProperty hs() { return this.hs; }
 	
	public double getWidthScene() { return this.ws.get(); } // Permet de r�cup�rer la longueur de la sc�ne.
	public double getHeightScene() { return this.hs.get(); } // Permet de r�cup�rer la hauteur de la sc�ne.
	
	public void setWidthScene(double Ws) { this.ws.set(Ws); } // Permet d'entrer la longueur de la sc�ne.
	public void setHeightScene(double Hs) { this.hs.set(Hs); } // Permet d'entrer la hauteur de la sc�ne.
}
