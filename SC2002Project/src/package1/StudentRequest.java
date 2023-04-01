package package1;
public abstract class StudentRequest extends Request {
    protected Student sender; // the user who sent the request

    public StudentRequest(Student sender, Supervisor recipient, Project project) {
        super(sender, recipient, project);
    }
    
    public int sendRequest() {
        // Send the request to the recipient
        if (sender == null || recipient == null) {
            return 0; // failure, sender or recipient is null
        }
        sender.addRequestToHistory(this);
        recipient.addPendingRequest(this);
        return 1; // success
    }
    public int create(Student sender, Supervisor recipient, Project project){
        if(sender == null || recipient == null || project == null){
            return 0;
        }
        this.sender = sender;
        this.recipient = recipient;
        this.project = project;
        this.isReviewed = false;
        this.status = RequestStatus.PENDING;
        return 1;
    }

    public int changeSender(Student sender){
        if (!(sender.isStudent())) {
            return 0; // failure, not an instance of Sender or its subclasses
        }
        this.sender = sender;
        return 1; // success
    }
    
}
