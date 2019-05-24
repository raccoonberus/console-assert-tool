package com.gfb.tools.consoleAssert.config;

public class Config {
    private final String workDir;
    private final String programBin;
    private final String programArgs;
    private final long timeLimit;
    private final long memoryLimit;

    private final String input;
    private final String expectedOutput;

    public Config(String workDir, String programBin, String programArgs, long timeLimit, long memoryLimit,
                  String input, String expectedOutput) {
        this.workDir = workDir;
        this.programBin = programBin;
        this.programArgs = programArgs;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    public String getWorkDir() {
        return workDir;
    }

    public String getProgramBin() {
        return programBin;
    }

    public String getProgramArgs() {
        return programArgs;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public long getMemoryLimit() {
        return memoryLimit;
    }

    public String getInput() {
        return input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }
}
