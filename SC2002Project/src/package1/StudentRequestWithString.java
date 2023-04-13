package package1;
/**
 * Abstract class representing a student request with a string-based new project title
 */
public abstract class StudentRequestWithString extends StudentRequest {
    /**
     * The new project title.
     */
    protected String newProjectTitle; 

    /**
     * The status of the request.
     */
    RequestStatus status;

    /**
     * A boolean that represents if the request has been reviewed or not.
     */
    boolean isReviewed;

    /**
     * Constructor for creating a new student request with a string-based new project title
     *
     * @param sender         the student sending the request
     * @param recipient      the supervisor receiving the request
     * @param project        the project associated with the request
     * @param newProjectTitle the new project title requested by the student
     */
    public StudentRequestWithString(Student sender, Supervisor recipient, Project project, String newProjectTitle) {
        super(sender, recipient, project);
        this.newProjectTitle = newProjectTitle;
    }

    /**
     * Creates a new student request with a string-based new project title
     *
     * @param sender         the student sending the request
     * @param recipient      the supervisor receiving the request
     * @param project        the project associated with the request
     * @param newProjectTitle the new project title requested by the student
     * @return 1 if the request was successfully created, 0 otherwise
     */
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

    /**
     * Changes the new project title associated with the request
     *
     * @param newProjectTitle the new project title requested by the student
     * @return 1 if the new project title was successfully changed, 0 otherwise
     */
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

