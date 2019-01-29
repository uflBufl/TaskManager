import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класс Узла.
 *
 * @version 0.9
 * @author Евгений Барабанов
 */
public class Node implements Serializable, Cloneable {
    /** Поле id узла */
    int id;

    /** Поле ссылки на узел родителя */
    public Node parent;

    /** Поле листа детей узла */
    public ArrayList<Node> children = new ArrayList<>();

    /** Поле имени узла */
    String name;

    /** Константа ссылки на узел родителя */
    final static private Node EMPTY_PARENT = null;

    /** Константа листа детей узла */
    final static private ArrayList<Node> EMPTY_CHILD = new ArrayList<>();

    /** Константа id узла */
    final static private int START_TREE_ID = 0;

    /** Константа имени узла */
    final static private String DEFAULT_STRING = "";

    /**
     * Конструктор - создание нового объекта
     * @see Node#Node()
     */
    Node() {
        this(START_TREE_ID);
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param id - id узла
     * @see Node#Node(int)
     */
    Node(int id) {
        this(id, DEFAULT_STRING);
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param id - id узла
     * @param name - имя узла
     * @see Node#Node(int, String)
     */
    Node(int id, String name) {
        this(id, name, EMPTY_PARENT);
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param id - id узла
     * @param name - имя узла
     * @param parent - ссылка на узел родителя
     * @see Node#Node(int, String, Node)
     */
    Node(int id, String name, Node parent) {
        this(id, name, parent, EMPTY_CHILD);
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param id - id узла
     * @param name - имя узла
     * @param parent - ссылка на узел родителя
     * @param children - лист детей узла
     * @see Node#Node(int, String, Node, ArrayList)
     */
    Node(int id, String name, Node parent, ArrayList<Node> children) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.children.addAll(children);
    }

    /**
     * Функция получения клона узла
     * @return клон узла
     */
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

    /**
     * Процедура записи в StringBuffer узла в формате JSON
     * @param bf - StringBuffer
     */
    public void parseToJSON(StringBuffer bf) {
        bf.append("{ \"NodeID\": \"");
        bf.append(id);
        bf.append("\", \"NodeName\": \"");
        bf.append(name);
        bf.append("\"");
        if (!children.isEmpty()) {
            bf.append(", \"children\": ");
            for (Node child : children) {
                child.parseToJSON(bf);
            }
        }
        bf.append(" },");

    }

    /**
     * Процедура вывода значений об узле
     */
    @Override
    public String toString() {
        return "id: " + id + " Name: " + name;
    }
}