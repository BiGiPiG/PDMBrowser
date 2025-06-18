import py4j.GatewayServer;

public class Server {

    private final PDMBrowserImplementation pdmBrowserImplementation;

    public Server() {
        this.pdmBrowserImplementation = new PDMBrowserImplementation();
    }

    public PDMBrowserImplementation getPdmBrowserImplementation() {
        return pdmBrowserImplementation;
    }

    public static void main(String[] args) {
        /* Создание и запуск сервера */
        GatewayServer gatewayServer = new GatewayServer(new Server());
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }
}

//mvn clean compile
//mvn exec:java "-Dexec.mainClass=Server"