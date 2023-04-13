package package1;

/**
 * The abstract class representing a student request.
 */
public abstract class StudentRequest extends Request {
    /**
     * the user who sent the request
     */
    protected Student sender;

    /**
     * Creates a new instance of the StudentRequest class.
     * 
     * @param sender the student sending the request
     * @param recipient the supervisor receiving the request
     * @param project the project related to the request
     */
    public StudentRequest(Student sender, Supervisor recipient, Project project) {
        super(sender, recipient, project);
    }
    
    /**
     * Sends the request to the recipient.
     * 
     * @return 1 if the request was successfully sent, 0 if there was an error
     */
    public int sendRequest() {
        try {
            // Send the request to the recipient
            if (sender == null || recipient == null) {
                return 0; // failure, sender or recipient is null
            }
            if(sender.addRequestToHistory(this)==0||recipient.addPendingRequest(this)==0) {return 0;}
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }
    
    /**
     * Creates a new instance of the StudentRequest class.
     * 
     * @param sender the student sending the request
     * @param recipient the supervisor receiving the request
     * @param project the project related to the request
     * @return 1 if the request was successfully created, 0 if there was an error
     */
    public int create(Student sender, Supervisor recipient, Project project){
        try {
            if(sender == null || recipient == null || project == null){
                return 0;
            }
            this.sender = sender;
            this.recipient = recipient;
            this.project = project;
            this.isReviewed = false;
            this.status = RequestStatus.PENDING;
            return 1;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }
    
    /**
     * Changes the sender of the request.
     * 
     * @param sender the new sender of the request
     * @return 1 if the sender was successfully changed, 0 if there was an error
     */
    public int changeSender(Student sender){
        try {
            if (!(sender.isStudent())) {
                return 0; // failure, not an instance of Sender or its subclasses
            }
            this.sender = sender;
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }
}

