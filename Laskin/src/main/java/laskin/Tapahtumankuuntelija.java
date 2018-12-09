package laskin;

import java.util.HashMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Tapahtumankuuntelija implements EventHandler {

    public HashMap<Button, Komento> komennot;
    private Komento edellinen = null;
    private Button undo;
    private Sovelluslogiikka sovellus;

    public Tapahtumankuuntelija(TextField tuloskentta, TextField syotekentta, Button plus, Button miinus, Button nollaa, Button undo) {
        this.undo = undo;
        this.sovellus = new Sovelluslogiikka();
        UIHandler handler = new UIHandler();
        komennot = new HashMap<>();
        komennot.put(plus, new Summa(handler, tuloskentta, syotekentta, nollaa, undo, this.sovellus));
        komennot.put(miinus, new Erotus(handler, tuloskentta, syotekentta, nollaa, undo, this.sovellus));
        komennot.put(nollaa, new Nollaa(handler, tuloskentta, syotekentta, nollaa, undo, this.sovellus));
    }

    @Override
    public void handle(Event event) {
        if (event.getTarget() != undo) {
            Komento komento = komennot.get((Button) event.getTarget());
            komento.suorita();
            edellinen = komento;
        } else if (event.getTarget() == undo && edellinen != null) {
            edellinen.peru();
            edellinen = null;
        }
    }

}
