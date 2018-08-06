package io.datamass;

import io.datamass.config.Config;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by Jakub Wszolek on 31/07/2018.
 */
public class ParquetProducer {

    private HashMap<String, RandomValue> currentValues;
    private Config config;
    private Schema parquetSchema;

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();


    public ParquetProducer(Config config, Schema parquetSchema, HashMap<String, RandomValue> valueMap){
        this.currentValues = new HashMap<>(valueMap);
        this.config = config;
        this.parquetSchema = parquetSchema;
    }

    public void save() throws IOException {

        Files.deleteIfExists(Paths.get(config.getOutputFile()));

        try (ParquetWriter<GenericRecord> parquetWriter
                     = AvroParquetWriter.<GenericRecord>builder(new Path(config.getOutputFile()))
                .withCompressionCodec(CompressionCodecName.SNAPPY)
                .withSchema(parquetSchema).build()) {

            IntStream.range(0, config.getRows()).boxed()
                    .map(i -> record(parquetSchema, i))
                    .forEach(r -> {
                        try {
                            System.out.println(r.toString());
                            parquetWriter.write(r);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
    }


    private String generateRandomString(int len) {
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    private Integer generateRandomInt() {
        int min = 0;
        int max = 10000;

        Random rand = new Random();
        Integer randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private <T> T getValue(String type){
        if(type.contains("string")){
            return (T) generateRandomString(10);
        }

        if(type.contains("int")){
            return (T) generateRandomInt();
        }

        return null;
    }


    private  GenericRecord record(Schema parquetSchema, int id) {
        GenericRecord record = new GenericData.Record(parquetSchema);

        for (String s : this.currentValues.keySet()) {

            if (this.currentValues.get(s).numberOfUniqueValues > 0) {

                this.currentValues.get(s).numberOfUniqueValues--;
                this.currentValues.get(s).lastValue = getValue(this.currentValues.get(s).type).toString();
                record.put(s, this.currentValues.get(s).lastValue);
                continue;
            }

            if (this.currentValues.get(s).numberOfNullValues > 0) {

                this.currentValues.get(s).numberOfNullValues--;
                this.currentValues.get(s).lastValue = "null";
                record.put(s, "null");
                continue;
            }

            if (this.currentValues.get(s).numberOfRestValues > 0) {

                this.currentValues.get(s).numberOfRestValues--;
                this.currentValues.get(s).lastValue = getValue(this.currentValues.get(s).type);
                record.put(s, this.currentValues.get(s).lastValue);
                continue;
            }
        }

        return record;
    }
}
