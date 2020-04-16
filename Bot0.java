import java.util.ArrayList;
import java.util.HashMap;

public class Bot0 implements BotAPI
{

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the game objects
    // It may only inspect the state of the board and the player objects

    private PlayerAPI me;
    private OpponentAPI opponent;
    private BoardAPI board;
    private UserInterfaceAPI info;
    private DictionaryAPI dictionary;
    private int turnCount;
    private String normalFrame;
    private ArrayList<String> permutations;
    private String permutation;
    private String wordString;
    private ArrayList<String> foundDicMatches;
    private String command;
    private Word chosenDicWordForPlacement;
    private int maxLength;

    //private float vowelCount;
    Bot0 (PlayerAPI me, OpponentAPI opponent, BoardAPI board, UserInterfaceAPI ui, DictionaryAPI dictionary) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.info = ui;
        this.dictionary = dictionary;
        this.turnCount = 0;
        this.normalFrame = "";
        unchrisIt();
        this.permutations = new ArrayList<String>();
        this.permutation = "";
        this.wordString = "";
        this.foundDicMatches = new ArrayList<String>();
        this.command = "";
        this.maxLength = 7;
        this.chosenDicWordForPlacement = new Word(7,7,true, normalFrame.toString().substring(0,2),"");


        //   this.vowelCount = 0;
    }

    private void unchrisIt() {
        String chris =me.getFrameAsString().toLowerCase();// this.getFrameAsString();//
        String antichris = "";
        for (int i = 1; i < 20; i += 3)
            antichris += chris.charAt(i);
        this.normalFrame = antichris;//this.frame.toString();

    }

    private int findFirst() { //if we're trying to find first word's letter combinations
        float vowelCount = this.countVowels();
        int maxFrameTryFirst = 7;
        if (vowelCount == 0)
            maxFrameTryFirst = 3;
        if (vowelCount == 1)
            maxFrameTryFirst = 5;
        if (vowelCount == 6)
            maxFrameTryFirst = 3;
        if (vowelCount == 5)
            maxFrameTryFirst = 4;
        // dictionary.
        //this.regEx
        return maxFrameTryFirst;

    }

public ArrayList <String> bruteForce (int wordSize){
       ArrayList <String> placedLetters = new ArrayList<String>();
       String permutation = "";
        int [] iterators = new int[wordSize];
        for (int i=0; i<iterators.length; i++)
            iterators[i]=0;
//~~~~~~~~~~~~~~~~~~

        while(iterators[0]<7){
            if(wordSize==1)
                placedLetters.add(new String(String.valueOf(this.normalFrame.charAt(iterators[0]))));
            else
            while(iterators[1]<7){
                if(iterators[0]==iterators[1])
                    iterators[1]++;
                if(wordSize==2) {
                    for(int i=0; i<wordSize;i++)
                        permutation+=this.normalFrame.charAt(iterators[i]);

                    placedLetters.add(permutation);
                    permutation= ""; //cleans it
                } else
                while(iterators[2]<7){
                     if(iterators[2]==iterators[0]||iterators[2]==iterators[1])
                         iterators[2]++;
                    if(wordSize==3){
                        for(int i=0; i<wordSize;i++)
                            permutation+=this.normalFrame.charAt(iterators[i]);

                    placedLetters.add(permutation);
                    permutation= "";} //cleans it
                       else
                    while(iterators[3]<7){
                        if(iterators[3]==iterators[0]||iterators[3]==iterators[1]||iterators[3]==iterators[2])
                             iterators[3]++;
                        if(wordSize==4){
                            for(int i=0; i<wordSize;i++)
                                permutation+=this.normalFrame.charAt(iterators[i]);

                            placedLetters.add(permutation);
                            permutation= "";}
                        else
                        while(iterators[4]<7){
                            if(iterators[4]==iterators[0]||iterators[4]==iterators[1]||iterators[4]==iterators[2]||iterators[4]==iterators[3])
                                iterators[4]++;
                            if(wordSize==5){
                                for(int i=0; i<wordSize; i++)
                                    permutation+=this.normalFrame.charAt(iterators[i]);

                                placedLetters.add(permutation);
                                permutation= "";}
                            else
                            while(iterators[5]<7){
                                if(iterators[5]==iterators[0]||iterators[5]==iterators[1]||iterators[5]==iterators[2]||iterators[5]==iterators[3]||iterators[5]==iterators[4])
                                    iterators[5]++;
                                if(wordSize==6){
                                    for(int i=0; i<wordSize;i++)
                                        permutation+=this.normalFrame.charAt(iterators[i]);

                                    placedLetters.add(permutation);
                                    permutation= "";}   else
                                while(iterators[6]<7) {
                                    if (iterators[6] == iterators[0] || iterators[6] == iterators[1] || iterators[6] == iterators[2] || iterators[6] == iterators[3] || iterators[6] == iterators[4] || iterators[6] == iterators[5])
                                        iterators[6]++;
                                    {
                                        for(int i=0; i<wordSize;i++)
                                            permutation+=this.normalFrame.charAt(iterators[i]);

                                        placedLetters.add(permutation);
                                        permutation= "";}
                                    iterators[6]++;                        }
                                iterators[5]++;                    }
                            iterators[4]++;               }
                        iterators[3]++;             }
                    iterators[2]++;         }
                iterators[1]++;     }
      iterators[0]++;  }
        return placedLetters;
}


    //use after updating unwrapped frame
    private Word generateWords(){
      chosenDicWordForPlacement =new Word(7,7,true, this.permutations.get(2),"");
      return chosenDicWordForPlacement;

    }

    //precondition: use after regex search in dictionary
    private boolean isFrameWeak() {
        //some algorithm for knowing if frame is unfortunate
        //for now, if no words found in dictionary, then we assume frame is weak
        float vowelCount;
        if (this.foundDicMatches.size() > this.permutations.size() / 7 && turnCount > 2) //adjustable strength of frame vs expectation- how suitable frame for existing matches, eg. 1/7th of possible quares can make words of
            return false;
        if (this.foundDicMatches.isEmpty()) {
            return true;
        }
        vowelCount = this.countVowels();
        if ((turnCount < 2 && (vowelCount == 0 || vowelCount == 7)) || (vowelCount == 1 && normalFrame.contains("y")))
            return true;
        else return false;

    } //if(this.isFrameWeak()) command = "EXCHANGE"+" "+this.normalFrame;

    //precondition: use after regEx creation/search on Board
    //happens before isFrame weak, cause it's before dic search
    private boolean shouldPass() {
        boolean isPass = false;


        if (permutations.isEmpty())
            isPass = true;

        return isPass;

    }
