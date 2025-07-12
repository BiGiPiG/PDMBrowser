from mailbox import MH

import pythoncom
from win32com.client import Dispatch, gencache


class CompasService:
    """Класс для работы с KOMPAS-3D v20 и получения информации о деталях сборки"""

    def __init__(self):
        self.properties = []
        self.parts_dict = {}

        self.kompas_object = None
        self.application = None
        self.property_manager = None
        self.active_document = None
        self.top_part = None
        self.kompas_api7_module = None

    def connect(self):
        try:
            # Подключаем константы API Компас
            kompas6_constants = gencache.EnsureModule("{75C9F5D0-B5B8-4526-8681-9903C567D2ED}", 0, 1, 0).constants
            kompas6_constants_3d = gencache.EnsureModule("{2CAF168C-7961-4B90-9DA2-701419BEEFE3}", 0, 1, 0).constants

            # Подключаем описание интерфейсов API5
            kompas6_api5_module = gencache.EnsureModule("{0422828C-F174-495E-AC5D-D31014DBBE87}", 0, 1, 0)
            kompas_object = kompas6_api5_module.KompasObject(
                Dispatch("Kompas.Application.5")._oleobj_.QueryInterface(kompas6_api5_module.KompasObject.CLSID,
                                                                         pythoncom.IID_IDispatch))
            MH.iKompasObject = kompas_object

            # Подключаем описание интерфейсов API7
            self.kompas_api7_module = gencache.EnsureModule("{69AC2981-37C0-4379-84FD-5DD2F3C0A520}", 0, 1, 0)
            application = self.kompas_api7_module.IApplication(
                Dispatch("Kompas.Application.7")._oleobj_.QueryInterface(self.kompas_api7_module.IApplication.CLSID,
                                                                         pythoncom.IID_IDispatch))
            MH.iApplication = application

            Documents = application.Documents
            # Получим активный документ
            kompas_document = application.ActiveDocument
            kompas_document_3d = self.kompas_api7_module.IKompasDocument3D(kompas_document)

            # Правильно присваиваем active_document — используем API7
            self.active_document = kompas_document_3d

            if self.active_document.DocumentType != 5:
                raise TypeError("Открытый документ не является 3D-сборкой.")

            self.property_manager = self.kompas_api7_module.IPropertyMng(application)
            self.top_part = self.active_document.TopPart



        except Exception as e:
            print("Ошибка подключения к Компас:", e)
            raise

    def get_property(self, part, prop_name):
        try:
            ipk = self.kompas_api7_module.IPropertyKeeper(part)
            prop = self.property_manager.GetProperty(part.FileName, prop_name)

            val = None
            from_source = True

            result = ipk.GetPropertyValue(prop, None, True, True)

            return result[1] if prop else None
        except Exception as e:
            print(f"Ошибка получения свойства '{prop_name}': {e}")
            return None

    def walk_parts(self, part, level=0):
        try:
            object_id = self.get_property(part, "Идентификатор")
            name = getattr(part, "Name", None)
            mark = getattr(part, "Mark", "")
            quantity = int(getattr(part, "Quantity", 1))
            material = getattr(part, "Material", "Неизвестно")

            is_assembly = hasattr(part, "Parts") and part.Parts.Count > 0

            part_key = (object_id, mark, name, material, level, is_assembly)
            print(part_key)

            if part_key in self.parts_dict:
                self.parts_dict[part_key] += quantity
            else:
                self.parts_dict[part_key] = quantity

                if is_assembly:
                    for i in range(part.Parts.Count):
                        child_part = part.Parts.Part(i)
                        self.walk_parts(child_part, level + 1)

        except Exception as e:
            print("  " * level + f"Ошибка при обходе: {e}")

    def fill_properties(self):
        try:
            self.properties.clear()
            self.parts_dict.clear()
            self.connect()

            print("Чтение данных из сборки...")
            self.walk_parts(self.top_part)

            self.properties = [
                (object_id, mark, name, str(quantity), material, level, is_assembly)
                for (object_id, mark, name, material, level, is_assembly), quantity in self.parts_dict.items()
            ]

            print("Готово. Найдено компонентов:", len(self.properties))

        except Exception as e:
            print("Ошибка при получении данных:", e)