import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Square extends StackPane {
	static boolean selected;
	private Tile tile;
	private Type type;
	Rectangle r = new Rectangle(40, 40);
	Text text = new Text();

	Square(){
		setType(Type.NORMAL);
		setStyle();
	}

	private void setStyle(){
		r.setFill(type.getColour());
		r.setStroke(Color.BLACK);
		text.setFont(Font.font("verdana", FontWeight.BOLD, 14));
		text.setFill(Color.WHITE);
		setAlignment(Pos.CENTER);
		getChildren().addAll(r, text);
	}

	public void setType(Type type) {
		this.type = type;
		r.setFill(type.getColour());
		text.setText(type.toString());
	}
	public void placeTile(Tile tile){
		if (this.isEmpty())	
			this.tile = tile;
		text.setText("" + tile.getLetter());
		text.setFill(Color.BLACK);
		r.setFill(Color.valueOf("FFE98D"));
		r.setArcHeight(5);
		r.setArcWidth(5);

	}

	public void removeTile(){
		this.tile = null;
	}
	
	public char getCharacter() {
		if(tile == null)
			return ' ';
		else
			return tile.getLetter(); 
	}
	
	public int getWordMultiplier() {
		return type.getWordMultiplier();
	}
	
	public int getLetterMultiplier() {
		return type.getLetterMultiplier();
	}
	
	public boolean isOccupied() {
		return  this.tile == null ? false : true;
	}

	public int getTileValue(){
		return this.tile.getValue();
	}

	public String toString() {
		if(isEmpty())
			return type.toString();
		else
			return "" + tile.toString();
	}

	public int getPlacementScore() {

			int placementScore;
			int lm;
			int tv;
			if (this.isEmpty())
				return 0;
			else {
				lm=this.getLetterMultiplier();
				tv= this.getTileValue();
				placementScore = lm *tv;
			}
			return placementScore;
		}
	}


