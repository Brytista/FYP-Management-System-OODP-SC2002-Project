package package1;

public abstract class StudentRequestWithString {
    protected String newProjectTitle; // the new project title
    
    public int create(Student sender, Supervisor recipient, Project project, String newProjectTitle){
        if(sender == null || recipient == null || project == null|| newProjectTitle == null){
            return 0;
        }
        this.sender = sender;
        this.recipient = recipient;
        this.project = project;
        this.newProjectTitle = newProjectTitle;
        this.isReviewed = false;
        this.status = RequestStatus.PENDING;
        return 1;
    }

    public int changeNewProjectTitle(String newProjectTitle){
        if (newProjectTitle == null) {
            return 0; // failure, newProjectTitle is null
        }
        this.newProjectTitle = newProjectTitle;
        return 1; // success
    }
    
}