package sample;

import javafx.scene.control.CheckBox;

import java.time.LocalDate;

public class ordiniView {
    private String username = null;
    private String vino;
    private int quantità;
    private Double prezzo;
    private LocalDate data;
    private CheckBox consegnato;
    private LocalDate consegna = null;


    public ordiniView(String vino, int quantità, double prezzo, LocalDate data, CheckBox consegnato)
    {
        this.vino = vino;
        this.quantità = quantità;
        this.prezzo = prezzo;
        this.data = data;
        this.consegnato = consegnato;

    }

    public String getUsername(){return username;}
    public String getVino() {
        return vino;
    }
    public int getQuantità() {return quantità;}
    public double getPrezzo() {return prezzo;}
    public LocalDate getData(){return data;}
    public CheckBox getConsegnato(){return consegnato;}
    public LocalDate getConsegna(){return consegna;}

    public void setConsegnato(CheckBox consegnato){this.consegnato = consegnato;}
    public void setConsegna(LocalDate consegna){this.consegna = consegna;}
    public void setUsername(String username){this.username = username;}

}

