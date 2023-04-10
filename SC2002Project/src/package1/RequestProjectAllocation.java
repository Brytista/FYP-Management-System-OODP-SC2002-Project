package package1;

public class RequestProjectAllocation extends StudentRequest {
    
    public RequestProjectAllocation(Student sender, Supervisor recipient, Project project) {
        super(sender, recipient, project);
    }

    @Override
public int approve() {
    try {
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
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        return 0; // failure
    }
}

@Override
public int reject() {
    try {
        project.changeProjectStatus(ProjectStatus.AVAILABLE);
        recipient.removePendingRequest(this);
        recipient.addRequestToHistory(this);
        return 1;
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
        project.changeProjectStatus(ProjectStatus.RESERVED); // change the status of the project to reserved
        this.changeStatus(RequestStatus.PENDING); // change the status of the request to pending
        return 1; // success
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        return 0; // failure
    }
}

public void displayRequestDescription(){
    try {
        System.out.println("Request to allocate project " + project.getProjectTitle() + " to " + sender.getUserName());
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
    }
}


}
