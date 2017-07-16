package test;

import java.util.LinkedList;
import java.util.Queue;

public class ClosingIslands {
	class Pair {
		int rowInd;
		int colInd;

		Pair(int r, int c) {
			this.rowInd = r;
			this.colInd = c;
		}
	}

	private int[] rowSet = new int[] { 0, 0, 1, -1 };
	private int[] colSet = new int[] { 1, -1, 0, 0 };

	public boolean isSafe(int r, int c, int[][] M, int row, int col, boolean[][] visited) {
		if ((r < row) && (c < col) && (r >= 0) && (c >= 0) && (M[r][c] == 1) && (!visited[r][c]))
			return true;
		return false;
	}

	public void searchNeighbourUsingDFS(int[][] M, boolean[][] visited, int currR, int currC, int row, int col) {
		visited[currR][currC] = true;
		for (int k = 0; k < 4; k++) {
			int newR = currR + rowSet[k];
			int newC = currC + colSet[k];
			if(isSafe(newR, newC, M, row, col, visited))
			{
				searchNeighbourUsingDFS(M, visited, newR, newC, row, col);
			}
		}
	}

	public void searchNeighbourUsingBFS(int[][] M, boolean[][] visited, int currR, int currC, int row, int col) {
		Queue<Pair> queue = new LinkedList<>();
		queue.offer(new Pair(currR, currC));
		while (!queue.isEmpty()) {
			Pair p = queue.poll();
			int r1 = p.rowInd;
			int c1 = p.colInd;
			for (int k = 0; k < 4; k++) {
				int newR = r1 + rowSet[k];
				int newC = c1 + colSet[k];
				if (isSafe(newR, newC, M, row, col, visited)) {
					visited[newR][newC] = true;
					queue.offer(new Pair(newR, newC));
				}
			}
		}
	}

	public int findCountOfIslands(int[][] M) {
		int row = M.length;
		int col = M[0].length;
		int count = 0;
		boolean[][] visited = new boolean[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (M[i][j] == 1 && !visited[i][j]) {
					// Use this for searching islands using BFS
					searchNeighbourUsingBFS(M, visited, i, j, row, col);
					// UnComment this for searching using DFS
					searchNeighbourUsingDFS(M, visited, i, j, row, col);
					count++;
				}
			}
		}

		return count;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClosingIslands prog = new ClosingIslands();
		int M[][] = new int[][] { 
			{ 1, 1, 0, 0, 0 }, 
			{ 0, 1, 0, 0, 1 }, 
			{ 1, 0, 0, 1, 1 }, 
			{ 0, 1, 0, 0, 0 },
			{ 1, 1, 1, 1, 1 } };
			System.out.println("Total Count = " + prog.findCountOfIslands(M));
	}

}
