package package1;

import java.time.Period;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private StudentRequest requestType;
    private StudentRequestWithString requestTypeWithString;
    private Project project;

    private static List<Student> students = new ArrayList<>();
    private static List<String> availableRequests = new ArrayList<>(Arrays.asList("Project Allocation", "Change Project Title","Deregistration"));

    // Constructor
    public Student(String userID, String password, String name, String email) {
        super(userID, password, name, email);
        students.add(this);
    }

    // displayProjects(): displays all projects
    public void displayProjects() {
        if (this.isProjectAssigned()) {
            System.out.println(project.getProjectID() + " " + project.getProjectTitle());
        }
    }

    // displayAvailableRequests(): displays all available requests
    public static void displayAvailableRequests() {
        for (int i = 0; i < availableRequests.size(); i++) {
            System.out.println("Choose " + (i+1) + ": " + availableRequests.get(i));
        }
    }

    @Override
    public int chooseAndSetRequest(int requestID) {

        try {
            switch (requestID) {
                case 1:
                    this.requestType = new RequestProjectAllocation();
                    this.requestTypeWithString = null;
                    break;
                case 2:
                    this.requestType = null;
                    this.requestTypeWithString = new RequestChangeProjectTitle();
                    break;
                case 3:
                    this.requestType = new RequestDeregistration();
                    this.requestTypeWithString = null;
                    break;
                default:
                    System.err.println("Error: Invalid request ID");
                    return 0;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // makeRequest(): makes a request to a supervisor; 1 returned if successful,
    public int makeRequest(String recipientID, int requestID, int projectID) {
        try {
            Project project = selectProject(projectID);
            Supervisor recipient = selectRecipient(recipientID);
            StudentRequest request = new StudentRequest(this, recipient, project);
            request.sendRequest();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // selectProject(): returns the project with the selected ID
    private Project selectProject(int projectID) {
        return Project.getProjectByID(projectID);
    }

    // selectRecipient(): returns the supervisor with the selected ID
    private Supervisor selectRecipient(String supervisorID) {
        return Supervisor.getSupervisorByID(supervisorID);
    }

    // viewAllProjects(): displays all available projects
    public void viewAllProjects() {
        Project.displayAllProjects();
    }

    // isProjectAssigned(): returns true if the student has an assigned project
    public boolean isProjectAssigned() {
        return this.project != null;
    }

    // changeProject(): changes the student's project; 1 returned if successful
    public int changeProject(Project newProject) {

        if (!this.isProjectAssigned()) {
            System.err.println("Error: Student has no assigned project");
            return 0;
        } else {
            project = newProject;
        }

        return 1;
    }

    // removeProject(): removes the student's project
    public int removeProject() {
        try {
            this.project = null;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // displayAllStudents(): displays all students
    public void displayAllStudents() {
        for (Student student : students) {
            System.out.println(student.getUserID() + " " + student.getUserName());
        }
    }

    // getStudentByName(): returns the student(s) with the selected name
    public List<Student> getStudentByName(String name) {

        List<Student> returnStudents = new ArrayList<>();

        for (Student student : students) {
            if (student.getUserName() == name) {
                returnStudents.add(student);
            }
        }

        return returnStudents;
    }

    // getStudentByID(): returns the student with the selected ID
    public Student getStudentByID(String userID) {
        Student returnStudent = null;

        for (Student student : students) {
            if (student.getUserID() == userID) {
                returnStudent = student;
            }
        }

        return returnStudent;
    }

    // addInitialStudents(): adds a list of students to the students List object
    public int addInitialStudents(List<Student> studentsList) {

        try {
            for (Student student : studentsList) {
                students.add(student);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // addToStudentsList(): adds a student to the students List object
    public int addToStudentsList(Student student) {

        try {
            students.add(student);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // removeFromStudentsList(): removes a student from the students List object
    public int removeFromStudentsList(Student student) {

        try {
            students.remove(student);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // isStudent(): confirms whether the user belongs to a Student class
    @Override
    public boolean isStudent() {
        return true;
    }
}
