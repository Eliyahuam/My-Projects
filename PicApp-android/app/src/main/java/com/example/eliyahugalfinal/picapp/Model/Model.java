package com.example.eliyahugalfinal.picapp.Model;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.URLUtil;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliba on 28/03/2017.
 */

public class Model {
    private final static Model instance = new Model();
    public MyFireBaseStorage storeFireBase;
    LocalFiles storeToLocal;
    ModelFireBase fireBasePictureData;
    ModelSql modelSql;

    private Model() {
        storeFireBase = new MyFireBaseStorage();
        storeToLocal = new LocalFiles();
        fireBasePictureData = new ModelFireBase();
        modelSql = new ModelSql(AppContext.getAppContext());
    }
    public static Model getInstance() {
        return instance;
    }


    public interface GetImageListener{
        void onSccess(Bitmap image);
        void onFail();
    }
    public interface SaveImageListener{
        void complete(String url);
        void fail();
    }
    public interface GetAllPicturesByUidListeners{
        public void onResult(List<Picture> picList);
        public void onCancel();
    }



    public void addPicture(Bitmap bmap,final Picture pic) {
        try {
            storeToLocal.saveImageToFile(bmap,pic.descreption);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fireBasePictureData.addPicture(pic);
        PictureSql.add(modelSql.getWritableDB(),pic, FirebaseAuth.getInstance().getCurrentUser().getUid());
        /*
        storeFireBase.saveImage(bmap, pic.descreption, new SaveImageListener() {
            @Override
            public void complete(String url) {
                pic.url = url;
                fireBasePictureData.addPicture(pic);
                PictureSql.add(modelSql.getWritableDB(),pic, FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
            @Override
            public void fail() {

            }
        });
        */
    }
    private String getLocalImageFileName(String url) {
        String name = URLUtil.guessFileName(url, null, null);
        return name;
    }

    public void getImage(final String url, final GetImageListener listener) {
        //1. first try to find the image on the device
        String localFileName = getLocalImageFileName(url);
        Bitmap image = storeToLocal.loadImageFromFile(localFileName);
        if (image == null) {                                      //if image not found - try downloading it from parse


            storeFireBase.getImage(url, new GetImageListener() {
                @Override
                public void onSccess(Bitmap image) {
                    //2.  save the image localy
                    String localFileName = getLocalImageFileName(url);

                    try {
                        storeToLocal.saveImageToFile(image,localFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //3. return the image using the listener
                    listener.onSccess(image);
                }

                @Override
                public void onFail() {
                    listener.onFail();
                }
            });
        }else {

            listener.onSccess(image);
        }
    }



    public void getImageByName(String name,final GetAllPicturesByUidListeners listener) {
        fireBasePictureData.getAllPictureByName(name, new GetAllPicturesByUidListeners() {
            @Override
            public void onResult(List<Picture> picList) {
                listener.onResult(picList);
            }

            @Override
            public void onCancel() {

            }
        });
    }
    public void getAllPicsByUid(final GetAllPicturesByUidListeners listener) {
        final double lastUpdateDate = PictureSql.getLastUpdateDate(modelSql.getReadbleDB());
        fireBasePictureData.getAllPictureByUid(lastUpdateDate, new GetAllPicturesByUidListeners() {

            @Override
            public void onResult(List<Picture> picList) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (picList != null && picList.size() > 0) {
                    double recentUpdate = lastUpdateDate;

                    for (Picture pic:picList) {
                        PictureSql.add(modelSql.getWritableDB(),pic,uid);
                        if (pic.lastUpdate > recentUpdate) {
                            recentUpdate = pic.lastUpdate;
                        }
                        PictureSql.setLastUpdateDate(modelSql.getWritableDB(),recentUpdate);
                    }

                }
                List<Picture> res = PictureSql.getAllPicsByUid(modelSql.getReadbleDB(),uid);
                listener.onResult(res);

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
