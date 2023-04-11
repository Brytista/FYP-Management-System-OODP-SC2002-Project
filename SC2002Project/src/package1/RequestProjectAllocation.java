package package1;

public class RequestProjectAllocation extends StudentRequest {
    
    public RequestProjectAllocation(Student sender, Supervisor recipient, Project project) {
        super(sender, recipient, project);
    }

    @Override
public int approve() {
    try {
        Supervisor supervisor = project.getSupervisor(); // get the supervisor of the project
        if(sender.changeProject(project)==0 || project.changeProjectStatus(ProjectStatus.ALLOCATED) == 0 
        || recipient.removePendingRequest(this) == 0 || recipient.addRequestToHistory(this) == 0 
        || supervisor.addStudentManaged(sender) == 0 
        || project.changeStudent(sender) == 0 
        || this.changeStatus(RequestStatus.APPROVED) == 0 
        || this.makeIsReviewed()==0) {return 0;
        }
        sender.setRequestProject(false);
        if(supervisor.capReached()){
            supervisor.makeAllProjectsUnavailable(); // make all projects unavailable if the supervisor has reached his cap
            return 1;
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
        if(project.changeProjectStatus(ProjectStatus.AVAILABLE)==0||
        recipient.removePendingRequest(this)==0||
        recipient.addRequestToHistory(this)==0||
        this.changeStatus(RequestStatus.REJECTED)==0||
        this.makeIsReviewed()==0) {return 0;}

        sender.setRequestProject(false);
        
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
