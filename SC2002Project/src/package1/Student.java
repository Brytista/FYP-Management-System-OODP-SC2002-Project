package package1;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Child Class of users that used to represent a student
*/
public class Student extends User {
    /**
     * the type of request made by the student
     */
    private StudentRequest requestType;
    /**
     * the type of request made by the student with a string parameter
     */
    private StudentRequestWithString requestTypeWithString;
    /**
     * the project the student is currently working on
     */
    private Project project;
    /**
     * true if student has requested for a project
     */
    private Boolean requestProject = false;
    /**
     * true if student has deregistered
     */
    private Boolean isDeregistered = false;

    /**
     * a list of all student users in the system
     */
    private static List<Student> students = new ArrayList<>();
    /**
     * a list of available requests a student can make
     */
    private static List<String> availableRequests = new ArrayList<>(
            Arrays.asList("1. Project Allocation", "2. Change Project Title", "3. Deregistration"));
    /**
     * scanner object for user input
     */
    Scanner scanner = new Scanner(System.in);

    /**
     * Creates a new student user with the specified parameters and adds them to the list of students in the system.
     * 
     * @param userID   the user ID of the student
     * @param password the password of the student
     * @param name     the name of the student
     * @param email    the email of the student
     */
    public Student(String userID, String password, String name, String email) {
        super(userID, password, name, email);
        // addToStudentsList(this); // add student to students list
    }



    /**
     * Sets the student's isDeregistered status to the specified value.
     *
     * @param change the value to set the isDeregistered status to
     * @return 1 if the status was successfully set, 0 if the change parameter is null
     */
    public int setIsDeregistered(Boolean change){
        if(change == null) return 0; // failure (change cannot be null)
        this.isDeregistered = change;
        return 1;
    }

    /**
     * Returns the student's isDeregistered status.
     *
     * @return the student's isDeregistered status
     */
    public Boolean getIsDeregistered(){
        return this.isDeregistered;
    }

