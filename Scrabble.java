import java.util.ArrayList;

public class Scrabble {

    private static int NUM_PLAYERS = 2;
    private static int ZERO_SCORE_PLAY_LIMIT = 6;

    private Board board;
    private Pool pool;
    private Dictionary dictionary;
    private ArrayList<Player> players;
   ArrayList<String> dictionar = new ArrayList<String>();


   public boolean seek (String sought){

       for (int i = 0; i<dictionar.size(); i++) //that's simpler, linear search for now- but on ArrayList
           if(dictionar.get(i).equalsIgnoreCase(sought)) //that's method where we can put any kind of search algorithm
           {
               return true;
               // System.out.println(soughtWord);
           }
       return false;

   }

    public Dictionary getDictionary() {
        return dictionary;
    }
    public boolean inDictionary = true;
    private int currentPlayerId;
    private int numZeroScorePlays;

    Scrabble() {
       dictionary = new Dictionary();
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

    public Player getCurrentPlayer()
    {
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
    public Player getOtherPlayer() {
        return players.get((currentPlayerId+1)%2);
    }

    public void challenge() {

       board.removeChallenged(getPool(),this.getOtherPlayer());
    }
    public void zeroScorePlay() {
        numZeroScorePlays++;
    }

    public void scorePlay() {
        numZeroScorePlays = 0;
    }
//precondition: invoke if there is lastWord to be challenged
    public boolean setInDictionary()
    {   this.dictionary.readToArrayList();
        String soughtWord = board.lastWord.getLetters().toLowerCase();
        boolean found = this.dictionary.contain();
        return found;
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