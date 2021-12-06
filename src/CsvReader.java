
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Map;


/**
* 
* @author      Yan Zhuang, Diego Gonzalez adapted from Francois Major's TSVTrackReader
* @version     %I%, %G%
* @since       1.0
*/
public class CsvReader {

    // attributes
    private String fileName;
    private FileReader reader;
    private BufferedReader bufferReader;
    private String line;

    // getter for the file name
    public String getFileName() { return this.fileName; }


    public CsvReader(String fileName, Map<String,Word> wordsList ) {
        this.fileName = fileName;

        try {
            this.reader = new FileReader( this.fileName );
        } catch( FileNotFoundException e ) {
            System.out.println(e.getMessage());
        }

        try {
            this.bufferReader = new BufferedReader( this.reader );
            int i = 0;
            // read first data line
            this.line = this.bufferReader.readLine();

            // read all tracks in wordList
            while( this.line != null ) {
                // Skip the subject line
                if(i == 0) {
                    this.line = this.bufferReader.readLine();
                    i++;
                    continue;
                }

                // parse line and add to wordList
                Word newWord = new Word(line);
                wordsList.put(newWord.getEnglish(),newWord);
                // read next line
                this.line = this.bufferReader.readLine();
            }
            this.bufferReader.close();
        } catch( IOException e ) {
                System.out.println(e.getMessage());
            }
    }

}
