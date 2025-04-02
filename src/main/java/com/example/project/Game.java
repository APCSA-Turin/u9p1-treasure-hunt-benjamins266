package com.example.project;
import java.util.Scanner;

public class Game{
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size; 
    private int count;

    //game class constructor
    public Game(int size){ 
        this.size = size;
        initialize(); //calls initialize method
        play(); //calls play method
        count = 0;
    }

    public static void clearScreen() { //do not modify
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //the play method provides all of the logic for the game
    public void play(){
        Scanner scanner = new Scanner(System.in);
        boolean game = true;
        //the method will keep running until the game variable turns false;
        while(game == true){
            try {
                Thread.sleep(100); // Wait for 1/10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen(); // Clear the screen at the beggining of the while loop

            //displays the grid and all of the Sprite's on it
            grid.display();
            //displays coordinates of the player
            System.out.println("Player: (" + player.getX() + "," + player.getY() + ")" );
            //displays the row and column of the player
            System.out.println("Player: [" + (size-player.getY() -1) + "][" + player.getX() + "]" );
            //displays the number of treasures the player has collected
            System.out.println("Treasure Collected: " + player.getTreasureCount());
            //displays the number of lives the player has remaining
            System.out.println("Lives Remaining: " + player.getLives());

            //checks if the players lives is equal to 0
            if(player.getLives() == 0){
                //creates a new Dot object and places the dot on the coordinate where the player is
                Dot newDot = new Dot(player.getX(), player.getY());
                grid.placeSprite(newDot);
                grid.placeSprite(player);
                //uses nested for loop to iterate the 2d array
                for(int i = 0; i<size; i++){
                    for(int j = 0; j<size; j++){
                        Sprite sprite = grid.getGrid()[i][j];
                        //checks if the sprite is not an instance of Player
                        if(!(sprite instanceof Player)){
                            //places Skull objects in every coordinate except for the Player
                            Skulls skull = new Skulls(j, size-i-1);
                            grid.getGrid()[i][j] = skull;
                        }
                    }
                }
                //calls the gameover method, and sets the game variable to false, to end the loop
                clearScreen();
                grid.display();
                gameover();
                game = false;
            } 
            //if the variable "win" is true, then it starts the win sequence
            if(player.getWin() == true){
                //creates a new Dot variable and places it where the player is
                Dot newDot = new Dot(player.getX(), player.getY());
                grid.placeSprite(newDot);
                grid.placeSprite(player);
                //uses nested for loop to iterate 2d array
                for(int i = 0; i<size; i++){
                    for(int j = 0; j<size; j++){
                        Sprite sprite = grid.getGrid()[i][j];
                        //if the sprite at row i, and column j is not a treasure or a trophy
                        if(!(sprite instanceof Treasure) || !(sprite instanceof Trophy)){
                            //it replaces the object with a trophy
                            Trophy trophies = new Trophy(j,size-i-1);
                            grid.getGrid()[i][j] = trophies;
                        }
                    }
                }

                //begins the wins sequence
                clearScreen();
                grid.display();
                //calls the win method and sets the game variable to false to end the loop
                win();
                game = false;
            } else {
                //displays the line below
                System.out.print("Enter: (w,a,s,d)");
                //checks for the user input
                String move = scanner.nextLine();
                boolean isValid = false;
                //if the user input either w,a,s or d, then the program is good to proceed
                if(move.equals("w") || move.equals("a") || move.equals("s") || move.equals("d")){
                    isValid = true;
                }

                //if isValid is true
                if(isValid == true){
                    int originalX = player.getX();
                    int originalY = player.getY();
                    int currentX = player.getX();
                    int currentY = player.getY();
                    //sees if the movement the player requested is valiid
                    if(player.isValid(size, move)){
                        //used to move left
                        if(move.equals("a")){
                            currentX--;
                            count++;
                        }
                        //used to move right
                        if(move.equals("d")){
                            currentX++;
                            count++;
                        }
                        //used to move up
                        if(move.equals("w")){
                            currentY++;
                            count++;
                        }
                        //used to move down
                        if(move.equals("s")){
                            currentY--;
                            count++;
                        }
                        //box is an object in the current place of the player
                        Sprite box = grid.getGrid()[size-currentY-1][currentX];
                        //if box is an instance of an Enemy
                        if(box instanceof Enemy){
                            Dot oldDot = new Dot(originalX, originalY);
                            Dot newDot = new Dot(currentX, currentY);
                            //it places a new Dot object in the previous place of the player
                            grid.placeSprite(oldDot);
                            //and the new place the player is in
                            grid.placeSprite(newDot);
                            //it also sets the players coordinates to the new x and y values
                            player.setX(currentX);
                            player.setY(currentY);
                            grid.placeSprite(player);
                            //it calls the interact method for the box object
                            player.interact(size, move, treasures.length, box);

                        }
                        //if box is an instance of a Treasure
                        else if(box instanceof Treasure){
                            Dot oldDot = new Dot(originalX, originalY);
                            Dot newDot = new Dot(currentX, currentY);
                            //it places a new Dot object in the previous place of the player
                            grid.placeSprite(oldDot);
                            //and the new place the player is in
                            grid.placeSprite(newDot);
                            //it also sets the players coordinates to the new x and y values
                            player.setX(currentX);
                            player.setY(currentY);
                            grid.placeSprite(player);
                            //it calls the interact method for the box object
                            player.interact(size, move, treasures.length, box);
                        }
                        //if box is an instance of Trophy
                        else if(box instanceof Trophy){
                            int numberOfTreasures = player.getTreasureCount();
                            //if the numberOfTreasures the player has collected is equal to the total number of treasures
                            if(numberOfTreasures == treasures.length){
                            Dot oldDot = new Dot(originalX, originalY);
                            //it places a new Dot object in the previous place of the player
                            grid.placeSprite(oldDot);
                            //it also sets the players coordinates to the new x and y values
                            player.setX(currentX);
                            player.setY(currentY);
                            grid.placeSprite(player);
                            //it calls the interact method for the box object
                            player.interact(size, move, treasures.length, box);
                            //if the numberOfTreasures the player has collected is  NOT equal to the total number of treasures
                            } else {
                                //it sets the players coordinates to the new original x and y values
                                player.setX(originalX);
                                player.setY(originalY);
                                //and displays not enough treasures
                                System.out.println("not enough treasures");
                            }
                            //if box isn't an instance of any of those
                        } else {
                            Dot originalDot = new Dot(originalX, originalY);
                            //it replaces the players spot with a dot object
                            grid.placeSprite(originalDot);
                            //and moves the player in the direction the user input
                            player.move(move);
                            grid.placeSprite(player);
                        }
                        //EXTRA CREDIT: ENEMIES MOVING
                        //iterates through the enemies array to go through each one of the enemies
                        for (int i = 0; i<enemies.length; i++){
                            //creates variables for each one of the enemies' coordinates
                            int origEnemyX = enemies[i].getX();
                            int origEnemyY = enemies[i].getY();
                            int currEnemyX = enemies[i].getX();
                            int currEnemyY = enemies[i].getY();
                            //this makes the enemies move every 2 moves
                            if(count % 2 ==0){
                                //if the enemy's x coordinate is greater than the player's x coordinate
                            if(enemies[i].getX()>player.getX()){
                                //it decreases the enemy's x coordinate by 1
                                currEnemyX--;
                                //if the box the enemy is moving to is the player, it decreases the number of lives by 1
                                if(grid.getGrid()[size-enemies[i].getY()-1][enemies[i].getX()-1] instanceof Player){
                                    player.setLives(player.getLives()-1);
                                }
                                //if the enemy's x coordinate is less than the player's x coordinate
                            } else if (enemies[i].getX()<player.getX()){
                                //it increases the enemy's x coordinate by 1
                                currEnemyX++;
                                //if the box the enemy is moving to is the player, it decreases the number of lives by 1
                                if(grid.getGrid()[size-enemies[i].getY()-1][enemies[i].getX()+1] instanceof Player){
                                    player.setLives(player.getLives()-1);
                                }
                                //if the enemy's y coordinate is less than the player's y coordinate
                            } else if(enemies[i].getY()<player.getY()){
                                //it increases the enemy's y coordinate by 1
                                currEnemyY++;
                                //if the box the enemy is moving to is the player, it decreases the number of lives by 1
                                if(grid.getGrid()[size-enemies[i].getY()-2][enemies[i].getX()] instanceof Player){
                                    player.setLives(player.getLives()-1);
                                }
                                //if the enemy's y coordinate is greater than the player's y coordinate
                            } else if(enemies[i].getY()>player.getY()){
                                //it decreases the enemy's y coordinate by 1
                                currEnemyY--;
                                //if the box the enemy is moving to is the player, it decreases the number of lives by 1
                                if(grid.getGrid()[size-enemies[i].getY()][enemies[i].getX()] instanceof Player){
                                    player.setLives(player.getLives()-1);
                                }
                            }
                            //creating a new dot object to replace the old space the enemy was in, and for the new one its in
                            Dot oDot = new Dot(origEnemyX, origEnemyY);
                            Dot nDot = new Dot(currEnemyX, currEnemyY);
                            //setting the enemy's coordinates to the new coordinates
                            enemies[i].setX(currEnemyX);
                            enemies[i].setY(currEnemyY);
                            //placing the new dot objects and the enemy into the new place
                            grid.placeSprite(oDot);
                            grid.placeSprite(nDot);
                            grid.placeSprite(enemies[i]);
                          
                        }
   
                        }
                    }
                }
            }
        }

     
    }

    //prints a message when the player loses
    public void gameover(){
        System.out.println("Game over, You lose");
   }

    //prints a message when the player wins
    public void win(){
    System.out.println("Congratulations! You WON!");
}

    //to test the grid
    public void initialize(){       
        grid = new Grid(size);
        player = new Player(0,0);
        grid.placeSprite(player);
        treasures = new Treasure[2];
        treasures[0] = new Treasure(1,7);
        treasures[1] = new Treasure(2,2);
        for(Treasure t : treasures){
            grid.placeSprite(t);
        }
        enemies = new Enemy[2];
        enemies[0] = new Enemy(7,2);
        enemies[1] = new Enemy(5,5);
        for(Enemy e : enemies){
            grid.placeSprite(e);
        }

        trophy = new Trophy(9,9);
        grid.placeSprite(trophy);
    }

    public static void main(String[] args) {
        new Game(10);
    }
}


