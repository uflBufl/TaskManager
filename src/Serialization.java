import java.io.*;

/**
 * Класс методов Serialization.
 * @version 0.9
 * @author Евгений Барабанов
 */
public class Serialization {

    /**
     * Процедура сериализации деревьев по заданному потоку
     * @param out - поток
     * @param trees - лист деревьев
     * @throws IOException если произошла неизвестная ошибка
     */
    public static void serializeTrees(OutputStream out, Trees trees) throws IOException {
        try{
                (new ObjectOutputStream(out)).writeObject(trees);
                }
            catch(Exception e){
            throw e;
        }
    }

    /**
     * Функция десериализации деревьев из заданного потока
     * @param in - поток
     * @return лист деревьев
     * @throws IOException если произошла неизвестная ошибка
     * @throws ClassNotFoundException не удалось прочитать искомый объект {@link Trees} из потока
     */
    public static Trees deserialaizeTrees(InputStream in) throws IOException, ClassNotFoundException {
        try {
        return (Trees) (new ObjectInputStream(in)).readObject();
    }
            catch(Exception e){
        throw e;
    }
    }

}
