package application.PDMBrowser;

import application.DBClass.SessionKeeper;
import py4j.GatewayServer;

public class PDMBrowserConnector {

	public PDMBrowserConnector() {
		GatewayServer gatewayServer = new GatewayServer(new SessionKeeper());
        gatewayServer.start();
	}
	
}
