package ca.mcgill.ecse321.eventregistration.application;

import ca.mcgill.ecse321.eventregistration.persistence.PersistenceEventRegistration;
import ca.mcgill.ecse321.eventregistration.view.EventRegistrationPage;

public class EventRegistration {

	public static void main(String[] args) {
		
		PersistenceEventRegistration.loadEventRegistrationModel();
		
		// TODO Auto-generated method stub
		java.awt.EventQueue.invokeLater(new Runnable() {
			
			public void run(){new EventRegistrationPage().setVisible(true);}
			}
		);

	}

}
