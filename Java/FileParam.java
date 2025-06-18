import java.util.Date;

class FileParam {


    /**
     *
     * Структура для хранения данных о файле
     *
     * @param name - имя файла
     * @param creationDate - дата создания
     * @param lastEditDate - дата последнего релактирования
     * @param creator - создатель файла
     */
    public FileParam(String name, Date creationDate, Date lastEditDate, String creator) {

        this.name = name;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
        this.creator = creator;

    }

    public String name;
    public Date creationDate;
    public Date lastEditDate;
    public String creator;

}