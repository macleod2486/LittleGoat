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

package macleod2486.com.littlegoat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import macleod2486.com.voat.VoatApi;

public class MainActivity extends ActionBarActivity
{

    private DrawerLayout drawer;
    private static ListView menu;
    private VoatApi api;
    private String requestResult = "";
    private Menu sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainlayout);

        api = new VoatApi(getApplicationContext());
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        this.sideMenu = menu;

        //Hide the login button if the token is still valid.
        try
        {
            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);

            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

            Date strDate = sdf.parse(shared.getString("apitokenexpiration", null));

            if (!new Date().after(strDate))
            {
                this.sideMenu.getItem(0).setVisible(false);

                setupDrawer();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.login:
            {
                LayoutInflater inflater = this.getLayoutInflater();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final View view = inflater.inflate(R.layout.fragment_login, null);
                builder.setView(view);
                builder.setMessage("Login");
                builder.setPositiveButton("Login", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Log.i("LittlGoat", "Attempting login");

                        EditText username = (EditText)view.findViewById(R.id.username);
                        EditText password = (EditText)view.findViewById(R.id.password);

                        requestResult = api.requestAuthtoken(username.getText().toString(),password.getText().toString());

                        try
                        {
                            JSONObject result = new JSONObject(requestResult);
                            String token = result.get("access_token").toString();
                            String expiration = result.get("expires_in").toString();

                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(System.currentTimeMillis());
                            cal.add(Calendar.SECOND, Integer.parseInt(expiration));

                            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                            SharedPreferences.Editor edit = shared.edit();
                            edit.putString("apitoken", token);
                            edit.putString("apitokenexpiration", cal.getTime().toString());
                            edit.commit();

                            Log.i("VoatAPI",cal.getTime().toString());
                        }
                        catch(Exception e)
                        {
                            Log.e("LittlGoat", "Error in logging in.");
                            e.printStackTrace();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                        Log.i("LittleGoat", "Cancelled by user");
                    }
                });

                AlertDialog login = builder.create();

                login.show();

                return true;
            }

            case R.id.settings:
            {
                //Settings

                return true;
            }

            case android.R.id.home:
            {
                if(this.drawer.isDrawerOpen(this.menu))
                {
                    this.drawer.closeDrawer(this.menu);
                }
                else
                {
                    this.drawer.openDrawer(this.menu);
                }
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupDrawer()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener()
                                 {
                                     @Override
                                     public void onDrawerClosed(View drawerView)
                                     {
                                         Log.i("LittleGoat", "Drawer Closed");
                                     }

                                     @Override
                                     public void onDrawerOpened(View drawerView)
                                     {
                                         Log.i("LittleGoat", "DrawerOpened");
                                     }
                                 }
        );
        drawer.setDrawerLockMode(drawer.LOCK_MODE_UNLOCKED);

        ArrayList<String> menuList = new ArrayList<String>();

        menuList.add("Inbox");
        menuList.add("Settings");

        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuList);

        menu = (ListView)findViewById(R.id.menu);
        menu.setAdapter(adaptor);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    //First option selected
                }
                if(position == 1)
                {
                    //Second option selected
                }
            }
        });

    }

}