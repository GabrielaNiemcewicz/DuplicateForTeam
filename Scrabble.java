import java.util.ArrayList;

public class Scrabble {

	private static int NUM_PLAYERS = 2;
	private static int ZERO_SCORE_PLAY_LIMIT = 6;

	private Board board;
	private Pool pool;
	private ArrayList<Player> players;
	private int currentPlayerId;
	private int numZeroScorePlays;

	Scrabble() {
		board = new Board();
		pool = new Pool();
		players = new ArrayList<>();
		for (int i = 0; i < NUM_PLAYERS; i++) {
			players.add(new Player(i));
		}
		for (Player player : players) {
			player.getFrame().refill(pool);
		}
		currentPlayerId = 0;
		numZeroScorePlays = 0;
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayerId);
	}

	public void turnOver() {
		if (currentPlayerId == NUM_PLAYERS - 1) {
			currentPlayerId = 0;
		} else {
			currentPlayerId++;
		}
	}

	public Board getBoard() {
		return board;
	}

	public Pool getPool() {
		return pool;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void zeroScorePlay() {
		numZeroScorePlays++;
	}

	public void scorePlay() {
		numZeroScorePlays = 0;
	}

	public boolean isZeroScorePlaysOverLimit() {
		return numZeroScorePlays >= ZERO_SCORE_PLAY_LIMIT;
	}

	public void adjustScores() {
		for (Player player : players) {
			player.adjustScore();
		}
	}
}
