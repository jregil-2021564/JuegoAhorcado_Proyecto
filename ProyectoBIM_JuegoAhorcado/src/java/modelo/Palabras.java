package modelo;

public class Palabras {
    private int codigoPalabra;
    private String palabra;
    private String pista;

    public Palabras() {
    }

    public Palabras(int codigoPalabra, String palabra, String pista) {
        this.codigoPalabra = codigoPalabra;
        this.palabra = palabra;
        this.pista = pista;
    }

    public int getCodigoPalabra() {
        return codigoPalabra;
    }

    public void setCodigoPalabra(int codigoPalabra) {
        this.codigoPalabra = codigoPalabra;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }
}