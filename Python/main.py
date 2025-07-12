import sys
from PySide6.QtWidgets import QApplication, QMainWindow
from ui_main_window import UiMainWindow
import resources


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        # Создаем экземпляр интерфейса и настраиваем его
        self.ui = UiMainWindow()
        self.ui.setup_ui(self)

        self.ui.reg_btn.clicked.connect(registration)
        self.ui.save_btn.clicked.connect(save)
        self.ui.update_btn.clicked.connect(self.update_table)
        self.ui.check_btn.clicked.connect(check_file)
        self.ui.edit_btn.clicked.connect(edit_file)

    def update_table(self):
        print("update table")
        self.ui.table_widget.setParent(None)
        self.ui.table_widget.deleteLater()
        self.ui.setup_table()


def registration():
    print("registration")

def save():
    print("add new file to data base")

def check_file():
    print("check file")

def edit_file():
    print("edit file")


if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec())