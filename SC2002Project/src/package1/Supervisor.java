package package1;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Supervisor extends User {
    int numberOfProjectManaged = 0;
    List<Project> projectList = new ArrayList<Project>();
    List<Request> pendingRequest = new ArrayList<Request>();
    private static List<String> availableRequests = new ArrayList<>(Arrays.asList("1. Change Supervisor")); 
    private SupervisorRequest requestType;
    private static List<Supervisor> allSupervisor = new ArrayList<Supervisor>() ;
    List<Student> studentManaged = new ArrayList<Student>();
    Scanner sc = new Scanner(System.in);


    public Supervisor(String userID, String password, String name, String email){
        super(userID, password, name, email);
        if(this instanceof FYPCoordinator){
            //do nothing
        }
        else{
            addToSupervisorList(this); 
        }
    }

    public static Supervisor createSupervisor(String userID, String password, String name, String email){
        try {
            if(Supervisor.getSupervisorByID(userID)==null) {
                Supervisor newSupervisor = new Supervisor(userID, password, name, email);
                return newSupervisor;
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

    public int createProject(String projectTitle){
        try {
            Project newProject = new Project(this, projectTitle);
            return 1;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }
    

    public void displayProjects() {
        try {
            if(projectList.isEmpty()){
                System.out.println("You have no project yet");
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
    

    public static void displayAvailableRequests() {
        try {
            for (String request : availableRequests) {
                System.out.println(request);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    

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
    
    

    public FYPCoordinator selectRecipient(String recipientID){
        try {
            return FYPCoordinator.getCoordinatorByID(recipientID);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to retrieve the FYP Coordinator with ID " + recipientID + ": " + e.getMessage());
            return null;
        }
    }
    
    public Supervisor selectSupervisor(String replacementSupervisorID){
        try {
            return Supervisor.getSupervisorByID(replacementSupervisorID);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to retrieve the supervisor with ID " + replacementSupervisorID + ": " + e.getMessage());
            return null;
        }
    }
    
    
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


    public int makeAllProjectsAvailable(){
        try {
            return Project.massModifyProjectStatus(this.getUserID(), ProjectStatus.AVAILABLE);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to make all projects available: " + e.getMessage());
            return 0;
        }
    }
    
    public int makeAllProjectsUnavailable(){
        try {
            return Project.massModifyProjectStatus(this.getUserID(), ProjectStatus.UNAVAILABLE);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to make all projects unavailable: " + e.getMessage());
            return 0;
        }
    }
    
    public List<Student> getStudentsManaged(){
        try {
            List<Student> arr = new ArrayList<>();
            for(Project project: projectList){
                if(project.getProjectStatus() == ProjectStatus.ALLOCATED){
                    arr.add(project.getStudent());
                }
            }
            return arr;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to get the managed students: " + e.getMessage());
            return null;
        }
    }
    


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
    
    private Project selectProject(int projectID) {
        try {
            return Project.getProjectByID(projectID);
        } catch (Exception e) {
            System.out.println("An error occurred while trying to select the project with ID " + projectID + ": " + e.getMessage());
            return null;
        }
    }
    



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
    
    public boolean doesProjectBelongToSupervisor(Project project){
        try {
            for(Project projects: projectList){
                if(project.equals(projects)){
    
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to determine if the project belongs to the supervisor: " + e.getMessage());
            return false;
        }
    }
    
    public int addStudentManaged(Student student) {
        try {
            if (capReached()){
                return 0;}
            numberOfProjectManaged++;
            studentManaged.add(student);
            return 1;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to add a managed student: " + e.getMessage());
            return 0;
        }
    }
    
    @Override
    public boolean isSupervisor() {
        return true;
    }
    
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
    
    public int removeProject(Project project){
        try {
            for(Project projects: projectList){
                if(project.equals(projects)){
                    projectList.remove(projects);
                    Project.removeFromProjectList(projects);
                    return 1;
                }
            }
            return 0;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to remove a project: " + e.getMessage());
            return 0;
        }
    }
    

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

   //NEW METHOD
    public boolean doSupervisorHaveProject(){
        try{
            if(projectList.isEmpty()) return false; //if the project list is empty, return false
            else return true; //if the project list is not empty, return true
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

}