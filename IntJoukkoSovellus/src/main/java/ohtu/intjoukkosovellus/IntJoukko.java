package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5; // aloitustalukon koko
    public final static int OLETUSKASVATUS = 5;  // luotava uusi taulukko on näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        constructorHelper(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti >= 0) {
            constructorHelper(kapasiteetti, OLETUSKASVATUS);
        }
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0 || kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetti väärin");
        }

        constructorHelper(kapasiteetti, kasvatuskoko);
    }

    private void constructorHelper(int kapasiteetti, int kasvatuskoko) {
        ljono = new int[kapasiteetti];
        for (int i = 0; i < ljono.length; i++) {
            ljono[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;
            checkSize();
            return true;
        }
        return false;
    }

    private void checkSize() {
        if (alkioidenLkm % ljono.length == 0) {
            ljono = kopioiTaulukko(ljono, new int[alkioidenLkm + kasvatuskoko]);
        }
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }

        return false;
    }

    public boolean poista(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                swap(i);
                return true;
            }
        }

        return false;
    }

    private void swap(int poistettuIndex) {
        ljono[poistettuIndex] = ljono[alkioidenLkm - 1];
        ljono[alkioidenLkm - 1] = 0;
        alkioidenLkm--;
    }

    private int[] kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

        return uusi;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + ljono[0] + "}";
        } else {
            return toStringHelper();
        }
    }

    private String toStringHelper() {
        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            tuotos += ljono[i] + ", ";
        }
        tuotos += ljono[alkioidenLkm - 1] + "}";
        return tuotos;
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        apuLisaa(x, a);
        apuLisaa(x, b);
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        for (int i = 0; i < a.toIntArray().length; i++) {
            for (int j = 0; j < b.toIntArray().length; j++) {
                apuLeikkaus(a, b, y, i, j);
            }
        }
        return y;
    }

    private static void apuLeikkaus(IntJoukko a, IntJoukko b, IntJoukko y, int i, int j) {
        if (a.toIntArray()[i] == b.toIntArray()[j]) {
            y.lisaa(b.toIntArray()[j]);
        }
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        apuLisaa(z, a);
        for (int i = 0; i < b.toIntArray().length; i++) {
            z.poista(i);
        }

        return z;
    }

    private static void apuLisaa(IntJoukko x, IntJoukko k) {
        for (int i = 0; i < k.toIntArray().length; i++) {
            x.lisaa(k.toIntArray()[i]);
        }
    }
}
