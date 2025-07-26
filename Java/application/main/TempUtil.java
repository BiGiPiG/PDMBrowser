package application.main;

import java.util.ArrayList;
import java.util.List;

import application.DBClass.DBObjectCollection;
import application.DBClass.DBObjectType;

public class TempUtil {
	
	//public static List<Object> types = new ArrayList<>();
	
	public TempUtil(){
		/*
		DBObjectType root = new DBObjectType("Объекты");
		
		DBObjectType el00 = new DBObjectType("Документы");
		
			DBObjectType el10 = new DBObjectType("Модели сборочных единиц");
			DBObjectType el11 = new DBObjectType("Модели деталей");
			DBObjectType el12 = new DBObjectType("Модели для 3Д-принтера");
			DBObjectType el13 = new DBObjectType("Эскизы для лазера");
		
		DBObjectType el01 = new DBObjectType("Изделия");
		
			DBObjectType el20 = new DBObjectType("Сборочные единицы");
			DBObjectType el21 = new DBObjectType("Детали");
			
			root.addChildType(el00);
			
			el00.addChildType(el10);
			el00.addChildType(el11);
			el00.addChildType(el12);
			el00.addChildType(el13);
			
		root.addChildType(el01);
			
			el01.addChildType(el20);
			el01.addChildType(el21);
			
		types.add(root);
		
		DBObjectCollection c1 = new DBObjectCollection("Детали");
		
		c1.add("Деталь 1");
		c1.add("Деталь 2");
		*/
	}
	
	public static DBObjectType getRootObjectType() {
		
		return new DBObjectType(0, "Объекты");
		
	}

}
