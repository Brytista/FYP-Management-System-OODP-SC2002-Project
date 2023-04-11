package package1;

public class RequestChangeSupervisor extends SupervisorRequest {
    
    public RequestChangeSupervisor(Supervisor sender, FYPCoordinator recipient, Project project,
            Supervisor replacementSupervisor) {
        super(sender, recipient, project, replacementSupervisor);
        this.sender = sender;
        this.recipient = recipient;
        this.project = project;
        this.replacementSupervisor = replacementSupervisor;
    }
    @Override
    public int approve() {
        try {
            if(sender == null || recipient == null || replacementSupervisor == null || project == null){
                System.out.println("Cannot approve request as sender, recipient, replacement supervisor or project is null");
                return 0; // failure, sender or recipient is null
            }

            if(project.getProjectStatus()==ProjectStatus.ALLOCATED){
            if(replacementSupervisor.capReached()){
                System.out.println("Cannot approve request as replacement supervisor has reached cap");
                this.changeStatus(RequestStatus.REJECTED);
                return 0; 
            }
            if(sender.capReached()){
                if(sender.makeAllProjectsAvailable()==0){return 0;} // make all projects available if the supervisor who send the request reached his cap before
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
            }
            if(this.changeStatus(RequestStatus.APPROVED)==0){return 0;}
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
            System.out.println("Request to change supervisor for project " + project.getProjectTitle() + " from " + sender.getUserName() + " to " + replacementSupervisor.getUserName());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
}
