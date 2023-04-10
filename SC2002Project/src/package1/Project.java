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
        try {
            this.projectID = IndexGenerator.getIndex();
            this.supervisor = supervisor;
            this.projectTitle = projectTitle;
            this.projectStatus = ProjectStatus.AVAILABLE;
            addToProjectList(this); // add this project to the project list
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
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
        try {
            if(projectTitle==null || projectTitle.length()==0) return 0;
            this.projectTitle = projectTitle;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }

    public int changeProjectStatus(ProjectStatus projectStatus) {
        try {
            if(projectStatus==null) return 0;
            this.projectStatus = projectStatus;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }


    public Student getStudent() {
        return this.student;
    }

    public int changeStudent(Student student) {
        try {
            if(student == null) {
                return 0;
            }
            this.student = student;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }
    

    public int removeStudent() {
        this.student = null;
        return 1;
    }
    
    public void displayProject() {
        try {
            System.out.format("Project ID: %d\n", this.projectID);
            System.out.format("Supervisor Name: %s\n", this.supervisor.getUserName());
            System.out.format("Supervisor Email: %s\n", this.supervisor.getEmail());
            System.out.format("Project Title: %s\n", this.projectTitle);
            System.out.format("Project Status: %s\n", this.projectStatus);
            if (this.projectStatus.equals(ProjectStatus.ALLOCATED)) {
                System.out.format("Student Name: %s\n", this.student.getUserName());
                System.out.format("Student Email: %s\n", this.student.getEmail());
            }
        } catch (Exception e) {
            System.out.println("An error has occurred while displaying the project: " + e.getMessage());
        }
    }
    
    public static void displayAllProjects() {
        try {
            for (Project project : projectList) {
                project.displayProject();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while displaying all projects: " + e.getMessage());
        }
    }
    
    public static void displayAvailableProjects() {
        try {
            for (Project project : projectList) {
                if (project.getProjectStatus().equals(ProjectStatus.AVAILABLE)) {
                    project.displayProject();
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while displaying available projects: " + e.getMessage());
        }
    }
    
    public static Project getProjectByID(int projectID) {
        try {
            for (Project project : projectList) {
                if (project.getProjectID() == projectID) {
                    return project;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred while getting project by ID: " + e.getMessage());
            return null;
        }
    }
    
    public static List<Project> getProjectByStatus(ProjectStatus projectStatus) {
        try {
            List<Project> returnList = new ArrayList<Project>();
            for (Project project : projectList) {
                if (project.getProjectStatus().equals(projectStatus)) {
                    returnList.add(project);
                }
            }
            return returnList;
        } catch (Exception e) {
            System.out.println("An error occurred while getting project by status: " + e.getMessage());
            return null;
        }
    }

    public static List<Project> getProjectBySupervisor(String supervisorID) {
        List<Project> returnList = new ArrayList<Project>();
        try {
            for (Project project : projectList) {
                if (project.getSupervisor().getUserID().equals(supervisorID)) {
                    returnList.add(project);
                }
            }
            return returnList;
        } catch (NullPointerException e) {
            // log the exception message
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    public static int addInitialProjects(List<Project> initialProjectList) {
        if (initialProjectList.isEmpty()) return 0;
        try {
            if (projectList.addAll(initialProjectList)) {
                return 1;
            }
        } catch (NullPointerException | UnsupportedOperationException e) {
            // log the exception message
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 0; // should never reach this point
        
    }
    
    public static int addToProjectList(Project project) {
        try {
            if (projectList.add(project)) {
                return 1;
            }
        } catch (NullPointerException | UnsupportedOperationException e) {
            // log the exception message
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 0;   }
    
    public static int removeFromProjectList(Project project) {
        try {
            if (projectList.remove(project)) {
                return 1;
            }
        } catch (NullPointerException | UnsupportedOperationException e) {
            // log the exception message
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 0; 
        
    }
    

    public static int massModifyProjectStatus(String supervisorID, ProjectStatus projectStatus) {
        try {
            for (Project project : projectList) {
                if (project.getSupervisor().getUserID().equals(supervisorID)) {
                    if (!project.getProjectStatus().equals(ProjectStatus.ALLOCATED)) {
                        project.changeProjectStatus(projectStatus);
                    }
                }
            }
        } catch (Exception e) {
            // handle exception appropriately, e.g. logging, throwing a custom exception, etc.
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }
    
    public static boolean isProject(Object obj) {
        try {
            if (obj.getClass().equals(Project.class)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // handle exception appropriately, e.g. logging, throwing a custom exception, etc.
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    
}
