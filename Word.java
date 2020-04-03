public class Word {

	private int row, column; // first letter position
	private boolean isHorizontal;  // true = horizontal, false = vertical
	private String letters;
	int score; //optional score storage for faster challenge
	boolean [] wasOccupied;

	Word(int row, int column, boolean isHorizontal, String letters) {
		this.row = row;
		this.column = column;
		this.isHorizontal = isHorizontal;
		this.letters = letters;
		this.wasOccupied = new boolean [letters.length()];
		for (int i=0; i<letters.length(); i++)
			wasOccupied[i] = true;
		//this.score=0;
	}

	// getRow pre-condition: isHorizontal is true
	public int getRow() {
		return row;
	}

	// getColumn pre-condition: isHorizonal is flase
	public int getColumn() {
		return column;
	}

	public int getFirstRow() {
		return row;
	}

	public int getLastRow() {
		if (isHorizontal) {
			return row;
		} else {
			return row + letters.length() - 1;
		}
	}

	public int getFirstColumn() {
		return column;
	}

	public int getLastColumn() {
		if (!isHorizontal) {
			return column;
		} else {
			return column + letters.length() - 1;
		}
	}

	public String getLetters() {
		return letters;
	}

	public char getLetter(int i) {
		return letters.charAt(i);
	}

	public int getLength() {
		return letters.length();
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public boolean isVertical () {
		return !isHorizontal;
	}


	public void saveScore(int score){
	this.score = score;
	}

	public void getScore(){
		return score;
}

//used for challenging- to not take out Tiles that belong to other words, not only challenged one
	public void notBelongToOtherWord(int positionFromStart){
		this.wasOccupied[positionFromStart] = false;
}


	public boolean occupiedBeforePlacement(int positionFromStart){
		return wasOccupied[positionFromStart];
}

}
