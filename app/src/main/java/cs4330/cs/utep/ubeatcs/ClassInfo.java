package cs4330.cs.utep.ubeatcs;

public class ClassInfo {

    private String class_name;
    private String class_teacher;
    private String class_number;

    public ClassInfo(String class_name, String class_teacher, String class_number) {
        this.class_name = class_name;
        this.class_teacher = class_teacher;

        this.class_number = class_number;
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
}
