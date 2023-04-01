package package1;

public class RequestProjectAllocation extends StudentRequest {
    RequestProjectAllocation(Student sender, Supervisor recipient, Project project) {
        this.sender = sender; 
        this.recipient = recipient;
        this.project = project;
        this.isReviewed = false;
    }
}
