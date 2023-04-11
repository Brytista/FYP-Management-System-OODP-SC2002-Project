package package1;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.Iterator;


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
            while (i < pendingRequest.size()) {
                System.out.println("--------------------");
                Request request = pendingRequest.get(i);
                request.displayRequestDescription();
                request.displayStatus();
                request.displaySender();
                System.out.println("Enter 1 to Approve or Enter 2 to Reject?");
                int input = sc.nextInt();
                sc.nextLine();
                while (input != 1 && input != 2) {
                    System.out.println("Invalid input. Approve or Reject?");
                    input = sc.nextInt();
                    sc.nextLine();
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




