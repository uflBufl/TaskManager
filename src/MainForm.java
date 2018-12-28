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
    private int whatIsSelected = 0;
    private int treeId = 0;

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

    public void createTree(String nodeName, String treeName){
        this.client.createTree(nodeName, treeName);
        trees = client.getData();
        setDefaultTree();
    }

    public void deleteTree(){
//        System.out.println("Введите id дерева");
//        int id = in.nextInt();
        if(trees.getTreeById(treeId)!=null) {

            client.deleteTreeById(treeId);
            trees = client.getData();
        }
//        else System.out.println("Дерева с таким id нет");


//        this.client.createTree(nodeName, treeName);
//        trees = client.getData();
        setDefaultTree();
    }

    public void newNode(String nodeName){
        this.client.newNode(treeId, Integer.parseInt(nodeId.getText()),nodeName);
        trees = client.getData();
        setDefaultTree();
    }

    public void deleteNode(){
        this.client.deleteNode(treeId,Integer.parseInt(nodeId.getText()));
        trees = client.getData();
        setDefaultTree();
    }

    public void splitTree(){
        System.out.println(2);
        this.client.splitTree(treeId,Integer.parseInt(nodeId.getText()));
        System.out.println(3);
        trees = client.getData();
        System.out.println(4);
        setDefaultTree();
        System.out.println(5);
    }

    public void cloneTree(){
        this.client.cloneTree(treeId);
        trees = client.getData();
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

//        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Id:"+node.id);
//if(node.children != null){
//
//}
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
//            if (tree.getHead() != null) {
                DefaultMutableTreeNode head = new DefaultMutableTreeNode("Id:" + tree.getHead().id);
                head.setUserObject(tree.getHead());
                node.add(head);

                setChildNodes(tree.getHead(), head);
//            }
        }

//        for (int i = 0; i < trees.getTrees().size(); i++){
//            // Содержимое корневого узла
//            DefaultMutableTreeNode folder = new DefaultMutableTreeNode("Узел" + i);
//            root.add(folder);
//            for (int j = 1; j < 7; j++){
//                // Содержимое папок корневого узла
//                DefaultMutableTreeNode leaf = new DefaultMutableTreeNode("Лист" + j);
////                leaf.setAllowsChildren(false);
//                folder.add(leaf);
//            }
//        }
        DefaultTreeModel model = new DefaultTreeModel(root, true);
        tree1.setModel(model);
    }




    public void start(){

        this.client = new Client();

        trees = client.getData();

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
//              System.out.println(treeId);
              nodeName.setText(selectedNode.name);
              nodeId.setText(Integer.toString(selectedNode.id));
          }
          else{
              whatIsSelected = 0;
              treeId = 0;
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

        ActionListener fileActionListener = event -> {
            Trees trees = new Trees();
//            switch (event.getActionCommand()) {
//                case "Open tasks":
//                    trees = Buildings.TypeOfBuilding.DWELLING;
//                    Buildings.setBuildingFactory(new DwellingFactory());
//                    break;
//                default:
//                    trees = null;
//                    break;
//            }

            JFileChooser openedFile = new JFileChooser();
            openedFile.setCurrentDirectory(new File("C:\\Users\\gamer\\Desktop\\Учёба\\NetCracker\\лабораторные куратора\\1 лаба"));
            int result = openedFile.showDialog(MainForm.this, "Выберите файл c зданием");
            if (result == JFileChooser.APPROVE_OPTION) {
                try (InputStream input = new FileInputStream(openedFile.getSelectedFile())) {
                    currentTree = null;

                    trees = deserialaizeTrees(input);
//                    input.close();

//                    initFrames(Buildings.readBuilding(fileReader), type);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(MainForm.this, exception);
                }
            }
        };




//        JMenuBar menuBar = new JMenuBar();
//
//        JMenu fileMenu = new JMenu("File");
//        JMenu themeMenu = new JMenu("Look & Feel");
//
//        JMenuItem itm = new JMenuItem("Open tasks");
//        itm.addActionListener(fileActionListener);
//        fileMenu.add(itm;

//textArea1.setSize(100, 100);
//textArea1.setVisible(true);

        setTitle("NetCracker");
        setContentPane(JPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1300, 700);
        setVisible(true);
    }




//    private void initTasks(){
////        System.out.println(building);
//        if(trees == null) {
////            for (Tree tree:trees.getTrees()) {
////                tree.showTree();
////            }
//            textArea1.setText(
//                    "This is Tree:" + "\n");
//        }
//        else{
//            textArea1.setText(
//                    "This is no Tree:" +"\n");
//        }
//        setInfo(building, 1, 1);
//        Floor[] floors = building.getFloors();
//
//        for (int i = 0; i < floors.length; i++) {
//            JPanel floorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//            floorPanel.setBorder(BorderFactory.createTitledBorder("Floor " + i));
//            Space[] spaces = floors[i].getSpaces();
//            for (int j = 0; j < spaces.length; j++) {
//                JButton button = new JButton(j + "");
//                int finalI = i+1;
//                int finalJ = j+1;
//                button.addActionListener(e -> setInfo(building, finalI, finalJ));
//                floorPanel.add(button);
//            }
//            floorPanel.setPreferredSize(floorPanel.getPreferredSize());
//            buildingSchema.add(floorPanel);
//        }
//        SwingUtilities.updateComponentTreeUI(MainForm.this.getContentPane());
//    }









    public void serializeTrees (OutputStream out) throws IOException {
        (new ObjectOutputStream(out)).writeObject(trees);
    }

    public Trees deserialaizeTrees (InputStream in) throws IOException, ClassNotFoundException {
        return (Trees) (new ObjectInputStream(in)).readObject();
    }
}
