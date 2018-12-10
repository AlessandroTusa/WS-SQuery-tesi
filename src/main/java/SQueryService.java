
import static com.sun.deploy.trace.Trace.print;
import static spark.Spark.*;

import ai.api.model.AIResponse;

import com.google.gson.Gson;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.vdurmont.emoji.EmojiParser;

import ai.api.GsonFactory;
import ai.api.model.Fulfillment;
import spark.Spark;
import sun.plugin2.message.Message;


public class SQueryService {
public static void main(String[] args) {

    Gson gson = GsonFactory.getDefaultFactory().getGson();

    post("/correggi", (request, response) -> {

        Fulfillment output = new Fulfillment();
        doWebhook(    gson.fromJson(request.body(), AIResponse.class)    , output);
        response.type("application/json");

        return output;
    }, gson::toJson);

}
private static String correzioneFormatoRipetizione(String candidata)  {
        candidata=candidata.substring(1);
            if(candidata.contains("\"")) {
                if (candidata.indexOf("\"") == candidata.length() - 1)
                    candidata = candidata.replace(("\""), "");
                else candidata = candidata.substring(candidata.indexOf("\"") + 1);
            }

            if(candidata.contains("\""))
                candidata=candidata.replace(("\""),"");

            return candidata;
    }
private static void doWebhook(AIResponse input, Fulfillment output) {
    //SQueryDao taskDao = new SQueryDao();
    String text = "";
        Query query;
        if (input.getResult().getAction().equalsIgnoreCase("azione")) {
            String candidata="" ;
            candidata += input.getResult().getResolvedQuery();
            candidata=candidata.toUpperCase(); //database tutto in maiusco
            int a =candidata.indexOf("SELECT");

                if(a>1) {//caso striga che precede la query
                    candidata= candidata.substring(a);
                    candidata=candidata.replace("\"","");
                }

                if(candidata.startsWith("\""))// caso "query"
                    candidata=correzioneFormatoRipetizione(candidata);

                candidata=candidata.replace("\"","");// copertura, caso query"

            query = SQueryDao.correggi(candidata.trim());

            if (query == null) {
                text += "La tua query non è presente nel database!";
            } else {
                text += query.getSUGGERIMENTO();
                }
        }
    output.setSpeech(text);
    output.setDisplayText(text);
        }


}

