import javax.swing.*; //JOptionPane
import java.awt.*; //For buttons
import java.awt.event.*; //Mouse & keyboard actions

public class Grid extends JFrame implements ActionListener, KeyListener{

    final int NUM_ROWS = 6;
    final int NUM_COLUMNS = 5;
    final int WIDTH = 500;
    final int HEIGHT = 600;
    final int FONT_SIZE = 45;

    JButton grid[][] = new JButton[NUM_ROWS][NUM_COLUMNS]; //2D Array of JButtons to create grid
    JPanel gridPanel = new JPanel();

    JLabel headerLabel;

    int currentAttempt; //0 = 1st attempt, 1 = 2nd attempt.. etc..
    int currentLetterSlot; //0 = first letter in the word.. etc..

    String gameAnswer;

    boolean canContinue = true;
    boolean gameOver;
    String font = "Cambria";

    boolean buttonChanged[] = new boolean[NUM_COLUMNS];
    boolean correctPosition[] = new boolean[NUM_COLUMNS];
    
    public Grid(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout()); //To add reset/next buttons, title and score on screen
        this.setVisible(true); //Shows window on screen
        this.setSize(WIDTH, HEIGHT);

        //Allows for colors of grid to change on Mac OS (Code works fine on Windows without this try/catch)
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }

        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        // ---------------------------- GRID ---------------------------- //
        gridPanel.setVisible(true);
        gridPanel.setLayout(new GridLayout(NUM_ROWS,NUM_COLUMNS));
        gridPanel.setBackground(Color.black);

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j] = new JButton();
                grid[i][j].setEnabled(false); //Prevents buttons from being pressed 
                grid[i][j].setBackground(Color.BLACK);
                grid[i][j].setFont(new Font(font, Font.BOLD,FONT_SIZE));
                gridPanel.add(grid[i][j]);
            }
        }
      
        this.add(gridPanel);
        
        // ----------------------------  GRID ---------------------------- //

        // ---------------------------- TITLE HEADER ---------------------------- //

        headerLabel = new JLabel();
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setFont(new Font(font, Font.BOLD, FONT_SIZE));
        headerLabel.setBackground(Color.BLACK);
        headerLabel.setForeground(Color.WHITE); //Text 
        headerLabel.setOpaque(true);
        headerLabel.setText("Wordle");

        this.add(headerLabel, BorderLayout.NORTH);

        // ---------------------------- TITLE HEADER ---------------------------- //

        this.addKeyListener(this); //To make it respond to key presses
        this.setFocusable(true);

        //Refreshing frame after changing components
        this.repaint();
        this.revalidate();

        this.setTitle("Wordle");
        this.setLocationRelativeTo(null); //Puts frame in middle of screen

        //Getting answer for the current round
        gameAnswer = new Words().getAnswer();
        System.out.println(gameAnswer);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) { //Key codes are same for uppercase and lowercase

        //KEYCODE: A/a to Z/z = 65 to 90
        int keyCode  = e.getKeyCode();
        final int KEYCODE_A = 65;
        final int KEYCODE_Z = 90;
        final int KEYCODE_ENTER = 10;
        final int KEYCODE_BACKSPACE = 8;

        //If the user types a letter between A-Z
        if(keyCode >= KEYCODE_A && keyCode <= KEYCODE_Z){
            if(canContinue){
                //And we havent filled all of the letter slots yet
                if(currentLetterSlot < NUM_COLUMNS){
                    grid[currentAttempt][currentLetterSlot].setText(String.valueOf((char)keyCode)); //Displaying the letter on the grid
                    currentLetterSlot++; //Going to the next letter slot
                }
                else{
                    canContinue = false; //If 5 letters have been typed, we can no longer type any more letters
                }
            }
        }
        //If user wants to delete a letter
        else if(keyCode == KEYCODE_BACKSPACE){
            if(currentLetterSlot > 0){ //To prevent index out of bounds when backspace is repeatedly pressed
                currentLetterSlot--;
                grid[currentAttempt][currentLetterSlot].setText("");
                canContinue = true; //User may continue to type because a letter slot has opened up (from deleting)
            }
        }
        //If the user wants to enter their word
        else if(keyCode == KEYCODE_ENTER){
            if(currentAttempt < NUM_ROWS){//If the current attempt is either attempt 1 - 5 (6 total attempts)
                if(currentLetterSlot == NUM_COLUMNS){//If the user has entered a 5 letter word
                    String typedWord = "";
                    for(int i = 0; i < NUM_COLUMNS; i++){
                        typedWord += grid[currentAttempt][i].getText(); //Adding all the letters to show one word
                    }

                    typedWord = typedWord.toLowerCase();

                    //Check if the 5-letter word the user has entered is an actual word
                    boolean isValid = new Words().validWord(typedWord);

                    if(isValid){
                        System.out.println("Is valid!");

                        // (If an invalid word was entered previously) Resets header label to "Wordle" if the word is valid 
                        headerLabel.setForeground(Color.WHITE);
                        headerLabel.setText("Wordle"); 


                        //Now we need to check the word they put in and see how it matches up to the answer
                        checkWord(typedWord);

                        //Check the correctPosition[] array to see if user won (all elements are true)

                        currentLetterSlot = 0; //Going to the next row now
                        currentAttempt++;
                        canContinue = true;

                    }
                    else{ //Displays error message if word is not valid
                        System.out.println("Not valid!");
                        headerLabel.setForeground(Color.RED);
                        headerLabel.setText("Word does not exist.");
                    }
                    
                }
                else{//User did NOT enter a 5-letter word --> Displays error message
                    headerLabel.setForeground(Color.RED);
                    headerLabel.setText("Not enough letters.");
                }
            }
        }
        
    }

    public void checkWord(String word){

        //Corresponds with final answer array, tells us whether the letter in each index has been matched/paired with letter in userEntry
        boolean[] isMatched = new boolean[NUM_COLUMNS]; 

        //Need to reset arrays after each round so every square each round is turned to either red, green, or gray
        for(int i = 0; i < NUM_COLUMNS; i++){
            isMatched[i] = false;
            buttonChanged[i] = false;
            correctPosition[i] = false;
        }

        //CASE 1: EXACT MATCH (right letter AND right spot)
        //First we need to check which letters are in the correct spot
        //Need to have two arrays, one to store the user's input, and one to store the answers
        char[] answer = gameAnswer.toCharArray();
        char[] userEntry = word.toCharArray();

        //GREEN SQUARES: Correct letter, correct position
        // Then we need to compare the arrays and check if there are letters that match up (same spot)
        for(int i = 0; i < NUM_COLUMNS; i++){
            if(userEntry[i] == answer[i]){
                System.out.println("Found a match at index " + i + " for letter: " + userEntry[i]);
                grid[currentAttempt][i].setBackground(Color.green);
                buttonChanged[i] = true;
                correctPosition[i] = true;
                isMatched[i] = true;
            }
        }

        //YELLOW SQUARES: Correct letter, wrong position
        // Get the first letter in the user input, then compares with every letter in final answer
        for(int i = 0; i < NUM_COLUMNS; i++){
            for(int j = 0; j < NUM_COLUMNS; j++){
                if(userEntry[i] == answer[j]){
                   if(!isMatched[j]){ //Ensures that that letter in the final answer hasn't been matched to a letter in another position in the user's entry
                       if(!buttonChanged[i]){ //To prevent us from turning a green square into yellow (incase of duplicate letters)
                            grid[currentAttempt][i].setBackground(Color.YELLOW);
                            buttonChanged[i] = true;
                            isMatched[j] = true;
                        }
                    }
                }
            }
        }

       
    }

    @Override
    public void keyReleased(KeyEvent e) {     
       
    }
    
}

