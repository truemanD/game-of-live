package gameoflive;

import gameoflive.screen.GameFrame;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author diyanov-a
 */
public class GameOfLive {

    private static String argMode = "console";
    private static Integer argFieldSize = 20;
    private static Integer argDelay = 100;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        parseCommandLine(args);
        System.out.println("mode: " + argMode + "; field size: " + argFieldSize + "; delay: " + argDelay);
        if (argMode.equals("console")) {
            consoleMode();
        } else if (argMode.equals("online")) {
            onlineMode();
        } else if (argMode.equals("offline")) {
            offlineMode();
        } else if (argMode.equals("interactive")) {
            interactiveMode();
        }
    }

    private static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

        } catch (IOException ex) {
            Logger.getLogger(GameOfLive.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (InterruptedException ex) {
            Logger.getLogger(GameOfLive.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void parseCommandLine(String[] args) {
        Options options = new Options();
        Option fieldSize = new Option("s", "fieldSize", true, "field size in points");
        fieldSize.setRequired(true);
        options.addOption(fieldSize);
        Option delay = new Option("d", "delay", true, "delay betwean days in ms");
        delay.setRequired(true);
        options.addOption(delay);
        Option mode = new Option("m", "mode", true, "game mode. Available values: console, online, offline, interactive; interactive is under construction ");
        mode.setRequired(true);
        options.addOption(mode);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("-s 20, -d 100, -m online", options);
            System.exit(1);
            return;
        }
        try {
            argFieldSize = Integer.valueOf(cmd.getOptionValue("fieldSize"));
            argDelay = Integer.valueOf(cmd.getOptionValue("delay"));
            argMode = cmd.getOptionValue("mode");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }

    private static void consoleMode() throws InterruptedException {
        PlayingFieldIface pf = PlayingField.getInstance(argFieldSize);
        System.out.println(pf.generateRandom());
        for (; ; ) {
            clearScreen();
            try {
                System.out.println(pf.goNextDay());
            } catch (GameOfLiveException ex) {
                System.out.println("Day:" + pf.getStepCounter() + "\n" + ex.getMessage());
                break;
            }
            Thread.sleep(argDelay);
        }
        System.exit(0);
    }

    private static void onlineMode() throws InterruptedException {
        PlayingFieldIface pf = PlayingField.getInstance(argFieldSize);
        pf.generateRandom();
        GameFrame gf = GameFrame.getInstance(pf.getField().size(), -1);
        gf.draw(pf.getField());
        for (; ; ) {
            try {
                Thread.sleep(argDelay);
                gf.draw(pf.goNextDay().getField());
            } catch (GameOfLiveException ex) {
                System.out.println(ex.getMessage());
                gf.draw(pf.getField());
                break;
            }
        }
    }

    private static void offlineMode() throws InterruptedException {
        PlayingFieldIface pf = PlayingField.getInstance(argFieldSize);
        pf.generateRandom();
        for (; ; ) {
            try {
                pf.goNextDay();
            } catch (GameOfLiveException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        }
        try {
            GameFrame gf = GameFrame.getInstance(argFieldSize, pf.getStepCounter());
            gf.draw(pf.goToDay(1).getField());
        } catch (GameOfLiveException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void interactiveMode() {
        PlayingFieldIface pf = PlayingField.getInstance(argFieldSize);
        GameFrame.getInstance(Integer.valueOf(argFieldSize), 0);
    }
}
