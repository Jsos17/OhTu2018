package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    Varasto var2;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        var2 = new Varasto(42.0, 10.0);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenTilavuusHuomataan() {
        Varasto v = new Varasto(-42.0);
        assertEquals(0.0, v.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void alkusaldoKontruktorinTilavuusTesti() {
        assertEquals(42.0, var2.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void alkusaldoKontruktorinSaldoTesti() {
        assertEquals(10.0, var2.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void liianSuuriAlkusaldoHuomataan() {
        Varasto v = new Varasto(37.0, 100);
        assertEquals(37.0, v.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivineAlkusaldoHuomataan() {
        Varasto v = new Varasto(37.0, -50.0);
        assertEquals(0.0, v.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivineTilavuusHuomataanToisessaKontruktorissa() {
        Varasto v = new Varasto(-50.0, 50.0);
        assertEquals(0.0, v.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenMaaraHuomataan() {
        varasto.lisaaVarastoon(-10);
        assertEquals(0.0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void tilavuuttaEiVoiYlittaaLisatessa() {
        varasto.lisaaVarastoon(5);
        varasto.lisaaVarastoon(10);
        varasto.lisaaVarastoon(42);
        assertEquals(10.0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivistaMaaraaEiVoiOttaa() {
        assertEquals(0.0, varasto.otaVarastosta(-5.0), vertailuTarkkuus);
    }

    @Test
    public void vainSaldonVerranVoiOttaa() {
        varasto.lisaaVarastoon(7.0);
        assertEquals(7.0, varasto.otaVarastosta(9.0), vertailuTarkkuus);
    }

    @Test
    public void toStringOnOikein() {
        varasto.lisaaVarastoon(5.0);
        assertEquals("saldo = 5.0, vielä tilaa 5.0", varasto.toString());
    }

}
