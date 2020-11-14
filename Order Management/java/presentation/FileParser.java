package presentation;

import java.io.File;
import java.util.Scanner;

/** FileParser Class
 *
 * Parses a file line by line and feeds the lines to a command interpreter.
 */

public class FileParser {
    private String inputFile;

    public FileParser(String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Parses the file line by line.
     */
    public void parseFile() {
        try {
            File file = new File(inputFile);
            Scanner reader = new Scanner(file);
            CommandInterpreter commandInterpreter = new CommandInterpreter();

            while (reader.hasNextLine()) {
                String command = reader.nextLine();
                System.out.println("COMAND IS ===>" + command);
                commandInterpreter.interpretCommand(command);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
