package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataPersistence {
    public static void saveCatalog(List<adminVinoView> catalogo, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            List<adminVinoView> catalogoList = new ArrayList<>(catalogo);
            outputStream.writeObject(catalogoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<adminVinoView> loadCatalog(String fileName) {
        List<adminVinoView> catalogo = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            List<adminVinoView> catalogoList = (List<adminVinoView>) inputStream.readObject();
            catalogo.addAll(catalogoList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return catalogo;
    }
}
