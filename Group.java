import java.awt.Point;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class Group {
	private static final char BLACK = 'b';
	private static final char WHITE = 'w';
	private static final char EMPTY = ' ';

	private ArrayList<Point> emptyAdjacentSpaces = new ArrayList<>();
	private ArrayList<Point> coordinates = new ArrayList<>();
	private char piece;
	private int w,h;

	/**
	 * A group will be made from a starting space and adding adjacent spaces into the list of coordinates.
	 */
	public Group(int x0, int y0, char[][] board, char piece){
		Queue<Point> uncheckedAdjacentSpaces = new LinkedList<>();
		uncheckedAdjacentSpaces.add(new Point(x0, y0));
		this.piece = piece;

		// Get all adjacent squares
		int h = board.length;
		int w = board[0].length;
		this.w = w;
		this.h = h;

		boolean[][] checkedSpaces = new boolean[h][w];
		while (!uncheckedAdjacentSpaces.isEmpty()){
			Point p = uncheckedAdjacentSpaces.remove();
			x0 = (int)p.getX();
			y0 = (int)p.getY();
			checkedSpaces[y0][x0] = true;
			if (x0 > 0){
				Point p2 = new Point(x0-1,y0);
				if (checkPiece(EMPTY, board, checkedSpaces, x0-1, y0)){
					this.emptyAdjacentSpaces.add(p2);
				}
				else if (checkPiece(piece, board, checkedSpaces, x0-1, y0)){
					uncheckedAdjacentSpaces.add(p2);
				}
				checkedSpaces[y0][x0-1] = true;
			}
			if (x0 < w-1){
				Point p2 = new Point(x0+1,y0);
				if (checkPiece(EMPTY, board, checkedSpaces, x0+1, y0)){
					this.emptyAdjacentSpaces.add(p2);
				}
				else if (checkPiece(piece, board, checkedSpaces, x0+1, y0)){
					uncheckedAdjacentSpaces.add(p2);
				}
				checkedSpaces[y0][x0+1] = true;
			}
			if (y0 > 0){
				Point p2 = new Point(x0,y0-1);
				if (checkPiece(EMPTY, board, checkedSpaces, x0, y0-1)){
					this.emptyAdjacentSpaces.add(p2);
				}
				else if (checkPiece(piece, board, checkedSpaces, x0, y0-1)){
					uncheckedAdjacentSpaces.add(p2);
				}
				checkedSpaces[y0-1][x0] = true;
			}
			if (y0 < h-1){
				Point p2 = new Point(x0,y0+1);
				if (checkPiece(EMPTY, board, checkedSpaces, x0, y0+1)){
					this.emptyAdjacentSpaces.add(p2);
				}
				else if (checkPiece(piece, board, checkedSpaces, x0, y0+1)){
					uncheckedAdjacentSpaces.add(p2);
				}
				checkedSpaces[y0+1][x0] = true;
			}
			coordinates.add(p);
		}
	}

	/* This was meant to reduce the number of lines in the contructor, but it didn't work out very well. */
	private static boolean checkPiece(char piece, char[][] board, boolean[][] checkedSpaces, int x, int y){
		return board[y][x] == piece && !checkedSpaces[y][x];
	}

	public Point[] getCoordinates(){
		return this.coordinates.toArray(new Point[this.coordinates.size()]);
	}

	public void printCoordinates(){
		char[][] board = new char[this.h][this.w];
		for (Point p : this.coordinates){
			board[(int)p.getY()][(int)p.getX()] = this.piece;
		}
		for (int y = 0; y < this.h; y++){
			for (int x = 0; x < this.w; x++){
				char c = board[y][x];
				if (c == this.piece){
					System.out.print(board[y][x]);
				}
				else{
					System.out.print(".");
				}
			}
			System.out.println("");
		}
	}

	public Point[] getEmptyAdjascentSpaces(){
		return this.emptyAdjacentSpaces.toArray(new Point[this.emptyAdjacentSpaces.size()]);
	}

	public void printEmptyAdjacentSpaces(){
		for (Point p : this.emptyAdjacentSpaces){
			System.out.println(p);
		}
	}
}