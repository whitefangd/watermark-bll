package com.boluclac.steganography.algorithmface;

import java.io.File;
import java.io.IOException;

import com.boluclac.steganography.exception.EncodeException;

/**
 * Algorithm interface
 * 
 * @author Bo Luc Lac
 *
 */
public interface Algorithm {

    /**
     * Decode Stego file to output data.
     * 
     * @param stegoFile Stego file {@link File}
     * @return Output data {@link OuputData}
     * @throws IOException when decode failed
     */
    OuputData decode(File stegoFile) throws IOException;

    /**
     * Steganography encode embed secret data into cover data.
     * 
     * @param coverData  Cover data {@link CoverData}
     * @param secretData Secret data {@link SecretData}
     * @return Stego Data {@link StegoData}
     * @throws EncodeException
     */
    StegoData encode(CoverData coverData, SecretData secretData) throws EncodeException;

    StegoData encode(byte[] input, byte[] data) throws EncodeException;

    StegoData encode(File input, File data) throws EncodeException;

    StegoData encode(String inputFilePath, String hiddenFilePath) throws EncodeException;

}
