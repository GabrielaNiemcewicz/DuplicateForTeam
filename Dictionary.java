import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.*;
import java.util.*;

public class Dictionary
{
    Set<String> dictionary = new HashSet<>();
    public ArrayList<String> dictionar = new ArrayList<String>();
    int [] bookmarks = new int [27];
    boolean match;
    String soughtWord;

    public Dictionary()
    {
        dictionary = new HashSet<>();
        dictionar = new ArrayList<String>();
        bookmarks = new int [27];//26 letters and 1 blank
        match = false;
        this.readToArrayList();
        this.findbookmark();

    }
    void getWord(String soughtWord){
        this.soughtWord = soughtWord;

    }//gimme a minute, I'll look at sth in ui
//so, which do we have? we have the bineyseach you wrote for me i take pics and send it to you okay
    public ArrayList<String> getDictionar()
    {
        return dictionar;
    }

    public void findbookmark()
    { //pecondition: use after writing readtoarralist
        this.bookmarks[0] = 0;
        int bookIndex=1;
        for (int i =1; i<dictionar.size();i++)
            if (dictionar.get(i).charAt(0)!=dictionar.get(i-1).charAt(0))
            {
                this.bookmarks[bookIndex] = i+1;
                bookIndex++;}
        this.bookmarks[bookIndex] = 267750;


    }
  /*  public boolean setInDictionary()
    {
        String soughtWord = board.lastWord.getLetters().toLowerCase();
        boolean found = this.dictionary.contain(soughtWord);
        return found;
    }*/


    public void readToArrayList ()
    {
        BufferedReader objReader = null;
        try
        {
            String currentline;
            File file = new File("sowpods.txt");
            BufferedReader sin = new BufferedReader(new FileReader(file));
            if(!file.exists())
                System.out.println("file doesn't exist");
            while ((currentline = sin.readLine()) != null)
            {
                this.dictionar.add(currentline.trim().toLowerCase());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (objReader != null)
                {
                    objReader.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public Set<String> getDictionary()
    {
        return dictionary;
    }

    public boolean contain()
    {
        boolean isIn = dictionar.contains(this.soughtWord);
       return isIn;
    } //i want to push dictionary to git, where is file?
    //precondition:: we used getWord first
    public boolean dictionarysearch(String soughtWord)
    { // int index = (int) soughtWord.charAt(0)-'a';

   //int start = this.bookmarks[index];
   //int finish = this.bookmarks[index+1];


     //  for (int i = start; i<=finish; i++) //that's simpler, linear search for now- but on ArrayList
        for (int i=0; i<dictionar.size();i++)
            if(dictionar.get(i).equalsIgnoreCase(soughtWord)) //that's method where we can put any kind of search algorithm
            {
                return true;
               // System.out.println(soughtWord);
            }
        return false;
    }
    public boolean wasFound ()
    {
        return match;
    }


    public boolean challengedToRemove(String soughtWord) //make this interface more efficient- it's stored by getWord
    {
        this.dictionarysearch(soughtWord);
        return match ? false:true;
    }
    public static void main (String[]args)
    {
        Board board = new Board();
       Word big = new Word(8,8,true,"BIG");
        System.out.println(big.getLetters()+"word before board letters");
       board.lastWord = big;
        System.out.println(board.lastWord.getLetters()+"word in board letters");
        Dictionary  dick = new Dictionary();
      //  dick.readToArrayList();
        System.out.println(dick.dictionar.get(101)+"is part of dict");
      if(dick.dictionarysearch("abide"))
         System.out.println("SPELLED WORD NN DICT");

      //  System.out.println(dick.dictionar.contains("big"));
 /*
for(int i=0; i<dick.bookmarks.length; i++)
    System.out.println(dick.bookmarks[i]);
*/

    }
}