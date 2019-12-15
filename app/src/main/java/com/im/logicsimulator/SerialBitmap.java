package com.im.logicsimulator;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SerialBitmap implements Serializable {
    public Bitmap image;

    public SerialBitmap(Resources res, int image, GridRect grid){
        Bitmap temp = BitmapFactory.decodeResource(res, image);
        this.image = Bitmap.createScaledBitmap(temp,grid.getGridWidth(), grid.getGridHeight(), true);
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,0,byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    private void readObject(java.io.ObjectInputStream in)throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while((b=in.read()) != -1){
            byteStream.write(b);
        }
        byte bitmapBytes[] = byteStream.toByteArray();
        image = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}
