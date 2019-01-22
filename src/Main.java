import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        System.out.println("Hello World!");

        Trees trees = new Trees();

        trees.createTree("MainChild","child");
//        trees.getTreeById(0);
//        Tree tree = new Tree("MainChild");

        System.out.println(trees.getTreeById(0).getHead().id);

        trees.getTreeById(0).addNodeById(0,new Node(1,"child1"));
        System.out.println(trees.getTreeById(0).getNodeById(1).name);

        trees.getTreeById(0).addNodeById(0,new Node(2,"child2"));
        System.out.println(trees.getTreeById(0).getNodeById(2).name);

//Проверка удаления
//        tree.deleteNodeById(1);
//        System.out.println(tree.getNodeById(1) == null);

        trees.getTreeById(0).addNodeById(0,new Node(3,"child3"));
        System.out.println(trees.getTreeById(0).getNodeById(3).name);

        trees.getTreeById(0).addNodeById(1,new Node(4,"child4"));
        System.out.println(trees.getTreeById(0).getNodeById(4).name);

        trees.getTreeById(0).newNode(3,"child5");
        trees.getTreeById(0).showTree();



        trees.createTree("Задачи","tasks");
        trees.getTreeById(1).newNode(0,"Сделать ТИ");
        trees.getTreeById(1).newNode(0,"Поесть");
        trees.getTreeById(1).showTree();

        StringBuffer bf = new StringBuffer();


//        System.out.println("\n\n\n\n\n");
        //Преоверить ObjectMapper
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            objectMapper.writeValue(new File("tree.json"), trees.getTreeById(0));
//        }
//        catch (Exception e){
//
//        }


//        Преоверить parseToJSON
//        trees.getTreeById(0).getHead().parseToJSON(bf);
//        trees.getTreeById(0).parseToJSON(bf);
        trees.parseToJSON(bf);
        System.out.println(bf);

        System.out.println("\n\n\n\n\n");

        String str = "{ \"NodeID\": \"1\", \"NodeName\": \"child1\", \"children\": { \"NodeID\": \"4\", \"NodeName\": \"child4\" }, }";
        String str1 = "{ \"TreeID\": \"1\", \"TreeName\": \"tasks\", \"TreeMaxID\": \"6\", \"Tree\": { \"NodeID\": \"0\", \"NodeName\": \"Задачи\", \"children\": { \"NodeID\": \"1\", \"NodeName\": \"Сделать ТИ\" },{ \"NodeID\": \"2\", \"NodeName\": \"Поесть\" }, }, }";
        String str2 = "{ \"NodeID\": \"0\", \"NodeName\": \"MainChild\", \"children\": { \"NodeID\": \"1\", \"NodeName\": \"child1\", \"children\": { \"NodeID\": \"4\", \"NodeName\": \"child4\" }, },{ \"NodeID\": \"2\", \"NodeName\": \"child2\" },{ \"NodeID\": \"6\", \"NodeName\": \"child6\" },{ \"NodeID\": \"7\", \"NodeName\": \"child7\" },{ \"NodeID\": \"3\", \"NodeName\": \"child3\", \"children\": { \"NodeID\": \"5\", \"NodeName\": \"child5\" }, }, }";
        String str3 = "{ \"TreesMaxID\": \"2\", \"Trees\": { \"TreeID\": \"0\", \"TreeName\": \"child\", \"TreeMaxID\": \"6\", \"Tree\": { \"NodeID\": \"0\", \"NodeName\": \"MainChild\", \"children\": { \"NodeID\": \"1\", \"NodeName\": \"child1\", \"children\": { \"NodeID\": \"4\", \"NodeName\": \"child4\" }, },{ \"NodeID\": \"2\", \"NodeName\": \"child2\" },{ \"NodeID\": \"3\", \"NodeName\": \"child3\", \"children\": { \"NodeID\": \"5\", \"NodeName\": \"child5\" }, }, }, },{ \"TreeID\": \"1\", \"TreeName\": \"tasks\", \"TreeMaxID\": \"3\", \"Tree\": { \"NodeID\": \"0\", \"NodeName\": \"Задачи\", \"children\": { \"NodeID\": \"1\", \"NodeName\": \"Сделать ТИ\" },{ \"NodeID\": \"2\", \"NodeName\": \"Поесть\" }, }, }, }.";

        ParseFromJSON parse = new ParseFromJSON();

        Node node1 = ParseFromJSON.parseNodeFromJSON(str2);

        Tree tree1 = new Tree(node1);

//        tree1.showTree();

        Tree tree3 = ParseFromJSON.parseTreeFromJSON(str1);
//        tree3.showTree();

        Trees trees3 = ParseFromJSON.parseTreesFromJSON(str3);
//        System.out.println(trees3.getMaxID());
//        for(Tree tree123: trees3.trees){
//            tree123.showTree();
//        }

//        tree.deleteTree();
//        System.out.println(subs);

        System.out.println("\n\n\n\n\n");

//        ServerMenu menu = new ServerMenu();
//        menu.start();


        //МЭИН ФОРМ!!!
        MainForm mf = new MainForm();
        mf.start();



//Проверка копирования
//        Tree tree2 = tree.cloneTree();
//        tree2.getNodeById(1).name = "new child1";
//        System.out.println(tree2.getNodeById(1).name);
//        System.out.println(tree.getNodeById(1).name);
//        tree2.getNodeById(2).name = "new child2";
//        System.out.println(tree2.getNodeById(2).name);
//        System.out.println(tree.getNodeById(2).name);
//        tree2.getNodeById(3).name = "new child3";
//        System.out.println(tree2.getNodeById(3).name);
//        System.out.println(tree.getNodeById(3).name);
//        tree2.getNodeById(4).name = "new child4";
//        System.out.println(tree2.getNodeById(4).name);
//        System.out.println(tree.getNodeById(4).name);

// Проверка расщепления
//        tree.splitTree(1);
//        System.out.println(tree.getNodeById(1) == null);
//        System.out.println(tree.getNodeById(0).children.contains(tree.getNodeById(4)));


    }
}
