package sample;

import java.time.LocalDate;

public class adminOrdiniView {

    private String username;
    private String nome;
    private String cognome;
    private String vino;
    private int quantità;
    private double prezzo;
    private String paese;
    private String città;
    private String indirizzo;
    private String posta;
    private String telefono;
    private LocalDate data;

    public adminOrdiniView(String username, String nome, String cognome, String vino, int quantità, double prezzo, String paese, String città, String indirizzo, String posta, String telefono, LocalDate data) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.vino = vino;
        this.quantità = quantità;
        this.prezzo = prezzo;
        this.paese = paese;
        this.città = città;
        this.indirizzo = indirizzo;
        this.posta = posta;
        this.telefono = telefono;
        this.data = data;
    }

    public String getUsername() {return username;}
    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
    public String getPaese() {return paese;}
    public String getCittà() {return città;}
    public String getVino() {return vino;}
    public int getQuantità() {return quantità;}
    public double getPrezzo() {return prezzo;}
    public String getIndirizzo() {return indirizzo;}
    public String getPosta() {return posta;}
    public String getTelefono() {return telefono;}
    public LocalDate getData(){return data;}
}
