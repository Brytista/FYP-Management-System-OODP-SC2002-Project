package package1;
import java.util.Scanner;

public class RequestChangeSupervisor extends SupervisorRequest {
    
    public RequestChangeSupervisor(Supervisor sender, FYPCoordinator recipient, Project project,
            Supervisor replacementSupervisor) {
        super(sender, recipient, project, replacementSupervisor);
        this.sender = sender;
        this.recipient = recipient;
        this.project = project;
        this.replacementSupervisor = replacementSupervisor;
    }
    private String extraDescription = null; // extra description for the request

    @Override
    public int approve() {
        try {
            boolean senderCapReachedBefore = sender.capReached(); // check if the sender has reached his cap before
            Scanner sc; 
            if(sender == null || recipient == null || replacementSupervisor == null || project == null){
                System.out.println("Cannot approve request as sender, recipient, replacement supervisor or project is null");
                return 0; // failure, sender or recipient is null
            }

            if(project.getProjectStatus()==ProjectStatus.ALLOCATED){
            if(replacementSupervisor.capReached()){
                System.out.println("The replacement supervisor has reached cap number of student managed, you may not want to accept the request. You sure to continue? (0 for No, enter anything else for YES)");
                int answer; 
                try{
                    sc = new Scanner(System.in);
                    answer = sc.nextInt();
                    sc.nextLine(); // consume the newline
                    if(answer==0){return 0;}
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    return 0; 
                }
            }
            if(
                project.changeSupervisor(replacementSupervisor)==0||
                sender.removeStudentManaged(project.getStudent())==0||
                sender.removeProject(project)==0||
                Project.addToProjectList(project)==0|| // add the project back to the project list
                replacementSupervisor.addStudentManaged(project.getStudent())==0||
                replacementSupervisor.addProject(project)==0||
                recipient.removePendingRequest(this)==0||
                recipient.addRequestToHistory(this)==0||
                this.makeIsReviewed()==0
            ){ System.err.println("Some steps in the project allocation is not successful");
                return 0; 
            }
            if(replacementSupervisor.capReached()){
                if(replacementSupervisor.makeAllProjectsUnavailable()==0){return 0;}; // make all projects unavailable if the supervisor has reached his cap
            }
        }
            else{
                if(
                project.changeSupervisor(replacementSupervisor)==0||
                sender.removeProject(project)==0||
                Project.addToProjectList(project)==0|| // add the project back to the project list
                replacementSupervisor.addProject(project)==0||
                recipient.removePendingRequest(this)==0||
                recipient.addRequestToHistory(this)==0||
                this.makeIsReviewed()==0
            ){ 
                System.err.println("Some steps in the project allocation is not successful");
                return 0; 
            }
            if(replacementSupervisor.capReached()){
                if(replacementSupervisor.makeAllProjectsUnavailable()==0){return 0;}; // make all projects unavailable if the supervisor has reached his cap
            }
            }
            if(this.changeStatus(RequestStatus.APPROVED)==0){return 0;}
            if(senderCapReachedBefore && !sender.capReached()){
                if(sender.makeAllProjectsAvailable()==0){return 0;} // make all projects available if the supervisor who send the request reached his cap before
            }
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }

    @Override
    public int reject() {
        try {
            if(
                recipient.removePendingRequest(this)==0||
                recipient.addRequestToHistory(this)==0||
                this.changeStatus(RequestStatus.REJECTED)==0||
                this.makeIsReviewed()==0
            ){return 0;}
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }

    public int sendRequest(){
        try {
            //Send the request to the recipient
            if (sender == null || recipient == null) {
                return 0; // failure, sender or recipient is null
            }
            if(
                sender.addRequestToHistory(this)==0||
                recipient.addPendingRequest(this)==0||
                this.changeStatus(RequestStatus.PENDING)==0 // change the status of the request to pending
            ){return 0;}
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }

    public void displayRequestDescription(){
        try {
            if(project.getProjectStatus()==ProjectStatus.ALLOCATED&&replacementSupervisor.capReached()){
                extraDescription = "The replacement supervisor has reached cap number of student managed, you may not want to accept the request." + "He/She is currently managing "+ replacementSupervisor.getStudentsManaged().size() + " students."; 
            }
            else{
                extraDescription = "No extra notice"; 
            }
            System.out.println("Request to change supervisor for project " + project.getProjectTitle() + " from " + sender.getUserName() + " to " + replacementSupervisor.getUserName());
            System.out.println("EXTRA NOTICE: "+ extraDescription);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
}
