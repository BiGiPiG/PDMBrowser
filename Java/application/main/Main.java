package application.main;

import java.util.ArrayList;
import java.util.List;

import application.DBClass.interfaces.IDBAttribute;
import application.PDMBrowser.PDMBrowserConnector;
import application.graphics.Navigator;
import application.graphics.TreeNodeContent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args){
		
		new TempUtil();
		
		new PDMBrowserConnector();
				
		launch();
		
		IDBAttribute atr;
    	
   }
    
    public void start(Stage stage) {
         
    	TabPane tabPane = new TabPane();
    	
    	addTab(tabPane);
    	
    	Scene scene = new Scene(tabPane, 600, 250);
          
        stage.setScene(scene);
        stage.setTitle("MyPDMSystem");
        stage.show();
        
    }
    
    void addTab(TabPane tabPane) {
    	
    	List<Object> objs = new ArrayList<>();
    	
    	objs.add(TempUtil.getRootObjectType());
    	
    	Navigator tab = new Navigator(objs, new TreeNodeContent("Рабочее пространство", null));
    	tab.setText("Навигатор");
    	
    	tabPane.getTabs().addAll(tab);
    	
    }
    
}
