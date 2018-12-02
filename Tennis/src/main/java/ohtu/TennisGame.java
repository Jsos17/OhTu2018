package ohtu;

import java.util.HashMap;

public class TennisGame {

    private int m_score1 = 0;
    private int m_score2 = 0;
    private final String player1Name;
    private final String player2Name;
    private HashMap<Integer, String> evenScores;
    private HashMap<Integer, String> individualScores;
    private HashMap<Integer, String> gameStates;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        initEvenScores();
        initIndividualScores();
        initGameStates();
    }

    private void initEvenScores() {
        evenScores = new HashMap<>();
        evenScores.put(0, "Love-All");
        evenScores.put(1, "Fifteen-All");
        evenScores.put(2, "Thirty-All");
        evenScores.put(3, "Forty-All");
    }

    private void initIndividualScores() {
        individualScores = new HashMap<>();
        individualScores.put(0, "Love");
        individualScores.put(1, "Fifteen");
        individualScores.put(2, "Thirty");
        individualScores.put(3, "Forty");
    }

    private void initGameStates() {
        gameStates = new HashMap<>();
        gameStates.put(1, "Advantage player1");
        gameStates.put(-1, "Advantage player2");
    }

    public void wonPoint(String playerName) {
        if (playerName.equals("player1")) {
            m_score1 += 1;
        } else {
            m_score2 += 1;
        }
    }

    public String getScore() {
        if (m_score1 == m_score2) {
            return evenScores.getOrDefault(m_score1, "Deuce");
        } else if (m_score1 >= 4 || m_score2 >= 4) {
            int minusResult = m_score1 - m_score2;
            if (minusResult >= 2) {
                return "Win for player1";
            } else {
                return gameStates.getOrDefault(minusResult, "Win for player2");
            }
        } else {
            return individualScores.get(m_score1) + "-" + individualScores.get(m_score2);
        }
    }
}
