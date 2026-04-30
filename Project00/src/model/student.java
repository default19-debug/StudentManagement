package model;

public class student {
    private int id;
    private String firstName;
    private String lastName;
    private double gpa;
    private String major;
    private int enrolledYear;


    public student() {}

    public student(int id, String firstName, String lastName, double gpa, String major, int enrolledYear) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;
        this.major = major;
        this.enrolledYear = enrolledYear;
    }

    // Constructor without ID (used when creating a NEW student before DB assigns an ID)
    public student(String firstName, String lastName, double gpa, String major, int enrolledYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;
        this.major = major;
        this.enrolledYear = enrolledYear;
    }

    // --- GETTERS AND SETTERS ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public int getEnrolledYear() { return enrolledYear; }
    public void setEnrolledYear(int enrolledYear) { this.enrolledYear = enrolledYear; }
}