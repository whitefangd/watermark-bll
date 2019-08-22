package com.boluclac.steganography.lsb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.boluclac.steganography.algorithmface.OuputData;

public class LSBOuputData implements OuputData {

    byte[] data = null;

    public LSBOuputData(byte[] bytesData) {
        data = bytesData;
    }

    @Override
    public void write(File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(data);
        }
    }

    @Override
    public void write(String filePath) throws IOException {
        File file = new File(filePath);
        write(file);
    }

}
