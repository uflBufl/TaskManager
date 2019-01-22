import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Client {

    MainForm mf;

    Socket s;
    ObjectInputStream in;
    ObjectOutputStream out;

    boolean wait = false;


    public Client(MainForm mf) {
        this.mf = mf;
        try {
            s = new Socket("localhost", 4445);
            in = new ObjectInputStream(new DataInputStream(s.getInputStream()));
            out = new ObjectOutputStream(new DataOutputStream(s.getOutputStream()));
        }
        catch (Exception e){
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {

while(true) {

    try {
        String str = in.readUTF();

        String type = ParseFromJSON.findTypeFromJSON(str);
//        String params = ParseFromJSON.findParamsFromJSON(str);

        switch(type){
            case "TREES":{
                String params = ParseFromJSON.findParamsFromJSON(str);
                mf.trees = ParseFromJSON.parseTreesFromJSON(params);
                break;
            }
            case "UPDATE":{

                System.out.println("Произошло обновление!");

                JOptionPane.showMessageDialog(mf,"Произошло обновление!");


//                mf.trees = ParseFromJSON.parseTreesFromJSON(params);
                break;
            }
            default:{
                System.out.println("Нет такого типа сообщения.");
            }
        }

        wait = false;
        System.out.println("false");

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
        }).start();
    }

    public void getData(){
        wait = true;
        System.out.println("true");

        try {
            String str = "{ \"Goal\": \"LOAD\" }" ;
            out.writeUTF(str);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(wait == true){
            System.out.print("");
        }
        System.out.println("exit");
    }

    public void newNode(int currentTreeID, int id, String name){

        try {
//            out.writeObject(Action.CREATENODE);

//            String str = "{ \"CurrentTreeID\": \""+currentTreeID+"\", \"Id\": \""+id+"\", \"Name\": \""+name+"\" }" ;
            String str = "{ \"Goal\": \"CREATENODE\", \"CurrentTreeID\": \""+currentTreeID+"\", \"Id\": \""+id+"\", \"Name\": \""+name+"\" }" ;
out.writeUTF(str);

//            out.writeInt(currentTreeID);
//            out.writeInt(id);
//            out.writeUTF(name);


            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteNode(int currentTreeID, int id){
        try {
//            out.writeObject(Action.DELETENODE);
//            out.writeInt(currentTreeID);
//            out.writeInt(id);

            String str = "{ \"Goal\": \"DELETENODE\", \"CurrentTreeID\": \""+currentTreeID+"\", \"Id\": \""+id+"\" }" ;
            out.writeUTF(str);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void splitTree(int currentTreeID, int id){
        try {
//            out.writeObject(Action.SPLITTREE);
//            out.writeInt(currentTreeID);
//            out.writeInt(id);

            String str = "{ \"Goal\": \"SPLITTREE\", \"CurrentTreeID\": \""+currentTreeID+"\", \"Id\": \""+id+"\" }" ;
            out.writeUTF(str);


            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cloneTree(int currentTreeID){
        try {
//            out.writeObject(Action.CLONETREE);
//            out.writeInt(currentTreeID);

            String str = "{ \"Goal\": \"CLONETREE\", \"CurrentTreeID\": \""+currentTreeID+"\" }" ;
            out.writeUTF(str);


            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveData(){
        try {
//            out.writeObject(Action.SAVE);

            String str = "{ \"Goal\": \"SAVE\" }" ;
            out.writeUTF(str);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createTree(String nodeName, String treeName){
        try {
//            out.writeObject(Action.CREATETREE);
//            out.writeUTF(nodeName);
//            out.writeUTF(treeName);

            String str = "{ \"Goal\": \"CREATETREE\", \"NodeName\": \""+nodeName+"\", \"TreeName\": \""+treeName+"\" }" ;
            out.writeUTF(str);


            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTreeById(int id){
        try {
//            out.writeObject(Action.DELETETREE);
//            out.writeInt(id);

            String str = "{ \"Goal\": \"DELETETREE\", \"ID\": \""+id+"\" }" ;
            out.writeUTF(str);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
