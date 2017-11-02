package Model;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class MemberRegistry {

	private ArrayList<Member> memberList;
	private ArrayList<Boat> boatList;

	/*
	 * Creates a member and stores them in an ArrayList.
	 * 
	 * Member: name, personal number, a unique id.
	 */
	public boolean registerMember(Member newMem) {
		String firstname = newMem.getFirstname();
		String lastname = newMem.getLastname();
		long persNumber = newMem.getPersNumber();
		int id = memberList.get(memberList.size() - 1).getID() + 1;
		boolean exists = false;

		Member tempMem = new Member(firstname, lastname, persNumber, id, 0, null);
		for (int i = 0; memberList.size() > i; i++) {
			Member member = memberList.get(i);
			if (member.getFullName().equals(tempMem.getFullName())) {
				exists = true;
			}
		}
		
		if (exists == false) {
			memberList.add(tempMem);
		}
		
		return exists;
	}

	/*
	 * Return the member requested.
	 * Also used to validate if member exists for other methods.
	 */
	public Member listMember(String selectedMember) {
		for (int i = 0; memberList.size() > i; i++) {
			String memberFullname = memberList.get(i).getFullName().toLowerCase();

			if (memberFullname.equals(selectedMember.toLowerCase())) {
				return memberList.get(i);
			}
		}
		return null;
	}

	/*
	 * Return the whole list of members.
	 */
	public ArrayList<Member> listMembers() {
		return memberList;
	}

	/*
	 * Delete a member from the ArrayList.
	 */
	public void deleteMember(String selectedMember) {
		for (int i = 0; memberList.size() > i; i++) {
			String memberFullname = memberList.get(i).getFullName().toLowerCase();

			if (memberFullname.equals(selectedMember.toLowerCase())) {
				memberList.remove(i);
			}
		}

	}

	/*
	 * Edit a member from the ArrayList.
	 */
	public void editMember(String selectedMember, String change, String newData) {
		for (int i = 0; memberList.size() > i; i++) {
			Member tempMember = memberList.get(i);
			String memberFullname = tempMember.getFullName().toLowerCase();

			if (memberFullname.equals(selectedMember.toLowerCase())) {

				if (change == "firstname") {
					tempMember.setFirstname(newData);
				} else if (change == "lastname") {
					tempMember.setLastname(newData);
				} else if (change == "persnum") {
					tempMember.setPersNumber(Long.parseLong(newData));
				}
			}
		}
	}

	/*
	 * Register a boat to a member and updates the data in the ArrayList.
	 */
	public void registerBoat(Boat newBoat, String selectedMember) {
		for (int i = 0; memberList.size() > i; i++) {
			Member tempMember = memberList.get(i);
			String memberFullname = tempMember.getFullName().toLowerCase();

			if (memberFullname.equals(selectedMember.toLowerCase())) {
				ArrayList<Boat> tempBoatList = tempMember.getBoats();
				int boatID;
				if (tempBoatList.isEmpty()) {
					boatID = 0;
				} else {
					boatID = tempBoatList.get(tempBoatList.size() - 1).getID() + 1;
				}
				Boat tempBoat = new Boat(newBoat.getType(), newBoat.getLength(), boatID);

				tempBoatList.add(tempBoat);
			}
		}
	}

	/*
	 * Delete a boat from selected member and updates the ArrayList.
	 */
	public void deleteBoat(String selectedMember, String selectedBoatID) {
		for (int i = 0; memberList.size() > i; i++) {
			Member tempMember = memberList.get(i);
			String memberFullname = tempMember.getFullName().toLowerCase();

			if (memberFullname.equals(selectedMember.toLowerCase())) {
				ArrayList<Boat> tempBoatList = tempMember.getBoats();

				for (int j = 0; tempBoatList.size() > j; j++) {
					Boat tempBoat = tempBoatList.get(j);
					if (Integer.toString(tempBoat.getID()).equals(selectedBoatID)) {
						tempBoatList.remove(j);
					}
				}

			}
		}
	}

	/*
	 * Edit a boat of selected member and update the ArrayList.
	 */
	public void editBoat(String selectedMember, String selectedBoatID, String change, String data) {
		for (int i = 0; memberList.size() > i; i++) {
			Member tempMember = memberList.get(i);
			String memberFullname = tempMember.getFullName().toLowerCase();

			if (memberFullname.equals(selectedMember.toLowerCase())) {
				ArrayList<Boat> tempBoatList = tempMember.getBoats();

				for (int j = 0; tempBoatList.size() > j; j++) {
					Boat tempBoat = tempBoatList.get(j);
					if (Integer.toString(tempBoat.getID()).equals(selectedBoatID)) {
						if (change == "type") {
							tempBoat.setType(data);
						} else if (change == "length") {
							tempBoat.setLength(Integer.parseInt(data));
						}
					}
				}

			}
		}
	}

	/*
	 * Initialize the ArrayLists and read from the data.xml and adds it all to
	 * the list.
	 */
	public void initialize() {
		memberList = new ArrayList<Member>();

		Document doc = readXMLfile();

		NodeList members = doc.getElementsByTagName("member");
		for (int temp = 0; temp < members.getLength(); temp++) {
			Node nNode = members.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String firstname = eElement.getElementsByTagName("firstname").item(0).getTextContent();
				String lastname = eElement.getElementsByTagName("lastname").item(0).getTextContent();
				int memberID = Integer.parseInt(eElement.getElementsByTagName("memberid").item(0).getTextContent());
				long persNum = Long.parseLong(eElement.getElementsByTagName("persnum").item(0).getTextContent());

				NodeList boatsChildren = eElement.getElementsByTagName("boats").item(0).getChildNodes();
				int bAmount = Integer.parseInt(boatsChildren.item(1).getTextContent());
				// Iterate over the boats and add them to list.
				// Reset the array of boats.
				boatList = new ArrayList<Boat>();
				for (int bTemp = 0; bTemp < boatsChildren.getLength(); bTemp++) {
					if (boatsChildren.item(bTemp).getNodeName().toLowerCase().equals("boat")) {
						Node boatNode = boatsChildren.item(bTemp);
						Element boatElement = (Element) boatNode;
						String bType = boatElement.getElementsByTagName("type").item(0).getTextContent();
						int bLength = Integer
								.parseInt(boatElement.getElementsByTagName("length").item(0).getTextContent());
						int bID = Integer.parseInt(boatElement.getAttributes().getNamedItem("id").getNodeValue());
						Boat newBoat = new Boat(bType, bLength, bID);
						boatList.add(newBoat);
					}

				}
				Member newMem = new Member(firstname, lastname, persNum, memberID, bAmount, boatList);
				// boatList.clear();
				memberList.add(newMem);

			}
		}
	}

	/*
	 * Finalize the data structure and writes from the ArrayList to the data.xml
	 * after removing all the previous nodes.
	 */
	public void finalize() {
		Document doc = readXMLfile();

		// Remove all the old nodes.
		Node root = doc.getFirstChild();
		while (root.hasChildNodes()) {
			root.removeChild(root.getFirstChild());
		}
		doc.removeChild(root);

		Element company = doc.createElement("company");
		doc.appendChild(company);

		NodeList members = doc.getElementsByTagName("member");

		// Iterate over the members and handle creating elements.
		for (int i = 0; memberList.size() > i; i++) {
			int lastID;

			if (members.getLength() == 0) {
				lastID = 1;
			} else {
				lastID = Integer.parseInt(
						members.item(members.getLength() - 1).getAttributes().getNamedItem("id").getNodeValue()) + 1;
			}

			// Create all member elements
			Element member = doc.createElement("member");
			Element firstname = doc.createElement("firstname");
			Element lastname = doc.createElement("lastname");
			Element memberid = doc.createElement("memberid");
			Element persnum = doc.createElement("persnum");

			// Append nodes to elements and set member ID
			firstname.appendChild(doc.createTextNode(memberList.get(i).getFirstname()));
			lastname.appendChild(doc.createTextNode(memberList.get(i).getLastname()));
			memberid.appendChild(doc.createTextNode(Integer.toString(memberList.get(i).getID())));
			persnum.appendChild(doc.createTextNode(Long.toString(memberList.get(i).getPersNumber())));
			member.setAttribute("id", Integer.toString(lastID));

			// Append elements to the member element.
			member.appendChild(firstname);
			member.appendChild(lastname);
			member.appendChild(memberid);
			member.appendChild(persnum);

			// Create boats element to hold all the boats.
			Element boats = doc.createElement("boats");
			Element boatAmount = doc.createElement("amount");

			// Get the members boats via ArrayList through the
			// member.getBoats();
			ArrayList<Boat> boatListTemp = memberList.get(i).getBoats();

			// Set the amount of boats and append the amount-element to the
			// boats-element.
			if (boatListTemp != null) {
				boatAmount.appendChild(doc.createTextNode(Integer.toString(boatListTemp.size())));
				boats.appendChild(boatAmount);

				NodeList boatNodes = boats.getElementsByTagName("boat");

				// Iterate over the boat list and create the boats and append
				// them.
				for (int j = 0; boatListTemp.size() > j; j++) {
					int boatID;
					if (boatNodes.getLength() == 0) {
						boatID = 1;
					} else {
						boatID = Integer.parseInt(boatNodes.item(boatNodes.getLength() - 1).getAttributes()
								.getNamedItem("id").getNodeValue()) + 1;
					}

					Element boat = doc.createElement("boat");
					boat.setAttribute("id", Integer.toString(boatID));
					Element boatType = doc.createElement("type");
					Element boatLength = doc.createElement("length");

					boatType.appendChild(doc.createTextNode(boatListTemp.get(j).getType()));
					boatLength.appendChild(doc.createTextNode(Integer.toString(boatListTemp.get(j).getLength())));

					boat.appendChild(boatType);
					boat.appendChild(boatLength);
					boats.appendChild(boat);
				}
			} else {
				boatAmount.appendChild(doc.createTextNode("0"));
				boats.appendChild(boatAmount);
			}
			// Finally append the boats to the member and the member to the
			// root(company).
			member.appendChild(boats);
			company.appendChild(member);

		}

		writeXMLfile(doc);
	}

	/*
	 * Read the XML-file and return the doc-element.
	 */
	private Document readXMLfile() {
		try {
			File fXmlFile = new File("src/data.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Take the doc-element as an argument and writes it in its current state.
	 */
	private void writeXMLfile(Document doc) {
		try {

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("src/data.xml"));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
