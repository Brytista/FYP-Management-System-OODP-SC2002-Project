package package1;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
/**
 * Child Class of users that used to represent a supervisor
*/
public class Supervisor extends User {
    /**
     * Represents the number of projects managed by a manager.
     * The default value is 0.
     */
    int numberOfProjectManaged = 0;

    /**
     * Represents a list of Project being handled by the supervisor.
     * An ArrayList is used to store the projects.
     */
    List<Project> projectList = new ArrayList<Project>();
    
    /**
     * Represents a list of Request objects that are pending.
     * An ArrayList is used to store the pending requests.
     */
    List<Request> pendingRequest = new ArrayList<Request>();

    /**
     * Represents a list of available requests that can be made.
     * An ArrayList is used to store the available requests, which are initially set to "1. Change Supervisor".
     * The list is marked as private and static, and cannot be accessed outside of the class.
     */
    private static List<String> availableRequests = new ArrayList<>(Arrays.asList("1. Change Supervisor"));
 
    /**
     * Represents the type of supervisor request.
     * The variable is marked as private, and can only be accessed within the class.
     */
    private SupervisorRequest requestType;

    /**
     * Represents a list of all supervisors.
     * An ArrayList is used to store the supervisors.
     * The list is marked as private and static, and cannot be accessed outside of the class.
     */
    private static List<Supervisor> allSupervisor = new ArrayList<Supervisor>();

    /**
     * Represents a list of Student managed by a supervisor.
     * An ArrayList is used to store the students.
     */
    List<Student> studentManaged = new ArrayList<Student>();

    /**
     * Represents a Scanner object used for reading input from the user.
     * The scanner is initialized with the standard input stream.
     */
    Scanner sc = new Scanner(System.in);


    /**
     * Creates a new Supervisor object with the specified user ID, password, name, and email.
     * The constructor calls the constructor of the parent class, User, to set the common properties of a user.
     * It also adds the new supervisor to the list of all supervisors.
     *
     * @param userID the user ID of the new supervisor
     * @param password the password of the new supervisor
     * @param name the name of the new supervisor
     * @param email the email of the new supervisor
     */
    public Supervisor(String userID, String password, String name, String email) {
        super(userID, password, name, email);
        addToSupervisorList(this);
    }


