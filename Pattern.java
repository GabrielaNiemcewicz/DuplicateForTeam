//to draw valid Scrabble Board, use  use .drawScrabble.
//To use smaller methods, Board SIZE = 15, V argument size=7. Also, 7=middle() for SIZE=15. use method. use print. use reset().
 	//A = TRIPLE WORD SCORE
	//B = DOUBLE WORD SCORE
	//C= TRIPLE LETTER SCORE
	//D= DOUBLE LETTER SCORE
	//o= regular Square

//algorithm uses 2 symmetry axes of scrabble board- one axis by divide and conquer, second by absolut= |x|
//absolut = distance of referenced mirror images of 2 Squares from pointy "peak" of V. 



public class Pattern {
private Square [][] scrabble; //Scrabble Board in chars
private int SIZE;


Pattern(Square[][] board){ //initializer
	this.SIZE = board.length;
	
	for (int i=0; i<this.SIZE; i++)
		for (int j=0; j<this.SIZE; j++)
				board[i][j] = new Square();
	
	//this.setScrabble(board);
	 
}

public void drawScrabble(Square[][] board) { //invoke this method to draw a valid Scrabble board at once
this.draw_all_V(this.middle(), board);
this.draw_X_as_VV(board);
this.draw_scattered_2L(board);
this.draw_scattered_3W(board);
}


private int middle() //middle of the board- single index
{ return middle(this.SIZE); }

private int middle(int size) //middle of V pattern- absolut here equals 0, it's "peak", "pointed part"
{ return size/2; }




private void draw_X_as_VV(Square[][] board) { //part of drawing X. X, ><, as > and <. Draws from up 0 row, down to bottom row, by going left>-right<-left>-right<-left>-right<....
	int j;
	int absolut;
	for (int i=0+1; i<this.SIZE-1; i++) //Vs don't 'touch' edges of Board
	{	absolut= java.lang.Math.abs(i-this.middle(this.SIZE));
		if (absolut==0) continue; //double assigning problem at square(middle(),middle()) solved by omitting it 
	
		//left half of the X,  V-shape. Draw >
		j=this.middle(this.SIZE)- absolut;	
		board[j][i].setType(assign_type_V(absolut));
		
		//right half of the X, of V-shape. Draw <
		j=this.SIZE-1-j;
		board[j][i].setType(assign_type_V(absolut));
		
	
	}
}




private void draw_all_V (int size, Square[][] board) {
	int j;
	int absolut;
	for (int i=(this.SIZE-size)/2+1; i<(size+this.SIZE)/2-1; i++)
		{absolut = java.lang.Math.abs(i-(this.SIZE-size)/2-this.middle(size));
		
		  j=this.middle(size)-absolut;
		  board[i][j].setType(assign_type_V(absolut)); //left
		  board[j][i].setType(assign_type_V(absolut)); //up
		
		  j=this.SIZE-1-j;
		  board[i][j].setType(assign_type_V(absolut)); //right
		  board[j][i].setType(assign_type_V(absolut)); //down
}

}

private void draw_scattered_3W (Square[][] board) {
	for (int i=0; i<this.SIZE; i+=this.middle())
		for (int j=0; j<this.SIZE; j+=this.middle())
				  board[i][j].setType(assign_type_scattered_3W(i,j));}
	
	


private void draw_scattered_2L (Square[][] board) { 
	int [] I = {0,this.SIZE-1}; //first [] => up and down border of Board, OR second [] => left and right border 
	int [] J = {this.middle(this.middle()),I[1]-this.middle(this.middle())}; //3 positions away from left and right borders
		for (int i: I) 
		for (int j: J)
		{board[i][j].setType(assign_type_scattered_2L());
		board[j][i].setType(assign_type_scattered_2L());}
}


private Type assign_type_V(int absolut) {
	//absolut for small V-pattern: 21012 and 3 more mirrors
	//absolut for X-pattern: 654321123456 and 1 mirror //no 0, because double assigning conflict at 0

	
	if (absolut<2)
		return Type.DOUBLE_LETTER;
	else if (absolut==2)
		return Type.TRIPPLE_LETTER;
	else //if (absolut<7)
		return Type.DOUBLE_WORD;
	
}

private Type assign_type_scattered_3W(int position_x, int position_y)
{
	if (position_x==this.middle() && position_y==this.middle())
		return Type.DOUBLE_LETTER;
	else
		return Type.TRIPLE_WORD; }

private Type assign_type_scattered_2L () 	
{ return Type.DOUBLE_LETTER; }

public void setScrabble(Square [][] board) {
	this.scrabble = board;
}


}

