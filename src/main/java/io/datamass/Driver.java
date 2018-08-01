package io.datamass;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.datamass.config.GeneratorConfig;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;

import static org.kohsuke.args4j.OptionHandlerFilter.ALL;

// jar --numberOfcolumns 5 --uniqnessPercent 100


/**
 * Created by Jakub Wszolek on 31/07/2018.
 */
public class Driver {

    @Option(name="-config",usage="json config file")
    private String configFile = "";

    public static void main(String[] args) throws IOException{
        new Driver().doMain(args);
    }

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            parser.parseArgument(args);

        } catch( CmdLineException e ) {
            System.err.println(e.getMessage());
            System.err.println("java ParquetGenerator [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            System.err.println("Example: java ParquetGenerator"+parser.printExample(ALL));
            return;
        }


        if(!configFile.isEmpty()){
            GeneratorConfig config = objectMapper.readValue(new File(configFile), GeneratorConfig.class);

            System.out.println(configFile);
        }
    }
}
