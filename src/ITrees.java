import java.io.Serializable;
import java.util.ArrayList;

public interface ITrees extends Serializable {

    void createTree(String name);

    void createTree(String name, String treeName);

    Tree getTreeById(int id);

    void deleteTreeById(int id);

    ArrayList<Tree> getTrees();

    void cloneTree(int id);



}
