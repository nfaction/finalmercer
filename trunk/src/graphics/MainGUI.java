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
	JButton mainMenu = new JButton("Main Menu");
	JLabel gravityL = new JLabel("Gravity:");
	JTextField gravityText = new JTextField();
	JButton soundon = new JButton("Sound ON");
	JButton soundoff = new JButton("Sound OFF");
	/** Sandbox Panel*/
	JPanel sandbox = new JPanel();
	
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
		this.setSize(950, 550);
		this.setLocation(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		Container cp = this.getContentPane();
		
		// Sets master panel specifications
		master.setLayout(null);
		master.setSize(950, 550);
		master.setLocation(0,0);
		
		// Sets up all individual frames
		setupMain();
		setupOptions();
		setupScenarios();
		setupSandbox();
		
		// First panel added
		master.add(main);
		//master.add(scenario);
		//master.add(options);
		cp.add(master);
	}
	
	public void registerListeners(){
		
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
	
	public void setupScenarios(){
		scenario.setLayout(null);
		scenario.setSize(950, 500);
		scenario.setLocation(0,0);
		
		select.setSize(250, 20);
		select.setLocation(50, 20);
		
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
		
		optionL.setSize(250, 20);
		optionL.setLocation(365, 50);
		
		mainMenu.setSize(200, 40);
		mainMenu.setLocation(50, 20);
		
		gravityL.setSize(250, 20);
		gravityL.setLocation(365, 100);
		
		gravityText.setSize(150, 20);
		gravityText.setLocation(365, 150);
		
		soundon.setSize(200, 40);
		soundon.setLocation(365, 180);
		
		soundoff.setSize(200, 40);
		soundoff.setLocation(575, 180);
		
		options.add(optionL);
		options.add(mainMenu);
		options.add(gravityL);
		options.add(gravityText);
		options.add(soundon);
		options.add(soundoff);
	}
	
	
	
	public void setupSandbox(){
		
	}
	
	
	
	
	
}
