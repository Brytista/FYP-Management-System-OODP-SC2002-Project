package package1;
import java.util.List;
import java.util.Scanner;

public class DisplayAll implements DisplayRequest {
    Scanner sc = new Scanner(System.in);

    public void DisplayRequestHistory(List<Request> requestHistory) {
        for(Request request : requestHistory) {
            request.displaySender();
            request.displayRecipient();
            request.displayProject();
            request.displayIsReviewed();
            request.displayStatus();
        }
    }

    public void DisplayPendingRequest(List<Request> pendingRequest) {
        for(Request request : pendingRequest) {
            System.out.println("Approve or Reject?");

            while(!sc.nextLine().equals("Approve") && !sc.nextLine().equals("Reject")) {
                System.out.println("Invalid input. Approve or Reject?");
            }

            if(sc.nextLine().equals("Approve")) request.approve();
            else request.reject();
        }
    }
}
