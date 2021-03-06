import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.*;

public class PuzzleBoard implements Comparable<PuzzleBoard> {
	int[][] table;
	private int zeroRow;
	private int zeroCol;

	int pathCost;
	int functionCost;
	int heuristicCost;

	// new, ordered Puzzle Board constructor
	public PuzzleBoard() {
		this.table = new int[3][3];
		this.zeroRow = 2;
		this.zeroCol = 2;
		this.pathCost = 0;
		this.heuristicCost = this.heuristicManhattan();
//		this.heuristicCost = this.heuristicMisplaced();
		this.functionCost = this.pathCost + this.heuristicCost;

		int counter = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				table[i][j] = counter;
				counter++;
			}
		}
		table[zeroRow][zeroCol] = 0;
	}

	// new PuzzleBoard based on a template of a board
	public PuzzleBoard(PuzzleBoard template) {
		this.table = new int[3][3];
		this.zeroRow = template.zeroRow;
		this.zeroCol = template.zeroCol;

		this.pathCost = template.pathCost;
		this.functionCost = template.functionCost;
		this.heuristicCost = template.heuristicCost;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.table[i][j] = template.table[i][j];
			}
		}

	}

	// Prints out the table
	public void printTable() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				System.out.print(this.table[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	// Upon request, changes the board with an UP move
	public void moveUp() {
		this.table[this.zeroRow][this.zeroCol] = this.table[this.zeroRow - 1][this.zeroCol];
		this.table[this.zeroRow - 1][this.zeroCol] = 0;
		this.zeroRow--;
	}

	// Upon request, changes the board with an RIGHT move
	public void moveRight() {
		this.table[this.zeroRow][this.zeroCol] = this.table[this.zeroRow][this.zeroCol + 1];
		this.table[this.zeroRow][this.zeroCol + 1] = 0;
		this.zeroCol++;
	}

	// Upon request, changes the board with an DOWN move
	public void moveDown() {
		this.table[this.zeroRow][this.zeroCol] = this.table[this.zeroRow + 1][this.zeroCol];
		this.table[this.zeroRow + 1][this.zeroCol] = 0;
		this.zeroRow++;
	}

	// Upon request, changes the board with an LEFT move
	public void moveLeft() {
		this.table[this.zeroRow][this.zeroCol] = this.table[this.zeroRow][this.zeroCol - 1];
		this.table[this.zeroRow][this.zeroCol - 1] = 0;
		this.zeroCol--;
	}

	// List of legal moves
	public List<Integer> isLegal() {
		List<Integer> legalMoves = new ArrayList<Integer>();

		// check for all legal moves and update the array
		if (this.zeroRow >= 1) {
			// LeagalMoves == up;
			legalMoves.add(1);
		}
		if (this.zeroCol <= 1) {
			// LeagalMoves == right;
			legalMoves.add(2);
		}
		if (this.zeroRow <= 1) {
			// LeagalMoves == down;
			legalMoves.add(3);
		}
		if (this.zeroCol >= 1) {
			// LeagalMoves == left;
			legalMoves.add(4);
		}

		return legalMoves;
	}

	// Checks if the board matches the initial 123456780 state
	public boolean isGoal() {
		int counter = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.table[i][j] == counter) {
					counter++;
				} else if (counter == 9) {
					if (this.table[2][2] == 0) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}

	// return a one-time list then pick based on the length of the list
	private void randomize() {
		List<Integer> legalMoves = this.isLegal();
		int randomMove;

		Random rand = new Random();
		randomMove = rand.nextInt(legalMoves.size());

		if (legalMoves.get(randomMove) == 1) {
			this.moveUp();
		} else if (legalMoves.get(randomMove) == 2) {
			this.moveRight();
		} else if (legalMoves.get(randomMove) == 3) {
			this.moveDown();
		} else if (legalMoves.get(randomMove) == 4) {
			this.moveLeft();
		}

	}

	public void randomizeBoard(int iterations) {
		for (int i = 0; i < iterations; i++) {
			this.randomize();
//			this.printTable();
		}
//		System.out.println("Randomizing Board is Finished"); 
	}

	// helper for heuristicManhattan
	private int mDistance(int row, int col) {
		int element = this.table[row][col];

		// Calculate element's goal row & col
		int temp = element - 1;
		int goalRow = temp / 3;
		int goalCol = temp % 3;

		int mDistance = Math.abs(goalRow - row) + Math.abs(goalCol - col);

		return mDistance;
	}

	// Heuristic by calculating the Manhattan distance
	protected int heuristicManhattan() {
		int h = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.table[i][j] != 0) {
					h += this.mDistance(i, j);
				}
			}
		}

		return h;
	}

	private int misplaced(int row, int col) {
		int element = this.table[row][col];
		// Calculate element's goal row & col
		int temp = element - 1;
		if (row == temp / 3 && col == temp % 3) {
			return 0;
		} else {
			return 1;
		}
	}

	// Heuristic by calculating the amount of tiles misplaced
	protected int heuristicMisplaced() {
		int h = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.table[i][j] != 0) {
					h += this.misplaced(i, j);
				}
			}
		}
		return h;
	}

	// Heuristic by calculating the Manhattan distance with linear conflicts
	protected int heuristicLinearConflict() {

		int h = this.heuristicManhattan();

		h += linearVerticalConflict();
		h += linearHorizontalConflict();

		return h;
	}

	private int linearVerticalConflict() {
		int linearConflict = 0;

		for (int row = 0; row < 3; row++) {
			int max = -1;
			for (int column = 0; column < 3; column++) {
				int cellValue = this.table[row][column];
				// is tile in its goal row ?
				if (cellValue != 0 && (cellValue - 1) / 3 == row) {
					if (cellValue > max) {
						max = cellValue;
					} else {
						// linear conflict, one tile must move up or down to allow the other to pass by
						// and then back up
						// add two moves to the manhattan distance
						linearConflict += 2;
					}
				}

			}

		}
		return linearConflict;
	}

	private int linearHorizontalConflict() {

		int linearConflict = 0;

		for (int column = 0; column < 3; column++) {
			int max = -1;
			for (int row = 0; row < 3; row++) {
				int cellValue = this.table[row][column];
				// is tile in its goal row ?
				if (cellValue != 0 && cellValue % 3 == column + 1) {
					if (cellValue > max) {
						max = cellValue;
					} else {
						linearConflict += 2;
					}
				}
			}
		}
		return linearConflict;
	}

	// CompareTo method for Priority Queue
	@Override
	public int compareTo(PuzzleBoard next) {
		if (this.functionCost > next.functionCost) {
			return 1;
		}

		if (this.functionCost == next.functionCost) {
			return 0;
		}

		return -1;
	}

	// Equals method for Priority Queue
	@Override
	public boolean equals(Object next) {

		if (next instanceof PuzzleBoard) { // if next is not puzzle board type, return false
			PuzzleBoard nextBoard = (PuzzleBoard) next;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (this.table[i][j] != nextBoard.table[i][j]) {
						return false;
					}
				}
			}

			return true;
		}
		return false;
	}

	// Convert PuzzleBoard to String to be stored in hash table
	@Override
	public int hashCode() {
		StringBuffer s = new StringBuffer();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				s.append(this.table[i][j] + " ");
			}
			s.append("\n");
		}

		return s.toString().hashCode();
	}

//	public static void main(String[] args) {
//		PuzzleBoard board = new PuzzleBoard();
//		board.randomizeBoard(80);
//		System.out.println("H3 cost is " + board.heuristicLinearConflict());
//		System.out.println("H2 cost is " + board.heuristicManhattan());
//		board.printTable();
//	}
}
