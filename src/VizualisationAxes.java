import java.util.LinkedList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class VizualisationAxes {

	Group axisGroup;
	String[] AxesNames = new String[3];
	int indexBox = -1;
	int i = 0;
	Boolean[] SelectsAxesNames = new Boolean[4];
	
	public VizualisationAxes() {
		this.axisGroup = new Group();
	}
	
	public Group buildAxes() {
		
		this.createAxe(Color.DARKRED, Color.RED, new Box(240.0, 1, 1));
		this.createAxe(Color.DARKGREEN, Color.GREEN, new Box(1, 240.0, 1));
		this.createAxe(Color.DARKBLUE, Color.BLUE, new Box(1, 1, 240.0));
		
		//axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		
		return axisGroup;
    }
	
	private void saveAxeName(String box) { 
		if(this.getIndex() < 0) {
			AxesNames[i] = box;
		}else{
			AxesNames[indexBox] = box;
		}
		
		indexBox = -1;
	}
	
	public void setIndex(int id) { this.indexBox = id; }
	public int getIndex() { return this.indexBox; }
	
	public void setValidBox(int id, boolean condition) { 
		this.SelectsAxesNames[id] = condition;
	}
	
	public boolean getValidBox(int id) { return this.SelectsAxesNames[id]; }

	public void createAxe(Color diffuseColor, Color specularColor, Box box) {
		
		PhongMaterial phongMaterial = new PhongMaterial();
		phongMaterial.setDiffuseColor(diffuseColor);
        phongMaterial.setSpecularColor(specularColor);
        
        box.setMaterial(phongMaterial);
        
        saveAxeName(box.toString());
        
        SelectsAxesNames[i] = true;
        
        i++;

        axisGroup.getChildren().add(box);
	}
	
	public void removeAxe(int n) {
		axisGroup.getChildren().remove(n);
		this.SelectsAxesNames[n] = false;
	}
	
	public Group getAxisGroup() { return this.axisGroup; }
	
}
