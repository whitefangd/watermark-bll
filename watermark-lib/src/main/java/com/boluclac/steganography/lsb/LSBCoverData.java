package com.boluclac.steganography.lsb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.boluclac.steganography.algorithmface.CoverData;
import com.boluclac.steganography.exception.EncodeException;

public class LSBCoverData implements CoverData {

    BufferedImage image = null;

    public LSBCoverData(byte[] data) throws EncodeException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new EncodeException(e);
        }
    }

    public LSBCoverData(String filePath) throws EncodeException {
        File file = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            image = ImageIO.read(fileInputStream);
        } catch (IOException e) {
            throw new EncodeException(e);
        }
    }

    public LSBCoverData(File file) throws EncodeException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            image = ImageIO.read(fileInputStream);
        } catch (IOException e) {
            throw new EncodeException(e);
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
