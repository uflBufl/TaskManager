import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionListener;
import java.io.*;

public class MainForm extends JFrame  {
    public Trees trees = new Trees();
    Tree currentTree;
    public Client client;
    private int whatIsSelected = -1;
    private int treeId = -1;

    private javax.swing.JPanel JPanel;
    private JButton createTreeButton;
    private JButton deleteTreeButton;
    private JButton newNodeButton;
    private JButton deleteNodeButton;
    private JTree tree1;
    private JButton splitTreeButton;
    private JTextField nodeName;
    private JTextField nodeId;
    private JButton cloneTreeButton;
    private JButton saveDataButton;
    private JButton loadDataButton;

    public void createTree(String nodeName, String treeName){
        this.client.createTree(nodeName, treeName);

        if(treeName.replaceAll(" ","").equals("")){
            trees.createTree(nodeName);
        }
        else {
            trees.createTree(nodeName, treeName);
        }

        setDefaultTree();
    }

    public void deleteTree(){
        if(trees.getTreeById(treeId)!=null) {

            client.deleteTreeById(treeId);


                trees.deleteTreeById(treeId);


        }
        else System.out.println("Дерево не выбрано");

        setDefaultTree();
    }

    public void newNode(String nodeName){
        if(trees.getTreeById(treeId)!=null && nodeId != null && trees.getTreeById(treeId).getNodeById(Integer.parseInt(nodeId.getText()))!=null){

        this.client.newNode(treeId, Integer.parseInt(nodeId.getText()),nodeName);

        if (nodeName.replaceAll(" ", "").equals("")) {
            trees.getTreeById(treeId).newNode(Integer.parseInt(nodeId.getText()));
        } else {
            trees.getTreeById(treeId).newNode(Integer.parseInt(nodeId.getText()), nodeName);
        }

        }
        else{
            System.out.println("Дерево не выбрано");
        }

        setDefaultTree();
    }

    public void deleteNode(){
//        this.client.deleteNode(treeId,Integer.parseInt(nodeId.getText()));
//        trees = client.getData();

        if(trees.getTreeById(treeId)!=null && nodeId.getText()!=null && trees.getTreeById(treeId).getNodeById(Integer.parseInt(nodeId.getText()))!=null){
            this.client.deleteNode(treeId,Integer.parseInt(nodeId.getText()));

            trees.getTreeById(treeId).deleteNodeById(Integer.parseInt(nodeId.getText()));
        }
        else{
            System.out.println("Дерево не выбрано");
        }

        setDefaultTree();
    }

    public void splitTree(){
//        System.out.println(2);
//        this.client.splitTree(treeId,Integer.parseInt(nodeId.getText()));
//        System.out.println(3);

        if(trees.getTreeById(treeId)!=null && nodeId.getText()!=null && trees.getTreeById(treeId).getNodeById(Integer.parseInt(nodeId.getText()))!=null){
            this.client.splitTree(treeId,Integer.parseInt(nodeId.getText()));

            trees.getTreeById(treeId).splitTree(Integer.parseInt(nodeId.getText()));
        }
        else{
            System.out.println("Узла с таким id нет");
        }

//        System.out.println(4);
        setDefaultTree();
//        System.out.println(5);
    }

    public void cloneTree(){
//        this.client.cloneTree(treeId);

        if(trees.getTreeById(treeId) != null){
            this.client.cloneTree(treeId);

            trees.cloneTree(treeId);
        }
        else{
            System.out.println("Дерево не выбрано");
        }

        setDefaultTree();
    }

    public void saveData(){
        this.client.saveData();
    }

    public void loadData(){
        this.client.getData();
        setDefaultTree();
    }

    public void setChildNodes(Node node, DefaultMutableTreeNode next){
        if(node.children != null && !node.children.isEmpty()){
            for (Node child:node.children) {
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Id:"+child.id);
                newNode.setUserObject(child);
                next.add(newNode);
                setChildNodes(child, newNode);
            }
        }
        else{
            next.setAllowsChildren(false);
        }
    }

    /**
     * Установка структуры дерева по умолчанию
     */
    public void setDefaultTree(){
        // Корневой узел
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Деревья");

        for (Tree tree:trees.getTrees()) {


            DefaultMutableTreeNode node = new DefaultMutableTreeNode("Id:" + tree.getTreeId());
            node.setUserObject(tree);
            root.add(node);
                DefaultMutableTreeNode head = new DefaultMutableTreeNode("Id:" + tree.getHead().id);
                head.setUserObject(tree.getHead());
                node.add(head);

                setChildNodes(tree.getHead(), head);
        }
        DefaultTreeModel model = new DefaultTreeModel(root, true);
        tree1.setModel(model);
    }




    public void start(){

        this.client = new Client(this);

        client.getData();

        setDefaultTree();

DefaultMutableTreeNode d = new DefaultMutableTreeNode();

        TreeSelectionListener tse = event ->{
          if(event.getPath().getPath().length == 2) {
              whatIsSelected = 1;
              Tree selectedNode = ((Tree) ((DefaultMutableTreeNode) event.getPath().getLastPathComponent()).getUserObject());
              treeId =  selectedNode.getTreeId();
              nodeName.setText(selectedNode.getTreeName());
              nodeId.setText(Integer.toString(selectedNode.getTreeId()));
          }
          else if(event.getPath().getPath().length > 2){
              whatIsSelected = 2;
              Node selectedNode = ((Node) ((DefaultMutableTreeNode) event.getPath().getLastPathComponent()).getUserObject());
              treeId = ((Tree)((DefaultMutableTreeNode)event.getPath().getPathComponent(1)).getUserObject()).getTreeId();
              nodeName.setText(selectedNode.name);
              nodeId.setText(Integer.toString(selectedNode.id));
          }
          else{
              whatIsSelected = -1;
              treeId = -1;
              nodeName.setText("");
              nodeId.setText("");
          }
        };

tree1.addTreeSelectionListener(tse);


createTreeButton.addActionListener(e -> {
    CreateTreeForm ctf = new CreateTreeForm(trees, this);
    ctf.start();
});

        deleteTreeButton.addActionListener(e -> {
            deleteTree();
        });

        newNodeButton.addActionListener(e -> {
            NewNodeForm nnf = new NewNodeForm(trees, this);
            nnf.start();
        });

        deleteNodeButton.addActionListener(e -> {
            deleteNode();
        });

        splitTreeButton.addActionListener(e -> {
            System.out.println(1);
            splitTree();
        });

        cloneTreeButton.addActionListener(e -> {
            cloneTree();
        });

        saveDataButton.addActionListener(e -> {
            saveData();
        });

        loadDataButton.addActionListener(e -> {
            loadData();
        });

        ActionListener fileActionListener = event -> {
            Trees trees = new Trees();

            JFileChooser openedFile = new JFileChooser();
            openedFile.setCurrentDirectory(new File("C:\\Users\\gamer\\Desktop\\Учёба\\NetCracker\\лабораторные куратора\\1 лаба"));
            int result = openedFile.showDialog(MainForm.this, "Выберите файл c зданием");
            if (result == JFileChooser.APPROVE_OPTION) {
                try (InputStream input = new FileInputStream(openedFile.getSelectedFile())) {
                    currentTree = null;
Serialization s = new Serialization();
                    trees = s.deserialaizeTrees(input);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(MainForm.this, exception);
                }
            }
        };

        setTitle("NetCracker");
        setContentPane(JPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1300, 700);
        setVisible(true);
    }
}
