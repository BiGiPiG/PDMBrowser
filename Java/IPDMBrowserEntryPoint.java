import py4j.GatewayServer;

public class IPDMBrowserEntryPoint {

    private final IPDMBrowser browser;

    public IPDMBrowserEntryPoint() {
        browser = new IPDMBrowser();
    }

    public IPDMBrowser getBrowser() {
        return browser;
    }

    public static void main(String[] args) {
        GatewayServer gatewayServer = new GatewayServer(new IPDMBrowserEntryPoint());
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }

}