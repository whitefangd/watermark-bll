package com.boluclac.steganography.factories;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import com.boluclac.steganography.Algorithms;
import com.boluclac.steganography.algorithmface.Algorithm;
import com.boluclac.steganography.algorithmface.CoverData;
import com.boluclac.steganography.algorithmface.OuputData;
import com.boluclac.steganography.algorithmface.SecretData;
import com.boluclac.steganography.algorithmface.StegoData;
import com.boluclac.steganography.exception.EncodeException;
import com.boluclac.steganography.lsb.LSBAlgorithm;

public class SteganographyFactory {

    private static final Map<Algorithms, Algorithm> ALGORITHMS = new EnumMap<>(Algorithms.class);

    public Algorithm instant(Algorithms algorithm) {
        Algorithms algorithmtemp = algorithm;

        if (algorithmtemp == null) {
            algorithmtemp = Algorithms.LSB;
        }

        Algorithm algorithmObject = ALGORITHMS.get(algorithmtemp);
        if (algorithmObject == null) {
            algorithmObject = getAlgorithm(algorithmtemp);
            ALGORITHMS.put(algorithmtemp, algorithmObject);
        }

        return algorithmObject;
    }

    public StegoData encode(Algorithms algorithm, CoverData input, SecretData data) throws EncodeException {
        Algorithm algorithmImpl = instant(algorithm);
        return algorithmImpl.encode(input, data);
    }

    public StegoData encode(Algorithms algorithm, byte[] input, byte[] data) throws EncodeException {
        Algorithm algorithmImpl = instant(algorithm);
        return algorithmImpl.encode(input, data);
    }

    public StegoData encode(Algorithms algorithm, File input, File data) throws EncodeException {
        Algorithm algorithmImpl = instant(algorithm);
        return algorithmImpl.encode(input, data);
    }

    protected Algorithm getAlgorithm(Algorithms algorithm) {
        Algorithm algorithmObject = null;

        if (algorithm == Algorithms.LSB) {
            algorithmObject = new LSBAlgorithm();
        } else {
            algorithmObject = new LSBAlgorithm();
        }

        return algorithmObject;
    }

    public OuputData decode(Algorithms algorithm, File file) throws IOException {
        Algorithm algorithmImpl = instant(algorithm);
        return algorithmImpl.decode(file);
    }

    public StegoData encode(Algorithms algorithm, String inputFilePath, String hiddenFilePath) throws EncodeException {
        Algorithm algorithmImpl = instant(algorithm);
        return algorithmImpl.encode(inputFilePath, hiddenFilePath);
    }

    public StegoData encode(String algorithm, String input, String data) throws EncodeException {
        Algorithms algorithms = Algorithms.LSB;
        if (Algorithms.LSB.name().equals(algorithm)) {
            algorithms = Algorithms.LSB;
        }
        return encode(algorithms, input, data);
    }

    public OuputData decode(String algorithm, String filePath) throws IOException {
        Algorithms algorithms = Algorithms.LSB;
        if (Algorithms.LSB.name().equals(algorithm)) {
            algorithms = Algorithms.LSB;
        }
        return decode(algorithms, filePath);
    }

    private OuputData decode(Algorithms algorithm, String filePath) throws IOException {
        File file = new File(filePath);
        return decode(algorithm, file);
    }
}
