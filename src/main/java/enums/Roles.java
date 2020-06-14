package enums;

public enum Roles {
    ADMINISTRATOR(1, "Administrator"),
    EMPLOYEE(2, "Employee"),
    USER(3, "User");

    private final int idRole;
    private final String description;

    Roles(int id, String description) {
        this.idRole = id;
        this.description = description;
    }

    public static void showPosition(){
        for(int i = 0; i < Roles.values().length; i++){
            System.out.println(Roles.values()[i].description);
        }
    }
}
