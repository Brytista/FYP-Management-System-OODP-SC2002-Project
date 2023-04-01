package package1;

public class FYPCoordinator extends Student {

    private List<FYPCoordinator> coordinators = new ArrayList<>();

    public void viewAllProjects() {
        for (Project project : projectList) {
            System.out.println(project.getProjectID() + " " + project.getProjectTitle());
        }
    }

    public void generateProjectReportBySupervisorName(String supervisorName) {
        for (Project project : projectList) {
            if (project.supervisor.name == supervisorName)
                project.displayProject();
        }
    }

    public void generateProjectReportBySupervisorID(String supervisorID) {
        for (Project project : projectList) {
            if (project.supervisor.userID == supervisorID)
                project.displayProject();
        }
    }

    public void generateProjectReportByStatus(ProjectStatus status) {
        for (Project project : projectList) {
            if (project.projectStatus == status)
                project.displayProject();
        }
    }

    public FYPCoordinator getCoordinatorByName(String coordinatorName) {
        for (FYPCoordinator coordinator : coordinators) {
            if (coordinator.name == coordinatorName)
                return coordinator;
        }
        return null;
    }

    public FYPCoordinator getCoordinatorByID(String coordinatorID) {
        for (FYPCoordinator coordinator : coordinators) {
            if (coordinator.userID == coordinatorID)
                return coordinator;
        }
    }

    public void displayAllCoordinators() {
        for (FYPCoordinator coordinator : coordinators) {
            System.out.println(coordinator.getUserID() + " " + coordinator.getUserName());
        }
    }

    public int addInitialCoordinators(List<FYPCoordinator> coordList) {

        try {
            for (Student student : coordList) {
                coordinators.add(student);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    public int addCoordinatorToList(FYPCoordinator coordinator) {

        try {
            coordinators.add(coordinator);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    public boolean isFYPCoordinator() {
        return true;
    }
}
