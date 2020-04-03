import java.util.ArrayList;

public class Player {

    private int id;
    private String name;
    private int score;
    private Frame frame;

    Player(int id)  {
        this.id = id;
        name = "";
        score = 0;
        frame = new Frame();
    }

    public int getPrintableId() {
        return id+1;
    }

    public void setName(String text) {
        name = text;
    }

    public String getName() {
        return name;
    }

    public void addPoints(int increment) {
        score += increment;
    }

    public void substractPoints(int increment) {
        score -= increment;
    }

    public int getScore() {
        return score;
    }

    public Frame getFrame() {
        return frame;
    }

    public void adjustScore() {
        int unused = 0;
        ArrayList<Tile> tiles = frame.getTiles();
        for (Tile tile : tiles) {
            unused = unused + tile.getValue();
        }
        score = score - unused;
    }

    public String toString() {
        return "Player " + getPrintableId();
    }

}
