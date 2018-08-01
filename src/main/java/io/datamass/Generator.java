package io.datamass;

import io.datamass.config.Config;
import org.apache.avro.Schema;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by Jakub Wszolek on 31/07/2018.
 */
public class Generator {

    public static Schema produceSchema(Config config) throws JSONException{

            JSONObject jobj = new JSONObject().put("type", "recordsGenerator")
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


}
