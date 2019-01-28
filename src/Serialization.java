import java.io.*;

/**
 * Класс методов Serialization.
 * @version 0.9
 * @autor Евгений Барабанов
 */
public class Serialization {

    /**
     * Процедура сериализации деревьев по заданному потоку
     * @param out - поток
     * @param trees - лист деревьев
     */
    public static void serializeTrees(OutputStream out, Trees trees) throws IOException {
        (new ObjectOutputStream(out)).writeObject(trees);
    }

    /**
     * Функция десериализации деревьев из заданного потока
     * @param in - поток
     * @return лист деревьев
     */
    public static Trees deserialaizeTrees(InputStream in) throws IOException, ClassNotFoundException {
        return (Trees) (new ObjectInputStream(in)).readObject();
    }

}
