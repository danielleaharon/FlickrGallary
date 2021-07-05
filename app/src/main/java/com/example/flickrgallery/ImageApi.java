package com.example.flickrgallery;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flickrgallery.model.Image;
import com.example.flickrgallery.model.PagesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ImageApi {
    public final static ImageApi instance = new ImageApi();


    private ImageApi() {
    }


    public interface onGetImageListener {
        void onCompleted(boolean completed);
    }

    public void GetImage(final Context context, final int page, final onGetImageListener listener) throws JSONException {


        JSONObject jsonBody = new JSONObject();

        final String requestBody = jsonBody.toString();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&extras=url_s&api_key=aabca25d8cd75f676d3a74a72dcebf21&format=json&nojsoncallback=1&page=" + page;


        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null)
                    listener.onCompleted(false);

                Log.e("GetImage-Error", "error");
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";

            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
                //return super.getBody();
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }


            @Override
            protected void deliverResponse(JSONObject response) {
                List<Image> imageList = new ArrayList<>();

                try {
                    JSONObject photos = response.getJSONObject("photos");
                    int pagesNumber = photos.getInt("pages");
                    int totalImage = photos.getInt("total");

                    JSONArray data = photos.getJSONArray("photo");
                    for (int i = 0; i < data.length(); i++) {
                        Image Image = new Image();
                        JSONObject image = (JSONObject) data.get(i);
//Check that there is a path to the image and if so take the relevant data
                        if (image.has("url_s")) {
                            Image.setUrl(image.getString("url_s"));
                            Image.setTitle(image.getString("title"));
                            imageList.add(Image);
                        }

                    }
                    //  Adds the information received to the map
                    PagesModel.instance.pushToImageMap(page, imageList);
                    if (page == 1) {
                        PagesModel.instance.setPagesTotalNumber(pagesNumber);
                        PagesModel.instance.setTotalImage(totalImage);
                    }

                } catch (JSONException e) {
                    if (listener != null)
                        listener.onCompleted(false);

                    e.printStackTrace();
                }
//Everything was successfully received and sent to the listener message -true
                if (listener != null)
                    listener.onCompleted(true);
            }
        };


        queue.add(jsonRequest);
    }

}
