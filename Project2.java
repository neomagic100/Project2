
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Scanner;

public class Project2 {
	public static final int MAX_PEOPLE = 100;
	public static final int EXIT = 7;

	public static void main(String[] args) {
		int choice = 0;
		Person[] people = new Person[MAX_PEOPLE];

		// Welcome Message	
		System.out.println("\t\t\tWelcome to my Personal Management Program\n\n");

		while (choice != 7) {
			// Get option
			choice = printOptionMenu();
			runOptionSwitch(choice, people);
			if (choice == EXIT)
				break;
		}
	}

	/**
	 * Execute an action to set or get info, or exit
	 * @param choice				User input integer selection
	 * @param people				Array of Person
	 */
	public static void runOptionSwitch(int choice, Person[] people) {
		
		switch(choice) {
			case 1: // Enter Faculty info
				Faculty tempFaculty = new Faculty();
				Person.addToArray(people, tempFaculty);
				break;
			case 2: // Enter Student info
				Student tempStudent = new Student();
				Person.addToArray(people, tempStudent);
				break;
			case 5: // Enter Staff info
				Staff tempStaff = new Staff();
				Person.addToArray(people, tempStaff);
				break;
			case 3: // Print Student tuition info
			case 4: // Print Faculty info
			case 6: // Print Staff info
				Person.findAndPrintPerson(people, choice, Person.casePersonType(choice));
				break;
			case 7:
				System.out.println("\n\n\nGoodbye!\n");
				break;
			default: // Invalid selection already handled in getOptionSelected
				break;
		}
	}

	/**
	 * Get the selection the user chooses
	 * @return						User input integer selection
	 */
	public static int getOptionSelected() {
		String selection;
		Scanner scnr = new Scanner(System.in);

		selection = scnr.nextLine();

		while (invalidSelection(selection)) {
			System.out.println("\n\tInvalid entry- please try again.\n");
			System.out.print("\n   Enter your Selection: ");
			selection = scnr.nextLine();
		}


		return Integer.parseInt(selection);
	}

	/**
	 * Check if the user's input selection is invalid
	 * @param selection				User input integer selection
	 * @return						true or false
	 */
	public static boolean invalidSelection(String selection) {
		int selectionInt;

		try {
			selectionInt = Integer.parseInt(selection);
		}
		catch (NumberFormatException e) {
			return true;
		}

		return (selectionInt < 1 || selectionInt > 7);
	}

	/**
	 * Print the option menu and get the user's selection
	 * @return						User input integer selection
	 */
	public static int printOptionMenu() {
		PrintStream out = new PrintStream(System.out);
			
		
		out.println("1. Enter the information of a faculty");
		out.println("2. Enter the information of a student");
		out.println("3. Print tuition invoice");
		out.println("4. Print faculty information");
		out.println("5. Enter the information of a staff member");
		out.println("6. Print the information of a staff member");
		out.println("7. Exit Program");
		out.print("\n   Enter your Selection: ");

		return getOptionSelected();
	}
}

/**
 *
 * Person abstract class
 *
 */
abstract class Person {
	// Constants
	protected static final String FACULTY = "Faculty",
			   					  STAFF   = "Staff member",
			                      STUDENT = "Student";
	
	// Fields
	private String name;
	private String id;

	/**
	 * Add a Person to the first null slot of a Person array
	 * @param people				Person array
	 * @param personToAdd			Person being added to array
	 */
	public static void addToArray(Person[] people, Person personToAdd) {
		for (int i = 0; i < people.length; i++) {
			if (people[i] == null) {
				people[i] = personToAdd;
				break;
			}
		}
	}

	/**
	 * Check if a Person matches their string representation
	 * @param p						Person being checked
	 * @param personType			String representation of the person type
	 * @return						true or false
	 */
	private static boolean validatePersonType(Person p, String personType) {
		return (p.getClass().equals(Student.class) && personType.equalsIgnoreCase(STUDENT) ||
				(p.getClass().equals(Faculty.class) && personType.equalsIgnoreCase(FACULTY)) ||
				(p.getClass().equals(Staff.class) && personType.equalsIgnoreCase(STAFF)));
	}

	/**
	 * Get a user id from console, find that Person in array, and print their information
	 * @param personArray			Person Array
	 * @param choice				User input integer selection
	 * @param personType			String representation of the person type
	 */
	public static void findAndPrintPerson(Person[] personArray, int choice, String personType) {
		Scanner scnr = new Scanner(System.in);
		String inputId;

		System.out.print("\n\n   Enter the " + casePersonType(choice) + "'s id: ");
		inputId = scnr.nextLine();

		// Search array for that person and print the relevant information
		for (int i = 0; i < personArray.length; i++) {
			if (personArray[i] != null && personArray[i].getId().equalsIgnoreCase(inputId) &&
					Person.validatePersonType(personArray[i], personType)) {
				personArray[i].print();
				return;
			}
		}

		System.out.println("\n\n No " + personType + " matched!\n\n");
	}

