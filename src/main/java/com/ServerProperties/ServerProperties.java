package com.ServerProperties;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ServerProperties {

    private int port;
    private String workingDir;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    public boolean getServerProperties(String ConfigurationFile)  {
        try {
            File file = new File(ConfigurationFile);
            if(file.exists()) {
                FileReader reader = new FileReader(ConfigurationFile);
                Properties properties = new Properties();
                properties.load(reader);
                setPort(Integer.parseInt(properties.getProperty("port")));
                setWorkingDir(properties.getProperty("workingdirectory"));
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

