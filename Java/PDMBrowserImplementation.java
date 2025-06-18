import java.util.Random;

public class PDMBrowserImplementation implements IPDMBrowser {
    private final Random rand;

    public PDMBrowserImplementation() {
        rand = new Random();
    }

    @Override
    public int isEditing(long objectID) {
       return rand.nextInt(3) - 1;
    }

    @Override
    public boolean takeEditing(long objectID) {
        // Реализация метода
        return true;
    }

    @Override
    public boolean finishEditing(long objectID) {
        // Реализация метода
        return true;
    }

    @Override
    public boolean saveFile(long objectID, FileParam param, byte[] data) {
        // Реализация метода
        return true;
    }

    @Override
    public boolean sendCADObjectStructure(CADObject object) {
        // Реализация метода
        return true;
    }

    @Override
    public CADObject registrationDocument(CADObject object) {
        // Реализация метода
        return object;
    }
}