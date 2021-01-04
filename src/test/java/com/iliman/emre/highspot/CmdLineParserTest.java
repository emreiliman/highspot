package com.iliman.emre.highspot;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.PlaylistRepositoryModifierParams;
import steps.CmdLineParser;

public class CmdLineParserTest {
    @Test
    public void Given_ValidCmdOptions_When_ParseOptions_Then_ReturnCorrectOptionValues() throws Exception
    {
        CmdLineParser parser = new CmdLineParser();
        PlaylistRepositoryModifierParams params = 
                parser.parseCommandLine(new String[] {"app.exe","-input","abc","-change","efg","-output","xyz"});
        assertTrue(params.getInputAbsoluteFilePath().equals("abc"));
        assertTrue(params.getChangesAbsoluteFilePath().equals("efg"));
        assertTrue(params.getOutputAbsoluteFilePath().equals("xyz"));
    }
    
    @Test(expected = Exception.class)
    public void Given_InvalidCmdOptions_When_ParseOptions_Then_Throw() throws Exception
    {
        CmdLineParser parser = new CmdLineParser();
        parser.parseCommandLine(new String[] {"app.exe","-X","abc","-c","efg","-o","xyz"});
    }
}