    /**
     * Creates a new student with the specified userID, password, name, and email, if a
     * student with the same userID does not already exist.
     *
     * @param userID the student's userID
     * @param password the student's password
     * @param name the student's name
     * @param email the student's email
     * @return the new student if created successfully, or null if a student with the same
     *         userID already exists or an error occurs
     */
    public static Student createStudent(String userID, String password, String name, String email){
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

    /**
     * Displays the student's assigned project, if one exists.
     * If the student does not have an assigned project, displays an error message.
     */
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

    /**
     * Displays all available requests.
     */
    public static void displayAvailableRequests() {
        try {

            for (String request : availableRequests) {
                System.out.println(request);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    /**
     * Adds the given request to the list of available requests.
     * @param request The request to add to the list of available requests.
     * @return Returns 1 if the request was successfully added to the list, or 0 if the request is null or already exists in the list.
     */
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


    /**
     * Returns the project ID for the given student if they have an assigned project.
     * @return Returns the project ID if the student has an assigned project, or -1 if no project is assigned or an exception occurs.
     */
    public int getProjectID(){
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


    
   /**
     * Returns the current value of the requestProject field.
     *
     * @return the current value of the requestProject field.
     */
    public Boolean returnRequestProject(){
        return this.requestProject; 
    }

    /**
     * Sets the value of the requestProject field to the given boolean value.
     *
     * @param change the new value to set the requestProject field to.
     */
    public void setRequestProject(Boolean change){
        this.requestProject = change;
    }

    /**
     * Chooses and sets the appropriate request type based on the given request ID.
     *
     * @param requestID the ID of the request type to set.
     * @return 1 if the request type is set successfully, 0 if an error occurs.
     */
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



    /**
     * Gets the new project title from the user.
     * 
     * @return A String containing the new project title entered by the user, or null if an error occurs
     */
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


    /**
     * Makes a request to a supervisor.
     * 
     * @param recipientID The ID of the supervisor receiving the request
     * @param projectID The ID of the project for which the request is being made
     * @return 1 if the request was successfully sent, 0 otherwise
     */
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
                } else {
                    return 0;
                }
            } else if (this.requestTypeWithString != null) {
                String newProjectTitle = getNewProjectTitle();
                this.requestTypeWithString.create(this, recipient, project, newProjectTitle);

                if (this.requestTypeWithString.sendRequest() == 1) {
                    return 1;
                } else {
                    return 0;
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



    /**
     * selectProject(): Returns the project with the selected ID
     * 
     * @param projectID the ID of the project to select
     * @return the selected project, or null if it cannot be found
     */
    private Project selectProject(int projectID) {
        try {
            return Project.getProjectByID(projectID);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }


    /**
     * selectRecipient(): Returns the supervisor with the selected ID
     * 
     * @param supervisorID the ID of the supervisor to select
     * @return the selected supervisor, or null if it cannot be found
     */
    private Supervisor selectRecipient(String supervisorID) {
        try {
            if(FYPCoordinator.getCoordinatorByID(supervisorID) != null) {
                return FYPCoordinator.getCoordinatorByID(supervisorID);
            }
            return Supervisor.getSupervisorByID(supervisorID);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * viewAllProjects(): Displays all available projects
     */
    public void viewAllProjects() {
        try {
            if(this.getIsDeregistered()){
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

    /**
     * isProjectAssigned(): Returns true if the student has an assigned project
     * 
     * @return true if the student has an assigned project, false otherwise
     */
    public boolean isProjectAssigned() {
        try {
            return this.project != null;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }


    /**
     * Changes the student's project.
     *
     * @param newProject the new project to be assigned to the student
     * @return 1 if successful, 0 if unsuccessful
     */
    public int changeProject(Project newProject) {
        try {
            this.project = newProject;
            return 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }


    /**
     * Returns the project assigned to the student.
     *
     * @return the project assigned to the student
     */
    public Project getProject() {
        return this.project;
    }


    /**
     * Removes the project assigned to the student.
     *
     * @return 1 if successful, 0 if unsuccessful
     */
    public int removeProject() {
        try {
            this.project = null;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    /**
     * Retrieves the list of students in the system.
     *
     * @return List of Student objects representing all the students in the system.
     */
    public static List<Student> getStudents() {
        return students;
    }


    /**
     * Displays all students in the system.
     */
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

    
    /**
     * Returns the student(s) with the selected name.
     * @param name the name to search for
     * @return a list of students with the given name
     */
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


    /**
     * Returns the student with the selected ID.
     * @param userID the ID to search for
     * @return the student with the given ID, or null if not found
     */
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


    /**
     * Adds a list of students to the students List object.
     * @param studentsList the list of students to add
     * @return 1 if successful, 0 otherwise
     */
    public static int addInitialStudents(List<Student> studentsList) {
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

    /**
     * Assigns a list of students to the students List object in the system.
     *
     * @param studentsList The list of Student objects to be assigned to the students list.
     * @return 1 if the assignment is successful, 0 if an error occurred during the assignment.
     */
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


    /**
     * Adds a student to the students List object
     *
     * @param student the Student object to be added
     * @return 1 if successful, 0 if unsuccessful
     */
    public static int addToStudentsList(Student student) {
        try {
            students.add(student);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * Removes a student from the students List object
     *
     * @param student the Student object to be removed
     * @return 1 if successful, 0 if unsuccessful
     */
    public int removeFromStudentsList(Student student) {
        try {
            students.remove(student);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * Confirms whether the user belongs to a Student class
     *
     * @return always returns true for a Student object
     */
    public boolean isStudent() {
        return true;
    }


    /**
     * Attempts to log in a student with the given user ID and password
     *
     * @param userID   the user ID of the student
     * @param password the password of the student
     * @return 1 if successful (user exists and password is correct), 0 if unsuccessful (user does not exist or password is incorrect)
     */
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
