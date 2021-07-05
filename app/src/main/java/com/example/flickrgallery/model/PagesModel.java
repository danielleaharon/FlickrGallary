package com.example.flickrgallery.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.flickrgallery.ImageApi;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

public class PagesModel {
    public final static PagesModel instance = new PagesModel();

    int PagesTotalNumber;
    int TotalImage;
    HashMap<Integer, List<Image>> ImageMap=new HashMap<>();




    PagesModel(){}

    public int getTotalImage() {
        return TotalImage;
    }

    public void setTotalImage(int totalImage) {
        TotalImage = totalImage;
    }

    public int getPagesTotalNumber() {
        return PagesTotalNumber;
    }

    public void setPagesTotalNumber(int pagesTotalNumber) {
        PagesTotalNumber = pagesTotalNumber;
    }

    public HashMap<Integer, List<Image>> getImageMap() {
        return ImageMap;
    }

    public void setImageMap(HashMap<Integer, List<Image>> imageMap) {
        ImageMap = imageMap;
    }
    public void pushToImageMap(int page,List<Image> imageList){

        ImageMap.put(page,imageList);
    }

    public void getImage(final Context context, final int page, final ImageApi.onGetImageListener listener ) {


        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {

                try {
                    ImageApi.instance.GetImage(context, page, new ImageApi.onGetImageListener() {
                        @Override
                        public void onCompleted(boolean onCompleted) {

                            if(listener!=null)
                            listener.onCompleted(onCompleted);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

            }

        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }


}
