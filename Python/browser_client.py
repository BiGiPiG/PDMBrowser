from py4j.java_gateway import JavaGateway

class BrowserClient:
    def __init__(self):
        self.gateway = JavaGateway()
        print(dir(self.gateway.entry_point))
        self.browser = self.gateway.entry_point.getBrowser()


    def is_editing(self, object_id):
        return self.browser.getSessionKeeper().session.getObject(object_id)
        .getAttributeCollection().


