import java.util.*;
public class Trietree
{
    static List<String> PermutedWordsList;
    // Trie Node holding children and character information
    static class TrieNode {
        private char c;
        private HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        private  boolean isLeaf;

        public TrieNode() {
        }

        public TrieNode(char c) {
            this.c = c;
            this.isLeaf=false;
        }
    }
    static TrieNode root;
    public void insertIntoTree(String WordToAdd)
    {
        HashMap<Character, TrieNode> children = root.children;
        for (int i = 0; i < WordToAdd.length(); i++) {
            char c = WordToAdd.charAt(i);
            TrieNode Node;
            if (children.containsKey(c))
            {
                Node = children.get(c);
            }
            else
            {
                Node = new TrieNode(c);
                children.put(c, Node);
            }
            children = Node.children;
            //set leaf node if we've reached last letter in the word
            if (i == WordToAdd.length() - 1)
                Node.isLeaf = true;
        }
    }

    // Function to display leaf nodes
    public void DisplayWords(TrieNode CurrentNode, String Prefix)
    {  ArrayList<String> sameLengthPErmutations = new ArrayList<String>();
        if (CurrentNode.isLeaf == true) {
            System.out.println(Prefix);
        } else {
            HashMap<Character, TrieNode> children = CurrentNode.children;

            for (Map.Entry mapElement : children.entrySet()) {
                String key = String.valueOf(mapElement.getKey());
                TrieNode ChildNode = (TrieNode) mapElement.getValue();
                DisplayWords(ChildNode,Prefix+key);
            }
        }
    }
    // Function to display words till a certain depth is reached
    public void DisplayWordsOnDepth(TrieNode CurrentNode,int NodeDepthValue,int DesiredDepthValue, String Prefix)
    {
        if (DesiredDepthValue==NodeDepthValue)
        {
            System.out.println(Prefix);
        }
        else
        {
            HashMap<Character, TrieNode> children = CurrentNode.children;
            for (Map.Entry mapElement : children.entrySet())
            {
                String key = String.valueOf(mapElement.getKey());
                TrieNode ChildNode = (TrieNode) mapElement.getValue();
                DisplayWordsOnDepth(ChildNode,NodeDepthValue+1,DesiredDepthValue,Prefix+key);
            }
        }
    }

    // Wrapper function to call in permutations
    public void CreatePermutation(String givenString)
    {
        Permute("", givenString);
    }
    // Recursive function to call for permuting all possible combinations of a word
    public  void Permute(String prefix, String providedString) {
        int StringLength = providedString.length();
        if (StringLength == 0) {
            if (!PermutedWordsList.contains(prefix)) {
                PermutedWordsList.add(prefix);
            }
        } else {
            for (int i = 0; i < StringLength; i++)
                Permute(prefix + providedString.charAt(i), providedString.substring(0, i) + providedString.substring(i + 1, StringLength));
        }
    }
    public static void main(String[] args)
    {
        root = new TrieNode();
        Trietree trieTree=new Trietree();
        PermutedWordsList = new ArrayList<String>();
        trieTree.CreatePermutation("eeee");
//        PermutedWordsList.forEach(eachPermutation-> System.out.println(eachPermutation));
        PermutedWordsList.forEach(eachPermutation -> trieTree.insertIntoTree(eachPermutation));
//        DisplayWords(root,"");
        trieTree.DisplayWordsOnDepth(root,0,1,"");
    }
}