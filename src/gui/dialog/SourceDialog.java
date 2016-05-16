package gui.dialog;

import gui.CustomLAF;
import gui.rounded.RoundedJDialog;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class SourceDialog extends RoundedJDialog {
    
    private static final long serialVersionUID = -899785751736556826L;
    
    private final JTree fileTree;
    private final FileSystemModel fileSystemModel;
    private final JTextArea fileDetailsTextArea = new JTextArea();
    
    public SourceDialog(final JFrame parent) {
        super(parent, true);
        super.setTitle("Source code viewer");
        this.fileDetailsTextArea.setEditable(false);
        this.fileSystemModel = new FileSystemModel(new File("."));
        this.fileTree = new JTree(fileSystemModel);
        this.fileTree.setRootVisible(false);
        this.fileTree.setEditable(false);
        this.fileTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent event) {
                File file = (File) fileTree.getLastSelectedPathComponent();
                fileDetailsTextArea.setText(getFileDetails(file));
                fileDetailsTextArea.setCaretPosition(0);
            }
        });
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JScrollPane(fileTree), new JScrollPane(fileDetailsTextArea));
        splitPane.setResizeWeight(0.1);
        super.setLayout(new GridLayout(1, 1));
        super.add(splitPane);
        super.setSize(1200, 600);
    }
    
    private String getFileDetails(File file) {
        if (file == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        //buffer.append("Name: " + file.getName() + "\n");
        //buffer.append("Path: " + file.getPath() + "\n");
        //buffer.append("Size: " + file.length() + "\n");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                sb.append(sc.nextLine()).append(System.lineSeparator());
            }
            sc.close();
        } catch (IOException e) {
            sb.append(e.getClass().getSimpleName());
            sb.append(System.lineSeparator());
            sb.append(e.getMessage());
        }
        return sb.toString();
    }
}

class FileSystemModel implements TreeModel {
    
    private final File root;
    private final Set<TreeModelListener> listeners;
    
    public FileSystemModel(final File rootDirectory) {
        this.root = rootDirectory;
        this.listeners = new HashSet<TreeModelListener>();
    }
    
    public Object getRoot() {
        return this.root;
    }
    
    public Object getChild(final Object parent, final int index) {
        final File directory = (File) parent;
        final String[] children = directory.list();
        return new TreeFile(directory, children[index]);
    }
    
    public int getChildCount(final Object parent) {
        final File file = (File) parent;
        if (file.isDirectory()) {
            String[] fileList = file.list();
            if (fileList != null) {
                return file.list().length;
            }
        }
        return 0;
    }
    
    public boolean isLeaf(final Object node) {
        final File file = (File) node;
        return file.isFile();
    }
    
    public int getIndexOfChild(final Object parent, final Object child) {
        final File directory = (File) parent;
        final File file = (File) child;
        final String[] children = directory.list();
        for (int i = 0; i < children.length; i++) {
            if (file.getName().equals(children[i])) {
                return i;
            }
        }
        return -1;
        
    }
    
    public void valueForPathChanged(final TreePath path, final Object value) {
        final File oldFile = (File) path.getLastPathComponent();
        final String fileParentPath = oldFile.getParent();
        final String newFileName = (String) value;
        final File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        final File parent = new File(fileParentPath);
        final int[] changedChildrenIndices = {getIndexOfChild(parent, targetFile)};
        final Object[] changedChildren = {targetFile};
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);
    }
    
    private void fireTreeNodesChanged(final TreePath parentPath, final int[] indices, final Object[] children) {
        final TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        final Iterator<TreeModelListener> iterator = listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().treeNodesChanged(event);
        }
    }
    
    public void addTreeModelListener(final TreeModelListener listener) {
        listeners.add(listener);
    }
    
    public void removeTreeModelListener(final TreeModelListener listener) {
        listeners.remove(listener);
    }
    
    private class TreeFile extends File {
        
        private static final long serialVersionUID = -5253923227039151156L;
        
        public TreeFile(final File parent, final String child) {
            super(parent, child);
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
    }
}
