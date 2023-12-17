package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class SquaredleSolver {
	
	private SquaredleBoard board;
	private Set<String> dictionary;
	
	public SquaredleSolver(SquaredleBoard board) {
		this.board = board;
		this.dictionary = new HashSet<String>();
		this.loadDictionary();
	}

	private void loadDictionary() {
		//get illegal letters
		StringBuffer buffer = new StringBuffer("[abcdefghijklmnopqrstuvwxyz]");
		for (SquaredleNode node : this.board.getNodes()) {
			if (buffer.toString().indexOf(node.getLetter()) != -1) {
				buffer.deleteCharAt(buffer.toString().indexOf(node.getLetter()));
			}		
		}
		String illegals = buffer.toString();
		Pattern pattern = Pattern.compile(illegals);
		
		//build the board dictionary
		try {
			String word = "";
			File dictFile = new File("resource/dictionary.txt");
			Scanner scanner = new Scanner(dictFile);
			scanner.useDelimiter(Pattern.compile(";"));
			while (scanner.hasNext()) {
				word = scanner.next();
				if (!pattern.matcher(word).find()) {
					this.dictionary.add(word);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> solveSquaredle(){
		List<String> solutions = new ArrayList<String>();
		
		//check if a word in dictionary is possible
		for (String word : dictionary) {
			if (this.checkWord(word)) {
				solutions.add(word);
			}
		}
		return solutions;
	}
	
	public boolean checkWord(String word) {
		List<SquaredleNode> visited = new ArrayList<SquaredleNode>();
		for (SquaredleNode node : board.getNodes()) {
			if (node.getLetter() == word.charAt(0)) {
				if (checkWordHelper(word, 1, node, visited)) {
					return true;
				}
			}
		}
		return false;		
	}

	private boolean checkWordHelper(String word, int i, SquaredleNode node, List<SquaredleNode> visited) {
		if (i == word.length()) {
			return true;
		}
		visited.add(node);
		Set<SquaredleNode> paths = node.getNeighborsOf(word.charAt(i));
		for (SquaredleNode path : paths) {
			if (!visited.contains(path)) {
				if (checkWordHelper(word, i+1, path, visited)) {
					return true;
				}
			}
		}
		
		// old code, kept for never being used in the future
//		Set<SquaredleNode> temp = node.getNeighborsOf(word.charAt(i));
//		List<SquaredleNode> paths = new ArrayList<SquaredleNode>();
//		paths.addAll(temp);
//		for (int k = 0; k < paths.size(); k++) {
//			if (!visited.contains(paths.get(k))) {
//				if (checkWordHelper(word, i+1, paths.get(k), visited)) {
//					return true;
//				}
//			}
//
//		}
		
		visited.remove(node);
		return false;
	}
}
