import java.io.*;
import java.util.Scanner;

public class Menu {
    public Trees trees = new Trees();
    Tree currentTree;

    public void start(){
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

                    if(treeName.replaceAll(" ","").equals("")){
                        trees.createTree(name);
                    }
                    else {
                        trees.createTree(name, treeName);
                    }
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
                        trees.deleteTreeById(id);
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
//                            Node node = new Node();


                            if (name.replaceAll(" ", "").equals("")) {
                                trees.getTreeById(currentTree.getTreeId()).newNode(id);
//                                node = new Node(id);
                            } else {
                                trees.getTreeById(currentTree.getTreeId()).newNode(id, name);
//                                node = new Node(id, name);
                            }

//                            trees.getTreeById(currentTree.getTreeId()).addNodeById(id, node);
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

                        if(currentTree.getNodeById(id)!=null){
                            currentTree.deleteNodeById(id);
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
                case 7:{
                    if(currentTree != null){
                        System.out.println("Введите id узла");
                        int id = in.nextInt();

                        if(currentTree.getNodeById(id)!=null){
                            currentTree.splitTree(id);
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
                case 8:{
                    if(currentTree != null){
                       trees.cloneTree(currentTree.getTreeId());
                    }
                    else{
                        System.out.println("Дерево не выбрано");
                    }
                    break;
                }
                case 9:{
                    for (Tree tree:trees.getTrees()) {
                        tree.showTree();
                    }
                    break;
                }
                case 10:{
                    System.out.println("Введите название файла");
                    String name = in.next();
                    name = name.concat(".bin");
                    try (OutputStream output = new FileOutputStream(name)) {
                        serializeTrees(output);
//                        (new ObjectOutputStream(output)).writeObject(trees);
                        output.close();
                    }
                    catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
//                    (new ObjectOutputStream(out)).writeObject(building);
                    break;
                }

//                SerializeFile

                case 11:{
                    System.out.println("Введите название файла");
                    String name = in.next();
                    name = name.concat(".bin");
                    try (InputStream input = new FileInputStream(name)) {

                        currentTree = null;

                        trees = deserialaizeTrees(input);
                        input.close();


                    }
                    catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
//                    return (Building) (new ObjectInputStream(in)).readObject();
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
