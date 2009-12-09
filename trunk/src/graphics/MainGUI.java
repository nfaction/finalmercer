 /**
 * 
 */
package graphics;
//Start, clear, reset
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
	private Model model = Model.getObj(500,500);
	// Model Call goes here
	static JFrame window = new JFrame();
	/** Menu Bar */
	private JMenuBar menuBar;
	private JMenu m_file, m_scenario, m_options;
	private JMenuItem m_mainmenu, m_start, m_clear, m_reset, m_save, m_load, m_gravity;
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
	/** Scenarios panel*/
	JPanel scenario = new JPanel();
	JLabel select = new JLabel("Please select a scenario:");
	JButton mainMenuScenario = new JButton("Main Menu");
	JButton s1 = new JButton("Scenario 1");
	JButton s2 = new JButton("Scenario 2");
	JButton s3 = new JButton("Scenario 3");
	JButton s4 = new JButton("Scenario 4");
	JButton s5 = new JButton("Scenario 5");
	JButton s6 = new JButton("Scenario 6");
	JButton s7 = new JButton("Scenario 7");
	JButton s8 = new JButton("Scenario 8");
	JButton s9 = new JButton("Scenario 9");
	JButton s10 = new JButton("Scenario 10");
	/** Options panel*/
	JPanel options = new JPanel();
	JLabel optionL = new JLabel("Options:");
	JLabel gravityL = new JLabel("Gravity:");
	JTextField gravityText = new JTextField("" );//+ model.getGravity());
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
		window = new MainGUI();
		window.setTitle("Rube Goldburg Machine");
		window.setVisible(true);
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
		this.setSize(950, 630);
		this.setLayout(null);
		this.setLocation(150, 50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = this.getContentPane();
		
		setJMenuBar(createMenuBar());
		
		// Sets master panel specifications
		master.setLayout(null);
		master.setSize(950, 600);
		master.setLocation(0,0);
		
		// Sets up all individual frames
		setupMain();
		repaint();
		setupOptions();
		setupScenarios();
		setupSandbox();
		
		// First panel added
		//master.add(main);
		//master.add(scenario);
		//master.add(options);
		master.add(sandbox);
		//master.add(mmp);
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
		
		startB.addActionListener(new startButtonListener());
		scenarioB.addActionListener(new scenarioButtonListener());
		optionB.addActionListener(new optionButtonListener());
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
	
	public void setupScenarios(){
		scenario.setLayout(null);
		scenario.setSize(950, 500);
		scenario.setLocation(0,0);
		
		select.setSize(250, 20);
		select.setLocation(50, 20);
		
		mainMenuScenario.setSize(125, 30);
		mainMenuScenario.setLocation(800, 10);
		
		// First Column
		s1.setSize(150, 80);
		s1.setLocation(50, 50);
		
		s2.setSize(150, 80);
		s2.setLocation(50, 140);
		
		s3.setSize(150, 80);
		s3.setLocation(50, 230);
		
		s4.setSize(150, 80);
		s4.setLocation(50, 320);
		
		s5.setSize(150, 80);
		s5.setLocation(50, 410);
		
		// Second Column
		s6.setSize(150, 80);
		s6.setLocation(450, 50);
		
		s7.setSize(150, 80);
		s7.setLocation(450, 140);
		
		s8.setSize(150, 80);
		s8.setLocation(450, 230);
		
		s9.setSize(150, 80);
		s9.setLocation(450, 320);
		
		s10.setSize(150, 80);
		s10.setLocation(450, 410);
		
		scenario.add(select);
		scenario.add(mainMenuScenario);
		scenario.add(s1);
		scenario.add(s2);
		scenario.add(s3);
		scenario.add(s4);
		scenario.add(s5);
		scenario.add(s6);
		scenario.add(s7);
		scenario.add(s8);
		scenario.add(s9);
		scenario.add(s10);
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
	 * This action listener listens for a scenario button click and handles that
	 * action.
	 */
	public class scenarioButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			master.removeAll();
			master.add(scenario);
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
			}

			if (e.getSource() == m_reset) {
				//model.getOriginalState();
			}	
			if (e.getSource() == m_save) {
				
			}
			if (e.getSource() == m_load) {
				
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

