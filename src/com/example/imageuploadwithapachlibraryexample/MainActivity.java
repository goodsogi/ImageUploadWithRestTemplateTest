package com.example.imageuploadwithapachlibraryexample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		String filePath = getFilesDir() + "/test.jpg";
		File fileCacheItem = new File(filePath);
        OutputStream out = null;
 
        try
        {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
 
            bitmap.compress(CompressFormat.JPEG, 100, out);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    	
		
        new ImageUploadTask().execute(filePath);
	}
		
	class ImageUploadTask extends AsyncTask<String, Void, Void> {


		
		@Override
		protected Void doInBackground(String... params) {
			String filePath = (String) params[0];
			String urlString = "http://goodsogi.mireene.com/UploadImageToServer.jsp";
			
			MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
			parts.add("file", new FileSystemResource(filePath));
			parts.add("testString", "test");
			RestTemplate rest = RestUtil.getRestTemplate();
			String response = rest.postForObject(urlString, parts, String.class);
			
			

			return null;
		}

		@Override
		protected void onPostExecute(final Void result) {

			Toast.makeText(MainActivity.this, R.string.uploaded_successfully,
					Toast.LENGTH_SHORT).show();
		}

	}
		
		
		
	}