//prototype
public /*Coordinates**/ String[][] getLegalStart(/*Frame frame, Word word*/)
{   ArrayList <Coordinates> legalTwos = new ArrayList<Coordinates>();
    Frame frame = new Frame();
    ArrayList<Tile> lettersIntoTiles = new ArrayList<Tile> ();
    unchrisIt();
    String fullWord = "";
    for(int i=0; i<this.normalFrame.length(); i++)
        lettersIntoTiles.add(new Tile (this.normalFrame.toUpperCase().charAt(i)));
    ArrayList <Integer> validWordLengthsHere = new ArrayList<Integer>();
    String[][] coordinatesRC = new String[15][15];

    ; //later- possible max length of word, horizontally
    int counter =0;
    Coordinates legalStart = new Coordinates(69,69); //= new Coordinates(7,7);
   //horizontal check
    for(int l=2;l<16;l++) //what's the length of searched word for legal issues
    for (int i=0; i<15;i++) //horiontal all rows
        for(int j=0,f=0;j<15-l+1;j++){ //vertical cut end of some columns

            for(int w=0;w<l;w++)//precondition: hor, change for ver
                if(board.getSquareCopy(i,j+w).isOccupied())
                    fullWord+=board.getSquareCopy(i,j+w).getTile().getLetter();
                else if (f<7){

                    fullWord += normalFrame.charAt(f);
                    f++;
                }
                else
                    fullWord+="XVX";


            if(board.isLegalPlay(frame, new Word(i,j,true, fullWord)))
            { this.command+="I'm gay 1920 meaning";
                coordinatesRC[i][j]+= l+","; //word.length()
            }
        f = 0; //when finished creating and checking word legality, there is 0 used tiles in Frame again
        }
    return coordinatesRC;
}

