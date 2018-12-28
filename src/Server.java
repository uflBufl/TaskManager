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

    public static void serializeTrees (OutputStream out, Trees trees) throws IOException {
        (new ObjectOutputStream(out)).writeObject(trees);
    }

    public static Trees deserialaizeTrees (InputStream in) throws IOException, ClassNotFoundException {
        return (Trees) (new ObjectInputStream(in)).readObject();
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

                trees = deserialaizeTrees(input);
                input.close();

            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

            trees.getTreeById(0).showTree();


            while (!socket.isClosed()) {

                int choice = in.read();
                System.out.println("Server: Получили Action " + choice);
                switch (choice) {
                    case 1:{
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
//                                out.writeObject(currentTree);
//                                out.flush();
                        break;
                    }
                    case 3:{
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
                    case 5:{
                        currentTree = trees.getTreeById(in.readInt());
                        int id = in.readInt();
                        String nodeName = in.readUTF();

                        if (nodeName.replaceAll(" ", "").equals("")) {
                            trees.getTreeById(currentTree.getTreeId()).newNode(id);
                            //                                node = new Node(id);
                        } else {
                            trees.getTreeById(currentTree.getTreeId()).newNode(id, nodeName);
                            //                                node = new Node(id, name);
                        }
//                        out.writeObject(currentTree);
//                        out.flush();
                        break;
                    }
                    case 6:{
                        currentTree = trees.getTreeById(in.readInt());
                        int id = in.readInt();


                        if(currentTree.getNodeById(id)!=null){
                            currentTree.deleteNodeById(id);
                        }
                        else{
                            System.out.println("Узла с таким id нет");
                        }
//                        out.writeObject(currentTree);
//                        out.flush();
                        break;
                    }
                    case 7:{
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
                    case 8:{
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
                    case 10:{
                        try (OutputStream output = new FileOutputStream("3.txt")) {
//                        try (FileWriter output = new FileWriter("3.txt")) {
//                            Gson gson = new GsonBuilder()
//                                    .setPrettyPrinting()
//                                    .create();
//                            String outputStr = gson.toJson(trees);
//output.write(outputStr);
//output.flush();
//System.out.println(outputStr);
                            serializeTrees(output, trees);
                        }
                        catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                        break;
                    }
                    case 11:{
//                                out.writeObject(trees);
                        for (Tree tree:trees.getTrees()) {
                            tree.showTree();
                        }
                        serializeTrees(out,trees);
                        out.flush();
                        break;
                    }
                    default:{
                        System.out.println("Нет такого пункта.");
                    }
                }


//                    Action neededAction = (Action) reader.readObject();
//                        System.out.println("Server: Получили Action " + choice);


            }
//                int typeInt;
//                while ((typeInt = reader.readInt()) != -1) {
//                    Buildings.TypeOfBuilding type = Buildings.getType(typeInt);
//
//                    BuildingFactory bf =  new OfficeFactory();
//                    switch (type){
//                        case DWELLING:  bf = new DwellingFactory(); break;
//                        case OFFICE:    bf = new OfficeFactory();   break;
//                        case HOTEL:     bf = new HotelFactory();    break;
//                    }
//                    Buildings.setBuildingFactory(bf);
//
//                    Building b = Buildings.inputBuilding(reader);
//                    int cost = 0;
//                    try {
//                        cost = calculateCost(b, getMultiplier(type));
//                        System.out.println("Server: Тип здания = " + type.name() +
//                                "; Общая площадь = " + b.getTotalSquare() + "; Стоимость = " + cost);
//                    } catch (BuildingUnderArrestException e) {
//                        cost = -1;
//                        System.out.println("Server: " + e.getMessage());
//                    }
//
//                    writer.writeInt(cost);
//                    writer.flush();
//                    System.out.println("Server: Стоимость отправленна.");
//                }

//                System.out.println("Server: Работа с данным клиентом завершена(закрываем Streams and socket).");
//                reader.close();
//                writer.close();
//                socket.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Server: FIN!");
        }
    }
}
