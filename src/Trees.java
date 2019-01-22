import java.io.*;
import java.util.ArrayList;

public class Trees implements ITrees, Serializable {
    ArrayList<Tree> trees = new ArrayList<>();
    private int id = 0;

    final static private ArrayList<Tree> EMPTY_TREES = new ArrayList<>();

    Trees(){
        this(EMPTY_TREES);
    }

    Trees(ArrayList<Tree> trees){
        this.trees.addAll(trees);
    }

    public void setMaxId(int maxId) {
        this.id = maxId;
    }

    public int getMaxID(){
        return id;
    }

    public void createTree(String name){
        createTree(name, null);
    }

    public void createTree(String name, String treeName){
        Tree tree = new Tree(name);
        tree.setTreeId(id);
        this.id++;
        tree.setTreeName(treeName);
        trees.add(tree);
    }

    public Tree getTreeById(int id){
        for (Tree tree:trees) {
            if(tree.getTreeId()==id) return tree;
        }
        return null;
    }

    public void deleteTreeById(int id){
        for (Tree tree:trees) {
            if(tree.getTreeId()==id){ trees.remove(getTreeById(id)); break;}
        }
    }

    public ArrayList<Tree> getTrees(){
        return trees;
    }

    public void cloneTree(int id){
        Tree clone = null;
        for (Tree tree:trees) {
            if(tree.getTreeId()==id) {
                clone = tree.cloneTree();
                clone.setTreeId(this.id);
                this.id++;
//                trees.add(clone);
            }
        }
        if(clone != null)trees.add(clone);
    }
    public void parseToJSON(StringBuffer bf){
        bf.append("{ \"TreesMaxID\": \"");
        bf.append(id);
        bf.append("\", \"Trees\": ");
        if(!trees.isEmpty()){
            for (Tree tree:trees) {
                tree.parseToJSON(bf);
            }
        }

//        bf.append("\", \"TreesMaxID\": \"");
//        bf.append(id);

        bf.append(" }.");
    }

//    public void parseFromJSON(StringBuffer bf){
//
//    }

//    public static void serializeBuilding (OutputStream out) throws IOException {
//        (new ObjectOutputStream(out)).writeObject(building);
//    }
//
//    public static Building deserialaizeBuilding (InputStream in) throws IOException, ClassNotFoundException {
//        return (Building) (new ObjectInputStream(in)).readObject();
//    }




}
