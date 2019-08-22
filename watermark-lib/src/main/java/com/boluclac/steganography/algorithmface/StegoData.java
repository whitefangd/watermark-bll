package com.boluclac.steganography.algorithmface;

import java.io.File;
import java.io.IOException;

public interface StegoData {

    public abstract void write(File file) throws IOException;

    public abstract void write(String filePath) throws IOException;
}
