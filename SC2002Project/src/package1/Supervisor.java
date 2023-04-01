package package1;
import java.util.*;
import java.util.ArrayList;

public class Supervisor extends User{
    int numberOfProjectManaged = 0;
    Project[] projectList;
    Request[] pendingRequest;
    SupervisorRequest requestType;
    static Supervisor[] allSupervisor;
    Student[] studentManaged;
    Scanner sc = new Scanner(System.in);

    public int createProject(Supervisor supervisor, String projectTitle){
        
        System.out.println("Enter project title: ");
        String projectName = sc.nextLine();
        Project newProject = new Project(this, projectTitle);
        projectList.add(newProject);
    }

    public void displayProjects(){
        for (int i = 0; i < projectList.length(); i++){
            projectList[i].displayProjects();
        }
    }

    public int modifyProjectTitle(int projectID, String newTitle){
        Project project = new Project;
        project = project.getProject(projectID);
        if(doesProjectBelongToSupervisor(project)){
            project.changeProjectTitle(newTitle);
            return 1;
        }
        return 0;
    }

    public boolean capReached(){
        if (numberOfProjectManaged >= 2){
            return true;
        }
        else return false;
    }

    public void viewAllPendingRequest(){
        if(pendingRequest.length() == 0){
            System.out.println("There are no pending Request");
        }
        DisplayRequest displayRequest = new DisplayAll();
        displayRequest.displayPendingRequest(pendingRequest);
    }

    public int makeRequest(String recipientID, int projectID, String replacementSupervisorID){
        Project project = selectProject(projectID);
        if(project == null) return 0;
        Supervisor replacementSupervisor = selectSupervisor(replacementSupervisorID);
        if(replacementSupervisorID == null) return 0;
        FYPCoordinator FYPCoor = selectRecipient(recipientID);
        if(FYPCoor == null) return 0;
        Request newRequest = new RequestChangeSupervisor(this, FYPCoor, project, replacementSupervisor);
        return 1;
    }

    public void displayAllSupervisor(){
        for (int i = 0; i < allSupervisor.length(); i++){
            String name = allSupervisor[i].getUserName();
            String UserID = allSupervisor[i].getUserID();
            String userEmail = allSupervisor[i].getEmail();
            System.out.println("Name: " + name + ", UserID: " + userID + "Email: " + userEmail + "\n");
    }

    public int makeAllProjectsAvailable(){
        return massModifyProjectStatus(userID, AVAILABLE);
    }

    public int makeAllProjectsuNAvailable(){
        return massModifyProjectStatus(userID, UNAVAILABLE);
    }

    public Student[] getStudentsManaged(){
        Student[] arr = {};
        for(int i = 0; i < projectList.length(); i++){
            if(projectList[i].getStatus == ALLOCATED){
                arr.add(projectList[i].getStudent())
            }
        }
        return arr;
    }

    public supervisor(String ID, String Password, String Name, String Email){
        userID = ID;
        password = Password;
        name = Name;
        emailAddress = Email;
    }

    public static Supervisor getSupervisorByName(String name){
        for(int i = 0; i < allSupervisor.length();i++){
            if(allSupervisor[i].getUserName() == name){
                return allSupervisor[i];
            }
        }
        return null;
    }

    public static Supervisor getSupervisorByID(String UserID){
        for(int i = 0; i < allSupervisor.length();i++){
            if(allSupervisor[i].getUserID() == UserID){
                return allSupervisor[i];
            }
        }
        return null;
    }

    public static int addInitialSupervisor(Supervisor[] supervisorLists){
        if(supervisorLists == null) return 0;
        allSupervisor = supervisorLists.clone();
        return 1;
    }

    public static int addToSupervisorList(Supervisor supervisor){
        if(supervisor == null) return 0;
        allSupervisor.add(supervisor);
        return 1;
    }

    public static int removeFromSupervisorList(Supervisor supervisor){
        boolean found = false;
        for(int i < 0; i < allSupervisor.length();i++){
            if(allSupervisor[i].equals(supervisor)){
                found = true;
            }
        }
        if(!found) return 0;
        allSupervisor.remove(supervisor);
        return 1;
    }

    private Project selectProject(int ProjectID){
        for(int i = 0; i < projectList.length();i++){
            if(projectList[i].getProjectID == ProjectID){
                return projectList[i];
            }
        }
        return null;
    }

    private Supervisor selectSupervior(String replacementSupervisorID){
        for(int i = 0; i < allSupervisor.length();i++){
            if(allSupervisor[i].getUserID == replacementSupervisorID){
                return allSupervisor[i];
            }
        }
        return null;
    }

    private FYPCoordinator selectProject(String RecipientID){
        for(int i = 0; i < allSupervisor.length();i++){
            if(allSupervisor[i].getProjectID == ProjectID && allSupervisor[i].isFYPCoordinator){
                return allSupervisor[i];
            }
        }
        return null;
    }

    public int addPendingRequest(Request request){
        if(request == null) return 0;
        pendingRequest.add(request);
    }

    public boolean doesProjectBelongToSupervisor(Project project){
        for(int i = 0; i < projectList.length(); i++){
            if(project.equals(projectList[i])){
                return true;
            }
        }
        return false;
    }

    public int addStudentManaged(Student student){
        if(capReached()) return 0;
        numberOfProjectManaged++;
        studentManaged.add(student);
        return 1;
    }

    public boolean isSupervisor(){
        return true;
    }

    public int removeStudentManaged(Student student){
        if(numberOfProjectManaged == 0) return 0;
        numberOfProjectManaged--;
        studentManaged.remove(student);
        return 1;
    } 
}
