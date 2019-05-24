package com.gfb.tools.consoleAssert;

import com.gfb.tools.consoleAssert.config.Config;
import com.gfb.tools.consoleAssert.config.ConfigParser;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.Date;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        Config config = null;
        ConfigParser parser = new ConfigParser();
        try {
            config = parser.extract(args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            parser.showHelpMessage();
            System.exit(1);
        }

        final Config cfg = config;
        final StringBuilder sb = new StringBuilder();

        final Date startTask = new Date();
        final Date finishTask = new Date();

        Thread programExecThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] s = new String[]{
                        "bash", "-c",
                        "cd " + cfg.getWorkDir() + " && " + cfg.getProgramBin() + " " + cfg.getProgramArgs()
                };

                startTask.setTime(new Date().getTime());

                ProcessBuilder pb = new ProcessBuilder(s);
                pb.redirectErrorStream(true);
                Process process = null;
                try {
                    process = pb.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                if (cfg.getInput() != null && !cfg.getInput().isEmpty()) {
                    OutputStream stdin = process.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
                    try {
                        writer.write(cfg.getInput());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while (true) {
                    try {
                        if ((line = reader.readLine()) == null)
                            break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }
                finishTask.setTime(new Date().getTime());
            }
        });
        programExecThread.start();
        programExecThread.join((long) (config.getTimeLimit() * 1.5));

        String result;
        result = sb.toString();
        result = result.trim();

        long spendTime = finishTask.getTime() - startTask.getTime();
        if (spendTime > config.getTimeLimit()) {
            System.err.println("FAIL:\n" +
                    "\tSpend: " + spendTime + " ms.");
            System.exit(1);
        } else {
            System.out.println("Spend: " + spendTime + " ms.");
        }

        if (!result.equals(config.getExpectedOutput())) {
            System.err.println("FAIL:\n" +
                    "\tExpected: '" + config.getExpectedOutput() + "'\n" +
                    "\tActual:   '" + result + "'");
            System.exit(1);
        } else {
            System.out.println("OK");
        }

    }

}
