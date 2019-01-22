import java.io.*;
import java.util.Scanner;

public class ServerMenu {
    public Trees trees = new Trees();
    Tree currentTree;

    public void start(){


        Client client = new Client(new MainForm());

//        trees = client.getData();
        client.getData();

        boolean menu = true;
        Scanner in = new Scanner(System.in);

        while(menu) {
            System.out.println("");
            System.out.println("1.Создать дерево");
            System.out.println("2.Выбрать дерево");
            System.out.println("3.Удалить дерево");
            System.out.println("4.Вывести выбранное дерево");
            System.out.println("5.Добавить узел к выбранному дереву");
            System.out.println("6.Удалить узел из выбранного дерева");
            System.out.println("7.Расщепление узла в выбранном дереве");
            System.out.println("8.Клонировать выбранное дерево");
            System.out.println("9.Показать все деревья");
            System.out.println("10.Сохранить");
            System.out.println("11.Загрузить");
            System.out.println("12.Выйти");

            int choose = in.nextInt();

            switch (choose){
                case 1:{
                    System.out.println("Введите название главного узла");
                    String name = in.next();
                    System.out.println("Введите название дерева(оставьте пустой, если не нужен)");
                    String treeName = in.next();

                    client.createTree(name, treeName);
                    //        trees = client.getData();
                    client.getData();
                    break;
                }
                case 2:{
                    System.out.println("Введите id дерева");
                    int id = in.nextInt();
                    if(trees.getTreeById(id)!=null)
                        currentTree = trees.getTreeById(id);
                    else System.out.println("Дерева с таким id нет");
                    break;
                }
                case 3:{
                    System.out.println("Введите id дерева");
                    int id = in.nextInt();
                    if(trees.getTreeById(id)!=null) {
                        if(trees.getTreeById(id) == currentTree){
                            currentTree = null;
                        }

                        client.deleteTreeById(id);
                        //        trees = client.getData();
                        client.getData();

                    }
                    else System.out.println("Дерева с таким id нет");
                    break;
                }
                case 4:{
                    if(currentTree != null){
                        trees.getTreeById(currentTree.getTreeId()).showTree();
                    }
                    else{
                        System.out.println("Дерево не выбрано");
                    }
                    break;
                }
                case 5:{
                    if(currentTree != null){
                        System.out.println("Введите id родительского узла");
                        int id = in.nextInt();
                        if(currentTree.getNodeById(id)!=null) {
                            System.out.println("Введите название нового узла");
                            String name = in.next();

                            client.newNode(currentTree.getTreeId(),id,name);
                            //        trees = client.getData();
                            client.getData();
                        }
                        else{
                            System.out.println("Узла с таким id нет");
                        }
                    }
                    else{
                        System.out.println("Дерево не выбрано");
                    }
                    break;
                }
                case 6:{
                    if(currentTree != null){
                        System.out.println("Введите id узла");
                        int id = in.nextInt();

                        client.deleteNode(currentTree.getTreeId(),id);
                        //        trees = client.getData();
                        client.getData();
                    }
                    else{
                        System.out.println("Дерево не выбрано");
                    }
                    break;
                }
                case 7:{
                    if(currentTree != null){
                        System.out.println("Введите id узла");
                        int id = in.nextInt();

                        client.splitTree(currentTree.getTreeId(),id);
                        //        trees = client.getData();
                        client.getData();
                    }
                    else{
                        System.out.println("Дерево не выбрано");
                    }
                    break;
                }
                case 8:{

                    client.cloneTree(currentTree.getTreeId());
                    //        trees = client.getData();
                    client.getData();
                    break;
                }
                case 9:{
                    for (Tree tree:trees.getTrees()) {
                        tree.showTree();
                    }
                    break;
                }
                case 10:{
                    client.saveData();
                    break;
                }

                case 11:{
                    System.out.println("Введите название файла");
                    String name = in.next();
                    name = name.concat(".bin");
                    try (InputStream input = new FileInputStream(name)) {
                        currentTree = null;
                        //        trees = client.getData();
                        client.getData();
//                        trees = deserialaizeTrees(input);
                    }
                    catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
                    break;
                }
                case 12:{
                    menu = false;
                    break;
                }
                default:{
                    System.out.println("Нет такого пункта.");
                }
            }

        }
        in.close();
    }



    public void serializeTrees (OutputStream out) throws IOException {
        (new ObjectOutputStream(out)).writeObject(trees);
    }

    public Trees deserialaizeTrees (InputStream in) throws IOException, ClassNotFoundException {
        return (Trees) (new ObjectInputStream(in)).readObject();
    }
}
