package singleton;

public class openConnection {

    private static final openConnection serverConInstance = new openConnection();

    private openConnection() {}

    public static synchronized openConnection getInstance() {
            return openConnection.serverConInstance;
    }
}
