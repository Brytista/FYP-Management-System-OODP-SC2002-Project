package package1;

public abstract class StudentRequestWithString extends StudentRequest {
    protected String newProjectTitle; // the new project title
    RequestStatus status;
    boolean isReviewed;

    public StudentRequestWithString(Student sender, Supervisor recipient, Project project, String newProjectTitle) {
        super(sender, recipient, project);
        this.newProjectTitle = newProjectTitle;
    }

    public int create(Student sender, Supervisor recipient, Project project, String newProjectTitle) {
        try {
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
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }
    
    public int changeNewProjectTitle(String newProjectTitle) {
        try {
            if (newProjectTitle == null) {
                return 0; // failure, newProjectTitle is null
            }
            this.newProjectTitle = newProjectTitle;
            return 1; // success
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // failure
        }
    }
    
    
}
