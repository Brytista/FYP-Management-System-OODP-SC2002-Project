package package1;

public class RequestChangeProjectTitle extends StudentRequestWithString {
    
    @Override
    public int approve() {
            if(this.project == null|| this.newProjectTitle == null|| this.recipient == null||this.sender == null||this.status == null){
                return 0;
            }
            int success = project.changeProjectTitle(this.newProjectTitle);
            if (success == 0) {
                return 0; // failure, project title is not changed
            }
            recipient.removePendingRequest(this);
            recipient.addRequestHistory(this);
            this.changeStatus(RequestStatus.APPROVED);
            return 1; // success
    }
    @Override
    public int reject() {
        if(this.project == null|| this.newProjectTitle == null|| this.recipient == null||this.sender == null||this.status == null){
            return 0;
        }
        recipient.removePendingRequest(this);
        recipient.addRequestHistory(this);
        this.changeStatus(RequestStatus.REJECTED);
        return 1; // success
    }

    public int sendRequest(){
        //Send the request to the recipient
        if (sender == null || recipient == null) {
            return 0; // failure, sender or recipient is null
        }
        sender.addRequestHistory(this);
        recipient.addPendingRequest(this);
        this.changeStatus(RequestStatus.PENDING); // change the status of the request to pending
        return 1; // success
    }

    public void displayRequestDescription(){
        System.out.println("Request to change project title from " + this.project.getProjectTitle() + " to " + this.newProjectTitle);
    }
}
