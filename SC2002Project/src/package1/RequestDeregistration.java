package package1;

public class RequestDeregistration extends StudentRequest {

    public RequestDeregistration(Student sender, Supervisor recipient, Project project) {
        super(sender, recipient, project);
    }

    @Override
    public int approve() {
        if(recipient.capReached()){
            recipient.makeAllProjectsAvailable(); // make all projects unavailable if the supervisor has reached his cap
        }
        project.changeProjectStatus(ProjectStatus.AVAILABLE);
        recipient.removePendingRequest(this);
        recipient.addRequestToHistory(this);
        sender.removeProject(); 
        recipient.removeStudentManaged(sender);
        project.removeStudent(); 
        this.changeStatus(RequestStatus.APPROVED);
        return 1; // success
    }
    @Override
    public int reject() {
        recipient.removePendingRequest(this);
        recipient.addRequestToHistory(this);
        this.changeStatus(RequestStatus.REJECTED);
        return 1; // success
    }
    public int sendRequest(){
        //Send the request to the recipient
        if (sender == null || recipient == null) {
            return 0; // failure, sender or recipient is null
        }
        sender.addRequestToHistory(this);
        recipient.addPendingRequest(this);
        this.changeStatus(RequestStatus.PENDING); // change the status of the request to pending
        return 1; // success
    }
    public void displayRequestDescription(){
        System.out.println("Request to deregister " + sender.getUserName() + " from project " + project.getProjectTitle());
    }
}
