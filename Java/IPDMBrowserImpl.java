import java.util.Random;

public class IPDMBrowserImpl implements IPDMBrowser {

    private final Random rand = new Random();

    @Override
    public SessionKeeper getSessionKeeper() {
        return null;
    }

    public int isEditing(int objectId) {
        return rand.nextInt(2);
    }
}
