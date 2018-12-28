import java.io.*;

public class Serialization {

    public static void serializeTrees (OutputStream out, Trees trees) throws IOException {
        (new ObjectOutputStream(out)).writeObject(trees);
    }

    public static Trees deserialaizeTrees (InputStream in) throws IOException, ClassNotFoundException {
        return (Trees) (new ObjectInputStream(in)).readObject();
    }

}
