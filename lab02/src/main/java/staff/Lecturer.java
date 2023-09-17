package staff;

import java.time.LocalDate;

public class Lecturer extends StaffMember {
    // Attributes
    private String school;
    private char status;

    // Constructors
    public Lecturer(String name, double salary, LocalDate hireDate, LocalDate endDate, String school, char status) {
        super(name, salary, hireDate, endDate);
        this.school = school;
        this.status = status;
    }
    public Lecturer(String name, double salary, String school, char status) {
        super(name, salary);
        this.school = school;
        this.status = status;
    }
    public Lecturer() {
        super();
    }

    // Getters and Setters
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    public char getStatus() {
        return status;
    }
    public void setStatus(char status) {
        this.status = status;
    }

    // Methods
    @Override
    public String toString() {
        String staff = super.toString();
        staff += ", school = '" + school + "', status = '" + status + "'";
        return staff;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Lecturer other = (Lecturer) obj;

        if (this.school == other.school && this.status == other.status) {
            return true;
        }
        return false;
    }
}
