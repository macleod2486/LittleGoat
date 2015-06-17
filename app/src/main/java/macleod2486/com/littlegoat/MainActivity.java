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

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity
{

    private DrawerLayout drawer;
    private ListView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainlayout);

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

        menuList.add("Signin");
        menuList.add("Settings");

        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuList);

        menu = (ListView)findViewById(R.id.menu);
        menu.setAdapter(adaptor);

    }


}
