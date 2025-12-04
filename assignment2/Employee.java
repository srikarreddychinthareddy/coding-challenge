package assignment2;


public class Employee {
    public final String firstName;
    public final String gender;
    public final String startDate;
    public final String lastLoginTime;
    public final double salary;
    public final double bonusPercent;
    public final boolean seniorManagement;
    public final String team;

    public Employee(String firstName, String gender, String startDate, String lastLoginTime,
                    double salary, double bonusPercent, boolean seniorManagement, String team) {
        this.firstName = firstName;
        this.gender = gender;
        this.startDate = startDate;
        this.lastLoginTime = lastLoginTime;
        this.salary = salary;
        this.bonusPercent = bonusPercent;
        this.seniorManagement = seniorManagement;
        this.team = team;
    }
}