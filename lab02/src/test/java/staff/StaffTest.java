package staff;

public class StaffTest {
    // Add your tests here

    public static void printStaffDetails(StaffMember staff) {
        System.out.println(staff);
    }

    public static void main(String[] args) {
        StaffMember staff1 = new StaffMember("Louis", 10000);
        Lecturer lecturer1 = new Lecturer("Yuna", 10000, "Engineering", 'A');
        printStaffDetails(staff1);
        printStaffDetails(lecturer1);
        System.out.println(staff1.equals(lecturer1));
        System.out.println(lecturer1.equals(staff1));
        System.out.println(staff1.equals(staff1));
        System.out.println(lecturer1.equals(lecturer1));
    }
}
