package com.example.project;

public class Enemy extends Sprite { //child  of Sprite
    
    //Enemy class constructor
    public Enemy(int x, int y) {
        super(x,y);
    }


    //the methods below should override the super class 


    public String getCoords(){ //returns the coords of the enemy
        return "Enemy:" + super.getCoords();
    }


    public String getRowCol(int size){ //returns the row and column of the enemy
        return "Enemy:" + super.getRowCol(size);
    }
}