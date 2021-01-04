package steps;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import main.PlaylistRepositoryModifierParams;

public class CmdLineParser implements ICmdLineParser {

    public static final String SHORT_INPUT_FILE_OPTION = "i";
    public static final String SHORT_CHANGE_FILE_OPTION = "c";
    public static final String SHORT_OUTPUT_FILE_OPTION = "o";
    public static final String LONG_INPUT_FILE_OPTION = "inputFilePath";
    public static final String LONG_CHANGE_FILE_OPTION = "changeFilePath";
    public static final String LONG_OUTPUT_FILE_OPTION = "outputFilePath";
    
    @Override
    public PlaylistRepositoryModifierParams parseCommandLine(String[] args) throws Exception {
        Options options = new Options();
        options.addRequiredOption(SHORT_INPUT_FILE_OPTION, LONG_INPUT_FILE_OPTION, true, "input file path");
        options.addRequiredOption(SHORT_CHANGE_FILE_OPTION, LONG_CHANGE_FILE_OPTION, true, "change file path");
        options.addRequiredOption(SHORT_OUTPUT_FILE_OPTION, LONG_OUTPUT_FILE_OPTION, true, "output file path");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse( options, args);
        } catch (Exception e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar highspot-0.0.1.jar", options);
            throw new Exception("Problem parsing the parameters.", e);
        }
        
        return new PlaylistRepositoryModifierParams(cmd.getOptionValue(LONG_INPUT_FILE_OPTION),
                cmd.getOptionValue(LONG_CHANGE_FILE_OPTION),
                cmd.getOptionValue(LONG_OUTPUT_FILE_OPTION));
    }
}
