from PySide6 import QtCore, QtGui, QtWidgets
from compas_service import CompasService
from browser_client import BrowserClient


class UiMainWindow(object):
    def __init__(self):
        self.reg_btn = None
        self.horizontal_layout = None
        self.vertical_layout = None
        self.central_widget = None
        self.table_widget = None
        self.save_btn = None
        self.update_btn = None
        self.check_btn = None
        self.edit_btn = None
        self.compas_service = CompasService()
        self.pdm_client = BrowserClient()

    def setup_ui(self, ui_main_window):
        ui_main_window.setObjectName("PDMBrowser")
        ui_main_window.resize(600, 300)
        ui_main_window.setMinimumSize(QtCore.QSize(600, 300))

        # Центральный виджет
        self.central_widget = QtWidgets.QWidget(parent=ui_main_window)
        self.central_widget.setEnabled(True)
        self.central_widget.setObjectName("central_widget")

        # Главный вертикальный layout
        self.vertical_layout = QtWidgets.QVBoxLayout(self.central_widget)
        self.vertical_layout.setObjectName("vertical_layout")

        # Горизонтальный layout  для кнопок
        self.horizontal_layout = QtWidgets.QHBoxLayout()
        self.horizontal_layout.setObjectName("horizontal_layout")

        # Кнопка регистрации
        self.reg_btn = QtWidgets.QPushButton(parent=self.central_widget)
        self.reg_btn.setIcon(QtGui.QIcon(QtGui.QPixmap(":/icon/resources/reg.png")))
        self.reg_btn.setIconSize(QtCore.QSize(20, 20))
        self.reg_btn.setObjectName("reg_btn")
        self.horizontal_layout.addWidget(self.reg_btn)

        # Кнопка сохранения
        self.save_btn = QtWidgets.QPushButton(parent=self.central_widget)
        self.save_btn.setIcon(QtGui.QIcon(QtGui.QPixmap(":/icon/resources/save2.png")))
        self.save_btn.setIconSize(QtCore.QSize(20, 20))
        self.save_btn.setObjectName("save_btn")
        self.horizontal_layout.addWidget(self.save_btn)

        # Кнопка обновления
        self.update_btn = QtWidgets.QPushButton(parent=self.central_widget)
        self.update_btn.setIcon(QtGui.QIcon(QtGui.QPixmap(":/icon/resources/update.png")))
        self.update_btn.setIconSize(QtCore.QSize(20, 20))
        self.update_btn.setObjectName("update_btn")
        self.horizontal_layout.addWidget(self.update_btn)

        # Кнопка проверки
        self.check_btn = QtWidgets.QPushButton(parent=self.central_widget)
        self.check_btn.setIcon(QtGui.QIcon(QtGui.QPixmap(":/icon/resources/check.png")))
        self.check_btn.setIconSize(QtCore.QSize(20, 20))
        self.check_btn.setObjectName("check_btn")
        self.horizontal_layout.addWidget(self.check_btn)

        # Кнопка редактирования
        self.edit_btn = QtWidgets.QPushButton(parent=self.central_widget)
        self.edit_btn.setIcon(QtGui.QIcon(QtGui.QPixmap(":/icon/resources/edit.png")))
        self.edit_btn.setIconSize(QtCore.QSize(20, 20))
        self.edit_btn.setObjectName("edit_btn")
        self.horizontal_layout.addWidget(self.edit_btn)

        # Прокладка между кнопками и таблицей
        spacer_item = QtWidgets.QSpacerItem(
            40, 20, QtWidgets.QSizePolicy.Policy.Expanding, QtWidgets.QSizePolicy.Policy.Minimum
        )
        self.horizontal_layout.addItem(spacer_item)

        # Добавление кнопочной панели в вертикальный layout
        self.vertical_layout.addLayout(self.horizontal_layout)

        self.setup_table()

        # Перевод текста интерфейса
        self.retranslate_ui(ui_main_window)
        QtCore.QMetaObject.connectSlotsByName(ui_main_window)

        # Установка центрального виджета
        ui_main_window.setCentralWidget(self.central_widget)

    def setup_table(self):
        """Инициализация таблицы при запуске приложения"""
        self.table_widget = QtWidgets.QTableWidget(parent=self.central_widget)
        self.table_widget.setObjectName("table_widget")
        self.table_widget.setColumnCount(5)
        self.table_widget.verticalHeader().setVisible(False)

        # Вертикальные заголовки
        for row in range(4):
            item = QtWidgets.QTableWidgetItem()
            self.table_widget.setVerticalHeaderItem(row, item)

        # Горизонтальные заголовки
        headers = ["", "Обозначение", "Наименование", "Количество", "Материал"]
        for col in range(5):
            item = QtWidgets.QTableWidgetItem()
            self.table_widget.setHorizontalHeaderItem(col, item)
            if col > 0:
                item.setText(headers[col])

        # Иконка в первой строке таблицы
        item = QtWidgets.QTableWidgetItem()
        item.setIcon(QtGui.QIcon(QtGui.QPixmap(":/icon/resources/asm.png")))
        self.table_widget.setItem(0, 0, item)

        # # Иерархические символы
        # for i, symbol in zip(range(1, 4), ["├", "├└", "└"]):
        #     item = QtWidgets.QTableWidgetItem()
        #     item.setFont(QtGui.QFont("", 10))
        #     item.setText(symbol)
        #     self.table_widget.setItem(i, 0, item)

        self.compas_service.fill_properties()
        self.table_widget.setRowCount(len(self.compas_service.properties))

        for row, (object_id, mark, name, quantity, material, level, is_assembly) in enumerate(self.compas_service.properties):
            level = int(level)

            # Генерация символов иерархии
            symbol = self.generate_line(row, level)

            # Создаём виджет для первой ячейки
            cell_widget = QtWidgets.QWidget()
            layout = QtWidgets.QHBoxLayout()
            layout.setContentsMargins(0, 0, 0, 0)

            # Сдвиг от края для первого столбца
            left_margin = 20
            layout.setContentsMargins(left_margin, 0, 0, 0)

            # Добавляем символы иерархии
            if row > 0:
                label_text = QtWidgets.QLabel(symbol)
                label_text.setFont(QtGui.QFont("", 10))
                layout.addWidget(label_text)

            # Добавляем иконку
            if is_assembly:
                icon_label = QtWidgets.QLabel()
                pixmap = QtGui.QPixmap(":/icon/resources/asm.png").scaled(16, 16, QtCore.Qt.KeepAspectRatio,
                                                                          QtCore.Qt.SmoothTransformation)
                icon_label.setPixmap(pixmap)
                layout.addWidget(icon_label)
            else:
                icon_label = QtWidgets.QLabel()
                pixmap = QtGui.QPixmap(":/icon/resources/part.png").scaled(16, 16, QtCore.Qt.KeepAspectRatio,
                                                                          QtCore.Qt.SmoothTransformation)
                icon_label.setPixmap(pixmap)
                layout.addWidget(icon_label)

            layout.addStretch()  # Чтобы элементы не прижимались

            cell_widget.setLayout(layout)
            self.table_widget.setCellWidget(row, 0, cell_widget)

            self.table_widget.setItem(row, 1, QtWidgets.QTableWidgetItem(mark))
            self.table_widget.setItem(row, 2, QtWidgets.QTableWidgetItem(name))
            self.table_widget.setItem(row, 3, QtWidgets.QTableWidgetItem(str(quantity)))
            self.table_widget.setItem(row, 4, QtWidgets.QTableWidgetItem(material))

            if object_id is None:
                cell_widget = self.table_widget.cellWidget(row, 0)
                if cell_widget is not None:
                    cell_widget.setStyleSheet("background-color: #fa8282;")
            else:
                cell_widget = self.table_widget.cellWidget(row, 0)
                if self.pdm_client.is_editing(object_id) == 1:
                    cell_widget.setStyleSheet("background-color: #0fffa7;")

        # Добавление таблицы в макет
        self.vertical_layout.addWidget(self.table_widget)

    def retranslate_ui(self, ui_main_window):
        _translate = QtCore.QCoreApplication.translate
        ui_main_window.setWindowTitle(_translate("PDMBrowser", "PDMBrowser"))
        self.edit_btn.setToolTip(_translate("PDMBrowser", "Взять на редактирование"))
        self.reg_btn.setToolTip(_translate("PDMBrowser", "Зарегистрировать документ"))
        self.save_btn.setToolTip(_translate("PDMBrowser", "Сохранить документ"))
        self.update_btn.setToolTip(_translate("PDMBrowser", "Обновить документ"))
        self.check_btn.setToolTip(_translate("PDMBrowser", "Завершить редактирование"))

    def generate_line(self, row, level):
        symbol = ""
        next_level = int(self.compas_service.properties[row + 1][5]) if row + 1 < len(
            self.compas_service.properties) else 0

        for lvl in range(1, level):
            symbol += "│   "

        symbol += "└" if next_level < level else "├"
        return symbol
