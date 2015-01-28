package opensoft.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stejasvin on 1/28/2015.
 */
public class DataService extends IntentService {

    private static final String TAG = "DataService";
    //private String url_send_note = Settings.getServerUrl()+"make_annotation.php";
    DefaultHttpClient mDefaultHttpClient = null;

    public DataService() {
        super("DataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras != null) {

            mDefaultHttpClient = new DefaultHttpClient();
            if(mDefaultHttpClient == null){
                return;
            }

            String note = extras.getString("com.dhilcare.app.NOTE");
            String ecgId = extras.getString("com.dhilcare.app.ECG_ID");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Utilities.TAG_NOTES, note));
            params.add(new BasicNameValuePair(Utilities.TAG_ECG_ID, ecgId));

            try {
                URL url;
                url = new URL(url_send_note);

                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams
                        .setConnectionTimeout(httpParameters, 50000);
                HttpConnectionParams.setSoTimeout(httpParameters, 50000);

                HttpPost httppost = new HttpPost(url.toString());
                httppost.setEntity(new UrlEncodedFormEntity(params));
                httppost.setParams(httpParameters);
                HttpResponse response = mDefaultHttpClient
                        .execute(httppost);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();

//                JSONArray jsonArray = Utilities.JSONAParse(is);

                String resp;
                resp = Utilities.IsParse(is);
                Log.i(TAG, resp);
                Log.d(Utilities.TAG_UPLOAD,"Annotation: "+resp);


            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Intent broadcastIntent = new Intent(Utilities.UPLOAD_NOTE_ACTION);
            sendBroadcast(broadcastIntent);

        }
    }
}
