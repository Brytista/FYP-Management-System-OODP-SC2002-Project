package package1;
/**
A*n interface for changing the title of a project.
*/
interface ChangeProjectTitle {
    /**
     * Changes the title of the project to the given title.
     * 
     * @param projectTitle the new title of the project.
     * @return an integer indicating the success or failure of the operation. 1 means success, 0 means failure.
     */
    public int changeProjectTitle(String projectTitle);
}
