package package1;

import java.util.ArrayList;
import java.util.List;

abstract class User {

    private String userID;
    private String password;
    private String name;
    private boolean loggedIn = false;
    private String email;

    private List<User> users = new ArrayList<>();
    private List<String> availableRequests = new ArrayList<>();
    private List<Request> requestHistory = new ArrayList<>();

    // Constructor
    public User(String userID, String password, String name, String email) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // chooseAndSetRequest(): user selects the request they want to make; 1 returned
    // if successful, otherwise 0 and error is logged
    public abstract int chooseAndSetRequest(int requestID);

    // getUserID(): returns the userID
    public String getUserID() {
        return userID;
    }

    // setUserID(): sets the userID; 1 returned if successful, otherwise 0 and error
    // is logged
    public int setUserID(String userID) {
        try {
            this.userID = userID;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // getUserName(): returns the user's name
    public String getUserName() {
        return name;
    }

    // setUserName(): sets the user's name; 1 returned if successful, otherwise 0
    // and error is logged
    public int setUserName(String name) {
        try {
            this.name = name;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // getPassword(): returns the password
    public String getPassword() {
        return password;
    }

    // setPassword(): sets the password; 1 returned if successful, otherwise 0 and
    // error is logged
    public int setPassword(String password) {
        try {
            this.password = password;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // getEmail(): returns the email
    public String getEmail() {
        return email;
    }

    // setEmail(): sets the email; 1 returned if successful, otherwise 0 and error
    // is logged
    public int setEmail(String email) {
        try {
            this.email = email;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // login(): returns 1 if login is successful, otherwise 0 and error is logged
    public int login(String userID, String password) {
        try {
            for (User user : users) {
                if (user.getUserID().equals(userID)) {
                    if (user.getPassword().equals(password)) {
                        // User exists and password is correct
                        this.loggedIn = true;
                        return 1;
                    } else {
                        // User exists but password is incorrect
                        return 0;
                    }
                }
            }
            // User does not exist
            return 0;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }

    // logout(): returns 1 if logout is successful, otherwise 0 and error is logged
    public int logout() {
        try {
            this.loggedIn = false;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // changePassword(): returns 1 if password change is successful, otherwise 0 and
    // error is logged
    public int changePassword(String password) {
        try {
            this.password = password;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // displayAvailableRequests(): displays all available requests for the user
    public void displayAvailableRequests() {
        for (String request : availableRequests) {
            System.out.println(request);
        }
    }

    // addNewRequest(): adds a new request to the list of available requests;
    public int addNewRequest(String request) {
        try {
            availableRequests.add(request);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // addRequestToHistory(): adds a request to the user's request history;
    // 1 returned if successful, otherwise 0 and error is logged
    public int addRequestToHistory(Request request) {
        try {
            requestHistory.add(request);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // displayRequestHistory(): displays the user's request history
    public void displayRequestHistory() {
        for (Request request : requestHistory) {
            System.out.println(
                    request.getRequestID() + " | " + request.getRequestType() + " | " + request.getRequestStatus());
        }
    }
}