	/**
	 * Format a string to be lower case and have an upper case first letter
	 * @param s						String to format
	 * @return						Formatted string
	 */
	protected String capitalizeFirstLetter(String s) {
		String retString;
		retString = s.toLowerCase();
		retString = retString.substring(0, 1).toUpperCase() + retString.substring(1);

		return retString;
	}

	/**
	 * Get the string representation of a Person type based on the user's selection
	 * @param choice				User input integer selection
	 * @return						String representation of the person type
	 */
	public static String casePersonType(int choice) {
		String personType;
		switch (choice) {
			case 3:
				personType = STUDENT;
				break;
			case 4:
				personType = FACULTY;
				break;
			case 6:
				personType = STAFF;
				break;
			default:
				personType = "Error";
				break;
		}

		return personType;
	}
	
	protected void printDashesLine() {
		System.out.println("\n---------------------------------------------------------------------------\n");
	}

	// Methods to include in Faculty, Staff, and Student classes
	public abstract void print();
	public abstract void promptForInfo();


	/**
	 * Getters and setters
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

/**
 *
 * Employee abstract class
 *
 */
abstract class Employee extends Person {
	// Constants
	protected static final String DEPT_MATH  = "Mathematics",
						   		  DEPT_ENGIN = "Engineering",
						   		  DEPT_SCI   = "Sciences",
						   		  EMPTY_DEPT = "NoDept";

	// Fields
	private String department;

	/**
	 * Prompt the user for a department
	 */
	public void promptDepartment() {
		Scanner scnr = new Scanner(System.in);
		
		do {
			System.out.print("\n\tDepartment: ");
			this.department = scnr.nextLine();
			if (!isValidDepartment(this.department))
				System.out.println("\n\t\t\"" + this.department + "\" is invalid. Please try again.");
		} while (!isValidDepartment(this.department));

		// Change to first letter upper case, rest of string lower case
		this.department = capitalizeFirstLetter(this.department);

	}

	/**
	 * Check if the user input department is valid
	 * @param inputDept				User input string department
	 * @return						true or false
	 */
	private boolean isValidDepartment(String inputDept) {
		return (inputDept.equalsIgnoreCase(DEPT_MATH) || inputDept.equalsIgnoreCase(DEPT_ENGIN) || inputDept.equalsIgnoreCase(DEPT_SCI));
	}


	/**
	 * Getters and setters
	 */
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}

/**
 *
 * Student class
 *
 */
class Student extends Person {
	// Constants
	private static final double PRICE_PER_CREDIT_HOUR = 236.45,
								ADMIN_FEE			  = 52.0,
								DISCOUNT_AMT		  = 0.25,
								HIGH_GPA			  = 3.85;

	// Fields
	private double gpa, tuition, discount;
	private int creditHours;

	/**
	 * Default Constructor
	 */
	public Student() {
		promptForInfo();
		calculateNetTuitionAndDiscount();
	}

	/**
	 * Prompt the user to input the information for a Student
	 */
	@Override
	public void promptForInfo() {
		Scanner scnr = new Scanner(System.in);
		System.out.println("\nEnter the student info:");

		System.out.print("\n\tName of Student: ");
		this.setName(scnr.nextLine());

		System.out.print("\n\tID: ");
		this.setId(scnr.nextLine());

		// If invalid format input to gpa or credit hours, set to 0
		System.out.print("\n\tGpa: ");
		String gpaString = scnr.nextLine();
		try {
			this.gpa = Double.parseDouble(gpaString);
			if (this.gpa > 4.0 || this.gpa < 0.0)
				throw new NumberFormatException();
		}
		catch (NumberFormatException e) {
			this.gpa = 0.0;
		}

		System.out.print("\n\tCredit Hours: ");
		String creditHourString = scnr.nextLine();
		try {
			this.creditHours = Integer.parseInt(creditHourString);
			if (this.creditHours < 0)
				throw new NumberFormatException();
		}
		catch (NumberFormatException e) {
			this.creditHours = 0;
		}

		System.out.println("\nStudent Added!\n\n");
	}

	/**
	 * Calculate the tuition and discount
	 */
	public void calculateNetTuitionAndDiscount() {
		double grossTuition = calculateGrossTuition();
		this.discount = calculateDiscount(grossTuition);
		this.tuition = grossTuition - this.discount;
	}

	/**
	 * Calculate the gross tuition
	 * @return					double gross tuition
	 */
	private double calculateGrossTuition() {
		return ADMIN_FEE + (this.creditHours * PRICE_PER_CREDIT_HOUR);
	}

	/**
	 * Calculate the discount
	 * @param grossTuition		double gross tuition
	 * @return					double discount
	 */
	private double calculateDiscount(double grossTuition) {
		return (this.gpa > HIGH_GPA) ? (grossTuition * DISCOUNT_AMT) : 0.0;
	}

