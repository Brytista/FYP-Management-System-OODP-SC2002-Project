package package1;

public class RequestDeregistration extends StudentRequest {

    public RequestDeregistration(Student sender, Supervisor recipient, Project project) {
        super(sender, recipient, project);
    }
    @Override
    public int approve() {
        try {
            Supervisor supervisor = project.getSupervisor();
            if(supervisor.capReached()){
                supervisor.makeAllProjectsAvailable(); // make all projects unavailable if the supervisor has reached his cap
            }
            if(
                project.changeProjectStatus(ProjectStatus.AVAILABLE) ==0 ||
                recipient.removePendingRequest(this) == 0 ||
                recipient.addRequestToHistory(this) == 0 ||
                sender.removeProject() == 0 ||
                sender.setIsDeregistered(true) == 0 || // set the student to be deregistered
                supervisor.removeStudentManaged(sender) == 0 ||
                project.removeStudent() == 0 ||
                this.changeStatus(RequestStatus.APPROVED) == 0 ||
                this.makeIsReviewed() == 0) {return 0;} 
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
                recipient.removePendingRequest(this) == 0 ||
                recipient.addRequestToHistory(this) == 0 ||
                this.changeStatus(RequestStatus.REJECTED) == 0 ||
                this.makeIsReviewed() == 0
            ){return 0;}
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }
    
    public int sendRequest(){
        try {
            //Send the request to the recipient
            if (sender == null || recipient == null) {
                return 0; // failure, sender or recipient is null
            }
            sender.addRequestToHistory(this);
            recipient.addPendingRequest(this);
            this.changeStatus(RequestStatus.PENDING); // change the status of the request to pending
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }
    
    public void displayRequestDescription(){
        try {
            System.out.println("Request to deregister " + sender.getUserName() + " from project " + project.getProjectTitle());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    
}
