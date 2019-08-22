package com.boluclac.steganography.lsb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import com.boluclac.steganography.algorithmface.SecretData;
import com.boluclac.steganography.exception.EncodeException;

public class LSBSecretData implements SecretData {

    protected byte[] hiddenData;

    public LSBSecretData(String data) {
        hiddenData = convertToBytes(data);
    }

    public LSBSecretData(byte[] data) {
        hiddenData = convertToBytes(data);
    }

    public LSBSecretData(File data) throws EncodeException {
        hiddenData = convertToBytes(data);
    }

    @Override
    public int[] getBytes() {
        byte[] bytes = hiddenData;
        int[] bytesTotal = new int[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            bytesTotal[i * 8 + 0] = (byte) (bytes[i] >> 7 & 0x1);
            bytesTotal[i * 8 + 1] = (byte) (bytes[i] >> 6 & 0x1);
            bytesTotal[i * 8 + 2] = (byte) (bytes[i] >> 5 & 0x1);
            bytesTotal[i * 8 + 3] = (byte) (bytes[i] >> 4 & 0x1);
            bytesTotal[i * 8 + 4] = (byte) (bytes[i] >> 3 & 0x1);
            bytesTotal[i * 8 + 5] = (byte) (bytes[i] >> 2 & 0x1);
            bytesTotal[i * 8 + 6] = (byte) (bytes[i] >> 1 & 0x1);
            bytesTotal[i * 8 + 7] = (byte) (bytes[i] & 0x1);
        }
        return bytesTotal;
    }

    private byte[] convertToBytes(String data) {
        return data.getBytes();
    }

    private byte[] convertToBytes(byte[] data) {
        return Arrays.copyOf(data, data.length);
    }

    private byte[] convertToBytes(File data) throws EncodeException {
        try (FileInputStream stream = new FileInputStream(data)) {
            byte[] bytes = new byte[(int) data.length()];
            stream.read(bytes);
            return bytes;
        } catch (IOException e) {
            throw new EncodeException(e);
        }
    }
}
