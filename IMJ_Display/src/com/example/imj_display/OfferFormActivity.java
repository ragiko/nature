package com.example.imj_display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.db.DBTools;
import com.db.DatabaseHelper;

public class OfferFormActivity extends Activity {
	private String title;
	private String version;
	private Spinner periodList;
	private String rentalValue;
	private TextView text;
	private List<String> result;
	SQLiteDatabase db;
	private Map<List, String> twoKeysMap2 = new HashMap<List, String>();
	private String[] prefecture = { "新作", "準新作", "一般作" };
	private String[] period = { "当日", "1泊2日", "2泊3日", "3泊4日", "7泊8日" };
	private int[][] Value = { { 340, 390, 440, 490, 640 },
			{ 340, 390, 390, 390, 390 }, { 100, 100, 100, 100, 340 } };
	
	public OfferFormActivity() {
		for (int i = 0; i < prefecture.length; i++) {
			for (int j = 0; j < period.length; j++) {
				List keys = new ArrayList();
				keys.add(prefecture[i]);
				keys.add(period[j]);
				twoKeysMap2.put(keys, String.valueOf(Value[i][j]));
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offer_form);
		
		Intent intent = getIntent();
		title = intent.getStringExtra("title");

		DatabaseHelper dbHelper = new DatabaseHelper(this);
		RatingBar rb = (RatingBar) findViewById(R.id.ratingBar1);
		db = dbHelper.getWritableDatabase();
		DBTools tool = new DBTools();
		
        rb.setNumStars(5);
        rb.setIsIndicator(true);
        rb.setStepSize((float) 0.5);
        rb.setRating((float) 4.0);
		Log.d("title", title);
		tool.searchData(db, "productOutline");
		result = tool.search(db, "productOutline", "version", title);

		setTitle(title + "(" + result.get(0) + ")"); 

		Button rental = (Button) findViewById(R.id.rental);
		periodList = (Spinner) findViewById(R.id.spinner2);
		String[] word = getResources().getStringArray(R.array.version);
		String[] periods = getResources().getStringArray(R.array.period);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, periods);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		periodList.setAdapter(adapter2);
		rental.setOnClickListener(new rental());
	}

	class rental implements OnClickListener {
		@Override
		public void onClick(View v) {
			String pref =  result.get(0);
			String per = (String) periodList.getSelectedItem();
			rentalValue(pref, per);

			Intent intent = new Intent(OfferFormActivity.this, RentalConfirmActivity.class);
			intent.putExtra("rentalValue", rentalValue);
			startActivity(intent);
		}
	}
	
	private int rentalValue(String ver, String per) {
		List keys2 = new ArrayList();
		keys2.add(ver);
		keys2.add(per);
		System.out.println(twoKeysMap2.get(keys2));
		rentalValue =  twoKeysMap2.get(keys2);
		
		return 0;
	}
}
