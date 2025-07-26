package application.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

import application.DBClass.DBObject;
import application.DBClass.DBObjectCollection;
import application.DBClass.DBObjectType;
import application.main.TempUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class Navigator extends Tab{
	
	private List<Object> root;
	private TreeNodeContent rootContent;
	
	private Object selectTreeItem;
	
	private Node contentPanel;
	
	public Navigator(List<Object> root, TreeNodeContent rootContent){
		
		this.root = root;
		this.rootContent = rootContent;
		
		SplitPane splitPane = new SplitPane();
		
		TreeView<TreeNodeContent> tree = new TreeView<TreeNodeContent>(createTree());
		
		contentPanel = setContentPanel();
		
		splitPane.getItems().addAll(tree, contentPanel);
		
		splitPane.setDividerPosition(0, 0.3);
		
		setContent(splitPane);
		
		MultipleSelectionModel<TreeItem<TreeNodeContent>> selectionModel = tree.getSelectionModel();
		
		tree.setOnMouseClicked(event -> {
            
        	TreeItem<TreeNodeContent> elem = selectionModel.getSelectedItem();
        	
        	if(elem == null) return;
        	
        	String val = elem.getValue().getText();
        	 
        	if(val == null) return;
        	
        	if(selectTreeItem == val) return; 
        		
        	selectTreeItem = val;
        	
        	if(elem.getValue() == null) return; 
        	
        	selectTreeEvent(elem.getValue());
           
       });
		
	}
	
	void selectTreeEvent(TreeNodeContent e) {
		
		if(e.getValue() == null) return;
		
		DBObjectCollection colection = new DBObjectCollection(((DBObjectType)e.getValue()).getID());
		
		((TableView<DBObject>)contentPanel).getItems().clear();
		
		for (DBObject object : colection.select()) {
			
			((TableView<DBObject>)contentPanel).getItems().add(object);
			
		}
		
	}
	
	Node setContentPanel() {
		
		TableView<DBObject> table = new TableView<DBObject>();
    	
    	// столбец для вывода имени
        TableColumn<DBObject, String> nameColumn = new TableColumn<DBObject, String>("Заголовок объекта");
        // определяем фабрику для столбца с привязкой к свойству name
        
        ObjectTableObserveble observeble = new ObjectTableObserveble();
        
        nameColumn.setCellValueFactory(observeble);
        
        //nameColumn.setCellValueFactory(new PropertyValueFactory<DBObject, String>("caption"));
        // добавляем столбец
        table.getColumns().add(nameColumn);
         
        // столбец для вывода возраста
        TableColumn<DBObject, String> ageColumn = new TableColumn<DBObject, String>("Тип объекта");
        ageColumn.setCellValueFactory(observeble);
        table.getColumns().add(ageColumn);
        
        table.setRowFactory(
        	    new Callback<TableView<DBObject>, TableRow<DBObject>>() {
        	        public TableRow<DBObject> call(TableView<DBObject> tableView) {
        	            final TableRow<DBObject> row = new TableRow<>();
        	            final ContextMenu rowMenu = new ContextMenu();
        	            MenuItem editItem = new MenuItem("Edit");
        	            editItem.setOnAction(new EventHandler<ActionEvent>() {
        	                @Override
        	                public void handle(ActionEvent event) {
        	                	
        	                	//System.out.println(row.getItem().getAttributeByID(2).getValue());
        	                	
        	                }
        	            });
        	            MenuItem removeItem = new MenuItem("Delete");
        	            removeItem.setOnAction(new EventHandler<ActionEvent>() {
        	                @Override
        	                public void handle(ActionEvent event) {
        	                    //table.getItems().remove(row.getItem());
        	                }
        	            });
        	            rowMenu.getItems().addAll(editItem, removeItem);
        	            // only display context menu for non-empty rows:
        	            row.contextMenuProperty().bind(
        	              Bindings.when(row.emptyProperty())
        	              .then((ContextMenu) null)
        	              .otherwise(rowMenu));
        	            return row;
        	    }
        	});
		
		return table;
	}
	
	TreeNode createTree() {
    	
		TreeNode element = new TreeNode(rootContent);
		
		createBranch(element, root);
		
    	return element;
    	
    }
    
	void createBranch(TreeNode node, List<Object> elem){
    	
    	for (Object root : elem) {
			
    		DBObjectType type = (DBObjectType)root;
    		
    		TreeNodeContent content = new TreeNodeContent(type.getName(), type);
    		
    		TreeNode element = new TreeNode(content);
    		
    		if(((DBObjectType)root).getChildTypes().size() != 0)
	    		element.getChildren().add(null);
    		
    		element.expandedProperty().addListener(new ChangeListener<Boolean>() {

				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					
					if(arg2) {
						
						element.getChildren().clear();
						
						Collection<DBObjectType> coll = ((DBObjectType)root).getChildTypes();
						
						List<Object> list = new ArrayList<>();
						
						for (Object object : coll) {
							list.add(object);
						}
						
						createBranch(element, list);
						
					}
					
				}
    			
			});
    		
    		node.getChildren().add(element);
    		
    		ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Создать");
            editItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                	ActiveXComponent kompas = ActiveXComponent.connectToActiveInstance("KOMPAS.Application.7");
                	if(kompas == null) kompas = new ActiveXComponent("KOMPAS.Application.7");
                	kompas.setProperty("Visible", true);
                	Dispatch documents = kompas.getProperty("Documents").getDispatch();
                	Dispatch.call(documents, "Add", 4, true);
                }
            });
            MenuItem removeItem = new MenuItem("Открыть в новой вкладке");
            removeItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    //table.getItems().remove(row.getItem());
                }
            });
    		
            contextMenu.getItems().addAll(editItem, removeItem);
            
    		content.setContextMenu(contextMenu);
    		
		}
    	
    }

}
