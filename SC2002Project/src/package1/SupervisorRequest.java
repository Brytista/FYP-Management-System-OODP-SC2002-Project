package package1;

public abstract class SupervisorRequest extends Request {
    protected Supervisor sender; // the user who sent the request
    protected FYPCoordinator recipient; // the user who will receive the request
    protected Supervisor replacementSupervisor; // the supervisor who will replace the current supervisor
    
    public SupervisorRequest(Supervisor sender, FYPCoordinator recipient, Project project, Supervisor replacementSupervisor) {
        super(sender, recipient, project);
        this.replacementSupervisor = replacementSupervisor;
    }
    
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
    
    public void displayReplacementSupervisor() {
        try {
            System.out.println("Replacement Supervisor:"+ replacementSupervisor);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public int sendRequest(){
        try {
            //Send the request to the recipient
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

    public int create(Supervisor sender, FYPCoordinator recipient, Project project, Supervisor replacementSupervisor){
        try {
            if(sender == null || recipient == null || project == null || replacementSupervisor == null){
                return 0;
            }
            this.sender = sender;
            this.recipient = recipient;
            this.project = project;
            this.replacementSupervisor = replacementSupervisor;
            this.isReviewed = false;
            this.status = RequestStatus.PENDING;
            return 1;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }

    public int changeRecipient(FYPCoordinator recipient){
        try {
            //Make a check to see if the user is a supervisor
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

    public int changeSender(Supervisor sender){
        try {
            if (!(sender.isSupervisor())) {
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
