package com.ResourcesHandling;

import java.io.IOException;
import java.io.OutputStream;

public class ResourceHandler {

    public void getMeThisResource(OutputStream outputStream, String filepath) throws IOException {
        InputOutputStreamHandler inputOutputStream = new InputOutputStreamHandler();
        inputOutputStream.readInputFile(outputStream, filepath);
    }
    public void putThisResource(){}
}
