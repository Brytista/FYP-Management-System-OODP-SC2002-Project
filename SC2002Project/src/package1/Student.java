package package1;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private StudentRequest requestType;
    private boolean projectAssigned = false;

    private static List<Student> students = new ArrayList<>();
    private static List<Supervisor> supervisors = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    // Constructor
    public Student(String userID, String password, String firstName, String lastName, String email) {
        super(userID, password, firstName, lastName, email);
        students.add(this);
    }

    // displayProjects(): displays all projects
    public void displayProjects() {
        for (Project project : projects) {
            System.out.println(project.getProjectID() + " " + project.getProjectTitle());
        }
    }

    @Override
    public int chooseAndSetRequest(int requestID) {

        try {
            switch (requestID) {
                case 1:
                    requestType = new RequestProjectAllocation();
                    break;
                case 2:
                    requestType = new RequestChangeProjectTitle();
                    break;
                case 3:
                    requestType = new RequestDeregistration();
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
        Project returnProject;

        for (Project project : projects) {
            if (project.getProjectID() == projectID) {
                returnProject = project;
            }
        }

        return returnProject;
    }

    // selectRecipient(): returns the supervisor with the selected ID
    private Supervisor selectRecipient(String supervisorID) {
        Supervisor returnSupervisor;

        for (Supervisor recipient : supervisors) {
            if (recipient.getUserID() == supervisorID) {
                returnSupervisor = recipient;
            }
        }

        return returnSupervisor;
    }

    // viewAllProjects(): displays all available projects
    public void viewAllProjects() {
        for (Project project : projects) {
            System.out.println(project.getProjectID() + " " + project.getProjectTitle());
        }
    }

    // hasAssignedProject(): returns true if the student has an assigned project
    public boolean hasAssignedProject() {
        return projectAssigned;
    }

    // changeProject(): changes the student's project; 1 returned if successful
    public int changeProject(Project newProject) {

        if (projectAssigned == false) {
            System.err.println("Error: Student has no assigned project");
            return 0;
        } else {
            try {
                projects[0] = newProject;

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return 0;
            }
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
        for (Student student : students) {
            if (student.getUserID() == userID) {
                return student;
            }
        }
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
    public boolean isStudent() {

    }
}
