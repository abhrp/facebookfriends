package com.abhiroop.facebookfriends;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by abhiroop on 10/2/13.
 */
public class HttpServices {
    public static void getBitmapFromURL(final String url, final ResponseHandler<Bitmap> responseHandler) {
        Thread imageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL urlConnection = null;
                try {
                    urlConnection = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    InputStream input = new BufferedInputStream(urlConnection.openConnection().getInputStream());
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    responseHandler.onResponse(null, myBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    responseHandler.onResponse(new ServerError("Unable to read bitmap data", 400), null);
                }
            }
        });
        imageThread.start();
    }
}
