import java.util.*;

class TrieTree {

    static List<String> PermutedWordsList;
    static ArrayList<String> LeafNodeArrayList;
    static ArrayList<String> CharAtDepthArrayList;

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

    // Function to add values of lead nodes to an array
    static void AddLeafNodeStringToArray(TrieNode CurrentNode, String Prefix) {

        if (CurrentNode.isLeaf == true) {
            LeafNodeArrayList.add(Prefix);
        } else {
            HashMap<Character, TrieNode> children = CurrentNode.children;

            for (Map.Entry mapElement : children.entrySet()) {
                String key = String.valueOf(mapElement.getKey());
                TrieNode ChildNode = (TrieNode) mapElement.getValue();
                AddLeafNodeStringToArray(ChildNode,Prefix+key);
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

    // Function to add words to array list at a certain depth
    static void AddWordsToArrayOnDepth(TrieNode CurrentNode,int NodeDepthValue,int DesiredDepthValue, String Prefix) {

        if (DesiredDepthValue==NodeDepthValue) {
            CharAtDepthArrayList.add(Prefix);
        } else {
            HashMap<Character, TrieNode> children = CurrentNode.children;

            for (Map.Entry mapElement : children.entrySet()) {
                String key = String.valueOf(mapElement.getKey());
                TrieNode ChildNode = (TrieNode) mapElement.getValue();
                AddWordsToArrayOnDepth(ChildNode,NodeDepthValue+1,DesiredDepthValue,Prefix+key);
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

    TrieTree(){
        root = new TrieNode();
        PermutedWordsList = new ArrayList<String>();
        LeafNodeArrayList = new ArrayList<String>();
        CharAtDepthArrayList = new ArrayList<String>();
    }

    public void SetupTree(String GivenWord) {
        CreatePermutation(GivenWord);
        PermutedWordsList.forEach(eachPermutation -> insertIntoTree(eachPermutation));
//        PermutedWordsList.forEach(eachPermutation-> System.out.println(eachPermutation));
    }

    public void DisplayAllWords(){
        DisplayWords(root,"");
    }

    public void DisplayWordAtDepth(int depth){
        DisplayWordsOnDepth(root,0,depth,"");
    }

    public ArrayList<String> GetArrayListLeafNodes(){
        LeafNodeArrayList.clear();
        AddLeafNodeStringToArray(root,"");
        return LeafNodeArrayList;
    }

    public ArrayList<String> GetArrayListAtDepth(int depth){
        CharAtDepthArrayList.clear();
        AddWordsToArrayOnDepth(root,0,depth,"");
        return CharAtDepthArrayList;
    }

}


//class HelloWorld {
//    public static void main(String[] args) {
//        TrieTree MyTree= new TrieTree();
//        MyTree.SetupTree("john");
//        ArrayList<String> LeafNodeWords = MyTree.GetArrayListLeafNodes();
//        ArrayList<String> NodeWordsAtDepth = MyTree.GetArrayListAtDepth(3);
//
//        // LeafNodeWords.forEach((str) -> System.out.println(str));
//        // NodeWordsAtDepth.forEach((str) -> System.out.println(str));
//        // MyTree.DisplayAllWords();
//        // MyTree.DisplayWordAtDepth(1);
//    }
//}

