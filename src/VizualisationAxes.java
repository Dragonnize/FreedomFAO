import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class VizualisationAxes {

	Group axisGroup;
	
	public VizualisationAxes() {
		this.axisGroup = new Group();
	}
	
	public Group buildAxes() {
		
    	final PhongMaterial greenMaterial = new PhongMaterial(), 
        					blueMaterial = new PhongMaterial(), 
        					redMaterial = new PhongMaterial();
    	
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(240.0, 1, 1),
        		  yAxis = new Box(1, 240.0, 1),
        		  zAxis = new Box(1, 1, 240.0);
        
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
        
		axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		
		return axisGroup;
    }
	
	public Group getAxisGroup() { return this.axisGroup; }
	
}
