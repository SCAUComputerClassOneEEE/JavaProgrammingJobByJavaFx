package model;

import java.io.File;
import javafx.scene.control.TreeItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileTreeItem extends TreeItem<TreeNode> {

    private File file;
    private TreeNode treeNode;

    public FileTreeItem(File file, String name) {
        this.file = file;
        this.treeNode = new TreeNode(file, name);
        this.setValue(treeNode);
    }
}