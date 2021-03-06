import java.io.PrintStream;
import java.util.Scanner;

public class Project2 {
	public static final int MAX_PEOPLE = 100;
	public static final int EXIT = 7;
	
	public static void main(String[] args) {
		int choice = 0;
		Person[] people = new Person[MAX_PEOPLE];
		
		// Welcome Message
		
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
			// TODO makes cases 1-6
			case 1: // Enter faculty info
				Faculty tempFaculty = new Faculty();
				Person.addToArray(people, tempFaculty);
				break;
			case 2: // Enter student info
				Student tempStudent = new Student();
				Person.addToArray(people, tempStudent);
				break;
			case 5: // Enter Staff info
				break;
			case 3:
			case 4:
			case 6:
				Person.findAndPrintPerson(people, choice, Person.casePersonType(choice));
				break;
			case 7:
				System.out.println("\n\n\nThank you, goodbye.\n");
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
		
		// TODO print menu
		
		selection = scnr.nextLine();
		
		while (invalidSelection(selection)) {
			System.out.println("\n\tInvalid Selection. Please Try again.\n");
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
		out.println("6. Print staff member information");
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
			   					  STAFF   = "Staff",
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
		if (p.getClass().equals(Student.class) && personType.equalsIgnoreCase(STUDENT))
			return true;
		if (p.getClass().equals(Faculty.class) && personType.equalsIgnoreCase(FACULTY))
			return true;
		if (p.getClass().equals(Staff.class) && personType.equalsIgnoreCase(STAFF))
			return true;
		
		return false;
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
		
		System.out.print("\n\nEnter the " + casePersonType(choice) + "'s id: ");
		inputId = scnr.nextLine();		
		
		// Search array for that person and print the relevant information
		for (int i = 0; i < personArray.length; i++) {
			if (personArray[i] != null && personArray[i].getId().equalsIgnoreCase(inputId) && 
					Person.validatePersonType(personArray[i], personType)) {
				personArray[i].print();
				return;
			}	
		}
		
		System.out.println("\n\n\t" + personType + " not found");
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
	
	// Methods to include in Faculty, Staff, and Student classes
	public abstract void print();
	public abstract void promptForInfo();

	
	/**
	 * Getters and setters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
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
		System.out.print("\n\tDepartment: ");
		department = scnr.nextLine();
		
		do {
			System.out.println("\n\t\t\"" + department + "\" is invalid. Please try again.");
			System.out.print("\n\tDepartment: ");
			department = scnr.nextLine();
		} while (!isValidDepartment(department));
		
		// Change to first letter upper case, rest of string lower case
		department = capitalizeFirstLetter(department);
		
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
		return department;
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
								DISCOUNT			  = 0.25,
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
			gpa = Double.parseDouble(gpaString);
		}
		catch (NumberFormatException e) {
			gpa = 0.0;
		}
		
		System.out.print("\n\tCredit Hours: ");
		String creditHourString = scnr.nextLine();
		try {
			creditHours = Integer.parseInt(creditHourString);
		}
		catch (NumberFormatException e) {
			creditHours = 0;
		}
		
		System.out.println("\nStudent Added!");
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
		return ADMIN_FEE + (creditHours * PRICE_PER_CREDIT_HOUR);
	}
	
	/**
	 * Calculate the discount
	 * @param grossTuition		double gross tuition
	 * @return					double discount
	 */
	private double calculateDiscount(double grossTuition) {
		return (gpa > HIGH_GPA) ? (grossTuition * DISCOUNT) : 0.0;
	}
	
	/**
	 * Print a Student's info
	 */
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println(this.toString());
	}
	
	@Override		//FIXME
	public String toString() {
		return "Student [gpa=" + gpa + ", tuition=" + tuition + ", discount=" + discount + ", creditHours="
				+ creditHours + "]";
	}

	/**
	 * Getters and setters
	 */
	public double getGpa() {
		return gpa;
	}


	public void setGpa(double gpa) {
		this.gpa = gpa;
	}


	public int getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(int creditHours) {
		this.creditHours = creditHours;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public double getTuition() {
		return tuition;
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
		
		rank = capitalizeFirstLetter(rank);
		
		this.promptDepartment();
		
		System.out.println("\n\nFaculty Added!");
		
	}
		
	/**
	 * Check if rank is valid
	 * @return				true or false
	 */
	private boolean hasValidRank() {
		return (rank.equalsIgnoreCase(RANK_PROF) || rank.equalsIgnoreCase(RANK_ADJ));
	}
	
	/**
	 * Print a Faculty's info
	 */
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println(this);
	}

	@Override		//FIXME
	public String toString() {
		return "Faculty [rank=" + rank + ", getDepartment()=" + getDepartment() + ", getName()=" + getName()
				+ ", getId()=" + getId() + "]";
	}
	
	/**
	 * Getters and Setters
	 */
	public String getRank() {
		return rank;
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
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Check if user input status is valid
	 * @param currStatus			String of status
	 * @return						true or false
	 */
	private boolean isValidStatus(String currStatus) {
		return (currStatus.equalsIgnoreCase("p") || currStatus.equalsIgnoreCase("f"));
	}
	
	/**
	 * Check if user input status is valid
	 * @return						true or false
	 */
	private boolean hasValidStatus() {
		return (status.equalsIgnoreCase("p") || status.equalsIgnoreCase("f"));
	}
	
	/**
	 * Print a Staff's information
	 */
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println(this.toString());
	}
	
	@Override		//FIXME
	public String toString() {
		return "Staff [status=" + status + ", getDepartment()=" + getDepartment() + ", getName()=" + getName()
				+ ", getId()=" + getId() + "]";
	}

	/**
	 * Getters and setters
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
