package edu.teco.pavos.sss;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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

	/**
	 * Default Constructor
	 */
	public Window() {
		
		this.frame = new JFrame("Sensor Source Simulator");
        this.contentPane = this.frame.getContentPane();
        this.layout = new SpringLayout();
        this.contentPane.setLayout(layout);
        
        this.button = new JButton("Start simulating Sensors");
        button.setName("Button");
        this.contentPane.add(button);
        this.layout.putConstraint(SpringLayout.WEST, button, 20, SpringLayout.WEST, this.contentPane);
        this.layout.putConstraint(SpringLayout.EAST, button, -20, SpringLayout.EAST, this.contentPane);
        this.layout.putConstraint(SpringLayout.NORTH, button, 20, SpringLayout.NORTH, this.contentPane);
        this.layout.putConstraint(SpringLayout.SOUTH, button, -20, SpringLayout.SOUTH, this.contentPane);
        button.addActionListener(this);
        
        this.frame.setSize(300, 150);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
