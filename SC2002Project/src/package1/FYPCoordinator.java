package package1;
import java.util.List;
import java.util.ArrayList;

/**
 * Child Class of Supervisor that used to represent a FYPCoordinator (because a FYPCoordinator is a supervisor)
*/
public class FYPCoordinator extends Supervisor {
    /**
     * List of all FYPCoordinator
     */
    static private List<FYPCoordinator> coordinators = new ArrayList<>();

    /**
     * Constructs a new FYPCoordinator object with the given user ID, password, name, and email.
     * Adds the new coordinator to the coordinator list.
     *
     * @param userID the user ID of the new coordinator
     * @param password the password of the new coordinator
     * @param name the name of the new coordinator
     * @param email the email of the new coordinator
     */
    public FYPCoordinator(String userID, String password, String name, String email) {
        super(userID, password, name, email);
        addCoordinatorToList(this);
    }

    /**
     * Creates a new FYPCoordinator object with the given user ID, password, name, and email.
     * Checks if the given user ID already exists in the system before creating the new coordinator.
     *
     * @param userID the user ID of the new coordinator
     * @param password the password of the new coordinator
     * @param name the name of the new coordinator
     * @param email the email of the new coordinator
     * @return a new FYPCoordinator object if the given user ID does not already exist, otherwise null
     */
    public static FYPCoordinator createFYPCoordinator(String userID, String password, String name, String email) {
        try {
            if(FYPCoordinator.getCoordinatorByID(userID)==null) {
                FYPCoordinator newCoordinator = new FYPCoordinator(userID, password, name, email);
                return newCoordinator;
            }
            else{
                System.out.println("Error: User ID already exists");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Displays all projects in the system.
     */
    public void viewAllProjects() {
        try {
            Project.displayAllProjects();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Generates and displays a project report for all projects supervised by the given supervisor ID.
     *
     * @param supervisorID the supervisor ID to generate the report for
     */
    public void generateProjectReportBySupervisorID(String supervisorID) {
        try {
            List<Project> projects2 = Project.getProjectBySupervisor(supervisorID); 
            for (Project project : projects2) {
                    project.displayProject();
            }
            return; 
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Generates a report of all projects with the given status and displays it.
     * @param status the status of the projects to be included in the report
     */
    public void generateProjectReportByStatus(ProjectStatus status) {
        try {
            List<Project> projects2 = Project.getProjectByStatus(status);
            for (Project project : projects2) {
                project.displayProject();
            }
            return; 
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Generates a report of the project assigned to the student with the given ID and displays it.
     * @param studentID the ID of the student whose project is to be included in the report
     */
    public void generateProjectReportByStudentID (String studentID) {
        try {
            Project project = Student.getStudentByID(studentID).getProject(); 
            if(project == null) {
                System.out.println("Student has not been assigned a project");
            }
            else {
                project.displayProject();
            }
            return; 
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the FYPCoordinator object with the given name.
     * @param coordinatorName the name of the coordinator to retrieve
     * @return the FYPCoordinator object with the given name, or null if no such coordinator exists
     */
    static public FYPCoordinator getCoordinatorByName(String coordinatorName) {
        try {
            for (FYPCoordinator coordinator : coordinators) {
                if (coordinator.getUserName().equals(coordinatorName))
                    return coordinator;
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    /**
    * Returns the FYPCoordinator object with the specified coordinatorID.
    *
    * @param coordinatorID the userID of the coordinator to be returned
    * @return the FYPCoordinator object with the specified coordinatorID, or null if not found
    */
    static public FYPCoordinator getCoordinatorByID(String coordinatorID) {
        try {
            for (FYPCoordinator coordinator : coordinators) {
                if (coordinator.getUserID().equals(coordinatorID))
                    return coordinator;
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    /**
    * Displays information for all FYP coordinators in the system.
    */
    static public void displayAllCoordinators() {
        try {
            if(coordinators.size() == 0){
                System.out.println("There are no FYP Coordinator");
            }
            else{
            for (FYPCoordinator coordinator: coordinators){
                System.out.println("----------------------------------");
                String name = coordinator.getUserName();
                String UserID = coordinator.getUserID();
                String userEmail = coordinator.getEmail();
                System.out.println("Name: " + name + ", UserID: " + UserID + ", Email: " + userEmail + "\n");
                System.out.println("----------------------------------");
            }
        }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to display the FYP Coordinators: " + e.getMessage());
        }
    }
    

    static public List<FYPCoordinator> getFYPCoordinators() {
        return coordinators;
    }


    /**
    * Adds the initial list of FYPCoordinator objects to the coordinators list.
    *
    * @param coordList a List of FYPCoordinator objects to be added to the coordinators list
    * @return 1 if the coordinators were successfully added, or 0 if an error occurred
    */
    static public int addInitialCoordinators(List<FYPCoordinator> coordList) {
        try {
            for (FYPCoordinator coordinator : coordList) {
                coordinators.add(coordinator);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }


    static public int assignFYPCoordinatorsList(List<FYPCoordinator> coordList) {
        try {
            coordinators = coordList;
            System.out.println("FYPCoord list assigned with length " + coordinators.size() + ".");
        } catch (Exception e) {
            System.out.println("An error occurred while trying to assign the FYP Coordinator list: " + e.getMessage());
            return 0;
        }

        return 1;
    }


    /**
    * Adds the given FYPCoordinator object to the list of coordinators.
    *
    * @param coordinator the FYPCoordinator object to add to the list
    * @return 1 if the coordinator was added successfully, or 0 if an error occurred
    */
    static public int addCoordinatorToList(FYPCoordinator coordinator) {
        try {
            coordinators.add(coordinator);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    /**
    * Removes the given FYPCoordinator object from the list of coordinators.
    *
    * @param coordinator the FYPCoordinator object to remove from the list
    * @return 1 if the coordinator was removed successfully, or 0 if an error occurred
    */
    static public int removeCoordinatorFromList(FYPCoordinator coordinator) {
        try {
            coordinators.remove(coordinator);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    /**
    * Returns true as this class represents an FYPCoordinator.
    *
    * @return true
    */
    @Override
    public boolean isFYPCoordinator() {
        return true;
    }

    /**
    * Attempts to log in an FYPCoordinator with the given userID and password.
    *
    * @param userID the userID of the FYPCoordinator to log in
    * @param password the password of the FYPCoordinator to log in
    * @return 1 if the login was successful, 0 if the login failed due to incorrect password, or -1 if the user does not exist
    */
    public static int loginFYPCoordinator (String userID, String password) {
        try {
            for (FYPCoordinator fypcoordinator : coordinators) {
                if (fypcoordinator.getUserID().equals(userID)) {
                    if (fypcoordinator.getPassword().equals(password)) {
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
