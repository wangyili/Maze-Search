import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class tester {
	   
	public static void main(String[] args) throws IOException {
		boolean[][] mazeData = {{false,false,true,true,false,false,false,false},
				{true,false,true,true,false,true,true,false},
				{true,false,true,true,false,true,true,false},
				{true,false,true,true,true,true,true,false},
				{true,false,false,false,false,false,false,false}};
		MazeCoord start = new MazeCoord(2,4);
		MazeCoord end = new MazeCoord(0,0);
		Maze test = new Maze (mazeData,start,end );
		System.out.println(test.getEntryLoc());
		System.out.println(test.getExitLoc());
		System.out.println(test.numRows());
		System.out.println(test.numCols());
		System.out.println(test.hasWallAt(start));
		System.out.println(test.hasWallAt(end));
		System.out.println(test.search());
		test.getPath();
		System.out.println(test.getPath());
		
	}
}

