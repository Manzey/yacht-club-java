package View;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Model.MemberRegistry;
import Model.Member;
import Model.Boat;

public class View {
	private Scanner sc = new Scanner(System.in);
	private MemberRegistry members = new MemberRegistry();

	public void ui() {

		// Initialize the MemberHandler by loading XML-file into ArrayLists
		members.initialize();
		
		// Run the menu.
		showMenu();	
		
		// Finalize the MemberHandler by loading the ArrayLists  into the XML-file.
		members.finalize();
		
		// Re-run the ui-method until user exits application.
		ui();
	}

	/*
	 * Print out the menu and deal with its user input.
	 */
	private void showMenu() {
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("|		  Welcome to the Yacht Club control panel!               |");
		System.out.println("|Navigate by using numbers, via clicking a number and then hitting enter.|");
		System.out.println("|    1. Create a new member   2. Edit a member    3. Delete a member     |");
		System.out.println("|    4. List a member         5. List all members 6. Register a boat     |");
		System.out.println("|    7. Edit a boat           8. Delete a boat    9. Exit application    |");
		System.out.println("--------------------------------------------------------------------------");
		
		int choice1 = 0;
		do {
			try {
				System.out.print("Input: ");
				choice1 = sc.nextInt();

				if (choice1 < 1 || choice1 > 9) {
					throw new InputMismatchException("Wrong number!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Not a valid number, try again!");
				sc.nextLine();
			}

		} while (choice1 < 1 || choice1 > 9);

		sc.nextLine();
		if (choice1 == 1) {
			// Create a new member.
			showCreateMember();
		} else if (choice1 == 2) {
			// Edit a member.
			showEditMember();
		} else if (choice1 == 3) {
			// Delete a member.
			showDeleteMember();
		} else if (choice1 == 4) {
			// List a member.
			showListAMember();
		} else if (choice1 == 5) {
			// List all members.
			showListMembers();
		} else if (choice1 == 6) {
			// Register a boat.
			showRegisterBoat();
		} else if (choice1 == 7) {
			// Edit a boat.
			showEditBoat();
		} else if (choice1 == 8) {
			// Delete a boat.
			showDeleteBoat();
		} else if (choice1 == 9) {
			// Exit application
			showExitApplication();
		}
	}

	/*
	 * Prints the create a new member and and deals with user input.
	 */
	private void showCreateMember() {
		System.out.println("You selected 1. Create a new member!");
		System.out.println("Please input the new members firstname! (e.g John)");
		System.out.print("Input: ");
		while (!sc.hasNext("[A-Z][a-zA-Z]*")) {
			System.out.println("Firstname is not properly formatted!");
			System.out.println("No symbols or numbers and capitalize the first letter!");
			System.out.print("Input: ");
			sc.next();
		}
		String firstname = sc.next();

		System.out.println("Please input the new members lastname! (e.g Smith)");
		System.out.print("Input: ");
		while (!sc.hasNext("[A-Z][a-zA-Z]*")) {
			System.out.println("Lastname is not properly formatted!");
			System.out.println("No symbols or numbers and capitalize the first letter!");
			System.out.print("Input: ");
			sc.next();
		}
		String lastname = sc.next();
		System.out.println("Please input the new members personal number! (e.g 9106145595)");
		System.out.print("Input: ");
		while (!sc.hasNext("[0-9]+")) {
			System.out.println("No letters allowed, only numbers!");
			System.out.print("Input: ");
			sc.next();
		}
		String personalNumber = sc.next();
		Member newMem = new Member(firstname, lastname, Long.parseLong(personalNumber), 0, 0, null);
		boolean exists = members.registerMember(newMem);
		if (exists == true) {
			System.out.println("Member already exists, returning to menu!");			
		} else if (exists == false) {
			System.out.println("Member created, returning to menu!");
		}
	}

	/*
	 * Prints edit a member and handles user input.
	 */
	private void showEditMember() {
		System.out.println("You selected 2. Edit a member!");
		System.out.println("Please input the full name of the user you wish to edit. (e.g 'John Smith')");
		System.out.print("Input: ");
		String fullName = sc.nextLine();
		Member tempMember = members.listMember(fullName);
		if (tempMember == null) {
			System.out.println("Member not found, returning to main menu!");
			ui();
		}
		System.out
				.println("What information do you wish to edit?\n" + "1. Firstname\n2. Lastname\n3. Personal number.");
		int change = 0;
		do {
			try {
				System.out.print("Input: ");
				change = sc.nextInt();

				if (change <= 0 || change > 3) {
					throw new InputMismatchException("Wrong number");
				}
			} catch (InputMismatchException e) {
				System.out.println("Not a valid number, try again!");
				sc.nextLine();
			}

		} while (change <= 0 || change > 3);
		sc.nextLine();
		String changed = "";
		String valueType = "";
		if (change == 1) {
			changed = "firstname";
			valueType = "Firstname";
		} else if (change == 2) {
			changed = "lastname";
			valueType = "Lastname";
		} else if (change == 3) {
			changed = "persnum";
			valueType = "Personal number";
		}
		System.out.println("What do you want the new value to be? (" + valueType + ")");
		System.out.print("Input: ");

		if (changed == "firstname" || changed == "lastname") {
			while (!sc.hasNext("[A-Z][a-zA-Z]*")) {
				System.out.println("Value is not properly formatted!");
				System.out.println("No symbols or numbers and capitalize the first letter!");
				System.out.print("Input: ");
				sc.next();
			}
		}

		if (changed == "persnum") {
			while (!sc.hasNext("[0-9]+")) {
				System.out.println("No letters allowed, only numbers!");
				System.out.print("Input: ");
				sc.next();
			}
		}
		String newData = sc.next();
		members.editMember(fullName, changed, newData);
	}

	/*
	 * Prints delete a member and handles user input.
	 */
	private void showDeleteMember() {
		System.out.println("You selected 3. Delete a member!");
		System.out.println("Please input the name of the user you wish to delete. (Full name e.g 'John Smith')");
		System.out.print("Input: ");
		String fullName = sc.nextLine();
		Member tempMember = members.listMember(fullName);
		if (tempMember == null) {
			System.out.println("Member not found, returning to main menu!");
			ui();
		}
		members.deleteMember(fullName);
	}

	/*
	 * Prints list a member and handles user input.
	 */
	private void showListAMember() {
		System.out.println("You selected 4. List a member!");
		System.out.println("Please input the name of the user you wish to view. (Full name e.g 'John Smith')");
		System.out.print("Input: ");
		String fullName = sc.nextLine();
		Member tempMember = members.listMember(fullName);
		if (tempMember == null) {
			System.out.println("Member not found, returning to main menu!");
			ui();
		}

		System.out.println("Name: " + tempMember.getFullName());
		System.out.println("Personal number: " + tempMember.getPersNumber());
		System.out.println("Unique ID: " + tempMember.getID());

		System.out.println("");
		ArrayList<Boat> boatList = tempMember.getBoats();
		for (int i = 0; boatList.size() > i; i++) {
			System.out.println("Boat ID: " + boatList.get(i).getID());
			System.out.println("Boat type: " + boatList.get(i).getType());
			System.out.println("Boat length: " + boatList.get(i).getLength());
			System.out.println("");
		}
	}

	/*
	 * Prints list all members and handles user input.
	 */
	private void showListMembers() {
		System.out.println("You selected 5. List all members!");
		System.out.println("Select what type of list you want.\n1. Compact\n2. Verbose");
		ArrayList<Member> memberList = members.listMembers();
		int list = 0;
		do {
			try {
				System.out.print("Input: ");
				list = sc.nextInt();

				if (list < 1 || list > 2) {
					throw new InputMismatchException("Wrong number");
				}
			} catch (InputMismatchException e) {
				System.out.println("Not a valid number, try again!");
				sc.nextLine();
			}

		} while (list < 1 || list > 2);
		sc.nextLine();
		if (list == 1) {
			memberList.forEach((member) -> {
				System.out.println("Name: " + member.getFullName());
				System.out.println("Personal number: " + member.getPersNumber());
				System.out.println("Unique ID: " + member.getID());
				System.out.println("Amount of boats: " + member.getAmountBoats());
				System.out.println("");
			});
		} else if (list == 2) {
			memberList.forEach((member) -> {
				System.out.println("-------------");
				System.out.println("Name: " + member.getFullName());
				System.out.println("Personal number: " + member.getPersNumber());
				System.out.println("Unique ID: " + member.getID());
				System.out.println("Amount of boats: " + member.getAmountBoats());
				System.out.println("-------------");
				member.getBoats().forEach((boat) -> {
					System.out.println("Boat ID: " + boat.getID());
					System.out.println("Boat type: " + boat.getType());
					System.out.println("Boat length: " + boat.getLength());
					System.out.println("");
				});
			});
		}
	}

	/*
	 * Prints register a boat and handles user input.
	 */
	private void showRegisterBoat() {
		System.out.println("You selected 6. Register a boat!");
		System.out.println(
				"Please input the name of the user you wish to register a boat to. (Full name e.g 'John Smith')");
		System.out.print("Input: ");
		String fullName = sc.nextLine();
		Member tempMember = members.listMember(fullName);
		if (tempMember == null) {
			System.out.println("Member not found, returning to main menu!");
			ui();
		}
		System.out.println("What type is the boat being registered? (e.g Airboat, Barge, Dinghy, Canoe)");
		System.out.print("Input: ");
		while (!sc.hasNext("[A-Z][a-zA-Z]*")) {
			System.out.println("Boats type is not properly formatted!");
			System.out.println("No symbols or numbers and capitalize the first letter!");
			System.out.print("Input: ");
			sc.next();
		}
		String boatType = sc.nextLine();

		System.out.println("How long is the boat being registered? (Input a number only, in metres)");
		System.out.print("Input: ");
		while (!sc.hasNext("[0-9]+")) {
			System.out.println("No letters allowed, only numbers!");
			System.out.print("Input: ");
			sc.next();
		}
		int boatLength = sc.nextInt();
		Boat newBoat = new Boat(boatType, boatLength, 0);
		members.registerBoat(newBoat, fullName);
		System.out.println("Boat registered to member " + fullName + "!");
	}

	/*
	 * Prints edit boat and handles user input.
	 */
	private void showEditBoat() {
		System.out.println("You selected 7. Register a boat!");
		System.out
				.println("Please input the name of the user you wish to edit a boat for. (Full name e.g 'John Smith')");
		System.out.print("Input: ");
		String fullName = sc.nextLine();
		Member tempMember = members.listMember(fullName);
		if (tempMember == null) {
			System.out.println("Member not found, returning to main menu!");
			ui();
		}
		tempMember.getBoats().forEach((boat) -> {
			System.out.println("Boat ID: " + boat.getID());
			System.out.println("Boat type: " + boat.getType());
			System.out.println("Boat length: " + boat.getLength());
			System.out.println("");
		});

		System.out.println("Please input the ID of the boat you wish to edit!");
		System.out.print("Input: ");
		while (!sc.hasNext("[0-9]+")) {
			System.out.println("No letters allowed, only numbers!");
			System.out.print("Input: ");
			sc.next();
		}
		int boatID = sc.nextInt();
		sc.nextLine();

		if (boatID > tempMember.getAmountBoats() || boatID < 1) {
			System.out.println("Invalid ID entered, returning to menu..");
			ui();
		}

		System.out.println("Select what you want to edit.\n1. Type\n2. Length");
		int type = 0;
		do {
			try {
				System.out.print("Input: ");
				type = sc.nextInt();

				if (type < 1 || type > 2) {
					throw new InputMismatchException("Wrong number");
				}
			} catch (InputMismatchException e) {
				System.out.println("Not a valid number, try again!");
				sc.nextLine();
			}

		} while (type < 1 || type > 2);
		sc.nextLine();
		String change = "";
		if (type == 1) {
			change = "type";
			System.out.println("What do you want the new value to be? (" + change + ")");
			System.out.print("Input: ");
			while (!sc.hasNext("[A-Z][a-zA-Z]*")) {
				System.out.println("Boat type is not properly formatted!");
				System.out.println("No symbols or numbers and capitalize the first letter!");
				System.out.print("Input: ");
				sc.next();
			}
		} else if (type == 2) {
			change = "length";
			System.out.println("What do you want the new value to be? (" + change + ")");
			System.out.print("Input: ");
			while (!sc.hasNext("[0-9]+")) {
				System.out.println("No letters allowed, only numbers!");
				System.out.print("Input: ");
				sc.next();
			}
		}

		String data = sc.nextLine();
		members.editBoat(fullName, Integer.toString(boatID), change, data);
		System.out.println("Boat's data updated!");
	}

	/*
	 * Prints delete boat and handles user input.
	 */
	private void showDeleteBoat() {
		System.out.println("You selected 8. Delete a boat!");
		System.out.println(
				"Please input the name of the user you wish to delete a boat for. (Full name e.g 'John Smith')");
		System.out.print("Input: ");
		
		String fullName = sc.nextLine();
		Member tempMember = members.listMember(fullName);
		if (tempMember == null) {
			System.out.println("Member not found, returning to main menu!");
			ui();
		}
		tempMember.getBoats().forEach((boat) -> {
			System.out.println("Boat ID: " + boat.getID());
			System.out.println("Boat type: " + boat.getType());
			System.out.println("Boat length: " + boat.getLength());
			System.out.println("");
		});

		System.out.println("Please input the ID of the boat you wish to edit!");
		System.out.print("Input: ");
		while (!sc.hasNext("[0-9]+")) {
			System.out.println("No letters allowed, only numbers!");
			System.out.print("Input: ");
			sc.next();
		}
		int boatID = sc.nextInt();
		sc.nextLine();

		if (boatID > tempMember.getAmountBoats() || boatID < 1) {
			System.out.println("Invalid ID entered, returning to menu..");
			ui();
		}
		members.deleteBoat(fullName, Integer.toString(boatID));
	}

	/*
	 * Prints exit message.
	 */
	private void showExitApplication() {
		System.out.println("-----------------------");
		System.out.println("|EXITING APPLICATION..|");
		System.out.println("|      GOOD BYE       |");
		System.out.println("-----------------------");
		System.exit(0);
	}
}
