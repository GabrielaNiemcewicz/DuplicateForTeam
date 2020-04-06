public class Boar
{
    char [][] boar = new char [9][9];
    ///////////////////CLASS WORD
    String word; //was class word, needs more info
    int newRowStart, newColumnStart; //needed extra storage to record info of created word
    boolean isHor= true;
    //Word oneOfParallels; //if more than one, assignment changes, it's returned to arrayList in Board or score is accessed, hence local storage
    int parallelScore;
    //////////////////////iterators, walkers
    int r;
    int c;

    int size;

    Boar(String word, int row, int col){
        for (int i= 0; i<9; i++)
            for (int j =0; j<9; j++)
                boar[i][j] = '_';

        int index;
        //	for (int i=2; i<6; i++)
        //{
        // index = (int)( (Math.random()*3)-2);
        for(int j=2;j<5; j++)
            boar[3][j] ='M';
        for(int j=3;j<4; j++)
            boar[4][j] ='M';
        for(int j=1;j<5; j++)
            boar[5][j] ='M';
        //}


        this.word = word;
        this.newRowStart = row;
        this.newColumnStart = col;
        this.size = word.length();



    }

    public void place() {
        r= this.newRowStart;
        c= this.newColumnStart;

        for (int i=0; i<word.length(); i++) {
            if (boar[r][c]=='_') {

                char letter = word.charAt(i);

                boar[r][c] = letter;

            }
            if (this.isHor) {
                c++;
            } else {
                r++;
            }
        }




    }



    public void display() {
        for (int i= 0; i<9; i++)
            for (int j =0; j<9; j++)
            {System.out.print(boar[i][j]+" ");
                if(j==8)System.out.println();
            }



    }

    public void findsParallel() { //multiple booleans...
        int back = -1;
        int next = +1;
        //up and down, left or right
        //on next run of loop, get r back to normal state, reset to beginning of word
        r = this.newRowStart;
        c = this.newColumnStart;
        //set squarewalkers _around_ the word, depending whether horizontal/vertical
        //[       ]
        //*AAAAAAAAA *
        //[        ]
        int[] rwalker = new int[2];  int[] cwalker = new int[2];
        int BoxHorS =0; int BoxHorF=0;
        int BoxVerS=0; int BoxVerF=0;

               if(this.isHor) {//word moves parallely in 1same row
            //row- const index
            if(this.newRowStart>0)
            rwalker[0] = this.newRowStart-1; //UP
            else rwalker[0] =  this.newRowStart+1;
            if(this.newRowStart<14)
            rwalker[1] = this.newRowStart+1; //DOWN
            else rwalker[1] = this.newRowStart-1;
            System.out.println(rwalker[0]+" = up, lower, AND "+rwalker[1]+"down, higher");
            //columns- mobile index
            BoxHorS = Math.max(0, c);
            BoxHorF = Math.min(c+size, 8);
        }	else
         {//this is vertical
        //column- constant index
             if(this.newColumnStart>0)
                 cwalker[0] = this.newColumnStart-1; //UP
             else cwalker[0] =  this.newColumnStart+1;
             if(this.newRowStart<14)
                 cwalker[1] = this.newColumnStart+1; //DOWN
             else cwalker[1] = this.newColumnStart-1;

        BoxVerS = Math.max(0, r);
        BoxVerF = Math.min(r+size, 8);

         }
        if(this.isHor)
          for(int i = BoxHorS; i<BoxHorF; i++) //testing code till now
            {
        if(boar[i][this.newColumnStart]=='A'&&!((boar[i][rwalker[0]]=='_') && (boar[i][rwalker[1]]=='_'))) //if occ. square was existing connection, or *both* of two around are still empty
        boar[i][this.newColumnStart] = 'N';}
			  			    // else
			  				//	for(int i = BoxVerS; i<BoxVerF; i++) //testing code till now
						      //  	for (int j=0; j<2; j++)




						        	/*		        */ /*
			        if (this.isHor) r += back;
			        else c += back;
			        for (int j = 0; j < word.length(); j++) {//for each square in line with word walk on squares

			            if (boar[r][c]!='_' && !word.occupiedBeforePlacement(j)) //empty squarewalker <=> no connection. If was occupied, connects normally, not parallely
			            {

			            else { //move mobile index
			                if (this.isHor) c++;
			                else r++;
			            } //skip them*/


    }


    public void findP() {
        r = this.newRowStart;
        c = this.newColumnStart;
        int boxTop = Math.max(r-1,0);
        int boxBottom = Math.min(r+word.length()-1, 9-1);
        int boxLeft = Math.max(c+1,0);
        int boxRight = Math.min(c+word.length()-1, 9-1);



        char around = 'S';
        for (int r=boxTop; r<=boxBottom; r++) {
            for (int c=boxLeft; c<=boxRight; c++) {
                if (boar[r][c]!='_') {
                    boar[r][c] = around;
                }
            }


        }
    }


    public int findStartParallelWord() {return 0;}


    public int findEndParalllelWord () {return 0;}

    public String buildParallelWord() {return "";}







    public static void main(String[] args)
    {
        Boar boar = new Boar("AAAA", 4,4);
        boar.place();
        boar.findsParallel();
        boar.display();
       // System.out.println(boar.newRowStart+1);

    }




}