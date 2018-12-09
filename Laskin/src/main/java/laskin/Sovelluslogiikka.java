package laskin;

public class Sovelluslogiikka {

    private int tulos;
    private int previous;

    public void plus(int luku) {
        savePrevious();
        tulos += luku;
    }

    public void miinus(int luku) {
        savePrevious();
        tulos -= luku;
    }

    public void nollaa() {
        savePrevious();
        tulos = 0;
    }

    private void savePrevious() {
        previous = tulos;
    }

    public int getPrevious() {
        tulos = previous;
        return previous;
    }

    public int tulos() {
        return tulos;
    }
}
