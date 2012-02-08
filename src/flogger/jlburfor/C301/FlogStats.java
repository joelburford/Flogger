/** FlogStats
 * @author jlburfor - Joel Burford - CMPUT 301 - 1294396
 *
 * FlogStats is an activity that shows statistics about the current
 * log database.
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
 */

package flogger.jlburfor.C301;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FlogStats extends Activity {

	// onCreate()
	// called when activity is created, queries the database
	// and prints the info to the screen.
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.stats);
	
	    DB db = new DB(getApplicationContext());
		double[] stats = db.getStats();
		db.close();
		
		
		BigDecimal totalCost = BigDecimal.valueOf(stats[0]).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalDistance = BigDecimal.valueOf(stats[1]).setScale(1, BigDecimal.ROUND_HALF_UP);
		BigDecimal consumptionRate = BigDecimal.valueOf(stats[2]).setScale(1, BigDecimal.ROUND_HALF_UP);
		
		TextView editText1 = (TextView)findViewById(R.id.textView7);  //total cost, non-editable
		editText1.setText(totalCost.toPlainString());
		
		TextView editText2 = (TextView)findViewById(R.id.textView4);  //total cost, non-editable
		editText2.setText(totalDistance.toPlainString());
		
		TextView editText3 = (TextView)findViewById(R.id.textView6);  //total cost, non-editable
		editText3.setText(consumptionRate.toPlainString());
			
	}

}
