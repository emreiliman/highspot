package main;



import org.apache.logging.log4j.Level;

import lombok.extern.log4j.Log4j2;
import steps.ChangesApplier;
import steps.ChangesFileReader;
import steps.CmdLineParser;
import steps.IChangesApplier;
import steps.IChangesReader;
import steps.ICmdLineParser;
import steps.IOutputWriter;
import steps.InputFileReader;
import steps.OutputFileWriter;


@Log4j2
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        App app = new App();
        app.run(args);
    }
    
    public void run( String args[] ) throws Exception {
        ICmdLineParser cmdLineParser = new CmdLineParser();
        InputFileReader inputFileReader = new InputFileReader();
        IChangesReader changesFileReader = new ChangesFileReader();
        IChangesApplier changesApplier = new ChangesApplier();
        IOutputWriter outputFileWriter = new OutputFileWriter();
        
        PlaylistRepositoryModifier modifier = new PlaylistRepositoryModifier(cmdLineParser, 
                inputFileReader, 
                changesFileReader, 
                changesApplier, 
                outputFileWriter);
        
        try {
            modifier.run(args);
        }
        catch(Exception e) {
            log.log(Level.FATAL, e.getMessage());
        }
    }
}
