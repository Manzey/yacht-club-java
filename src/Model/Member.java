package Model;

import java.util.ArrayList;

public class Member {
	private String firstname;
	private String lastname;
	private long persNum;
	private int uniqueID = 0;
	private int amountBoats;
	private ArrayList<Boat> boats = new ArrayList<Boat>();
	
	public Member(String x_firstname, String x_lastname, long number, int id, int bAmount, ArrayList<Boat> boatList) {
		firstname = x_firstname;
		lastname = x_lastname;
		persNum = number;
		boats = boatList;
		uniqueID = id;
		amountBoats = bAmount;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public String getFullName() {
		return firstname + " " + lastname;
	}
	
	public long getPersNumber() {
		return persNum;
	}
	
	public int getID() {
		return uniqueID;
	}
	
	public int getAmountBoats() {
		return amountBoats;
	}
	
	public ArrayList<Boat> getBoats() {
		return boats;
	}
	
	public void setFirstname(String data) {
		firstname = data;
	}
	
	public void setLastname(String data) {
		lastname = data;
	}
	
	public void setPersNumber(long l) {
		persNum = l;
	}
}
