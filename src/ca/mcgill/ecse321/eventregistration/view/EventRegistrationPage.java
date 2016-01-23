package ca.mcgill.ecse321.eventregistration.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;
import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
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
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		eventDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		eventDateLabel = new JLabel();
		startTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(startTimeEditor);
		startTimeLabel = new JLabel();
		endTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner,"HH:mm");
		endTimeSpinner.setEditor(endTimeEditor);
		endTimeLabel = new JLabel();
		addEventButton = new JButton();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("event Registration");
		
		participantLabel.setText("Select Participant:");
		eventLabel.setText("Select Event:");
		registerButton.setText("Register");
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				registerButtonActionPerformed(evt);
				
			}
		});
		
		
		addParticipantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				AddParticipantButtonActionPerformed(evt);
			}
		});
		
		eventNameLabel.setText("Name:");
		eventDateLabel.setText("Date:");
		startTimeLabel.setText("End time:");
		addEventButton.setText("Add Event");
		addEventButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				addEventButtonActionPerformed(evt);
				
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
						.addGroup(layout.createParallelGroup()
								.addComponent(participantLabel)
								.addComponent(registerButton)
								.addComponent(participantNameLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(participantNameTextField,200,200,400)
								.addComponent(addParticipantButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(eventLabel)
								.addComponent(eventNameLabel)
								.addComponent(eventDateLabel)
								.addComponent(startTimeLabel)
								.addComponent(endTimeLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(eventList)
								.addComponent(eventNameTextField,200,200,400)
								.addComponent(eventDatePicker)
								.addComponent(startTimeSpinner)
								.addComponent(endTimeSpinner)
								.addComponent(addEventButton)))
				);
						
	
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {addParticipantButton,participantNameTextField});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {registerButton,participantLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {addEventButton,eventNameTextField});
		
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()
			.addComponent(errorMessage)
			.addGroup(layout.createParallelGroup()
					.addComponent(participantNameLabel)
					.addComponent(participantList)
					.addComponent(eventLabel)
					.addComponent(eventList))
			.addComponent(registerButton)
			.addGroup(layout.createParallelGroup()
					.addComponent(participantNameLabel)
					.addComponent(participantNameTextField)
					.addComponent(eventNameLabel)
					.addComponent(eventNameTextField))
			.addGroup(layout.createParallelGroup()
					.addComponent(eventDateLabel)
					.addComponent(eventDatePicker))
			.addGroup(layout.createParallelGroup()
					.addComponent(startTimeLabel)
					.addComponent(startTimeSpinner))
			.addGroup(layout.createParallelGroup()
					.addComponent(endTimeLabel)
					.addComponent(endTimeSpinner))
			.addGroup(layout.createParallelGroup()
					.addComponent(addParticipantButton)
					.addComponent(addEventButton))
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
		
		RegistrationManager rm = RegistrationManager.getInstance();
		
		errorMessage.setText(error);
		if(error == null||error.length() == 0)
		{
			participants = new HashMap<Integer, Participant>();
			participantList.removeAllItems();
			Iterator<Participant> pIt = rm.getParticipants().iterator();
			Integer index = 0;
			
			while(pIt.hasNext())
			{
				Participant p = pIt.next();
				participants.put(index, p);
				participantList.addItem(p.getName());
				index++;
			}
			
			selectedParticipant = -1;
			participantList.setSelectedIndex(selectedParticipant);
			
			events = new HashMap<Integer,Event>();
			eventList.removeAllItems();
			Iterator<Event> eIt = rm.getEvents().iterator();
			index = 0;
			while (eIt.hasNext())
			{
				Event e = eIt.next();
				events.put(index, e);
				eventList.addItem(e.getName());
				index++;
			}
			
			selectedEvent = -1;
			eventList.setSelectedIndex(selectedEvent);
			
			participantNameTextField.setText("");
			
			eventNameTextField.setText("");
			eventDatePicker.getModel().setValue(null);
			startTimeSpinner.setValue(new Date());
			endTimeSpinner.setValue(new Date());
		
		}
		
		pack();
	}
	
	private void addEventButtonActionPerformed(ActionEvent evt)
	{
		
		EventRegistrationController erc = new EventRegistrationController();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime((Date) startTimeSpinner.getValue());
		calendar.set(2000, 1, 1);
		Time startTime = new Time(calendar.getTime().getTime());
		calendar.setTime((Date) endTimeSpinner.getValue());
		calendar.set(2000, 1,1);
		Time endTime = new Time(calendar.getTime().getTime());
		
		
		
		error = null;
		
		try {
			erc.createEvent(eventNameTextField.getText(), (java.sql.Date)eventDatePicker.getModel().getValue(), startTime, endTime);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			error = e.getMessage();
		}
		
		
		refreshData();
	}
	
	private void registerButtonActionPerformed(ActionEvent evt )
	{
		error = "";
		
		if(selectedParticipant< 0)
		{
			error = error + "Participan needs to be selected for registration!";
		}
		if(selectedEvent<0)
		{
			error = error + "Event needs to be selected for registration!";
		}
		error = error.trim();
		
		if(error.length() == 0)
		{
			EventRegistrationController erc = new EventRegistrationController();
			
			try {
				erc.register(participants.get(selectedParticipant), events.get(selectedEvent));
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				error = e.getMessage();
			}
		}
		
		refreshData();
		
	}
	
	
}
