/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.eventregistration.model;

// line 4 "../../../../../eventRegistration.ump"
// line 41 "../../../../../eventRegistration.ump"
public class Participant
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Participant Attributes
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Participant(String aName)
  {
    name = aName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "]"
     + outputString;
  }
}