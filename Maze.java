// Name:
// USC loginid:
// CS 455 PA3
// Fall 2016

import java.util.LinkedList;
import java.util.ListIterator;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls
 */

public class Maze {
   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   public static final int VISITED = 1;
   public static final int DIRECTIONS = 4;
   
   private boolean[][] mazeMap;
   private MazeCoord startLocation;
   private MazeCoord endLocation;
   
   private LinkedList<MazeCoord> path = new LinkedList<MazeCoord>();
   private ListIterator<MazeCoord> iter = path.listIterator();
   
   private int[][] visited;
   
   
 
   
   
   
  

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param endLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
   {
	   int mazeRow = mazeData.length;
	   int mazeCol = mazeData[0].length;
	   
	   mazeMap = new boolean[mazeRow + 2][mazeCol + 2];
	   
	   for(int i = 0; i < mazeRow + 2; i++){
		   for(int j = 0; j < mazeCol + 2; j++){
			   if(i==0||i==mazeRow+1||j==0||j==mazeCol+1){
				   mazeMap[i][j] = WALL;
			   }
			   else{
				   mazeMap[i][j] = mazeData[i-1][j-1];
			   }
		   }
	   }
	   
	   startLocation = new MazeCoord(startLoc.getRow() + 1, startLoc.getCol() + 1);
	   endLocation = new MazeCoord(endLoc.getRow() + 1, endLoc.getCol() + 1);;
   }


   /**
   Returns the number of rows in the maze
   @return number of rows
   */
   public int numRows() {
      return mazeMap.length-2;   // DUMMY CODE TO GET IT TO COMPILE
   }

   
   /**
   Returns the number of columns in the maze
   @return number of columns
   */   
   public int numCols() {
      return mazeMap[0].length-2;   // DUMMY CODE TO GET IT TO COMPILE
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
	   int row = loc.getRow();
	   int col = loc.getCol();
	   return mazeMap[row+1][col+1];   // DUMMY CODE TO GET IT TO COMPILE
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
	   int row = startLocation.getRow() - 1;
	   int col = startLocation.getCol() - 1;
	   
	   return new MazeCoord(row, col);   // DUMMY CODE TO GET IT TO COMPILE
   }
   
   
   /**
   Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() {
	   int row = endLocation.getRow() - 1;
	   int col = endLocation.getCol() - 1;
	   
	   return new MazeCoord(row, col);   // DUMMY CODE TO GET IT TO COMPILE
   }

   
   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {
	   LinkedList<MazeCoord> actualPath = new LinkedList<MazeCoord>();
	   ListIterator<MazeCoord> iter2 = path.listIterator();
	   ListIterator<MazeCoord> iter3 = actualPath.listIterator();
	   while(iter2.hasNext()){
		   MazeCoord newCoord = iter2.next();
		   iter3.add(new MazeCoord(newCoord.getRow()-1, newCoord.getCol()-1));
	   }
	   return actualPath;   // DUMMY CODE TO GET IT TO COMPILE
//	   return path;
   }


   /**
      Find a path through the maze if there is one.  Client can access the
      path found via getPath method.
      @return whether path was found.
    */
   public boolean search(){
	   visited = new int[mazeMap.length][mazeMap[0].length];
	   //visited[startLocation.getRow()][startLocation.getCol()] = VISITED;
	   while(iter.hasPrevious()){
		   iter.previous();
		   iter.remove();
	   }
	   //iter.add(startLocation);
	   searchH(startLocation);
	   return true;
   }
   
   private boolean searchH(MazeCoord loc){
	   MazeCoord[] adjacentLoc = new MazeCoord[DIRECTIONS];
	   
	   adjacentLoc[0] = new MazeCoord(loc.getRow(), loc.getCol() + 1);
	   adjacentLoc[1] = new MazeCoord(loc.getRow() + 1, loc.getCol());
	   adjacentLoc[2] = new MazeCoord(loc.getRow() - 1, loc.getCol());
	   adjacentLoc[3] = new MazeCoord(loc.getRow(), loc.getCol() - 1);
	   //base case
	 
	   if(loc.equals(endLocation)){
   	          iter.add(loc);
	    	  return true;
	   }
	   if(mazeMap[loc.getRow()][loc.getCol()] == WALL){
		   return false;
	   }
	   if(visited[loc.getRow()][loc.getCol()] == VISITED){
		   return false;
	   }
	   //recursion case
	   /*for(int i = 0; i < DIRECTIONS; i++){
		   if(mazeMap[adjacentLoc[i].getRow()][adjacentLoc[i].getCol()] == FREE && visited[adjacentLoc[i].getRow()][adjacentLoc[i].getCol()] != VISITED){
			   visited[loc.getRow()][loc.getCol()] = VISITED;
			   iter.add(loc);
			   searchH(adjacentLoc[i]);
		   }
	   }
	   return searchH(iter.previous());*/
	   visited[loc.getRow()][loc.getCol()] = VISITED;

		  
		   if(searchH(adjacentLoc[0])){
			   iter.add(loc);
			   return true;
		   }
		   if(searchH(adjacentLoc[1])){
			   iter.add(loc);
			   return true;
		   }
		   if(searchH(adjacentLoc[2])){
			   iter.add(loc);
			   return true;
		   }
		   if(searchH(adjacentLoc[3])){
			   iter.add(loc);
			   return true;
		   }
		  else{
			   return false;
		   }
	   }
	
   }
