import java.util.HashMap;

class CADObject {

    /**
     *
     * Представляет объект в БД. Содержит атрибуты CAD объекта и структуру
     *
     * @param objectID - идентификатор объекта в БД
     * @param type - тип объекта в БД
     */
    public CADObject(long objectID, int type) {
        this.objectID = objectID;
        this.type = type;
        this.attributes = new HashMap<>();
        this.child = new HashMap<>();
    }

    private final long objectID;

    public long getID() { return objectID; }

    private final int type;

    public int getType() { return type; }

    private final HashMap<String, Object> attributes;

    public HashMap<String, Object> getAttributeCollection() { return attributes; }

    private final HashMap<Long, Object> child;

    public HashMap<Long, Object> getConsistCollection() { return child; }

}
