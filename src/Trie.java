import java.util.Collection;
import java.util.HashMap;

public class Trie {

    private class TrieNode{
        private char value;
        private HashMap<Character,TrieNode> children;
        private boolean isEnd;

        public TrieNode(char ch){
            value = ch;
            children = new HashMap<>();
            isEnd = false;
        }

        public HashMap<Character, TrieNode> getChildren() {
            return children;
        }

        public char getValue() {
            return value;
        }

        public void setIsEnd(boolean val){
            isEnd = val;
        }

        public boolean isEnd() {
            return isEnd;
        }
    }


    private TrieNode root;

    public Trie(){
        root = new TrieNode((char) 0);
    }

    public void insert(String word){
        int length = word.length();
        TrieNode walk = root;

        for (int level = 0; level < length;level++){
            HashMap<Character,TrieNode> child = walk.getChildren();
            char current = word.charAt(level);

            if(child.containsKey(current))
                walk = child.get(current);
            else{
                TrieNode newNode = new TrieNode(current);
                child.put(current,newNode);
                walk = newNode;
            }
        }

        walk.setIsEnd(true);
    }

    public void insertWords (Collection<String> words){
        for(String word:words){
            insert(word);
        }
    }

    private String searchHelper(String word){
        if(word.length() == 0){
            return "";
        }

        TrieNode walk = root;
        int index = 0;
        String prefix ="";

        while(walk.getChildren().size() !=0 && index <= word.length() - 1){
            boolean found = false;
            HashMap<Character,TrieNode> child = walk.getChildren();
            for (char current : child.keySet()){
                if(current == word.charAt(index)){
                    walk = child.get(current);
                    found = true;
                    index++;
                    prefix = word.substring(0,index);
                    break;
                }
            }

            if (!found){
                break;
            }
        }

        return prefix;
    }

    public String search(String word){
        return searchHelper(word);
    }



}
