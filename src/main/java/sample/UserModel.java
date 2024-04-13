package sample;

public class UserModel {
    private static UserModel instance;
    private String username;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;
    private String paese;
    private String città;
    private String posta;

    private UserModel() {}

    public static UserModel getInstance() {
        if (instance == null) {
            instance = new UserModel();
        }
        return instance;
    }

    public String getUsername() {return username;}
    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
    public String getIndirizzo() {return indirizzo;}
    public String getTelefono() {return telefono;}
    public String getPaese() {return paese;}
    public String getCittà() {return città;}
    public String getPosta() {return posta;}

    public void setUsername(String username) {this.username = username;}
    public void setNome(String nome) {this.nome = nome;}
    public void setCognome(String cognome) {this.cognome = cognome;}
    public void setIndirizzo(String indirizzo) {this.indirizzo = indirizzo;}
    public void setTelefono(String telefono) {this.telefono = telefono;}
    public void setPaese(String paese) {this.paese = paese;}
    public void setCittà(String città) {this.città = città;}
    public void setPosta(String posta) {this.posta = posta;}
}

