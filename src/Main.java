import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;

/**
 * Класс Старта Программы.
 * @version 0.9
 * @author Евгений Барабанов
 */
public class Main {

    /**
     * Процедура вызывающая Главную форму
     * @param args - параметры в виде строки
     */
    public static void main(String[] args) {

        MainForm mf = new MainForm();
        mf.start();
    }
}
