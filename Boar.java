import java.sql.SQLOutput;
import java.util.ArrayList;

public class Boar {
    Integer[] gindices_r_c = new Integer[2];
//int shouldbe = gindices_r_c[0].intValue();

    ArrayList<Integer[]> gpositions = new ArrayList<Integer[]>();
    ArrayList<Integer[]> g_start_positions = new ArrayList<Integer[]>();
    ArrayList<String> g_parallelWords = new ArrayList<String>();
    char[][] boar = new char[9][9];
    ///////////////////CLASS WORD
    String word; //was class word, needs more info
    int newRowStart, newColumnStart; //needed extra storage to record info of created word
    boolean isHor;
    //Word oneOfParallels; //if more than one, assignment changes, it's returned to arrayList in Board or score is accessed, hence local storage
    int parallelScore;
    //////////////////////iterators, walkers
    int r;
    int c;

    int size;

    Boar(String word, int row, int col, boolean isHor) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                boar[i][j] = '_';

        int index;
        //	for (int i=2; i<6; i++)
        //{
        // index = (int)( (Math.random()*3)-2);

        for (int j = 4; j < 6; j++)
            boar[6][j] = 'M';
        for (int j = 3; j < 7; j++)
            boar[j][2] = 'M';
        for (int j = 1; j < 5; j++)
            boar[4][j] = 'M';
        //}


        this.word = word;
        this.newRowStart = row;
        this.newColumnStart = col;
        this.size = word.length();
        this.isHor = isHor;

