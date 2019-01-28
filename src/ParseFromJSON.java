/**
 * Класс методов ParseFromSJSON.
 * @version 0.9
 * @autor Евгений Барабанов
 */
public class ParseFromJSON {

    /**
     * Функция поиска начала значения
     * @param str - строка для поиска
     * @param start - точка начала поиска
     * @return начало значения
     */
    public static int findStart(String str, int start) {
        int i = str.indexOf(':', start + 1);
        return str.indexOf('"', i) + 1;
    }

    /**
     * Функция поиска конца значения
     * @param str - строка для поиска
     * @param start - точка начала поиска
     * @return конец значения
     */
    public static int findEnd(String str, int start) {
        return str.indexOf('"', start);
    }

    /**
     * Функция поиска подстроки выделенной {}
     * @param string - строка для поиска
     * @return подстроки выделенной {}
     */
    public static String findSubstring(String string) {
        int n = 1;
        int i = 1;

        while (n != 0) {
            char c = string.charAt(i);
            if (c == '}') {
                n--;
            }
            if (c == '{') {
                n++;
            }
            i++;
        }

        return string.substring(0, i);
    }

    /**
     * Функция нахождения Узла дерева из JSON
     * @param str - строка для поиска в формате JSON
     * @return Узел дерева из JSON
     */
    public static Node parseNodeFromJSON(String str) {
        int end = 0;

        int start = findStart(str, end);
        end = findEnd(str, start);

        String NodeID = str.substring(start, end);
        int nodeID = Integer.parseInt(NodeID);

        start = findStart(str, end);
        end = findEnd(str, start);

        String NodeName = str.substring(start, end);
        Node newnode = new Node(nodeID, NodeName);

        int startChild = str.indexOf('{', end);

        while (startChild != -1) {
            String subStr = str.substring(startChild);
            String childStr = findSubstring(subStr);
            int endChild = startChild + childStr.length();
            Node child = parseNodeFromJSON(childStr);
            newnode.children.add(child);
            child.parent = newnode;
            startChild = str.indexOf('{', endChild);
        }

        return newnode;
    }

    /**
     * Функция нахождения Дерева из JSON
     * @param str - строка для поиска в формате JSON
     * @return дерево
     */
    public static Tree parseTreeFromJSON(String str) {
        int end = 0;

        int start = findStart(str, end);
        end = findEnd(str, start);

        String TreeID = str.substring(start, end);
        int treeID = Integer.parseInt(TreeID);

        start = findStart(str, end);
        end = findEnd(str, start);

        String TreeName = str.substring(start, end);

        start = findStart(str, end);
        end = findEnd(str, start);

        String TreeMaxID = str.substring(start, end);
        int maxID = Integer.parseInt(TreeMaxID);

        int startTree = str.indexOf('{', end);

        String subStr = str.substring(startTree);
        String treeStr = findSubstring(subStr);
        Node head = parseNodeFromJSON(treeStr);

        Tree tree = new Tree(head);
        tree.setTreeId(treeID);
        tree.setTreeName(TreeName);
        tree.setMaxId(maxID);

        return tree;
    }

    /**
     * Функция нахождения Деревьев из JSON
     * @param str - строка для поиска в формате JSON
     * @return деревьев
     */
    public static Trees parseTreesFromJSON(String str) {
        Trees trees = new Trees();
        int end = 0;

        int start = findStart(str, end);
        end = findEnd(str, start);

        String TreesMaxID = str.substring(start, end);
        int treesMaxID = Integer.parseInt(TreesMaxID);

        trees.setMaxId(treesMaxID);

        int startTree = str.indexOf('{', 3);

        while (startTree != -1) {
            String subStr = str.substring(startTree);
            String treeStr = findSubstring(subStr);
            int endTree = startTree + treeStr.length();
            Tree tree = parseTreeFromJSON(treeStr);
            trees.trees.add(tree);
            startTree = str.indexOf('{', endTree);
        }

        return trees;
    }

    /**
     * Функция Создания сообщения с заданным типом и Параметрами
     * @param type - строка Типа сообщения
     * @param str - строка параметров сообщения в формате JSON
     * @return сообщение с заданным типом и Параметрами
     */
    public static String createJSON(String type, String str) {
        StringBuffer bf = new StringBuffer();

        bf.append("{ \"Type\": \"");
        bf.append(type);
        bf.append("\", \"Params\": ");
        bf.append(str);
        bf.append(" }.");

        return bf.toString();
    }

    /**
     * Функция нахождения типа сообщения из строки в формате JSON
     * @param str - строка поиска в формате JSON
     * @return типа сообщения из строки в формате JSON
     */
    public static String findTypeFromJSON(String str) {
        int end = 0;

        int start = findStart(str, end);
        end = findEnd(str, start);

        return str.substring(start, end);
    }

    /**
     * Функция нахождения параметров сообщения из строки в формате JSON
     * @param str - строка поиска в формате JSON
     * @return параметров сообщения из строки в формате JSON
     */
    public static String findParamsFromJSON(String str) {
        int startParams = str.indexOf('{', 3);

        String subStr = str.substring(startParams);

        return findSubstring(subStr);
    }

}
