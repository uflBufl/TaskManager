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
            out.writeObject(Action.LOAD);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Serialization s = new Serialization();
            trees = s.deserialaizeTrees(in);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

trees.getTreeById(0).showTree();
        return trees;
    }

    public void newNode(int currentTreeID, int id, String name){

        try {
            out.writeObject(Action.CREATENODE);
            out.writeInt(currentTreeID);
            out.writeInt(id);
            out.writeUTF(name);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNode(int currentTreeID, int id){
        try {
            out.writeObject(Action.DELETENODE);
            out.writeInt(currentTreeID);
            out.writeInt(id);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void splitTree(int currentTreeID, int id){
        try {
            out.writeObject(Action.SPLITTREE);
            out.writeInt(currentTreeID);
            out.writeInt(id);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cloneTree(int currentTreeID){
        try {
            out.writeObject(Action.CLONETREE);
            out.writeInt(currentTreeID);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveData(){
        try {
            out.writeObject(Action.SAVE);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createTree(String nodeName, String treeName){
        try {
            out.writeObject(Action.CREATETREE);
            out.writeUTF(nodeName);
            out.writeUTF(treeName);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTreeById(int id){
        try {
            out.writeObject(Action.DELETETREE);
            out.writeInt(id);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
