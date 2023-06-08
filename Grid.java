import javax.swing.*; //JOptionPane
import java.awt.*; //For buttons
import java.awt.event.*; //Mouse & keyboard actions

public class Grid extends JFrame implements ActionListener, KeyListener{

    final int NUM_ROWS = 6;
    final int NUM_COLUMNS = 5;
    final int WIDTH = 500;
    final int HEIGHT = 600;

    JButton grid[][] = new JButton[NUM_ROWS][NUM_COLUMNS]; //2D Array of JButtons to create grid
    JPanel gridPanel = new JPanel();

    int currentRow; //0 = 1st attempt, 1 = 2nd attempt.. etc..
    int currentLetterSlot; //0 = first letter in the word.. etc..

    String gameAnswer;

    boolean canContinue = true;
    boolean gameOver;
    String font = "Cambria";
    
    public Grid(){

        //Getting answer for the current round
        gameAnswer = new Words().getAnswer();
        

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout()); //To add reset/next buttons, title and score on screen
        this.setVisible(true); //Shows window on screen
        this.setSize(WIDTH, HEIGHT);

        //Building the grid
        gridPanel.setVisible(true);
        gridPanel.setLayout(new GridLayout(NUM_ROWS,NUM_COLUMNS));
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j] = new JButton();
                grid[i][j].setBackground(Color.WHITE);
                grid[i][j].setEnabled(false); //Prevents buttons from being pressed 
                grid[i][j].setFont(new Font(font, Font.BOLD, 20));
                gridPanel.add(grid[i][j]);
            }
        }
        this.add(gridPanel);
        this.addKeyListener(this); //To make it respond to key presses
        //this.setFocusable(true);

        //Refreshing frame after changing components
        this.repaint();
        this.revalidate();

        this.setTitle("Wordle");
        this.setLocationRelativeTo(null); //Puts frame in middle of screen
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
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
                    grid[currentRow][currentLetterSlot].setText(String.valueOf((char)keyCode)); //Displaying the letter on the grid
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
                grid[currentRow][currentLetterSlot].setText("");
                canContinue = true; //User may continue to type because a letter slot has opened up (from deleting)
            }
        }
        //If the user wants to enter their word
        else if(keyCode == KEYCODE_ENTER){
            if(currentRow < NUM_ROWS){//If the current attempt is either attempt 1 - 5 (6 total attempts)
                if(currentLetterSlot == NUM_COLUMNS){//If the user has entered a 6 letter word
                    String typedWord = "";
                    for(int i = 0; i < NUM_COLUMNS; i++){
                        typedWord += grid[currentRow][i].getText(); //Adding all the letters to show one word
                    }

                    //Check if the 5-letter word the user has entered is an actual word
                    boolean isValid = new Words().validWord(typedWord.toLowerCase());

                    if(isValid){
                        System.out.println("Is valid!");

                    }
                    else{
                        System.out.println("Not valid!");

                    }
                    
                }
            }
        }
        



    
    }

    @Override
    public void keyReleased(KeyEvent e) {     
       
    }
    
}

