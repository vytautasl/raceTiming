package lt.agmis.raceLive.faces.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: VytautasL
 * Date: 12/25/13
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallUtils {

    public static Object getCall(String url, Class sampleClass, HashMap params)
    {
        RestTemplate template = new RestTemplate();
        String callPath = (String)Messages.getSessionAttribute("api-call-path");
        ResponseEntity schemaListEntity = template.getForEntity(callPath+url, sampleClass, params);
        return schemaListEntity.getBody();
    }


    public static Object postCall(String url, Object postingObject, Class sampleClass, HashMap params)
    {
        RestTemplate template = new RestTemplate();
        String callPath = (String)Messages.getSessionAttribute("api-call-path");
        ResponseEntity schemaListEntity = template.postForEntity(callPath+url, postingObject, sampleClass, params);
        return schemaListEntity.getBody();
    }
}
