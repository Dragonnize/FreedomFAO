import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;

public abstract class Information {
	
	public String TITLE = "Freedom FAO";
	public static final double VERSION = 1.0;
	Color COLOR_SCENE = Color.WHITE;
	public final int DEBUG = 1; // Si 1 alors c'est en mode débugage sinon si c'est 0 c'est en mode application public.
	
	public final double MODEL_SCALE_FACTOR = 0.5;
	public final double MODEL_X_OFFSET = 0; // standard
	public final double MODEL_Y_OFFSET = 0; // standard
	public final double MODEL_MIN_Z = -800;
	public final double MODEL_MAX_Z = 40;
	public final double MODEL_MIN_X = 0;
	public final double MODEL_MAX_X = 0;
	public final double MODEL_MIN_Y = 0;
	public final double MODEL_MAX_Y = 0;
	
	double mousePosX, mouseOldX;
    double mousePosY, mouseOldY;
    double mousePosZ, mouseOldZ;
    
    double mouseDeltaX;
    double mouseDeltaY;
    
    double factor = 0.50;
    
    public String getTitle() { return this.TITLE; }
	
	public Class<? extends Information> getNameClass() { return this.getClass(); } // Retourne le nom de la classe courante.
	
	DoubleProperty currentX = new SimpleDoubleProperty(); // cordonnée actuel en X.
	DoubleProperty currentY = new SimpleDoubleProperty(); // cordonnée actuel en Y.
	DoubleProperty currentZ = new SimpleDoubleProperty(); // cordonnée actuel en Z.
	
	public double getVERSION() { return Information.VERSION; }
	
	// Ancienne cordonnées de la souris en X, Y et Z.
	public void setMouseOldX(double X) { this.mouseOldX = X; }
	public void setMouseOldY(double Y) { this.mouseOldY = Y; }
	public void setMouseOldZ(double Z) { this.mouseOldZ = Z; }
	
	public final double getMouseOldX() { return this.mouseOldX; }
	public final double getMouseOldY() { return this.mouseOldY; }
	public final double getMouseOldZ() { return this.mouseOldZ; }
	
	// Nouvelles cordonnées de la souris en X, Y et Z.
	public void setMouseX(double X) { this.mousePosX = X; }
	public void setMouseY(double Y) { this.mousePosY = Y; }
	public void setMouseZ(double Z) { this.mousePosZ = Z; }
	
	public final double getMouseX() { return this.mousePosX; }
	public final double getMouseY() { return this.mousePosY; }
	public final double getMouseZ() { return this.mousePosZ; }
	
	
	public DoubleProperty currentX() { return this.currentX; }
	public DoubleProperty currentY() { return this.currentY; }
	public DoubleProperty currentZ() { return this.currentZ; }
	
	public void setGetX(double X) { this.currentX.set(X); }
	public void setGetY(double Y) { this.currentY.set(Y); }
	public void setGetZ(double Z) { this.currentZ.set(Z); }
	
	public final double getDisplayX() { return this.currentX.get(); }
	public final double getDisplayY() { return this.currentY.get(); }
	public final double getDisplayZ() { return this.currentZ.get(); }
	
	public void setDeltaX(double currentX, double oldX){ this.mouseDeltaX = (currentX - oldX); }
	public void setDeltaY(double currentY, double oldY){ this.mouseDeltaY = (currentY - oldY); }
	
	public final double getMouseDeltaX(){ return this.mouseDeltaX; }
	public final double getMouseDeltaY(){ return this.mouseDeltaY; }
	
	public void initInformations() {
		
		currentX.set(0);
		currentY.set(0);
		currentZ.set(0);
		
	}
	
	public double calculNormal(double x1, double x2, double x3) {
    	return ((x2 - x1) * (x3 - x1));
    }
}
