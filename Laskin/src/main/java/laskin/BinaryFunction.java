/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laskin;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author jpssilve
 */
public abstract class BinaryFunction implements Komento {

    public UIHandler getH() {
        return h;
    }

    public TextField getSyote() {
        return syote;
    }

    public Sovelluslogiikka getApp() {
        return app;
    }

    private UIHandler h;
    private TextField tulos;
    private TextField syote;
    private Button reset;
    private Button undo;
    private Sovelluslogiikka app;

    public BinaryFunction(UIHandler handler, TextField tuloskentta, TextField syotekentta, Button nollaa, Button undoIt, Sovelluslogiikka sovellus) {
        h = handler;
        tulos = tuloskentta;
        syote = syotekentta;
        reset = nollaa;
        undo = undoIt;
        app = sovellus;
    }

    @Override
    public void suorita() {
        int res = app.tulos();
        syote.setText("");
        tulos.setText(res + "");
        h.doChecks(res, reset, undo);
    }

    @Override
    public void peru() {
        syote.setText("");
        int prev = app.getPrevious();
        tulos.setText(prev + "");
        h.doChecks(prev, reset, undo);
    }
}
