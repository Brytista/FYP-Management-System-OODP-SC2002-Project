package package1;
import java.util.List;
import java.util.ArrayList;

public class FYPCoordinator extends Supervisor {

    static private List<FYPCoordinator> coordinators = new ArrayList<>();

    public FYPCoordinator(String userID, String password, String name, String email) {
        super(userID, password, name, email);
        try {
            addCoordinatorToList(this);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public void viewAllProjects() {
        try {
            Project.displayAllProjects();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public void generateProjectReportBySupervisorName(String supervisorName) {
        try {
            for (Project project : Project.projectList) {
                if (project.supervisor.getUserName().equals(supervisorName))
                    project.displayProject();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public void generateProjectReportBySupervisorID(String supervisorID) {
        try {
            for (Project project : Project.projectList) {
                if (project.supervisor.getUserID().equals(supervisorID))
                    project.displayProject();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    
    public void generateProjectReportByStatus(ProjectStatus status) {
        try {
            for (Project project : Project.projectList) {
                if (project.projectStatus.equals(status))
                    project.displayProject();
            }
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
            for (FYPCoordinator coordinator : coordinators) {
                System.out.println(coordinator.getUserID() + " " + coordinator.getUserName());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
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