	/**
	 * Print a Student's info
	 */
	@Override
	public void print() {
		NumberFormat n = NumberFormat.getCurrencyInstance();
		
		System.out.println();
		System.out.println("\nTuition Invoice for " + this.getName() + ":");
		
		this.printDashesLine();
		System.out.println(this);
	
		System.out.printf("%nFees: $%.0f", ADMIN_FEE);
		System.out.printf("%n%n%nTotal payment (after discount): %s", n.format(this.tuition));
		System.out.printf("\t\t(%s discount applied)%n", n.format(this.discount));
		
		this.printDashesLine();
		System.out.println();
	}

	@Override
	public String toString() {
		String retString = this.getName() + "\t\t" + this.getId() + "\n\n";
		retString += "Credit Hours: " + this.creditHours + " ($" + PRICE_PER_CREDIT_HOUR + "/ credit hour)";
		
		return retString;
	}

	/**
	 * Getters and setters
	 */
	public double getGpa() {
		return this.gpa;
	}


	public void setGpa(double gpa) {
		this.gpa = gpa;
	}


	public int getCreditHours() {
		return this.creditHours;
	}

	public void setCreditHours(int creditHours) {
		this.creditHours = creditHours;
	}

	public double getDiscount() {
		return this.discount;
	}

	public double getTuition() {
		return this.tuition;
	}
}

/**
 *
 * Faculty Class
 *
 */
class Faculty extends Employee {
	// Constants
	private static final String RANK_PROF =  "Professor",
								RANK_ADJ  =  "Adjunct";

	// Fields
	private String rank;

	/**
	 * Default Constructor
	 */
	public Faculty() {
		promptForInfo();
	}

	/**
	 * Prompt the user for a Faculty's information
	 */
	@Override
	public void promptForInfo() {
		Scanner scnr = new Scanner(System.in);
		System.out.println("\nEnter the faculty info:");

		System.out.print("\n\tName of Faculty: ");
		this.setName(scnr.nextLine());

		System.out.print("\n\tID: ");
		this.setId(scnr.nextLine());

		// Check if rank valid
		do {
			System.out.print("\n\tRank: ");
			this.setRank(scnr.nextLine());
			if (!this.hasValidRank())
				System.out.println("\n\t\t\"" + this.getRank() + "\" is invalid");
		} while (!this.hasValidRank());

		this.rank = capitalizeFirstLetter(this.rank);

		this.promptDepartment();

		System.out.println("\n\nFaculty Added!\n\n");

	}

	/**
	 * Check if rank is valid
	 * @return				true or false
	 */
	private boolean hasValidRank() {
		return (this.rank.equalsIgnoreCase(RANK_PROF) || this.rank.equalsIgnoreCase(RANK_ADJ));
	}

	/**
	 * Print a Faculty's info
	 */
	@Override
	public void print() {
		System.out.println();
		this.printDashesLine();
		System.out.println(this);
		this.printDashesLine();
		System.out.println();
	}

	@Override
	public String toString() {
		return this.getName() + "\t\t" + this.getId() + "\n\n" + this.getDepartment() + " Department, " + this.rank;
	}

	/**
	 * Getters and Setters
	 */
	public String getRank() {
		return this.rank;
	}


	public void setRank(String rank) {
		this.rank = rank;
	}
}

/**
 *
 * Staff class
 *
 */
class Staff extends Employee {
	// Constants
	private static final String PART_TIME = "P",
							    FULL_TIME = "F";

	// Fields
	private String status;

	/**
	 * Default Constructor
	 */
	public Staff() {
		promptForInfo();
	}

	/**
	 * Prompt user for a Staff's information
	 */
	@Override
	public void promptForInfo() {
		Scanner scnr = new Scanner(System.in);

		System.out.print("\n\tName of Staff Member: ");
		this.setName(scnr.nextLine());

		System.out.print("\n\tEnter the ID: ");
		this.setId(scnr.nextLine());

		this.promptDepartment();
		
		do {
			System.out.print("\n\tStaus, Enter P for Part Time, or Enter F for Full Time: ");
			this.status = scnr.nextLine();
			if (!this.hasValidStatus())
				System.out.println("\n\t\t\"" + this.status + "\" is invalid");
		} while (!this.hasValidStatus());
		
		this.status = this.status.toUpperCase();

		System.out.println("\n\nStaff member Added!\n\n");
	}
	
	/**
	 * Check if user input status is valid
	 * @return						true or false
	 */
	private boolean hasValidStatus() {
		return (this.status.equalsIgnoreCase(PART_TIME) || this.status.equalsIgnoreCase(FULL_TIME));
	}

	/**
	 * Print a Staff's information
	 */
	@Override
	public void print() {
		System.out.println();
		this.printDashesLine();
		System.out.println(this);
		this.printDashesLine();
		System.out.println();
	}

	@Override
	public String toString() {
		String retString = this.getName() + "\t\t" + this.getId() + "\n\n" + this.getDepartment() + " Department, ";
		
		if (this.getStatus().equalsIgnoreCase(PART_TIME))
			retString += "Part Time";
		else
			retString += "Full Time";
		
		return retString;
	}

	/**
	 * Getters and setters
	 */
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}