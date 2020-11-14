import presentation.FileParser;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Expected one argument: <file_path>");
            return;
        }

        FileParser fileParser = new FileParser(args[0]);

        fileParser.parseFile();
    }
}
