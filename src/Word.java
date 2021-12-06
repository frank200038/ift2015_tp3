import java.util.Locale;
import java.util.Objects;

public class Word {
    private String english,french,type,meaning;


    public Word(String line){
        String[] data = line.split(",");
        this.english = data[0].toLowerCase(Locale.ROOT);
        this.french = data[1];
        this.type = data[2];
        this.meaning = data[3];
    }

    public String getEnglish(){
        return english;
    }
    public String getFrench() {return french;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return english.toLowerCase(Locale.ROOT).equals(word.english.toLowerCase(Locale.ROOT));
    }

    @Override
    public int hashCode() {
        return Objects.hash(english);
    }

    @Override
    public String toString() {

        return "" + "English: " + english + "\n" +
                "French: " + french + "\n" +
                "Type: " + type + "\n" +
                "Meaning: " + meaning + "\n";
    }
}
