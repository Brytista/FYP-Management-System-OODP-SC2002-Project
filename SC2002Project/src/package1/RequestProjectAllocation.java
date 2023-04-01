package package1;

public class RequestProjectAllocation extends StudentRequest {
    
    public RequestProjectAllocation(Student sender, Supervisor recipient, Project project) {
        super(sender, recipient, project);
    }

    @Override
    public int approve() {
        sender.changeProject(project);
        project.changeProjectStatus(ProjectStatus.ALLOCATED);
        recipient.removePendingRequest(this);
        recipient.addRequestToHistory(this);
        recipient.addStudentManaged(sender);
        project.changeStudent(sender);
        if(recipient.capReached()){
            recipient.makeAllProjectsUnavailable(); // make all projects unavailable if the supervisor has reached his cap
            return 0;
        }
        return 1;
    }
    @Override
    public int reject() {
        project.changeProjectStatus(ProjectStatus.AVAILABLE);
        recipient.removePendingRequest(this);
        recipient.addRequestToHistory(this);
        return 1;
    }
    public int sendRequest(){
        //Send the request to the recipient
        if (sender == null || recipient == null) {
            return 0; // failure, sender or recipient is null
        }
        sender.addRequestToHistory(this);
        recipient.addPendingRequest(this);
        project.changeProjectStatus(ProjectStatus.RESERVED); // change the status of the project to reserved
        this.changeStatus(RequestStatus.PENDING); // change the status of the request to pending
        return 1; // success
    }
    
    public void displayRequestDescription(){
        System.out.println("Request to allocate project " + project.getProjectTitle() + " to " + sender.getUserName());
    }

}
