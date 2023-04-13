package package1;

public abstract class Request {
    protected User sender; // the user who sent the request
    protected Supervisor recipient; // the user who will receive the request
    protected boolean isReviewed; // the status of the request
    protected Project project; // the project the request is about
    protected RequestStatus status; // the status of the request

    public Request(User sender, Supervisor recipient, Project project) {
        this.sender = sender;
        this.recipient = recipient;
        this.isReviewed = false; // default value
        this.project = project;
        this.status = RequestStatus.PENDING; // default value
    }

    public int changeStatus(RequestStatus status) {
        if (status == null) {
            return 0; // failure, status is null
        }
        this.status = status;
        return 1; // success
    }

    public void displayStatus() {
        System.out.println("Status:" + status);
    }

    public int changeProject(Project project) {
        if (Project.isProject(project)) {
            this.project = project;
            return 1; // success
        }
        return 0; // failure
    }

    public void displayProject() {
        System.out.println("Project:" + project.getProjectTitle());
    }

    public void printRequest() {
        System.out.println("Request from " + sender.getUserID() + " to " + recipient.getUserID() + " with status "
                + this.isReviewed);
    }

    public int approve() {
        return 0;
    } // by Concrete Class

    public int reject() {
        return 0;
    } // by Concrete Class

    public void displayIsReviewed() {
        System.out.println("IsReviewed:" + isReviewed);
    }

    public int makeIsReviewed() {
        this.isReviewed = true;
        return 1; // success
    }

    public void displaySender() {
        System.out.println("Sender:" + sender.getUserID());
    }

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

    public void displayRecipient() {
        try {
            System.out.println("Recipient: " + this.recipient.getUserID());
        } catch (NullPointerException e) {
            System.out.println("Error: recipient is null.");
        }
    }

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

    public void displayRequestDescription() {
    }
}
