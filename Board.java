import java.util.*;

public class Board {

	private Square[][] squares;
	private int checkCode; //nr of validity check not passed
	private int numPlays;

	public static final int WORD_INCORRECT_FIRST_PLAY = 0;
	public static final int WORD_OUT_OF_BOUNDS = 1;
	public static final int WORD_LETTER_NOT_IN_FRAME = 2;
	public static final int WORD_LETTER_CLASH = 3;
	public static final int WORD_NO_LETTER_PLACED = 4;
	public static final int WORD_NO_CONNECTION = 5;
	public static final int BOARD_SIZE = 15;
	public static final int BOARD_CENTRE = 7;

	Board() {
		squares = new Square[BOARD_SIZE][BOARD_SIZE];
		Pattern pattern = new Pattern(squares);
		pattern.drawScrabble(squares);
		numPlays = 0;
	}

	public Square[][] getBoard(){
		return squares;
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





	public void reset() {
		for (Square [] rows:squares)
			for (Square square:rows)
				square.removeTile();
	}



	public void place(Word word, Player player) {
		Frame frame = player.getFrame();
		int r = word.getRow();
		int c = word.getColumn();
		for (int i=0; i<word.getLength(); i++) {
			if (squares[r][c].isEmpty()) {
				word.bingoIncrementor(); //if all 7 tiles from frame are put on empty squares to create a word, bingo, 50 bonus points
				char letter = word.getLetter(i);
				Tile tile = frame.accessByLetter(letter);
				squares[r][c].placeTile(tile);
				frame.removeTile(tile);
			}
			if (word.isHorizontal()) {
				c++;
			} else {
				r++;
			}
		}
		this.increasePlayerScore(word, player);


		numPlays++;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// getCheckCode precondition: isLegal is false
	public int getCheckCode() {
		return checkCode;
	};

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



	public boolean isLegal(Frame frame, Word word) {
		boolean isLegal = true;
		//check for invalid first play
		if (numPlays == 0 &&
				((word.isHorizontal() && (word.getRow()!=BOARD_CENTRE || word.getColumn()>BOARD_CENTRE ||
						word.getLastColumn()<BOARD_CENTRE)) ||
						(word.isVertical() && (word.getColumn()!=BOARD_CENTRE || word.getRow()>BOARD_CENTRE ||
								word.getLastRow()<BOARD_CENTRE)))) {
			isLegal = false;
			checkCode = WORD_INCORRECT_FIRST_PLAY;
		}
		// check for word out of bounds
		if (isLegal && ((word.isHorizontal() && word.getLastColumn()>= BOARD_SIZE) ||
				(word.isVertical() && word.getLastRow()>= BOARD_SIZE))) {
			isLegal = false;
			checkCode = WORD_OUT_OF_BOUNDS;
		}
		// check that letters in the word do not clash with those on the board
		String lettersPlaced = "";
		if (isLegal) {
			int r = word.getRow();
			int c = word.getColumn();
			for (int i = 0; i < word.getLength() && isLegal; i++) {
				if (!squares[r][c].isEmpty() && squares[r][c].getCharacter() != word.getLetter(i)) {
					isLegal = false;
					checkCode = WORD_LETTER_CLASH;
				} else if (squares[r][c].isEmpty()) {
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
			checkCode = WORD_NO_LETTER_PLACED;
		}
		// check that the letters placed are in the frame
		if (isLegal && !frame.isStringIn(lettersPlaced)) {
			isLegal = false;
			checkCode = WORD_LETTER_NOT_IN_FRAME;
		}
		// check that the letters placed connect with the letters on the board
		if (isLegal && numPlays!=0) {
			int boxTop = Math.max(word.getRow()-1,0);
			int boxBottom = Math.min(word.getLastRow()+1, BOARD_SIZE-1);
			int boxLeft = Math.max(word.getColumn()-1,0);
			int boxRight = Math.min(word.getLastColumn()+1, BOARD_SIZE-1);
			boolean foundConnection = false;
			for (int r=boxTop; r<=boxBottom && !foundConnection; r++) {
				for (int c=boxLeft; c<=boxRight && !foundConnection; c++) {
					if (!squares[r][c].isEmpty()) {
						foundConnection = true;
					}
				}
			}
			if (!foundConnection) {
				isLegal = false;
				checkCode = WORD_NO_CONNECTION;
			}
		}
		return isLegal;
	}




//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private int returnScore (Word word)
	{
		int firstPositionX = word.getRow();
		int firstPositionY = word.getColumn();

		int score = 0;
		if (word.isHorizontal()) { //h placement
			for (int i = 0; i < word.getLength(); i++)
				score += squares[firstPositionX][firstPositionY + i].getPlacementScore(); //add each multiplication letter score with tile score for word score
			for (int i = 0; i < word.getLength(); i++)
				score *= squares[firstPositionX][firstPositionY + i].getWordMultiplier(); //multiply by word multipliers if there are any, otherwise by 1
		}
		else 	/*if (word.isVertical())*/ { //v placement
			for (int i = 0; i < word.getLength(); i++)
				score += squares[firstPositionX+i][firstPositionY].getPlacementScore(); //add each multiplication letter score with tile score for word score
			for (int i = 0; i < word.getLength(); i++)
				score *= squares[firstPositionX+i][firstPositionY].getWordMultiplier(); //multiply by word multipliers if there are any, otherwise by 1
		}
		if (word.isBingo())
			score+=50;
		return score;

	}



	public void increasePlayerScore (Word word,Player player) {
		int score = this.returnScore(word);
		player.increaseScore(score);
		player.increaseScore(this.parallelScore(word));
		//System.out.println("Great word choice,"+player.getName()+"! Your worth is "+score);
	}



	}





	public void challengeWord (Word word, Player player2) {
		int firstPositionX = word.getRow();
		int firstPositionY = word.getColumn();
		//substracting score of bad word in other player than is used in the round
		int score = this.returnScore(word);
		player2.substractScore(score); //another player, not current player
		//clearing board from bad word

			for (int i = 0; i < word.getLength(); i++) {
				squares[firstPositionX][firstPositionY].removeTile(); //remove placement
				if (word.isVertical())
					firstPositionX++;
				else
					firstPositionY++;

			}
	}