import java.util.Locale;
import java.util.Objects;

/**
 * Internal representation of word which contains the english word, its french translation, meaning and its type
 */
public class Word {
    private String english,french,type,meaning;


    public Word(String line){
        String[] data = line.split(",");
        this.english = data[0].toLowerCase(Locale.ROOT); // Convert to lowercase by default for easier search
        this.french = data[1];
        this.type = data[2];
        this.meaning = data[3];
    }

    public String getEnglish(){
        return english;
    }
    public String getFrench() {return french;}

    // Redefine equals so that two words are equals if they have the same English spelling.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return english.toLowerCase(Locale.ROOT).equals(word.english.toLowerCase(Locale.ROOT));
    }

    @Override
    public String toString() {

        return "" + "English: " + english + "\n" +
                "French: " + french + "\n" +
                "Type: " + type + "\n" +
                "Meaning: " + meaning + "\n";
    }
}
