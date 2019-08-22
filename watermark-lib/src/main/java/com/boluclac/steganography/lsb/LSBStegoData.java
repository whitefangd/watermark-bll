package com.boluclac.steganography.lsb;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.boluclac.steganography.algorithmface.StegoData;

public class LSBStegoData implements StegoData {
    BufferedImage output;

    public LSBStegoData(BufferedImage inputBytes) {
        output = inputBytes;
    }

    @Override
    public void write(File file) throws IOException {
        if (!file.exists()) {
            File abFile = file.getAbsoluteFile();
            File parent = abFile.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new FileNotFoundException();
            }
        } else if (!file.isFile()) {
            throw new FileNotFoundException();
        }

        ImageIO.write(output, "bmp", file);
    }

    @Override
    public void write(String filePath) throws IOException {
        File file = new File(filePath);
        write(file);
    }

}
