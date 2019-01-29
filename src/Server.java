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
 *
 * @version 0.9
 * @author Евгений Барабанов
 */
public class Server implements Runnable {
    /** Поле сокет */
    Socket socket;

    /** Поле Лист нитей */
    public static ArrayList<Thread> threads = new ArrayList<>();

    /** Поле Лист потоков */
    public static ArrayList<ObjectOutputStream> oos = new ArrayList<>();

    /**
     * Поле Объекта ParseFromJSON
     * @see ParseFromJSON
     */
    ParseFromJSON parse = new ParseFromJSON();

    /**
     * Конструктор - создание нового объекта сервера с определенными значениями
     * @param socket Сокет
     * @see Server#Server(Socket)
     */
    public Server(Socket socket) {
        this.socket = socket;
    }

    /**
     * Процедура старта сервера и ожидание клиентов
     * @param args - параметры в виде строки
     * @throws IOException если произошла неизвестная ошибка
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Server: Start");
        try (ServerSocket server = new ServerSocket(4445)) {
            System.out.println("Server: Серверный сокет с портом 4445 создан.");
            System.out.println("Server: Входим в бесконечный цикл ожидания...");
            while (true) {
                Socket socket = server.accept();

                Server newServer = new Server(socket);
                Thread t = new Thread(newServer);

                threads.add(t);
                System.out.println(threads);

                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Server: FIN!");
            throw e;
        }
    }

    /**
     * Процедура соеденения с клиентом и получение его запросов
     */
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
                BufferedReader br = new BufferedReader(new FileReader(treeName));
                String str3 = br.readLine();
                br.close();
                trees = parse.parseTreesFromJSON(str3);
                input.close();
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

            trees.getTreeById(0).showTree();


            while (!socket.isClosed()) {
                String str = in.readUTF();

                int end = 0;
                int start = ParseFromJSON.findStart(str, end);
                end = ParseFromJSON.findEnd(str, start);

                String choice = str.substring(start, end);

                System.out.println("Server: Получили Action " + choice);

                switch (choice) {
                    case "CREATETREE": {
                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String nodeName = str.substring(start, end);

                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String newTreeName = str.substring(start, end);

                        if (treeName.replaceAll(" ", "").equals("")) {
                            trees.createTree(nodeName);
                        } else {
                            trees.createTree(nodeName, newTreeName);
                        }
                        for (Tree tree : trees.getTrees()) {
                            tree.showTree();
                        }
                        break;
                    }
                    case "DELETETREE": {
                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String ID = str.substring(start, end);
                        int id = Integer.parseInt(ID);

                        if (trees.getTreeById(id) != null) {
                            trees.deleteTreeById(id);
                        } else System.out.println("Дерева с таким id нет");
                        break;
                    }

                    case "CREATENODE": {
                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String TreeID = str.substring(start, end);
                        currentTree = trees.getTreeById(Integer.parseInt(TreeID));

                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String TreeName = str.substring(start, end);
                        int id = Integer.parseInt(TreeName);

                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String nodeName = str.substring(start, end);

                        if (nodeName.replaceAll(" ", "").equals("")) {
                            trees.getTreeById(currentTree.getTreeId()).newNode(id);
                        } else {
                            trees.getTreeById(currentTree.getTreeId()).newNode(id, nodeName);
                        }
                        break;
                    }

                    case "DELETENODE": {
                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String TreeID = str.substring(start, end);
                        currentTree = trees.getTreeById(Integer.parseInt(TreeID));

                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String TreeName = str.substring(start, end);
                        int id = Integer.parseInt(TreeName);

                        if (currentTree.getNodeById(id) != null) {
                            currentTree.deleteNodeById(id);
                        } else {
                            System.out.println("Узла с таким id нет");
                        }
                        break;
                    }

                    case "SPLITTREE": {
                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String TreeID = str.substring(start, end);
                        currentTree = trees.getTreeById(Integer.parseInt(TreeID));

                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String TreeName = str.substring(start, end);
                        int id = Integer.parseInt(TreeName);

                        if (currentTree.getNodeById(id) != null) {
                            currentTree.splitTree(id);
                        } else {
                            System.out.println("Узла с таким id нет");
                        }
                        break;
                    }

                    case "CLONETREE": {
                        start = ParseFromJSON.findStart(str, end);
                        end = ParseFromJSON.findEnd(str, start);

                        String TreeID = str.substring(start, end);
                        currentTree = trees.getTreeById(Integer.parseInt(TreeID));

                        if (currentTree != null) {
                            trees.cloneTree(currentTree.getTreeId());
                        } else {
                            System.out.println("Дерево не выбрано");
                        }
                        break;
                    }

                    case "SAVE": {
                        try (FileWriter writer = new FileWriter(treeName, false)) {
                            StringBuffer bf = new StringBuffer();
                            trees.parseToJSON(bf);
                            String text = bf.toString();
                            writer.write(text);
                            writer.flush();
                            writer.close();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }

                        String str3 = " { } ";
                        String testStr = ParseFromJSON.createJSON("UPDATE", str3);

                        for (ObjectOutputStream out1 : oos) {
                            try {
                                out1.writeUTF(testStr);
                                out1.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }

                    case "LOAD": {
                        for (Tree tree : trees.getTrees()) {
                            tree.showTree();
                        }
                        BufferedReader br = new BufferedReader(new FileReader(treeName));
                        String str3 = br.readLine();
                        br.close();

                        trees = parse.parseTreesFromJSON(str3);
                        System.out.println(str3);

                        String testStr = ParseFromJSON.createJSON("TREES", str3);

                        System.out.println(testStr);

                        String type = ParseFromJSON.findTypeFromJSON(testStr);
                        String params = ParseFromJSON.findParamsFromJSON(testStr);

                        System.out.println(type);
                        System.out.println(params);

                        out.writeUTF(testStr);

                        out.flush();
                        break;
                    }
                    default: {
                        System.out.println("Нет такого пункта.");
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Server: FIN!");
        }
    }
}
