/** DB
 * @author jlburfor - Joel Burford - CMPUT 301 - 1294396
 *
 * DB is a wrapper around the database functionality.  All other activities
 * go through DB to use the database.
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
 *SQLite code adapted from:
 *http://www.codeproject.com/Articles/119293/Using-SQLite-Database-with-Android
 */

package flogger.jlburfor.C301;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper{
	private static final String dbName="flogger";
	private static final String logTable="logs";
	private static final String colID = "id";
	private static final String colDay = "day";
	private static final String colMonth = "month";
	private static final String colYear = "year";
	private static final String colStation = "station";
	private static final String colGrade = "grade";
	private static final String colAmount = "amount";
	private static final String colUnitCost = "unitcost";
	private static final String colCost = "cost";
	private static final String colDist = "distance";
	
	// This is the DB constructer, which calls the SQLiteOpenHelper
	// constructor.
	public DB(Context context) {
		super(context, dbName, null,1); 
	}	
	
	
	// onCreate()
	// This method will run only on the first execution of a program
	// on a specific phone.  After that the database will remain on that
	// phone until the program is deleted.
	
	public void onCreate(SQLiteDatabase db) {
		  db.execSQL("CREATE TABLE " + logTable + " (" +
				  colID + " INTEGER, " +
				  colDay + " INTEGER, " +
				  colMonth + " INTEGER, " +
				  colYear + " INTEGER, " +
				  colStation + " VARCHAR(50), " +
				  colGrade + " VARCHAR(50), " +
				  colAmount + " FLOAT, " +
				  colUnitCost + " FLOAT, " +
				  colCost + " FLOAT, " +
				  colDist + " FLOAT, " +
				  "PRIMARY KEY (" + colID + "))");		
	}
	
	// tempInsertData()
	// This method inserts two values into the database for testing
	// purposes.
	
	private void tempInsertData(){
		  Log insert1 = this.insertLog("Esso", "Ugly", 7.0, 0.98, 200);
		  Log insert2 = this.insertLog("Shell", "Sour", 3.0, 0.90, 50);
	}
	
	// insertLog()
	// This takes values for a Log and inserts them into a new row of the database.
	// It will return a Log object with the same values, including the id given
	// to it by the database
	
	public Log insertLog(String station, String grade, double amount, double unitcost, double distance){
		Log lg = new Log();
		SQLiteDatabase db = this.getWritableDatabase();
		lg.setData(station, grade, amount, unitcost, distance);
		ContentValues cv = new ContentValues();
		cv.put(colDay, lg.getDay());
		cv.put(colMonth, lg.getMonth());
		cv.put(colYear, lg.getYear());
		cv.put(colStation, lg.getStation());
		cv.put(colGrade, lg.getGrade());
		cv.put(colAmount, lg.getAmount());
		cv.put(colUnitCost, lg.getUnitCost());
		cv.put(colCost, lg.getCost());
		cv.put(colDist, lg.getDistance());
		db.insert(logTable, colID, cv);
		db.close();
		
		return lg;
	}
	
	// This method will be called if the version of the database is changed
	// it's required to extend the SQLiteOpenHelper class but I do not use
	// it
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		  //Will be called when version of constructor is changed
		  db.execSQL("DROP TABLE IF EXISTS "+logTable + " ");
		  onCreate(db);
		 }
		 
	
	// getList()
	// This method will return an ArrayList of Map objects to be displayed
	// on the FloggerActivity activity.  It fills this ArrayList using the
	// current Log database.
	
	public ArrayList<Map<String, String>> getList(){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Cursor c=db.query(logTable, new String[]{colID+" as _id",colStation,colCost, colYear, colMonth, colDay},
				null, null, null, null, null);
		   if (c.getCount() == 0){
			   db.close();
			   return data;
		   }
		   c.moveToFirst();
		   do {
			   
			   Map<String, String> datum = new HashMap<String, String>();
			   datum.put(colID, "" + c.getInt(c.getColumnIndex("_id")));
			   datum.put(colStation, c.getString(c.getColumnIndex(colStation)));
			   String date = "" + c.getInt(c.getColumnIndex(colYear)) + "-" + c.getInt(c.getColumnIndex(colMonth)) +
			   "-" + c.getInt(c.getColumnIndex(colDay));   
			   datum.put("date", date);
			   data.add(datum);  
		   } while (c.moveToNext());
		   c.close();	   
		   db.close();
		return data;	
	}
	
	// getLog()
	// This method queries the database for a log with the given id and
	// returns that log with all the appropriate data attached.
	public Log getLog(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c=db.query(logTable, new String[]{colID+" as _id",colStation,colCost, colYear, colMonth, colDay,
				colGrade,colAmount, colUnitCost, colCost, colDist},
				"_id=?", new String[]{""+id}, null, null, null);
		c.moveToFirst();
		Log ret = new Log(c.getInt(c.getColumnIndex(colDay)), c.getInt(c.getColumnIndex(colMonth)), 
				c.getInt(c.getColumnIndex(colYear)));
		ret.setData(c.getString(c.getColumnIndex(colStation)), c.getString(c.getColumnIndex(colGrade)), 
				c.getDouble(c.getColumnIndex(colAmount)),c.getDouble(c.getColumnIndex(colUnitCost)),
				c.getDouble(c.getColumnIndex(colDist)));
		return ret;
	}
	
	// updateLog()
	// This method takes information about a log and updates an already existing
	// entry with the new info.  The returned int value is for testing, representing
	// the number of rows updated.
	
	public int updateLog(int id, int year, int month, int day, String station, String grade,
			double amount, double unitCost, double cost, double distance){
		
		SQLiteDatabase db = this.getWritableDatabase();
		   ContentValues cv = new ContentValues();
			cv.put(colDay, day);
			cv.put(colMonth, month);
			cv.put(colYear, year);
			cv.put(colStation, station);
			cv.put(colGrade, grade);
			cv.put(colAmount, amount);
			cv.put(colUnitCost, unitCost);
			cv.put(colCost, cost);
			cv.put(colDist, distance);
			
		   return db.update(logTable, cv, colID+"=?", 
		    new String []{String.valueOf(id)}); 
	}
	
	// removeLog()
	// This method takes an id and removes the corresponding log from the database.
	
	public void removeLog(int id){
		   SQLiteDatabase db = this.getWritableDatabase();
		   db.execSQL("DELETE FROM " + logTable + " WHERE " + colID + " = " + id);
		   db.close();
		  return;
	}
	
	// getStats()
	// This method queries the database and returns an array of doubles
	// representing statistics about the database.
	// stats[0] = Total Cost
	// stats[1] = Total Distance
	// stats[2] = Consumption Rate
	
	public double[] getStats(){
		double[] stats = new double[3];
		double totalFuel;
		
		//put total cost in stats[0]
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT SUM(" + colCost + ") FROM " + logTable, null);
		if (c.getCount() == 0)
			stats[0] = 0;
		else {
			c.moveToFirst();
			stats[0] = c.getDouble(0);
		}
		
		//put total distance in stats[1]
		c = db.rawQuery("SELECT SUM(" + colDist + ") FROM " + logTable, null);
		if (c.getCount() == 0)
			stats[1] = 0;
		else {
			c.moveToFirst();
			stats[1] = c.getDouble(0);
		}
		
		//put consumption rate in stats[2]
		c = db.rawQuery("SELECT SUM(" + colAmount + ") FROM " + logTable, null);
		if (c.getCount() == 0)
			stats[2] = 0;
		else if (stats[1] == 0)
			stats[2] = 0;
		else {
			c.moveToFirst();
			totalFuel = c.getDouble(0);
			stats[2] = totalFuel / stats[1] * 100;
		}
		c.moveToFirst();
		db.close();
		return stats;
	}
	
}
