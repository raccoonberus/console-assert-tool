package com.gfb.tools.consoleAssert.config;

import org.apache.commons.cli.*;

public class ConfigParser {

    private Options options;
    private CommandLineParser parser;
    private HelpFormatter formatter;

    public ConfigParser() {
        options = new Options();

        Option workDir = new Option("w", "work-dir", true, "input file path");
        workDir.setRequired(true);
        options.addOption(workDir);

        Option programName = new Option("pn", "program-name", true, "output file");
        programName.setRequired(true);
        options.addOption(programName);

        Option programArgs = new Option("pa", "program-args", true, "output file");
        programArgs.setRequired(true);
        options.addOption(programArgs);

        Option timeLimit = new Option("tl", "time-limit", true, "output file");
        timeLimit.setRequired(false);
        options.addOption(timeLimit);

        Option memoryLimit = new Option("ml", "memory-limit", true, "output file");
        memoryLimit.setRequired(false);
        options.addOption(memoryLimit);

        Option input = new Option("i", "input", true, "output file");
        input.setRequired(false);
        options.addOption(input);

        Option expectedOutput = new Option("eo", "expected-output", true, "output file");
        expectedOutput.setRequired(true);
        options.addOption(expectedOutput);

        parser = new DefaultParser();
        formatter = new HelpFormatter();
    }

    public Config extract(String[] args) throws ParseException {
        CommandLine cmd = parser.parse(options, args);
        long timeLimit = null != cmd.getParsedOptionValue("time-limit")
                ? Long.parseLong(cmd.getOptionValue("time-limit"))
                : -1;
        long memoryLimit = null != cmd.getParsedOptionValue("memory-limit")
                ? Long.parseLong(cmd.getOptionValue("memory-limit"))
                : -1;
        String input = null != cmd.getParsedOptionValue("input")
                ? cmd.getOptionValue("input")
                : null;
        return new Config(
                cmd.getOptionValue("work-dir"),
                cmd.getOptionValue("program-name"),
                cmd.getOptionValue("program-args"),
                //
                timeLimit,
                memoryLimit,
                //
                input,
                cmd.getOptionValue("expected-output")
        );
    }

    public void showHelpMessage() {
        formatter.printHelp("console-assert-tool", options);
    }

}
