import java.util.*;

public class TrieTree {

    static List<String> PermutedWordsList;

    // Trie Node holding children and character information
    static class TrieNode {
        char c;
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        boolean isLeaf;

        public TrieNode() {
        }

        public TrieNode(char c) {
            this.c = c;
            this.isLeaf=false;
        }
    }

    static TrieNode root;

    static void insertIntoTree(String WordToAdd) {
        HashMap<Character, TrieNode> children = root.children;

        for (int i = 0; i < WordToAdd.length(); i++) {
            char c = WordToAdd.charAt(i);

            TrieNode Node;
            if (children.containsKey(c)) {
                Node = children.get(c);
            } else {
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
    static void DisplayWords(TrieNode CurrentNode, String Prefix) {

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
    static void DisplayWordsOnDepth(TrieNode CurrentNode,int NodeDepthValue,int DesiredDepthValue, String Prefix) {

        if (DesiredDepthValue==NodeDepthValue) {
            System.out.println(Prefix);
        } else {
            HashMap<Character, TrieNode> children = CurrentNode.children;

            for (Map.Entry mapElement : children.entrySet()) {
                String key = String.valueOf(mapElement.getKey());
                TrieNode ChildNode = (TrieNode) mapElement.getValue();
                DisplayWordsOnDepth(ChildNode,NodeDepthValue+1,DesiredDepthValue,Prefix+key);
            }
        }
    }

    // Wrapper function to call in permutations
    static void CreatePermutation(String givenString) {
        Permute("", givenString);
    }

    // Recursive function to call for permuting all possible combinations of a word
    static void Permute(String prefix, String providedString) {
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

    public static void main(String[] args) {
        root = new TrieNode();
        PermutedWordsList = new ArrayList<String>();
        CreatePermutation("abc");

//        PermutedWordsList.forEach(eachPermutation-> System.out.println(eachPermutation));
        PermutedWordsList.forEach(eachPermutation -> insertIntoTree(eachPermutation));
//        DisplayWords(root,"");
//        DisplayWordsOnDepth(root,0,3,"");
    }


}
