package package1;
import java.util.List;
import java.util.ArrayList;

public class Project implements ChangeProjectTitle {
    int projectID;
    Supervisor supervisor;
    String projectTitle;
    ProjectStatus projectStatus;
    Student student;
    static List<Project> projectList = new ArrayList<Project>();

    public Project(Supervisor supervisor, String projectTitle) {
        this.projectID = IndexGenerator.getIndex();
        this.supervisor = supervisor;
        this.projectTitle = projectTitle;
        this.projectStatus = ProjectStatus.AVAILABLE;
    }

    public int getProjectID() {
        return this.projectID;
    }

    public int changeProjectID(int projectID) {
        if(projectID<0) return 0;
        this.projectID = projectID;
        return 1;
    }

    public Supervisor getSupervisor() {
        return this.supervisor;
    }

    public int changeSupervisor(Supervisor supervisor) {
        if(supervisor==null) return 0;
        this.supervisor = supervisor;
        return 1;
    }

    public String getProjectTitle() {
        return this.projectTitle;
    }

    public int changeProjectTitle(String projectTitle) {
        if(projectTitle==null || projectTitle.length()==0) return 0;
        this.projectTitle = projectTitle;
        return 1;
    }

    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }

    public int changeProjectStatus(ProjectStatus projectStatus) {
        if(projectStatus==null) return 0;
        this.projectStatus = projectStatus;
        return 1;
    }

    public Student getStudent() {
        return this.student;
    }

    public int changeStudent(Student student) {
        if(student==null) return 0;
        this.student = student;
        return 1;
    }

    public int removeStudent() {
        this.student = null;
        return 1;
    }
    
    public void displayProject() {
        System.out.format("Project ID: %d\n", this.projectID);
        System.out.format("Supervisor Name: %s\n", this.supervisor.getUserName());
        System.out.format("Supervisor Email: %s\n", this.supervisor.getEmail());
        System.out.format("Project Title: %s\n", this.projectTitle);
        System.out.format("Project Status: %s\n", this.projectStatus);
        if(this.projectStatus.equals(ProjectStatus.ALLOCATED)) {
            System.out.format("Student Name: %s\n", this.student.getUserName());
            System.out.format("Student Email: %s\n", this.student.getEmail());
        }
    } 

    public static void displayAllProjects() {
        for (Project project : projectList) {
            project.displayProject();
        }
    }

    public static void displayAvailableProjects() {
        for (Project project : projectList) {
            if (project.getProjectStatus().equals(ProjectStatus.AVAILABLE)) {
                project.displayProject();
            }
        }
    }

    public static Project getProjectByID(int projectID) {
        for (Project project : projectList) {
            if (project.getProjectID() == projectID) {
                return project;
            }
        }
        return null;
    }

    public static List<Project> getProjectByStatus(ProjectStatus projectStatus) {
        List<Project> returnList = new ArrayList<Project>();
        for (Project project : projectList) {
            if (project.getProjectStatus().equals(projectStatus)) {
                returnList.add(project);
            }
        }
        return returnList;
    }

    public static List<Project> getProjectBySupervisor(String supervisorID) {
        List<Project> returnList = new ArrayList<Project>();
        for (Project project : projectList) {
            if (project.getSupervisor().getUserID().equals(supervisorID)) {
                returnList.add(project);
            }
        }
        return returnList;
    }

    public static int addInitialProjects(List<Project> initialProjectList) {
        if(initialProjectList.isEmpty()) return 0;
        if(projectList.addAll(initialProjectList)) return 1;
        return 0;
    }

    public static int addToProjectList(Project project) {
        if(projectList.add(project)) return 1;
        else return 0;
    }
    
    public static int removeFromProjectList(Project project) {
        if(projectList.remove(project)) return 1;
        else return 0;
    }

    public static int massModifyProjectStatus(String supervisorID, ProjectStatus projectStatus) {
        for (Project project : projectList) {
            if (project.getSupervisor().getUserID().equals(supervisorID)) {
                if (!project.getProjectStatus().equals(ProjectStatus.ALLOCATED)) {
                    project.changeProjectStatus(projectStatus);
                }
            }
        }
        return 1;
    }
    
    public static boolean isProject(Object obj) {
        if (obj.getClass().equals(Project.class)) {
            return true;
        } else {
            return false;
        }
    }
}
