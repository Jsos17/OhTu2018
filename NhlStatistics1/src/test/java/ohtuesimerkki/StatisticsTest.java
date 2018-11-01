/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jpssilve
 */
public class StatisticsTest {

    Reader readerStub = new Reader() {

        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<Player>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri", "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }
    };

    Statistics stats;

    @Before
    public void setUp() {
        stats = new Statistics(readerStub);
    }

    @Test
    public void searchToimii1() {
        assertEquals("Lemieux", stats.search("Lemieux").getName());
        assertEquals(54, stats.search("Lemieux").getAssists());
        assertEquals(35, stats.search("Gretzky").getGoals());
    }

    @Test
    public void searchToimii2() {
        assertTrue(stats.search("Selanne") == null);
    }

    @Test
    public void joukkueenListausToimii1() {
        List<Player> p = stats.team("EDM");
        assertEquals("Semenko", p.get(0).getName());
        assertEquals("Kurri", p.get(1).getName());
        assertEquals("Gretzky", p.get(2).getName());
    }

    @Test
    public void joukkueenListausToimii2() {
        List<Player> p = stats.team("PHI");
        assertEquals(0, p.size());
    }

    @Test
    public void pisteporssiListausToimii1() {
        List<Player> scorers = stats.topScorers(4);
        assertEquals("Gretzky", scorers.get(0).getName());
        assertEquals("Lemieux", scorers.get(1).getName());
        assertEquals("Yzerman", scorers.get(2).getName());
        assertEquals("Kurri", scorers.get(3).getName());
        assertEquals("Semenko", scorers.get(4).getName());
    }

    @Test
    public void pisteporssiListausToimii2() {
        List<Player> scorers = stats.topScorers(1);
        assertEquals("Gretzky", scorers.get(0).getName());
        assertEquals("Lemieux", scorers.get(1).getName());
    }

    @Test
    public void pisteporssiListausToimii3() {
        List<Player> scorers = stats.topScorers(-1);
        assertEquals(0, scorers.size());
    }
}
