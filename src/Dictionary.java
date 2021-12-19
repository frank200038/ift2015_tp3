import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Dictionary {

    private static Map<String,Word> words = new TreeMap<>(); // Stores all the words
    private static Trie trieWords = new Trie(); // For finding longest prefix
    private static Stack<Word> history = new Stack<>(); // Store user history

    /**
     * Search the word. Obtain an arrayList of word that shares the same prefixes
     * @param word Word to be searched
     * @return {@code ArrayList} of {@link Word} that shares the same prefixes
     */
    private static List<Word> searchWord(String word){

        // Find the longest prefix (Lowercase by default)
        String resultPrefix = trieWords.search(word.toLowerCase(Locale.ROOT));

        // No need to search, empty prefix returns empty result
        if (resultPrefix.equals("")){
            return new ArrayList<>();
        }

        // Find the words that start by the prefix from the map
        return searchWordInMap(resultPrefix);
    }

    /**
     * Helper method to search in the {@code HashMap} words that share the same prefixes
     * @param prefix Prefix to be searched
     * @return {@code ArrayList} if {@link Word} that shares the same prefixes
     */
    private static List<Word> searchWordInMap(String prefix){
        List<Word> results = new ArrayList<>();

        // Iterate through all the words in the hashmap to find words that start with the same prefix.
        for(String current : words.keySet()){
            if (current.startsWith(prefix)){
                results.add(words.get(current));
            }
        }

        return results;
    }

    /**
     * Helper method to make sure we only store the 10 most recent search history
     * @param word Word to be stored into the history
     */
    private static void storeHistory(Word word){
        if(history.size() < 10){
            history.push(word);
        } else
        {
            history.remove(0);
            history.push(word);
        }
    }

    //Delegate the real execution menu to Menu
    public static void main(String[] args){
        CsvReader reader = new CsvReader("src/dictionary.csv",words);
        trieWords.insertWords(words.keySet());
        menu();
    }

    private static void menu(){
        Scanner sc = new Scanner(System.in);

        System.out.println("------English To French Dictionary--------");
        System.out.println("Choose one option from below\n");
        System.out.println("1) Search Word");
        System.out.println("2) Print History");
        System.out.println("3) Translate the word");
        System.out.println("4) For grading use (Choose this option for assessment purpose)");
        System.out.println("5) Exit");

        int option = sc.nextInt();
        sc.nextLine();
        if(option >= 1 && option <= 5){
            // The following menu option, besides 4, are created just for demonstration purpose.
            // For assessment please choose option 4
            switch (option) {
                case 1 -> {
                    System.out.println("Enter the word:");
                    String word = sc.nextLine();

                    Word wordExactMatch;
                    // Found an exact match of word. No need to do prefix searching
                    if ((wordExactMatch = words.get(word)) != null) {
                        System.out.println(wordExactMatch);
                        storeHistory(wordExactMatch);
                    }
                    // Exact match doesn't exist. But similar words exist
                    else {
                        List<Word> words = searchWord(word);

                        if (words.size() > 1) {

                            // Display top-10 results. Or if results are less than 10, display all results.
                            System.out.println("Exact match didn't find. Do you mean (" + Math.min(words.size(), 10) + " result):");
                            for (int i = 0; i <= Math.min(words.size() - 1, 9); i++) {
                                System.out.println(words.get(i).getEnglish());
                            }

                            // Display an extra message to show that there are more similar words
                            if (words.size() > 10) {
                                System.out.println(words.size() - 10 + " more words to show");
                            }
                        } else {
                            System.out.println("No Result");
                        }
                    }
                }
                // Display history of words that users have searched.
                case 2 -> {
                    if (history.size() != 0) {
                        System.out.println("Words History: ");
                        for(Word word : history){
                            System.out.println(word);
                            System.out.println("---------------------------------------");
                        }
                    } else
                        System.out.println("No words history");
                }
                case 3 -> {
                    System.out.println("Enter the word:");
                    String word = sc.nextLine();

                    Word wordExactMatch;

                    // We assume the word will only be translated if there is an exact match (As no specification was given)
                    // User should use Dictionary function (Menu 1) if they are not sure how to spell the word
                    if ((wordExactMatch = words.get(word)) != null) {
                        System.out.println(wordExactMatch.getEnglish());
                        System.out.println("Its french translation is: " + wordExactMatch.getFrench());
                        System.out.println("Want to know more information about this word? Use option 1 - Dictionary");
                    }else {
                        System.out.println("The word cannot be translated because it does not exist! \n" +
                                "Consider using menu option 1");
                    }
                }

                // Special menu item built just for assessment purpose
                case 4 ->{
                    System.out.println("Please enter the word (One line each). " +
                            "Enter \"$\" for the last line indicate the end. ");
                    String word;

                    List<List<Word>> wordsAll = new ArrayList<>();

                    while(! (word = sc.nextLine()).equals("$")){
                        List<Word> words = searchWord(word);
                        wordsAll.add(words);
                    }

                    // ** Store all the result lists in a separate lists. Do this for the sake of output format.
                    // Output all the numbers all at once, just like the test file ** (Just trying to follow the format)
                    for(List<Word> list : wordsAll){
                        System.out.println(list.size());
                    }

                }
                case 5 -> System.exit(0);
            }
        } else
            System.out.println("Invalid option");

        menu();
    }
}
