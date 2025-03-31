package com.example.project;

public class Sprite {
    private int x, y;

    //sprite class constructor
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //returns the x coordinate
    public int getX(){return x;}
    //returns the y coordinate
    public int getY(){return y;}

    //setter method for the x coordinate
    public void setX(int newX){
        x = newX;
    }
    //setter method for the y coordinate
    public void setY(int newY){
        y = newY;
    }

    //returns the coordinate in the format (x,y)
    public String getCoords(){
        return "(" + x + "," + y + ")";
    }

    //returns the rows and columns in the format [row][col]
    public String getRowCol(int size){
        return "[" + (size-y-1) + "][" + x + "]";
    }
    

    public void move(String direction) { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }

    public void interact() { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }



}
