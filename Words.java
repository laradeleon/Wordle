import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Words{

    private static ArrayList<String> allWords = new ArrayList<String>(); //To store all of the possible 5-letter word answers

    public static void main(String[] args){
        loadWords();
    }

/**
 * Reads in a text-file that contains a list of common 5-letter words.
 * These words are added to a list which will act as a pool of potential answers for the game.
 */
    public static void loadWords(){
        try{

            //Reading the text file
            File textFile = new File("five-letter-words.txt");
            FileReader fileReader = new FileReader(textFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String word;

            //Adding all of the 5-letter words from the text file onto the array list
            while((word = bufferedReader.readLine()) != null){
                allWords.add(word);
            }

            bufferedReader.close();
            
        }

        catch(IOException exception){
            System.out.println("An error has occurred when loading the words from the file: " + exception.getMessage());
        }
    }
 
}