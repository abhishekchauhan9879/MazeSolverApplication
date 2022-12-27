package com.maze.MazeSolverApp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 *
 */
public class ShortestPathInMaze {

	private static class Cell {
		int x;
		int y;
		public int distance;
		Cell prev;

		public Cell(int x, int y, int distance, Cell prev) {
			this.x = x;
			this.y = y;
			this.distance = distance;
			this.prev = prev;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}

	/**
	 * This method finds the shortest Path in the given Matrix using Breadth First Search. Time Complexity->O(N^2) and Space Complexity-> O(N^2)
	 * @param matrix : Matrix containing start Point and destination points
	 * @param startPoint : Starting Coordinate of the Maze
	 * @param endPoint: Destination Coordinate of the Maze
	 * @return : Returns LinkedList having the trail of Coordinates of shortest Path
	 */
	public static LinkedList<Cell> getShortestPath(List<String> matrix, int[] startPoint, int[] endPoint) {
		int sx = startPoint[0], sy = startPoint[1];
		int dx = endPoint[0], dy = endPoint[1];
		if (matrix.get(sx).substring(sy, sy + 1).equalsIgnoreCase("X")
				|| matrix.get(dx).substring(dy, dy + 1).equalsIgnoreCase("X")) {
			return null;
		}
		int rowSize = matrix.size();
		int columnSize = matrix.get(0).length();
		Cell[][] cells = new Cell[rowSize][columnSize];
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < columnSize; j++) {
				if (!matrix.get(i).substring(j, j + 1).equalsIgnoreCase("X")) {
					cells[i][j] = new Cell(i, j, Integer.MAX_VALUE, null);
				}
			}
		}
		LinkedList<Cell> queue = new LinkedList<Cell>();
		LinkedList<Cell> path = new LinkedList<Cell>();
		Cell src = cells[sx][sy];
		src.distance = 0;
		queue.add(src);
		Cell destination = null;
		Cell q;
		while ((q = queue.poll()) != null) {
			if (q.x == dx && q.y == dy) {
				destination = q;
				break;
			}
			traverseMaze(cells, queue, q.x - 1, q.y, q);
			traverseMaze(cells, queue, q.x + 1, q.y, q);
			traverseMaze(cells, queue, q.x, q.y - 1, q);
			traverseMaze(cells, queue, q.x, q.y + 1, q);
		}
		if (destination == null) {
			return null;
		} else {
			q = destination;
			do {
				path.addFirst(q);
			} while ((q = q.prev) != null);
		}
		return path;
	}

	/**
	 * function to update cell traversing status, Time O(1), Space O(1)
	 * @param cells : Matrix containing cells
	 * @param queue : queue for traversing paths
	 * @param x : current x coordinate
	 * @param y : current y coordinate
	 * @param parent : Previous of current cell
	 */
	private static void traverseMaze(Cell[][] cells, LinkedList<Cell> queue, int x, int y, Cell parent) {
		if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length || cells[x][y] == null) {
			return;
		}
		int dist = parent.distance + 1;
		Cell q = cells[x][y];
		if (dist < q.distance) {
			q.distance = dist;
			q.prev = parent;
			queue.add(q);
		}
	}

	private static void printShortestPath(List<String> matrix, int[] startPoint, int[] endPoint) {
		LinkedList<Cell> shortestPath = getShortestPath(matrix, startPoint, endPoint);
		if (shortestPath == null) {
			System.out.println("No Path Found.");
			return;
		}
		System.out.println("Shortest Path found:");
		for (Cell cell : shortestPath) {
			System.out.println(cell + " ");
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the Matrix:(Press Enter twice to stop)");
		List<String> matrix = new ArrayList<String>();

		int[] startPoint = new int[2];
		int[] endPoint = new int[2];
		String cellInput;
		while (!(cellInput = scanner.nextLine()).isEmpty()) {
			matrix.add(cellInput);
			if (cellInput.contains("S")) {
				startPoint[0] = matrix.indexOf(cellInput);
				startPoint[1] = cellInput.indexOf("S");
			}
			if (cellInput.contains("G")) {
				endPoint[0] = matrix.indexOf(cellInput);
				endPoint[1] = cellInput.indexOf("G");
			}
		}
		printShortestPath(matrix, startPoint, endPoint);
		scanner.close();
	}
}
