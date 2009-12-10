 /**
 * 
 */
package graphics;
//Start, clear, reset
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Model;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainGUI extends JFrame{
	private Model model = Model.getObj(550,550);
	// Model Call goes here
	static JFrame window = new JFrame();
	/** Menu Bar */
	private JMenuBar menuBar;
	private JMenu m_file, m_scenario, m_options;
	private JMenuItem m_mainmenu, m_start, m_clear, m_reset, m_save, m_load, m_gravity;
	JFileChooser file = new JFileChooser("./Scenarios/");
    FileFilter filter = new FileNameExtensionFilter("State Files", "state");
	/** Master panel*/
	static JPanel master = new JPanel();
	/** Main panel*/
	static Image rubeG;
	MainMenuPanel mmp = new MainMenuPanel();
	JPanel main = new JPanel();
	JLabel intro = new JLabel("Please select and option: ");
	JButton startB = new JButton("Start");
	JButton scenarioB = new JButton("Scenarios");
	JButton optionB = new JButton("Options");
	/** Options panel*/
	JPanel options = new JPanel();
	JLabel optionL = new JLabel("Options:");
	JLabel gravityL = new JLabel("Gravity:");
	JTextField gravityText = new JTextField("" + model.getGravity());
	JButton submit = new JButton("Submit");
	JLabel soundL = new JLabel("Sound:");
	JButton soundon = new JButton("Sound ON");
	JButton soundoff = new JButton("Sound OFF");
	/** Sandbox Panel*/
	JPanel sandbox = new JPanel();
	SandboxPanel sandboxPanel = new SandboxPanel(model, 500, 500);
	//JButton mainMenuSandbox = new JButton("Main Menu");

	
	
	/**
	 * Main method creates a GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			window = new MainGUI();
			window.setTitle("Rube Goldburg Machine");
			window.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor to setup frame
	 */
	public MainGUI(){
		try
		{
			rubeG = ImageIO.read(new File("Images/Rubenvent.gif"));
			//Add other objects to read in.
		} catch (IOException e)
		{	
		}
		setupJFrameModel();
		registerListeners();
	}
	
	/**
	 * Sets up the JFrame and master panel
	 */
	public void setupJFrameModel(){
		this.setSize(950, 650);
		this.setLayout(null);
		this.setLocation(150, 50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = this.getContentPane();
		
		setJMenuBar(createMenuBar());
		
		// Sets master panel specifications
		master.setLayout(null);
		master.setSize(950, 650);
		master.setLocation(0,0);
		
		// Sets up all individual frames
		setupMain();
		repaint();
		setupOptions();
		setupSandbox();
		
		// First panel added
		//master.add(main);
		//master.add(scenario);
		//master.add(options);
		//master.add(sandbox);
		master.add(mmp);
		cp.add(master);
	}
	
	public JMenuBar createMenuBar() {
		menuBar = new JMenuBar();

		m_file = new JMenu("File");
		m_scenario = new JMenu("Scenario");
		m_options = new JMenu("Options");
		//File menu
		m_mainmenu = new JMenuItem("Main Menu");
		m_start = new JMenuItem("Start");
		m_clear = new JMenuItem("Clear Sandbox");
		m_reset = new JMenuItem("Reset");
		m_file.add(m_mainmenu);
		m_file.add(m_start);
		m_file.add(m_clear);
		m_file.add(m_reset);
		// Scenario menu
		m_save = new JMenuItem("Save Scenario");
		m_load = new JMenuItem("Load Scenario");
		m_scenario.add(m_save);
		m_scenario.add(m_load);
		// Options
		m_gravity = new JMenuItem("Change Gravity");
		m_options.add(m_gravity);
		// Menu Bar
		menuBar.add(m_file);
		menuBar.add(m_scenario);
		menuBar.add(m_options);
		return menuBar;
	}
	
	public void registerListeners(){
		// Menu options
		m_mainmenu.addActionListener(new userChoice());
		m_start.addActionListener(new userChoice());
		m_clear.addActionListener(new userChoice());
		m_reset.addActionListener(new userChoice());
		m_save.addActionListener(new userChoice());
		m_load.addActionListener(new userChoice());
		m_gravity.addActionListener(new userChoice());
		
		//Sandbox
		
		//Options
		submit.addActionListener(new submitButtonListener());
//		soundon.addActionListener(new soundOnButtonListener());
//		soundoff.addActionListener(new soundOffButtonListener());
		//Scenario
//		mainMenuScenario.addActionListener(new mainMenuScenarioButtonListener());
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D o = (Graphics2D) g;
		// Static background images
		o.drawImage(rubeG, 10, 10, main);
	}
	
	public void setupMain(){
		
		main.setLayout(null);
		main.setSize(950, 500);
		main.setLocation(0,0);
		
		intro.setSize(250, 20);
		intro.setLocation(365, 100);
		
		startB.setSize(150, 40);
		startB.setLocation(400, 200);
		
		scenarioB.setSize(150, 40);
		scenarioB.setLocation(400, 250);
		
		optionB.setSize(150, 40);
		optionB.setLocation(400, 300);
		
		main.add(intro);
		main.add(startB);
		main.add(scenarioB);
		main.add(optionB);
	}
	
	public void setupSandbox(){
		sandbox.setLayout(null);
		sandbox.setSize(950, 600);
		sandbox.setLocation(0,0);
		
		//mainMenuSandbox.setSize(125,30);
		//mainMenuSandbox.setLocation(50, 20);
		
		//sandbox.add(mainMenuSandbox);
		sandbox.add(sandboxPanel);
	}
	
	public void setupOptions(){
		options.setLayout(null);
		options.setSize(950, 500);
		options.setLocation(0,0);
		
		Font optionLf = new Font("serif", Font.BOLD, 18);

		optionL.setSize(250, 30);
		optionL.setLocation(380, 50);
		optionL.setFont(optionLf);
		
		Font gravityLf = new Font("serif", Font.BOLD, 14);
		
		gravityL.setSize(250, 20);
		gravityL.setLocation(275, 100);
		gravityL.setFont(gravityLf);
		
		gravityText.setSize(150, 30);
		gravityText.setLocation(275, 130);
		//Set the text from the model;
		//gravityText.setText(model.)
		
		submit.setSize(100, 30);
		submit.setLocation(435, 130);
		
		soundL.setSize(250, 20);
		soundL.setLocation(275, 165);
		soundL.setFont(gravityLf);
		
		soundon.setSize(125, 40);
		soundon.setLocation(275, 200);
		
		soundoff.setSize(125, 40);
		soundoff.setLocation(410, 200);
		
		options.add(optionL);
		options.add(gravityL);
		options.add(gravityText);
		options.add(submit);
		//options.add(soundL);
		//options.add(soundon);
		//options.add(soundoff);
	}
	
	
	
	
	
	// All Listeners for Main window
	/**
	 * This action listener listens for a start button click and handles that
	 * action.
	 */
	public class startButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			master.removeAll();
			master.add(sandbox);
			master.updateUI();
			repaint();
		}
	}

	/**
	 * This action listener listens for a option button click and handles that
	 * action.
	 */
	public class optionButtonListener implements ActionListener {
	
		public void actionPerformed(ActionEvent arg0) {
			master.removeAll();
			master.add(options);
			master.updateUI();
			repaint();
		}
	}
	
	
	//All listeners for Scenario window
