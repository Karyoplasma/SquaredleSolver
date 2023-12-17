package gui;

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
import actions.AutoTypeButtonAction;
import actions.SolveButtonAction;

public class SquaredleSolverGUI {
	
	public List<String> solutions;
	private JFrame frmSquaredleCleaner;
	public JTextArea textAreaResults;
	private JButton btnSolve;
	public JTextField textFieldBoard;
	public JButton btnAutotype;

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
	}
}
