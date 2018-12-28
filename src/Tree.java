import java.io.Serializable;
import java.util.ArrayList;


//jackson для сохранения
//интерфейс, сохранение и загрузка, создание нового нода.
class Node implements Serializable, Cloneable {
    int id;
    Node parent;
    ArrayList<Node> children = new ArrayList<>();
    String name;

    final static private Node EMPTY_PARENT = null;
    final static private ArrayList<Node> EMPTY_CHILD = new ArrayList<>();
    final static private int START_TREE_ID = 0;
    final static private String DEFAULT_STRING = "";

    Node() {
        this(START_TREE_ID);
    }

    Node(int id) {
        this(id, DEFAULT_STRING);
    }

    Node(int id, String name) {
        this(id, name, EMPTY_PARENT);
    }

    Node(int id, String name, Node parent) {
        this(id, name, parent, EMPTY_CHILD);
    }

    Node(int id, String name, Node parent, ArrayList<Node> children) {
        this.id = id;
        this.name = name;
        this.parent = parent;
//        for (Node child:children
//             ) {
//            Node childClone = child.clone();
//
//            childClone.parent = cloneNode;
//            this.children.add(childClone);
//        }
        this.children.addAll(children);

//if(this.children.size() != 0){
//
//}

//        for (int i = 0; i < children.size(); i++) {
//            this.children.add(children.get(i));
//        }
//        this.children = children;
    }

    //Клонирование Node, без изменения parent
    public Node clone() {
        Node cloneNode = new Node(this.id);
        cloneNode.name = this.name;
        for (Node child : this.children
        ) {
            Node childClone = child.clone();
            cloneNode.children.add(childClone);
            childClone.parent = cloneNode;
        }
        return cloneNode;
    }

    public void parseToJSON(StringBuffer bf){
//        StringBuffer bf = new StringBuffer();
bf.append("{ \"NodeID\": \"");
bf.append(id);
bf.append("\", \"NodeName\": \"");
bf.append(name);
bf.append("\"");
if(!children.isEmpty()){
    bf.append(", ");
    for (Node child:children) {
        child.parseToJSON(bf);
    }
}
bf.append(" }");




//        return bf.toString();
    }


}


public class Tree implements Serializable {
    private int treeId = 0;
    private String treeName = "";
    private int id = 1;
    private Node head;
    final static private Node START_TREE = new Node();
    final static private int START_TREE_ID = 0;

    Tree() {
        this(START_TREE);
    }

    Tree(String name) {
        this(new Node(START_TREE_ID, name));
    }

    Tree(Node head) {
        this.head = head;
    }

    //Получить голову
    public Node getHead() {
        return head;
    }

    //Получить noce по id среди детей
    private Node getNodeByIdInChildren(int id, Node node) {
        if (head.children != null) {
            for (int i = 0; i < node.children.size(); i++) {
                if (node.children.get(i).id == id) {
                    return node.children.get(i);
                }
                Node subnode = getNodeByIdInChildren(id, node.children.get(i));
                if (subnode != null) {
                    return subnode;
                }
            }
        }
        return null;
    }

    public int getTreeId() {
        return treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    //Получить Node по id
    public Node getNodeById(int id) {
        if (head.id == id) {
            return head;
        }
        Node subnode = getNodeByIdInChildren(id, head);
        return subnode;
    }

    private void changeId(Node node) {
        node.id = this.id;
        this.id++;
        if (node.children.size() != 0) {
            for (Node child : node.children) {
                changeId(child);
            }
        }
    }


    //Добавить node к узлу с таким то id
    //должен ли я заменять id у node при добавлении?
    //изменить id детей
    public void addNodeById(int id, Node node) {
        Node parent = getNodeById(id);
        if (parent != null) {
//            Node child = getNodeById(node.id);
            //if(getNodeById(node.id) == null){
            node.parent = parent;
            changeId(node);
//            node.id = this.id;
            parent.children.add(node);
//            this.id++;
            //}
        }
    }

    //удаление node по id
    public void deleteNodeById(int id) {
        Node deletedNode = getNodeById(id);
        if (deletedNode != null) {
            Node parent = deletedNode.parent;
//            int index = parent.children.indexOf(deletedNode);
            parent.children.remove(deletedNode);
        }
    }

    //Расщепление дерева, по node id
    public void splitTree(int id) {
        Node deletedNode = getNodeById(id);
        if (deletedNode != null) {
            Node parent = deletedNode.parent;
//            int index = parent.children.indexOf(deletedNode);
            parent.children.remove(deletedNode);
            for (Node child : deletedNode.children) {
                parent.children.add(child);
                child.parent = parent;
            }
        }
    }

    //Клонирование дерева
    public Tree cloneTree() {
//        Node headClone = getHead().clone();
//Tree treeClone = new Tree(getHead().clone());
        Tree tree = new Tree(getHead().clone());
        tree.treeName = this.treeName;
        return tree;
    }

    //удаление дерева
    public void deleteTree() {
        head = null;
    }

    private void showNode(Node node, StringBuffer buffer) {

        System.out.print(buffer);
        System.out.println("- id: " + node.id + ", name: " + node.name);
        if (node.children.size() != 0) {
            buffer.append("\t");
            for (Node child : node.children
            ) {
                showNode(child, buffer);
            }
            buffer.deleteCharAt(buffer.length() - 1);
        }
    }

    public void showTree() {
        System.out.println("TreeId = "+treeId+" TreeName = "+ treeName);
        StringBuffer buffer = new StringBuffer();
        showNode(head, buffer);

//        System.out.println(getNodeById(1).name);
    }

    public void newNode(int id) {
        newNode(id,"");
    }

    public void newNode(int id, String name) {
        Node parent = getNodeById(id);
        if (parent != null) {
            Node newNode = new Node(this.id, name, parent);
            parent.children.add(newNode);
            this.id++;
        }
    }


//    @Override
//    public String toString() {
//        return "Tree{" +
//                "treeId=" + treeId +
//                ", treeName='" + treeName + '\'' +
////                ", id=" + id +
////                ", head=" + head +
//                '}';
//    }
}