    /**
     * Creates a new Supervisor object with the specified user ID, password, name, and email, and adds it to the list of all supervisors.
     * If a supervisor with the specified user ID already exists, an error message is printed and null is returned.
     *
     * @param userID the user ID of the new supervisor
     * @param password the password of the new supervisor
     * @param name the name of the new supervisor
     * @param email the email of the new supervisor
     * @return the new Supervisor object if it is created successfully, or null if an error occurs
     */
    public static Supervisor createSupervisor(String userID, String password, String name, String email) {
        try {
            if(Supervisor.getSupervisorByID(userID) == null) {
                Supervisor newSupervisor = new Supervisor(userID, password, name, email);
                return newSupervisor;
            } else {
                System.out.println("Error: User ID already exists");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }


    /**
     * Creates a new Project object with the specified project title and adds it to the supervisor's project list.
     * If the supervisor has reached their project cap, all of their projects are made unavailable.
     *
     * @param projectTitle the title of the new project
     * @return 1 if the project is created successfully, or 0 if an error occurs
     */
    public int createProject(String projectTitle) {
        try {
            Project newProject = new Project(this, projectTitle); //In the project creation, it automatically add the project to the supervisor's project list
            if(this.capReached()){
                this.makeAllProjectsUnavailable(); // make all projects unavailable if the supervisor has reached his cap
            }
            return 1;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }

    

    /**
     * Displays a list of projects currently managed by the supervisor.
     * If the supervisor has no projects, a message is displayed indicating this.
     */
    public void displayProjects() {
        try {
            if(projectList.isEmpty()){
                System.out.println("You have no projects yet");
                return;
            }
            else{
                for (Project project : projectList) {
                    project.displayProject();
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    

    /**
     * Sets the type of supervisor request based on the given request ID.
     * 
     * @param requestID an integer representing the type of supervisor request to set
     *                  (1 for RequestChangeSupervisor, other IDs are invalid)
     * @return 1 if the request type is set successfully, or 0 if an error occurs
     */
    @Override
    public int chooseAndSetRequest(int requestID) {

        try {

            switch (requestID) {
                case 1:
                    this.requestType = new RequestChangeSupervisor(this,null,null,null);
                    break;

                default:
                    System.out.println("Error: Invalid request ID");
                    return 0;
            }

        } catch (Exception e) {
            System.out.println("Error: Invalid request ID");
            return 0;
        }
        return 1;
    }


    /**
     * Modifies the title of a project belonging to the supervisor, given the project ID and new title.
     * 
     * @param projectID an integer representing the ID of the project to modify
     * @param newTitle a string representing the new title of the project
     * @return 1 if the project title is modified successfully, or 0 if an error occurs
     */
    public int modifyProjectTitle(int projectID, String newTitle){
        try {
            Project project = Project.getProjectByID(projectID);
            if (doesProjectBelongToSupervisor(project)) {
                project.changeProjectTitle(newTitle);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }



    /**
     * Checks if the supervisor has reached the maximum number of projects they can manage.
     * 
     * @return true if the supervisor has reached the maximum number of projects they can manage (2), false otherwise
     */
    public boolean capReached() {
        try {
            if (numberOfProjectManaged >= 2) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    

    /**
     * Displays a list of available supervisor requests.
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
     * Adds a new request to the list of available supervisor requests.
     *
     * @param request the new request to add
     * @return 1 if the request was added successfully, 0 if the request is null or already exists in the list
     */
    public static int addAvailableRequest(String request) {
        try {
            if(availableRequests.contains(request)||request==null) return 0;
            availableRequests.add(request);
            return 1;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }



    /**
     * Displays all the pending requests from the `pendingRequest` list.
     * If the `pendingRequest` list is empty, it displays a message indicating there are no pending requests.
     */
    public void viewAllPendingRequest(){
        try {
            if(pendingRequest.size() == 0){
                System.out.println("There are no pending Request");
                return; 
            }
            DisplayAll.displayPendingRequest(pendingRequest);
            return; 
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    
    /**
    *This method creates a request for a project to be assigned to a replacement supervisor by the FYPCoordinator.
    *@param recipientID The ID of the FYPCoordinator who will receive the request.
    *@param projectID The ID of the project to be reassigned.
    *@param replacementSupervisorID The ID of the supervisor to whom the project will be reassigned.
    *@return 1 if the request was successfully sent, 0 otherwise.
    */
    public int makeRequest(String recipientID, int projectID, String replacementSupervisorID) {
        try {
            Project project = Project.getProjectByID(projectID);
            Supervisor replacementSupervisor = Supervisor.getSupervisorByID(replacementSupervisorID);
            FYPCoordinator FYPCoor = FYPCoordinator.getCoordinatorByID(recipientID); 
            
            if (project == null||FYPCoor==null||replacementSupervisor==null){
                System.out.println("NULL INPUT");
                return 0;}

            if(requestType!=null){
                this.requestType.create(this, FYPCoor, project, replacementSupervisor); 
                if(requestType.sendRequest()==1){
                    return 1; // request sent
                }
                else{
                    return 0; // request not sent
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to make the request: " + e.getMessage());
        }
            
        return 1;
    }
    
    

    /**
     * Returns an FYPCoordinator object that corresponds to the given recipient ID.
     * 
     * @param recipientID the unique identifier for the FYPCoordinator object to be retrieved
     * @return the FYPCoordinator object that corresponds to the given recipient ID, or null if an error occurred
     */
    public FYPCoordinator selectRecipient(String recipientID){
        try {
            return FYPCoordinator.getCoordinatorByID(recipientID);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to retrieve the FYP Coordinator with ID " + recipientID + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns a Supervisor object that corresponds to the given replacement supervisor ID.
     * 
     * @param replacementSupervisorID the unique identifier for the Supervisor object to be retrieved
     * @return the Supervisor object that corresponds to the given replacement supervisor ID, or null if an error occurred
     */
    public Supervisor selectSupervisor(String replacementSupervisorID){
        try {
            return Supervisor.getSupervisorByID(replacementSupervisorID);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to retrieve the supervisor with ID " + replacementSupervisorID + ": " + e.getMessage());
            return null;
        }
    }

    
    
    /**
     * Displays all existing Supervisor objects in the allSupervisor list.
     * If the list is empty, a message is printed to indicate that there are no supervisors.
     * If the list is not empty, the details of each supervisor are printed, including name, user ID, and email address.
     * The details are separated by a line of dashes for clarity.
     */
    public static void displayAllSupervisor(){
        try {
            if(allSupervisor.size() == 0){
                System.out.println("There are no supervisors");
            }
            else{
                for (Supervisor supervisor: allSupervisor){
                    System.out.println("----------------------------------");
                    String name = supervisor.getUserName();
                    String UserID = supervisor.getUserID();
                    String userEmail = supervisor.getEmail();
                    System.out.println("Name: " + name + ", UserID: " + UserID + ", Email: " + userEmail + "\n");
                    System.out.println("----------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to display the supervisors: " + e.getMessage());
        }
    }
    
    /**
     * Displays details of all available Supervisor objects in the allSupervisor list.
     * A supervisor is considered available if their project capacity has not been reached.
     * If there are no available supervisors, a message is printed to indicate so.
     * The details of each available supervisor are printed, including name, user ID, and email address.
     * The details are separated by a line of dashes for clarity.
     */
    public static void displayAllAvailableSupervisor(){
        try {
            int num = 0; 
            for (Supervisor supervisor: allSupervisor){
                if(supervisor.capReached() == false){
                    System.out.println("----------------------------------");
                    String name = supervisor.getUserName();
                    String UserID = supervisor.getUserID();
                    String userEmail = supervisor.getEmail();
                    System.out.println("Name: " + name + ", UserID: " + UserID + ", Email: " + userEmail + "\n");
                    System.out.println("----------------------------------");
                    num++;
                }
            }
            if(num == 0){
                System.out.println("There are no available supervisors");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to display the supervisors: " + e.getMessage());
        }
    }



    /**
     * Changes the status of all projects owned by this user to AVAILABLE.
     * Returns the number of projects whose status was successfully modified.
     * If an error occurs, a message is printed to the console and 0 is returned.
     * 
     * @return The number of projects whose status was successfully modified.
     */
    public int makeAllProjectsAvailable(){
        try {
            return Project.massModifyProjectStatus(this.getUserID(), ProjectStatus.AVAILABLE);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to make all projects available: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Changes the status of all projects owned by this user to UNAVAILABLE.
     * Returns the number of projects whose status was successfully modified.
     * If an error occurs, a message is printed to the console and 0 is returned.
     * 
     * @return The number of projects whose status was successfully modified.
     */
    public int makeAllProjectsUnavailable(){
        try {
            return Project.massModifyProjectStatus(this.getUserID(), ProjectStatus.UNAVAILABLE);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to make all projects unavailable: " + e.getMessage());
            return 0;
        }
    }

    
    /**
     * Returns a list of students managed by this supervisor.
     * 
     * @return the list of students managed by this supervisor
     */
    public List<Student> getStudentsManaged(){
        try {
            return studentManaged; 
        } catch (Exception e) {
            System.out.println("An error occurred while trying to get the managed students: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns the supervisor with the specified name.
     * 
     * @param name the name of the supervisor to retrieve
     * @return the supervisor with the specified name, or null if no supervisor was found
     */
    public static Supervisor getSupervisorByName(String name){
        try {
            for(Supervisor supervisor: allSupervisor){
                if(supervisor.getUserName().equals(name)){
                    return supervisor;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to get the supervisor by name: " + e.getMessage());
            return null;
        }
    }

    
    /**
     * Returns the Supervisor object with the specified UserID.
     * 
     * @param UserID the ID of the Supervisor to retrieve
     * @return the Supervisor object with the specified UserID, or null if no such Supervisor exists
     */
    public static Supervisor getSupervisorByID(String UserID){
        try {
            for(Supervisor supervisor: allSupervisor){
                if(supervisor.getUserID().equals(UserID)){
                    return supervisor;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to get the supervisor by ID: " + e.getMessage());
            return null;
        }
    }

    
    /**
     * Adds the specified list of Supervisors to the allSupervisor list.
     * 
     * @param supervisorLists the list of Supervisors to add
     * @return 1 if the Supervisors were added successfully, 0 if an error occurred
     */
    public static int addInitialSupervisor(List<Supervisor> supervisorLists){
        try {
            if(supervisorLists == null) return 0;
            for(Supervisor supervisor : supervisorLists) {
                allSupervisor.add(supervisor);
            }
            return 1;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to add initial supervisors: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Adds the specified Supervisor to the allSupervisor list.
     * 
     * @param supervisor the Supervisor to add
     * @return 1 if the Supervisor was added successfully, 0 if an error occurred
     */
    public static List<Supervisor> getSupervisors() {
        return allSupervisor;
    }
    
    /**
     * Assigns the specified list of Supervisors to the allSupervisor list.
     * 
     * @param supervisorsList the list of Supervisors to assign
     * @return 1 if the list was assigned successfully, 0 if an error occurred
     */
    public static int assignSupervisorsList(List<Supervisor> supervisorsList) {
        try {
            allSupervisor = supervisorsList;
            System.out.println("Supervisor list assigned with length " + allSupervisor.size() + ".");
        } catch (Exception e) {
            System.out.println("An error occurred while trying to assign the supervisor list: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    
    /**
     * Adds a supervisor to the list of all supervisors.
     * 
     * @param supervisor The supervisor to add to the list.
     * @return 1 if the supervisor was successfully added, 0 otherwise.
     */
    public static int addToSupervisorList(Supervisor supervisor) {
        try {
            if (supervisor == null)
                return 0;
            allSupervisor.add(supervisor);
            return 1;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to add a supervisor to the list: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Removes a supervisor from the list of all supervisors.
     * 
     * @param supervisor The supervisor to remove from the list.
     * @return 1 if the supervisor was successfully removed, 0 if the supervisor was not found in the list.
     */
    public static int removeFromSupervisorList(Supervisor supervisor){
        try {
            boolean found = false;
            for(Supervisor supervisors: allSupervisor){
                if(supervisor.equals(supervisors)){
                    found = true;
                    break;
                }
            }
            if(!found) return 0;
            allSupervisor.remove(supervisor);
            return 1;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to remove a supervisor from the list: " + e.getMessage());
            return 0;
        }
    }

    
   /**
     * Returns the project with the specified ID.
     * @param projectID the ID of the project to select
     * @return the project with the specified ID, or null if no project was found with the given ID
     */
    private Project selectProject(int projectID) {
        try {
            return Project.getProjectByID(projectID);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to select the project with ID " + projectID + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Adds a pending request to the list of pending requests.
     * @param request the request to add
     * @return 1 if the request was successfully added, 0 if the request is null
     */
    public int addPendingRequest(Request request) {
        try {
            if (request == null)
                return 0;
            pendingRequest.add(request);
            return 1;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to add a pending request: " + e.getMessage());
            return 0;
        }
    }

    
    /**
     * Determines if the specified project belongs to the supervisor.
     *
     * @param project the project to check
     * @return true if the project belongs to the supervisor, false otherwise
     */
    public boolean doesProjectBelongToSupervisor(Project project) {
        try {
            for(Project projects: projectList) {
                if(project.equals(projects)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to determine if the project belongs to the supervisor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Determines if the specified student belongs to the supervisor.
     *
     * @param student the student to check
     * @return true if the student belongs to the supervisor, false otherwise
     */
    public int assignStudentManaged(List<Student> students) {
        try {
            studentManaged = students;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to assign the student managed: " + e.getMessage());
            return 0;
        }

        return 1;
    }
    

    /**
     * Adds the specified student to the list of students managed by the supervisor and increments the number of projects managed.
     *
     * @param student the student to add to the list
     * @return 1 if the student was added successfully, 0 otherwise
     */
    public int addStudentManaged(Student student) {
        try {
            if (student == null) return 0;
            studentManaged.add(student);
            numberOfProjectManaged++;
            return 1;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to add a managed student: " + e.getMessage());
            return 0;
        }
    }

    
    /**
     * Returns true as the object is a Supervisor.
     *
     * @return true as the object is a Supervisor.
     */
    @Override
    public boolean isSupervisor() {
        return true;
    }

    /**
     * Removes a student from the list of managed students.
     *
     * @param student the Student object to remove.
     * @return 1 if the student was successfully removed, otherwise 0.
     */
    public int removeStudentManaged(Student student) {
        try {
            if (numberOfProjectManaged == 0)
                return 0;
            numberOfProjectManaged--;
            if(studentManaged.remove(student)) return 1;
            else return 0;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to remove a managed student: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Removes a request from the list of pending requests.
     *
     * @param request the Request object to remove.
     * @return 1 if the request was successfully removed, otherwise 0.
     */
    public int removePendingRequest(Request request){
        try {
            for(Request requests: pendingRequest){
                if(request.equals(requests)){
                    pendingRequest.remove(request);
                    return 1;
                }
            }
            return 0;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to remove a pending request: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Adds a project to the list of projects supervised by this supervisor.
     *
     * @param project the project to add to the list
     * @return 1 if the project was successfully added, 0 if an error occurred or if the project parameter is null
     */
    public int addProject(Project project){
        try {
            if(project == null) return 0;
            projectList.add(project);
            return 1;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to add a project: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Removes a project from the list of projects supervised by this supervisor.
     * If the removal of the project causes the supervisor to have less than the maximum number of projects they can supervise, all projects will be made available for students to select.
     *
     * @param project the project to remove from the list
     * @return 1 if the project was successfully removed, 0 if an error occurred or if the project parameter is null
     */
    public int removeProject(Project project){
        try {
            boolean capReachedBefore = this.capReached(); // Check if the supervisor has reached the cap before removing the project
            for(Project projects: projectList){
                if(project.equals(projects)){
                    projectList.remove(projects);
                    Project.removeFromProjectList(projects);
                    this.removeStudentManaged(project.getStudent());
                    break; 
                }
            }
            if(capReachedBefore && !this.capReached()){
                this.makeAllProjectsAvailable(); 
                return 1; 
            }
            else{
                return 1; 
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to remove a project: " + e.getMessage());
            return 0;
        }
    }

    

/**
 * This method checks if a supervisor's login credentials are valid.
 * @param userID The userID entered by the supervisor.
 * @param password The password entered by the supervisor.
 * @return Returns 1 if the userID and password match a supervisor's account, 0 otherwise.
 */
public static int loginSupervisor(String userID, String password) {
    try {
        for (Supervisor supervisor : allSupervisor) {
            if (supervisor.getUserID().equals(userID)) {
                if (supervisor.getPassword().equals(password)) {
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

/**
 * This method checks if the supervisor has any projects assigned.
 * @return Returns true if the supervisor has projects assigned, false otherwise.
 */
public boolean doSupervisorHaveProject(){
    try{
        if(projectList.isEmpty()) {
            return false; //if the project list is empty, return false
        } else {
            return true; //if the project list is not empty, return true
        }
    } catch(Exception e) {
        System.err.println("Error: " + e.getMessage());
        return false;
    }
}



}