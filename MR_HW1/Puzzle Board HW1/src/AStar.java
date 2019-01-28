import java.util.PriorityQueue;

//not using static methods
public class AStar extends PuzzleBoard {


	private static void queueInsert(PuzzleBoard x, PriorityQueue frontier) {
		isLegal(x);
		
		if(x.legalz[0] == 1) {
			PuzzleBoard upBoard = new PuzzleBoard(x);
			moveUp(upBoard);
			Node up = new Node(upBoard, heuristic1(upBoard), 1);
			frontier.add(up);
		}
		
		if(x.legalz[1] == 1) {
			PuzzleBoard rightBoard = new PuzzleBoard(x);
			moveRight(rightBoard);
			Node right = new Node(rightBoard, heuristic1(rightBoard), 1);
			frontier.add(right);
		}
		
		if(x.legalz[2] == 1) {
			PuzzleBoard downBoard = new PuzzleBoard(x);
			moveDown(downBoard);
			Node down = new Node(downBoard, heuristic1(downBoard), 1);
			frontier.add(down);
		}
		
		if(x.legalz[3] == 1) {
			PuzzleBoard leftBoard = new PuzzleBoard(x);
			moveLeft(leftBoard);
			Node left = new Node(leftBoard, heuristic1(leftBoard), 1);
			frontier.add(left);
		}
		
	}
	


	//	GRAPH-SEARCH(problem):
	//		initialize frontier using the initial state of problem
	//		loop:
	//			if frontier is empty, return failure
	//			pop node from frontier
	//			if node contains goal state, return solution
	//			for each successor s of node:
	//				add s to frontier
	public static int solve(PuzzleBoard x) {
		
		PriorityQueue<Node> frontier = new PriorityQueue<Node>();
		
		Node start = new Node(x, heuristic1(x), 1);
		
		frontier.add(start);
		
		while(!frontier.isEmpty()) {
			Node next = frontier.poll();
			
			printTable(next.puzzle);
			//counter++ somewhere
			if(isGoal(next.puzzle)) {
				return next.puzzle;
			}
			queueInsert(next.puzzle,frontier);
		}
		
		PuzzleBoard error = new PuzzleBoard(0);
		System.out.println("Error");
		return -1; // throw an exception

	}



	public static void main(String[] args) {
		
		// Create boards and solve them using A*
		
		PuzzleBoard a = new PuzzleBoard();

		printTable(a);
		
		System.out.println(" ");

		randomizeBoard(a, 3);

		printTable(a);
		System.out.println(" ");

		System.out.println("heuristic1 is " + heuristic1(a));
		
		System.out.println(" ");
		
		PuzzleBoard solution = new PuzzleBoard(aStar(a));
		
		printTable(solution);
		

	}

}