//	/**
//	 * This action listener listens for a Main Menu Scenario button click and handles that
//	 * action.
//	 */
//	public class mainMenuScenarioButtonListener implements ActionListener {
//
//		public void actionPerformed(ActionEvent arg0) {
//			master.removeAll();
//			master.add(main);
//			master.updateUI();
//			
//		}
//	}
	
	/**
	 * This action listener listens for a Scenario 1 button click and handles that
	 * action.
	 */
	public class s1ButtonListener implements ActionListener {
	
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}
	
	/**
	 * This action listener listens for a Submit Options button click and handles that
	 * action.
	 */
	public class submitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			String temp = gravityText.getText();
			model.setGravity(Float.parseFloat(temp));
		}
	}
	
//	/**
//	 * This action listener listens for a Sound On Options button click and handles that
//	 * action.
//	 */
//	public class soundOnButtonListener implements ActionListener {
//	
//		public void actionPerformed(ActionEvent arg0) {
//			
//		}
//	}
//	
//	/**
//	 * This action listener listens for a Sound Off Options button click and handles that
//	 * action.
//	 */
//	public class soundOffButtonListener implements ActionListener {
//	
//		public void actionPerformed(ActionEvent arg0) {
//			
//			
//		}
//	}
	

	
	public class userChoice implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == m_mainmenu) {
				master.removeAll();
				master.add(mmp);
				master.updateUI();
				main.repaint();
			}
			if (e.getSource() == m_start) {
				master.removeAll();
				master.add(sandbox);
				master.updateUI();
				repaint();
			}

			if (e.getSource() == m_clear) {
				model.reset();
				model.setStarted(false);
				model.setRunning(false);
			}

			if (e.getSource() == m_reset) {
				//model.getOriginalState();
				model.setStarted(false);
				model.setRunning(false);
			}	
			if (e.getSource() == m_save) {
				model.saveState("Scenario" + 1 + ".state", model.getObj());
			}
			if (e.getSource() == m_load) {
//				file.addChoosableFileFilter(filter);
//                int returnVal = file.showDialog(null, "Open File");
//                if(returnVal == JFileChooser.APPROVE_OPTION){
//                        File fileOpen = file.getSelectedFile();
//                        System.out.println(file + " was opened");
//                }
//				model.loadState(file.toString());
				model.loadState(".");
				sandbox.repaint();
			}
			if (e.getSource() == m_gravity) {
				master.removeAll();
				master.add(options);
				master.updateUI();
				repaint();
			}
			
	}
	}
}

