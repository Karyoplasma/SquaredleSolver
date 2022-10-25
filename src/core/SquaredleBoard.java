package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SquaredleBoard {
	
	private String board;
	private int size;
	private Set<SquaredleNode> nodes;
	
	public SquaredleBoard(String board) {
		this.nodes = new HashSet<SquaredleNode>();
		this.board = board;
		this.size = -1;
		this.setUpBoard();
	}
	
	public void setUpBoard() {
		String[] boardSplit = this.board.split("-");
		
		if (boardSplit[0].length() < 2) {
			throw new IllegalArgumentException("The square must be at least of size 2.");
		}
		for (String part : boardSplit) {
			if (part.length() != boardSplit.length) {
				throw new IllegalArgumentException("The board must be a perfect square.");
			}
		}
		this.size = boardSplit.length;
		int id = 1;
		List<SquaredleNode> temp = new ArrayList<SquaredleNode>();
		for (int row = 0; row < this.size; row++) {
			for (int column = 0; column < this.size; column++) {
				SquaredleNode node = new SquaredleNode(boardSplit[row].charAt(column), id++);
				temp.add(node);
			}
		}
		
		for (int row = 0; row < this.size; row++) {
			for (int column = 0; column < this.size; column++) {
				SquaredleNode node = temp.get(row * this.size + column);
				if (row - 1 >= 0) {
					if (column - 1 >= 0) {
						SquaredleNode neighbor = temp.get((row - 1) * this.size + (column - 1));
						node.addNeighbor(neighbor);
					}
					if (column >= 0) {
						SquaredleNode neighbor = temp.get((row - 1) * this.size + column);
						node.addNeighbor(neighbor);
					}
					if (column + 1 < this.size) {
						SquaredleNode neighbor = temp.get((row - 1) * this.size + (column + 1));
						node.addNeighbor(neighbor);
					}
				}
				if (row >= 0) {
					if (column - 1 >= 0) {
						SquaredleNode neighbor = temp.get(row * this.size + (column - 1));
						node.addNeighbor(neighbor);
					}
					if (column + 1 < this.size && (row * this.size + (column + 1)) < temp.size()) {
						SquaredleNode neighbor = temp.get(row * this.size + (column + 1));
						node.addNeighbor(neighbor);
					}
				}
				
				if (row + 1 < this.size) {
					if (column - 1 >= 0) {
						SquaredleNode neighbor = temp.get((row + 1) * this.size + (column - 1));
						node.addNeighbor(neighbor);
					}
					if (column >= 0) {
						SquaredleNode neighbor = temp.get((row + 1) * this.size + column);
						node.addNeighbor(neighbor);
					}
					if (column + 1 < this.size && ((row + 1) * this.size + (column + 1)) < temp.size()) {
						SquaredleNode neighbor = temp.get((row + 1) * this.size + (column + 1));
						node.addNeighbor(neighbor);
					}
				}
			}
		}
		this.nodes.addAll(temp);
	}
	
	public Set<SquaredleNode>getNodes() {
		return this.nodes;
	}
}
