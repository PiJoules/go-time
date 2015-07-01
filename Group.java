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

		while (!uncheckedAdjacentSpaces.isEmpty()){
			Point p = uncheckedAdjacentSpaces.remove();
			x0 = (int)p.getX();
			y0 = (int)p.getY();
			int startX = Math.max(0, x0-1);
			int startY = Math.max(0, y0-1);
			int endX = Math.min(w, x0+2);
			int endY = Math.max(h, y0+2);
			for (int y = startY; y < endY; y++){
				for (int x = startX; x < endX; x++){
					if (x != x0 && y != y0){
						char c = board[y][x];
						Point p2 = new Point(x,y);
						if (c == EMPTY && !this.emptyAdjacentSpaces.contains(p2)){
							this.emptyAdjacentSpaces.add(p2);
						}
						else if (c == piece && !this.coordinates.contains(p2)){
							uncheckedAdjacentSpaces.add(p2);
						}
					}
				}
			}
			coordinates.add(p);
		}
	}

	public Point[] getCoordinates(){
		return this.coordinates.toArray(new Point[this.coordinates.size()]);
	}

	public void printCoordinates(){
		char[][] board = new char[this.h][this.w];
		for (Point p : this.coordinates){
			board[(int)p.getY()][(int)p.getX()] = 's';
		}
		for (int y = 0; y < this.h; y++){
			for (int x = 0; x < this.w; x++){
				System.out.print(board[y][x]);
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