package package1;

public abstract class SupervisorRequest extends Request {
    protected Supervisor sender; // the user who sent the request
    protected FYPCoordinator recipient; // the user who will receive the request
    protected Supervisor replacementSupervisor; // the supervisor who will replace the current supervisor
    public int changeReplacementSupervisor(Supervisor replacementSupervisor) {
        if (replacementSupervisor == null) {
            return 0; // failure, replacementSupervisor is null
        }
        this.replacementSupervisor = replacementSupervisor;
        return 1; // success
    }
    public void displayReplacementSupervisor() {
        System.out.println("Replacement Supervisor:"+ replacementSupervisor);
    }

    @Override
    public int sendRequest(){
        //Send the request to the recipient
        if (sender == null || recipient == null) {
            return 0; // failure, sender or recipient is null
        }
        sender.addRequestHistory(this);
        recipient.addPendingRequest(this);
        return 1; // success
    }

    @Override
    public int changeRecipient(FYPCoordinator recipient){
        //Make a check to see if the user is a supervisor
        if (recipient == null) {
            return 0; // failure, recipient is null
        }
        this.recipient = recipient;
        return 1; // success
    }
    public int changeSender(User sender){
        if (!(sender.isSupervisor())) {
            return 0; // failure, not an instance of Sender or its subclasses
        }
        this.sender = sender;
        return 1; // success
    }
}
