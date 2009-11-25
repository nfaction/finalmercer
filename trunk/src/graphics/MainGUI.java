/**
 * 
 */
package graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainGUI extends JFrame{
	// Model Call goes here
	static JFrame window = new JFrame();
	/** Master panel*/
	static JPanel master = new JPanel();
	/** Main panel*/
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
	JButton mainMenuOptions = new JButton("Main Menu");
	JLabel gravityL = new JLabel("Gravity:");
	JTextField gravityText = new JTextField();
	JButton submit = new JButton("Submit");
	JLabel soundL = new JLabel("Sound:");
	JButton soundon = new JButton("Sound ON");
	JButton soundoff = new JButton("Sound OFF");
	/** Sandbox Panel*/
	JPanel sandbox = new JPanel();
	SandboxPanel sandboxPanel = new SandboxPanel();
	JButton mainMenuSandbox = new JButton("Main Menu");
	
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
		setupJFrameModel();
		registerListeners();
	}
	
	/**
	 * Sets up the JFrame and master panel
	 */
	public void setupJFrameModel(){
		this.setSize(950, 630);
		this.setLayout(null);
		this.setLocation(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = this.getContentPane();
		
		// Sets master panel specifications
		master.setLayout(null);
		master.setSize(950, 600);
		master.setLocation(0,0);
		
		// Sets up all individual frames
		setupMain();
		setupOptions();
		setupScenarios();
		setupSandbox();
		
		// First panel added
		//master.add(main);
		//master.add(scenario);
		//master.add(options);
		master.add(sandbox);
		cp.add(master);
	}
	
	public void registerListeners(){
		startB.addActionListener(new startButtonListener());
		scenarioB.addActionListener(new scenarioButtonListener());
		optionB.addActionListener(new optionButtonListener());
		//Sandbox
		mainMenuSandbox.addActionListener(new mainMenuSandboxButtonListener());
		//Options
		mainMenuOptions.addActionListener(new mainMenuOptionsButtonListener());
		submit.addActionListener(new submitButtonListener());
		soundon.addActionListener(new soundOnButtonListener());
		soundoff.addActionListener(new soundOffButtonListener());
		//Scenario
		mainMenuScenario.addActionListener(new mainMenuScenarioButtonListener());
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
		
		mainMenuSandbox.setSize(125,30);
		mainMenuSandbox.setLocation(50, 20);
		
		sandbox.add(mainMenuSandbox);
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
		
		mainMenuOptions.setSize(125,30);
		mainMenuOptions.setLocation(50, 20);
		
		Font gravityLf = new Font("serif", Font.BOLD, 14);
		
		gravityL.setSize(250, 20);
		gravityL.setLocation(275, 100);
		gravityL.setFont(gravityLf);
		
		gravityText.setSize(150, 30);
		gravityText.setLocation(275, 130);
		
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
		options.add(mainMenuOptions);
		options.add(gravityL);
		options.add(gravityText);
		options.add(submit);
		options.add(soundL);
		options.add(soundon);
		options.add(soundoff);
	}
	
	
	
	
	
	// All Listeners for Main window
	/**
	 * This action listener listens for a start button click and handles that
	 * action.
	 */
	public class startButtonListener implements ActionListener {
		@Override
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
		@Override
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
		@Override
		public void actionPerformed(ActionEvent arg0) {
			master.removeAll();
			master.add(options);
			master.updateUI();
			repaint();
		}
	}
	
	//All listeners for Sandbox window
	/**
	 * This action listener listens for a Main Menu Scenario button click and handles that
	 * action.
	 */
	public class mainMenuSandboxButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			master.removeAll();
			master.add(main);
			master.updateUI();
			
		}
	}
	
	//All listeners for Scenario window
	/**
	 * This action listener listens for a Main Menu Scenario button click and handles that
	 * action.
	 */
	public class mainMenuScenarioButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			master.removeAll();
			master.add(main);
			master.updateUI();
			
		}
	}
	
	/**
	 * This action listener listens for a Scenario 1 button click and handles that
	 * action.
	 */
	public class s1ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}
	
	//All listeners for Options window
	/**
	 * This action listener listens for a Main Menu Options button click and handles that
	 * action.
	 */
	public class mainMenuOptionsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			master.removeAll();
			master.add(main);
			master.updateUI();
			
		}
	}
	
	/**
	 * This action listener listens for a Submit Options button click and handles that
	 * action.
	 */
	public class submitButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}
	
	/**
	 * This action listener listens for a Sound On Options button click and handles that
	 * action.
	 */
	public class soundOnButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}
	
	/**
	 * This action listener listens for a Sound Off Options button click and handles that
	 * action.
	 */
	public class soundOffButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}
	}
}
