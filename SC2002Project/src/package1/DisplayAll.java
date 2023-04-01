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
            request.displayRequestDescription();
        }
    }

    public void DisplayPendingRequest(List<Request> pendingRequest) {
        for(Request request : pendingRequest) {
            System.out.println("Approve or Reject?");

            String input = sc.nextLine();
            while(!input.equals("Approve") && !input.equals("Reject")) {
                System.out.println("Invalid input. Approve or Reject?");
                input = sc.nextLine();
            }

            if(input.equals("Approve")) {
                if(request.approve()==1) System.out.println("Request approved");
                else System.out.println("Request not approved");
            }
            else {
                if(request.reject()==1) System.out.println("Request rejected");
                else System.out.println("Request not rejected");
            }
        }
    }
}
