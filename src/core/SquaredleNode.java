package core;

import java.util.HashSet;
import java.util.Set;

public class SquaredleNode {
	private char letter;
	private Set<SquaredleNode> neighbors;
	private int id;

	public SquaredleNode(char letter, int id) {
		this.letter = letter;
		this.id = id;
		this.neighbors = new HashSet<SquaredleNode>();
	}

	public void addNeighbor(SquaredleNode neighbor) {
		this.neighbors.add(neighbor);
	}

	public char getLetter() {
		return this.letter;
	}

	public int getID() {
		return this.id;
	}

	public Set<SquaredleNode> getNeighbors() {
		return this.neighbors;
	}

	public Set<SquaredleNode> getNeighborsOf(char letter) {
		Set<SquaredleNode> ret = new HashSet<SquaredleNode>();

		for (SquaredleNode neighbor : neighbors) {
			if (neighbor.getLetter() == letter) {
				ret.add(neighbor);
			}
		}
		return ret;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SquaredleNode)) {
			return false;
		}
		return this.getID() == ((SquaredleNode) o).getID();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Node: ").append(this.letter).append("; ID: ").append(this.getID()).append("; Neighbors: ");
		for (SquaredleNode neighbor : this.neighbors) {
			buffer.append(neighbor.getLetter()).append(", ");
		}
		buffer.setLength(buffer.length() - 2);
		return buffer.toString();
	}
}
