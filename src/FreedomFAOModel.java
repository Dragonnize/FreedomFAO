import javafx.scene.Group;

public class FreedomFAOModel extends AbstractModel{
	
	public void reset() {
		this.root = new Group();
		this.world = new Xform();
		getRoot().getChildren().add(this.getWorld());
	}
}
