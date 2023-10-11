package unsw.enrolment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import unsw.enrolment.exceptions.InvalidEnrolmentException;
// import java.util.Collections;
// import java.util.stream.Stream;

public class CourseOffering {
    private Course course;
    private String term;
    private List<Enrolment> enrolments = new ArrayList<Enrolment>();

    public CourseOffering(Course course, String term) {
        this.course = course;
        this.term = term;
        this.course.addOffering(this);
    }

    public Course getCourse() {
        return course;
    }

    public String getCourseCode() {
        return course.getCourseCode();
    }

    public List<Course> getCoursePrereqs() {
        return course.getPrereqs();
    }

    public String getTerm() {
        return term;
    }

    public Enrolment addEnrolment(Student student) throws InvalidEnrolmentException {
        if (checkValidEnrolment(student)) {
            Enrolment enrolment = new Enrolment(this, student);
            enrolments.add(enrolment);
            student.addEnrolment(enrolment);
            return enrolment;
        } else {
            throw new InvalidEnrolmentException("student has not satisfied the prerequisites");
        }
    }

    private boolean checkValidEnrolment(Student student) {
        List<Course> prereqs = getCoursePrereqs();

        for (Course prereq : prereqs) {
            List<Enrolment> studentEnrolments = student.getEnrolments();
            boolean valid = studentEnrolments.stream().anyMatch(enrolment -> enrolment.checkPassedCourse(prereq));
            if (!valid) {
                return false;
            }
        }

        return true;
    }

    public List<Student> studentsEnrolledInCourse() {
        // List<Student> students = enrolments.stream().map(Enrolment::getStudent).collect(Collectors.toList());
        // Comparator<Student> myCmp = new Comparator<Student>() {
        //     @Override
        //     public int compare(Student o1, Student o2) {
        //         if (o1.getProgram() != o2.getProgram()) {
        //             return o1.getProgram() - o2.getProgram();
        //         }
        //         if (o1.getStreams().length != o2.getStreams().length) {
        //             return o1.getStreams().length - o2.getStreams().length;
        //         }
        //         if (o1.getName() != o2.getName()) {
        //             return o1.getName().compareTo(o2.getName());
        //         }
        //         return o1.getZid().compareTo(o2.getZid());
        //     }
        // };
        // students.sort(myCmp);
        List<Student> students = enrolments.stream().map(Enrolment::getStudent)
                .sorted(Comparator.comparing(Student::getProgram)
                        .thenComparing(Student::getStreams, (s1, s2) -> s1.length - s2.length)
                        .thenComparing(Student::getName)
                        .thenComparing(Student::getZid))
                .collect(Collectors.toList());
        System.out.println(students);
        return students;
    }

}
