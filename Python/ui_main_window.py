from PySide6 import QtCore, QtGui, QtWidgets
from PySide6.QtWidgets import QTreeWidget, QTreeWidgetItem
import datetime

from compas_service import CompasService
from browser_client import BrowserClient
from row_color import RowColor


class UiMainWindow(object):
    def __init__(self):
        self.reg_btn = None
        self.horizontal_layout = None
        self.vertical_layout = None
        self.central_widget = None
        self.tree_widget = None
        self.save_btn = None
        self.update_btn = None
        self.check_btn = None
        self.edit_btn = None
        self.compas_service = CompasService()
        self.browser_client = BrowserClient()
        self.item_data_map = {}

    def setup_ui(self, ui_main_window):
        ui_main_window.setObjectName("PDMBrowser")
        ui_main_window.resize(600, 300)
        ui_main_window.setMinimumSize(QtCore.QSize(600, 300))

        self.central_widget = QtWidgets.QWidget(parent=ui_main_window)
        self.central_widget.setEnabled(True)
        self.central_widget.setObjectName("central_widget")

        self.vertical_layout = QtWidgets.QVBoxLayout(self.central_widget)
        self.vertical_layout.setObjectName("vertical_layout")

        self.horizontal_layout = QtWidgets.QHBoxLayout()
        self.horizontal_layout.setObjectName("horizontal_layout")

        # Кнопки
        self.reg_btn = self._create_button(":/resources/reg.png", "reg_btn")
        self.reg_btn.setEnabled(False)
        self.horizontal_layout.addWidget(self.reg_btn)

        self.save_btn = self._create_button(":/resources/save2.png", "save_btn")
        self.save_btn.setEnabled(False)
        self.horizontal_layout.addWidget(self.save_btn)

        self.update_btn = self._create_button(":/resources/update.png", "update_btn")
        self.update_btn.setEnabled(True)
        self.horizontal_layout.addWidget(self.update_btn)

        self.check_btn = self._create_button(":/resources/check.png", "check_btn")
        self.check_btn.setEnabled(False)
        self.horizontal_layout.addWidget(self.check_btn)

        self.edit_btn = self._create_button(":/resources/edit.png", "edit_btn")
        self.edit_btn.setEnabled(False)
        self.horizontal_layout.addWidget(self.edit_btn)

        spacer_item = QtWidgets.QSpacerItem(
            40, 20, QtWidgets.QSizePolicy.Policy.Expanding, QtWidgets.QSizePolicy.Policy.Minimum
        )
        self.horizontal_layout.addItem(spacer_item)

        self.vertical_layout.addLayout(self.horizontal_layout)

        self.setup_tree()
        self.retranslate_ui(ui_main_window)
        QtCore.QMetaObject.connectSlotsByName(ui_main_window)

        ui_main_window.setCentralWidget(self.central_widget)

    def _create_button(self, icon_path, object_name):
        btn = QtWidgets.QPushButton(parent=self.central_widget)
        btn.setIcon(QtGui.QIcon(QtGui.QPixmap(icon_path)))
        btn.setIconSize(QtCore.QSize(20, 20))
        btn.setObjectName(object_name)
        return btn

    def setup_tree(self):
        """Инициализация дерева с табличным стилем"""
        self.tree_widget = QTreeWidget(parent=self.central_widget)
        self.tree_widget.setObjectName("tree_widget")
        self.tree_widget.setColumnCount(6)
        self.tree_widget.setHeaderLabels(
            ["", "Обозначение", "Наименование", "Количество", "Материал", "Раздел спецификации"])

        # === НАСТРОЙКИ ДЛЯ ВИДА ТАБЛИЦЫ ===
        self.tree_widget.setIndentation(20)              # Отступ для иерархии
        self.tree_widget.setUniformRowHeights(True)      # Одинаковая высота строк
        self.tree_widget.setAllColumnsShowFocus(True)    # Подсветка всей строки
        self.tree_widget.setItemsExpandable(True)        # Разрешить сворачивание
        self.tree_widget.setExpandsOnDoubleClick(False)  # Разворачивание по клику на стрелку

        self.tree_widget.setStyleSheet("""
            QTreeWidget::item {
                height: 28px;
            }
            QTreeWidget::item:hover {
                background-color: #e8f4fc;
            }
            QTreeWidget::item:selected {
                background-color: #0078d4;
                color: white;
            }
            QHeaderView::section {
                background-color: #f0f0f0;
                border: 1px solid #cccccc;
                border-right: none;
                padding: 4px;
                font-weight: bold;
            }
        """)

        # Включение сетки
        self.tree_widget.setAlternatingRowColors(True)

        # Настройки выделения
        self.tree_widget.setSelectionBehavior(QTreeWidget.SelectRows)
        self.tree_widget.setSelectionMode(QTreeWidget.SingleSelection)

        # Подключение сигналов
        self.tree_widget.itemClicked.connect(self.on_item_clicked)
        self.tree_widget.itemSelectionChanged.connect(self.on_selection_changed)

        # Заполнение дерева
        self.compas_service.fill_properties()

        parent_stack = {}

        for row, (object_id, mark, name, quantity, material, level, is_assembly, specification, last_edit) in enumerate(
                self.compas_service.properties):
            level = int(level)

            tree_item = QTreeWidgetItem()

            # Иконка
            if specification == "Стандартные изделия":
                icon_pixmap = QtGui.QPixmap(":/resources/std.png")
            elif is_assembly:
                icon_pixmap = QtGui.QPixmap(":/resources/asm.png")
            else:
                icon_pixmap = QtGui.QPixmap(":/resources/part.png")

            icon_pixmap = icon_pixmap.scaled(16, 16, QtCore.Qt.KeepAspectRatio, QtCore.Qt.SmoothTransformation)
            tree_item.setIcon(0, QtGui.QIcon(icon_pixmap))

            # Данные колонок
            tree_item.setText(1, mark)
            tree_item.setText(2, name)
            tree_item.setText(3, str(quantity))
            tree_item.setText(4, material)
            tree_item.setText(5, specification)

            # Сохранение данных
            self.item_data_map[tree_item] = {
                'object_id': object_id,
                'mark': mark,
                'name': name,
                'quantity': quantity,
                'material': material,
                'level': level,
                'is_assembly': is_assembly,
                'specification': specification
            }

            if specification == "Cтандартные изделия":
                tree_item.setFlags(tree_item.flags() & ~QtCore.Qt.ItemFlag.ItemIsSelectable)
            #     for col in range(6):
            #         tree_item.setForeground(col, QtGui.QColor("#888888"))
            #
            # if object_id is None:
            #     for col in range(6):
            #         tree_item.setBackground(col, QtGui.QColor("#fa8282"))
            # elif self.browser_client.is_editing(object_id) == 1:
            #     print ( self.browser_client.browser.getSessionKeeper().getSession().getObject(object_id).getModifyDate() )
            #     for col in range(6):
            #         tree_item.setBackground(col, QtGui.QColor("#0fffa7")) 

            row_color = self.get_raw_color(object_id, last_edit, specification, name)
            for col in range(6):
                tree_item.setBackground(col, row_color)

            if level == 0:
                self.tree_widget.addTopLevelItem(tree_item)
                parent_stack[level] = tree_item
            else:
                parent_level = level - 1
                if parent_level in parent_stack:
                    parent_stack[parent_level].addChild(tree_item)
                else:
                    self.tree_widget.addTopLevelItem(tree_item)

            parent_stack[level] = tree_item

            for deeper_level in list(parent_stack.keys()):
                if deeper_level > level:
                    del parent_stack[deeper_level]
            tree_item.setExpanded(False)

        self.tree_widget.header().setSectionResizeMode(0, QtWidgets.QHeaderView.ResizeToContents)
        self.tree_widget.header().setSectionResizeMode(1, QtWidgets.QHeaderView.Interactive)
        self.tree_widget.header().setSectionResizeMode(2, QtWidgets.QHeaderView.Stretch)
        self.tree_widget.header().setSectionResizeMode(3, QtWidgets.QHeaderView.ResizeToContents)
        self.tree_widget.header().setSectionResizeMode(4, QtWidgets.QHeaderView.ResizeToContents)
        self.tree_widget.header().setSectionResizeMode(5, QtWidgets.QHeaderView.ResizeToContents)

        self.vertical_layout.addWidget(self.tree_widget)

    def on_item_clicked(self, item, column):
        data = self.item_data_map.get(item)
        if data:
            print(f"Клик: {data['mark']} - {data['name']}")

    def on_selection_changed(self):
        selected_items = self.tree_widget.selectedItems()
        if selected_items:
            data = self.item_data_map.get(selected_items[0])
            if data:
                print(f"Выбрано: {data['mark']} (ID: {data['object_id']})")
                color = f"#{hex(selected_items[0].background(0).color().rgb())[4:]}"
                self.update_buttons(color)

    def update_buttons(self, color):
        match color:
            case RowColor.RED:
                self.reg_btn.setEnabled(True)
                self.save_btn.setEnabled(False)
                self.check_btn.setEnabled(False)
                self.edit_btn.setEnabled(False)
            case RowColor.GREEN:
                self.reg_btn.setEnabled(False)
                self.save_btn.setEnabled(False)
                self.check_btn.setEnabled(True)
                self.edit_btn.setEnabled(False)
            case RowColor.YELLOW:
                self.reg_btn.setEnabled(False)
                self.save_btn.setEnabled(True)
                self.check_btn.setEnabled(True)
                self.edit_btn.setEnabled(False)
            case RowColor.WHITE:
                self.reg_btn.setEnabled(False)
                self.save_btn.setEnabled(False)
                self.check_btn.setEnabled(False)
                self.edit_btn.setEnabled(True)
            case RowColor.BLUE:
                self.reg_btn.setEnabled(False)
                self.save_btn.setEnabled(False)
                self.check_btn.setEnabled(False)
                self.edit_btn.setEnabled(False)
            case _:
                self.reg_btn.setEnabled(False)
                self.save_btn.setEnabled(False)
                self.check_btn.setEnabled(False)
                self.edit_btn.setEnabled(False)
            

    def block_buttons(self):
        self.reg_btn.setEnabled(False)
        self.save_btn.setEnabled(False)
        self.check_btn.setEnabled(False)
        self.edit_btn.setEnabled(False)


    def get_selected_item_data(self):
        selected_items = self.tree_widget.selectedItems()
        if selected_items:
            return self.item_data_map.get(selected_items[0])
        return None

    def retranslate_ui(self, ui_main_window):
        _translate = QtCore.QCoreApplication.translate
        ui_main_window.setWindowTitle(_translate("PDMBrowser", "PDMBrowser"))
        self.edit_btn.setToolTip(_translate("PDMBrowser", "Взять на редактирование"))
        self.reg_btn.setToolTip(_translate("PDMBrowser", "Зарегистрировать документ"))
        self.save_btn.setToolTip(_translate("PDMBrowser", "Сохранить документ"))
        self.update_btn.setToolTip(_translate("PDMBrowser", "Обновить документ"))
        self.check_btn.setToolTip(_translate("PDMBrowser", "Завершить редактирование"))

    #TODO
    def get_raw_color(self, object_id, last_edit, specification, name):
    
        if specification == "Стандартные изделия":
            
            session = self.browser_client.browser.getSessionKeeper().getSession()
            
            collection = session.getObjectCollection(5)
            
            cols = self.browser_client.gateway.new_array(self.browser_client.gateway.jvm.API.kernel.search.ColumnDescriptor,1)
            
            cols[0] = self.browser_client.gateway.jvm.API.kernel.search.ColumnDescriptor("Наименование")
            
            setParam = self.browser_client.gateway.jvm.API.kernel.search.DBRecordSetParams(None, cols)
            
            objs = collection.select(setParam)
            
            for obj in objs:
                if obj[1] == name:
                    return QtGui.QColor(RowColor.GREEN_STD)
            
            return QtGui.QColor(RowColor.RED_STD)
            
        else:
        
            if object_id is None:
                return QtGui.QColor(RowColor.RED)
            elif self.browser_client.is_editing(object_id) == 1:
                date = self.browser_client.browser.getSessionKeeper().getSession().getObject(object_id).getModifyDate()
                if datetime.datetime.strptime(str(date), '%Y-%m-%d %H:%M:%S.%f') == last_edit:
                    return QtGui.QColor(RowColor.GREEN)
                else:
                    return QtGui.QColor(RowColor.YELLOW)
            else:
                return QtGui.QColor(RowColor.WHITE)
