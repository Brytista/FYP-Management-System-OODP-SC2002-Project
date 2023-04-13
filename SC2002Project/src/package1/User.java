package package1;

import java.util.ArrayList;
import java.util.List;
/**
 * Parent Class of all user using the FYP management system
*/
abstract class User{

    /**
     * The unique user ID of the user.
     */
    private String userID;

    /**
     * The password of the user's account.
     */
    private String password;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * A boolean flag indicating if the user is currently logged in.
     */
    private boolean loggedIn = false;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * A list of requests made by the user in the past.
     */
    private List<Request> requestHistory = new ArrayList<>();


    /**
    * Creates a new user with the specified user ID, password, name, and email address.
    *
    * @param userID the user's unique identifier
    * @param password the user's password
    * @param name the user's name
    * @param email the user's email address
    */
    public User(String userID, String password, String name, String email) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    /**
     * Allows the user to select a request they want to make, and sets the selected request ID.
     *
     * @param requestID the ID of the request the user wants to make
     * @return 1 if the selection is successful; otherwise 0, and an error is logged.
     */
    public abstract int chooseAndSetRequest(int requestID);

    /**
     * Returns the user's unique identifier.
     *
     * @return the user's unique identifier
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param userID the new unique identifier to set for the user
     * @return 1 if the set operation is successful; otherwise 0, and an error is logged.
     */
    public int setUserID(String userID) {
        try {
            this.userID = userID;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }


    /**
     * Returns the user's name.
     *
     * @return the user's name
     */
    public String getUserName() {
        return name;
    }


    /**
     * Sets the user's name.
     *
     * @param name the new name to set for the user
     * @return 1 if the set operation is successful; otherwise 0, and an error is logged.
     */
    public int setUserName(String name) {
        try {
            this.name = name;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }


    /**
     * Returns the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes the user's password to the specified value.
     *
     * @param password the new password to set for the user
     * @return 1 if the password change operation is successful; otherwise 0, and an error is logged.
     */
    public int changePassword(String password) {
        try {
            this.password = password;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }


    /**
     * Returns the user's email.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the user's email.
     *
     * @param email the new email to set for the user
     * @return 1 if the set operation is successful; otherwise 0, and an error is logged.
     */
    public int setEmail(String email) {
        try {
            this.email = email;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }


    
    /**
     * Logs the user into the system. Returns 1 if the login operation is successful;
     * otherwise 0 and an error is logged.
     *
     * @return 1 if the login operation is successful; otherwise 0, and an error is logged.
     */
    public int login(){
        try {
            if(loggedIn) return 0;
            loggedIn = true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    

    /**
     * Logs the user out of the system. Returns 1 if the logout operation is successful;
     * otherwise 0 and an error is logged.
     *
     * @return 1 if the logout operation is successful; otherwise 0, and an error is logged.
     */
    public int logout() {
        try {
            this.loggedIn = false; // set loggedIn to false
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

 

    /**
     * Adds a request to the user's request history. Returns 1 if the operation is successful;
     * otherwise 0 and an error is logged.
     *
     * @param request the request to add to the user's request history
     * @return 1 if the operation is successful; otherwise 0, and an error is logged.
     */
    public int addRequestToHistory(Request request) {
        try {
            requestHistory.add(request);
            return 1; // return 1 if successful
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }


    /**
     * Displays the user's request history.
     */
    public void displayRequestHistory() {
        try {
            DisplayAll.displayRequestHistory(requestHistory);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    

    /**
     * Returns false to indicate that the user is not a student.
     *
     * @return false to indicate that the user is not a student
     */
    public boolean isStudent() {
        return false;
    }


    /**
     * Returns false to indicate that the user is not a supervisor.
     *
     * @return false to indicate that the user is not a supervisor
     */
    public boolean isSupervisor() {
        return false;
    }

    /**
     * Returns false to indicate that the user is not an FYPCoordinator.
     *
     * @return false to indicate that the user is not an FYPCoordinator
     */
    public boolean isFYPCoordinator() {
        return false;
    }

    /**
     * Returns the value of the loggedIn field to indicate whether the user is currently logged in or not.
     *
     * @return true if the user is currently logged in, false otherwise
     */
    public boolean isLoggedIn(){
        return loggedIn;
    }
}
