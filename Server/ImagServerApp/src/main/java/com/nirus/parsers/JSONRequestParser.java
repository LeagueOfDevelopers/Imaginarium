package com.nirus.parsers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by ndiezel on 26.11.2015.
 */
public class JSONRequestParser {
    public JSONRequestParser(HttpServletRequest request){
        _request = request;
    }
    public StringBuffer GetStringBuffer() throws IOException {
        if(_bufferOfJson != null){
            return _bufferOfJson;
        }
        else{
            return ParseToString();
        }
    }
    public JsonObject GetJSONObject() throws IOException {

        if(_readyJson != null){
            return _readyJson;
        }
        else{
            return ParseToJSONObject();
        }
    }
    public String GetStringByKey(String key) throws IOException {
        if(_readyJson == null)
            GetJSONObject();
        JsonElement element = _readyJson.get(key);
        return element.getAsString();
    }
    private StringBuffer ParseToString() throws IOException {
        String line;
        _bufferOfJson = new StringBuffer();
        BufferedReader reader = _request.getReader();
        while ((line = reader.readLine()) != null) {
            _bufferOfJson.append(line);
        }
        return _bufferOfJson;
    }
    private JsonObject ParseToJSONObject() throws IOException {
        if(_bufferOfJson == null)
            ParseToString();
        JsonObject object = new JsonObject();
        JsonParser parser = new JsonParser();
        _readyJson = parser.parse(_bufferOfJson.toString()).getAsJsonObject();
        return _readyJson;
    }
    private JsonObject _readyJson;
    private HttpServletRequest _request;
    private StringBuffer _bufferOfJson;
}
