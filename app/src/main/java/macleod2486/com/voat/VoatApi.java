/*
*
*    LittleGoat
*    A simple application for general use
*
*    Copyright (C) 2015  Manuel Gonzales Jr.
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see [http://www.gnu.org/licenses/].
*
*/

package macleod2486.com.voat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import macleod2486.com.littlegoat.R;

public class VoatApi
{
    private String apiUrl = null;
    private String result = null;
    private String username = null;
    private String password = null;

    private Context applicationContext;

    public VoatApi(Context context)
    {
        this.applicationContext = context;
    }

    public String requestAuthtoken(String username, String password)
    {
        try
        {
            this.apiUrl = applicationContext.getString(R.string.urlbase)+"/api/token";

            this.username = username;
            this.password = password;

            new postRequest().execute().get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public String getInfo(String username)
    {
        try
        {
            apiUrl = applicationContext.getString(R.string.urlbase)+"/api/v1/u/"+username+"/info";

            new getRequest().execute().get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    //Makes a post request.
    class postRequest extends AsyncTask<Void, Void, Void>
    {
        protected Void doInBackground(Void... arg)
        {
            HttpClient client = new DefaultHttpClient();
            ResponseHandler<String> handler = new BasicResponseHandler();

            HttpPost post = new HttpPost(apiUrl);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.addHeader("Voat-ApiKey", applicationContext.getString(R.string.apikey));

            try
            {
                ArrayList<NameValuePair> namevaluepair = new ArrayList<NameValuePair>(3);
                namevaluepair.add(new BasicNameValuePair("grant_type","password"));
                namevaluepair.add(new BasicNameValuePair("username", username));
                namevaluepair.add(new BasicNameValuePair("password", password));

                post.setEntity(new UrlEncodedFormEntity(namevaluepair));

                result = client.execute(post, handler);
            }

            catch(Exception e)
            {
                Log.e("LittleGoat", e.toString());
            }

            return null;
        }

        protected Void onPostExecute(Void... arg)
        {
            return null;
        }
    }

    //Makes a get request.
    class getRequest extends AsyncTask<Void, Void, Void>
    {
        HttpClient client = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();

        HttpGet get = new HttpGet(apiUrl);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        String apitoken = shared.getString("apitoken", null);

        protected Void doInBackground(Void... arg)
        {
            get.setHeader("Content-Type", "application/x-www-form-urlencoded");
            get.addHeader("Voat-ApiKey", applicationContext.getString(R.string.apikey));
            //get.addHeader("Authorization","Bearer z9iRWujOWWXR0gU_rm5bg_cntymt4GhGxDTj0CBerFF1MGURiThcVgiTCYAMXwLqtH7ppegBgAswA_l_ZHojsGXiKsWp8KMdnhIcYt6nMRC4Gw_k8xDC6iNA77X-C6qQL812SMud6jYiORhLozPP7_WeOteFCCpO5iRcwwpET5edwVBtWmT8fTd6NfSaG0hBR6bLSfgp-11l30llnQ2qnXimhDVqpN1wfBqqGJQArCW0nTQ_RzVD4QRyMXoM2InGoxRtPLp3wJlLaekvwHcXo3jCkqMD8PS8vM0eD97pfAYzECu1D9NaoNKOckWvGvPvUXLzBczDe6JFGy86NOxWJSYpAUI5n209jCFO5lhYBtJ_gQfKD4wLoruymjZIiFDLKT7P0BM6SiiOqD9FRN5fCfkS20DAszrETvLXP6NMIzVXCYSbFpHmB9LGJffKd7zf7mx3j5au0zyT9uue3A6r3c41YQkeSzDamxbu8XaNCTs");
            get.addHeader("Authorization","Bearer "+apitoken);

            try
            {
                String response = client.execute(get,handler);

                if(response != null)
                {
                    Log.i("Async",response);
                    result = response;
                    Log.i("Async", result);
                }
            }

            catch(Exception e)
            {
                Log.e("VoatAPI", e.toString());
            }

            return null;
        }

        protected Void onPostExecute(Void... arg)
        {
            return null;
        }
    }

}
