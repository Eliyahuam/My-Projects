package com.example.eliyahugalfinal.picapp.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eliba on 28/03/2017.
 */

public class Picture {

    public String descreption;
    public String imageName;
    public Double lastUpdate;
    public String url;

public Map<String,Object> toFirebase() {
    HashMap<String,Object> json = new HashMap<>();
    json.put("descreption",descreption);
    json.put("imageName",imageName);
    json.put("lastUpdate", ServerValue.TIMESTAMP);
    json.put("url",url);

    return json;
}

public Map<String,Object> imageSearchtoFirebase() {
        HashMap<String, Object> json = new HashMap<>();
        json.put("imageName", imageName);
        json.put("url", url);
        return json;
    }
}
