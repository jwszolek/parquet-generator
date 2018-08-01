package io.datamass;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

/**
 * Created by kubaw on 31/07/2018.
 */
public class ParquetUtils {

    public static void writer() throws IOException {

        Files.deleteIfExists(Paths.get("/tmp/parquet/data.parquet"));

        String schemaLocation = "./src/main/resources/sampleSchema.json";
        Schema avroSchema = new Schema.Parser().parse(new File(schemaLocation));

        String path = "file:///tmp/parquet/data.parquet";

        try (ParquetWriter<GenericRecord> parquetWriter
                     = AvroParquetWriter.<GenericRecord>builder(new Path(path))
                .withCompressionCodec(CompressionCodecName.UNCOMPRESSED)
                .withSchema(avroSchema).build()) {

            IntStream.range(0, 10).boxed()
                    .map(i -> record(avroSchema, i))
                    .forEach(r -> {
                        try {
                            parquetWriter.write(r);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }


    private static GenericRecord record(Schema avroSchema, int id) {
        GenericRecord record = new GenericData.Record(avroSchema);
        record.put("id", id);
        record.put("useragent", "LeUserAgent");
        record.put("ip", "10.0.0." + id);
        record.put("path", "/path/" + id);
        return record;
        }

}
