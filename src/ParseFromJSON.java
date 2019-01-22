public class ParseFromJSON {
    public static String findSubstring(String string){
//        String substring = new String(string);
//        System.out.println(string);
        int n = 1;
        int i = 1;

        while (n != 0){
            char c = string.charAt(i);
            if(c == '}'){
                n--;
            }
            if(c == '{'){
                n++;
            }
            i++;
        }
        String substring = string.substring(0,i);
//        System.out.println(substring);
        return substring;
    }

    public static Node parseNodeFromJSON(String str){
        int i = str.indexOf(':')+1;
        int j = str.indexOf('"',i)+1;
        i = str.indexOf('"',j);
        String NodeID = str.substring(j, i);
        int nodeID = Integer.parseInt(NodeID);

//        System.out.println(nodeID);

        j = str.indexOf(':', i+1);
        i = str.indexOf('"',j)+1;
        j = str.indexOf('"',i);
        String NodeName = str.substring(i,j);

//        System.out.println(NodeName);

//        ArrayList<Node> children = new ArrayList<>();

        Node newnode = new Node(nodeID,NodeName);

//        boolean child = false;
        int startChild = str.indexOf('{', j);
        while (startChild != -1){
//            System.out.println(startChild);

            String subStr = str.substring(startChild);
//            System.out.println(subStr);

            String childStr = findSubstring(subStr);
//            System.out.println(childStr);

//            int endChild = j+childStr.length();
            int endChild = startChild + childStr.length();
//            System.out.println(endChild);
//            System.out.println(str.charAt(endChild));
//            System.out.println(childStr);

            Node child = parseNodeFromJSON(childStr);

            //            children.add(node);

            newnode.children.add(child);
            child.parent = newnode;

            startChild = str.indexOf('{',endChild);
//            j += endChild;
        }

        return newnode;

//        Node node = new Node(Integer.parseInt(NodeID), NodeName, children);


//        if(str.charAt(j+2) != '}'){
//            i = str.indexOf('{',j);
//            String subStr = str.substring(i);
//            String newNode = findSubstring(subStr);
//            parseNodeFromJSON(newNode);
//
//        }
//int i = str.indexOf("NodeId");
//i += 6;


    }






    public static Tree parseTreeFromJSON(String str){

//        System.out.println(str);

        int i = str.indexOf(':')+1;
        int j = str.indexOf('"',i)+1;
        i = str.indexOf('"',j);
        String TreeID = str.substring(j, i);
        int treeID = Integer.parseInt(TreeID);

        j = str.indexOf(':', i+1);
        i = str.indexOf('"',j)+1;
        j = str.indexOf('"',i);
        String TreeName = str.substring(i,j);

        i = str.indexOf(':',j+1);
        j = str.indexOf('"', i)+1;
        i = str.indexOf('"',j);
        String TreeMaxID = str.substring(j,i);
//        System.out.println(TreeMaxID);
        int maxID = Integer.parseInt(TreeMaxID);

//        System.out.println(treeID);
//        System.out.println(TreeName);
//        System.out.println(maxID);


        j = i;

        int startTree = str.indexOf('{', j);

        String subStr = str.substring(startTree);

        String treeStr = findSubstring(subStr);

        Node head = parseNodeFromJSON(treeStr);

        Tree tree = new Tree(head);
        tree.setTreeId(treeID);
        tree.setTreeName(TreeName);
        tree.setMaxId(maxID);

        return tree;
    }





    public static Trees parseTreesFromJSON(String str){
        Trees trees = new Trees();

        int i = str.indexOf(':')+1;
        int j = str.indexOf('"',i)+1;
        i = str.indexOf('"',j);
        String TreesMaxID = str.substring(j, i);
        int treesMaxID = Integer.parseInt(TreesMaxID);

        trees.setMaxId(treesMaxID);

        int startTree = str.indexOf('{', 3);

        while (startTree != -1){
//            System.out.println(startChild);

            String subStr = str.substring(startTree);
//            System.out.println(subStr);

            String treeStr = findSubstring(subStr);
//            System.out.println(childStr);

//            int endChild = j+childStr.length();
            int endTree = startTree + treeStr.length();
//            System.out.println(endChild);
//            System.out.println(str.charAt(endChild));
//            System.out.println(childStr);

            Tree tree = parseTreeFromJSON(treeStr);

            //            children.add(node);

            trees.trees.add(tree);
//            tree.parent = newnode;

            startTree = str.indexOf('{',endTree);
//            j += endChild;
        }

        return trees;
    }

    public static String createJSON(String type, String str){
StringBuffer bf = new StringBuffer();

        bf.append("{ \"Type\": \"");
        bf.append(type);
        bf.append("\", \"Params\": ");
        bf.append(str);
        bf.append(" }.");

        String newStr = bf.toString();

        return newStr;
    }

    public static String findTypeFromJSON(String str){

        int i = str.indexOf(':')+1;
        int j = str.indexOf('"',i)+1;
        i = str.indexOf('"',j);
        String Type = str.substring(j, i);

        return Type;
    }

    public static String findParamsFromJSON(String str){

        int startParams = str.indexOf('{', 3);

        String subStr = str.substring(startParams);

        String params = findSubstring(subStr);

        return params;
    }

}
