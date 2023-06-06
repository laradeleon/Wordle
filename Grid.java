import javax.swing.*; //JOptionPane
import java.awt.*; //For buttons
import java.awt.event.*; //Mouse & keyboard actions


public class Grid extends JFrame implements KeyListener, ActionListener{

    final int NUM_ROWS = 6;
    final int NUM_COLUMNS = 5;
    final int WIDTH = 500;
    final int HEIGHT = 600;

    JButton grid[][] = new JButton[NUM_ROWS][NUM_COLUMNS];
    JPanel gridPanel = new JPanel();
    
    public Grid(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout()); //To add reset/next buttons, title and score on screen
        this.setVisible(true); //Shows window on screen
        this.setSize(WIDTH, HEIGHT);

        //Building the grid
        gridPanel.setLayout(new GridLayout(NUM_ROWS,NUM_COLUMNS));
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                JButton newButton = grid[i][j];
                newButton = new JButton();
                newButton.setBackground(Color.PINK);
                newButton.setEnabled(false); //Prevents buttons from being pressed 
                newButton.setFont(new Font("MV Boli", Font.BOLD, 20));
                gridPanel.add(newButton);
            }
        }
        this.add(gridPanel);
        //gridPanel.setVisible(true);

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
    public void keyTyped(KeyEvent e) { //Pressed AND released
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    
}

