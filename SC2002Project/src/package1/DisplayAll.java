package package1;
import java.util.List;

public class DisplayAll implements DisplayRequest {
    void DisplayRequestHistory(List<Request> requestHistory) {
        for(Request request : requestHistory) {
            System.out.println(request);
        }
    }
}
