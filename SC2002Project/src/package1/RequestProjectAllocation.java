package package1;

public class RequestProjectAllocation extends StudentRequest {
    @Override
    public int approve() {
        sender.changeProject(project);
        project.changeStatus(ProjectStatus.ALLOCATED);
        recipient.removePendingRequest(this);
        recipient.addRequestHistory(this);
        recipient.addStudentManaged(sender);
        project.changeStudent(sender);
        if(recipient.capReached()){
            recipient.makeAllProjectsUnavailable(); // make all projects unavailable if the supervisor has reached his cap
        }
    }
    @Override
    public int reject() {
        project.changeStatus(ProjectStatus.AVAILABLE);
        recipient.removePendingRequest(this);
        recipient.addRequestHistory(this);
    }
    public int sendRequest(){
        //Send the request to the recipient
        if (sender == null || recipient == null) {
            return 0; // failure, sender or recipient is null
        }
        sender.addRequestHistory(this);
        recipient.addPendingRequest(this);
        project.changeStatus(ProjectStatus.RESERVED); // change the status of the project to reserved
        this.changeStatus(RequestStatus.PENDING); // change the status of the request to pending
        return 1; // success
    }

}
