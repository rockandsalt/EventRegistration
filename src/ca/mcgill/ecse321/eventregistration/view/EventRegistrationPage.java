package ca.mcgill.ecse321.eventregistration.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EventRegistrationPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7728776519554488404L;
	private JPanel contentPane;
	private JTextField participantNameTextField;
	private JLabel participantNameLabel;
	private JButton addParticipantButton;

	/**
	 * Launch the application.
	 */
	public EventRegistrationPage()
	{
		initComponents();
		refreshData();
	}
	
	/**
	 * Create the frame.
	 */
	private void initComponents() {
		
		participantNameTextField = new JTextField();
		participantNameLabel = new JLabel("Name:");
		addParticipantButton = new JButton("Add Participant");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("event Registration");
		
		
		addParticipantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				AddParticipantButtonActionPerformed(evt);
			}
		});
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addComponent(participantNameLabel)
				.addGroup(layout.createParallelGroup()
						.addComponent(participantNameTextField, 200,200,400)
						.addComponent(addParticipantButton))			
	    );
		
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {addParticipantButton,participantNameTextField});
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
					.addComponent(participantNameLabel)
					.addComponent(participantNameTextField))
			.addComponent(addParticipantButton)
				
		);
		
		pack();
		
	}
	
	private void AddParticipantButtonActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		EventRegistrationController erc = new EventRegistrationController();
		
		try {
			erc.createParticipant(participantNameTextField.getText());
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		refreshData();
	}
	
	
	private void refreshData() {
		// TODO Auto-generated method stub
		participantNameTextField.setText("");
		
	}
	
}
