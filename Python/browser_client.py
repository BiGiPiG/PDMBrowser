from py4j.java_gateway import JavaGateway

class BrowserClient:
    def __init__(self):
        self.gateway = JavaGateway()
        print(dir(self.gateway.entry_point))
        self.browser = self.gateway.entry_point


    def is_editing(self, object_id):
        
        checkoutBy = self.browser.getSessionKeeper().getSession().getObject(object_id).getCheckoutBy()
        
        if checkoutBy == 0:
            return 0
        elif checkoutBy == self.browser.getSessionKeeper().getSession().getUserID():
            return 1
        else:
            return 2


