import java.io.*;
import java.net.Socket;

public class Client {

    Socket s;
    ObjectInputStream in;
    ObjectOutputStream out;

    public Client() {
        try {
            s = new Socket("localhost", 4445);
            in = new ObjectInputStream(new DataInputStream(s.getInputStream()));
            out = new ObjectOutputStream(new DataOutputStream(s.getOutputStream()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Trees getData(){
Trees trees = new Trees();

        try {
            out.write(11);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            trees = deserialaizeTrees(in);
//            trees = (Trees)in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

trees.getTreeById(0).showTree();
        return trees;
    }

    public void newNode(int currentTreeID, int id, String name){
//        Tree tree = new Tree();

        try {
            out.write(5);
            out.writeInt(currentTreeID);
            out.writeInt(id);
            out.writeUTF(name);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try{
//        tree = (Tree)in.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        return tree;
    }

    public void deleteNode(int currentTreeID, int id){
        try {
            out.write(6);
            out.writeInt(currentTreeID);
            out.writeInt(id);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void splitTree(int currentTreeID, int id){
        try {
            out.write(7);
            out.writeInt(currentTreeID);
            out.writeInt(id);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cloneTree(int currentTreeID){
        try {
            out.write(8);
            out.writeInt(currentTreeID);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveData(){
        try {
            out.write(10);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createTree(String nodeName, String treeName){
        try {
            out.write(1);
            out.writeUTF(nodeName);
            out.writeUTF(treeName);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTreeById(int id){
        try {
            out.write(3);
            out.writeInt(id);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serializeTrees (OutputStream out, Trees trees) throws IOException {
        (new ObjectOutputStream(out)).writeObject(trees);
    }

    public static Trees deserialaizeTrees (InputStream in) throws IOException, ClassNotFoundException {
        return (Trees) (new ObjectInputStream(in)).readObject();
    }


}
