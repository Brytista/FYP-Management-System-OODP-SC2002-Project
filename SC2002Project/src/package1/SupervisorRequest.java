package package1;
/**
* Parent Class of all possible requests than can be made by a supervisor, extends from Request class
*
*/
public abstract class SupervisorRequest extends Request {
    /**
     * The user who sent the request.
     */
    protected Supervisor sender;

    /**
     * The supervisor who will replace the current supervisor.
     */
    protected Supervisor replacementSupervisor;

    
    /**
     * Constructs a new SupervisorRequest object with the specified sender, recipient, project, and replacement supervisor.
     * 
     * @param sender               the Supervisor who is sending the request.
     * @param recipient            the FYPCoordinator who is the intended recipient of the request.
     * @param project              the Project associated with the request.
     * @param replacementSupervisor the Supervisor who is to replace the current supervisor of the Project (optional).
     */
    public SupervisorRequest(Supervisor sender, FYPCoordinator recipient, Project project, Supervisor replacementSupervisor) {
        super(sender, recipient, project);
        this.sender = sender;
        this.recipient = recipient;
        this.project = project;
        this.replacementSupervisor = replacementSupervisor;
    }

    
    /**
     * Updates the replacement supervisor for this request.
     * 
     * @param replacementSupervisor the new Supervisor who will replace the current supervisor of the Project.
     * @return                      1 if the replacement supervisor was successfully updated, 0 if an error occurred.
     */
    public int changeReplacementSupervisor(Supervisor replacementSupervisor) {
        try {
            if (replacementSupervisor == null) {
                return 0; // failure, replacementSupervisor is null
            }
            this.replacementSupervisor = replacementSupervisor;
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }

    
    /**
     * Displays the replacement supervisor for this request.
     */
    public void displayReplacementSupervisor() {
        try {
            System.out.println("Replacement Supervisor: " + replacementSupervisor);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    /**
     * Sends this request to the intended recipient.
     * 
     * @return  1 if the request was successfully sent, 0 if an error occurred.
     */
    @Override
    public int sendRequest() {
        try {
            // Send the request to the recipient
            if (sender == null || recipient == null) {
                return 0; // failure, sender or recipient is null
            }
            sender.addRequestToHistory(this);
            recipient.addPendingRequest(this);
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }


    /**
     * Creates a new SupervisorRequest with the specified parameters.
     * 
     * @param sender                the supervisor sending the request
     * @param recipient             the FYP coordinator who will receive the request
     * @param project               the project related to the request
     * @param replacementSupervisor the new supervisor assigned to the project, if any
     * 
     * @return  1 if the request was successfully created, 0 if an error occurred.
     */
    public int create(Supervisor sender, FYPCoordinator recipient, Project project, Supervisor replacementSupervisor) {
        try {
            if (sender == null || recipient == null || project == null || replacementSupervisor == null) {
                System.out.println("Error: " + "One or more of the parameters are null");
                return 0;
            }
            this.sender = sender;
            this.recipient = recipient;
            this.project = project;
            this.replacementSupervisor = replacementSupervisor;
            this.isReviewed = false;
            this.status = RequestStatus.PENDING;
            if (this.recipient == null) {
                System.out.println("Error: " + "Recipient is null");
                return 0;
            }
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }


    /**
     * Changes the recipient of this SupervisorRequest.
     * 
     * @param recipient  the new FYPCoordinator to receive the request
     * 
     * @return  1 if the recipient was successfully changed, 0 if an error occurred.
     */
    public int changeRecipient(FYPCoordinator recipient) {
        try {
            // Make a check to see if the user is a supervisor
            if (recipient == null) {
                return 0; // failure, recipient is null
            }
            this.recipient = recipient;
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }


    /**
     * Changes the sender of this SupervisorRequest.
     * 
     * @param sender  the new Supervisor to send the request
     * 
     * @return  1 if the sender was successfully changed, 0 if an error occurred.
     */
    public int changeSender(Supervisor sender) {
        try {
            // Check if the sender is an instance of Supervisor or its subclasses
            if (!(sender instanceof Supervisor)) {
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
