public class Board {

    public Word lastWord;

    public static final int BOARD_SIZE = 15;
    public static final int BOARD_CENTRE = 7;
    private static int BONUS = 50;
    private boolean isBoardEmpty = true;

    public static final int WORD_INCORRECT_FIRST_PLAY = 0;
    public static final int WORD_OUT_OF_BOUNDS = 1;
    public static final int WORD_LETTER_NOT_IN_FRAME = 2;
    public static final int WORD_LETTER_CLASH = 3;
    public static final int WORD_NO_LETTER_PLACED = 4;
    public static final int WORD_NO_CONNECTION = 5;
    public static final int WORD_EXCLUDES_LETTERS = 6;
    public static final int WORD_ONE_LETTER_LENGTH = 7;
    public static final int CHALLENGED_BEFORE_FIRST_ROUND=8;

    private static final int[][] LETTER_MULTIPLIER =
            { 	{1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1},
                    {2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
                    {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
                    {1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
                    {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
                    {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2},
                    {1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1} };
    private static final int[][] WORD_MULTIPLIER =
            {   {3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3},
                    {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
                    {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1},
                    {1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
                    {1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {3, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 3},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1},
                    {1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1},
                    {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
                    {3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3} };

    private Square[][] squares;
    private int errorCode;
    private int numPlays;
    private int points;

    Board() {
        squares = new Square[BOARD_SIZE][BOARD_SIZE];
        for (int r=0; r<BOARD_SIZE; r++)  {
            for (int c=0; c<BOARD_SIZE; c++)   {
                squares[r][c] = new Square(LETTER_MULTIPLIER[r][c],WORD_MULTIPLIER[r][c]);
            }
        }
        numPlays = 0;
        points = 0;
    }


    public boolean isFirstPlay(){
        return numPlays<1?true:false; //first play for first player is 0, for second player- 1.
    }
    private void set_IsBoardEmptyToFalse(){
        this.isBoardEmpty = false;
    }

    public boolean isFirstPlacement(){
        if (this.isBoardEmpty==true) {
            for (int i=0; i<this.BOARD_SIZE; i++)
                for (int j=0; j<this.BOARD_SIZE; j++)
                    if (this.squares[i][j].isOccupied())
                    {this.set_IsBoardEmptyToFalse();
                        /*return false;*/}


            /*return true;*/ }
        return this.isBoardEmpty;


    }

    public boolean isLegalPlay(Frame frame, Word word) {
        boolean isLegal = true;
        //check for invalid first play
        if (this.isFirstPlacement() &&
                ((word.isHorizontal() && (word.getRow()!=BOARD_CENTRE || word.getFirstColumn()>BOARD_CENTRE ||
                        word.getLastColumn()<BOARD_CENTRE)) ||
                        (word.isVertical() && (word.getColumn()!=BOARD_CENTRE || word.getFirstRow()>BOARD_CENTRE ||
                                word.getLastRow()<BOARD_CENTRE)))) {
            isLegal = false;
            errorCode = WORD_INCORRECT_FIRST_PLAY;
        }
        //SPRINT 4 - no 1 letter-length words
        if (isLegal && word.getLength() == 1) {
            isLegal = false;
            errorCode = WORD_ONE_LETTER_LENGTH;
        }
        // check for word out of bounds
        if (isLegal && ((word.isHorizontal() && word.getLastColumn()>= BOARD_SIZE) ||
                (word.isVertical() && word.getLastRow()>= BOARD_SIZE))) {
            isLegal = false;
            errorCode = WORD_OUT_OF_BOUNDS;
        }
        // check that letters in the word do not clash with those on the board
        String lettersPlaced = "";
        if (isLegal) {
            int r = word.getFirstRow();
            int c = word.getFirstColumn();
            for (int i = 0; i < word.getLength() && isLegal; i++) {
                if (squares[r][c].isOccupied() && squares[r][c].getTile().getLetter() != word.getLetter(i)) {
                    isLegal = false;
                    errorCode = WORD_LETTER_CLASH;
                } else if (!squares[r][c].isOccupied()) {
                    lettersPlaced = lettersPlaced + word.getLetter(i);
                }
                if (word.isHorizontal()) {
                    c++;
                } else {
                    r++;
                }
            }
        }
        // check that more than one letter is placed
        if (isLegal && lettersPlaced.length() == 0) {
            isLegal = false;
            errorCode = WORD_NO_LETTER_PLACED;
        }

        // check that the letters placed are in the frame
        if (isLegal && !frame.isAvailable(lettersPlaced)) {
            isLegal = false;
            errorCode = WORD_LETTER_NOT_IN_FRAME;
        }
        // check that the letters placed connect with the letters on the board
        if (isLegal && (!this.isFirstPlacement())) {
            int boxTop = Math.max(word.getFirstRow()-1,0);
            int boxBottom = Math.min(word.getLastRow()+1, BOARD_SIZE-1);
            int boxLeft = Math.max(word.getFirstColumn()-1,0);
            int boxRight = Math.min(word.getLastColumn()+1, BOARD_SIZE-1);
            boolean foundConnection = false;
            for (int r=boxTop; r<=boxBottom && !foundConnection; r++) {
                for (int c=boxLeft; c<=boxRight && !foundConnection; c++) {
                    if (squares[r][c].isOccupied()) {
                        foundConnection = true;
                    }
                }
            }
            if (!foundConnection) {
                isLegal = false;
                errorCode = WORD_NO_CONNECTION;
            }
        }
        // check there are no tiles before the word
        if (isLegal &&(!this.isFirstPlacement())) {
            if ( (word.isHorizontal() && word.getFirstColumn()>0 &&
                    squares[word.getRow()][word.getFirstColumn()-1].isOccupied()) ||
                    (word.isHorizontal() && word.getLastColumn()<BOARD_SIZE-1 &&
                            squares[word.getRow()][word.getLastColumn()+1].isOccupied()) ||
                    (word.isVertical() && word.getFirstRow()>0 &&
                            squares[word.getFirstRow()-1][word.getColumn()].isOccupied()) ||
                    (word.isVertical() && word.getLastRow()<BOARD_SIZE-1 &&
                            squares[word.getLastRow()+1][word.getColumn()].isOccupied())) {
                isLegal = false;
                errorCode = WORD_EXCLUDES_LETTERS;
            }
        }
        return isLegal;
    }

    // getCheckCode precondition: isLegal is false
    public int getErrorCode() {
        return errorCode;
    }

    // place precondition: isLegal is true
    public void place(Frame frame, Word word) {
        points = 0;

        boolean frameWasFull = frame.isFull();
        int wordMultipler = 1;
        int r = word.getFirstRow();
        int c = word.getFirstColumn();
        for (int i=0; i<word.getLength(); i++) {
            if (!squares[r][c].isOccupied()) {
                word.notBelongToOtherWord(i); //assign where Tiles were not occupied- to not remove pre-existing Tiles in challenge, making holes in other words
                char letter = word.getLetter(i);
                Tile tile = frame.getTile(letter);
                squares[r][c].add(tile);
                frame.removeTile(tile);
                points = points + tile.getValue() * squares[r][c].getLetterMuliplier();
                wordMultipler = wordMultipler * squares[r][c].getWordMultiplier();
            }
            if (word.isHorizontal()) {
                c++;
            } else {
                r++;
            }
        }
        points = points * wordMultipler;
        if (frameWasFull && frame.isEmpty()) {
            points = points + BONUS;
        }
        //check of parallel word score needs to be added
        word.saveScore(points);
        this.lastWord = word; //for knowing which word opponent challenges

        numPlays++;
    }

    public int getPoints() {
        return points;
    }

rivate int parallelScore(Word word){
   int r =word.getRow();
   int c = word.getColumn();

   String tempWord = "";
   for (int i= -1; i<2; i+=2) //up and down, left or right
      for(int j=0;j<word.getLength(); j++)

   if(squares[r][c].isEmpty())
         {if(word.isHorizontal()) c++;
         else r++;}
   else while (!squares[r][c].isEmpty()&&r>=0&&c>=0) //find parallel
   {
      if (word.isHorizontal()) r--;
      else c--;
      tempWord += squares[r][c].getCharacter(); //slowly create a new word
   }
   //create new word and count it's score
   int parallelScore = 0;
      parallelScore+=    this.returnScore(new Word(r,c,word.isHorizontal(), tempWord));
   return parallelScore;
      //got at first index of new word





}



    public Square getSquare(int row, int col) {
        return squares[row][col];
    }

    public boolean challengeLegal(){
        if(this.numPlays<1)
            return false;
         return true;
    }
    public void challengeErrorAssinger(){
        errorCode = CHALLENGED_BEFORE_FIRST_ROUND;
    }

    //precondition: challenge was successful
    public void removeChallenged(Pool pool, Player otherPlayer)
    { //other player than current one
        if(this.lastWord!=null)
        {
            int r = this.lastWord.getFirstRow();
            int c = this.lastWord.getFirstColumn();
            int incorrectWordScore;
            for (int i = 0; i < lastWord.getLength(); i++) {
                if (!this.lastWord.occupiedBeforePlacement(i)) {//to not cause holes in previous words
                    squares[r][c].removeTile(pool);

                }
                if (this.lastWord.isHorizontal()) {
                    c++;
                } else {
                    r++;
                }
            }
            incorrectWordScore = this.lastWord.getScore();
            otherPlayer.substractPoints(incorrectWordScore);
            this.lastWord = null; //check if that works, if not,do at least: this.lastWord.saveScore(0);
        } }
}