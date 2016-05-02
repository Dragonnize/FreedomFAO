import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import observer.Observable;
import observer.Observer;

public abstract class AbstractModel implements Observable{
	
	SimpleStringProperty ProgrammeName = new SimpleStringProperty();
	
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	
	Group groupAxes;
	
	public boolean VisibleAxeX = true;
	public boolean VisibleAxeY = true;
	public boolean VisibleAxeZ = true;
	
	DoubleProperty cordX = new SimpleDoubleProperty();
	DoubleProperty cordY = new SimpleDoubleProperty();
	DoubleProperty cordZ = new SimpleDoubleProperty();

	public String nameFile;
	public String nameAxes;
	
	public int idFile;
	public int idAxes;
	
	Group root;
    Xform world;
    
    double factor = 0.50;
	double RotateX = 0, RotateY = 0, RotateZ = 0;

	public abstract void reset();
	
	public void addObserver(Observer obs) {
		this.listObserver.add(obs);
	}

	public void removeObserver() {
		listObserver = new ArrayList<Observer>();
	}

	public void notifyObserver(String str) {
		if(str.matches("^0[0-9]+"))
			str = str.substring(1, str.length());
		for(Observer obs : listObserver)
			obs.update(str);
	}
	
	public Group getRoot() { return this.root; }
	public Xform getWorld() { return this.world; }
	
}