package package1;
import java.util.Scanner;

/**
 * Represents a request to change the supervisor of a project.
 * Extends the SupervisorRequest class.
 */
public class RequestProjectAllocation extends StudentRequest {
    /**
    * This variable represents the extra description of a project, which is an optional field and can be null.
    */
    private String extraDescription = null;
    /**
     * This variable represents the supervisor of a project.
    */
    private Supervisor supervisor; // get the supervisor of the project

    /**
     * Constructor for RequestProjectAllocation
     * @param sender
     * @param recipient
     * @param project
     */
    public RequestProjectAllocation(Student sender, Supervisor recipient, Project project) {
        super(sender, recipient, project);
    }

    /**
     * Approves the request to change the supervisor of a project.
     * 
     * @return 1 if the request was successfully approved, 0 otherwise
     */
    @Override
    public int approve() {
    try {// get the supervisor of the project   
        Scanner sc;
        supervisor = project.getSupervisor();
        if(supervisor.capReached()){
            System.out.println("The replacement supervisor has reached cap number of student managed. Press anything to reject the request.");
            int answer; 
            try{
                sc = new Scanner(System.in);
                answer = sc.nextInt();
                sc.nextLine(); // consume the newline
                this.reject();
                return 0; 
            } catch (Exception e) {
                this.reject();
                System.out.println("Request rejected.");
                return 0; 
            }
        }


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

    /**
     * Rejects the request to change the supervisor of a project.
     *
     * @return 1 if the request was successfully rejected, 0 otherwise
     */
    @Override
    public int reject() {
    try {
        if(project.changeProjectStatus(ProjectStatus.AVAILABLE)==0||
        recipient.removePendingRequest(this)==0||
        recipient.addRequestToHistory(this)==0||
        this.changeStatus(RequestStatus.REJECTED)==0||
        this.makeIsReviewed()==0) {return 0;}

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
        project.changeProjectStatus(ProjectStatus.RESERVED); // change the status of the project to reserved
        this.changeStatus(RequestStatus.PENDING); // change the status of the request to pending
        return 1; // success
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        return 0; // failure
    }
}

/**
 * Displays the request description.
 */
public void displayRequestDescription(){
    try {
        supervisor = project.getSupervisor();
        if(supervisor.capReached()){
            extraDescription = "The replacement supervisor has reached cap number of student managed, you cannot reject it" + " He/She is currently managing "+ supervisor.getStudentsManaged().size() + " students."; 
        }
        else{
            extraDescription = "No extra notice"; 
        }
        System.out.println("Request to allocate project " + project.getProjectTitle() + " to " + sender.getUserName());
        System.out.println("EXTRA NOTICE: "+ extraDescription);
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
    }
}


}
