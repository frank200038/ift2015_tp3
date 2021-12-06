import java.lang.reflect.Array;
import java.util.*;

public class Dictionary {

    private static SortedMap<String,Word> words = new TreeMap<>();
    private static Trie trieWords = new Trie();
    private static Stack<String> history = new Stack<>();

    private static ArrayList<Word> searchWord(String word){
        String resultPrefix = trieWords.search(word.toLowerCase(Locale.ROOT));
        return searchWordInMap(resultPrefix);
    }

    private static ArrayList<Word> searchWordInMap(String prefix){
        ArrayList<Word> results = new ArrayList<>();

        for(String current : words.keySet()){
            if (current.startsWith(prefix)){
                results.add(words.get(current));
            }
        }

        return results;
    }

    private static int checkContain(String word, ArrayList<Word> words){
        for (int i = 0;i<=words.size()-1;i++){
            if(word.equals(words.get(i).getEnglish()))
                return i;
        }
        return -1;
    }

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
        System.out.println("4) Exit");

        int option = sc.nextInt();
        sc.nextLine();
        if(option >= 1 && option <= 4){
            switch (option) {
                case 1 -> {
                    System.out.println("Enter the word:");
                    String word = sc.nextLine();

                    ArrayList<Word> words = searchWord(word);

                    // If there is one search result, or the search result contain the word.
                    // Return the exact match
                    int index;
                    if ((index = checkContain(word, words)) != -1) {
                        Word exactMatch = words.get(index);
                        System.out.println(exactMatch);
                        history.push(exactMatch.getEnglish());

                    }
                    // Exact match doesn't exist. But similar words exist
                    else if (words.size() > 1) {

                        // Display top-10 results. Or if results are less than 10, display all.
                        System.out.println("Exact match didn't find. Do you mean (" + Math.min(words.size(), 10) + " result):");
                        for (int i = 0; i <= Math.min(words.size() - 1, 10); i++) {
                            System.out.println(words.get(i).getEnglish());
                        }
                        if (words.size() > 10) {
                            System.out.println(words.size() - 10 + " more words to show");
                        }
                    }
                }
                case 2 -> {
                    if (history.size() != 0) {
                        System.out.println("Words History: ");

                        for (int i = history.size() - 1; i >= 0; i--) {
                            System.out.println(history.get(i));
                        }

                    } else
                        System.out.println("No words history");
                }
                case 3 -> {
                    System.out.println("Enter the word:");
                    String word = sc.nextLine();

                    ArrayList<Word> words = searchWord(word);

                    int index;
                    if ((index = checkContain(word, words)) != -1) {
                        Word exactMatch = words.get(index);
                        System.out.println(exactMatch.getFrench());
                    } else {
                        System.out.println("The word cannot be translated because it does not exist!");
                    }
                }
                case 4 -> System.exit(0);
            }
        } else
            System.out.println("Invalid option");

        menu();
    }
}
