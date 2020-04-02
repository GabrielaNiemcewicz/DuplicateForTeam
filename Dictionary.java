import java.io.*;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Map;
public class Dictionary
{
    Set<String> dictionary = new HashSet<>();

    public Set<String> getDictionary()
    {
        return dictionary;
    }
    public boolean contain(String words)
    {
        return dictionary.contains(words);
    }
    public void Dictionarysearch()
    {
        BufferedReader objReader = null;
        try
        {
            String currentline;
            objReader = new BufferedReader(new FileReader(""));
            while ((currentline=objReader.readLine())!=null)
            {
                System.out.println(currentline);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if (objReader !=null)
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

    public static void main(String[] args)
    {

    }
}
