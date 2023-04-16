package package1;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;

/**
 * The InitializeApplication class is responsible for loading data from CSV
 * files
 * and initializing the application with the loaded data. It reads data for
 * students,
 * supervisors, FYP coordinators, and projects from their respective CSV files
 * and
 * assigns the loaded data to the corresponding classes.
 *
 * The class provides methods for loading each type of data individually and a
 * method
 * to load all data at once. Upon successful loading of data, it assigns the
 * lists to
 * their respective classes.
 */
public class InitializeApplication {

    /**
     * A static list containing all Student objects in the system.
     */
    private static List<Student> studentList = new ArrayList<>();

    /**
     * A static list containing all Supervisor objects in the system.
     */
    private static List<Supervisor> supervisorList = new ArrayList<>();

    /**
     * A static list containing all FYPCoordinator objects in the system.
     */
    private static List<FYPCoordinator> fypCoordinatorList = new ArrayList<>();

    /**
     * A static list containing all Project objects in the system.
     */
    private static List<Project> projectList = new ArrayList<>();

    private static int studentListIterCount = 0;
    private static int supervisorListIterCount = 0;

    /** Constructor for the InitializeApplication class. */
    public InitializeApplication() {
    }

    /**
     * Assigns the loaded lists to their respective classes.
     *
     * @return 1 if the lists are loaded successfully, 0 if there was an error
     */
    public static int assignAllLists() {
        int loaded = InitializeApplication.loadAllLists();

        if (loaded == 1) {
            // System.out.println("Lists loaded successfully");
            return 1;

        } else {
            System.out.println("Error: Unable to load lists");
            return 0;
        }
    }

    /**
     * Loads the student list from a CSV file.
     *
     * @return 1 if the list is loaded successfully, 0 if there was an error
     */
    public static int loadStudentList() {

        List<Student> studentListReturn = new ArrayList<>();

        // Create a BufferedReader object to read the CSV file
        String filePath = Paths.get("files/studentList.csv").toString();
        int count = 0;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            // Iterate over the remaining rows
            String row;
            while ((row = csvReader.readLine()) != null) {
                // Split the row into fields using the comma delimiter
                String[] fields = row.split(",");

                // Process the fields as needed
                String userID = fields[0];
                String password = fields[1];
                String name = fields[2];
                String email = fields[3];

                // Create a Student object and add it to the studentList
                Student student = new Student(userID, password, name, email);

                if (studentListIterCount > 0) {
                    // If projectID is greater than 0, assign the student's project
                    int projectID = Integer.parseInt(fields[4]);
                    if (projectID > 0) {
                        Project project = Project.getProjectByID(projectID);
                        student.changeProject(project);
                    }

                    // Assign the student's requestProject boolean
                    boolean requestProject = Boolean.parseBoolean(fields[5]);
                    student.setRequestProject(requestProject);

                    // Assign the student's isDeregistered boolean
                    boolean isDeregistered = Boolean.parseBoolean(fields[6]);
                    student.setIsDeregistered(isDeregistered);
                }

                studentListReturn.add(student);

                count++;
            }

            // Print a message if no student data is found in the file
            if (count == 0) {
                System.out.println("No student data found in the file");
            }

            // Close the BufferedReader object
            csvReader.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        studentList.clear();
        studentList = studentListReturn;

        studentListIterCount++;
        return 1;
    }

    /**
     * Loads the supervisor list from a CSV file.
     *
     * @return 1 if the list is loaded successfully, 0 if there was an error
     */
    public static int loadSupervisorList() {

        List<Supervisor> supervisorListReturn = new ArrayList<>();

        // Create a BufferedReader object to read the CSV file
        String filePath = Paths.get("files/supervisorList.csv").toString();
        int count = 0;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            // Iterate over the remaining rows
            String row;
            while ((row = csvReader.readLine()) != null) {
                // Split the row into fields using the comma delimiter
                String[] fields = row.split(",");

                // Process the fields as needed
                String userID = fields[0];
                String password = fields[1];
                String name = fields[2];
                String email = fields[3];

                // Create a Student object and add it to the studentList
                Supervisor supervisor = new Supervisor(userID, password, name, email);

                // Assign the supervisor's numberOfProjectManaged
                int numberOfProjectManaged = Integer.parseInt(fields[4]);
                if (numberOfProjectManaged > 0) {
                    supervisor.numberOfProjectManaged = numberOfProjectManaged;
                }

                if (supervisorListIterCount > 0) {
                    // Assign the supervisor's managed students
                    String[] retrievedStudentsManaged = fields[5].split(";");

                    if (!retrievedStudentsManaged[0].equals("None")) {
                        List<Student> studentsManaged = new ArrayList<Student>();

                        for (String studentID : retrievedStudentsManaged) {
                            Student student = Student.getStudentByID(studentID);
                            studentsManaged.add(student);
                        }

                        supervisor.assignStudentManaged(studentsManaged);
                    }
                }

                supervisorListReturn.add(supervisor);

                count++;
            }

            // Print a message if no supervisor data is found in the file
            if (count == 0) {
                System.out.println("No supervisor data found in the file");
            }

            // Close the BufferedReader object
            csvReader.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        supervisorList.clear();
        supervisorList = supervisorListReturn;

        supervisorListIterCount++;
        return 1;

    }

