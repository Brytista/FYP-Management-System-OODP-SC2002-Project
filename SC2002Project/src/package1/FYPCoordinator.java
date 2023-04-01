package package1;
import java.util.List;
import java.util.ArrayList;

public class FYPCoordinator extends Supervisor {

    static private List<FYPCoordinator> coordinators = new ArrayList<>();

    public FYPCoordinator(String userID, String password, String name, String email) {
        super(userID, password, name, email);
        coordinators.add(this);
    }

    public void viewAllProjects() {
        Project.displayAllProjects();
    }

    public void generateProjectReportBySupervisorName(String supervisorName) {
        for (Project project : Project.projectList) {
            if (project.supervisor.getUserName() == supervisorName)
                project.displayProject();
        }
    }

    public void generateProjectReportBySupervisorID(String supervisorID) {
        for (Project project : Project.projectList) {
            if (project.supervisor.getUserID() == supervisorID)
                project.displayProject();
        }
    }

    public void generateProjectReportByStatus(ProjectStatus status) {
        for (Project project : Project.projectList) {
            if (project.projectStatus.equals(status))
                project.displayProject();
        }
    }

    static public FYPCoordinator getCoordinatorByName(String coordinatorName) {
        for (FYPCoordinator coordinator : coordinators) {
            if (coordinator.getUserName().equals(coordinatorName))
                return coordinator;
        }
        return null;
    }

    static public FYPCoordinator getCoordinatorByID(String coordinatorID) {
        for (FYPCoordinator coordinator : coordinators) {
            if (coordinator.getUserID().equals(coordinatorID))
                return coordinator;
        }
        return null;
    }

    static public void displayAllCoordinators() {
        for (FYPCoordinator coordinator : coordinators) {
            System.out.println(coordinator.getUserID() + " " + coordinator.getUserName());
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

    @Override
    public boolean isFYPCoordinator() {
        return true;
    }
}
