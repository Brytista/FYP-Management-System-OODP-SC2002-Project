package package1;
import java.util.List;
import java.util.ArrayList;

public class FYPCoordinator extends Supervisor {

    static private List<FYPCoordinator> coordinators = new ArrayList<>();

    public FYPCoordinator(String userID, String password, String name, String email) {
        super(userID, password, name, email);
        addCoordinatorToList(this);
    }

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
    
    public void viewAllProjects() {
        try {
            Project.displayAllProjects();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
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

    static public int addCoordinatorToList(FYPCoordinator coordinator) {
        try {
            coordinators.add(coordinator);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    static public int removeCoordinatorFromList(FYPCoordinator coordinator) {
        try {
            coordinators.remove(coordinator);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    @Override
    public boolean isFYPCoordinator() {
        return true;
    }

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
