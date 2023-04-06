package package1;
import java.util.*;

public class App {
    public static void main(String[] args) {
        //Put the read file
        Scanner sc = new Scanner(System.in);
        int quit = 0;
        while(quit == 0){
            if(quit == 1) break;
            System.out.println("1. Exit");
            System.out.println("2. Register");
            System.out.println("3. Login");
            System.out.print("Enter your choice: ");
            int option1 = sc.nextInt();
            sc.nextLine();
            switch(option1){
                case(1):
                quit = 1;
                break;
                case(2):
                int register = 0;
                while(register == 0){
                    System.out.println("Enter your name: ");
                    String newName = sc.nextLine();
                    System.out.println("Enter your username: ");
                    String newUsername = sc.nextLine();
                    System.out.println("Enter your password");
                    String newPassword = sc.nextLine();
                    System.out.println("Enter your role (Student/Supervisor/FYP Coordinator), enter \"quit\" to cancel");
                    String newRole = sc.nextLine();
                    if(newRole == "Student"){
                        Student newStudent = new Student(newUsername, newPassword, newName, newUsername + "@e.ntu.edu.sg");
                        Student.addToStudentsList(newStudent);
                        //Add student to the student file
                        register = 1;
                    }
                    else if(newRole == "Supervisor"){
                        Supervisor newSupervisor = new Supervisor(newUsername, newPassword, newName, newUsername + "@ntu.edu.sg");
                        Supervisor.addToSupervisorList(newSupervisor);
                        //Add supervisor to the supervisor file
                        register = 1;
                    }
                    else if(newRole == "FYP Coordinator"){
                        FYPCoordinator newFYPCoordinator = new FYPCoordinator(newUsername, newPassword, newName, newUsername + "@ntu.edu.sg");
                        FYPCoordinator.addCoordinatorToList(newFYPCoordinator);
                        //Add FYPCoordinator to the FYPCoordinator file
                        register = 1;
                    }
                    else if(newRole == "quit"){
                        register = 1;
                    }
                    else{
                        System.out.println("Your role is invalid");
                    }
                }
                break;
                case(3):
                boolean logging = true;
                while(logging){
                    System.out.println("Enter your username: ");
                    String username = sc.nextLine();
                    System.out.println("Enter your password");
                    String password = sc.nextLine();
                    System.out.println("1. Student");
                    System.out.println("2. Supervisor");
                    System.out.println("3. FYPCoordinator");
                    System.out.print("Enter your user type: ");
                    int type = sc.nextInt();
                    sc.nextLine();
                    switch(type){
                        case(1):
                        if(Student.loginStudent(username, password) == 1){
                            Student currentUser = Student.getStudentByID(username);
                            logging = false;
                            System.out.println("Welcome, student " + currentUser.getUserName);
                        }
                        else{
                            System.out.println("Your Username, Password and type does not match");
                        }
                        break;
                        case(2):
                        if(Supervisor.loginSupervisor(username, password) == 1){
                            Supervisor currentUser = Supervisor.getSupervisorByID(username);
                            logging = false;
                            System.out.println("Welcome, Supervisor " + currentUser.getUserName);
                        }
                        else{
                            System.out.println("Your Username, Password and type does not match");
                        }
                        break;
                        case(3):
                        if(FYPCoordinator.loginFYPCoordinator(username, password) == 1){
                            FYPCoordinator currentUser = FYPCoordinator.getCoordinatorByID(username);
                            logging = false;
                            System.out.println("Welcome, FYP Coordinator " + currentUser.getUserName);
                        }
                        else{
                            System.out.println("Your Username, Password and type does not match");
                        }
                        break;
                        default:
                        System.out.println("Please choose an appropriate user type");
                        break;
                    }

                    while(currentUser.isLoggedIn && currentUser.isStudent){
                        System.out.println("List of Available Actions: ");
                        System.out.println("1. Send Request");
                        System.out.println("2. See all available Projects");
                        System.out.println("3. Change Password");
                        System.out.println("4. Check my Request History");
                        System.out.println("5. Logout");
                        int option2 = sc.nextInt();
                        sc.nextLine();
                        switch(option2){
                            case(1):
                            System.out.println("Choose a request type: ");
                            currentUser.displayAvailableRequests();
                            int requestID = sc.nextInt();
                            sc.nextLine();
                            while(currentUser.chooseAndSetRequest(requestID)){
                                System.out.println("Choose a request type: ");
                                currentUser.displayAvailableRequests();
                                requestID = sc.nextInt();
                                sc.nextLine();
                            }
                            System.out.print("Input recipient ID: ");
                            String recipientID = sc.nextLine();
                            System.out.println("Input project ID: ");
                            int projectID =sc.nextInt();
                            sc.nextLine();
                            while(currentUser.makeRequest(recipientID, requestID, projectID) == 0){
                                System.out.print("Input recipient ID: ");
                                recipientID = sc.nextLine();
                                System.out.println("Input project ID: ");
                                projectID =sc.nextInt();
                                sc.nextLine();
                            }
                            System.out.println("Request is sent");
                            break;

                            case(2):
                            if(currentUser.isProjectAssigned)currentUser.viewAllProjects();
                            else currentUser.displayProjects();
                            break;

                            case(3):
                            boolean cancel = false;
                            while(!cancel){
                                System.out.println("Type \"cancel\" to cancel. Enter your old password: ");
                                String oldPass = sc.nextLine();
                                if(oldPass = "cancel") cancel = true;
                                else if (oldPass == currentUser.getPassword){
                                    break;
                                }
                                else{
                                    System.out.println("Wrong old password, try again");
                                }
                            }
                            if(cancel) break;
                            String newPass = "cancel";
                            while(newPass == "cancel"){
                                System.out.println("\"cancel\" can't be the new password. Enter a new password: ");
                                newPass = sc.nextLine();
                            }
                            currentUser.changePassword(newPass);
                            System.out.println("Your password have been changed to " + currentUser.getPassword);
                            break;

                            case(4):
                            currentUser.displayRequestHistory();
                            break;

                            case(5):
                            currentUser.logout();
                            break;

                            default:
                            System.out.println("Please choose a valid option!");
                            break;
                        }
                    }

                    while(currentUser.isLoggedIn && currentUser.isSupervisor){
                        System.out.println("List of Available Actions: ");
                        System.out.println("1. Send Request");
                        System.out.println("2. See all my Projects");
                        System.out.println("3. Change Password");
                        System.out.println("4. Check my Request History");
                        System.out.println("5. Check Inbox");
                        System.out.println("6. Create Project");
                        System.out.println("7. Logout");
                        if(currentUser.isFYPCoordinator) System.out.println("8. View Projects by:");
                        if(currentUser.isFYPCoordinator) System.out.println("9. View all projects");
                        if(currentUser.isFYPCoordinator) System.out.println("10. View all request history");
                        int option3 = sc.nextInt();
                        sc.nextLine();
                        switch(option3){
                            case(1):
                            System.out.println("Choose a request type: ");
                            currentUser.displayAvailableRequests();
                            int requestID = sc.nextInt();
                            sc.nextLine();
                            while(currentUser.chooseAndSetRequest(requestID)){
                                System.out.println("Choose a request type: ");
                                currentUser.displayAvailableRequests();
                                requestID = sc.nextInt();
                                sc.nextLine();
                            }
                            System.out.print("Input recipient ID: ");
                            String recipientFYPCoordinatorID = sc.nextLine();
                            System.out.println("Input project ID: ");
                            int projectSupervisorID =sc.nextInt();
                            sc.nextLine();
                            System.out.print("Input replacement ID");
                            String replacementID = sc.nextLine();
                            while(currentUser.makeRequest(recipientFYPCoordinatorID, projectSupervisorID, replacementID) == 0){
                                System.out.print("Input recipient ID: ");
                                recipientFYPCoordinatorID = sc.nextLine();
                                System.out.println("Input project ID: ");
                                projectSupervisorID =sc.nextInt();
                                sc.nextLine();
                                System.out.print("Input replacement ID");
                                replacementID = sc.nextLine();
                            }
                            System.out.println("Request is sent");
                            break;

                            case(2):
                            currentUser.displayProjects();
                            break;

                            case(3):
                            boolean cancel1 = false;
                            while(!cancel1){
                                System.out.println("Type \"cancel\" to cancel. Enter your old password: ");
                                String oldPass = sc.nextLine();
                                if(oldPass = "cancel") cancel = true;
                                else if (oldPass == currentUser.getPassword){
                                    break;
                                }
                                else{
                                    System.out.println("Wrong old password, try again");
                                }
                            }
                            if(cancel1) break;
                            String newPass = "cancel";
                            while(newPass == "cancel"){
                                System.out.println("\"cancel\" can't be the new password. Enter a new password: ");
                                newPass = sc.nextLine();
                            }
                            currentUser.changePassword(newPass);
                            System.out.println("Your password have been changed to " + currentUser.getPassword);
                            break;

                            case(4):
                            currentUser.displayRequestHistory();
                            break;

                            case(5):
                            currentUser.displayPendingRequest();
                            break;

                            case(6):
                            System.out.println("Enter new project title: ");
                            String newTitle = sc.nextLine();
                            currentUser.createProject(newTitle);
                            break;

                            case(7):
                            currentUser.logout();
                            break;

                            case(8):
                            if(!currentUser.isSupervisor()){
                                System.out.println("Please choose a valid option!");
                            }
                            else{
                                System.out.println("1. Supervisor name");
                                System.out.println("2. Supervisor ID");
                                System.out.println("3. Status");
                                int sortBy = sc.nextInt();
                                sc.nextLine();
                                switch(sortBy){
                                    case(1):
                                    System.out.println("Enter Supervisor Name: ");
                                    String sortByName = sc.nextLine();
                                    generateProjectReportBySupervisorName(sortByName);
                                    break;

                                    case(2):
                                    System.out.println("Enter Supervisor ID: ");
                                    String sortByID = sc.nextLine();
                                    generateProjectReportBySupervisorID(sortByID);
                                    break;

                                    case(3):
                                    while(true){
                                        System.out.println("Enter Availability (AVAILABLE, RESERVED, UNAVAILABLE, ALLOCATED): ");
                                        String sortByAvailability = sc.nextLine();
                                        ProjectStatus status;
                                        if(sortByAvailability == "AVAILABLE"){
                                            status = ProjectStatus.AVAILABLE;
                                            break;
                                        }
                                        else if(sortByAvailability == "RESERVED"){
                                            status = ProjectStatus.RESERVED;
                                            break;
                                        }
                                        else if(sortByAvailability == "UNAVAILABLE"){
                                            status = ProjectStatus.UNAVAILABLE;
                                            break;
                                        }
                                        else if(sortByAvailability == "ALLOCATED"){
                                            status = ProjectStatus.ALLOCATED;
                                            break;
                                        }
                                        else{
                                            System.out.println("Please input a proper Availability status");
                                        }
                                    }
                                    generateProjectReportBySupervisorName(status);
                                    break;
                                }
                            }
                            break;

                            case(9):
                            if(!currentUser.isSupervisor()){
                                System.out.println("Please choose a valid option!");
                            }
                            else{
                                Project.displayAllProjects();
                            }
                            break;

                            case(10):
                            if(!currentUser.isSupervisor()){
                                System.out.println("Please choose a valid option!");
                            }
                            else{
                                DisplayAll.displayRequestHistory(); //This one I'm not sure, pls cross check
                            }
                            break;

                            default:
                            System.out.println("Please choose a valid option!");
                            break;
                        }
                    }

                }
                
                break;
            }
                


            }
        }
    }
