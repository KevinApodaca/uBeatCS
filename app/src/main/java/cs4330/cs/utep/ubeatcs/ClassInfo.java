package cs4330.cs.utep.ubeatcs;

public class ClassInfo {

    private String class_name;
    private String class_teacher;
    private String class_number;
    private String class_url;
    private String class_email;

    public ClassInfo(String class_name, String class_teacher, String class_number, String class_url, String class_email) {
        this.class_name = class_name;
        this.class_teacher = class_teacher;
        this.class_number = class_number;
        this.class_url = class_url;
        this.class_email = class_email;
    }

    public ClassInfo() {
    }

    public ClassInfo(String name, String number, String url) {
        this.class_name = name;
        this.class_number = number;
        this.class_url = url;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_teacher() {
        return class_teacher;
    }

    public void setClass_teacher(String class_teacher) {
        this.class_teacher = class_teacher;
    }

    public String getClass_number() {
        return class_number;
    }

    public void setClass_number(String class_number) {
        this.class_number = class_number;
    }

    public String getClass_url() {
        return class_url;
    }

    public void setClass_url(String class_url) {
        this.class_url = class_url;
    }

    public String getClass_email() {
        return class_email;
    }

    public void setClass_email(String class_email) {
        this.class_email = class_email;
    }
}
