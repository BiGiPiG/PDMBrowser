import java.util.Date;

public interface IPDMBrowser {
	
	/**
	 * 
	 * Проверяет состояние редактирования объекта
	 * 
	 * @param objectID - идентификатор объекта в БД
	 * @return
	 * <ul>
	 * 	<li>
	 *   1 - объект взят на редактирование
	 * 	</li>
	 * 	<li>
	 *   0 - объект не взят на редактирование
	 * 	</li>
	 *  <li>
	 *   -1 - объект взят на редактирование другим пользователем
	 * 	</li>
	 * </ul>
	 */
	int isEditing(long objectID);
	
	/**
	 * 
	 * Берет объект в БД на редактирование
	 * 
	 * @param objectID - идентификатор объекта в БД
	 * 
	 * @return true - в случае удачи
	 * 
	 */
	boolean takeEditing(long objectID);
	
	/**
	 * 
	 * Завершает редактирование объекта в БД
	 * 
	 * @param objectID - идентификатор объекта в БД
	 * 
	 * @return true - в случае удачи
	 * 
	 */
	boolean finishEditing(long objectID);
	
	/**
	 * 
	 * Сохраняет файл в БД
	 * 
	 * @param objectID - идентификатор объекта в БД
	 * @param param - параметры файла
	 * @param data - массив байт данных
	 * @return true - в случае удачи
	 */
	boolean saveFile(long objectID, FileParam param, byte[] data);
	
	/**
	 * 
	 * @return возвращает структуру параметров файла
	 * 
	 */
	default FileParam getFileParamStructure(String name, Date creationDate, Date lastEditDate, String creator) {
		
		return new FileParam(name, creationDate, lastEditDate, creator);
		
	}
	
	/**
	 * 
	 * Принимает головной объект CAD и сохраняет его в БД
	 * 
	 * @param object - головной объект
	 * @return true - в случае удачи
	 */
	boolean sendCADObjectStructure(CADObject object);
	
	/**
	 * 
	 * Создает пустой объект CAD
	 * 
	 * @param objectID объекта в БД
	 * @param type тип объекта в БД
	 * @return
	 */
	default CADObject getCADObject(long objectID, int type) {
		
		return new CADObject(objectID, type);
		
	}
	
	/**
	 * 
	 * Создает в БД новые объекты. id для новых документов должно быть -1  
	 * 
	 * @param object - объект CAD
	 * @return объект CAD с проставленными id
	 */
	CADObject registrationDocument(CADObject object);

}
