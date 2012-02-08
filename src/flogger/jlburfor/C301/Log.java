/** Log
 * @author jlburfor - Joel Burford - CMPUT 301 - 1294396
 *
 * Log is a class representing a log entry in the database
 * 
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
 */

package flogger.jlburfor.C301;
import java.util.Calendar;

public class Log {
	private int id;
	private int year;
	private int month;
	private int day;
	private String fstation;
	private String fgrade;
	private double famount;
	private double funitcost;
	private double fcost;
	private double distance;
	
	// default constructor
	// Constructs a log and fills the time data in with the current date
	
	public Log(){
		Calendar fdate = Calendar.getInstance();
		day = fdate.get(Calendar.DATE);
		month = fdate.get(Calendar.MONTH) + 1;
		year = fdate.get(Calendar.YEAR);
	}
	
	// Other constructor
	// Constructs a log with the specified time and date
	
	public Log(int d, int m, int y){
		this.day = d;
		this.month = m;
		this.year = y;
	}
	
	// setData()
	// Used to set all of the Log values.
	
	public void setData(String station, String grade, double amount, double unitcost, double distance){
		this.fstation = station;
		this.fgrade = grade;
		this.famount = amount;
		this.funitcost = unitcost;
		this.fcost = unitcost * amount / 100;
		this.distance = distance;
		
	}
		
	// getID, getDate, getMonth, getYear, getStation, getGrade, 
	// getAmount, getUnitCost, getCost, getDistance
	// All getter methods intended to return the specified data
	
	public int getID(){
		return 0;
	}
	
	public String getDate() {
		return (year + "-" + month + "-" + day);
	}
	
	public int getDay(){
		return day;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	public String getStation() {
		return fstation;
	}
	
	public String getGrade() {
		return fgrade;
	}

	public double getAmount() {
		return famount;
	}

	public double getUnitCost() {
		return funitcost;
	}

	public double getCost(){
		return fcost;
	}

	public double getDistance() {
		return distance;
	}

}
