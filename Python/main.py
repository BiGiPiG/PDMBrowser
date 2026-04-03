import resources

import sys

from PySide6.QtWidgets import QApplication, QMainWindow

from ui_main_window import UiMainWindow
from browser_client import BrowserClient


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        # Создаем экземпляр интерфейса и настраиваем его
        self.ui = UiMainWindow()
        self.ui.setup_ui(self)

        self.ui.reg_btn.clicked.connect(self.registration)
        self.ui.save_btn.clicked.connect(save)
        self.ui.update_btn.clicked.connect(self.update_tree)
        self.ui.check_btn.clicked.connect(check_file)
        self.ui.edit_btn.clicked.connect(edit_file)

    def update_tree(self):
        print("update tree")
        self.ui.tree_widget.deleteLater()
        #self.ui.table_widget.setParent(None)
        #self.ui.table_widget.deleteLater()
        self.ui.block_buttons()
        self.ui.setup_tree()


    def registration(self):
        print("registration")
        selected_items = self.ui.tree_widget.selectedItems()
        if selected_items:
            data = self.ui.item_data_map.get(selected_items[0])
            if data:
                if data['specification'] == "Стандартные изделия":
                    colection = self.ui.browser_client.browser.getSessionKeeper().getSession().getObjectCollection(5);
                    obj = colection.create()
                    attrs = obj.getAttributeCollection()
                    attr = attrs.addAttribute(6)
                    attr.setValue(data['name'])
                    self.update_tree()

def save(self):
    print("add new file to data base")

def check_file(self):
    print("check file")

def edit_file(self):
    print("edit file")


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec())