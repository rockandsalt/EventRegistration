package ca.mcgill.ecse321.eventregistration.persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;

public class TestPersistence {

	@Before
	public void setUp() throws Exception {
		RegistrationManager rm = RegistrationManager.getInstance();
		
		Participant pa = new Participant("Martin");
		Participant pa2 = new Participant("Jennifer");
		
		Calendar c  = Calendar.getInstance();
		c.set(2015, Calendar.SEPTEMBER,15,8,30,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2015, Calendar.SEPTEMBER,15, 10, 0 , 0 );
		Time endTime = new Time(c.getTimeInMillis());
		Event ev = new Event("Concert", eventDate, startTime, endTime);
		
		Registration re = new Registration(pa,ev);
		Registration re2 = new Registration(pa2, ev);
		
		rm.addRegistration(re);
		rm.addRegistration(re2);
		rm.addEvent(ev);
		rm.addParticipant(pa);
		rm.addParticipant(pa2);
		
	}

	@After
	public void tearDown() throws Exception {
		RegistrationManager rm = RegistrationManager.getInstance();
		rm.delete();
		
	}

	@Test
	public void test() {
		
		RegistrationManager rm = RegistrationManager.getInstance();
		PersistenceXstream.setFilename("test"+File.separator+"ca"+File.separator+"mcgill"+File.separator+"ecse321"+File.separator+"eventregistration"
				+File.separator+"persistence"+File.separator+"data.xml");
		PersistenceXstream.setAlias("event", Event.class);
		PersistenceXstream.setAlias("participant", Participant.class);
		PersistenceXstream.setAlias("registration", Registration.class);
		PersistenceXstream.setAlias("manager", Registration.class);
		
		if(!PersistenceXstream.saveToXMLwithXStream(rm))
		{
			fail("could not save your file");
		}
		
		
		rm.delete();
		assertEquals(0,rm.getParticipants().size());
		assertEquals(0, rm.getEvents().size());
		assertEquals(0,rm.getRegistrations().size());
		
		rm = (RegistrationManager) PersistenceXstream.loadFromXMLwithXStream();
		
		if(rm == null){fail("could not load file");}
		
		assertEquals(2,rm.getParticipants().size());
		assertEquals("Martin", rm.getParticipant(0).getName());
		assertEquals("Jennifer", rm.getParticipant(1).getName());
		
		assertEquals(1, rm.getEvents().size());
		assertEquals("Concert",rm.getEvent(0).getName());
		
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.SEPTEMBER, 15, 8 , 30,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2015, Calendar.SEPTEMBER,15, 10, 0,0);
		Time endTime = new Time(c.getTimeInMillis());
		assertEquals(eventDate.toString(),rm.getEvent(0).getEventDate().toString());
		assertEquals(startTime.toString(), rm.getEvent(0).getStartTime().toString());
		assertEquals(endTime.toString(), rm.getEvent(0).getEndTime().toString());
		
		assertEquals(2, rm.getRegistrations().size());
		assertEquals(rm.getEvent(0), rm.getRegistration(0).getEvent());
		assertEquals(rm.getParticipant(0), rm.getRegistration(0).getParticipant());
		assertEquals(rm.getEvent(0), rm.getRegistration(1).getEvent());
		assertEquals(rm.getParticipant(1), rm.getRegistration(1).getParticipant());
		
		
	}

}
