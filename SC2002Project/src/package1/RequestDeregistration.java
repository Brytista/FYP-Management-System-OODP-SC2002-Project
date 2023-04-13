package package1;

/**
 * A class representing a request to deregister a student from a project.
 * Extends the StudentRequest class.
 */
public class RequestDeregistration extends StudentRequest {
    /**
     * Constructor for RequestDeregistration
     * @param sender
     * @param recipient
     * @param project
     */
    public RequestDeregistration(Student sender, Supervisor recipient, Project project) {
        super(sender, recipient, project);
    }
    
    /**
     * Approves the request to deregister a student from a project.
     * 
     * @return 1 if the request was successfully approved, 0 otherwise
     */
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
    
    /**
     * Rejects the request to deregister a student from a project.
     * 
     * @return 1 if the request was successfully rejected, 0 otherwise
     */
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
    
    /**
     * Sends the request to the recipient.
     * 
     * @return 1 if the request was successfully sent, 0 otherwise
     */
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
    
    /**
     * Displays or print the request description.
     */
    public void displayRequestDescription(){
        try {
            System.out.println("Request to deregister " + sender.getUserName() + " from project " + project.getProjectTitle());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    
}
