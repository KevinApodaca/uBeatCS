package cs4330.cs.utep.ubeatcs;

import java.util.ArrayList;
import java.util.List;

/**
 * Class will be used to define the properties of each of the courses in the CS department.
 *
 * @author Isaias Leos
 */
class StudyClass {

    private String class_name;
    private String class_teacher;
    private String class_number;
    private String class_url;
    private String class_email;
    private List<String> youtubePlaylist;
    private String class_crn;

    StudyClass(String class_name, String class_teacher, String class_number, String class_url, String class_email, ArrayList<String> youtubeLists, String crn) {
        this.class_name = class_name;
        this.class_teacher = class_teacher;
        this.class_number = class_number;
        this.class_url = class_url;
        this.class_email = class_email;
        this.youtubePlaylist = youtubeLists;
        this.class_crn = crn;
    }

    /**
     * Default constructor.
     */
    StudyClass() {
        youtubePlaylist = new ArrayList<>();
    }

    /**
     * Class will instantiate the values.
     *
     * @param name - the course name
     * @param crn  - the course number
     */
    StudyClass(String name, String crn) {
        this.class_name = name;
        this.class_crn = crn;
    }

    /**
     * Getter for the name of the class.
     *
     * @return the class name.
     */
    String getClass_name() {
        return class_name;
    }

    /**
     * Setter for the name of the class.
     *
     * @param class_name - the name of the course
     */
    void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    /**
     * Getter for the instructor of the class.
     *
     * @return the class instructor.
     */
    String getClass_teacher() {
        return class_teacher;
    }

    /**
     * Setter for the instructor of the class.
     *
     * @param class_teacher - the name of the teacher.
     */
    void setClass_teacher(String class_teacher) {
        this.class_teacher = class_teacher;
    }

    /**
     * Getter for the course number.
     *
     * @return the course number.
     */
    String getClass_number() {
        return class_number;
    }

    /**
     * Setter for the course number.
     *
     * @param class_number - the number of the course.
     */
    void setClass_number(String class_number) {
        this.class_number = class_number;
    }

    /**
     * Getter for the course webpage url.
     *
     * @return the url of the webpage
     */
    String getClass_url() {
        if (class_url == null || class_url.equals("")) {
            setClass_url("https://www.utep.edu/cs/people/index.html");
        }
        return class_url;
    }

    /**
     * Setter for the course webpage url.
     *
     * @param class_url - the url of the course.
     */
    void setClass_url(String class_url) {
        this.class_url = class_url;
    }

    /**
     * Getter for the email for the instructor for that course.
     *
     * @return the email of the instructor.
     */
    String getClass_email() {
        return class_email;
    }

    /**
     * Setter for the email of the instructor of the course.
     *
     * @param class_email - the instructor email.
     */
    void setClass_email(String class_email) {
        this.class_email = class_email;
    }

    List<String> getYoutubePlaylist() {
        return youtubePlaylist;
    }

    void setYoutubePlaylist(List<String> youtubePlaylist) {
        this.youtubePlaylist = youtubePlaylist;
    }

    String getClass_crn() {
        return class_crn;
    }

    void setClass_crn(String class_crn) {
        this.class_crn = class_crn;
    }
}
