import java.util.Scanner;

public class Project2 {
	public static final int MAX_PEOPLE = 100;
	public static final int EXIT = 7;
	public static final String FACULTY = "Faculty",
							   STAFF   = "Staff",
							   STUDENT = "Student";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int choice = 0;
		Person[] people = new Person[MAX_PEOPLE];
		// Welcome
		while (choice != 7) {
			//TODO Get option
			choice = getOptionSelected();
			runOptionSwitch(choice, people);
			if (choice == EXIT)
				break;
			
			//TODO Execute command selected through switch
		}
	}
	
	public static void runOptionSwitch(int choice, Person[] people) {
		Scanner scnr = new Scanner(System.in);
		String inputId;
		
		switch(choice) {
			// TODO makes cases 1-6
			case 1: // Enter faculty info
				Faculty facultyMember = new Faculty();
				facultyMember.promptForInfo();
				Person.addToArray(people, facultyMember);
				break;
			case 2: // Enter student info
				break;
			case 5: // Enter Staff info
				break;
			case 3:
			case 4:
			case 6:
				System.out.print("\n\n\tEnter the" + casePersonType(choice) + "'s id: ");
				inputId = scnr.nextLine();
				Person.printPersonIndex(people, inputId, casePersonType(choice));
				break;
			case 7:
				System.out.println("\n\n\nThank you, goodbye.\n");
				break;
			default: // Invalid selection already handled in getOptionSelected
				break;
		}
	}
	
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
	
	public static int getOptionSelected() {
		String selection;
		Scanner scnr = new Scanner(System.in);
		
		// TODO print menu
		
		selection = scnr.nextLine();
		
		while (invalidSelection(selection)) {
			System.out.println("\n\nInvalid Selection. Please Try again.\n");
			selection = scnr.nextLine();
		}
		
		
		return Integer.parseInt(selection);
	}
	
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
}

abstract class Person {
	//TODO
	protected static final String EMPTY_NAME = "NoName",
								  EMPTY_ID   = "NoID";
	
	private String name;
	private String id;
	
	public static void addToArray(Person[] people, Person personToAdd) {
		for (int i = 0; i < people.length; i++) {
			if (people[i] == null) {
				people[i] = personToAdd;
				break;
			}
		}	
	}

	public static void printPersonIndex(Person[] personArray, String searchID, String personType) {
		
		for (int i = 0; i < personArray.length; i++) {
			if (personArray[i] != null && personArray[i].getId().equalsIgnoreCase(searchID)) {
				personArray[i].print();
				return;
			}	
		}
		
		printPersonNotFound(personType);
	}
	
	private static void printPersonNotFound(String personType) {
		System.out.println("\n\n\t" + personType + " not found");
	}
	
	protected String capitalizeFirstLetter(String s) {
		String retString;
		retString = s.toLowerCase();
		retString = retString.substring(0, 1).toUpperCase() + retString.substring(1);
		
		return retString;
	}
	
	public abstract void print();
	public abstract void promptForInfo();

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}

abstract class Employee extends Person {
	//TODO
	protected static final String DEPT_MATH  = "Mathematics",
						   		  DEPT_ENGIN = "Engineering",
						   		  DEPT_SCI   = "Sciences",
						   		  EMPTY_DEPT = "NoDept";
	private String department;
	
	public void promptDepartment() {
		Scanner scnr = new Scanner(System.in);
		System.out.print("\n\n\tDepartment: ");
		department = scnr.nextLine();
		
		while (!isValidDepartment(department)) {
			System.out.println("\n\t\"" + department + "\" is invalid. Please try again.");
			System.out.print("\n\n\tDepartment: ");
			department = scnr.nextLine();
		}
		
		// Change to first letter upper case, rest of string lower case
		department = capitalizeFirstLetter(department);
		
	}
	
	// Internal method to check validity of department
	private boolean isValidDepartment(String inputDept) {
		return (inputDept.equalsIgnoreCase(DEPT_MATH) || inputDept.equalsIgnoreCase(DEPT_ENGIN) || inputDept.equalsIgnoreCase(DEPT_SCI));
	}
	
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
}

class Student extends Person {
	private static final double PRICE_PER_CREDIT_HOUR = 236.45,
								ADMIN_FEE			  = 52.0,
								DISCOUNT			  = 0.25,
								HIGH_GPA			  = 3.85;
	private double gpa, tuition, discount;
	private int creditHours;
	
