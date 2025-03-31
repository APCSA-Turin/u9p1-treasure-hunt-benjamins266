package com.example.project;

public class Grid{
    private Sprite[][] grid;
    private int size;

    //constructor for the Grid class
    public Grid(int size) {
        this.size = size;
        grid = new Sprite[size][size];
        //iterates through the 2d array using nested for loops
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[0].length; j++){
                //fills the array with Dot objects
                grid[i][j] = new Dot(j,size - i -1);
            }
        }
    }

    //returns grid
    public Sprite[][] getGrid(){return grid;}



    //placing a sprite
    public void placeSprite(Sprite s){
        int row = (size-1 -s.getY());
        int col = s.getX();
        //places a sprite in the designated row and columns
        grid[row][col] = s;
    }

    //placing a sprite in a new place with direction
    public void placeSprite(Sprite s, String direction) {
        //if the user input is "s"
        if(direction.equals("s")){
            //it sets the Sprite object s coordinates' to the new place
            grid[size-1-s.getY()][s.getX()] = s;
            //sets the previous spot of the object to a Dot object
            grid[size-2-s.getY()][s.getX()] = new Dot(s.getX(), s.getY());
        //if the user input is "w"
        } if (direction.equals("w")){
            //it sets the Sprite object s coordinates' to the new place
            grid[size-s.getY()-1][s.getX()] = s;
            //sets the previous spot of the player to a Dot object
            grid[size-s.getY()][s.getX()] = new Dot(s.getX(), s.getY());
        //if the user input is "d"
        } if (direction.equals("d")){
            //it sets the Sprite object s coordinates' to the new place
            grid[size-s.getY()-1][s.getX()] = s;
            //sets the previous spot of the player to a Dot object
            grid[size-1-s.getY()][s.getX()-1] = new Dot(s.getX(), s.getY());
        //if the user input is "a"
        } if (direction.equals("a")){
            //it sets the Sprite object s coordinates' to the new place
            grid[size-s.getY()-1][s.getX()] = s;
            //sets the previous spot of the player to a Dot object
            grid[size-1-s.getY()][s.getX()+1] = new Dot(s.getX(), s.getY());
    }
}

    //displays the different types of objects on the grid
    public void display() {
        //iterates the 2d array using nested iteratioin
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                //if the object at the specified coordinates is a Dot object
                if(grid[i][j] instanceof Dot){
                    //prints out a white box
                    System.out.print("⬜");
                //if the object at the specified coordinates is a Enemy object
                } else if(grid[i][j] instanceof Enemy){
                    //prints out a red box
                    System.out.print("🟥");
                //if the object at the specified coordinates is a Trophy object
                } else if(grid[i][j] instanceof Trophy){
                    //prints out a blue box
                    System.out.print("🟦");
                //if the object at the specified coordinates is a Treasure object
                } else if(grid[i][j] instanceof Treasure){
                    //prints out a orange box
                    System.out.print("🟧");
                //if the object at the specified coordinates is a Player object
                } else if(grid[i][j] instanceof Player){
                    //prints out a black box
                    System.out.print("⬛");
                //if the object at the specified coordinates is a Skulls object
                } else if(grid[i][j] instanceof Skulls){
                    //prints out a skull
                    System.out.print("💀");
                }
            }
            System.out.println();
        }

    }


}