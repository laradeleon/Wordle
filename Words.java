import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Words{

    private ArrayList<String> allWords = new ArrayList<String>(); //To store all of the possible 5-letter word answers

    public Words(){
        loadWords();
    }

/**
 * Reads in a text-file that contains a list of common 5-letter words.
 * These words are added to a list which will act as a pool of potential answers for the game.
 */
    public void loadWords(){
        try{

            //Reading the text file
            File textFile = new File("five-letter-words.txt");
            FileReader fileReader = new FileReader(textFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String word;

            //Adding all of the 5-letter words from the text file onto the array list
            while((word = bufferedReader.readLine()) != null){
                word = word.toLowerCase();
                allWords.add(word);
            }
            bufferedReader.close();
        }

        catch(IOException exception){
            System.out.println("An error has occurred when loading the words from the file: " + exception.getMessage());
        }
    }

/**
 * Generates a random index, and retrieves the word at that index from an array list of words
 * @return A randomly selected word from a list of common 5-letter words
 */

    public String getAnswer(){
        Random random = new Random();
        int listIndex = random.nextInt(allWords.size());
        String word = allWords.get(listIndex);
        return word;
    }

/**
 * When user types in a word as an attempt, we need a list of words that confirms the attempt is valid
 * ie: their attempt was an actual word and not a series of random letters.
 * Similarly, this will read in the list of ALL 5-letter words and search for a word. 
 * Will return true if word is found, false otherwise.
 */

    public boolean validWord(String word){

        boolean valid = false;
       
        try{
            //Reading the text file
            File textFile = new File("valid-wordle-words.txt");
            FileReader fileReader = new FileReader(textFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;

            //Continously searches the text file for the word and stops when we find it
            while((line = bufferedReader.readLine()) != null && !valid){
                if(line.equals(word)){
                    valid = true;

                }
            }
            bufferedReader.close();
            return valid;
        }
        catch(IOException exception){
            System.out.println("An error has occurred when loading the words from the file: " + exception.getMessage());
            
        }
        return valid;
    }
 
}

