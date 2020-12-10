package file_handling.service;

public enum UserActions {

    LIST_FILES("1"),
    CREATE_FILE("2"),
    CREATE_FOLDER("3"),
    EDIT_FILE("4"),
    DELETE_FILE("5"),
    GO_IN_FOLDER("6"),
    BACK_FOLDER("7"),
    READ_TXT_FILE("8"),
    COPY_TXT_FILE("9"),
    COPY_FILE_BENCHMARK("10"),
    EXIT("11");

    private String value;

    UserActions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean containsAction(String value) {
        UserActions[] actions = UserActions.values();

        for (UserActions action : actions) {
            if (action.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }
}
