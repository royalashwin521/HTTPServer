package com.ResourcesHandling;

import com.ServerProperties.ServerProperties;

import java.io.*;
import java.util.Objects;

public class InputOutputStreamHandler {

    private final String databasePath;

    public InputOutputStreamHandler() {
        ServerProperties serverProperties = new ServerProperties();
        serverProperties.getServerProperties("config.properties");
        this.databasePath = serverProperties.getWorkingDir();
    }




    public void readInputFile(OutputStream outStream,String filePath) throws IOException {
        try {
            int readLength;
            File filein = new File(databasePath.concat(filePath));
            if(filein.exists() && filein.isFile()) {
                outStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                outStream.write(("ContentType: text/html\r\n").getBytes());
                outStream.write("\r\n".getBytes());

                byte[] dataBytes = new byte[6015000];
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filein.getAbsoluteFile()));
                while (-1 != (readLength = bufferedInputStream.read(dataBytes, 0, 6000000))) {
                    System.out.println("Writing file");
                    outStream.write(dataBytes, 0, readLength);
                }
                bufferedInputStream.close();
            }
            else if(filein.exists() && filein.isDirectory()){
                System.out.println("write method of dir");
                outStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                outStream.write(("ContentType: text/html\r\n").getBytes());
                outStream.write("\r\n".getBytes());
                outStream.write("<b>write method of dir!</b>".getBytes());
                File[] list = filein.listFiles();
                for (File temp: Objects.requireNonNull(list)) {
                    outStream.write((temp.getName()+"\n").getBytes());
                }
            }
            else {
                System.out.println("File not found");
                outStream.write("HTTP/1.1 404 NOTFOUND\r\n".getBytes());
                outStream.write("\r\n".getBytes());
            }
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOutputFile(byte[] databytes , int Length, String outfile) throws FileNotFoundException {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outfile));
            bufferedOutputStream.write(databytes, 0, Length);
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
