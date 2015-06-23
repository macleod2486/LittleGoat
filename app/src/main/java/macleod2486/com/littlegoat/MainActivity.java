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

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

import macleod2486.com.voat.VoatApi;

public class MainActivity extends ActionBarActivity
{

    private DrawerLayout drawer;
    private static ListView menu;
    private VoatApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainlayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        api = new VoatApi(getApplicationContext());

        setup();
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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.login:
            {
                //Login
                DialogFragment newFragment = new LoginFragment();
                newFragment.show(getSupportFragmentManager(), "login");

                return true;
            }
            case R.id.settings:
            {
                //Settings

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setup()
    {
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                                     @Override
                                     public void onDrawerClosed(View drawerView) {
                                         Log.i("LittleGoat", "Drawer Closed");
                                     }

                                     @Override
                                     public void onDrawerOpened(View drawerView) {
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