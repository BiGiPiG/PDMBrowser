package application.graphics;

import javafx.scene.control.Label;

public class TreeNodeContent extends Label{

	private Object value;
	
	public TreeNodeContent(String name, Object value) {
		super(name);
		
		this.value = value;
		
	}
	
	Object getValue() {
		return value;
	}
	
}
