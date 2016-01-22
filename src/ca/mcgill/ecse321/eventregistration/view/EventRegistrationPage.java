package ca.mcgill.ecse321.eventregistration.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;
import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EventRegistrationPage extends JFrame {

	private static final long serialVersionUID = 4044106250878687576L;
	
	private JLabel errorMessage;
	private JTextField participantNameTextField;
	private JLabel participantNameLabel;
	private JButton addParticipantButton;
	
	private JComboBox<String> participantList;
	private JLabel participantLabel;
	private JComboBox<String> eventList;
	private JLabel eventLabel;
	private JButton registerButton;
	private JTextField eventNameTextField;
	private JLabel eventNameLabel;
	private JDatePickerImpl eventDatePicker;
	private JLabel eventDateLabel;
	private JSpinner startTimeSpinner;
	private JLabel startTimeLabel;
	private JSpinner endTimeSpinner;
	private JLabel endTimeLabel;
	private JButton addEventButton;
	

	
	private String error = null;
	private Integer selectedParticipant = -1;
	private HashMap<Integer, Participant> participants;
	private Integer selectedEvent = -1;
	private HashMap<Integer, Event> events;
	
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
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		participantList = new JComboBox<String>(new String[0]);
		participantList.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) {
				
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedParticipant = cb.getSelectedIndex();
			}
		});
		
		participantLabel = new JLabel();
		eventList = new JComboBox<String>(new String[0]);
		eventList.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) {
				
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedEvent = cb.getSelectedIndex();
			}
		});
		
		eventLabel = new JLabel();
		
		registerButton = new JButton();
		
		
		//element Participant
		participantNameTextField = new JTextField();
		participantNameLabel = new JLabel("Name:");
		addParticipantButton = new JButton("Add Participant");
		
		//element for event
		eventNameTextField = new JTextField();
		eventNameLabel = new JLabel();
		
		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("event Registration");
		
		
		addParticipantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				AddParticipantButtonActionPerformed(evt);
			}
		});
		
		//Layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createSequentialGroup()
				.addComponent(participantNameLabel)
				.addGroup(layout.createParallelGroup()
						.addComponent(participantNameTextField, 200,200,400)
						.addComponent(addParticipantButton)))			
	    );
		
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {addParticipantButton,participantNameTextField});
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
			.addComponent(errorMessage)
			.addGroup(layout.createParallelGroup()
					.addComponent(participantNameLabel)
					.addComponent(participantNameTextField))
			.addComponent(addParticipantButton)
				
		);
		
		pack();
		
	}
	
	private void AddParticipantButtonActionPerformed(ActionEvent evt) {
		
		EventRegistrationController erc = new EventRegistrationController();
		
		error = null;
		
		try {
			erc.createParticipant(participantNameTextField.getText());
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		refreshData();
	}
	
	
	private void refreshData() {
		
		errorMessage.setText(error);
		participantNameTextField.setText("");
		
		pack();
	}
	
}
