package package1;
public abstract class StudentRequest extends Request {
    protected Student sender; // the user who sent the request
    @Override
    public int sendRequest() {
        // Send the request to the recipient
        if (sender == null || recipient == null) {
            return 0; // failure, sender or recipient is null
        }
        sender.addRequestHistory(this);
        recipient.addPendingRequest(this);
        return 1; // success
    }

    @Override
    public int changeSender(User sender){
        if (sender.isStudent()) {
            return 0; // failure, not an instance of Sender or its subclasses
        }
        this.sender = sender;
        return 1; // success
    }
    
}
