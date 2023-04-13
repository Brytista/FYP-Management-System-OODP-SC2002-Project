package package1;
/**
 * This class represents the "Change Project Title" request type that can be made by student to Supervisor
 * extends from StudentRequest class
 */
public class RequestChangeProjectTitle extends StudentRequestWithString {
    /**
     * Constructs a new request to change the project title of a project, with a sender, recipient, project, and new project title. 
     *
     * @param sender the student who is sending the request
     * @param recipient the supervisor who is receiving the request
     * @param project the project for which the title is being changed
     * @param newProjectTitle the new title for the project
     */
    public RequestChangeProjectTitle(Student sender, Supervisor recipient, Project project, String newProjectTitle) {
        super(sender, recipient, project, newProjectTitle);
    }

    /**
     * Approves the request by changing the project title of the project and updating the status of the request to 'APPROVED'. 
     * Removes the request from the recipient's pending requests and adds it to the recipient's request history. 
     * Marks the request as reviewed.
     *
     * @return 0 if any of the actions fails, 1 if all actions are successful
     */
    public int approve() {
        try {
            if (this.project == null || this.newProjectTitle == null || this.recipient == null || this.sender == null || this.status == null) {
                return 0;
            }
            int success = project.changeProjectTitle(this.newProjectTitle);
            if (success == 0) {
                return 0; // failure, project title is not changed
            }
            if(
                recipient.removePendingRequest(this) == 0 ||
                recipient.addRequestToHistory(this) == 0 ||
                this.changeStatus(RequestStatus.APPROVED) == 0 ||
                this.makeIsReviewed() == 0) {return 0;}
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }
    
    /**
     * Rejects the request by removing it from the recipient's pending requests and adding it to the recipient's request history. 
     * Changes the status of the request to 'REJECTED' and marks it as reviewed.
     *
     * @return 0 if any of the actions fails, 1 if all actions are successful
     */
    public int reject() {
        try {
            if(this.project == null|| this.newProjectTitle == null|| this.recipient == null||this.sender == null||this.status == null){
                return 0;
            }
            if(
                recipient.removePendingRequest(this) == 0 ||
                recipient.addRequestToHistory(this) == 0 ||
                this.changeStatus(RequestStatus.REJECTED) == 0 ||
                this.makeIsReviewed() == 0
            ){return 0;}
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Sends the request to the recipient by adding it to the sender's history and the recipient's pending requests.
     *
     * @return 1 if the request is successfully sent; otherwise, 0.
     */
    public int sendRequest() {
        try {
            // Send the request to the recipient
            if (sender == null || recipient == null) {
                return 0; // Failure, sender or recipient is null.
            }
            if (
                sender.addRequestToHistory(this) == 0 ||
                recipient.addPendingRequest(this) == 0 ||
                this.changeStatus(RequestStatus.PENDING) == 0
            ) {
                return 0; // Failure, request is not sent (request is not added to sender's history, recipient's pending requests, or request status is not changed to pending).
            }
            return 1; // Success.
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // Failure.
        }
    }

    /**
     * Displays the description of the request to change project title.
     */
    public void displayRequestDescription() {
        System.out.println("Request to change project title to " + this.newProjectTitle);
    }
}
