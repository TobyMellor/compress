package uk.co.tobymellor.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownloadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String...urls){
        String imageURL = urls[0];
        Bitmap image = null;

        try (InputStream inputStream = new URL(imageURL).openStream()) {
            image = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}
