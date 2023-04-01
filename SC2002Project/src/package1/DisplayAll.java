package package1;
import java.util.List;

public class DisplayAll implements DisplayRequest {
    public void DisplayRequestHistory(List<Request> requestHistory) {
        for(Request request : requestHistory) {
            System.out.println(request);
        }
    }

    public void DisplayPendingRequest(List<Request> pendingRequest) {
        for(Request request : pendingRequest) {
            System.out.println(request);
        }
    }
}
