import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sun.nio.ch.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * Класс сервера, соединяющего все клиенты.
 * @autor Евгений Барабанов
 * @version 0.9
 */
public class Server implements Runnable {
    /** Поле сокет */
    Socket socket;

    /** Поле Пул потоков */
    public static ThreadPool tp;
    public static ArrayList<Thread> threads = new ArrayList<>();
    public static ArrayList<ObjectOutputStream> oos = new ArrayList<>();

    /**
     * Конструктор - создание нового объекта сервера
     * @param socket Сокет
     */
    public Server(Socket socket){
        this.socket = socket;
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Server: Start");
        try (ServerSocket server = new ServerSocket(4445)) {
            System.out.println("Server: Серверный сокет с портом 4444 создан.");
            System.out.println("Server: Входим в бесконечный цикл ожидания...");
            while (true) {
                Socket socket = server.accept();

                Server newServer = new Server(socket);
                Thread t = new Thread(newServer);

//                tp.
                threads.add(t);
                System.out.println(threads);

                t.start();


            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Server: FIN!");
        }

    }



    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(new DataOutputStream(socket.getOutputStream()));
             ObjectInputStream in = new ObjectInputStream(new DataInputStream(socket.getInputStream()))) {

            oos.add(out);

            Trees trees = new Trees();
            Tree currentTree = new Tree();


            String treeName = "test";
            treeName = treeName.concat(".txt");
            try (InputStream input = new FileInputStream(treeName)) {

                currentTree = null;
Serialization s = new Serialization();
//                trees = s.deserialaizeTrees(input);
//String str3 = input.toString();

//                String str3 = Files.lines(Paths.get(treeName)).reduce("", String::concat);
//                System.out.println(str3);

                BufferedReader br = new BufferedReader(new FileReader(treeName));
                String str3 = br.readLine();
//                System.out.println(str3);
                br.close();
//                System.out.println(str3);

//                String str3 = "{ \"TreesMaxID\": \"2\", \"Trees\": { \"TreeID\": \"0\", \"TreeName\": \"child\", \"TreeMaxID\": \"6\", \"Tree\": { \"NodeID\": \"0\", \"NodeName\": \"MainChild\", \"children\": { \"NodeID\": \"1\", \"NodeName\": \"child1\", \"children\": { \"NodeID\": \"4\", \"NodeName\": \"child4\" }, },{ \"NodeID\": \"2\", \"NodeName\": \"child2\" },{ \"NodeID\": \"3\", \"NodeName\": \"child3\", \"children\": { \"NodeID\": \"5\", \"NodeName\": \"child5\" }, }, }, },{ \"TreeID\": \"1\", \"TreeName\": \"tasks\", \"TreeMaxID\": \"3\", \"Tree\": { \"NodeID\": \"0\", \"NodeName\": \"Задачи\", \"children\": { \"NodeID\": \"1\", \"NodeName\": \"Сделать ТИ\" },{ \"NodeID\": \"2\", \"NodeName\": \"Поесть\" }, }, }, }.";
                trees = ParseFromJSON.parseTreesFromJSON(str3);

                input.close();

            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

            trees.getTreeById(0).showTree();


            while (!socket.isClosed()) {

//                Action choice = (Action) in.readObject();

                String str = in.readUTF();

                int i1 = str.indexOf(':')+1;
                int j1 = str.indexOf('"',i1)+1;
                i1 = str.indexOf('"',j1);
                String choice = str.substring(j1, i1);

                System.out.println("Server: Получили Action " + choice);



                switch (choice) {
                    case "CREATETREE":{

                        int i = str.indexOf(':',i1)+1;
                        int j = str.indexOf('"',i)+1;
                        i = str.indexOf('"',j);
                        String nodeName = str.substring(j, i);

                        j = str.indexOf(':', i+1);
                        i = str.indexOf('"',j)+1;
                        j = str.indexOf('"',i);
                        String newTreeName = str.substring(i,j);


//                        String nodeName = in.readUTF();
//                        String newTreeName = in.readUTF();

                        if(treeName.replaceAll(" ","").equals("")){
                            trees.createTree(nodeName);
                        }
                        else {
                            trees.createTree(nodeName, newTreeName);
                        }
                        for (Tree tree:trees.getTrees()) {
                            tree.showTree();
                        }
                        break;
                    }
                    case "DELETETREE":{

                        int i = str.indexOf(':',i1)+1;
                        int j = str.indexOf('"',i)+1;
                        i = str.indexOf('"',j);
                        String ID = str.substring(j, i);
//                        int currentTreeID = Integer.parseInt(TreeID);
                        int id = Integer.parseInt(ID);


//                        int id = in.readInt();


                        if(trees.getTreeById(id)!=null) {
                            trees.deleteTreeById(id);
                        }
                        else System.out.println("Дерева с таким id нет");
                        break;
                    }
                    //xml строка для сообщения. или json
                    //сделать понятное сохранение
                    //ошибку передавать и обрабатывать на сервере
                    //копонент ля рисовки дерева
                    //патерн слушатель
                    case "CREATENODE":{
//                        String str = in.readUTF();

                        int i = str.indexOf(':',i1)+1;
                        int j = str.indexOf('"',i)+1;
                        i = str.indexOf('"',j);
                        String TreeID = str.substring(j, i);
//                        int currentTreeID = Integer.parseInt(TreeID);
                        currentTree = trees.getTreeById(Integer.parseInt(TreeID));

                        j = str.indexOf(':', i+1);
                        i = str.indexOf('"',j)+1;
                        j = str.indexOf('"',i);
                        String TreeName = str.substring(i,j);
                        int id = Integer.parseInt(TreeName);

                        i = str.indexOf(':',j+1);
                        j = str.indexOf('"', i)+1;
                        i = str.indexOf('"',j);
                        String nodeName = str.substring(j,i);


//                        currentTree = trees.getTreeById(in.readInt());
//                        int id = in.readInt();
//                        String nodeName = in.readUTF();

                        if (nodeName.replaceAll(" ", "").equals("")) {
                            trees.getTreeById(currentTree.getTreeId()).newNode(id);
                        } else {
                            trees.getTreeById(currentTree.getTreeId()).newNode(id, nodeName);
                        }
                        break;
                    }

                    //Зачем я делаю через currentTree?!!??!?!?! Возможно в этом ошибка. Проверить!!
                    case "DELETENODE":{

                        int i = str.indexOf(':',i1)+1;
                        int j = str.indexOf('"',i)+1;
                        i = str.indexOf('"',j);
                        String TreeID = str.substring(j, i);
//                        int currentTreeID = Integer.parseInt(TreeID);
                        currentTree = trees.getTreeById(Integer.parseInt(TreeID));

                        j = str.indexOf(':', i+1);
                        i = str.indexOf('"',j)+1;
                        j = str.indexOf('"',i);
                        String TreeName = str.substring(i,j);
                        int id = Integer.parseInt(TreeName);



//                        currentTree = trees.getTreeById(in.readInt());
//                        int id = in.readInt();


                        if(currentTree.getNodeById(id)!=null){
                            currentTree.deleteNodeById(id);
                        }
                        else{
                            System.out.println("Узла с таким id нет");
                        }
                        break;
                    }
                    case "SPLITTREE":{

                        int i = str.indexOf(':',i1)+1;
                        int j = str.indexOf('"',i)+1;
                        i = str.indexOf('"',j);
                        String TreeID = str.substring(j, i);
//                        int currentTreeID = Integer.parseInt(TreeID);
                        currentTree = trees.getTreeById(Integer.parseInt(TreeID));

                        j = str.indexOf(':', i+1);
                        i = str.indexOf('"',j)+1;
                        j = str.indexOf('"',i);
                        String TreeName = str.substring(i,j);
                        int id = Integer.parseInt(TreeName);




//                        currentTree = trees.getTreeById(in.readInt());
//                        int id = in.readInt();


                        if(currentTree.getNodeById(id)!=null){
                            currentTree.splitTree(id);
                        }
                        else{
                            System.out.println("Узла с таким id нет");
                        }
//                        out.writeObject(currentTree);
//                        out.flush();
                        break;
                    }
                    case "CLONETREE":{

                        int i = str.indexOf(':',i1)+1;
                        int j = str.indexOf('"',i)+1;
                        i = str.indexOf('"',j);
                        String TreeID = str.substring(j, i);
//                        int currentTreeID = Integer.parseInt(TreeID);
                        currentTree = trees.getTreeById(Integer.parseInt(TreeID));


//                        currentTree = trees.getTreeById(in.readInt());

                        if(currentTree != null){
                            trees.cloneTree(currentTree.getTreeId());
                        }
                        else{
                            System.out.println("Дерево не выбрано");
                        }
//                        out.writeObject(currentTree);
//                        out.flush();
                        break;
                    }
                    case "SAVE":{
//                        try (OutputStream output = new FileOutputStream("2.bin")) {
//
////                        try (FileWriter output = new FileWriter("3.txt")) {
////                            Gson gson = new GsonBuilder()
////                                    .setPrettyPrinting()
////                                    .create();
////                            String outputStr = gson.toJson(trees);
////output.write(outputStr);
////output.flush();
////System.out.println(outputStr);
//
//                            Serialization s = new Serialization();
//
//                            s.serializeTrees(output, trees);
//                        }
//                        catch (Exception e) {
//                            System.out.print(e.getMessage());
//                        }

                        try(FileWriter writer = new FileWriter(treeName, false))
                        {
                            StringBuffer bf = new StringBuffer();
                            trees.parseToJSON(bf);
                            // запись всей строки
                            String text = bf.toString();
                            writer.write(text);
                            writer.flush();
                        writer.close();
                        }
                        catch(IOException ex){

                            System.out.println(ex.getMessage());
                        }

                        String str3 = " { } ";

                        String testStr = ParseFromJSON.createJSON("UPDATE" ,str3);

//                        out.writeUTF(testStr);
//                        out.flush();

                        for (ObjectOutputStream out1:oos) {
                            out1.writeUTF(testStr);
                            out1.flush();
                        }

                        break;
                    }
                    case "LOAD":{
                        for (Tree tree:trees.getTrees()) {
                            tree.showTree();
                        }

//                        try (InputStream input = new FileInputStream(treeName)) {
//
//                            currentTree = null;
//
////                            trees = s.deserialaizeTrees(input);
//                            input.close();
//
//                        } catch (Exception e) {
//                            System.out.print(e.getMessage());
//                        }

//                        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
//                        String str3 = br.readLine();
//                System.out.println(str3);
//                        br.close();

//                        StringBuffer bf = new StringBuffer();
//                        trees.parseToJSON(bf);
//                        // запись всей строки
//                        String str3 = bf.toString();

                        BufferedReader br = new BufferedReader(new FileReader(treeName));
                        String str3 = br.readLine();
//                System.out.println(str3);
                        br.close();
//                System.out.println(str3);

//                String str3 = "{ \"TreesMaxID\": \"2\", \"Trees\": { \"TreeID\": \"0\", \"TreeName\": \"child\", \"TreeMaxID\": \"6\", \"Tree\": { \"NodeID\": \"0\", \"NodeName\": \"MainChild\", \"children\": { \"NodeID\": \"1\", \"NodeName\": \"child1\", \"children\": { \"NodeID\": \"4\", \"NodeName\": \"child4\" }, },{ \"NodeID\": \"2\", \"NodeName\": \"child2\" },{ \"NodeID\": \"3\", \"NodeName\": \"child3\", \"children\": { \"NodeID\": \"5\", \"NodeName\": \"child5\" }, }, }, },{ \"TreeID\": \"1\", \"TreeName\": \"tasks\", \"TreeMaxID\": \"3\", \"Tree\": { \"NodeID\": \"0\", \"NodeName\": \"Задачи\", \"children\": { \"NodeID\": \"1\", \"NodeName\": \"Сделать ТИ\" },{ \"NodeID\": \"2\", \"NodeName\": \"Поесть\" }, }, }, }.";
                        trees = ParseFromJSON.parseTreesFromJSON(str3);
System.out.println(str3);


String testStr = ParseFromJSON.createJSON("TREES" ,str3);

System.out.println(testStr);

String type = ParseFromJSON.findTypeFromJSON(testStr);
String params = ParseFromJSON.findParamsFromJSON(testStr);

                        System.out.println(type);
                        System.out.println(params);




//                        System.out.println(str3);
                        out.writeUTF(testStr);

//out.writeInt(999);
//                        s.serializeTrees(out,trees);
                        out.flush();
                        break;
                    }
                    default:{
                        System.out.println("Нет такого пункта.");
                    }
                }

            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Server: FIN!");
        }
    }
}
