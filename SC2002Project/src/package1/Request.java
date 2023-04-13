package package1;


/**
 * The Request class is an abstract class representing requests sent between users,
 * specifically between a user (student) and a supervisor regarding projects. 
 * It holds information about the sender, recipient, review status, project, and request status.
 *
 * This class provides methods to modify and display various attributes of a request,
 * such as status, project, sender, and recipient. It also includes methods to approve or
 * reject a request, which should be implemented by concrete subclasses.
 *
 * The sendRequest method can be used to send the request to the recipient and add it
 * to the sender's and recipient's request lists.
 */
public abstract class Request {
    /**
     * The user who sent the request.
     */
    protected User sender;

    /**
     * The user who will receive the request.
     */
    protected Supervisor recipient;

    /**
     * The status of the request.
     */
    protected boolean isReviewed;

    /**
     * The project the request is about.
     */
    protected Project project;

    /**
     * The status of the request.
     */
    protected RequestStatus status;
    


    /**
     * Constructs a new request with a sender, recipient, and project.
     * 
     * @param sender the user who is sending the request
     * @param recipient the user who is receiving the request
     * @param project the project the request is about
     */
    public Request(User sender, Supervisor recipient, Project project) {
        this.sender = sender;
        this.recipient = recipient;
        this.isReviewed = false; // default value
        this.project = project;
        this.status = RequestStatus.PENDING; // default value
    }

    /**
     * Changes the status of the request.
     * 
     * @param status the new status of the request
     * @return 1 if the status was successfully changed, 0 otherwise
     */
    public int changeStatus(RequestStatus status) {
        if (status == null) {
            return 0; // failure, status is null
        }
        this.status = status;
        return 1; // success
    }

    /**
     * Displays the status of the request.
     */
    public void displayStatus() {
        System.out.println("Status:" + status);
    }

    /**
     * Changes the project associated with the request.
     * 
     * @param project the new project to be associated with the request
     * @return 1 if the project was successfully changed, 0 otherwise
     */
    public int changeProject(Project project) {
        if (Project.isProject(project)) {
            this.project = project;
            return 1; // success
        }
        return 0; // failure
    }

    /**
     * Displays the project title associated with the request.
     */
    public void displayProject() {
        System.out.println("Project:" + project.getProjectTitle());
    }

    /**
     * Prints the request information including sender, recipient, and review status.
     */
    public void printRequest() {
        System.out.println("Request from " + sender.getUserID() + " to " + recipient.getUserID() + " with status "
                + this.isReviewed);
    }

    /**
     * Approves the request.
     * 
     * @return 1 if the request was successfully approved, 0 otherwise
     */
    public int approve() {
        return 0;
    } // by Concrete Class

    /**
     * Rejects the request.
     * 
     * @return 1 if the request was successfully rejected, 0 otherwise
     */
    public int reject() {
        return 0;
    } // by Concrete Class

    /**
     * Displays the review status of the request.
     */
    public void displayIsReviewed() {
        System.out.println("IsReviewed:" + isReviewed);
    }

    /**
     * Marks the request as reviewed.
     * 
     * @return 1 if the request was successfully marked as reviewed, 0 otherwise
     */
    public int makeIsReviewed() {
        this.isReviewed = true;
        return 1; // success
    }

    /**
     * Displays the user ID of the sender of the request.
     */
    public void displaySender() {
        System.out.println("Sender:" + sender.getUserID());
    }

    /**
     * Changes the sender of the request.
     * 
     * @param sender the new sender of the request
     * @return 1 if the sender was successfully changed, 0 otherwise
     */
    public int changeSender(User sender) {
        try {
            if (!(sender instanceof User)) {
                return 0; // failure, not an instance of Sender or its subclasses
            }
            this.sender = sender;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
        return 1; // success
    }

    /**
     * Displays the user ID of the recipient of the request.
     */
    public void displayRecipient() {
        try {
            System.out.println("Recipient: " + this.recipient.getUserID());
        } catch (NullPointerException e) {
            System.out.println("Error: recipient is null.");
        }
    }

    /**
     * Changes the recipient of the request.
     * 
     * @param recipient the new recipient of the request
     * @return 1 if the recipient was successfully changed, 0 otherwise
     */
    public int changeRecipient(Supervisor recipient) {
        try {
            // Make a check to see if the user is a supervisor
            if (recipient == null) {
                return 0; // failure, recipient is null
            }
            this.recipient = recipient;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1; // success
    }


    /**
     * Sends the request to the recipient and adds it to the sender's and recipient's request lists.
     * 
     * @return 1 if the request was successfully sent, 0 otherwise
     */
    public int sendRequest() {
        try {
            // Send the request to the recipient
            if (sender == null || recipient == null) {
                return 0; // failure, sender or recipient is null
            }
            sender.addRequestToHistory(this);
            recipient.addPendingRequest(this);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1; // success
    }

    /**
     * Displays the request description. Should be implemented by concrete subclasses.
     */
    public void displayRequestDescription() {
    }
}
