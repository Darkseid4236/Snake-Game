import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //storing segments o snake's body, each part is a tile
import java.util.Random; //to get random x and y value for placing food on screen
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;
        Tile(int x, int y){
            this.x= x;
            this.y = y;}}// now we neeed to specify x and y widht and height


    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    Tile snakeHead; // a variable
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random; // an object to place food randomly

    //game logic - we need to regularly update size of snake, so first we need to make a game loop, and set a timer
    Timer gameLoop;
    int velocityx;
    int velocityy;
    boolean gameOver = false;


    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth= boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this); // and we want our sake game to listen to key presses so
        setFocusable(true);

        snakeHead = new Tile (5,5); // deauklt starting position
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10,10);
        random = new Random();
        placeFood(); // then calling this function

        velocityx = 0; // we will be playing using key
        velocityy= 1;


        gameLoop = new Timer(200, this); // we have to tell what to d ever 100ms
        gameLoop.start(); 
        // we want to move snake every 100 ms

    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over     Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.setColor(Color.green);
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }


        //Grids
        //x1, y1, x2, y2
        // for (int i = 0; i<boardWidth/tileSize; i++){
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight ); // vertical
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);// horizontal lines
        //}
        //Snake head
        g.setColor(Color.yellow);
        //g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);

        //Snakebody
        g.setColor(Color.green);
        for (int i =0; i< snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i); // here the array list stores x and y position of the food with which the snake last collided, then here, body part of sake is coloured
            //g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }

        //food 
        g.setColor(Color.red);
        //g.fillRect(food.x*tileSize, food.y *tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, food.y *tileSize, tileSize, tileSize, true);


    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize); //600/25 =24 so random no. between 0 to 24
        food.y = random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        //to check if two tiles collide, for collision upon snake's body itself
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        //eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake body
        for (int i = snakeBody.size()-1; i>=0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i==0){
                //this means this is the first member of the snake body, one that comes right after the snake head
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;

            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        //Snake head
        snakeHead.x += velocityx;
        snakeHead.y += velocityy; //we are updating x and y positions every 100ms by one tile
        
        //game over conditions
        // 1 - hit itself
        for (int i = 0; i< snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            //collide with the snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        //for wall collision
        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize <0 || snakeHead.y*tileSize > boardHeight){
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); // so it will call draw again and again
        if (gameOver) {
            gameLoop.stop();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityy !=1) {
            velocityx = 0;
            velocityy = -1;
        
        }
        else if(e.getKeyCode()== KeyEvent.VK_DOWN && velocityy !=-1) {
            velocityx =0;
            velocityy =1;
        }
        else if(e.getKeyCode()== KeyEvent.VK_LEFT && velocityx !=1) {
            velocityx =-1;
            velocityy =0;
        }
        else if(e.getKeyCode()== KeyEvent.VK_RIGHT && velocityx !=-1) {
            velocityx =1;
            velocityy =0;
        }}


// do not rest two methods for keylistener      
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}


