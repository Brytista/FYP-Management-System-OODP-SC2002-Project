package package1;
import java.util.*;
import package1.Student;
import package1.Supervisor;
import package1.FYPCoordinator;


public class App {
    public static void main(String[] args) {
        //Put the read file
        Scanner sc = new Scanner(System.in);
        int quit = 0;

        // Add Students
        Student student1 = new Student("BKIELY001", "password", "Bryan", "BKIELY001@e.ntu.edu.sg"); 
        Student student2 = new Student("JAMIE69", "password", "Yiji", "JAMIE69@e.ntu.edu.sg"); 
        Student student3 = new Student("MATOS69", "password", "Matthew", "MATOS69@e.ntu.edu.sg"); 
        Student student4 = new Student("JOSHAC69", "password", "Joshua", "JOSHAC69@e.ntu.edu.sg"); 

        //add supervisors
        Supervisor supervisor1 = new Supervisor("JAMES", "password", "James", "JAMES@ntu.edu.sg"); 
        Supervisor supervisor2 = new Supervisor("LOKE", "password", "Loke Yuan Ren", "LOKE@ntu.edu.sg"); 
        Supervisor supervisor3 = new Supervisor("NEWTON", "password", "Newton Fernando", "NEWTON@ntu.edu.sg"); 

        //add fyp coordinators
        FYPCoordinator fypCoordinator1 = new FYPCoordinator("Li Fang", "password", "Li Fang", "ASFLI@ntu.edu.sg"); 

        //add projects
        Project project1 = new Project(Supervisor.getSupervisorByID("JAMES"), "Project 1"); 
        Project project2 = new Project(Supervisor.getSupervisorByID("LOKE"), "Project 2");
        Project project3 = new Project(Supervisor.getSupervisorByID("NEWTON"), "Project 3");
        Project project4 = new Project(Supervisor.getSupervisorByID("JAMES"), "Project 4");

        while(quit == 0){
            if(quit == 1) break;
            System.out.println("1. Exit");
            System.out.println("2. Register");
            System.out.println("3. Login");
            System.out.print("Enter your choice: ");
            int option1 = sc.nextInt();
            sc.nextLine();
            Student currentStudent = null; 
            Supervisor currentSupervisor = null;
            FYPCoordinator currentFYPCoordinator = null;
            switch(option1){
                case(1):
                quit = 1;
                break;
                case(2):
                int register = 0;
                while(register == 0){
                    System.out.println("Enter your name: ");
                    String newName = sc.nextLine();
                    System.out.println("Enter your userID: ");
                    String newUsername = sc.nextLine();
                    System.out.println("Enter your password");
                    String newPassword = sc.nextLine();
                    System.out.println("Press 1 to register as a student, 2 to register as a supervisor, 3 to register as a FYPCoordinator, 4 to quit");
                    String newRole = sc.nextLine();
                    switch(newRole){
                        case("1"):
                        Student newStudent = new Student(newUsername, newPassword, newName, newUsername + "@e.ntu.edu.sg");
                        Student.addToStudentsList(newStudent);
                        //Add student to the student file
                        register = 1;
                        break;
                        case("2"):
                        Supervisor newSupervisor = new Supervisor(newUsername, newPassword, newName, newUsername + "@ntu.edu.sg");
                        Supervisor.addToSupervisorList(newSupervisor);
                        //Add supervisor to the supervisor file
                        register = 1;
            
                        break;
                        case("3"):
                        FYPCoordinator newFYPCoordinator = new FYPCoordinator(newUsername, newPassword, newName, newUsername + "@ntu.edu.sg");
                        FYPCoordinator.addCoordinatorToList(newFYPCoordinator);
                        //Add FYPCoordinator to the FYPCoordinator file
                        register = 1;
                        break;
                        case("4"):
                        register = 1;
                        break;
                        default:
                        System.out.println("Your role is invalid"); 
                    }
                }
                break;
                case(3):
                boolean logging = true;
                while(logging){
                    System.out.println("Enter your userID: ");
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
                            int loginResult = Student.loginStudent(username, password);
                            if(loginResult == 1){
                                currentStudent = Student.getStudentByID(username);
                                if (currentStudent != null) {
                                    currentStudent.login();
                                    logging = false;
                                    System.out.println("Welcome, student " + currentStudent.getUserName());
                                } else {
                                    System.out.println("Error: Unable to find student with ID " + username);
                                }
                            }
                            else{
                                System.out.println("Your username, password, and type do not match");
                            }
                            break;
                        case(2):
                            int supervisorLoginResult = Supervisor.loginSupervisor(username, password);
                            if(supervisorLoginResult == 1){
                                currentSupervisor = Supervisor.getSupervisorByID(username);
                                if (currentSupervisor != null) {
                                    currentSupervisor.login();
                                    logging = false;
                                    System.out.println("Welcome, Supervisor " + currentSupervisor.getUserName());
                                } else {
                                    System.out.println("Error: Unable to find supervisor with ID " + username);
                                }
                            }
                            else{
                                System.out.println("Your username, password, and type do not match");
                            }
                            break;
                        case(3):
                            int fypCoordinatorLoginResult = FYPCoordinator.loginFYPCoordinator(username, password);
                            if(fypCoordinatorLoginResult == 1){
                                currentFYPCoordinator = FYPCoordinator.getCoordinatorByID(username);
                                if (currentFYPCoordinator != null) {
                                    currentFYPCoordinator.login();
                                    logging = false;
                                    System.out.println("Welcome, FYP Coordinator " + currentFYPCoordinator.getUserName());
                                } else {
                                    System.out.println("Error: Unable to find FYP coordinator with ID " + username);
                                }
                            }
                            else{
                                System.out.println("Your username, password, and type do not match");
                            }
                            break;
                        default:
                            System.out.println("Please choose an appropriate user type");
                            break;
                    }
                    
                    

                    while(currentStudent.isLoggedIn() && currentStudent!=null){
                        System.out.println("List of Available Actions: ");
                        System.out.println("1. Send Request");
                        System.out.println("2. See all available Projects");
                        System.out.println("3. Change Password");
                        System.out.println("4. Check my Request History");
                        System.out.println("5. Logout");
                        int option2 = 6; 
                        try {
                            option2 = sc.nextInt();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Your input is invalid!"); 
                        }
                        
                        switch(option2){
                            case(1):
                            System.out.println("Choose a request type: ");
                            Student.displayAvailableRequests();
                            System.out.println("Input 4 to cancel");
                            int requestID = sc.nextInt();
                            sc.nextLine();
                            if(requestID == 4) break;
                            while(currentStudent.chooseAndSetRequest(requestID)==0){
                                System.out.println("Choose a request type: ");
                                Student.displayAvailableRequests();
                                System.out.println("Input 4 to cancel");
                                requestID = sc.nextInt();
                                sc.nextLine();
                                if(requestID == 4) break;
                            }
                            System.out.print("Input recipient ID (String): ");
                            String recipientID = sc.nextLine();
                            System.out.println("Input project ID (Integer): ");
                            int projectID =sc.nextInt();
                            sc.nextLine();
                            while(currentStudent.makeRequest(recipientID, requestID, projectID) == 0){
                                System.out.print("Input recipient ID: ");
                                recipientID = sc.nextLine();
                                System.out.println("Input project ID: ");
                                projectID =sc.nextInt();
                                sc.nextLine();
                            }
                            System.out.println("Request is sent");
                            break;

                            case(2):
                            if(currentStudent.isProjectAssigned()) currentStudent.viewAllProjects();
                            else currentStudent.displayProjects();
                            break;

                            case(3):
                            boolean cancel = false;
                            while(!cancel){
                                System.out.println("Type \"1\" to cancel. Enter your old password: ");
                                String oldPass = sc.nextLine();
                                if (oldPass.equals("1")) cancel = true;

                                else if (oldPass == currentStudent.getPassword()){
                                    break;
                                }
                                else{
                                    System.out.println("Wrong old password, try again");
                                }
                            }
                            if(cancel) break;
                            String newPass = "cancel";
                            while (newPass.equals("cancel")){
                                System.out.println("\"cancel\" can't be the new password. Enter a new password: ");
                                newPass = sc.nextLine();
                            }
                            currentStudent.changePassword(newPass);
                            System.out.println("Your password have been changed to " + currentStudent.getPassword());
                            break;

                            case(4):
                            currentStudent.displayRequestHistory();
                            break;

                            case(5):
                            currentStudent.logout();
                            break;

                            default:
                            System.out.println("Please choose a valid option!");
                            break;
                        }
                    }
                    
                    while(currentSupervisor.isLoggedIn() && currentSupervisor != null){
                        System.out.println("List of Available Actions: ");
                        System.out.println("1. Send Request");
                        System.out.println("2. See all my Projects");
                        System.out.println("3. Change Password");
                        System.out.println("4. Check my Request History");
                        System.out.println("5. Check Inbox");
                        System.out.println("6. Create Project");
                        System.out.println("7. Logout");
                        // if(currentUser.isFYPCoordinator()) System.out.println("8. View Projects by:");
                        // if(currentUser.isFYPCoordinator()) System.out.println("9. View all projects");
                        // if(currentUser.isFYPCoordinator()) System.out.println("10. View all request history");
                        int option3 = sc.nextInt();
                        sc.nextLine();
                        switch(option3){
                            case(1):
                            System.out.println("Choose a request type: ");
                            Supervisor.displayAvailableRequests();
                            int requestID = sc.nextInt();
                            sc.nextLine();
                            while(currentSupervisor.chooseAndSetRequest(requestID)!=0){
                                System.out.println("Choose a request type: ");
                                currentSupervisor.displayAvailableRequests();
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
                            //Iterate until the request is sent
                            while(currentSupervisor.makeRequest(recipientFYPCoordinatorID, projectSupervisorID, replacementID) == 0){
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
                            currentSupervisor.displayProjects();
                            break;

                            case(3):
                            boolean cancel1 = false;
                            //Iterate until the old password is correct or the user cancels
                            while(!cancel1){
                                System.out.println("Type \"1\" to cancel. Enter your old password: ");
                                String oldPass = sc.nextLine();
                                if (oldPass.equals("1")) cancel1 = true;
                                else if (oldPass == currentSupervisor.getPassword()){
                                    break;
                                }
                                else{
                                    System.out.println("Wrong old password, try again");
                                }
                            }
                            if(cancel1) break;
                            boolean cancel2 = false;
                            String newPass = null;
                            //Iterate until the new password is not "cancel"
                            while(!cancel2){
                                System.out.println("Enter a new password: ");
                                newPass = sc.nextLine();
                            }
                            currentSupervisor.changePassword(newPass);
                            System.out.println("Your password have been changed to " + currentSupervisor.getPassword());
                            break;

                            case(4):
                            currentSupervisor.displayRequestHistory();
                            break;

                            case(5):
                            currentSupervisor.viewAllPendingRequest();
                            break;

                            case(6):
                            System.out.println("Enter new project title: ");
                            String newTitle = sc.nextLine();
                            currentSupervisor.createProject(newTitle);
                            break;

                            case(7):
                            currentSupervisor.logout();
                            break;

                            // case(8):
                            // if(currentSupervisor.isSupervisor()){
                            //     System.out.println("Please choose a valid option!");
                            // }
                            // else{
                            //     System.out.println("1. Supervisor name");
                            //     System.out.println("2. Supervisor ID");
                            //     System.out.println("3. Status");
                            //     int sortBy = sc.nextInt();
                            //     sc.nextLine();
                            //     switch(sortBy){
                            //         case(1):
                            //         System.out.println("Enter Supervisor Name: ");
                            //         String sortByName = sc.nextLine();
                            //         generateProjectReportBySupervisorName(sortByName);
                            //         break;

                            //         case(2):
                            //         System.out.println("Enter Supervisor ID: ");
                            //         String sortByID = sc.nextLine();
                            //         generateProjectReportBySupervisorID(sortByID);
                            //         break;

                            //         case(3):
                            //         ProjectStatus status;
                            //         while(true){
                            //             System.out.println("Enter Availability (AVAILABLE, RESERVED, UNAVAILABLE, ALLOCATED): ");
                            //             String sortByAvailability = sc.nextLine();
                            //             if(sortByAvailability == "AVAILABLE"){
                            //                 status = ProjectStatus.AVAILABLE;
                            //                 break;
                            //             }
                            //             else if(sortByAvailability == "RESERVED"){
                            //                 status = ProjectStatus.RESERVED;
                            //                 break;
                            //             }
                            //             else if(sortByAvailability == "UNAVAILABLE"){
                            //                 status = ProjectStatus.UNAVAILABLE;
                            //                 break;
                            //             }
                            //             else if(sortByAvailability == "ALLOCATED"){
                            //                 status = ProjectStatus.ALLOCATED;
                            //                 break;
                            //             }
                            //             else{
                            //                 System.out.println("Please input a proper Availability status");
                            //             }
                            //         }
                            //         FYPCoordinator.generateProjectReportByStatus(status); 
                            //         break;
                            //     }
                            // }
                            // break;

                            // case(9):
                            // if(!currentUser.isSupervisor()){
                            //     System.out.println("Please choose a valid option!");
                            // }
                            // else{
                            //     Project.displayAllProjects();
                            // }
                            // break;

                            // case(10):
                            // if(!currentUser.isSupervisor()){
                            //     System.out.println("Please choose a valid option!");
                            // }
                            // else{
                            //     DisplayAll.displayRequestHistory(); //This one I'm not sure, pls cross check
                            // }
                            // break;

                            default:
                            System.out.println("Please choose a valid option!");
                            break;
                        }
                    }
                    break;
                    

                }
                
                break;
            }
                


            }
        }
    }
