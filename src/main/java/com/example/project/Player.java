package com.example.project;

public class Player extends Sprite {
    private int treasureCount;
    private int numLives;
    private boolean win;

    //constructor for the Player class
    public Player(int x, int y) {
        super(x,y);
        treasureCount = 0;
        numLives = 2;
        win = false;
    }


    //returns the number of treasures the player has collected
    public int getTreasureCount(){return treasureCount;}
    //returns the number of lives the player has
    public int getLives(){return numLives;}
    //returns the status of win
    public boolean getWin(){return win;}

    //setter method for lives
    public void setLives(int lives){
        numLives = lives;
    }

    //moves the coordinates of the player
    public void move(String direction) {
        //if the direction is "w"
        if(direction.equals("w")){
            //increase the y by 1
            super.setY(super.getY() + 1);
        }
        //if the direction is "s"
        if(direction.equals("s")){
            //decrease the y by 1
            super.setY(super.getY()-1);
        }
        //if the direction is "d"
        if(direction.equals("d")){
            //increase the x by 1
            super.setX(super.getX()+1);
        }
        //if the direction is "a"
        if(direction.equals("a")){
            //decrease the x by 1
            super.setX(super.getX()-1);
        }
    }

    //the player interacts with whatever object it comes in contact with
    public void interact(int size, String direction, int numTreasures, Object obj) {
        //if the object is an instance of Treasure, but is not an instance of Trophy
        if(obj instanceof Treasure && !(obj instanceof Trophy)){
            //increase the treasureCount by 1
            treasureCount++;
        //if the object is an instance of Trophy, and the treasureCount is equal to the total number of treasures
        } else if(obj instanceof Trophy && treasureCount==numTreasures){
            //set win to true
            win = true;
        //if the object is an instance of Enemy
        }else if(obj instanceof Enemy){
            //decrease the number of lives by 1
            numLives--;
        }
    }


    //checks to see if the movement is valid
    public boolean isValid(int size, String direction){
        //if the direction is w, and the y coordinate +1, is less than size
        if((direction.equals("w")) && (super.getY()+1<size)){
            //return true
            return true;
        }
        //if the direction is s, and the y coordinate -1, is greater than 0
        if((direction.equals("s")) && (super.getY()-1>=0)){
            //return true
            return true;
        }
        //if the direction is d, and the x coordinate +1, is less than size
        if((direction.equals("d")) && (super.getX()+1<size)){
            //return true
            return true;
        }
        //if the direction is a, and the y coordinate -1, is greater than 0
        if((direction.equals("a")) && (super.getX()-1>=0)){
            //return true
            return true;
        }
        //otherwise return false
        return false;
    }

    //returns the coordinates of the player
    @Override
    public String getCoords(){
        return "Player:" + super.getCoords();
    }

    //returns the row and column of the player
    @Override
    public String getRowCol(int size){
        return "Player:" + super.getRowCol(size);
    }


   
}



