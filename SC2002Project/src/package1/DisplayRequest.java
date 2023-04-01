package package1;
import java.util.List;
interface DisplayRequest {
    void DisplayRequestHistory(List<Request> requestHistory);
    void DisplayPendingRequest(List<Request> pendingRequest);
}
