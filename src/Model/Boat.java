package Model;

public class Boat {
	private String type;
	private int length;
	private int uniqueID = 0;
	
	public Boat(String x_type, int x_length, int boatID) {
		type = x_type;
		length = x_length;
		uniqueID = boatID;
	}
	
	public String getType() {
		return type;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getID() {
		return uniqueID;
	}
	
	public void setType(String data) {
		type = data;
	}
	
	public void setLength(int data) {
		length = data;
	}
}
