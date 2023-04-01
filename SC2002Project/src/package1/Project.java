package package1;
import java.util.List;
import java.util.ArrayList;

public class Project {
    int projectID;
    Supervisor supervisor;
    String projectTitle;
    Status projectStatus;
    Student student;
    static List<Project> projectList = new ArrayList<Project>();

    public Project(Supervisor supervisor, String projectTitle) {
        this.projectID = IndexGenerator.getIndex();
        this.supervisor = supervisor;
        this.projectTitle = projectTitle;
        this.projectStatus = Status.AVAILABLE;
        projectList.add(this);
    }

    public int getProjectID() {
        return this.projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public Supervisor getSupervisor() {
        return this.supervisor;
    }

    public void changeSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String changeProjectTitle() {
        return this.projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public Status getProjectStatus() {
        return this.projectStatus;
    }

    public void changeProjectStatus(Status projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Student getStudent() {
        return this.student;
    }

    public void changeStudent(Student student) {
        this.student = student;
    }
    
    public void displayProject() {
        System.out.format("Project ID: %d\n", this.projectID);
        System.out.format("Supervisor Name: %s\n", this.supervisor.getUserName());
        System.out.format("Supervisor Email: %s\n", this.supervisor.getUserEmail());
        System.out.format("Project Title: %s\n", this.projectTitle);
        System.out.format("Project Status: %s\n", this.projectStatus);
        if(this.projectStatus==Status.ALLOCATED) {
            System.out.format("Student Name: %s\n", this.student.getUserName());
            System.out.format("Student Email: %s\n", this.student.getUserEmail());
        }
    } 

    public static void displayAllProjects() {
        for (Project project : projectList) {
            project.displayProject();
        }
    }

    public static void displayAvailableProjects() {
        for (Project project : projectList) {
            if (project.getProjectStatus() == Status.AVAILABLE) {
                project.displayProject();
            }
        }
    }

    public static List<Project> getProjectByID(int projectID) {
        List<Project> returnList = new ArrayList<Project>();
        for (Project project : projectList) {
            if (project.getProjectID() == projectID) {
                returnList.add(project);
            }
        }
        return returnList;
    }

    public static List<Project> getProjectByStatus(Status projectStatus) {
        List<Project> returnList = new ArrayList<Project>();
        for (Project project : projectList) {
            if (project.getProjectStatus() == projectStatus) {
                returnList.add(project);
            }
        }
        return returnList;
    }

    public static List<Project> getProjectBySupervisor(int supervisorID) {
        List<Project> returnList = new ArrayList<Project>();
        for (Project project : projectList) {
            if (project.getSupervisor().getUserID() == supervisorID) {
                returnList.add(project);
            }
        }
        return returnList;
    }

    public static int addInitialProjects(List<Project> initialProjectList) {
        projectList.addAll(initialProjectList);
        return 1;
    }

    public static int addToProjectList(Project project) {
        projectList.add(project);
        return 1;
    }
    
    public static int removeFromProjectList(Project project) {
        projectList.remove(project);
        return 1;
    }

    public static int massModifyProjectStatus(int supervisorID, Status projectStatus) {
        for (Project project : projectList) {
            if (project.getSupervisor().getUserID() == supervisorID) {
                if (project.getProjectStatus() != Status.ALLOCATED) {
                    project.changeProjectStatus(projectStatus);
                }
            }
        }
        return 1;
    }
    
    public boolean isProject(Object obj) {
        if (obj instanceof Project) {
            return true;
        } else {
            return false;
        }
    }
}
