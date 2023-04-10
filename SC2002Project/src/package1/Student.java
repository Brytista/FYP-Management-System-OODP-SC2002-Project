package package1;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends User {

    private StudentRequest requestType;
    private StudentRequestWithString requestTypeWithString;
    private Project project;

    private static List<Student> students = new ArrayList<>();
    private static List<String> availableRequests = new ArrayList<>(
            Arrays.asList("1. Project Allocation", "2. Change Project Title", "3. Deregistration"));

    Scanner scanner = new Scanner(System.in);

    // Constructor
    public Student(String userID, String password, String name, String email) {
        super(userID, password, name, email);
        addToStudentsList(this); // add student to students list
    }

    // displayProjects(): displays all projects
    public void displayProjects() {
        try {
            if (this.isProjectAssigned()) {
                System.out.println(project.getProjectID() + " " + project.getProjectTitle());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return;
    }

    // displayAvailableRequests(): displays all available requests
    public static void displayAvailableRequests() {
        try {
            for (String request : availableRequests) {
                System.out.println(request);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static int addAvailableRequest(String request) {
        if (request == null) {
            System.err.println("Error: Request cannot be null");
            return 0;
        }
        if (availableRequests.contains(request)) {
            System.err.println("Error: Request already exists");
            return 0;
        }
        availableRequests.add(request);
        return 1;
    }



    // chooseAndSetRequest(): sets the request type based on user selection
    @Override
    public int chooseAndSetRequest(int requestID) {

        try {
            switch (requestID) {
                case 1:
                    this.requestType = new RequestProjectAllocation(null, null, null);
                    this.requestTypeWithString = null;
                    break;
                case 2:
                    this.requestType = null;
                    this.requestTypeWithString = new RequestChangeProjectTitle(null, null, null, null);
                    break;
                case 3:
                    this.requestType = new RequestDeregistration(null, null, null);
                    this.requestTypeWithString = null;
                    break;
                default:
                    System.err.println("Error: Invalid request ID");
                    return 0;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // getNewProjectTitle(): gets the new project title from the user
       // getNewProjectTitle(): gets the new project title from the user
       public String getNewProjectTitle() {
        try {
            System.out.println("Enter new project title: ");
            String newProjectTitle = scanner.nextLine();
            return newProjectTitle;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }


    // makeRequest(): makes a request to a supervisor; 1 returned if successful,
    public int makeRequest(String recipientID, int requestID, int projectID) {

        try {
            Project project = selectProject(projectID);
            Supervisor recipient = selectRecipient(recipientID);

            if (project == null || recipient == null) {
                System.err.println("Error: Invalid project or recipient ID");
                return 0;
            }

            if (this.requestType != null) {

                this.requestType.create(this, recipient, project);
                if (this.requestType.sendRequest() == 1) {
                    return 1;
                } // request is sent
                else {
                    return 0; // request not sent
                }

            } else if (this.requestTypeWithString != null) {

                String newProjectTitle = getNewProjectTitle();
                this.requestTypeWithString.create(this, recipient, project, newProjectTitle);

                if (this.requestTypeWithString.sendRequest() == 1) {
                    return 1; // request is sent
                } else {
                    return 0; // request not sent
                }

            } else {
                System.err.println("Error: No request type selected");
                return 0;
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    }

    // selectProject(): returns the project with the selected ID
private Project selectProject(int projectID) {
    try {
        return Project.getProjectByID(projectID);
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        return null;
    }
}

// selectRecipient(): returns the supervisor with the selected ID
private Supervisor selectRecipient(String supervisorID) {
    try {
        return Supervisor.getSupervisorByID(supervisorID);
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        return null;
    }
}

// viewAllProjects(): displays all available projects
public void viewAllProjects() {
    try {
        Project.displayAvailableProjects();
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
    }
}

// isProjectAssigned(): returns true if the student has an assigned project
public boolean isProjectAssigned() {
    try {
        return this.project != null;
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        return false;
    }
}



    // changeProject(): changes the student's project; 1 returned if successful
    public int changeProject(Project newProject) {
        try {
            if (!this.isProjectAssigned()) {
              throw new Exception("Error: Student has no assigned project");
        } else {
            project = newProject;
        }
        return 1;
    } catch (Exception e) {
        System.err.println(e.getMessage());
        return 0;
    }
}


    // removeProject(): removes the student's project
    public int removeProject() {
        try {
            this.project = null;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

        // displayAllStudents(): displays all students
        public void displayAllStudents() {
            try {
                for (Student student : students) {
                    System.out.println(student.getUserID() + " " + student.getUserName());
            }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

    
        // getStudentByName(): returns the student(s) with the selected name
        public static List<Student> getStudentByName(String name) {
            List<Student> returnStudents = new ArrayList<>();
            try {
                for (Student student : students) {
                    if (student.getUserName().equals(name)) {
                        returnStudents.add(student);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            return returnStudents;
        }
    
    
        // getStudentByID(): returns the student with the selected ID
        public static Student getStudentByID(String userID) {
            Student returnStudent = null;
            try {
                for (Student student : students) {
                    if (student.getUserID().equals(userID)) {
                        returnStudent = student;
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            return returnStudent;
        }
    
    

    // addInitialStudents(): adds a list of students to the students List object
    public int addInitialStudents(List<Student> studentsList) {

        try {
            for (Student student : studentsList) {
                students.add(student);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // addToStudentsList(): adds a student to the students List object
    public static int addToStudentsList(Student student) {

        try {
            students.add(student);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // removeFromStudentsList(): removes a student from the students List object
    public int removeFromStudentsList(Student student) {

        try {
            students.remove(student);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;
    }

    // isStudent(): confirms whether the user belongs to a Student class
    public boolean isStudent() {
        return true;
    }

    

    public static int loginStudent(String userID, String password) {
        try {
            for (Student student : students) {
                if (student.getUserID().equals(userID)) {
                    if (student.getPassword().equals(password)) {
                        // User exists and password is correct
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
}