private HashMap<Coordinates,ArrayList<Integer>> parseLegalLengthsAt()
{
   String [][] lengthsStartingHere=  this.getLegalStart(); //Strings: "" "3"  "347"
    String tempString = "";
    int eachLength=0;
    HashMap<Coordinates,ArrayList<Integer>> legalLenghts=new HashMap<Coordinates,ArrayList<Integer>>();
  //
    Integer currLen;
    ArrayList<Integer> allLengthsHere = new ArrayList<Integer>();
   for(int i=0;i<15;i++)
       for(int j=0;j<15;j++)
           /*if(lengthsStartingHere[i][j].length()>0)*/
       {  eachLength=0;
               while (eachLength < lengthsStartingHere[i][j].length()-1) {
                   while (lengthsStartingHere[i][j].charAt(eachLength) != ',') {
                       tempString += lengthsStartingHere[i][j].charAt(eachLength); //build each string number till comma

                       currLen = Integer.parseInt(tempString);
                       allLengthsHere.add(currLen);
                       tempString = "";
                       eachLength++;
                   }
                   if (lengthsStartingHere[i][j].charAt(eachLength) == ',')
                       eachLength++;
               }//allLengthsHere at one coordinate created
             legalLenghts.put(new Coordinates(i,j),allLengthsHere);
               allLengthsHere.clear();
       }
  return legalLenghts;
}
//yooho! we can save it, push to github and call it a day great plus you seen my googl



    /////////////////////////////////////////////////////////////////////////////////////
    private String build_command_startPlace(Word chosenDicWordForPlacement)
    { //local var
        //find where is the start
        String command= "";
        int hor = chosenDicWordForPlacement.getFirstRow();
        int ver = chosenDicWordForPlacement.getFirstColumn(); //ver is letter
        hor++; //different index in real game
        char vertical = (char) ((int) 'A' + ver);
        command += vertical;
        command += hor;
        //eg. "F1"
        return command;
    }

    private String build_command_orientation(Word chosenDicWordForPlacement) {
        String command= "";
        command += " ";
        if (chosenDicWordForPlacement.isHorizontal())
            command += "A"; //hor
        else
            command += "D"; //ver
        return command;
    }



        private String build_command_correct_word(Word chosenDicWordForPlacement){
        //if no blanks
            String command= "";
        command+=" ";
        command+= chosenDicWordForPlacement.getLetters();
            return command;

        }




    public String build_command(Word chosenDicWordForPlacement) {
        String command= "";
     command+=  this.build_command_startPlace(chosenDicWordForPlacement);
     command+=   this.build_command_orientation(chosenDicWordForPlacement);
     command+=  this.build_command_correct_word(chosenDicWordForPlacement);
     command+=   this.build_command_if_blank(chosenDicWordForPlacement);
     return command;

    }




    //use at the very end for no conflicts, if earlier be careful about conflicts
    //maybe hopefully not needed but possible to override both two
    private String build_command_if_blank(Word chosenDicWordForPlacement) {
        String command= "";
        String blankFilling = "";
        String designated = chosenDicWordForPlacement.getDesignatedLetters();
        String withBlanks = chosenDicWordForPlacement.getLetters();
        for(int i =0; i<designated.length();i++)
            if(designated.charAt(i)!=withBlanks.charAt(i))
                blankFilling+=designated.charAt(i);

        if(blankFilling.length()>0)
        {    command+=" ";
            command += blankFilling;}
        return command;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private float countVowels() {
        char ch;
        int count = 0;
        for (int i = 0; i < this.normalFrame.length(); i++) {
            ch = normalFrame.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'y')
                count += 1;
            else if (ch == '_')
                count += 1 / 3;

        }
        return count;
    }


    public String getCommand() {
        // Add your code here to input your commands
        // Your code must give the command NAME <botname> at the start of the game
        String command = "";
        //  command = "CHALLENGE";
        unchrisIt();
        this.permutations.addAll(this.bruteForce(3));
        generateWords();
        switch (turnCount) {

            case 0:

                command = "NAME CrispyChris";
                command= this.build_command(this.generateWords());
                command+=this.getLegalStart()+"  ";//.getRow()+" "+this.getLegalStart().getCol();
                command+=this.command;
                break;
       /*     case 1:

                this.regEx = "[" + this.normalFrame + "]";
                this.regEx+= this.countVowels();
                command = "H8 A " + this.regEx;

                this.maxLength = this.findFirst();
                for (int i=0; i<this.maxLength; i++)
                  command+=  this.normalFrame.charAt(i);

                //    command = "PASS";
                break;*/
            case 1:

                command+= this.build_command(this.generateWords());

                //command = "H8 D " + board.getSquareCopy(7, 7).getTile().getLetter() + this.normalFrame.charAt(0);
                break;
            case 2:

               // command+= this.build_command(this.generateWords());
                break;
            case 3:
                command=  this.build_command(this.generateWords());
                //     command = "POOL";
                break;
            default:
                command=  this.build_command(this.generateWords());
                break;
        }
        turnCount++;
        return command;
    }

    public static void main(String[] args) {

    }

}

