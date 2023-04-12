package package1;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends User {

    private StudentRequest requestType;
    private StudentRequestWithString requestTypeWithString;
    private Project project;
    private Boolean requestProject = false; // true if student has requested for a project
    private Boolean isDeregistered = false; // true if student has deregistered

    private static List<Student> students = new ArrayList<>();
    private static List<String> availableRequests = new ArrayList<>(
            Arrays.asList("1. Project Allocation", "2. Change Project Title", "3. Deregistration"));

    Scanner scanner = new Scanner(System.in);

    // Constructor
    public Student(String userID, String password, String name, String email) {
        super(userID, password, name, email);
        // addToStudentsList(this); // add student to students list
    }

    public int setIsDeregistered(Boolean change) {
        if (change == null)
            return 0; // failure (change cannot be null
        this.isDeregistered = change;
        return 1;
    }

    public Boolean getIsDeregistered() {
        return this.isDeregistered;
    }

    public static Student createStudent(String userID, String password, String name, String email) {
        try {
            if (Student.getStudentByID(userID) == null) {
                Student newStudent = new Student(userID, password, name, email);
                if (Student.addToStudentsList(newStudent) == 0) {
                    return null;
                }
                return newStudent;
            } else {
                System.out.println("--------------------");
                System.out.println("Error: User ID already exists");
                System.out.println("--------------------");
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    // displayProjects(): displays all projects
    public void displayProjects() {
        try {
            if (this.isProjectAssigned()) {
                this.project.displayProject();
            } else {
                System.out.println("--------------------");
                System.out.println("You have not registered a project.");
                System.out.println("--------------------");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return;
    }

    // displayAvailableRequests(): displays all available requests
    public static void displayAvailableRequests() {
        try {

            for (String request : availableRequests) {
                System.out.println(request);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static int addAvailableRequest(String request) {
        if (request == null) {
            System.err.println("Error: Request cannot be null");
            return 0;
        }
        if (availableRequests.contains(request)) {
            System.err.println("Error: Request already exists");
            return 0;
        }
        availableRequests.add(request);
        return 1;
    }

    public int getProjectID() {
        try {
            if (this.isProjectAssigned()) {
                return this.project.getProjectID();
            } else {
                System.out.println("No Project Is Assigned!");
                return -1;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return -1;
    }

    // ADD NEW METHODS HERE
    public Boolean returnRequestProject() {
        return this.requestProject;
    }

    // ADD NEW METHODS HERE
    public void setRequestProject(Boolean change) {
        this.requestProject = change;
    }

    // chooseAndSetRequest(): sets the request type based on user selection
    @Override
    public int chooseAndSetRequest(int requestID) {

        try {
            switch (requestID) {
                case 1:
                    if (this.returnRequestProject() || this.isProjectAssigned()) {
                        System.out.println(
                                "Error: You have requested for a Project Allocation or a Project is already assigned to you");
                        return 0;
                    }
                    this.requestType = new RequestProjectAllocation(this, null, null);
                    this.requestTypeWithString = null;
                    break;
                case 2:
                    if (this.isProjectAssigned() == false) {
                        System.out.println("Error: No project is assigned to you, so cannot change project title");
                        return 0;
                    }
                    this.requestType = null;
                    this.requestTypeWithString = new RequestChangeProjectTitle(this, null, null, null);
                    break;
                case 3:
                    if (this.isProjectAssigned() == false) {
                        System.out.println("Error: No project is assigned to you");
                        return 0;
                    }
                    this.requestType = new RequestDeregistration(this, null, null);
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

        return 1; // request type set successfully
    }

    // getNewProjectTitle(): gets the new project title from the user
    // getNewProjectTitle(): gets the new project title from the user
    public String getNewProjectTitle() {
        try {
            System.out.println("Enter new project title: ");
            String newProjectTitle = scanner.nextLine();
            return newProjectTitle;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    // makeRequest(): makes a request to a supervisor; 1 returned if successful,
    public int makeRequest(String recipientID, int projectID) {

        try {
            Project project = selectProject(projectID);
            Supervisor recipient = selectRecipient(recipientID);

            if (project == null || recipient == null) {
                System.err.println("Error: Invalid project or recipient ID");
                return 0;
            }

            if (this.requestType != null) {

                this.requestType.create(this, recipient, project);

                if (this.requestType.sendRequest() == 1) {
                    if (requestType instanceof RequestProjectAllocation) {
                        setRequestProject(true);
                    }
                    return 1;
                } // request is sent
                else {
                    return 0; // request not sent
                }

            } else if (this.requestTypeWithString != null) {

                String newProjectTitle = getNewProjectTitle();
                this.requestTypeWithString.create(this, recipient, project, newProjectTitle);

                if (this.requestTypeWithString.sendRequest() == 1) {
                    return 1; // request is sent
                } else {
                    return 0; // request not sent
                }

            } else {
                System.err.println("Error: No request type selected");
                return 0;
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }

    // selectProject(): returns the project with the selected ID
    private Project selectProject(int projectID) {
        try {
            return Project.getProjectByID(projectID);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    // selectRecipient(): returns the supervisor with the selected ID
    private Supervisor selectRecipient(String supervisorID) {
        try {
            if (Supervisor.getSupervisorByID(supervisorID) == null) {
                return FYPCoordinator.getCoordinatorByID(supervisorID);
            }
            ;
            return Supervisor.getSupervisorByID(supervisorID);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    // viewAllProjects(): displays all available projects
    public void viewAllProjects() {
        try {
            if (this.getIsDeregistered()) {
                System.out.println("--------------------");
                System.out.println("You are not allowed to make selection again as you deregistered your FYP");
                System.out.println("--------------------");
                return;
            }
            Project.displayAvailableProjects();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // isProjectAssigned(): returns true if the student has an assigned project
    public boolean isProjectAssigned() {
        try {
            return this.project != null;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    // changeProject(): changes the student's project; 1 returned if successful
    public int changeProject(Project newProject) {
        try {
            this.project = newProject;
            return 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    public Project getProject() {
        return this.project;
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

    public static List<Student> getStudents() {
        return students;
    }

    // displayAllStudents(): displays all students
    public static void displayAllStudents() {
        try {
            for (Student student : students) {
                System.out.println("Student ID: " + student.getUserID());
                System.out.println("Student Name: " + student.getUserName());
                System.out.println("Student Email: " + student.getEmail());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // getStudentByName(): returns the student(s) with the selected name
    public static List<Student> getStudentByName(String name) {
        List<Student> returnStudents = new ArrayList<>();
        try {
            for (Student student : students) {
                if (student.getUserName().equals(name)) {
                    returnStudents.add(student);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return returnStudents;
    }

    // getStudentByID(): returns the student with the selected ID
    public static Student getStudentByID(String userID) {
        Student returnStudent = null;
        try {
            for (Student student : students) {
                if (student.getUserID().equals(userID)) {
                    returnStudent = student;
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
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

    // assignStudentsList(): assigns a list of students to the students List object
    public static int assignStudentsList(List<Student> studentsList) {
        try {
            students = studentsList;
            System.out.println("Students list assigned with length " + students.size() + ".");
        } catch (Exception e) {
            System.out.println("An error occurred while trying to assign the student list: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // addToStudentsList(): adds a student to the students List object
    public static int addToStudentsList(Student student) {

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
        return true;
    }

    public static int loginStudent(String userID, String password) {
        try {
            for (Student student : students) {
                if (student.getUserID().equals(userID)) {
                    if (student.getPassword().equals(password)) {
                        // User exists and password is correct
                        return 1;
                    } else {
                        // User exists but password is incorrect
                        return 0;
                    }
                }
            }
            // User does not exist
            return 0;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }
}
