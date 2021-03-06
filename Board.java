import java.util.ArrayList;

public class Board {

    public Word lastWord;
    public ArrayList<Word> parallelWords;

    public static final int BOARD_SIZE = 15;
    public static final int BOARD_CENTRE = 7;
    private static int BONUS = 50;

    public boolean isBoardEmpty() {
        return isBoardEmpty;
    }

    private boolean isBoardEmpty = true;

    public static final int WORD_INCORRECT_FIRST_PLAY = 0;
    public static final int WORD_OUT_OF_BOUNDS = 1;
    public static final int WORD_LETTER_NOT_IN_FRAME = 2;
    public static final int WORD_LETTER_CLASH = 3;
    public static final int WORD_NO_LETTER_PLACED = 4;
    public static final int WORD_NO_CONNECTION = 5;
    public static final int WORD_EXCLUDES_LETTERS = 6;
    public static final int WORD_ONE_LETTER_LENGTH = 7;
    public static final int CHALLENGED_BEFORE_FIRST_ROUND = 8;

    private static final int[][] LETTER_MULTIPLIER =
            {{1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1},
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
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    private static final int[][] WORD_MULTIPLIER =
            {{3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3},
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
                    {3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3}};

    private Square[][] squares;
    private int errorCode;
    private int numPlays;
    private int points;

    Board() {
        squares = new Square[BOARD_SIZE][BOARD_SIZE];
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                squares[r][c] = new Square(LETTER_MULTIPLIER[r][c], WORD_MULTIPLIER[r][c]);
            }
        }
        numPlays = 0;
        points = 0;
        parallelWords = new ArrayList<Word>(); //empty if not created
    }
    public void display() {
        for(int i=0; i<this.BOARD_SIZE;i++) {
            System.out.printf("            -------------------------------------------------------------\n        %-4d", i);
            for(int j=0; j<this.BOARD_SIZE; j++) {
                System.out.print("| " + squares[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("            -------------------------------------------------------------");
        System.out.println("              0   1   2   3   4   5   6   7   8   9   10  11  12  13  14");
    }




    public boolean isFirstPlay() {
        return numPlays < 1 ? true : false; //first play for first player is 0, for second player- 1.
    }

    private void set_IsBoardEmptyToFalse() {
        this.isBoardEmpty = false;
    }

    public boolean isFirstPlacement() {
        if (this.isBoardEmpty == true) {
            for (int i = 0; i < this.BOARD_SIZE; i++)
                for (int j = 0; j < this.BOARD_SIZE; j++)
                    if (this.squares[i][j].isOccupied()) {
                        this.set_IsBoardEmptyToFalse();
                        /*return false;*/
                    }


            /*return true;*/
        }
        return this.isBoardEmpty;


    }

    public boolean isLegalPlay(Frame frame, Word word) {
        boolean isLegal = true;
        //check for invalid first play
        if (this.isFirstPlacement() &&
                ((word.isHorizontal() && (word.getRow() != BOARD_CENTRE || word.getFirstColumn() > BOARD_CENTRE ||
                        word.getLastColumn() < BOARD_CENTRE)) ||
                        (word.isVertical() && (word.getColumn() != BOARD_CENTRE || word.getFirstRow() > BOARD_CENTRE ||
                                word.getLastRow() < BOARD_CENTRE)))) {
            isLegal = false;
            errorCode = WORD_INCORRECT_FIRST_PLAY;
        }
        //SPRINT 4 - no 1 letter-length words
        if (isLegal && word.getLength() == 1) {
            isLegal = false;
            errorCode = WORD_ONE_LETTER_LENGTH;
        }
        // check for word out of bounds
        if (isLegal && ((word.isHorizontal() && word.getLastColumn() >= BOARD_SIZE) ||
                (word.isVertical() && word.getLastRow() >= BOARD_SIZE))) {
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
            int boxTop = Math.max(word.getFirstRow() - 1, 0);
            int boxBottom = Math.min(word.getLastRow() + 1, BOARD_SIZE - 1);
            int boxLeft = Math.max(word.getFirstColumn() - 1, 0);
            int boxRight = Math.min(word.getLastColumn() + 1, BOARD_SIZE - 1);
            boolean foundConnection = false;
            for (int r = boxTop; r <= boxBottom && !foundConnection; r++) {
                for (int c = boxLeft; c <= boxRight && !foundConnection; c++) {
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
        if (isLegal && (!this.isFirstPlacement())) {
            if ((word.isHorizontal() && word.getFirstColumn() > 0 &&
                    squares[word.getRow()][word.getFirstColumn() - 1].isOccupied()) ||
                    (word.isHorizontal() && word.getLastColumn() < BOARD_SIZE - 1 &&
                            squares[word.getRow()][word.getLastColumn() + 1].isOccupied()) ||
                    (word.isVertical() && word.getFirstRow() > 0 &&
                            squares[word.getFirstRow() - 1][word.getColumn()].isOccupied()) ||
                    (word.isVertical() && word.getLastRow() < BOARD_SIZE - 1 &&
                            squares[word.getLastRow() + 1][word.getColumn()].isOccupied())) {
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

        for (int i = 0; i < word.getLength(); i++) {
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
        //    this.parallelScore();
        this.lastWord = word; //for knowing which word opponent challenges
        numPlays++;
    }

    public int getPoints() {
        return points;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////needs testing. urgently.
  /*  private void parallelScore() {
        Word word = this.lastWord;
        String tempWord = "";
        int newRowStart, newColumnStart; //needed extra storage to record info of created word
        Word oneOfParallels; //if more than one, assignment changes, it's returned to arrayList in Board or score is accessed, hence local storage
        int parallelScore;

//two vars for iterative squarewalkers
        int r;
        int c;


        for (int i = -1; i < 2; i += 2) { //up and down, left or right
//on next run of loop, get r back to normal state, reset to beginning of word
            r = word.getRow();
            c = word.getColumn();
//set squarewalkers _around_ the word, depending whether horizontal/vertical
            if (word.isHorizontal()) r += i;
            else c += i;
            for (int j = 0; j < word.getLength(); j++) {//for each square in line with word walk on squares

                if (!squares[r][c].isOccupied() || word.occupiedBeforePlacement(j)) //empty squarewalker <=> no connection. If was occupied, connects normally, not parallely
                {
                    if (word.isHorizontal()) c++;
                    else r++;
                } //skip them
                else {
                    while (r > 0 && c > 0 && squares[r][c].isOccupied())//end search where Tiles 'don't touch', or we run out of Board
                    { //search back for beginning of parallel word (left or up- 90* from 1st word direction)
                        if (word.isHorizontal()) r--;
                        else c--;
                    }

//small adjustment, because after index n isOccupied, n-1 is answer no matter if n-1 occupied or empty, and that's incorrect
                    if (!squares[r][c].isOccupied()) //go back, check to be safe
                        if (word.isHorizontal()) r++;
                        else c++;


//we're at the start position of parallel (uninstantiated) word now
//so we can record (save, instantiate locally) where start positions of parallel word are
                    newRowStart = r;
                    newColumnStart = c;
//only string is missing- go down, concatenating adding letters
                    while (squares[r][c].isOccupied() && r < 13 && c < 13) //end search where Tiles 'don't touch', or we run out of Board- last index tricky cause word creation, can't get away with unocupied tile
                    { //search forward for end of parallel word (right or down- 270* from original word direction)
                        tempWord += squares[r][c].getTile().getLetter(); //slowly create a new word
                        if (word.isHorizontal()) r++;
                        else c++;
                    }
//small adjustment, because after index n-1 isOccupied, n is answer no matter if n occupied or empty, and that's incorrect
//so we stopped loop before last tile, and add letter to word only if last is occupied too- iterate there
                    if (word.isHorizontal()) r--;
                    else c--;

                    if (squares[r][c].isOccupied()) //go further, check is priority
                        tempWord += squares[r][c].getTile().getLetter();
//if empty, don't assign, do nothing


//check what word is
                    //create new perpendicular word and count its score
                    parallelScore = 0;
                    oneOfParallels = new Word(newRowStart, newColumnStart, word.isVertical(), tempWord);
                    parallelScore += this.returnScore(oneOfParallels);
                    oneOfParallels.saveScore(parallelScore);
                    this.parallelWords.add(oneOfParallels);
                    this.points += parallelScore;

                }
            }
        } //finish second for loop, cause both statements have same code 'coverage'

}//finish first for loop that goes -1 or +1- cause we do all code (on conditions) for each square in squrewalkers around original word
*/
    private int returnScore (Word word) {
        int firstPositionX = word.getRow();
        int firstPositionY = word.getColumn();
        int wordMultiplier = 1;

        int score = 0;

        for (int i = 0; i < word.getLength(); i++) {
            if(!word.occupiedBeforePlacement(i)){
            { score += squares[firstPositionX][firstPositionY].getPlacementScore(); //add each multiplication letter score with tile score for word score
                wordMultiplier *= squares[firstPositionX][firstPositionY].getWordMultiplier(); }//multiply by word multipliers if there are any, otherwise by 1

            if (word.isHorizontal())  //horizontal placement
                firstPositionY++;
            else
                firstPositionX++;
        }}
        return score * wordMultiplier;

    }

    public Square getSquare(int row, int col) {
        return squares[row][col];
    }

    public boolean challengeLegal(){
        if(this.numPlays<1 ||this.lastWord==null) //don't challenge when board is emptied, even if on first central word challenged but numlays>1
        { challengeErrorAssinger();
            return false;}
        return true;
    }
    public void challengeErrorAssinger(){
        errorCode = CHALLENGED_BEFORE_FIRST_ROUND;
    }

    //precondition: challenge was successful
    public void removeChallenged(Pool pool, Player otherPlayer)
    { //other player than current one
        if(this.lastWord!=null) {
            int r = this.lastWord.getFirstRow();
            int c = this.lastWord.getFirstColumn();
            int incorrectWordScore;
            for (int i = 0; i < lastWord.getLength(); i++) {
                if (!this.lastWord.occupiedBeforePlacement(i)) //to not cause holes in previous words
                    squares[r][c].removeTile(pool);


                if (this.lastWord.isHorizontal()) c++;
                else r++;

            }
            incorrectWordScore = this.lastWord.getScore();
            otherPlayer.substractPoints(incorrectWordScore);
            this.lastWord = null; //check if that works, if not,do at least: this.lastWord.saveScore(0);
        }  else
                errorCode = CHALLENGED_BEFORE_FIRST_ROUND;
        }

    public int[] findsParallel() { //multiple booleans...
        Word word = this.lastWord;
        //up and down, left or right
        //on next run of loop, get r back to normal state, reset to beginning of word

        int[] mobileIndex = new int[16]; //max number of parallel words is 15, and last one is number of parallel words in 1 round
        for (int i =0; i<15; i++)
            mobileIndex[i] = -1;
        mobileIndex[15] = 0; //at first, no parallel words assumed
        int counter = 0;
        int newRowStart = word.getFirstRow();
        int newColumnStart = word.getFirstColumn();
        //set squarewalkers _around_ the word, depending whether horizontal/vertical
        //[       ]
        //*AAAAAAAAA *
        //[        ]
        int[] rwalker = new int[2];
        int[] cwalker = new int[2];
        int BoxHorS = 0;
        int BoxHorF = 0;
        int BoxVerS = 0;
        int BoxVerF = 0;
        int temp = 0;
        if (word.isHorizontal()) {//word moves parallely in 1same row
            //row- const index
            if (newRowStart > 0)
                rwalker[0] = newRowStart - 1; //UP
            else rwalker[0] = newRowStart + 1;
            if (newRowStart < 14)
                rwalker[1] = newRowStart + 1; //DOWN
            else rwalker[1] = newRowStart - 1;
            System.out.println(rwalker[0] + " = up, lower, AND " + rwalker[1] + "down, higher");
            //columns- mobile index
            BoxHorS = Math.max(0, newColumnStart);
            BoxHorF = Math.min(newColumnStart + word.getLength(), 14);
        } else {//this is vertical
            //column- constant index //error
            if (newColumnStart > 0)
                cwalker[0] = newColumnStart - 1; //UP
            else cwalker[0] = newColumnStart + 1;
            if (newRowStart < 8)
                cwalker[1] = newColumnStart + 1; //DOWN
            else cwalker[1] = newColumnStart - 1;
            temp = newRowStart + word.getLength();
            BoxVerS = Math.max(0, newRowStart);
            BoxVerF = Math.min(temp, 8);

        }
        if (word.isHorizontal()) {
            for (int i = BoxHorS; i < BoxHorF; i++) //testing code till now
            {

                if (!word.occupiedBeforePlacement(i - BoxHorS) && ((squares[rwalker[0]][i].isOccupied()) || (squares[rwalker[1]][i].isOccupied()))) //if occ. square was existing connection, or *both* of two around are still empty
                {
                    mobileIndex[counter] = i;
                    counter += 1; //1 word parallel found
                }
            }
        }
        if (word.isVertical()) { //is Vertical
            for (int i = BoxVerS; i < BoxVerF; i++) {

              //  if (!word.occupiedBeforePlacement(i - BoxVerS) && ((squares[i][cwalker[0]].isOccupied()) || (squares[i][cwalker[1]].isOccupied()))) //if occ. square was existing connection, or *both* of two around are still empty
               // {
                    mobileIndex[counter] = i;
                    counter += 1; //1 word parallel found
              //  }
            }
        mobileIndex[15] = counter;

        }  return mobileIndex;
    }


    public String messageParallel(){
        int [] parIndex = this.findsParallel();
        String msg = "";
        for (int i =0; i<15; i++)
        {if (parIndex[i]<0)
            break;
         else   msg+=parIndex[i]; }
        msg+="- at those positions detected";
        msg+=parIndex[15];
        msg+=" parallel words";
        return msg;


    }

//use with external loop involving findParallel
    public /*int[]*/ String findStartPW(int foundP, Word word) { //PW = parallel word
int rcontained, ccontained;

        int[] startPosPW;
        // startPosPW = new int[2];
        //startPosPW = new int[]{rcontained, ccontained};
        //horizontal: row is constant, col is mobile
        //vertical:   row is mobile,   col is horizontal
        if(word.isHorizontal())
        {
           rcontained = word.getRow();//mobile in p
           ccontained = foundP;

        }
        else //vertical
        {
            rcontained = foundP;
            ccontained = word.getColumn();//mobile in p
        }
        if (word.isHorizontal())
        {  while (rcontained > 0 && squares[rcontained - 1][ccontained].isOccupied()) //while tiles 'touch' and we don't run out of board
        {
            rcontained-=1;

        }}
        else //isVertical
        {  while (ccontained > 0 && squares[rcontained][ccontained - 1].isOccupied()) //while tiles 'touch' and we don't run out of board
        {
            ccontained--;

        }}
        //  startPosPW[0] = rcontained;
        //startPosPW[1] = ccontained;
        //System.out.println(startPosPW[0] + "   " + startPosPW[1]);
        String start = "";
        start+= rcontained;
        start+= "  and column";
        start+= ccontained;
        System.out.println(rcontained + "   " + ccontained);
       return start;

    }

    public void buildParallelWord(int begin,Word word) {
    int  [] found = this.findsParallel();

        String pWord = "";
        int riter, citer;
        int score = 0;
        if(word.isHorizontal())
        {
            riter = word.getRow();//mobile in p
            citer = begin;

        }
        else //vertical
        {
            riter = begin;
            citer = word.getColumn();//mobile in p
        }

        //horizontal: row is constant, col is mobile
        //vertical:   row is mobile,   col is horizontal
        //parallel word transposes indices
        if (word.isHorizontal()) {
            while (riter < 14 && squares[riter][citer].isOccupied()) //while tiles 'touch' and we don't run out of board
            {
                pWord += squares[riter][citer].getTile().getLetter();
                riter++;
            }
            if (riter <14)
                riter--; //if we wanted to test what is last index of returned position
        } else //isVertical
        {
            while (citer < 14 && squares[riter][citer].isOccupied()) //while tiles 'touch' and we don't run out of board
            {
                pWord += squares[riter][citer].getTile().getLetter();

                citer++;
            }
            if (citer < 8) //if we wanted to test what is last index of returned position
                citer--;
        }
        Word ppword = new Word(riter,citer,word.isVertical(),pWord);
        score+= this.returnScore(ppword);
        this.points+=score;
        ppword.saveScore(score);
        this.parallelWords.add(ppword);}









}