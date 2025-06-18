from py4j.java_gateway import JavaGateway

class PDMClient:
    def __init__(self):
        self.gateway = JavaGateway()
        self.pdm_browser = self.gateway.entry_point.getPdmBrowserImplementation()

    def is_editing(self, object_id):
        return self.pdm_browser.isEditing(int(object_id))