    /**
     * Loads the FYP Coordinator list from a CSV file.
     *
     * @return 1 if the list is loaded successfully, 0 if there was an error
     */
    public static int loadFYPCoordinatorList() {

        // Create a BufferedReader object to read the CSV file
        String filePath = Paths.get("files/fypCoordinatorList.csv").toString();
        int count = 0;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            // Iterate over the remaining rows
            String row;
            while ((row = csvReader.readLine()) != null) {
                // Split the row into fields using the comma delimiter
                String[] fields = row.split(",");

                // Process the fields as needed
                String userID = fields[0];
                String password = fields[1];
                String name = fields[2];
                String email = fields[3];

                // Create a Student object and add it to the studentList
                FYPCoordinator fypCoordinator = new FYPCoordinator(userID, password, name, email);
                fypCoordinatorList.add(fypCoordinator);

                count++;
            }

            // Print a message if no FYP Coordinator data is found in the file
            if (count == 0) {
                System.out.println("No FYP Coordinator data found in the file");
            }

            // Close the BufferedReader object
            csvReader.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        return 1;

    }

    /**
     * Loads the project list from a CSV file.
     *
     * @return 1 if the list is loaded successfully, 0 if there was an error
     */
    public static int loadProjectList() {

        List<Project> projectListReturn = new ArrayList<>();

        // Create a BufferedReader object to read the CSV file
        String filePath = Paths.get("files/projectList.csv").toString();
        int count = 0;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

            // Iterate over the remaining rows
            String row;
            while ((row = csvReader.readLine()) != null) {
                // Split the row into fields using the comma delimiter
                String[] fields = row.split(",");

                // Process the fields as needed
                Supervisor supervisor = Supervisor.getSupervisorByID(fields[1]);
                String projectTitle = fields[2];

                // Create a new project object
                Project project = new Project(supervisor, projectTitle);

                ProjectStatus projectStatus = ProjectStatus.valueOf(fields[3]);
                project.changeProjectStatus(projectStatus);

                project.changeProjectID(Integer.parseInt(fields[0])); 

                if (!fields[4].equals("None")) {
                    Student student = Student.getStudentByID(fields[4]);
                    project.changeStudent(student);
                }

                projectListReturn.add(project);
                
                count++;
            }

            // Print a message if no project data is found in the file
            if (count == 0) {
                System.out.println("No project data found in the file");
            }

            // Close the BufferedReader object
            csvReader.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }

        projectList.clear();
        projectList = projectListReturn;

        return 1;

    }

    /**
     * Loads all lists (student, supervisor, FYP Coordinator, and project) from CSV
     * files.
     *
     * @return 1 if all lists are loaded successfully, 0 if there was an error
     */
    public static int loadAllLists() {

        int loadedStudents, loadedSupervisors, loadedFYPCoordinators, loadedProjects;

        // 1. Load the FYPCoordinator list
        loadedFYPCoordinators = InitializeApplication.loadFYPCoordinatorList();
        FYPCoordinator.assignFYPCoordinatorsList(fypCoordinatorList);

        // 2. Load the Student list, assign no project yet
        loadedStudents = InitializeApplication.loadStudentList();
        // System.out.println("Step 2 studentList length: " + studentList.size());
        Student.assignStudentsList(studentList);

        // 3. Load the Supervisor list, assign no student yet
        loadedSupervisors = InitializeApplication.loadSupervisorList();
        // System.out.println("Step 3 supervisorList length: " + supervisorList.size());
        Supervisor.assignSupervisorsList(supervisorList);

        // 4. Load the Project list
        loadedProjects = InitializeApplication.loadProjectList();
        // System.out.println("Step 4 projectList length: " + projectList.size());
        Project.assignProjectList(projectList);

        // 5. Load the Student list, assign project
        loadedStudents = InitializeApplication.loadStudentList();
        // System.out.println("Step 5 studentList length: " + studentList.size());
        Student.assignStudentsList(studentList);

        // 6. Load the Supervisor list, assign student
        loadedSupervisors = InitializeApplication.loadSupervisorList();
        // System.out.println("Step 6 supervisorList length: " + supervisorList.size());
        Supervisor.assignSupervisorsList(supervisorList);

        // 7. Load the Project list, assign to respective supervisors
        loadedProjects = InitializeApplication.loadProjectList();
        // System.out.println("Step 4 projectList length: " + projectList.size());
        Project.assignProjectList(projectList);

        int result;
        if (loadedStudents == 1 && loadedSupervisors == 1 && loadedFYPCoordinators == 1 && loadedProjects == 1) {
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }

}
