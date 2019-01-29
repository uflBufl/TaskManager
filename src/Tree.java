import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класс Дерева.
 *
 * @version 0.9
 * @author Евгений Барабанов
 */
public class Tree implements Serializable {
    /** Поле id дерева */
    private int treeId = 0;

    /** Поле названия дерева */
    private String treeName = "";

    /** Поле MaxID для узлов */
    private int id = 1;

    /** Поле Главного узла(Головы дерева) */
    private Node head;

    /** Константа Главного узла */
    final static private Node START_TREE = new Node();

    /** Константа id Главного узла */
    final static private int START_TREE_ID = 0;

    /**
     * Конструктор - создание нового объекта
     * @see Tree#Tree()
     */
    Tree() {
        this(START_TREE);
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param name - название дерева
     * @see Tree#Tree(String)
     */
    Tree(String name) {
        this(new Node(START_TREE_ID, name));
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param head - Главный узел дерева
     * @see Tree#Tree(Node)
     */
    Tree(Node head) {
        this.head = head;
    }

    //Получить голову
    /**
     * Функция получения значения поля {@link Tree#head}
     * @return возвращает Главный узел
     */
    public Node getHead() {
        return head;
    }

    //Получить noce по id среди детей
    /**
     * Функция поиска и получения Узла среди детей заданного узла
     * @param id - id искомого узла
     * @param node - id дерева
     */
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

    /**
     * Функция получения значения поля {@link Tree#treeId}
     * @return id дерева
     */
    public int getTreeId() {
        return treeId;
    }

    /**
     * Функция получения значения поля {@link Tree#treeName}
     * @return возвращает имя дерева
     */
    public String getTreeName() {
        return treeName;
    }

    /**
     * Процедура определения id дерева {@link Tree#treeId}
     * @param treeId - id дерева
     */
    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    /**
     * Процедура определения имя дерева {@link Tree#treeName}
     * @param treeName - имя дерева
     */
    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    /**
     * Процедура определения maxId узлов дерева {@link Tree#id}
     * @param maxId - maxId узлов дерева
     */
    public void setMaxId(int maxId) {
        this.id = maxId;
    }

    /**
     * Функция получения значения поля {@link Tree#id}
     * @return возвращает MaxID для узлов дерева
     */
    public int getMaxID() {
        return id;
    }

    /**
     * Функция получения узла по его id
     * @param id - id узла
     * @return узел
     */
    public Node getNodeById(int id) {
        if (head.id == id) {
            return head;
        }
        Node subnode = getNodeByIdInChildren(id, head);
        return subnode;
    }

    /**
     * Процедура изменения id у заданного узла
     * @param node - узел
     */
    private void changeId(Node node) {
        node.id = this.id;
        this.id++;
        if (node.children.size() != 0) {
            for (Node child : node.children) {
                changeId(child);
            }
        }
    }

    /**
     * Процедура добавления узла к узлу с заданным id
     * @param id - id узла, к которому необходимо добавить узел
     * @param node - добавляемый узел
     */
    public void addNodeById(int id, Node node) {
        Node parent = getNodeById(id);
        if (parent != null) {
            node.parent = parent;
            changeId(node);
            parent.children.add(node);
        }
    }

    /**
     * Процедура удаления узла с заданным id
     * @param id - id узла
     */
    public void deleteNodeById(int id) {
        Node deletedNode = getNodeById(id);
        if (deletedNode != null) {
            Node parent = deletedNode.parent;
            parent.children.remove(deletedNode);
        }
    }

    /**
     * Процедура расщепления узла с заданным id
     * @param id - id узла
     */
    public void splitTree(int id) {
        Node deletedNode = getNodeById(id);
        if (deletedNode != null) {
            Node parent = deletedNode.parent;
            parent.children.remove(deletedNode);
            for (Node child : deletedNode.children) {
                parent.children.add(child);
                child.parent = parent;
            }
        }
    }

    /**
     * Функция получения клона дерева
     *
     * @return возвращает клонированное дерево
     */
    public Tree cloneTree() {
        Tree tree = new Tree(getHead().clone());
        tree.treeName = this.treeName;
        return tree;
    }

    /**
     * Процедура удаления дерева
     */
    public void deleteTree() {
        head = null;
    }

    /**
     * Процедура записи в StringBuffer узла в формате JSON
     * @param node - записываемый узел
     * @param buffer - StringBuffer
     */
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

    /**
     * Процедура записи в StringBuffer дерева в формате JSON
     */
    public void showTree() {
        System.out.println("TreeId = " + treeId + " TreeName = " + treeName);
        StringBuffer buffer = new StringBuffer();
        showNode(head, buffer);
    }

    /**
     * Процедура создания нового узла
     * @param id - id родительского узла
     */
    public void newNode(int id) {
        newNode(id, "");
    }

    /**
     * Процедура создания нового узла
     * @param id - id родительского узла
     * @param name - имя нового узла
     */
    public void newNode(int id, String name) {
        Node parent = getNodeById(id);
        if (parent != null) {
            Node newNode = new Node(this.id, name, parent);
            parent.children.add(newNode);
            this.id++;
        }
    }

    /**
     * Процедура записи дерева в StringBuffer в форме JSON
     * @param bf - StringBuffer
     */
    public void parseToJSON(StringBuffer bf) {
        bf.append("{ \"TreeID\": \"");
        bf.append(treeId);
        bf.append("\", \"TreeName\": \"");
        bf.append(treeName);
        bf.append("\", \"TreeMaxID\": \"");
        bf.append(id);
        bf.append("\"");
        if (!(head == null)) {
            bf.append(", \"Tree\": ");
            getHead().parseToJSON(bf);
        }
        bf.append(" },");
    }

    /**
     * Процедура вывода значений о лсите деревьев
     */
    @Override
    public String toString() {
        return "ID: " + getTreeId() + " Name: " + treeName;
    }

}
