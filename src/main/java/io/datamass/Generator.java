package io.datamass;

import io.datamass.config.Config;
import org.apache.avro.Schema;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Jakub Wszolek on 31/07/2018.
 */
public class Generator {

    public static Schema produceSchema(Config config) throws JSONException{

            JSONObject jobj = new JSONObject().put("type", "record")
                    .put("name", "recordsGenerator")
                    .put("namespace", "io.datamass");

            JSONArray columns = new JSONArray();

            config.getColumns().stream().forEach(item -> {

                try {
                    JSONObject column = new JSONObject();
                    column.put("name", item.getName());

                    JSONArray typeArray = new JSONArray();
                    item.getType().stream().forEach(type -> typeArray.put(type));
                    column.put("type",typeArray);

                    columns.put(column);

                } catch (Exception ex) {
                    System.err.println(ex);
                }
            });

            jobj.put("fields",columns);

        return new Schema.Parser().parse(jobj.toString());
    }

    public static HashMap<String, RandomValue> generateRandomValues(Config config){
        HashMap<String, RandomValue> randomValues = new HashMap<>();

        Integer rows = config.getRows();

        config.getColumns().forEach(item -> {
            String name = item.getName();
            RandomValue rValue = new RandomValue();

            if(item.getType().size() == 1 && item.getType().stream().map(x -> x.toUpperCase()).anyMatch(y -> y.contains("NULL"))){
                rValue.numberOfNullValues = rows;
            }
            else if(item.getType().stream().map(x -> x.toUpperCase()).anyMatch(y -> y.contains("NULL"))){
                double nullValues = rows * 0.0;
                rValue.numberOfNullValues = (int)nullValues;
            }
            else{
                rValue.numberOfNullValues = 0;
            }

            rValue.type = item.getType().stream().filter(x -> !x.toUpperCase().contains("NULL")).findFirst().get();

            double numberOfUniqueValues = (rows - rValue.numberOfNullValues) * item.getUniquenessPercentage()/100;
            rValue.numberOfUniqueValues = (int)numberOfUniqueValues;
            rValue.numberOfRestValues = rows - (rValue.numberOfNullValues + rValue.numberOfUniqueValues);

            randomValues.put(name, rValue);
        });

        return randomValues;
    }
}

class RandomValue {
    public String type;
    public Integer numberOfNullValues;
    public Integer numberOfUniqueValues;
    public Integer numberOfRestValues;

    public String lastValue;
}