        // this.gpositions.addAll(this.findsParallel());
        // this.g_start_positions.addAll(gpositions);
        // this.findStartPW(g_start_positions);

    }

    public void place() {
        r = this.newRowStart;
        c = this.newColumnStart;

        for (int i = 0; i < word.length(); i++) {
            if (boar[r][c] == '_') {

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
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                System.out.print(boar[i][j] + " ");
                if (j == 8) System.out.println();
            }


    }

    public ArrayList<Integer[]> findsParallel() { //multiple booleans...
        Integer introw;
        Integer intcol;

        Integer[] indices_r_c = new Integer[2];
        ArrayList<Integer[]> positions = new ArrayList<Integer[]>();
        //up and down, left or right
        //on next run of loop, get r back to normal state, reset to beginning of word
        r = this.newRowStart;
        c = this.newColumnStart;
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

        if (this.isHor) {//word moves parallely in 1same row
            //row- const index
            if (this.newRowStart > 0)
                rwalker[0] = this.newRowStart - 1; //UP
            else rwalker[0] = this.newRowStart + 1;
            if (this.newRowStart < 8)
                rwalker[1] = this.newRowStart + 1; //DOWN
            else rwalker[1] = this.newRowStart - 1;
            System.out.println(rwalker[0] + " = up, lower, AND " + rwalker[1] + "down, higher");
            //columns- mobile index
            BoxHorS = Math.max(0, c);
            BoxHorF = Math.min(c + size, 8);
        } else {//this is vertical
            //column- constant index //error
            if (this.newColumnStart > 0)
                cwalker[0] = this.newColumnStart - 1; //UP
            else cwalker[0] = this.newColumnStart + 1;
            if (this.newRowStart < 8)
                cwalker[1] = this.newColumnStart + 1; //DOWN
            else cwalker[1] = this.newColumnStart - 1;

            BoxVerS = Math.max(0, r);
            BoxVerF = Math.min(r + size, 8);

        }
        if (this.isHor) {
            for (int i = BoxHorS; i < BoxHorF; i++) //testing code till now
            {
                System.out.println(boar[this.newRowStart][i] + "is each letter in first-placed word, AAAA, where NNAA is paralel info");
                if (boar[this.newRowStart][i] == 'A' && !((boar[rwalker[0]][i] == '_') && (boar[rwalker[1]][i] == '_'))) //if occ. square was existing connection, or *both* of two around are still empty
                {
                    introw = new Integer(this.newRowStart);
                    intcol = new Integer(i);
                    indices_r_c[0] = introw;
                    //   i;
                    indices_r_c[1] = intcol;
                   // boar[this.newRowStart][i] = 'n';
                    positions.add(indices_r_c);
                }
            }
        }
        if (!this.isHor) { //is Vertical
            for (int i = BoxVerS; i < BoxVerF; i++) {
                System.out.println(boar[i][this.newColumnStart] + "is each letter in first-placed word, AAAA, where ???? is paralel info");
                if (boar[i][this.newColumnStart] == 'A' && !((boar[i][cwalker[0]] == '_') && (boar[i][cwalker[1]] == '_'))) //if occ. square was existing connection, or *both* of two around are still empty
                {
                    introw = new Integer(i);
                    intcol = new Integer(this.newColumnStart);
                    indices_r_c[0] = introw;
                    indices_r_c[1] = intcol;
                    positions.add(indices_r_c);
                }
            }
        }

        this.gpositions = positions;
        return positions;

    }
    //precindition: given indices locate a square that *IS* part of parallel word, but we don't know if it's first, second or which index
    //so we look


    public ArrayList<Integer[]> findStartPW(/*ArrayList<Integer[]> gPositions*/) { //PW = parallel word
        ArrayList<Integer[]> start_positions = new ArrayList<Integer[]>();
        Integer introw;
        Integer intcol;
        Integer[] indices_r_c = new Integer[2];             /////////////////////////////////this.gindeces_r_c
        int[] startPosPW;
        startPosPW = new int[2];
        int rcontained;
        int ccontained;
        for (int i = 0; i < this.gpositions.size(); i++) //for(int i=0;i<gPositions.size(); i++)
        {
            indices_r_c = this.gpositions.get(i); /////////////////////gpositions.get(i);
            rcontained = indices_r_c[0].intValue();
            ccontained = indices_r_c[1].intValue();

            startPosPW[0] = rcontained;
            startPosPW[1] = ccontained;
            //horizontal: row is constant, col is mobile
            //vertical:   row is mobile,   col is horizontal
            if (isHor)
                while (rcontained > 0 && boar[rcontained - 1][ccontained] != '_') //while tiles 'touch' and we don't run out of board
                {
                    rcontained--;
                }
            else //isVertical
                while (ccontained > 0 && boar[rcontained][ccontained - 1] != '_') //while tiles 'touch' and we don't run out of board
                {
                    ccontained--;
                }
            introw = new Integer(rcontained);
            intcol = new Integer(ccontained);
            indices_r_c[0] = introw;
            indices_r_c[1] = intcol;
            start_positions.add(indices_r_c);
        }
        this.g_start_positions = start_positions;
        return start_positions;
    }


    public /*ArrayList<String>*/ void buildParallelWord(/*ArrayList<Integer[]> startPositions*/) {


        ArrayList<Integer[]> positions = new ArrayList<Integer[]>();
        //  Integer introw;
        //  Integer intcol;
        Integer[] indices_r_c = new Integer[2];
        int[] iterPosPW = new int[2]; //not used in previous implementation- might delete later
        int riter;
        int citer;
        ArrayList<String> parallelWords = new ArrayList<String>();


        for (int i = 0; i < this.g_start_positions.size(); i++) {  // iterPosPW = new int[]{firstRow, firstColumn};
            String pWord = "";
            indices_r_c = this.g_start_positions.get(i);
            riter = indices_r_c[0].intValue();
            citer = indices_r_c[1].intValue();

            iterPosPW[0] = riter;
            iterPosPW[1] = citer;


            //horizontal: row is constant, col is mobile
            //vertical:   row is mobile,   col is horizontal
            //parallel word transposes indices
            if (isHor) {
                while (riter < 8 && boar[riter][citer] != '_') //while tiles 'touch' and we don't run out of board
                {
                    pWord += boar[riter][citer];
                    riter++;
                }
                if (riter < 8)
                    riter--; //if we wanted to test what is last index of returned position
            } else //isVertical
            {
                while (citer < 8 && boar[riter][citer] != '_') //while tiles 'touch' and we don't run out of board
                {
                    pWord += boar[riter][citer];
                    System.out.println(boar[riter][citer]);
                    citer++;
                }
                if (citer < 8) //if we wanted to test what is last index of returned position
                    citer--;
            }
            parallelWords.add(pWord);
        }
        this.g_parallelWords.addAll(parallelWords);
        //return pWord;
    }

    public static void main(String[] args) {
        Boar boar = new Boar("AAAA", 7, 2, true); //rows 3,5 eg.
        boar.place();
        boar.findsParallel();
        //boar.findStartPW();
        //boar.buildParallelWord();

        // ArrayList<Integer[]>
        Integer[] int_g_pos = new Integer[2];
        Integer[] int_p = new Integer[2];
        Integer p1;// = new Integer();
        Integer p2;// = new Integer();

        int row_Start_PW;
        int col_Start_PW;
        int row_PW;
        int col_PW;

        for (int i = 0; i < boar.g_parallelWords.size(); i++) {
           // int_g_pos = boar.g_start_positions.get(i);//.intValue();
            //row_Start_PW = int_g_pos[0].intValue();
           // col_Start_PW = int_g_pos[1].intValue();
            System.out.println("We saved" + boar.g_parallelWords.size() + "  parallel words. Number " + i + " is: " + boar.g_parallelWords.get(0));
           // System.out.println("Its starting row index is:" + row_Start_PW + "and column is:" + col_Start_PW);

            int_p = boar.gpositions.get(i);
            row_PW = int_p[0].intValue();
            col_PW = int_p[1].intValue();

            System.out.println("Its in-word row index is:" + row_PW + "and column is:" + col_PW);


        }
            boar.display();


    }
}