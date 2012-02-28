/** FloggerActivity
 * @author jlburfor - Joel Burford - CMPUT 301
 *
 * FloggerActivity is the main activity of the Flogger application.
 * 
 * FloggerActivity will load the Log database (which will be empty
 * initially) and display the items on the screen.  From here the
 * user can click on an item to go the FlogEdit activity and edit
 * the log, press the add button to append an empty log to the
 * list, or press the statistics button to to go to the FlogStats
 * activity where stats about the data will be displayed.
 * 
 * This activity will update onResume and will therefore always
 * display data consistent with what's in the database.
 * 
 * Right now the data is appended to the bottom of the list but
 * it would be nice to sort the list by date, which could 
 * eventually be implemented.
 * 
 * Copyright (C) 2012 Joel Burford <jlburfor@ualberta.ca>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * A stackoverflow page was consulted to help with the use of a
 * simple adapter:
 * 
 * http://stackoverflow.com/questions/5319001/update-listview-from-simpleadapter-with-new-data
 *
 */

package flogger.jlburfor.C301;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;


public class FloggerActivity extends Activity {
	private ArrayList<Log> logs = new ArrayList<Log>();
	private SimpleAdapter adapter;
	private ArrayList<Map<String, String>> data;
	private ListView logList;
	private boolean first = true;
	
    @Override
    
    // onCreate()     
    // This method will be called automatically when the program
    // opens on an Android phone.  It populates an ArrayList
    // using the database of logs and displays that database to
    // the screen.
     
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        first = false;
        setContentView(R.layout.main);
        
        DB db = new DB(getApplicationContext());
        
        // Use the tempInsertData() call below to add two entries
        // to the database on startup. For testing.
        // db.tempInsertData();
        
        ArrayList<Map<String, String>> data = db.getList();
        db.close();     
      
        // SimpleAdapter code taken from StackOverflow
        
        adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"station", "date"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});

        logList = (ListView) findViewById(R.id.logList);
        logList.setAdapter(adapter);     
        
        // Below is the listener for a list button click.  It takes the id
        // of the clicked item and passes it to the FlogEdit activity so
        // you can edit the selected log.
        
        logList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) { 
            	HashMap<String, String> temp = (HashMap<String, String>) adapter.getItem(position);
            	String clickedID = temp.get("id");
            	Intent myIntent = new Intent();
            	myIntent.setClassName("flogger.jlburfor.C301", "flogger.jlburfor.C301.FlogEdit");
            	myIntent.putExtra("id", clickedID);
            	startActivity(myIntent); 
            }
        }
        );
    }
    
    // onResume()
    // This code, called whenever this activity is brought back
    // into focus, updates the onscreen list for consistent data.
    
    protected void onResume(){
    super.onResume();
    	if(first)
    		return;
    	DB db = new DB(getApplicationContext());
    	ArrayList<Map<String, String>> dataAdd = db.getList();
    	db.close();
    	
    	// This is the same adapter used in the onCreate() method
    	adapter = new SimpleAdapter(this, dataAdd,
                android.R.layout.simple_list_item_2,
                new String[] {"station", "date"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});
		logList.setAdapter(adapter);
		return;
		
    }
    
    // addLog()
    // This code is called by the Add button (linked in the XML)
    // It adds an empty log to the data and updates the screen.
    
    public void addLog(View view){
        DB db = new DB(getApplicationContext());
        Log insert = db.insertLog("New Log", "", 0, 0, 0);
        ArrayList<Map<String, String>> dataAdd = db.getList();
        db.close();

    	
		adapter = new SimpleAdapter(this, dataAdd,
                android.R.layout.simple_list_item_2,
                new String[] {"station", "date"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});
		logList.setAdapter(adapter);
		

    }
    
    // stats()
    // This method is called when the statistics button is pushed
    // (linked in the XML)  It starts the FlogStats activity
    
    public void stats(View view){
    	//Code on push of stats button
    	Intent myIntent = new Intent();
    	myIntent.setClassName("flogger.jlburfor.C301", "flogger.jlburfor.C301.FlogStats");
    	startActivity(myIntent); 
    }
    
}