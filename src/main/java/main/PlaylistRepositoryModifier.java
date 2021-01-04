package main;

import java.util.List;

import data.ChangeData;
import data.InputCollection;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import repository.IRepository;
import repository.Repository;
import steps.IChangesApplier;
import steps.IChangesReader;
import steps.ICmdLineParser;
import steps.IInputReader;
import steps.IOutputWriter;

@AllArgsConstructor
@Log4j2
public class PlaylistRepositoryModifier {

    @NonNull
    private ICmdLineParser cmdLineParser;
    
    @NonNull
    private IInputReader inputFileReader;
    
    @NonNull
    private IChangesReader changesFileReader;
    
    @NonNull
    private IChangesApplier changesApplier;
    
    @NonNull
    private IOutputWriter outputFileWriter;
    
    public void run(String args[]) throws Exception {
        log.info("Parsing command line parameters"); 
        PlaylistRepositoryModifierParams params = cmdLineParser.parseCommandLine(args);
        
        log.info("Parsing input file");
        InputCollection inputCollection = inputFileReader.parseInput(params.getInputAbsoluteFilePath());
        IRepository repository = new Repository(inputCollection);
        
        log.info("Parsing changes file");
        List<ChangeData> changes = changesFileReader.parseChangeFile(params.getChangesAbsoluteFilePath());
        
        log.info("Applying changes");
        changesApplier.applyChanges(repository, changes);
        
        log.info("Writing output file");
        outputFileWriter.writeOuput(repository.getDataForOutput(), params.getOutputAbsoluteFilePath());
    }
}