	/**
	 * Default Constructor
	 */
	public Student() {
		gpa = tuition = -1.0;
		creditHours = -1;
		this.setName(EMPTY_NAME);
		this.setId(EMPTY_ID);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param id
	 * @param gpa
	 * @param creditHours
	 */
	public Student(String name, String id, double gpa, int creditHours) {
		this.setName(name);
		this.setId(id);
		this.gpa = gpa;
		this.creditHours = creditHours;
		tuition = 0;
	}
	
	public void calculateNetTuitionAndDiscount() {
		double grossTuition = calculateGrossTuition();
		this.discount = calculateDiscount(grossTuition);
		this.tuition = grossTuition - this.discount;		
	}
	
	private double calculateGrossTuition() {
		return ADMIN_FEE + (creditHours * PRICE_PER_CREDIT_HOUR);
	}
	
	private double calculateDiscount(double grossTuition) {
		return (gpa > HIGH_GPA) ? (grossTuition * DISCOUNT) : 0.0;
	}
	
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println(this.toString());
	}
	//TODO

	/**
	 * @return the gpa
	 */
	public double getGpa() {
		return gpa;
	}

	/**
	 * @param gpa the gpa to set
	 */
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	/**
	 * @return the creditHours
	 */
	public int getCreditHours() {
		return creditHours;
	}

	/**
	 * @param creditHours the creditHours to set
	 */
	public void setCreditHours(int creditHours) {
		this.creditHours = creditHours;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public double getTuition() {
		return tuition;
	}

	@Override		//FIXME
	public String toString() {
		return "Student [gpa=" + gpa + ", tuition=" + tuition + ", discount=" + discount + ", creditHours="
				+ creditHours + "]";
	}

	@Override
	public void promptForInfo() {
		// TODO Auto-generated method stub
		
	}
}

class Faculty extends Employee {
	private static final String RANK_PROF =  "Professor",
								RANK_ADJ  =  "Adjunct",
								EMPTY_RANK = "NoRank";
	private String rank;
	
	public Faculty() {
		this.setName(EMPTY_NAME);
		this.setId(EMPTY_ID);
		this.setDepartment(EMPTY_DEPT);
		rank = EMPTY_RANK;
	}
	
	public Faculty(String name, String id, String dept, String rank) {
		this.setName(name);
		this.setId(id);
		this.setDepartment(dept);
		this.rank = rank;
	}
	
	@Override
	public void promptForInfo() {
		Scanner scnr = new Scanner(System.in);
		System.out.println("\nEnter the faculty info:");
		
		System.out.print("\n\tName of Faculty: ");
		this.setName(scnr.nextLine());
		
		System.out.print("\n\n\tID: ");
		this.setId(scnr.nextLine());
		
		// make sure rank valid
		while (!this.hasValidRank()) {
			System.out.print("\n\n\tRank: ");
			this.setRank(scnr.nextLine());
			if (!this.hasValidRank())
				System.out.println("\n\n\t\t\"" + this.getRank() + "\" is invalid\n");
		}
		
		rank = capitalizeFirstLetter(rank);
		
		this.promptDepartment();
		
	}
		
	private boolean hasValidRank() {
		return (rank.equalsIgnoreCase(RANK_PROF) || rank.equalsIgnoreCase(RANK_ADJ));
	}
	
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println(this);
	}
	//TODO

	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}

	@Override		//FIXME
	public String toString() {
		return "Faculty [rank=" + rank + ", getDepartment()=" + getDepartment() + ", getName()=" + getName()
				+ ", getId()=" + getId() + "]";
	}

}

class Staff extends Employee {
	private static final String EMPTY_STATUS = "NoStatus";
	private String status;
	
	public Staff() {
		this.setName(EMPTY_NAME);
		this.setId(EMPTY_ID);
		this.setDepartment(EMPTY_DEPT);
		status = EMPTY_STATUS;
	}
	
	public Staff(String name, String id, String dept, String status) {
		this.setName(name);
		this.setId(id);
		this.setDepartment(dept);
		this.status = status;
	}
	
	protected boolean isValidStatus(String currStatus) {
		return (currStatus.equalsIgnoreCase("p") || currStatus.equalsIgnoreCase("f"));
	}
	
	private boolean hasValidStatus() {
		return (status.equalsIgnoreCase("p") || status.equalsIgnoreCase("f"));
	}
	
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println(this.toString());
	}
	//TODO

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override		//FIXME
	public String toString() {
		return "Staff [status=" + status + ", getDepartment()=" + getDepartment() + ", getName()=" + getName()
				+ ", getId()=" + getId() + "]";
	}

	@Override
	public void promptForInfo() {
		// TODO Auto-generated method stub
		
	} 

}
