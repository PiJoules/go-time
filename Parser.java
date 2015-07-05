import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Point;

public class Parser{
	private static final char BLACK = 'b';
	private static final char WHITE = 'w';
	private static final char EMPTY = ' ';

	public static void main(String[] args){
		/* Read from stdin */
		Scanner scan = new Scanner(System.in);
		String[] dims = scan.nextLine().split("\\s+");
		int w = Integer.parseInt(dims[0]);
		int h = Integer.parseInt(dims[1]);
		char player = scan.nextLine().charAt(0);
		char opponent = (player == BLACK ? WHITE : BLACK);
		char[][] board = new char[h][w];
		for (int y = 0; y < h; y++){
			board[y] = scan.nextLine().toCharArray();
		}

		/*
		Find the best place to put my piece.
		- For each empty space, find the number of white pieces caught in a group had a player piece been there.
		- Will need Group class with method for checking if is next to empty space.
		  - Group will have a list of coordinates that makes it up.
		    - A group will be made from a starting space and adding adjacent spaces into the list of coordinates.
		    - Given a space, get an array of all adjascent spaces with corresponding pieces and add them to a queue. While the queue is not empty, get the first element of the queue, find all adjacent pieces, add them to the queue, then finally add the current element to the list of coordinates.
		    - All the while, keep track of how many open spaces are next to the group. The group cannot be blocked if it has more that 1 emprt adja

		1. Find all existing white groups
		2. For each empty space, check for how many white pieces in groups are caught. Groups are caught if they are not next to any open spaces.
		3. Pick the space with the largest number of white pieces caught.
		 */
		boolean checkedPoints[][] = new boolean[h][w];
		ArrayList<Point> emptySpaces = new ArrayList<>();
		ArrayList<Group> groups = new ArrayList<>();
		for (int y = 0; y < h; y++){
			for (int x = 0; x < w; x++){
				char c = board[y][x];
				if (!checkedPoints[y][x]){
					Point p = new Point(x,y);
					if (c == EMPTY){
						emptySpaces.add(p);
					}
					else if (c == opponent){
						Group g = new Group(x,y,board,opponent);
						Point[] coordinates = g.getCoordinates();
						for (Point coord : coordinates){
							checkedPoints[(int)coord.getY()][(int)coord.getX()] = true;
						}
						groups.add(g);
					}
					checkedPoints[y][x] = true;
				}
			}
		}

		// Compare the scores
		Point bestPoint = null;
		int maxScore = 0;
		for (Point p : emptySpaces){
			int score = 0;
			for (Group g : groups){
				Point[] adjEmptSpaces = g.getEmptyAdjascentSpaces();
				if (adjEmptSpaces.length == 1){
					if (adjEmptSpaces[0].equals(p)){
						score += g.getCoordinates().length;
					}
				}
			}
			if (score > maxScore){
				maxScore = score;
				bestPoint = p;
			}
		}

		/* Print stuff */
		if (bestPoint == null){
			System.out.println("No constructive move");
		}
		else {
			System.out.println("(" + (int)bestPoint.getX() + ", " + (int)bestPoint.getY() + ")");
		}
	}
}