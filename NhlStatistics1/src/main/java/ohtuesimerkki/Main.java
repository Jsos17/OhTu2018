package ohtuesimerkki;

public class Main {
    public static void main(String[] args) {
        Statistics stats = new Statistics(new PlayerReader("http://nhlstatistics.herokuapp.com/players.txt"));
          
        System.out.println("Philadelphia Flyers");
        for (Player player : stats.team("PHI") ) {
            System.out.println( player );
        }
        
        System.out.println("");
        
        System.out.println("Top scorers");
        final int listHowMany = 10;
        for (Player player : stats.topScorers(listHowMany) ) {
            System.out.println( player );
        }        
    }
}
