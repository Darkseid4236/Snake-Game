import javax.swing.JFrame; //imported to make a window

public class App {
    public static void main(String[] args) throws Exception {
        int boardwidth = 600; //sets window width pixels
        int boardheight = boardwidth;

        JFrame frame = new JFrame("Sanke"); //"Sanke is name of window, this is contructor of object frame"
        frame.setVisible(true); //makes the frame visible
        frame.setSize(boardwidth, boardheight);
        frame.setLocationRelativeTo(null); // positions frame in the middle
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //program terminates hen user clicks on x button on window
        
        //now we need a JPanel, so we will create a separate class in src file and inherits its functions here
        SnakeGame snakeGame = new SnakeGame(boardwidth, boardheight);
        frame.add(snakeGame);
        frame.pack(); //first white box above the window was included in 600 height, but now jpanel will be inside with full dimensions
        // now we have to define tile size snake colour(let 25*25 pixel), snake moves in that

        //we will craete another class to keep track of x and y positions of each tile
        snakeGame.requestFocus();


    }
}
