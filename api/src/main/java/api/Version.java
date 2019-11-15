package api;

public class Version {

    private final String version;

    public Version(String version) {
        this.version = version;
    }

    public String getApp() {
        return version;
    }

    public String getRuntime() {return System.getProperty("java.version");}
}