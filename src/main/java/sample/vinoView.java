package sample;

public class vinoView {
    private String vino;
    private int anno;
    private double prezzo;

    public vinoView(String vino, int anno, double prezzo)
    {
        this.vino = vino;
        this.anno = anno;
        this.prezzo = prezzo;
    }

    public String getVino() {
        return vino;
    }

    public int getAnno(){return anno;}

    public double getPrezzo() {
        return prezzo;
    }
}
