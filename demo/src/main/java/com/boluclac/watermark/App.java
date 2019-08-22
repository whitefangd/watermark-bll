package com.boluclac.watermark;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.boluclac.steganography.Algorithms;
import com.boluclac.steganography.algorithmface.OuputData;
import com.boluclac.steganography.algorithmface.StegoData;
import com.boluclac.steganography.exception.EncodeException;
import com.boluclac.steganography.factories.SteganographyFactory;
import com.boluclac.watermark.exception.AlgorithmException;
import com.boluclac.watermark.exception.BaseException;
import com.boluclac.watermark.exception.ConflictEnDeException;
import com.boluclac.watermark.exception.CoverException;
import com.boluclac.watermark.exception.OutputException;
import com.boluclac.watermark.exception.SecretException;
import com.boluclac.watermark.exception.StegoException;
import com.boluclac.watermark.exception.SyntaxException;

/**
 * Hello world!
 *
 */
public class App {

    private static final Options OPTIONS = new Options();

    private static final Option OPTION_ALGORITHM = new Option("al", "algorithm", true,
            "steganography algorithms:\n+     LSB: Least Significant Bit");
    private static final Option OPTION_COVER = new Option("c", "cover", true, "Cover file");
    private static final Option OPTION_SECRET = new Option("sc", "secret", true, "Secret file");
    private static final Option OPTION_STEGO = new Option("st", "stego", true, "Stego file");
    private static final Option OPTION_OUT = new Option("o", "out", true, "Output file");
    private static final Option OPTION_HELP = new Option("h", "help", false, "Help");
    private static final Option OPTION_ENCODE = new Option("E", "Encode", false, "Encode");
    private static final Option OPTION_DECODE = new Option("D", "Decode", false, "Decode");

    public static void main(String[] args) {

        OPTIONS.addOption(OPTION_ALGORITHM);
        OPTIONS.addOption(OPTION_COVER);
        OPTIONS.addOption(OPTION_SECRET);
        OPTIONS.addOption(OPTION_STEGO);
        OPTIONS.addOption(OPTION_OUT);
        OPTIONS.addOption(OPTION_HELP);
        OPTIONS.addOption(OPTION_ENCODE);
        OPTIONS.addOption(OPTION_DECODE);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(OPTIONS, args);
        } catch (ParseException e) {
            formatter.printHelp("Steganography", OPTIONS);
            System.exit(0);
        }

        Option[] optionCmds = cmd.getOptions();

