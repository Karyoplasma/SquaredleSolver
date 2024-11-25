package gui;

import java.awt.AWTException;
import java.awt.EventQueue;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import actions.URLLabelMouseAdapter;
import core.AutoTypeRobot;
import core.Puzzle;
import actions.AutoTypeButtonAction;
import actions.PuzzleComboBoxActionListener;
import actions.SolveButtonAction;
import javax.swing.JComboBox;

public class SquaredleSolverGUI {
	
	private List<String> solutions;
	private JFrame frmSquaredleCleaner;
	private JTextArea textAreaResults;
	private JButton btnSolve;
	private JTextField textFieldBoard;
	private JButton btnAutotype;
	private JComboBox<Puzzle> comboBox_puzzles;
	private AutoTypeRobot robot;

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
		lblURL.addMouseListener(new URLLabelMouseAdapter());
		
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
		
		btnSolve = new JButton(new SolveButtonAction(this));
		btnSolve.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmSquaredleCleaner.getContentPane().add(btnSolve, "flowx,cell 1 0");
		
		btnAutotype = new JButton(new AutoTypeButtonAction(this));
		btnAutotype.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAutotype.setEnabled(false);
		frmSquaredleCleaner.getContentPane().add(btnAutotype, "cell 1 0");
		
		comboBox_puzzles = new JComboBox<Puzzle>(Puzzle.values());
		comboBox_puzzles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_puzzles.addActionListener(new PuzzleComboBoxActionListener(this));
		frmSquaredleCleaner.getContentPane().add(comboBox_puzzles, "cell 1 0,growx");
		
		try {
			this.robot = new AutoTypeRobot();
		} catch (AWTException e) {
			this.robot = null;
		}

	}
	
	public Puzzle getPuzzleSelection() {
		return (Puzzle) this.comboBox_puzzles.getSelectedItem();
	}
	
	public void toggleButtonText() {
		if (this.btnAutotype.getText().equals("Cancel")) {
			this.btnAutotype.setText("Auto-type");
		} else {
			this.btnAutotype.setText("Cancel");
		}
		
	}
	
	public void setBoard(String board) {
		this.textFieldBoard.setText(board);
	}
	
	public void setResultText(String text) {
		this.textAreaResults.setText(text);
	}
	
	public void unlockAutoType() {
		this.btnAutotype.setEnabled(true);
	}
	
	public String getBoard() {
		return this.textFieldBoard.getText();
	}
	
	public AutoTypeRobot getRobot() {
		return this.robot;
	}
	
	public List<String> getSolutions(){
		return this.solutions;
	}
	
	public void setSolutions(List<String> solutions){
		this.solutions = solutions;
	}
}
