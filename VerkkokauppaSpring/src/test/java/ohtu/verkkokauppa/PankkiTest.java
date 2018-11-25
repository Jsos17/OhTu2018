/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.verkkokauppa;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jpssilve
 */
public class PankkiTest {

    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa k;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        k = new Kauppa(varasto, pankki, viite);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void kaksiEriTuotettaJoitaOnOstetaanTilisiirtoaKutsutaan() {
        when(viite.uusi()).thenReturn(147);

        when(varasto.saldo(0)).thenReturn(15);
        when(varasto.haeTuote(0)).thenReturn(new Tuote(0, "Pepsi", 3));

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "Sprite", 2));

        k.aloitaAsiointi();
        k.lisaaKoriin(0);
        k.lisaaKoriin(1);
        k.tilimaksu("Code monkey", "00700");

        verify(pankki).tilisiirto("Code monkey", 147, "00700", "33333-44455", 5);
    }

    @Test
    public void kaksiSamaaTuotettaJoitaOnOstetaanTilisiirtoaKutsutaan() {
        when(viite.uusi()).thenReturn(337);

        when(varasto.saldo(0)).thenReturn(15);
        when(varasto.haeTuote(0)).thenReturn(new Tuote(0, "Pepsi", 3));

        k.aloitaAsiointi();
        k.lisaaKoriin(0);
        k.lisaaKoriin(0);
        k.tilimaksu("Code monkey", "00700");

        verify(pankki).tilisiirto("Code monkey", 337, "00700", "33333-44455", 6);
    }

    @Test
    public void kaksiEriTuotettaOstetaanToistaOnToistaEiOleTarpeeksiTilisiirtoaKutsutaan() {
        when(viite.uusi()).thenReturn(17);

        when(varasto.saldo(0)).thenReturn(15);
        when(varasto.haeTuote(0)).thenReturn(new Tuote(0, "Pepsi", 10));

        when(varasto.saldo(1)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "Sprite", 2));

        k.aloitaAsiointi();
        k.lisaaKoriin(0);
        k.lisaaKoriin(1);
        k.tilimaksu("Code monkey", "00700");

        verify(pankki).tilisiirto("Code monkey", 17, "00700", "33333-44455", 10);
    }
}