        try {
            assentOption(optionCmds);
            runCommand(optionCmds);
            System.exit(1);
        } catch (BaseException e) {
            e.print();
            formatter.printHelp("Steganography", OPTIONS);
            System.exit(0);
        }
    }

    private static void runCommand(Option[] optionCmds) {
        Map<String, Option> mapOption = new HashMap<String, Option>();

        for (int i = 0; i < optionCmds.length; i++) {
            mapOption.put(optionCmds[i].getOpt(), optionCmds[i]);

        }

        if (mapOption.containsKey(OPTION_HELP.getOpt())) {
            runHelp();
        }

        if (mapOption.containsKey(OPTION_ENCODE.getOpt())) {
            runEncode(mapOption);
        }

        if (mapOption.containsKey(OPTION_DECODE.getOpt())) {
            runDecode(mapOption);
        }
    }

    private static void runDecode(Map<String, Option> mapOption) {
        SteganographyFactory factory = new SteganographyFactory();

        String algorithm = mapOption.get(OPTION_ALGORITHM.getOpt()).getValue();
        String stegoFile = mapOption.get(OPTION_STEGO.getOpt()).getValue();
        String outFile = mapOption.get(OPTION_OUT.getOpt()).getValue();

        try {
            OuputData out = factory.decode(algorithm, stegoFile);
            out.write(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runEncode(Map<String, Option> mapOption) {
        SteganographyFactory factory = new SteganographyFactory();

        String algorithm = mapOption.get(OPTION_ALGORITHM.getOpt()).getValue();
        String coverFile = mapOption.get(OPTION_COVER.getOpt()).getValue();
        String secretFile = mapOption.get(OPTION_SECRET.getOpt()).getValue();
        String stegoFile = mapOption.get(OPTION_STEGO.getOpt()).getValue();

        try {
            StegoData stego = factory.encode(algorithm, coverFile, secretFile);
            stego.write(stegoFile);
        } catch (EncodeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Steganography", OPTIONS);
    }

    private static void assentOption(Option[] optionCmds) throws BaseException {
        HelpFormatter formatter = new HelpFormatter();
        if (optionCmds == null || optionCmds.length <= 0) {
            formatter.printHelp("Steganography", OPTIONS);
            System.exit(1);
            return;
        }
        Map<String, Option> mapOption = new HashMap<String, Option>();

        for (int i = 0; i < optionCmds.length; i++) {
            mapOption.put(optionCmds[i].getOpt(), optionCmds[i]);

        }

        if (mapOption.containsKey(OPTION_HELP.getOpt()) && mapOption.size() > 1) {
            throw new SyntaxException();
        }

        if (mapOption.containsKey(OPTION_ENCODE.getOpt()) && mapOption.containsKey(OPTION_DECODE.getOpt())) {
            throw new ConflictEnDeException();
        }

        if (mapOption.containsKey(OPTION_ENCODE.getOpt())) {
            if (!mapOption.containsKey(OPTION_COVER.getOpt())) {
                throw new CoverException();
            }
            Option cover = mapOption.get(OPTION_COVER.getOpt());
            if (cover.getValue() == null || cover.getValue().isEmpty()) {
                throw new CoverException();
            }

            if (!mapOption.containsKey(OPTION_SECRET.getOpt())) {
                throw new SecretException();
            }
            Option secret = mapOption.get(OPTION_SECRET.getOpt());
            if (secret.getValue() == null || secret.getValue().isEmpty()) {
                throw new SecretException();
            }

            if (!mapOption.containsKey(OPTION_STEGO.getOpt())) {
                throw new StegoException();
            }
            Option stego = mapOption.get(OPTION_STEGO.getOpt());
            if (stego.getValue() == null || stego.getValue().isEmpty()) {
                throw new StegoException();
            }

            if (!mapOption.containsKey(OPTION_ALGORITHM.getOpt())) {
                throw new AlgorithmException();
            }
            Option algorithm = mapOption.get(OPTION_ALGORITHM.getOpt());
            if (algorithm.getValue() == null || algorithm.getValue().isEmpty()
                    || !checkAlgorithm(algorithm.getValue())) {
                throw new AlgorithmException();
            }
        }

        if (mapOption.containsKey(OPTION_DECODE.getOpt())) {
            if (!mapOption.containsKey(OPTION_STEGO.getOpt())) {
                throw new StegoException();
            }
            Option stego = mapOption.get(OPTION_STEGO.getOpt());
            if (stego.getValue() == null || stego.getValue().isEmpty()) {
                throw new StegoException();
            }

            if (!mapOption.containsKey(OPTION_OUT.getOpt())) {
                throw new OutputException();
            }
            Option out = mapOption.get(OPTION_OUT.getOpt());
            if (out.getValue() == null || out.getValue().isEmpty()) {
                throw new OutputException();
            }

            if (!mapOption.containsKey(OPTION_ALGORITHM.getOpt())) {
                throw new AlgorithmException();
            }
            Option algorithm = mapOption.get(OPTION_ALGORITHM.getOpt());
            if (algorithm.getValue() == null || algorithm.getValue().isEmpty()
                    || !checkAlgorithm(algorithm.getValue())) {
                throw new AlgorithmException();
            }
        }
    }

    private static boolean checkAlgorithm(String value) {
        if (Algorithms.LSB.name().equals(value)) {
            return true;
        }
        return false;
    }
}
