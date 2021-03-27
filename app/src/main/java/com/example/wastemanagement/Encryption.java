package com.example.wastemanagement;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Encryption {
    String encrypt(String data,Context context){
        RequestQueue queue  = Volley.newRequestQueue(context);
        String url = "http://ec2-18-191-209-110.us-east-2.compute.amazonaws.com/encrypt.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jObject = null;
                try {
                    jObject = new JSONObject(response);
                    String s = jObject.getString("data");
                    Log.d("Volley", "onResponse: "+s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Volley", "onResponse: ()"+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("string",data);
                params.put("api-key", "1224c17a-8a14-4ada-90f8-b4f096c485f9");
                return params;
            }
            @Override
            public Map<String,String> getHeaders(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("Content-type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
        return data;
    }

}
