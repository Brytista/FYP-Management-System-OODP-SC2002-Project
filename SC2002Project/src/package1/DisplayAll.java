package package1;
import java.util.List;
import java.util.Scanner;
public class DisplayAll {

    public static void displayRequestHistory(List<Request> requestHistory) {
    try {
        if(requestHistory.isEmpty()){
            System.out.println("--------------------");
            System.out.println("No requests found");
            System.out.println("--------------------");
            return;
        }
        else{
        for(Request request : requestHistory) {
            System.out.println("--------------------");
            request.displaySender();
            request.displayRecipient();
            request.displayProject();
            request.displayIsReviewed();
            request.displayStatus();
            request.displayRequestDescription();
            System.out.println("--------------------");
        }
    }
    } catch (Exception e) {
        System.out.println("--------------------");
        System.err.println("Error: " + e.getMessage());
        System.out.println("--------------------");
    }
}


public static void displayPendingRequest(List<Request> pendingRequest) {
    Scanner sc = new Scanner(System.in);
    try {
        if (pendingRequest.isEmpty()) {
            System.out.println("--------------------");
            System.out.println("No pending requests found");
            System.out.println("--------------------");
            return;
        } else {
            int i = 0;
            int num = pendingRequest.size(); 
            while (i < pendingRequest.size()) {
                System.out.println("--------------------");
                System.out.println("NEW REQUEST:");
                System.out.println("Select an action to this request so that you can see other requests!");
                System.out.println("Number of request unprocessed: " + num);
                System.out.println("");
                Request request = pendingRequest.get(i);
                request.displayRequestDescription();
                request.displayStatus();
                request.displaySender();
                int input;
                try{    
                    System.out.println("Enter 1 to Approve or Enter 2 to Reject?");
                    input = sc.nextInt();
                    sc.nextLine();
                    while (input != 1 && input != 2) {
                        System.out.println("Invalid input. Approve or Reject? (1 or 2))");
                        input = sc.nextInt();
                        sc.nextLine();
                    }
                }catch(Exception e){
                    System.out.println("Invalid input. Approve or Reject? (1 or 2)" + e.getMessage());
                    sc.nextLine();
                    continue; // go back to the beginning of the loop
                }
                
                if (input == 1) {
                    if (request.approve() == 1) {
                        System.out.println("Request approved");
                    } else {
                        System.out.println("Request failed to be approved");
                    }
                } else {
                    if (request.reject() == 1) {
                        System.out.println("Request rejected");
                    } else {
                        System.out.println("Request failed to be rejected");
                    }
                }
                // Only increment i if the request was not removed from the list.
                if (pendingRequest.contains(request)) {
                    i++;
                }
                num--; // decrement the number of requests unprocessed
                System.out.println("--------------------");
            }
        }
    } catch (Exception e) {
        System.out.println("--------------------");
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
        System.out.println("--------------------");
    }
}


}




