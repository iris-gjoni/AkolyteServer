
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
    private String keyStoreFilePath;
    private String keyStorePassword;
    private String trustFilePath;
    private String trustStorePassword;
    private boolean useSSL;

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

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public String getKeyStoreFilePath() {
        return keyStoreFilePath;
    }

    public void setKeyStoreFilePath(String keyStoreFilePath) {
        this.keyStoreFilePath = keyStoreFilePath;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getTrustFilePath() {
        return trustFilePath;
    }

    public void setTrustFilePath(String trustFilePath) {
        this.trustFilePath = trustFilePath;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
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
                ", keyStoreFilePath='" + keyStoreFilePath + '\'' +
                ", keyStorePassword='" + keyStorePassword + '\'' +
                ", trustFilePath='" + trustFilePath + '\'' +
                ", trustStorePassword='" + trustStorePassword + '\'' +
                ", useSSL=" + useSSL +
                '}';
    }
}
