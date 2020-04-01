import javafx.scene.paint.Color;

enum Type {
	NORMAL(1, 1, "  ", Color.LIGHTYELLOW),
	DOUBLE_WORD(1, 2, "2W", Color.LIGHTPINK),
	TRIPLE_WORD(1, 3, "3W", Color.RED),
	DOUBLE_LETTER(2, 1, "2L", Color.LIGHTBLUE),
	TRIPPLE_LETTER(3, 1, "3L", Color.BLUE),
	MIDDLE(1, 1, "*", Color.HOTPINK);

	private final int WordMultiplier;
	private final int LetterMultiplier;
	private final String identifier;
	private final Color colour;
	Type(int LetterMultiplier, int WordMultiplier, String identifier, Color colour){
		this.LetterMultiplier = LetterMultiplier;
		this.WordMultiplier = WordMultiplier;
		this.identifier = identifier;
		this.colour = colour;
	}

	public int getWordMultiplier() {
		return WordMultiplier;
	}

	public int getLetterMultiplier() {
		return LetterMultiplier;
	}

	public Color getColour(){return this.colour;}

	public String toString() {
		return identifier;
	}
}
