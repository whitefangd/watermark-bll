package com.boluclac.steganography.lsb;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.boluclac.steganography.algorithmface.Algorithm;
import com.boluclac.steganography.algorithmface.CoverData;
import com.boluclac.steganography.algorithmface.OuputData;
import com.boluclac.steganography.algorithmface.SecretData;
import com.boluclac.steganography.algorithmface.StegoData;
import com.boluclac.steganography.exception.CoverDataEncodeException;
import com.boluclac.steganography.exception.EncodeException;
import com.boluclac.steganography.exception.OutOfSizeEncodeException;
import com.boluclac.steganography.exception.SecretDataEncodeException;

public class LSBAlgorithm implements Algorithm {

    @Override
    public StegoData encode(CoverData input, SecretData data) throws EncodeException {
        if (!(input instanceof LSBCoverData)) {
            throw new CoverDataEncodeException();
        }
        if (!(data instanceof LSBSecretData)) {
            throw new SecretDataEncodeException();
        }
        LSBCoverData inputOrigin = (LSBCoverData) input;
        int[] dataBytes = data.getBytes();
        BufferedImage inputBytes = inputOrigin.getImage();

        if (inputBytes.getHeight() * inputBytes.getWidth() < dataBytes.length + 1) {
            throw new OutOfSizeEncodeException();
        }
        int index = 0;
        boolean flag = true;
        for (int y = 0; y < inputBytes.getHeight(); y++) {
            for (int x = 0; x < inputBytes.getWidth(); x++) {
                if (index < dataBytes.length) {
                    if (dataBytes[index] == 1) {
                        inputBytes.setRGB(x, y, (inputBytes.getRGB(x, y) | 0x1));
                    } else {
                        inputBytes.setRGB(x, y, (inputBytes.getRGB(x, y) & 0xFFFFFFFE));
                    }
                } else if (flag) {
                    inputBytes.setRGB(x, y, inputBytes.getRGB(x, y) | 0x1);
                    flag = false;
                } else {
                    inputBytes.setRGB(x, y, inputBytes.getRGB(x, y) & 0xFFFFFFFE);
                }
                index++;
            }
        }

        return new LSBStegoData(inputBytes);
    }

    @Override
    public OuputData decode(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            BufferedImage image = ImageIO.read(fileInputStream);
            int indexByte = 0;
            int b = 0x0;
            List<Integer> bytes = new ArrayList<>();
            int indexList = 0;
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    b = ((b << 1) | (image.getRGB(x, y) & 1));
                    indexByte++;
                    if (indexByte >= 8) {
                        bytes.add(b);
                        if (b != 0) {
                            indexList = bytes.size();
                        }
                        indexByte = 0;
                        b = 0x0;
                    }
                }
            }
            byte[] bytesData = new byte[indexList - 1];
            for (int i = 0; i < indexList - 1; i++) {
                bytesData[i] = bytes.get(i).byteValue();
            }
            return new LSBOuputData(bytesData);
        }
    }

    @Override
    public StegoData encode(byte[] input, byte[] data) throws EncodeException {
        LSBCoverData originalData = new LSBCoverData(input);
        LSBSecretData hiddenData = new LSBSecretData(data);
        return encode(originalData, hiddenData);
    }

    @Override
    public StegoData encode(File input, File data) throws EncodeException {
        LSBCoverData originalData = new LSBCoverData(input);
        LSBSecretData hiddenData = new LSBSecretData(data);
        return encode(originalData, hiddenData);
    }

    @Override
    public StegoData encode(String inputFilePath, String hiddenFilePath) throws EncodeException {
        File input = new File(inputFilePath);
        File data = new File(hiddenFilePath);
        return encode(input, data);
    }

}
