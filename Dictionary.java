import java.io.*;
import java.util.*;

public class Dictionary
{
    Set<String> dictionary = new HashSet<>();
    public ArrayList<String> dictionar = new ArrayList<String>();
    int [] bookmarks = new int [27];//26 letters and 1 blank
    boolean match;

    public Dictionary(){
        dictionary = new HashSet<>();
        dictionar = new ArrayList<String>();
        bookmarks = new int [27];//26 letters and 1 blank
        match = false;
    }


    public ArrayList<String> getDictionar()
    {
        return dictionar;
    }
    public void findbookmark()
    { //pecondition: use after writing readtoarralist
        bookmarks[0] = 0;
        int bookIndex=1;
        for (int i =1; i<dictionar.size();i++)
            if (dictionar.get(i).charAt(0)!=dictionar.get(i-1).charAt(0))
            {bookmarks[bookIndex] = i+1;
                bookIndex++;}
    }


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
                dictionar.add(currentline);
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

    public void contain( UserInterface ui,Board board)
    {
        String word = board.lastWord.getLetters();
       ui.notInDictionary= dictionar.contains(word);
    }
    //I'm still waiting for my phone to charge
    public boolean dictionarysearch(String soughtWord)
    {
        for (int i = 0; i<dictionar.size(); i++) //that's simpler, linear search for now- but on ArrayList
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
    public boolean challengedToRemove(String soughtWord)
    {
        this.dictionarysearch(soughtWord);
        return match ? false:true;
    }
    public static void main (String[]args)
    {
        Dictionary  dick = new Dictionary();
        dick.readToArrayList();
        //dick.dictionarysearch("witc"); is your phone on just starting to charge enough now
        if(dick.challengedToRemove("dog")==false)
            System.out.println(" dog is word, valid  word, not challenge edto remove, and should stay");
    }
}