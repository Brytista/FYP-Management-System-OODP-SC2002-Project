
import java.util.*;

import package1.InitializeApplication;
import package1.DataManager;
import package1.Student;
import package1.Supervisor;
import package1.FYPCoordinator;
import package1.Project;
import package1.ProjectStatus;

public class App {
    public static void main(String[] args) {
        // Put the read file
        Scanner sc = new Scanner(System.in);
        int quit = 0;
        InitializeApplication.assignAllLists();

        while (quit == 0) {
            // FIRST ()
            if (quit == 1)
                break;
            try {
                System.out.println("1. Exit");
                System.out.println("2. Register");
                System.out.println("3. Login");
                System.out.print("Enter your choice: ");
                int option1;
                try {
                    option1 = sc.nextInt();
                    sc.nextLine();
                    // do something with option1
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    sc.nextLine(); // consume invalid input
                    continue;
                    // handle the error or retry input
                }

                Student currentStudent = null;
                Supervisor currentSupervisor = null;
                FYPCoordinator currentFYPCoordinator = null;
                switch (option1) {
                    case (1):

                        DataManager.updateAll();

                        quit = 1;
                        break; // exit

                    case (2):
                        int register = 0;
                        while (register == 0) {
                            System.out.println("Enter your name: ");
                            String newName = sc.nextLine();
                            System.out.println("Enter your userID: ");
                            String newUsername = sc.nextLine();
                            System.out.println("Enter your password");
                            String newPassword = sc.nextLine();
                            System.out.println(
                                    "Press 1 to register as a student, 2 to register as a supervisor, 3 to register as a FYPCoordinator, 4 to quit");
                            String newRole = sc.nextLine();
                            try {
                                switch (newRole) {
                                    case ("1"):
                                        Student newStudent = Student.createStudent(newUsername, newPassword, newName,
                                                newUsername + "@e.ntu.edu.sg");
                                        // Add student to the student file
                                        if (newStudent != null)
                                            register = 1;
                                        break;
                                    case ("2"):
                                        Supervisor newSupervisor = Supervisor.createSupervisor(newUsername, newPassword,
                                                newName, newUsername + "@ntu.edu.sg");
                                        // Add supervisor to the supervisor file
                                        if (newSupervisor != null)
                                            register = 1;
                                        break;
                                    case ("3"):
                                        FYPCoordinator newFYPCoordinator = FYPCoordinator.createFYPCoordinator(
                                                newUsername, newPassword, newName, newUsername + "@ntu.edu.sg");
                                        // Add FYPCoordinator to the FYPCoordinator file
                                        if (newFYPCoordinator != null)
                                            register = 1;
                                        break;
                                    case ("4"):
                                        register = 1;
                                        break;
                                    default:
                                        System.out.println("Your role is invalid");
                                        break;
                                }
                            } catch (Exception e) {
                                System.out.println("An error occurred during registration: " + e.getMessage());
                            }
                        }
                        break; // end of case 2

                    case (3):
                        boolean logging = true;
                        while (logging) {
                            try {
                                System.out.println("Enter your userID: ");
                                String username = sc.nextLine();
                                System.out.println("Enter your password");
                                String password = sc.nextLine();
                                System.out.println("1. Student");
                                System.out.println("2. Supervisor");
                                System.out.println("3. FYPCoordinator");
                                System.out.print("Enter your user type: ");
                                int type = 0;
                                try {
                                    type = sc.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter an integer value.");
                                    sc.nextLine(); // Clear the scanner buffer
                                    continue;
                                }
                                sc.nextLine();
                                switch (type) {
                                    case (1):
                                        try {
                                            int loginResult = Student.loginStudent(username, password);
                                            if (loginResult == 1) {
                                                currentStudent = Student.getStudentByID(username);
                                                if (currentStudent != null) {
                                                    currentStudent.login();
                                                    logging = false;
                                                    System.out.println("--------------------");
                                                    System.out.println(
                                                            "Welcome, Student " + currentStudent.getUserName());
                                                    System.out.println("--------------------");
                                                } else {
                                                    System.out.println("--------------------");
                                                    System.out.println(
                                                            "Error: Unable to find student with ID " + username);
                                                    System.out.println("--------------------");
                                                }
                                            } else {
                                                System.out.println(
                                                        "Your username, password, and type do not match. You may have entered the wrong type.");
                                            }
                                        } catch (Exception e) {
                                            System.out.println(
                                                    "An error occurred during student login: " + e.getMessage());
                                        }
                                        break;
                                    case (2):
                                        try {
                                            int supervisorLoginResult = Supervisor.loginSupervisor(username, password);
                                            if (supervisorLoginResult == 1) {
                                                currentSupervisor = Supervisor.getSupervisorByID(username);
                                                if (currentSupervisor != null) {
                                                    currentSupervisor.login();
                                                    logging = false;
                                                    System.out.println("--------------------");
                                                    System.out.println(
                                                            "Welcome, Supervisor " + currentSupervisor.getUserName());
                                                    System.out.println("--------------------");
                                                } else {
                                                    System.out.println("--------------------");
                                                    System.out.println(
                                                            "Error: Unable to find supervisor with ID " + username);
                                                    System.out.println("--------------------");
                                                }
                                            } else {
                                                System.out.println(
                                                        "Your username, password, and type do not match. You may have entered the wrong type.");
                                            }
                                        } catch (Exception e) {
                                            System.out.println(
                                                    "An error occurred during supervisor login: " + e.getMessage());
                                        }
                                        break;
                                    case (3):
                                        try {
                                            int fypCoordinatorLoginResult = FYPCoordinator.loginFYPCoordinator(username,
                                                    password);
                                            if (fypCoordinatorLoginResult == 1) {
                                                currentFYPCoordinator = FYPCoordinator.getCoordinatorByID(username);
                                                if (currentFYPCoordinator != null) {
                                                    currentFYPCoordinator.login();
                                                    logging = false;
                                                    System.out.println("--------------------");
                                                    System.out.println("Welcome, FYP Coordinator "
                                                            + currentFYPCoordinator.getUserName());
                                                    System.out.println("--------------------");
                                                } else {
                                                    System.out.println("--------------------");
                                                    System.out.println("Error: Unable to find FYP coordinator with ID "
                                                            + username);
                                                    System.out.println("--------------------");
                                                }
                                            } else {
                                                System.out.println(
                                                        "Your username, password, and type do not match. You may have entered the wrong type.");
                                            }

                                        } catch (Exception e) {
                                            System.out.println("An error occurred during FYP coordinator login: "
                                                    + e.getMessage());
                                        }
                                        break;
                                    default:
                                        System.out.println("Please choose an appropriate user type");
                                        break;
                                }
                            } catch (Exception e) {
                                System.out.println("An error occurred during the login process: " + e.getMessage());
                            }
                        } // DONE

                        while (currentStudent != null && currentStudent.isLoggedIn()) {
                            System.out.println("");
                            System.out.println("--------------------");
                            System.out.println("List of Available Actions: ");
                            System.out.println("1. Send Request");
                            System.out.println("2. See all available Projects");
                            System.out.println("3. See my Project");
                            System.out.println("4. Change Password");
                            System.out.println("5. Check my Request History");
                            System.out.println("6. Logout");
                            System.out.println("--------------------");
                            System.out.println("Enter your choice: ");
                            int option2 = 6;
                            try {
                                option2 = sc.nextInt();
                                sc.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Your input is invalid!");
                                sc.nextLine(); // Clear the scanner buffer
                                continue;
                            }

                            switch (option2) {
                                case (1):
                                    String recipientID;
                                    Project project;
                                    int projectID;
                                    System.out.println("Choose a request type: ");
                                    Student.displayAvailableRequests();
                                    System.out.println("4. Cancel");
                                    int requestID;
                                    try {
                                        requestID = sc.nextInt();
                                        sc.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a valid request type.");
                                        sc.nextLine();
                                        break;
                                    }
                                    if (requestID == 4)
                                        break;
                                    while (currentStudent.chooseAndSetRequest(requestID) == 0) {
                                        if (requestID == 4)
                                            break;
                                        System.out.println("Choose a request type: ");
                                        try {
                                            requestID = sc.nextInt();
                                            sc.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a valid request type.");
                                            sc.nextLine();
                                        }
                                    }
                                    if (requestID == 4)
                                        break;

                                    switch (requestID) {
                                        case (1):
                                            if (currentStudent.getIsDeregistered()) {
                                                System.out.println(
                                                        "You are not allowed to make selection again as you deregistered your FYP");
                                                break;
                                            }
                                            System.out.println("--------------------");
                                            System.out.println("List of all possible Projects:");
                                            currentStudent.viewAllProjects();
                                            System.out.println("");
                                            System.out.println("Input projectID:");
                                            try {
                                                projectID = sc.nextInt();
                                                sc.nextLine();
                                                while (Project.getProjectByID(projectID).getProjectStatus() != ProjectStatus.AVAILABLE) {
                                                    System.out.println(
                                                            "Invalid input. Please enter a valid projectID. It must be an available project from the list!");
                                                    projectID = sc.nextInt();
                                                    sc.nextLine();
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println("Invalid input. Please enter a valid projectID.");
                                                sc.nextLine();
                                                break;
                                            }
                                            project = Project.getProjectByID(projectID);
                                            System.out.println(
                                                    "List of possible recipients of the request (FYP Coordinators):");
                                            FYPCoordinator.displayAllCoordinators();
                                            System.out.println("");
                                            System.out.println("Input recipientID:");
                                            recipientID = sc.nextLine();
                                            while (FYPCoordinator.getCoordinatorByID(recipientID) == null) {
                                                System.out.println(
                                                        "Invalid input. Please enter a valid recipientID. It must be a FYP Coordinator from the list!");
                                                recipientID = sc.nextLine();
                                            }
                                            while (currentStudent.makeRequest(recipientID, projectID) == 0) {
                                                System.out.println("Input projectID:");
                                                try {
                                                    projectID = sc.nextInt();
                                                    System.out.println("");
                                                    System.out.println("Input recipientID:");
                                                    recipientID = sc.nextLine();
                                                    while (FYPCoordinator.getCoordinatorByID(recipientID) == null) {
                                                        System.out.println(
                                                                "Invalid input. Please enter a valid recipientID. It must be a FYP Coordinator from the list!");
                                                        recipientID = sc.nextLine();
                                                    }
                                                } catch (InputMismatchException e) {
                                                    System.out
                                                            .println("Invalid input. Please enter a valid projectID.");
                                                    sc.nextLine();
                                                }
                                            }
                                            System.out.println("Request is sent");
                                            break;

                                        case (2):
                                            projectID = currentStudent.getProjectID();
                                            project = Project.getProjectByID(projectID);
                                            recipientID = project.getSupervisor().getUserID();
                                            while (currentStudent.makeRequest(recipientID, projectID) == 0) {
                                                // empty loop
                                            }
                                            System.out.println("Request is sent");
                                            break;

                                        case (3):
                                            projectID = currentStudent.getProjectID();
                                            System.out.println(
                                                    "List of possible recipients of the request (FYP Coordinators):");
                                            FYPCoordinator.displayAllCoordinators();
                                            System.out.println("");
                                            System.out.println("Input recipientID:");
                                            recipientID = sc.nextLine();
                                            while (FYPCoordinator.getCoordinatorByID(recipientID) == null) {
                                                System.out.println(
                                                        "Invalid input. Please enter a valid recipientID. It must be a FYP Coordinator from the list!");
                                                recipientID = sc.nextLine();
                                            }
                                            System.out.println("Are you sure to deregister?");
                                            System.out.println(
                                                    "Press 1 to cancel deregistration. Press any other number to continue.");
                                            int choice;
                                            try {
                                                choice = sc.nextInt();
                                                sc.nextLine();
                                            } catch (InputMismatchException e) {
                                                System.out.println("Invalid input. Please enter a valid choice.");
                                                sc.nextLine();
                                                break;
                                            }
                                            if (choice == 1) {
                                                System.out.println("Request is cancelled");
                                                break;
                                            }
                                            while (currentStudent.makeRequest(recipientID, projectID) == 0) {
                                                System.out.println(
                                                        "List of possible recipients of the request (FYP Coordinators):");
                                                FYPCoordinator.displayAllCoordinators();
                                                System.out.println("");
                                                System.out.println("Input recipientID:");
                                                recipientID = sc.nextLine();
                                                while (FYPCoordinator.getCoordinatorByID(recipientID) == null) {
                                                    System.out.println(
                                                            "Invalid input. Please enter a valid recipientID. It must be a FYP Coordinator from the list!");
                                                    recipientID = sc.nextLine();
                                                }
                                            }
                                            System.out.println("Request is sent");
                                            break;
                                        case (4):
                                            break;
                                        default:
                                            System.out.println("Your input is invalid!");
                                            break;
                                    }
                                    break;
                                case (2):
                                    if (currentStudent.isProjectAssigned() == false) {
                                        currentStudent.viewAllProjects();
                                    } else {
                                        System.out.println("");
                                        System.out.println(
                                                "You Cannot view all projects, as you are assigned a project. Instead, here is your project:");
                                        currentStudent.displayProjects();
                                    }
                                    break;
                                case (3):
                                    currentStudent.displayProjects();
                                    break;

                                case (4):
                                    boolean cancel = false;
                                    while (!cancel) {
                                        System.out.println("Type \"1\" to cancel. Enter your old password: ");
                                        String oldPass = sc.nextLine();
                                        if (oldPass.equals("1"))
                                            cancel = true;

                                        else if (oldPass.equals(currentStudent.getPassword())) {
                                            break;
                                        } else {
                                            System.out.println("Wrong old password, try again");
                                        }
                                    }
                                    if (cancel)
                                        break;
                                    String newPass;
                                    System.out.println("Enter a new password: ");
                                    newPass = sc.nextLine();
                                    currentStudent.changePassword(newPass);
                                    System.out.println(
                                            "Your password have been changed to " + currentStudent.getPassword());
                                    System.out.println(
                                            "You will be logged out now. Please login again with your new password to continue.");
                                    currentStudent.logout();
                                    break;

                                case (5):
                                    currentStudent.displayRequestHistory();
                                    break;

                                case (6):
                                    currentStudent.logout();
                                    break;

                                default:
                                    System.out.println("Please choose a valid option!");
                                    break;

                            }
                        } // DONE

                        // SUPERVISOR
                        while (currentSupervisor != null && currentSupervisor.isLoggedIn()) {
                            System.out.println("--------------------");
                            System.out.println("List of Available Actions: ");
                            System.out.println("1. Send Request");
                            System.out.println("2. See all my Projects");
                            System.out.println("3. Change Password");
                            System.out.println("4. Check my Request History");
                            System.out.println("5. Check Inbox");
                            System.out.println("6. Create Project");
                            System.out.println("7. Modify Project Title");
                            System.out.println("8. Logout");
                            System.out.println("--------------------");
                            System.out.println("");
                            System.out.println("Please choose an option: ");
                            int option3;
                            try {
                                option3 = sc.nextInt();
                                sc.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid choice.");
                                sc.nextLine();
                                continue;
                            }
                            switch (option3) {
                                case (1):
                                    System.out.println("Choose a request type: ");
                                    Supervisor.displayAvailableRequests();
                                    System.out.println("2. Cancel");
                                    int requestID;
                                    try {
                                        requestID = sc.nextInt();
                                        sc.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a valid choice.");
                                        sc.nextLine();
                                        break;
                                    }
                                    if (requestID == 2)
                                        break;
                                    while (currentSupervisor.chooseAndSetRequest(requestID) == 0) {
                                        if (requestID == 2)
                                            break;
                                        System.out.println("Choose a request type: ");
                                        try {
                                            requestID = sc.nextInt();
                                            sc.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a valid choice.");
                                            sc.nextLine();
                                            break;
                                        }
                                    }
                                    if (requestID == 2)
                                        break;
                                    if (!currentSupervisor.doSupervisorHaveProject()) {
                                        System.out.println("You do not have any project assigned to you!");
                                        break;
                                    }

                                    System.out.println("List of all possible FYP Coordinator:");
                                    FYPCoordinator.displayAllCoordinators();
                                    System.out.println("");
                                    System.out.print("Input recipient ID (FYP Coordinator ID): ");
                                    String recipientFYPCoordinatorID = sc.nextLine();
                                    while (FYPCoordinator.getCoordinatorByID(recipientFYPCoordinatorID) == null) {
                                        System.out.println(
                                                "Invalid input. Please enter a valid recipientID. It must be a FYP Coordinator from the list!");
                                        recipientFYPCoordinatorID = sc.nextLine();
                                    }
                                    System.out.println("List of all my Projects:");
                                    currentSupervisor.displayProjects();
                                    System.out.println("");
                                    System.out.println("Input ID of projects you wanted to change: ");
                                    int projectID;
                                    try {
                                        projectID = sc.nextInt();
                                        sc.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a valid project ID.");
                                        sc.nextLine();
                                        break;
                                    }
                                    while (!currentSupervisor
                                            .doesProjectBelongToSupervisor(Project.getProjectByID(projectID))) {
                                        System.out.println("Only select projects that you are supervising!");
                                        System.out.println("Input ID of projects you wanted to change: ");
                                        try {
                                            projectID = sc.nextInt();
                                            sc.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a valid project ID.");
                                            sc.nextLine();
                                            break;
                                        }
                                    }

                                    System.out.println("List of all Supervisors");
                                    Supervisor.displayAllSupervisor();
                                    System.out.println("");
                                    System.out.println("Input replacement Supervisor ID");
                                    String replacementID = sc.nextLine();

                                    // Iterate until the request is sent
                                    while (currentSupervisor.makeRequest(recipientFYPCoordinatorID, projectID,
                                            replacementID) == 0) {
                                        System.out.println("Input recipient ID (FYP Coordinator ID): ");
                                        recipientFYPCoordinatorID = sc.nextLine();
                                        while (FYPCoordinator.getCoordinatorByID(recipientFYPCoordinatorID) == null) {
                                            System.out.println(
                                                    "Invalid input. Please enter a valid recipientID. It must be a FYP Coordinator from the list!");
                                            recipientFYPCoordinatorID = sc.nextLine();
                                        }
                                        System.out.println("");
                                        System.out.println("Input ID of projects you want to change: ");
                                        try {
                                            projectID = sc.nextInt();
                                            sc.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a valid project ID.");
                                            sc.nextLine();
                                            break;
                                        }
                                        System.out.println("");
                                        System.out.println("Input replacement Supervisor ID");
                                        replacementID = sc.nextLine();
                                        while (Supervisor.getSupervisorByID(replacementID) == null) {
                                            System.out.println(
                                                    "Invalid input. Please enter a valid replacementID. It must be a Supervisor from the list!");
                                            replacementID = sc.nextLine();
                                        }
                                    }
                                    System.out.println("Request is sent");
                                    break;

                                case (2):
                                    currentSupervisor.displayProjects();
                                    break;

                                case (3): // Change Password
                                    boolean cancel1 = false;
                                    // Iterate until the old password is correct or the user cancels
                                    while (!cancel1) {
                                        System.out.println("Type \"1\" to cancel. Enter your old password: ");
                                        String oldPass = sc.nextLine();
                                        if (oldPass.equals("1"))
                                            cancel1 = true;
                                        else if (oldPass.equals(currentSupervisor.getPassword())) {
                                            break;
                                        } else {
                                            System.out.println("Wrong old password, try again");
                                        }
                                    }

                                    if (cancel1)
                                        break;
                                    String newPass = null;
                                    // Iterate until the new password is not "cancel"

                                    System.out.println("Enter a new password: ");
                                    newPass = sc.nextLine();

                                    currentSupervisor.changePassword(newPass);
                                    System.out.println(
                                            "Your password have been changed to " + currentSupervisor.getPassword());
                                    System.out.println(
                                            "You will be logged out now. Please login again with your new password to continue.");
                                    currentSupervisor.logout();
                                    break;

                                case (4):
                                    currentSupervisor.displayRequestHistory();
                                    break;

                                case (5):
                                    currentSupervisor.viewAllPendingRequest();
                                    break;

                                case (6):
                                    System.out.println("Enter new project title: ");
                                    String newTitle = sc.nextLine();
                                    if (currentSupervisor.createProject(newTitle) == 0) {
                                        System.out.println("Project creation failed");
                                    }
                                    ;
                                    System.out.println("Project creation successful");
                                    break;

                                case (7):
                                    // code to allow modification of project title
                                    if (!currentSupervisor.doSupervisorHaveProject()) {
                                        System.out.println("You do not have any project!");
                                        break;
                                    }
                                    System.out.println("List of all my Projects:");
                                    currentSupervisor.displayProjects();
                                    System.out.println("");
                                    System.out.println("Input ID of projects you wanted to change: ");
                                    int projectID2;
                                    try {
                                        projectID2 = sc.nextInt();
                                        sc.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a valid project ID.");
                                        sc.nextLine();
                                        break;
                                    }
                                    while (!currentSupervisor
                                            .doesProjectBelongToSupervisor(Project.getProjectByID(projectID2))) {
                                        System.out.println("Only select projects that you are supervising!");
                                        System.out.println("Input ID of projects you wanted to change: ");
                                        try {
                                            projectID2 = sc.nextInt();
                                            sc.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a valid project ID.");
                                            sc.nextLine();
                                            break;
                                        }
                                    }
                                    System.out.println("Enter new project title: ");
                                    String newTitle2 = sc.nextLine();
                                    if (currentSupervisor.modifyProjectTitle(projectID2, newTitle2) == 0) {
                                        System.out.println("Project modification failed");
                                    }
                                    ;
                                    System.out.println("Project modification successful");
                                    break;

                                case (8):
                                    currentSupervisor.logout();
                                    break;

                                default:
                                    System.out.println("Please choose a valid option!");
                                    break;
                            }
                        } // DONE

                        // FYPCoordinator
                        while (currentFYPCoordinator != null && currentFYPCoordinator.isLoggedIn()) {
                            System.out.println("--------------------");
                            System.out.println("List of Available Actions: ");
                            System.out.println("1. Check my Request History");
                            System.out.println("2. Check Inbox");
                            System.out.println("3. Generate Project Report");
                            System.out.println("4. View all projects");
                            System.out.println("5. Change Password");
                            System.out.println("6. Send Request");
                            System.out.println("7. See all my Projects");
                            System.out.println("8. Create Project");
                            System.out.println("9. Modify Project Title");
                            System.out.println("10. Logout");
                            System.out.println("--------------------");
                            System.out.println("");
                            ;

                            System.out.print("Choose an option: ");
                            int option3 = 0;
                            try {
                                option3 = sc.nextInt();
                                sc.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid choice.");
                                sc.nextLine();
                                continue;
                            }
                            switch (option3) {
                                case (1):
                                    currentFYPCoordinator.displayRequestHistory();
                                    break;

                                case (2): // Check Inbox
                                    currentFYPCoordinator.viewAllPendingRequest();
                                    break;

                                case (3): // Generate Project Report
                                    System.out.println("Which filter do you want to apply to select the report?");
                                    System.out
                                            .println("1. Project Status (AVAILABLE, RESERVED, UNAVAILABLE, ALLOCATED)");
                                    System.out.println("2. Supervisor ID (Enter the ID of the supervisor Later)");
                                    System.out.println("3. Student ID (Enter the ID of the Student Later)");
                                    int option4;
                                    try {
                                        option4 = sc.nextInt();
                                        sc.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a valid choice.");
                                        sc.nextLine();
                                        continue;
                                    }
                                    switch (option4) {
                                        case (1):
                                            ProjectStatus status;
                                            while (true) {
                                                System.out.println(
                                                        "Filter the report by Project Status (enter the appropriate number): ");
                                                System.out.println("1. AVAILABLE");
                                                System.out.println("2. RESERVED");
                                                System.out.println("3. UNAVAILABLE");
                                                System.out.println("4. ALLOCATED");
                                                int sortByAvailability;
                                                try {
                                                    sortByAvailability = sc.nextInt();
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Invalid input. Please enter a valid choice.");
                                                    sc.nextLine();
                                                    continue;
                                                }
                                                if (sortByAvailability == 1) {
                                                    status = ProjectStatus.AVAILABLE;
                                                    break;
                                                } else if (sortByAvailability == 2) {
                                                    status = ProjectStatus.RESERVED;
                                                    break;
                                                } else if (sortByAvailability == 3) {
                                                    status = ProjectStatus.UNAVAILABLE;
                                                    break;
                                                } else if (sortByAvailability == 4) {
                                                    status = ProjectStatus.ALLOCATED;
                                                    break;
                                                } else {
                                                    System.out.println(
                                                            "Please input a proper Availability status (Integer from 1 to 4)");
                                                }
                                            }
                                            currentFYPCoordinator.generateProjectReportByStatus(status);
                                            break;

                                        case (2):
                                            System.out.println("--------------------");
                                            System.out.println("List of Supervisor:");
                                            Supervisor.displayAllSupervisor();
                                            System.out.println("Enter Supervisor ID: ");
                                            String sortByName = sc.nextLine();
                                            currentFYPCoordinator.generateProjectReportBySupervisorID(sortByName);
                                            break;

                                        case (3):
                                            System.out.println("--------------------");
                                            System.out.println("List of Student:");
                                            Student.displayAllStudents();
                                            System.out.println("Enter Student ID: ");
                                            String sortByStudentID = sc.nextLine();
                                            currentFYPCoordinator.generateProjectReportByStudentID(sortByStudentID);
                                            break;

                                        default:
                                            System.out.println("Please choose a valid option!");
                                            break;
                                    }
                                    break;

                                case (4):
                                    currentFYPCoordinator.viewAllProjects();
                                    break;

                                case (5):
                                    boolean cancel1 = false;
                                    // Iterate until the old password is correct or the user cancels
                                    while (!cancel1) {
                                        System.out.println("Type \"1\" to cancel. Enter your old password: ");
                                        String oldPass = sc.nextLine();
                                        if (oldPass.equals("1"))
                                            cancel1 = true;
                                        else if (oldPass.equals(currentFYPCoordinator.getPassword())) {
                                            break;
                                        } else {
                                            System.out.println("Wrong old password, try again");
                                        }
                                    }
                                    if (cancel1)
                                        break;
                                    String newPass = null;
                                    // Iterate until the new password is not "cancel"
                                    System.out.println("Enter a new password: ");
                                    newPass = sc.nextLine();
                                    currentFYPCoordinator.changePassword(newPass);
                                    System.out.println("Your password have been changed to "
                                            + currentFYPCoordinator.getPassword());
                                    System.out.println(
                                            "You will be logged out now. Please login again with your new password to continue.");
                                    currentFYPCoordinator.logout();
                                    break;

                                case (6): // Send Request (1)
                                    System.out.println("Choose a request type: ");
                                    Supervisor.displayAvailableRequests();
                                    System.out.println("2. Cancel");
                                    int requestID;
                                    try {
                                        requestID = sc.nextInt();
                                        sc.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a valid choice.");
                                        sc.nextLine();
                                        break;
                                    }
                                    if (requestID == 2)
                                        break;
                                    while (currentFYPCoordinator.chooseAndSetRequest(requestID) == 0) {
                                        if (requestID == 2)
                                            break;
                                        System.out.println("Choose a request type: ");
                                        try {
                                            requestID = sc.nextInt();
                                            sc.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a valid choice.");
                                            sc.nextLine();
                                            break;
                                        }
                                    }
                                    if (requestID == 2)
                                        break;
                                    if (!currentFYPCoordinator.doSupervisorHaveProject()) {
                                        System.out.println("You do not have any project assigned to you!");
                                        break;
                                    }

                                    System.out.println("List of all possible FYP Coordinator:");
                                    FYPCoordinator.displayAllCoordinators();
                                    System.out.println("");
                                    System.out.print("Input recipient ID (FYP Coordinator ID): ");
                                    String recipientFYPCoordinatorID = sc.nextLine();
                                    while (FYPCoordinator.getCoordinatorByID(recipientFYPCoordinatorID) == null) {
                                        System.out.println(
                                                "Invalid input. Please enter a valid recipientID. It must be a FYP Coordinator from the list!");
                                        recipientFYPCoordinatorID = sc.nextLine();
                                    }
                                    System.out.println("List of all my Projects:");
                                    currentFYPCoordinator.displayProjects();
                                    System.out.println("");
                                    System.out.println("Input ID of projects you wanted to change: ");
                                    int projectID;
                                    try {
                                        projectID = sc.nextInt();
                                        sc.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a valid project ID.");
                                        sc.nextLine();
                                        break;
                                    }
                                    while (!currentFYPCoordinator
                                            .doesProjectBelongToSupervisor(Project.getProjectByID(projectID))) {
                                        System.out.println("Only select projects that you are supervising!");
                                        System.out.println("Input ID of projects you wanted to change: ");
                                        try {
                                            projectID = sc.nextInt();
                                            sc.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a valid project ID.");
                                            sc.nextLine();
                                            break;
                                        }
                                    }

                                    System.out.println("List of all Supervisors");
                                    Supervisor.displayAllSupervisor();
                                    System.out.println("");
                                    System.out.println("Input replacement Supervisor ID");
                                    String replacementID = sc.nextLine();

                                    // Iterate until the request is sent
                                    while (currentFYPCoordinator.makeRequest(recipientFYPCoordinatorID, projectID,
                                            replacementID) == 0) {
                                        System.out.println("Input recipient ID (FYP Coordinator ID): ");
                                        recipientFYPCoordinatorID = sc.nextLine();
                                        while (FYPCoordinator.getCoordinatorByID(recipientFYPCoordinatorID) == null) {
                                            System.out.println(
                                                    "Invalid input. Please enter a valid recipientID. It must be a FYP Coordinator from the list!");
                                            recipientFYPCoordinatorID = sc.nextLine();
                                        }
                                        System.out.println("");
                                        System.out.println("Input ID of projects you want to change: ");
                                        try {
                                            projectID = sc.nextInt();
                                            sc.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a valid project ID.");
                                            sc.nextLine();
                                            break;
                                        }
                                        System.out.println("");
                                        System.out.println("Input replacement Supervisor ID");
                                        replacementID = sc.nextLine();
                                        while (Supervisor.getSupervisorByID(replacementID) == null) {
                                            System.out.println(
                                                    "Invalid input. Please enter a valid replacement ID. It must be a Supervisor from the list!");
                                            replacementID = sc.nextLine();
                                        }
                                    }
                                    System.out.println("Request is sent");
                                    break;

                                case (7): // See all my Projects (2)
                                    currentFYPCoordinator.displayProjects();
                                    break;

                                case (8): // Create Project (6)
                                    System.out.println("Enter new project title: ");
                                    String newTitle = sc.nextLine();
                                    if (currentFYPCoordinator.createProject(newTitle) == 0) {
                                        System.out.println("Project creation failed");
                                    }
                                    ;
                                    System.out.println("Project creation successful");
                                    break;

                                case (9): // Modify Project Title (7)
                                    // code to allow modification of project title
                                    if (!currentFYPCoordinator.doSupervisorHaveProject()) {
                                        System.out.println("You do not have any project!");
                                        break;
                                    }
                                    System.out.println("List of all my Projects:");
                                    currentFYPCoordinator.displayProjects();
                                    System.out.println("");
                                    System.out.println("Input ID of projects you wanted to change: ");
                                    int projectID2;
                                    try {
                                        projectID2 = sc.nextInt();
                                        sc.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a valid project ID.");
                                        sc.nextLine();
                                        break;
                                    }
                                    while (!currentFYPCoordinator
                                            .doesProjectBelongToSupervisor(Project.getProjectByID(projectID2))) {
                                        System.out.println("Only select projects that you are supervising!");
                                        System.out.println("Input ID of projects you wanted to change: ");
                                        try {
                                            projectID2 = sc.nextInt();
                                            sc.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a valid project ID.");
                                            sc.nextLine();
                                            break;
                                        }
                                    }
                                    System.out.println("Enter new project title: ");
                                    String newTitle2 = sc.nextLine();
                                    if (currentFYPCoordinator.modifyProjectTitle(projectID2, newTitle2) == 0) {
                                        System.out.println("Project modification failed");
                                    }
                                    ;
                                    System.out.println("Project modification successful");
                                    break;

                                case (10):
                                    currentFYPCoordinator.logout();
                                    break;

                                default:
                                    System.out.println("Please choose a valid option!");
                                    break;
                            }
                        } // DONE
                        break; // End of case 3
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

    }
}
