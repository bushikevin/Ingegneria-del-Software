package sample;

public class adminVinoView {
    private String vino;
    private int anno;
    private double prezzo;
    private String fornitore;

    public adminVinoView(String vino, int anno, double prezzo, String fornitore)
    {
        this.vino = vino;
        this.anno = anno;
        this.prezzo = prezzo;
        this.fornitore = fornitore;
    }

    public String getVino() {
        return vino;
    }

    public int getAnno(){return anno;}

    public double getPrezzo() {
        return prezzo;
    }

    public String getFornitore(){return fornitore;}
}
