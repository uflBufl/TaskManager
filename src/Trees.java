import java.io.*;
import java.util.ArrayList;

/**
 * Класс Деревьев.
 *
 * @version 0.9
 * @author Евгений Барабанов
 */
public class Trees implements Serializable {
    /** Поле листа деревьев */
    public ArrayList<Tree> trees = new ArrayList<>();

    /** Поле id листа деревьев */
    private int id = 0;

    /** Константа пустого листа деревьев */
    final static private ArrayList<Tree> EMPTY_TREES = new ArrayList<>();

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @see Trees#Trees()
     */
    Trees() {
        this(EMPTY_TREES);
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param trees - лист деревьев
     * @see Trees#Trees(ArrayList)
     */
    Trees(ArrayList<Tree> trees) {
        this.trees.addAll(trees);
    }

    /**
     * Процедура определения значения поля {@link Trees#id}
     * @param maxId - id листа деревьев
     */
    public void setMaxId(int maxId) {
        this.id = maxId;
    }

    /**
     * Функция получения значения поля {@link Trees#id}
     * @return id листа деревьев
     */
    public int getMaxID() {
        return id;
    }

    /**
     * Процедура создания дерева
     * @param name - имя Главного узла дерева
     */
    public void createTree(String name) {
        createTree(name, null);
    }

    /**
     * Процедура создания дерева
     * @param name - имя Главного узла дерева
     * @param treeName - имя дерева
     */
    public void createTree(String name, String treeName) {
        Tree tree = new Tree(name);
        tree.setTreeId(id);
        this.id++;
        tree.setTreeName(treeName);
        trees.add(tree);
    }

    /**
     * Функция получения дерева по его id
     * @param id - id дерева
     * @return дерево
     */
    public Tree getTreeById(int id) {
        for (Tree tree : trees) {
            if (tree.getTreeId() == id) return tree;
        }
        return null;
    }

    /**
     * Функция удаления дерева по его id
     * @param id - id дерева
     */
    public void deleteTreeById(int id) {
        for (Tree tree : trees) {
            if (tree.getTreeId() == id) {
                trees.remove(getTreeById(id));
                break;
            }
        }
    }

    /**
     * Функция получения значения поля {@link Trees#trees}
     * @return лист деревьев
     */
    public ArrayList<Tree> getTrees() {
        return trees;
    }

    /**
     * Функция клонирования дерева по его id
     * @param id - id дерева
     */
    public void cloneTree(int id) {
        Tree clone = null;
        for (Tree tree : trees) {
            if (tree.getTreeId() == id) {
                clone = tree.cloneTree();
                clone.setTreeId(this.id);
                this.id++;
            }
        }
        if (clone != null) trees.add(clone);
    }

    /**
     * Функция перевода Деревьев в формат JSON
     * @param bf - StringBuffer в который будут записаны деревья
     */
    public void parseToJSON(StringBuffer bf) {
        bf.append("{ \"TreesMaxID\": \"");
        bf.append(id);
        bf.append("\", \"Trees\": ");
        if (!trees.isEmpty()) {
            for (Tree tree : trees) {
                tree.parseToJSON(bf);
            }
        }
        bf.append(" }.");
    }

}
