package edu.teco.pavos.sss;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * Main Window of the simulator
 * @author Jean Baumgarten
 */
public class Window implements ActionListener {
	
	private JFrame frame;
	private Container contentPane;
	private SpringLayout layout;
	private JButton button;
	
	private JTextField urlField;
	private boolean simulationActive;
	private HashSet<Thread> threads = new HashSet<Thread>();

	/**
	 * Default Constructor
	 */
	public Window() {
		
		this.simulationActive = false;
		this.gui();
		
	}
	
	private void run() {
		
		this.threads = new HashSet<Thread>();
		
		for (int la = 47; la < 55; la += 2) {
			for (int lo = 4; lo < 16; lo += 4) {
            	final double fla = la;
            	final double flo = lo;
				Thread aThread = new Thread(new Runnable() {
		            public void run() {
		                ThreadInfo ti = new ThreadInfo(fla, flo);
		                new Simulator(ti, urlField.getText());
		            }
		        });
		        aThread.start();
		        this.threads.add(aThread);
			}
		}
		
	}
	
	private void gui() {
		
		this.frame = new JFrame("Sensor Source Simulator");
        this.contentPane = this.frame.getContentPane();
        this.layout = new SpringLayout();
        this.contentPane.setLayout(layout);
        
        this.urlField = new JTextField("http://pavos-master.teco.edu/FROST-Server/v1.0/");
        contentPane.add(this.urlField);
        layout.putConstraint(SpringLayout.WEST, this.urlField, 20, SpringLayout.WEST, this.contentPane);
        layout.putConstraint(SpringLayout.EAST, this.urlField, -20, SpringLayout.EAST, this.contentPane);
        layout.putConstraint(SpringLayout.NORTH, this.urlField, 20, SpringLayout.NORTH, this.contentPane);
        
        this.button = new JButton("Start simulating Sensors");
        button.setName("Button");
        this.contentPane.add(button);
        this.layout.putConstraint(SpringLayout.WEST, button, 20, SpringLayout.WEST, this.contentPane);
        this.layout.putConstraint(SpringLayout.EAST, button, -20, SpringLayout.EAST, this.contentPane);
        this.layout.putConstraint(SpringLayout.NORTH, button, 20, SpringLayout.SOUTH, this.urlField);
        this.layout.putConstraint(SpringLayout.SOUTH, button, -20, SpringLayout.SOUTH, this.contentPane);
        button.addActionListener(this);
        
        this.frame.setSize(350, 200);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		this.simulationActive = !this.simulationActive;
		
		if (this.simulationActive) {
			
			Simulator.prepareAll(this.urlField.getText());
			this.button.setText("Stop simulating Sensors");
			this.run();
			
		} else {
			
			this.button.setText("Start simulating Sensors");
			for (Thread t : this.threads) {
				t.interrupt();
			}
			
		}
		
	}

}
