import java.util.Scanner;

public class Project2 {
	public static final int MAX_PEOPLE = 100;
	public static final int EXIT = 7;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int choice = 0;
		Person[] people = new Person[MAX_PEOPLE];
		// Welcome
		while (choice != 7) {
			//TODO Get option
			choice = getOptionSelected();
			if (choice == EXIT) {
				// TODO print bye
				break;
			}
			//TODO Execute command selected through switch
		}
	}
	
	public static Person runOptionSwitch(int choice) {
		Person person = null;
		
		// If setting, create and return new Person
		// If getting, find and return that person
		
		
		switch(choice) {
			// TODO makes cases 1-6
			default:
				// TODO invalid selection
				break;
		}
		
		return person;
	}
	
	public static int getOptionSelected() {
		String selection;
		Scanner scnr = new Scanner(System.in);
		
		// TODO print menu
		
		selection = scnr.nextLine();
		
		while (invalidSelection(selection)) {
			System.out.println("Invalid Selection");
			selection = scnr.nextLine();
		}
		
		scnr.close();
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
	
	public Person findPerson(Person[] personArray, String searchID) {
		
		for (Person person : personArray) {
			if (person.id.equalsIgnoreCase(searchID))
				return person;
		}
		
		return null;
	}
	
	public abstract void print();

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
		String dept = scnr.nextLine();
		
		while (!isValidDepartment(dept)) {
			//TODO print Error message
			dept = scnr.nextLine();
		}
		
		// Change to first letter upper case, rest of string lower case
		dept = dept.toLowerCase();
		dept = dept.substring(0, 1).toUpperCase() + dept.substring(1);
		
		this.department = dept;
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
	
	
	protected boolean isValidRank(String inputRank) {
		return (inputRank.equalsIgnoreCase(RANK_PROF) || inputRank.equalsIgnoreCase(RANK_ADJ));
	}
	
	@Override
	public void print() {
		// TODO Auto-generated method stub
		
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
	
	protected boolean isValidStatus(String status) {
		return (status.equalsIgnoreCase("p") || status.equalsIgnoreCase("f"));
	}
	
	@Override
	public void print() {
		// TODO Auto-generated method stub
		
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
}
