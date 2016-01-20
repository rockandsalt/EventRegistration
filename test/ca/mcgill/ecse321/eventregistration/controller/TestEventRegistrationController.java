package ca.mcgill.ecse321.eventregistration.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXstream;

public class TestEventRegistrationController {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		PersistenceXstream.setFilename("test"+File.separator+"ca"+File.separator+"mcgill"+File.separator+"ecse321"+File.separator+"eventregistration"
				+File.separator+"controller"+File.separator+"data.xml");
		PersistenceXstream.setAlias("event", Event.class);
		PersistenceXstream.setAlias("participant", Participant.class);
		PersistenceXstream.setAlias("registration", Registration.class);
		PersistenceXstream.setAlias("manager", Registration.class);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		RegistrationManager rm = RegistrationManager.getInstance();
		rm.delete();
	}

	
	@Test
	public void test() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipants().size());
		
		String name = "Oscar";
		
		EventRegistrationController erc = new EventRegistrationController();
		erc.createParticipant(name);
		
		//check model memory
		assertEquals(1, rm.getParticipants().size());
		assertEquals(name , rm.getParticipant(0).getName());
		assertEquals(0 , rm.getEvents().size());
		assertEquals(0, rm.getRegistrations().size());
		
		RegistrationManager rm2 = (RegistrationManager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals(1 ,rm2.getParticipants().size());
		assertEquals(name, rm2.getParticipant(0).getName());
		assertEquals(0, rm2.getEvents().size());
		assertEquals(0, rm2.getRegistrations().size());
		
		
	
	}

}
