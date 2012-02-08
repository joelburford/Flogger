/** FlogEdit
 * @author jlburfor - Joel Burford - CMPUT 301 - 1294396
 * 
 * FlogEdit is an activity that lets you edit an alread existing 
 * log in the database.
 * 
 * FlogEdit should be passed an id of a Log, which is used to look
 * that log up in the database.  The logs information is displayed
 * on the screen where it can be edited.  If the user clicks save
 * the database entry is updated with the new info, otherwise the
 * changes will be ignored.  Delete removes the log with that id
 * from the database.
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
 * A stackoverflow page was consulted to help with the use of dialog windows:
 * http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
 *
 */

package flogger.jlburfor.C301;

import java.math.BigDecimal;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FlogEdit extends Activity {
	private int id;

	// onCreate()
	// This is called when the activity is first created.  It populates
	// the onscreen text boxes with info from the database.
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.edit);	    
	    id = Integer.parseInt( getIntent().getStringExtra("id"));
	    populate();
	}
	
	// 	populate()
	//  This method, using the global id variable, pulls info about a
	//  log from the database and displays it on the fields of the screen

	private void populate(){
		DB db = new DB(getApplicationContext());
		Log values = db.getLog(id);
		db.close();
		
		EditText editText1 = (EditText)findViewById(R.id.editText1);  //editText1 is Station
		editText1.setText(values.getStation(), TextView.BufferType.EDITABLE);
		
		EditText editText2 = (EditText)findViewById(R.id.editText2);  //editText2 is Fuel Grade
		editText2.setText(values.getGrade(), TextView.BufferType.EDITABLE);
		
		EditText editText3 = (EditText)findViewById(R.id.editText3);  //editText3 is Fuel Amount
		BigDecimal fuelAmount = BigDecimal.valueOf(values.getAmount()).setScale(3, BigDecimal.ROUND_HALF_UP);
		editText3.setText(fuelAmount.toPlainString(), TextView.BufferType.EDITABLE);
		
		EditText editText4 = (EditText)findViewById(R.id.editText4);  //editText4 is Unit Cost
		BigDecimal unitCost = BigDecimal.valueOf(values.getUnitCost()).setScale(1, BigDecimal.ROUND_HALF_UP);
		editText4.setText(unitCost.toPlainString(), TextView.BufferType.EDITABLE);
		
		EditText editText5 = (EditText)findViewById(R.id.editText5);  //editText5 is Trip Distance
		BigDecimal distance = BigDecimal.valueOf(values.getDistance()).setScale(1, BigDecimal.ROUND_HALF_UP);
		editText5.setText(distance.toPlainString(), TextView.BufferType.EDITABLE);
		
		EditText editText6 = (EditText)findViewById(R.id.editText6);  //editText6 is year
		editText6.setText(Integer.toString(values.getYear()), TextView.BufferType.EDITABLE);
		
		EditText editText7 = (EditText)findViewById(R.id.editText7);  //editText7 is month
		editText7.setText(Integer.toString(values.getMonth()), TextView.BufferType.EDITABLE);
		
		EditText editText8 = (EditText)findViewById(R.id.editText8);  //editText8 is day
		editText8.setText(Integer.toString(values.getDay()), TextView.BufferType.EDITABLE);
		
		TextView editText9 = (TextView)findViewById(R.id.textView9);  //total cost, non-editable
		BigDecimal cost = unitCost.divide(new BigDecimal(100)).multiply(fuelAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		editText9.setText(cost.toPlainString());
		return;
	}
	
	//  saveLog()
	//  This method will take the current text values on the screen and update
	//  the database with the new values using the global id variable.
	
	public void saveLog(View view){
		String newStation;
		String newGrade;
		Double newAmount;
		Double newUnitCost;
		Double newDistance;
		Double newCost;
		int newYear;
		int newMonth;
		int newDay;
		
		EditText editText1 = (EditText)findViewById(R.id.editText1);  //editText1 is Station
		newStation = editText1.getText().toString();
		
		EditText editText2 = (EditText)findViewById(R.id.editText2);  //editText2 is Fuel Grade
		newGrade = editText2.getText().toString();
		
		EditText editText3 = (EditText)findViewById(R.id.editText3);  //editText3 is Fuel Amount

		if(editText3.getText().toString().length() != 0)
			newAmount = Double.parseDouble(editText3.getText().toString());
		else
			newAmount = 0.0;
		
		EditText editText4 = (EditText)findViewById(R.id.editText4);  //editText4 is Unit Cost
		if(editText4.getText().toString().length() != 0)
			newUnitCost = Double.parseDouble(editText4.getText().toString());
		else
			newUnitCost = 0.0;
		
		EditText editText5 = (EditText)findViewById(R.id.editText5);  //editText5 is Trip Distance
		if(editText5.getText().toString().length() != 0)
			newDistance = Double.parseDouble(editText5.getText().toString());
		else
			newDistance = 0.0;
		
		newCost = (newUnitCost/100) * newAmount;
		
		EditText editText6 = (EditText)findViewById(R.id.editText6);  //editText6 is year
		if(editText6.getText().toString().length() != 0){
			newYear = Integer.parseInt(editText6.getText().toString());
			if(newYear < 0 || newYear > 9999)
				newYear = 2012;
		}else
			newYear = 2012;
		
		EditText editText7 = (EditText)findViewById(R.id.editText7);  //editText7 is month
		if(editText7.getText().toString().length() != 0){
			newMonth = Integer.parseInt(editText7.getText().toString());
			if(newMonth < 0 || newMonth > 12)
				newMonth = 1;
		}else
			newMonth = 1;
		
		EditText editText8 = (EditText)findViewById(R.id.editText8);  //editText8 is day
		if(editText8.getText().toString().length() != 0){
			newDay = Integer.parseInt(editText8.getText().toString());
			if (newDay < 0 || newDay > 31)
				newDay = 1;
		}else
			newDay = 1;
		
		TextView editText9 = (TextView)findViewById(R.id.textView9);  //total cost, non-editable
				
		DB db = new DB(getApplicationContext());
		int test = db.updateLog(id, newYear, newMonth, newDay, newStation, newGrade,
				newAmount, newUnitCost, newCost, newDistance);
		//Testing
		//if (test != 1)
			//System.out.println("ERROR INSERTING ROW, CHANGED: " + test + " ROWS");
		db.close();
		
		finish();

	}
	
	// Delete log will take the global id and delete the row in the database
	// with that id.
	// Code for the popup dialogue was taken from StackOverflow
	// http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android	
	
	public void deleteLog(View view){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete this entry?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id_button) {
		        	   
		        	   DB db = new DB(getApplicationContext());
		        	   db.removeLog(id);
		        	   db.close();
		        	   finish();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}

}
