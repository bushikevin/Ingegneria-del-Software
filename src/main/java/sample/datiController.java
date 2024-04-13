package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class datiController {
    private static ObservableList<String> datiPrezzo = FXCollections.observableArrayList();

    public static ObservableList<String> getDatiPrezzo() {
        return datiPrezzo;
    }

    public static void aggiungiPrezzo(String prezzo) {
        if (!datiPrezzo.contains(prezzo)) {
            datiPrezzo.add(prezzo);
        }
    }

    public static void resetDati() {
        datiPrezzo.clear();
    }
}



