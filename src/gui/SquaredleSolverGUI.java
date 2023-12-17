package gui;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.EventQueue;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import javax.swing.JTextArea;

import core.AutoTypeRobot;
import core.SquaredleBoard;
import core.SquaredleSolver;
import core.WordListComparator;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class SquaredleSolverGUI implements ActionListener{
	
	final String urlString = "https://minoli-g.github.io/squaredle-solver/";
	final String newLine = System.getProperty("line.separator");
	private List<String> solutions;
	private JFrame frmSquaredleCleaner;
	private JTextArea textAreaResults;
	private JButton btnSolve;
	private JTextField textFieldBoard;
	private JButton btnAutotype;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SquaredleSolverGUI window = new SquaredleSolverGUI();
					window.frmSquaredleCleaner.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SquaredleSolverGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSquaredleCleaner = new JFrame();
		frmSquaredleCleaner.setTitle("Squaredle Solver");
		frmSquaredleCleaner.setBounds(100, 100, 800, 600);
		frmSquaredleCleaner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSquaredleCleaner.getContentPane().setLayout(new MigLayout("", "[grow][grow]", "[][grow][]"));
		
		JLabel lblURL = new JLabel("Inspired by: https://minoli-g.github.io/squaredle-solver/");
		lblURL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					try {
						URI url = new URI(urlString);
						Desktop.getDesktop().browse(url);
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}		
			}
		});
		
		textFieldBoard = new JTextField();
		textFieldBoard.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmSquaredleCleaner.getContentPane().add(textFieldBoard, "cell 0 0,growx");
		textFieldBoard.setColumns(10);
		lblURL.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmSquaredleCleaner.getContentPane().add(lblURL, "flowx,cell 0 2 2 1");
		
		JScrollPane scrollPaneResultList = new JScrollPane();
		frmSquaredleCleaner.getContentPane().add(scrollPaneResultList, "cell 0 1 2 1,grow");
		
		textAreaResults = new JTextArea();
		textAreaResults.setLineWrap(true);
		textAreaResults.setEditable(false);
		textAreaResults.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPaneResultList.setViewportView(textAreaResults);
		
		btnSolve = new JButton("Solve");
		btnSolve.addActionListener(this);
		btnSolve.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmSquaredleCleaner.getContentPane().add(btnSolve, "flowx,cell 1 0");
		
		btnAutotype = new JButton("Auto-type");
		btnAutotype.addActionListener(this);
		btnAutotype.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAutotype.setEnabled(false);
		frmSquaredleCleaner.getContentPane().add(btnAutotype, "cell 1 0");
	}
	
	private void arrangeResults(List<String> solutions) {
		Collections.sort(solutions, new WordListComparator());
		StringBuffer buffer = new StringBuffer();
		int currentSize = 3;
		boolean start = true;
		char currentLetter = '.';
		buffer.append("Found ").append(solutions.size()).append(" candidates:");
		buffer.append(newLine).append(newLine);
		for (String word : solutions) {
			if (word.length() > currentSize) {
				currentSize = word.length();
				currentLetter = '.';
				if (!start) {
					buffer.append(newLine);
				}
				buffer.append(currentSize + "-letter words:").append(newLine);
				start = false;
			}
			if (currentLetter == '.') {
				currentLetter = word.charAt(0);
				buffer.append(word);
				continue;
			}
			if (currentLetter == word.charAt(0)) {
				buffer.append("\t").append(word);
			} else {
				currentLetter = word.charAt(0);
				buffer.append(newLine).append(word);
			}
			
		}
		textAreaResults.setText(buffer.toString());	
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSolve) {
			if (textFieldBoard.getText().equals("")) {
				return;
			}				
			try {
				SquaredleBoard board = new SquaredleBoard(textFieldBoard.getText());
				SquaredleSolver solver = new SquaredleSolver(board);
				this.solutions = solver.solveSquaredle();
				this.arrangeResults(this.solutions);
				btnAutotype.setEnabled(true);
			} catch (IllegalArgumentException boardException) {
				textAreaResults.setText(boardException.getMessage());
			}				
		}
		if (e.getSource() == btnAutotype) {
			if (!(this.solutions == null) || !this.solutions.isEmpty()) {
				try {
//					Robot robot = new Robot();
//					Thread.sleep(2000);
//					for (String word : this.solutions) {
//						for (int i = 0; i < word.length(); i++) {
//							robot.keyPress(word.charAt(i) - 32);
//							Thread.sleep(10);
//							robot.keyRelease(word.charAt(i) - 32);
//							Thread.sleep(100);
//						}
//						robot.keyPress(KeyEvent.VK_ENTER);
//						Thread.sleep(10);
//						robot.keyRelease(KeyEvent.VK_ENTER);
//						Thread.sleep(200);
//					}
					AutoTypeRobot robot = new AutoTypeRobot();
					Thread.sleep(2000);
					for (String word : this.solutions) {
						robot.enterWord(word);
					}
				} catch (AWTException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
}
