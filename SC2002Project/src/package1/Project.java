package package1;

import java.util.List;
import java.util.ArrayList;
/**
 * A class that represents a project.
 * A project has a unique project ID, a supervisor, a title, a status, and a student.
 * A project can be in one of the following statuses: AVAILABLE, RESERVED, UNAVAILABLE, ALLOCATED.
 */
public class Project {
    /**
     * the ID of the project
     */
    private int projectID;
    /**
     * the supervisor who is in charge of this project
     */
    private Supervisor supervisor;
    /**
     * the project title
     */
    private String projectTitle;
    /**
     * the status of the project (AVAILABLE, RESERVED, UNAVAILABLE, ALLOCATED)
     */
    private ProjectStatus projectStatus;
    /**
     * the student that is allocated to this project (if any)
     */
    private Student student;
    /**
     * the list of all projects
     */
    private static List<Project> projectList = new ArrayList<Project>();

    /**
     * Constructs a new Project object with a unique project ID, supervisor, project title,
     * and sets the project status to AVAILABLE. Also adds the project to the project list and
     * adds the project to the supervisor's list of projects.
     *
     * @param supervisor the supervisor assigned to this project
     * @param projectTitle the title of the project
     */
    public Project(Supervisor supervisor, String projectTitle) {
        try {
            this.projectID = IndexGenerator.getIndex();
            this.supervisor = supervisor;
            this.projectTitle = projectTitle;
            this.projectStatus = ProjectStatus.AVAILABLE;
            addToProjectList(this); // add this project to the project list
            this.supervisor.addProject(this);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Returns the unique project ID of this project.
     *
     * @return the unique project ID of this project
     */
    public int getProjectID() {
        return this.projectID;
    }

    /**
     * Changes the project ID of this project to the specified value.
     * If the specified value is less than 0, the project ID is not changed.
     *
     * @param projectID the new project ID
     * @return 1 if the project ID was changed successfully, 0 otherwise
     */
    public int changeProjectID(int projectID) {
        if (projectID < 0)
            return 0;
        this.projectID = projectID;
        return 1;
    }

    /**
     * Returns the supervisor assigned to this project.
     *
     * @return the supervisor assigned to this project
     */
    public Supervisor getSupervisor() {
        return this.supervisor;
    }

    /**
     * Changes the supervisor assigned to this project to the specified supervisor.
     * If the specified supervisor is null, the supervisor is not changed.
     *
     * @param supervisor the new supervisor assigned to this project
     * @return 1 if the supervisor was changed successfully, 0 otherwise
     */
    public int changeSupervisor(Supervisor supervisor) {
        if (supervisor == null)
            return 0;
        this.supervisor = supervisor;
        return 1;
    }

    /**
     * Returns the title of this project.
     *
     * @return the title of this project
     */
    public String getProjectTitle() {
        return this.projectTitle;
    }
    
    /**
     * Changes the title of this project to the specified title.
     * If the specified title is null or an empty string, the project title is not changed.
     *
     * @param projectTitle the new title of this project
     * @return 1 if the project title was changed successfully, 0 otherwise
     */
    public int changeProjectTitle(String projectTitle) {
        try {
            if (projectTitle == null || projectTitle.length() == 0)
                return 0;
            this.projectTitle = projectTitle;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * Returns the current status of this project.
     *
     * @return the current status of this project
     */
    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }
    
    /**
     * Changes the status of this project to the specified status.
     * If the specified status is null, the project status is not changed.
     *
     * @param projectStatus the new status of this project
     * @return 1 if the project status was changed successfully, 0 otherwise
     */
    public int changeProjectStatus(ProjectStatus projectStatus) {
        try {
            if (projectStatus == null)
                return 0;
            this.projectStatus = projectStatus;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }




    /**
     * Checks if the project with the specified project ID belongs to the supervisor of this project.
     *
     * @param projectID the project ID to check
     * @return true if the project with the specified project ID belongs to the supervisor of this project, false otherwise
     */
    public boolean doesProjectBelongToSupervisor(int projectID){
        try{
            for(Project project : projectList){
                if(project.getProjectID() == projectID){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }


    /**
     * Returns the student assigned to this project.
     *
     * @return the student assigned to this project
     */
    public Student getStudent() {
        return this.student;
    }

    
    /**
     * Assigns the specified student to this project.
     * If the specified student is null, the student is not assigned to the project.
     *
     * @param student the student to assign to this project
     * @return 1 if the student was assigned to the project successfully, 0 otherwise
     */
    public int changeStudent(Student student) {
        try {
            if (student == null) {
                return 0;
            }
            this.student = student;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    
    /**
     * Removes the student assigned to this project.
     *
     * @return 1 if the student was removed from the project successfully
     */
    public int removeStudent() {
        this.student = null;
        return 1;
    }

    
    /**
     * Displays the details of this project on the console, including its ID, supervisor, title, status, and, if allocated, student.
     * If an error occurs while displaying the project, a message is printed on the console indicating that an error occurred.
     */
    public void displayProject() {
        try {
            System.out.println("");
            System.out.format("Project ID: %d\n", this.projectID);
            System.out.format("Supervisor Name: %s\n", this.supervisor.getUserName());
            System.out.format("Supervisor Email: %s\n", this.supervisor.getEmail());
            System.out.format("Project Title: %s\n", this.projectTitle);
            System.out.format("Project Status: %s\n", this.projectStatus);
            if (this.projectStatus == ProjectStatus.ALLOCATED) {
                if (this.student != null) {
                    System.out.format("Student Name: %s\n", this.student.getUserName());
                    System.out.format("Student Email: %s\n", this.student.getEmail());
                }
            }
            System.out.println("");
        } catch (Exception e) {
            System.out.println("An error has occurred while displaying the project: " + e.getMessage());
        }
    }

    /**
     * Displays information for all projects in the project list.
     * 
     * @throws Exception if an error occurs while displaying the projects.
     */
    public static void displayAllProjects() {
        try {
            for (Project project : projectList) {
                project.displayProject();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while displaying all projects: " + e.getMessage());
        }
    }
    
    /**
     * Displays information for all available projects in the project list.
     * 
     * @throws Exception if an error occurs while displaying the available projects.
     */
    public static void displayAvailableProjects() {
        try {
            for (Project project : projectList) {
                if (project.getProjectStatus() == ProjectStatus.AVAILABLE) {
                    project.displayProject();
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while displaying available projects: " + e.getMessage());
        }
    }


    /**
     * Retrieves a project from the project list by project ID.
     * 
     * @param projectID the ID of the project to retrieve.
     * @return the project with the specified ID, or null if the project was not found.
     * @throws Exception if an error occurs while retrieving the project.
     */
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


    /**
     * Retrieves a list of projects from the project list by project status.
     * 
     * @param projectStatus the status of the projects to retrieve.
     * @return a list of projects with the specified status, or null if no projects were found.
     * @throws Exception if an error occurs while retrieving the projects.
     */
    public static List<Project> getProjectByStatus(ProjectStatus projectStatus) {
        try {
            List<Project> returnList = new ArrayList<Project>();
            for (Project project : projectList) {
                if (project.getProjectStatus() == projectStatus) {
                    returnList.add(project);
                }
            }
            return returnList;
        } catch (Exception e) {
            System.out.println("An error occurred while getting project by status: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns a list of projects supervised by the given supervisor.
     *
     * @param supervisorID the ID of the supervisor to search for
     * @return a list of projects supervised by the given supervisor, or null if an error occurred
     */
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

    
    /**
    * Adds a list of initial projects to the project list.
    *
    * @param initialProjectList the list of initial projects to add
    * @return 1 if the addition was successful, 0 otherwise
    */
    public static int addInitialProjects(List<Project> initialProjectList) {
        if (initialProjectList.isEmpty())
            return 0;
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

    /** Assigns a retrieved list of projects to the current project list.
    * @param retrievedProjectList a list of projects retrieved from a data source
    * @return an integer indicating the success or failure of the operation. 1 means success, 0 means failure.
    */
    public static int assignProjectList(List<Project> retrievedProjectList) {
        try {
            projectList.clear();
            projectList = retrievedProjectList;
            System.out.println("Project list assigned with length " + projectList.size() + ".");
        } catch (Exception e) {
            System.out.println("An error occurred while trying to assign the project list: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    
    /**
     * Adds a project to the project list if it does not already exist.
     *
     * @param project the project to add
     * @return 1 if the addition was successful, 0 if the project already exists or an error occurred
     */

    public static int addToProjectList(Project project) {
        try {
            if (Project.getProjectByID(project.getProjectID()) != null)
                return 0; // project already exists
            if (projectList.add(project)) {
                return 1;
            }
        } catch (NullPointerException | UnsupportedOperationException e) {
            // log the exception message
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 0;
    }



    
    /**
     * Removes a project from the project list.
     *
     * @param project the project to remove
     * @return 1 if the removal was successful, 0 otherwise
     */
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

    
    /**
     * Modifies the status of all projects supervised by the given supervisor, except for projects with status "ALLOCATED".
     *
     * @param supervisorID the ID of the supervisor whose projects' status are to be modified
     * @param projectStatus the new status to set for the projects
     * @return 1 if the modification was successful, 0 otherwise
     */
    public static int massModifyProjectStatus(String supervisorID, ProjectStatus projectStatus) {
        try {
            for (Project project : projectList) {
                if (project.getSupervisor().getUserID().equals(supervisorID)) {
                    if (!(project.getProjectStatus() == ProjectStatus.ALLOCATED)) {
                        project.changeProjectStatus(projectStatus);
                    }
                }
            }
        } catch (Exception e) {
            // handle exception appropriately, e.g. logging, throwing a custom exception,
            // etc.
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    
    /**
     * Determines if the given object is an instance of the Project class.
     *
     * @param obj the object to check
     * @return true if the object is an instance of the Project class, false otherwise
     */
    public static boolean isProject(Object obj) {
        try {
            if (obj.getClass().equals(Project.class)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // handle exception appropriately, e.g. logging, throwing a custom exception,
            // etc.
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
    Returns the current project list.
    @return the current project list.
    */
    public static List<Project> getProjectList(){
        return projectList;
    }

}
