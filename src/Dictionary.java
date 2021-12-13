import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Dictionary {

    private static Map<String,Word> words = new TreeMap<>(); // Stores all the words
    private static Trie trieWords = new Trie(); // For finding longest prefix
    private static Stack<String> history = new Stack<>(); // Store user history

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

        for(String current : words.keySet()){
            if (current.startsWith(prefix)){
                results.add(words.get(current));
            }
        }

        return results;
    }

    /**
     * Helper method to check if among all the similar word we have the exact match
     * @param word Word to be searched for an exact match
     * @param words {@code ArrayList} of {@link Word}
     * @return the index of the exact match, or -1 if there is no exact match
     */
    private static int checkContain(String word, List<Word> words){
        for (int i = 0;i<=words.size()-1;i++){
            if(word.equals(words.get(i).getEnglish()))
                return i;
        }
        return -1;
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
        System.out.println("4) For grading use (This option will generate the same output as test file). \n \t \t \t \t \t " +
                "Choose this option for assessment purpose");
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

                    List<Word> words = searchWord(word);

                    // If the search result actually contains the word, return the exact match
                    int index;
                    if ((index = checkContain(word, words)) != -1) {
                        Word exactMatch = words.get(index);
                        System.out.println(exactMatch);
                        history.push(exactMatch.getEnglish());
                    }

                    // Exact match doesn't exist. But similar words exist
                    else if (words.size() > 1) {

                        history.push(word);
                        // Display top-10 results. Or if results are less than 10, display all results.
                        System.out.println("Exact match didn't find. Do you mean (" + Math.min(words.size(), 10) + " result):");
                        for (int i = 0; i <= Math.min(words.size() - 1, 10); i++) {
                            System.out.println(words.get(i).getEnglish());
                        }
                        if (words.size() > 10) {
                            System.out.println(words.size() - 10 + " more words to show");
                        }
                    } else{
                        System.out.println("No Result");
                    }
                }
                // Display history of words that users have searched.
                case 2 -> {
                    if (history.size() != 0) {
                        System.out.println("Words History: ");
                        for(String word : history){
                            System.out.println(word);
                        }
                    } else
                        System.out.println("No words history");
                }
                case 3 -> {
                    System.out.println("Enter the word:");
                    String word = sc.nextLine();

                    List<Word> words = searchWord(word);

                    // Only translate the word if it is an exact match~
                    // User should use Dictionary function (Menu 1) ) if they are not sure the word they want to translate
                    int index;
                    if ((index = checkContain(word, words)) != -1) {
                        Word exactMatch = words.get(index);
                        System.out.println(exactMatch.getFrench());
                    } else {
                        System.out.println("The word cannot be translated because it does not exist!");
                    }
                }
                // Special menu item built just for assessment purpose
                case 4 ->{
                    System.out.println("Please enter the word. Enter \"$\" for the last line indicate the end. ");
                    String word;

                    // Generate output test file
                    File output = new File("output.txt");
                    try {
                        FileWriter fileWriter = new FileWriter(output);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        while(! (word = sc.nextLine()).equals("$")){
                            List<Word> words = searchWord(word);
                            bufferedWriter.append(String.valueOf(words.size())).append("\n");
                        }

                        bufferedWriter.close();
                        fileWriter.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 5 -> System.exit(0);
            }
        } else
            System.out.println("Invalid option");

        menu();
    }
}
