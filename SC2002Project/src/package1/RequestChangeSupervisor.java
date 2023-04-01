package package1;

public class RequestChangeSupervisor extends SupervisorRequest {
    @Override
    public int approve() {
        if(sender == null || recipient == null || replacementSupervisor == null || project == null || project.getStudent() == null){
            return 0; // failure, sender or recipient is null
        }
       if(replacementSupervisor.capReached()){
            System.out.println("Cannot approve request as replacement supervisor has reached cap");
            this.changeStatus(status.REJECTED);
            return 0; 
       }
       if(sender.capReached()){
        sender.makeAllProjectsAvailable(); // make all projects unavailable if the supervisor has reached his cap
    }
        project.changeSupervisor(replacementSupervisor);
        sender.removeStudentManaged(project.getStudent());
        sender.removeProject(project);
        replacementSupervisor.addStudentManaged(project.getStudent()); //This assumed that addStudentManaged will add the variable numberOfProjectManaged and add the student to the list of students managed
        replacementSupervisor.addProject(project); //This assumes that add project would add the project directly to project list
        recipient.removePendingRequest(this); 
        recipient.addRequestHistory(this);
        if(replacementSupervisor.capReached()){
            replacementSupervisor.makeAllProjectsUnavailable(); // make all projects unavailable if the supervisor has reached his cap
        }
        this.changeStatus(status.APPROVED);
        return 1; // success
    }
    @Override
    public int reject() {
        recipient.removePendingRequest(this);
        recipient.addRequestHistory(this);
        this.changeStatus(RequestStatus.REJECTED);
        return 1; // success
    }
    public int sendRequest(){
        //Send the request to the recipient
        if (sender == null || recipient == null) {
            return 0; // failure, sender or recipient is null
        }
        sender.addRequestHistory(this);
        recipient.addPendingRequest(this);
        this.changeStatus(RequestStatus.PENDING); // change the status of the request to pending
        return 1; // success
    }
}
