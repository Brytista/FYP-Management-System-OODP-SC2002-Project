package package1;

public class RequestChangeProjectTitle extends StudentRequestWithString {
    
    public RequestChangeProjectTitle(Student sender, Supervisor recipient, Project project, String newProjectTitle) {
        super(sender, recipient, project, newProjectTitle);
    }

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
    
    public int sendRequest(){
        try {
            //Send the request to the recipient
            if (sender == null || recipient == null) {
                return 0; // failure, sender or recipient is null
            }
            if(
                sender.addRequestToHistory(this)==0 ||
                recipient.addPendingRequest(this)==0 ||
                this.changeStatus(RequestStatus.PENDING)==0
            ){return 0;}// failure, request is not sent (request is not added to sender's history, recipient's pending requests, or request status is not changed to pending
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: failure, request is not sent (request is not added to sender's history, recipient's pending requests, or request status is not changed to pending" + e.getMessage());
            return 0;
        }
    }
    

    public void displayRequestDescription(){
        System.out.println("Request to change project title " + "to " + this.newProjectTitle);
    }
}
