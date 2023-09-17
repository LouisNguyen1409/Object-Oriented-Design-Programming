package staff;

import java.time.LocalDate;
/**
 * A staff member
 * @author Robert Clifton-Everest
 *
 */
public class StaffMember {
    // add your code here
    // Attributes
    private String name;
    private double salary;
    private LocalDate hireDate;
    private LocalDate endDate;

    // Constructors
    public StaffMember(String name, double salary, LocalDate hireDate, LocalDate endDate) {
        this.name = name;
        this.salary = salary;
        this.hireDate = hireDate;
        this.endDate = endDate;
    }

    public StaffMember(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public StaffMember() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalaray() {
        return salary;
    }

    public void setSalaray(double salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate date) {
        this.hireDate = date;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }

    // Methods
    @Override
    public String toString() {
        return "StaffMember: "
                + "name = '" + name + "'"
                + ", salary = " + salary
                + ", hireDate = " + hireDate
                + ", endDate = " + endDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        StaffMember other = (StaffMember) obj;

        if (this.name == other.name && this.salary == other.salary && this.hireDate == other.hireDate
                && this.endDate == other.endDate) {
            return true;
        }
        return false;
    }
}
