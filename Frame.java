import java.util.ArrayList;

public class Frame {

	private static final int MAX_TILES = 7;
	public static final int EXCHANGE_NOT_AVAILABLE = 0;
	public static final int EXCHANGE_NOT_ENOUGH_IN_POOL = 1;

	private ArrayList<Tile> frame;
	int errorCode;

	Frame() {
		frame = new ArrayList<>();
	}

	public int size() {
		return(frame.size());
	}

	public boolean isEmpty() {
		return frame.isEmpty();
	}

	public boolean isFull() {
		return this.size() == MAX_TILES;
	}

	public boolean isAvailable(String letters) {
		boolean found = true;
		if (letters.length() > this.size()) {
			found = false;
		}
		else {
			ArrayList<Tile> copyTiles = new ArrayList<>(frame);
			for (int i=0; i<letters.length() && found; i++) {
				Tile tileSought = new Tile(letters.charAt(i));
				if (copyTiles.contains(tileSought)) {
					copyTiles.remove(tileSought);
				}
				else {
					found = false;
				}
			}
		}
		return found;
	}

	
	public boolean isAvailable (char letter) {
	boolean found = false;
	if (this.size() > 0) 
		{
			Tile tileSought = new Tile(letter);
			if (frame.contains(tileSought))
				found = true;	
		}
	
	return found;
}
	
	// remove precondition: isAvailable(letters) is true
	public void removeTile(Tile tile) {
		frame.remove(tile);
	}

	// remove precondition: isAvailable(letters) is true
	public void removeTiles(ArrayList<Tile> tiles) {
		for (Tile tile : tiles) {
			frame.remove(tile);
		}
	}

	// getTile precondition: isAvailable(letters) is true
	public Tile getTile(Character letter) {
		int index = frame.indexOf(new Tile(letter));
		return frame.get(index);
	}

	// remove precondition: isAvailable(letters) is true
	public ArrayList<Tile> getTiles(String letters) {
		ArrayList<Tile> tiles = new ArrayList<>();
		for (int i=0; i<letters.length(); i++) {
			tiles.add(getTile(letters.charAt(i)));
		}
		return tiles;
	}

	public ArrayList<Tile> getTiles() {
		return frame;
	}

	public Tile accessByIndex (int i) throws Exception
	{
		if (i<frame.size()&&i>-1)
			return frame.get(i);
		else
			throw new Exception("Outside of scope of this frame");
	}


	//allows access to single letter in the frame
	public Tile accessByLetter (char checkedCharacter) {
		if (this.isAvailable(checkedCharacter))
			return frame.get(atWhichIndex(checkedCharacter));	//frame.get(accessTileByIndex(atWhichIndex(checkedCharacter)));
		else return null;
	}


	public int atWhichIndex (char checkedLetter) {
		int whichIndex = -1;
		if (!frame.isEmpty())
			if (this.isAvailable(checkedLetter)){
				for (int i=0; i<frame.size(); i++) {
					if (frame.get(i).getLetter() == checkedLetter)
					{whichIndex = i; return whichIndex;}
				} //if not in, in previous version, return -1
			}

		return whichIndex;
		//return checkedLetter;
	}

	public void refill(Pool pool) {
		int numTilesToDraw = MAX_TILES - frame.size();
		ArrayList<Tile> draw = pool.drawTiles(numTilesToDraw);
		frame.addAll(draw);
	}

	public boolean isLegalExchange(Pool pool, String letters) {
		boolean isLegal;
		if (!isAvailable(letters)) {
			errorCode = EXCHANGE_NOT_AVAILABLE;
			isLegal = false;
		} else if (pool.size() < letters.length()) {
			errorCode = EXCHANGE_NOT_ENOUGH_IN_POOL;
			isLegal = false;
		} else {
			isLegal = true;
		}
		return isLegal;
	}

	public int getErrorCode() {
		return errorCode;
	}

	// exchange precondition: isLegalExchange(pool, letters) is true
	public void exchange(Pool pool, String letters) {
		ArrayList<Tile> tilesToExchange = getTiles(letters);
		pool.addTiles(tilesToExchange);
		removeTiles(tilesToExchange);
		refill(pool);
	}

	@Override
	public String toString() {
		return frame.toString();
	}

}
