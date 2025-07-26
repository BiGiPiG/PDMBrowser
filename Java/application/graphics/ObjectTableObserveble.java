package application.graphics;

import application.DBClass.DBObject;
import application.DBClass.interfaces.IDBAttribute;
import application.DBClass.interfaces.IDBAttributeCollection;
import application.DBClass.interfaces.IDBObject;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class ObjectTableObserveble implements Callback<TableColumn.CellDataFeatures<DBObject,String>, ObservableValue<String>> {
	
	public ObservableValue<String> call(CellDataFeatures<DBObject, String> arg0) {
		
		return new ObservableValue<String>() {

			@Override
			public void addListener(InvalidationListener arg0) {}

			@Override
			public void removeListener(InvalidationListener arg0) {}

			@Override
			public void addListener(ChangeListener<? super String> arg0) {}

			@Override
			public String getValue() {
				
				IDBAttribute atr = arg0.getValue()
						.getAttributeColection()
						.get(arg0.getTableColumn().getText());
				
				if(atr == null) return "";
				
				Object val = atr.getValue();
				
				if(val == null) return "";
				
				return val.toString();
				
			}

			@Override
			public void removeListener(ChangeListener<? super String> arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
	}
	
}
