import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server implements Runnable {
    Socket socket;

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
            Trees trees = new Trees();
            Tree currentTree = new Tree();


            String treeName = "2";
            treeName = treeName.concat(".bin");
            try (InputStream input = new FileInputStream(treeName)) {

                currentTree = null;
Serialization s = new Serialization();
                trees = s.deserialaizeTrees(input);
                input.close();

            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

            trees.getTreeById(0).showTree();


            while (!socket.isClosed()) {

                Action choice = (Action) in.readObject();
                System.out.println("Server: Получили Action " + choice);



                switch (choice) {
                    case CREATETREE:{
                        String nodeName = in.readUTF();
                        String newTreeName = in.readUTF();

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
                    case DELETETREE:{
                        int id = in.readInt();
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
                    case CREATENODE:{
                        currentTree = trees.getTreeById(in.readInt());
                        int id = in.readInt();
                        String nodeName = in.readUTF();

                        if (nodeName.replaceAll(" ", "").equals("")) {
                            trees.getTreeById(currentTree.getTreeId()).newNode(id);
                        } else {
                            trees.getTreeById(currentTree.getTreeId()).newNode(id, nodeName);
                        }
                        break;
                    }

                    //Зачем я делаю через currentTree?!!??!?!?! Возможно в этом ошибка. Проверить!!
                    case DELETENODE:{
                        currentTree = trees.getTreeById(in.readInt());
                        int id = in.readInt();


                        if(currentTree.getNodeById(id)!=null){
                            currentTree.deleteNodeById(id);
                        }
                        else{
                            System.out.println("Узла с таким id нет");
                        }
                        break;
                    }
                    case SPLITTREE:{
                        currentTree = trees.getTreeById(in.readInt());
                        int id = in.readInt();


                        if(currentTree.getNodeById(id)!=null){
                            currentTree.splitTree(id);
                        }
                        else{
                            System.out.println("Узла с таким id нет");
                        }
                        out.writeObject(currentTree);
                        out.flush();
                        break;
                    }
                    case CLONETREE:{
                        currentTree = trees.getTreeById(in.readInt());

                        if(currentTree != null){
                            trees.cloneTree(currentTree.getTreeId());
                        }
                        else{
                            System.out.println("Дерево не выбрано");
                        }
                        out.writeObject(currentTree);
                        out.flush();
                        break;
                    }
                    case SAVE:{
                        try (OutputStream output = new FileOutputStream("2.bin")) {

//                        try (FileWriter output = new FileWriter("3.txt")) {
//                            Gson gson = new GsonBuilder()
//                                    .setPrettyPrinting()
//                                    .create();
//                            String outputStr = gson.toJson(trees);
//output.write(outputStr);
//output.flush();
//System.out.println(outputStr);

                            Serialization s = new Serialization();

                            s.serializeTrees(output, trees);
                        }
                        catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                        break;
                    }
                    case LOAD:{
                        for (Tree tree:trees.getTrees()) {
                            tree.showTree();
                        }
                        Serialization s = new Serialization();
                        try (InputStream input = new FileInputStream(treeName)) {

                            currentTree = null;

                            trees = s.deserialaizeTrees(input);
                            input.close();

                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }


                        s.serializeTrees(out,trees);
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
