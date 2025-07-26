package application.graphics;

import javafx.scene.control.TreeItem;

public class TreeNode extends TreeItem<TreeNodeContent>{
	
	String caption;
	
	public TreeNode(TreeNodeContent treeNodeContent) {
		super(treeNodeContent);
	}

	public String getCaption() {
		return caption;
	}

}
