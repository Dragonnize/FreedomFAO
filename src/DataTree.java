import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Usinage.Contournage;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class DataTree {
	
	// MENU PRINCIPAL PROGRAMME
	TreeItem<String> root = new TreeItem<String> ("Programme");
	// Menu principal Procédures
	TreeItem<String> rootOP = new TreeItem<String> ("Opérations");
	// Menu principal Magasin d'outils
    TreeItem<String> rootMO = new TreeItem<String> ("MO (Magasin d'outils)");
    
    ArrayList Tools = new ArrayList();
	ArrayList Procedures = new ArrayList();
	
	public DataTree() {
		root.setExpanded(true);
		
		rootOP.setExpanded(true);
		root.getChildren().add(rootOP);
		
		rootMO.setExpanded(true);
		root.getChildren().add(rootMO);
	}
	
	public void addTool(String name) {
		TreeItem<String> itemTool = new TreeItem<String> (name);
		rootMO.getChildren().add(itemTool);
	}
	
	public void addProcedure(String name) {
		TreeItem<String> itemOP = new TreeItem<String> (name);
		rootOP.getChildren().add(itemOP);
	}
	
	public ArrayList getTool() { return this.Tools; }
	public ArrayList getProcedure() { return this.Procedures; }
	
    List<Procedure> procedures = Arrays.<Procedure>asList(
            new Procedure("Isabella Johnson", "T11"));
    
    List<Procedure> tools = Arrays.<Procedure>asList(
            new Procedure("Isabella Johnson", "T11"));
    
    TreeItem<String> rootNode = new TreeItem<String>("Programme");
	 
    public TreeView<String> dataTree() {
    
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("Message" + i);            
            rootOP.getChildren().add(item);
        }
		
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("Message" + i);            
            rootMO.getChildren().add(item);
        }        
        TreeView<String> treeView = new TreeView<String> (root);
        treeView.setEditable(true);
        treeView.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new TextFieldTreeCellImpl();
            }
        });
        
        for (Procedure process : procedures) {
            TreeItem<String> empLeaf = new TreeItem<String>(process.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(process.getTool())){
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem depNode = new TreeItem(process.getTool());
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }
        
        for (Procedure tool : tools) {
            TreeItem<String> empLeaf = new TreeItem<String>(tool.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(tool.getTool())){
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem depNode = new TreeItem(tool.getTool());
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }
        
        return treeView;
    }
	 
    private final class TextFieldTreeCellImpl extends TreeCell<String> {
 
        private TextField textField;
        private ContextMenu addMenu = new ContextMenu();
 
        public TextFieldTreeCellImpl() {
            MenuItem addMenuItem = new MenuItem("Ajouter une procédure");
            addMenu.getItems().add(addMenuItem);
            addMenuItem.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    TreeItem newEmployee = 
                        new TreeItem<String>("Ajouter une procédure");
                            getTreeItem().getChildren().add(newEmployee);
                }
            });
        }
	 
        public void startEdit() {
            super.startEdit();
 
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
        
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }
	 
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if (
                        !getTreeItem().isLeaf()&&getTreeItem().getParent()!= null
                    ){
                        setContextMenu(addMenu);
                    }
                }
            }
        }
	        
        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
 
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });  
            
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
	 
    public static class Procedure {
 
        private final SimpleStringProperty name;
        private final SimpleStringProperty tool;
 
        private Procedure(String name, String tool) {
            this.name = new SimpleStringProperty(name);
            this.tool = new SimpleStringProperty(tool);
        }
 
        public String getName() {
            return name.get();
        }
 
        public void setName(String fName) {
            name.set(fName);
        }
 
        public String getTool() {
            return tool.get();
        }
 
        public void setTool(String toolName) {
            tool.set(toolName);
        }
    }
	
}
