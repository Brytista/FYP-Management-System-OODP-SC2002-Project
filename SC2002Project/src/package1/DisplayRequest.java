package package1;
import java.util.List;
interface DisplayRequest {
    static void displayRequestHistory(List<Request> requestHistory) {};
    static void displayPendingRequest(List<Request> pendingRequest) {};
}
