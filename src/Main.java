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
//        tree.deleteTree();
//        System.out.println(tree.getHead() == null);

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
