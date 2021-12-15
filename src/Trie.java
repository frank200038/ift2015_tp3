import java.util.Collection;
import java.util.HashMap;

/**
 * Trie class is a tree structure that is used to find the longest prefixes.
 * Inspired by https://www.techiedelight.com/longest-common-prefix-given-set-strings-using-trie/,
 * {@author} Yan Zhuang, Diego Gonzalez
 */
public class Trie {

    /**
     * Inner class that represents the node of each Trie node.
     * Use {@code HashMap} to contain the current node and its children
     */
    private class TrieNode{
        private char value;
        private HashMap<Character,TrieNode> children;

        public TrieNode(char ch){
            value = ch;
            children = new HashMap<>();
        }

        public HashMap<Character, TrieNode> getChildren() {
            return children;
        }

    }


    private TrieNode root;

    //By default, the parent node of Trie is char 0
    public Trie(){
        root = new TrieNode((char) 0);
    }

    /**
     * Insert a word to the trie tree
     * @param word Word to be inserted
     */
    private void insert(String word){
        int length = word.length();
        TrieNode walk = root;

        // Make sure we stop at the end of the word
        for (int level = 0; level < length;level++){
            HashMap<Character,TrieNode> child = walk.getChildren();
            char current = word.charAt(level);

            // If the character already exists in the Trie, obtain it directly
            if(child.containsKey(current))
                walk = child.get(current); // Descend the tree

            // Otherwise, create a new Node for this new character as the child of the current node
            else{
                TrieNode newNode = new TrieNode(current);
                child.put(current,newNode);
                walk = newNode; // Descend the tree
            }
        }
    }

    /**
     * Helper method to insert a series of word
     * @param words Series of words to be inserted
     */
    public void insertWords (Collection<String> words){
        for(String word:words){
            insert(word);
        }
    }

    /**
     * Search the longest prefix of the word
     * @param word Longest prefix of this word ot be searched
     * @return Longest prefix
     */
    private String searchHelper(String word){

        // If the word length 0, no need to conduct search.
        if(word.length() == 0){
            return "";
        }

        // Start at the root
        TrieNode walk = root;
        int index = 0;
        String prefix =""; // Initial prefix is empty

        // If the current node has no children, or we exceed the length of the word.
        // No need to keep searching
        while(walk.getChildren().size() !=0 && index <= word.length() - 1){
            boolean found = false;
            HashMap<Character,TrieNode> child = walk.getChildren();

            // Check the characters of all current children.
            // If exists, means we have a matching prefix. We descend, and add the prefix to current prefix.
            for (char current : child.keySet()){
                if(current == word.charAt(index)){
                    walk = child.get(current);
                    found = true;
                    index++; // Update the index to make sure we are at the right character of the word
                    prefix = word.substring(0,index); // Update the longest prefix (so far) as well
                    break;
                }
            }

            // If not found, no need to continue, we have obtained our longest prefix.
            if (!found){
                break;
            }
        }

        return prefix;
    }

    /**
     * Delegation method to search for the prefix
     * @param word Prefix of this word will be searched
     * @return Longest prefix
     */
    public String search(String word){
        return searchHelper(word);
    }



}
