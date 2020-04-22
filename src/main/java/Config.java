
/**
 * Created by irisg on 22/04/2020.
 */
public class Config {

    private int queueCapacity;
    private int SocketConnectPort;
    private int mongoConnectPort;
    private String mongoConnectHost;
    private String SocketConnectHost;
    private String logFilePath;

    public int getMongoConnectPort() {
        return mongoConnectPort;
    }

    public void setMongoConnectPort(int mongoConnectPort) {
        this.mongoConnectPort = mongoConnectPort;
    }

    public String getMongoConnectHost() {
        return mongoConnectHost;
    }

    public void setMongoConnectHost(String mongoConnectHost) {
        this.mongoConnectHost = mongoConnectHost;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getSocketConnectPort() {
        return SocketConnectPort;
    }

    public void setSocketConnectPort(int socketConnectPort) {
        SocketConnectPort = socketConnectPort;
    }

    public String getSocketConnectHost() {
        return SocketConnectHost;
    }

    public void setSocketConnectHost(String socketConnectHost) {
        SocketConnectHost = socketConnectHost;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public String toString() {
        return "Config{" +
                "queueCapacity=" + queueCapacity +
                ", SocketConnectPort=" + SocketConnectPort +
                ", mongoConnectPort=" + mongoConnectPort +
                ", mongoConnectHost='" + mongoConnectHost + '\'' +
                ", SocketConnectHost='" + SocketConnectHost + '\'' +
                ", logFilePath='" + logFilePath + '\'' +
                '}';
    }
}
