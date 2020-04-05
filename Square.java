import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Square {

    private int letterMuliplier;
    private int wordMultiplier;
    // private boolean isOccupied;
    private Tile tile;
    private Tile abstractEmptyTile;

    Square(int letterMultiplier, int wordMultiplier) {
        //  isOccupied = false;
        this.letterMuliplier = letterMultiplier;
        this.wordMultiplier = wordMultiplier;
    }

    public int getLetterMuliplier() {
        return letterMuliplier;
    }

    public int getWordMultiplier() {
        return wordMultiplier;
    }

    public boolean isDoubleLetter() {
        return letterMuliplier == 2;
    }

    public boolean isTripleLetter() {
        return letterMuliplier == 3;
    }

    public boolean isDoubleWord() {
        return wordMultiplier == 2;
    }

    public boolean isTripleWord() {
        return wordMultiplier == 3;
    }
    public int getPlacementScore(){
        int score = 0;
        if(this.isOccupied())
            score+= this.letterMuliplier*this.getTile().getValue();
        return score;
    }

    public int getValue(){
        int score = 0;
        if(this.isOccupied())
            score+= this.getTile().getValue();
        return score;

    }

    public void add(Tile tile) {
        // isOccupied = true;
        this.tile = tile;
    }

    public boolean isOccupied() {
        return this.tile == null ? false : true;
        //   return isOccupied;
    }
    public String toString() {
        if(!this.isOccupied())
            if(wordMultiplier==1&&letterMuliplier==1)
                return "REG";
        else if(wordMultiplier==2&&letterMuliplier==1)
                return "2W";
            else if(wordMultiplier==3&&letterMuliplier==1)
                return "3W";
            else if(wordMultiplier==1&&letterMuliplier==2)
                return "2L";
            else //if(wordMultiplier==1&&letterMuliplier==3)
                return "2L";

            return "" + tile.toString();
    }

    // getTile pre-condition: isOccupied must be true
    public Tile getTile() {
        return tile;
    }

    public void removeTile(Pool pool) { //used for each letter of challenged words
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        tiles.add(this.tile);
        pool.addTiles(tiles);
        this.tile = null; //abstractEmptyTile;

    }
    /*public static void main(String[] args) {
        Pool pool = new Pool ();
        Square basic = new Square(1,1);
        Square dl = new Square (2,1);
        Square tw = new Square (1,3);
        Tile a = new Tile('A');
        Tile b = new Tile('B');
        Tile blank = new Tile('_');
        Board board = new Board();

    basic = tw;
    Frame frame = new Frame();
        System.out.println(basic.getPlacementScore());
        basic.add(blank);
        System.out.println(basic.getPlacementScore());
        basic.removeTile(pool);
        basic.add(b);
        System.out.println(basic.getPlacementScore());
      char userInput = ' ';
      Scanner scanner = new Scanner(System.in);
      Scanner choose
      while(userInput!='q'){
      userInput = scanner.next().charAt(0);
      frame.add(a,b);
      frame.add(b, blank);
      if(frame.size()<5)
          frame.add(a, blank);
       board.display();
      board.
      }
        Scrabble scrabble = new Scrabble();
       // boolean match = dict.contain("cat");
        if (match==true)
            System.out.println("Cat is in dictionary");
        else
            System.out.println("Error");
        Square square = new Square(1,2);
        Tile a = new Tile('A');
        Tile blan = new Tile('_');
        Pool pool = new Pool();
        if(!square.isOccupied())
            System.out.println("First it was empty");
        square.add(blan);
        if(square.isOccupied())
            System.out.println("Then a is put on");
        square.removeTile(pool);
        if(!square.isOccupied())
            System.out.println("Then it;'s taken off so again it is empty");
      Board board = new Board();
      Frame frame = new Frame();
      frame.add(a,a);
      frame.add(a,a);
        Word word = new Word(2,3,true,"AA");
      board.place(frame, word);
        System.out.println(board.getSquare(2,3).getTile().getLetter());
        Word word2 = new Word(2,3,false,"AA");
        board.place(frame, word2);
        System.out.println(board.lastWord.getLetters()+"   "+board.lastWord.getScore());
        for (int i=0; i<2; i++)
            System.out.println(board.lastWord.wasOccupied[i]);
        Player player2 = new Player(0);
        board.removeChallenged(pool,player2);
       Square notRemovedCause1stWord= board.getSquare(2,3);
       Square neverAskedToBeRemovedStays= board.getSquare(2,4);
       Square challengedAndRemoved= board.getSquare(3,3);
        if(notRemovedCause1stWord.isOccupied())
            System.out.println("notRemovedCause1stWord");
        if(neverAskedToBeRemovedStays.isOccupied())
            System.out.println("neverAskedToBeRemovedStays");
        if(!challengedAndRemoved.isOccupied())
            System.out.println("challengedAndRemoved");
        a.setBlankAs('W');
        System.out.println(a.getLetter()+"  "+ a.getValue()+"   "+a.isBlank());
     Scrabble scrabble = new Scrabble();
    Player curplayer= scrabble.getPlayers().get(0);
    Player othplayer= scrabble.getPlayers().get(1);
        System.out.println(curplayer.getName());
    scrabble.turnOver();
    scrabble.turnOver();
     scrabble.getOtherPlayer().setName("coineoor");
     scrabble.getCurrentPlayer().setName("abudgkldnvik");
      System.out.println(curplayer.getName() +"  +  "+ othplayer.getName());
        Tile a = new Tile ('A');
        Tile b = new Tile ('B');
        Tile c = new Tile ('C');
        Tile d = new Tile ('D');
        Tile f = new Tile ('F');
        Tile blank1 = new Tile ('_');
        Tile blank2 = new Tile ('_');
        //blank2.setBlankAs('B');
        Frame frame = new Frame ();
        frame.add(blank1,blank2);
        frame.add(f,d);
        ArrayList<Tile>tiles = new ArrayList<Tile>();
        tiles.add(blank1);
        tiles.add(blank2);
        //tiles.addAll(frame.getTiles("DF"));
        //tiles.remove(0);
        //tiles.remove(1);
        //System.out.println(frame.toString());
        //frame.removeTiles(tiles);
        //System.out.println("");
        //System.out.println(frame.toString());
        //System.out.println(blank2.getLetter()+"   "+blank2.toString());
        Tile storedT = frame.getTiles("ZFZ").get(0);
        Tile storedT2 = frame.getTiles("ZFZ").get(1);
        Tile storedT3 = frame.getTiles("ZFZ").get(2);
        System.out.println(storedT.toString());
        System.out.println(storedT2.toString());
        System.out.println(storedT3.toString());
        //System.out.println(frame.toString());
        //
        //System.out.println(frame.toString());
    }*/


    }
