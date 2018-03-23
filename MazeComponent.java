// Name:yilin wang	
// USC loginid:wangyili@usc.edu
// CS 455 PA3
// Spring 2016

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.ListIterator;

/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{
   private Maze maze;
   
   private static final int START_X = 10; // where to start drawing maze in frame
   private static final int START_Y = 10;
   private static final int BOX_WIDTH = 20;  // width and height of one maze unit
   private static final int BOX_HEIGHT = 20;
   private static final int INSET = 2;  
   private static final int HALF_WIDTH = BOX_WIDTH / 2;
   private static final int HALF_HEIGHT = BOX_HEIGHT / 2;
                    // how much smaller on each side to make entry/exit inner box
   
   
   /**
      Constructs the component.
      @param maze   the maze to display
   */
   public MazeComponent(Maze maze) 
   {   
      this.maze=maze;
   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
	   Graphics2D g2 = (Graphics2D) g;
	   Rectangle box = new Rectangle(START_X, START_Y, maze.numCols()*BOX_WIDTH, maze.numRows()*BOX_HEIGHT);
	   g2.draw(box);
	   for(int i=0; i< maze.numRows(); i++){
		   for(int j=0; j<maze.numCols(); j++){
			   if(maze.hasWallAt(new MazeCoord(i,j))){
				   g2.setColor(Color.BLACK);
				   Rectangle unit = new Rectangle(START_Y+j*BOX_WIDTH,START_X+i*BOX_HEIGHT,BOX_WIDTH,BOX_HEIGHT);
				   g2.fill(unit);   
			   }
			   else{
				   g2.setColor(Color.WHITE);
				   Rectangle unit = new Rectangle(START_Y+j*BOX_WIDTH,START_X+i*BOX_HEIGHT,BOX_WIDTH,BOX_HEIGHT);
				   g2.fill(unit);
			   }
		   }
		   
	   }
	   
	   g2.setColor(Color.YELLOW);
	   Rectangle entry = new Rectangle(maze.getEntryLoc().getCol()*BOX_WIDTH+START_X+INSET,maze.getEntryLoc().getRow()*BOX_HEIGHT+START_Y+INSET,BOX_WIDTH-INSET*2,BOX_HEIGHT-INSET*2);
	   g2.fill(entry);
	   if (!this.maze.hasWallAt(new MazeCoord(maze.numRows() - 1, maze.numCols() - 1))){
	   g2.setColor(Color.GREEN);
	   Rectangle exit = new Rectangle(maze.getExitLoc().getCol()*BOX_WIDTH+START_X+INSET,maze.getExitLoc().getRow()*BOX_HEIGHT+START_Y+INSET,BOX_WIDTH-INSET*2,BOX_HEIGHT-INSET*2);
	   g2.fill(exit);}
	   
	   if(maze.search()){
	   g2.setColor(Color.BLUE);
	   LinkedList<MazeCoord> path = maze.getPath();
	   if(!path.isEmpty()){ 
		   ListIterator<MazeCoord> iter = path.listIterator();
		   while (iter.hasNext()){
			   MazeCoord start = iter.next();
			   if(iter.hasNext()){
				   MazeCoord end = iter.next();
				   Line2D.Double drawpath = new Line2D.Double((double)(START_X + start.getCol() * BOX_WIDTH + HALF_WIDTH),
						   (double)(START_Y + start.getRow() * BOX_HEIGHT + HALF_HEIGHT),
						   (double)(START_X + end.getCol() * BOX_WIDTH + HALF_WIDTH),
						   (double)(START_Y + end.getRow() * BOX_HEIGHT + HALF_HEIGHT));
				   g2.draw(drawpath);
				   iter.previous();
			   }
			   }   
		   }
		  System.out.println(maze.getPath()); 
	   }
	   
   }
   
}


