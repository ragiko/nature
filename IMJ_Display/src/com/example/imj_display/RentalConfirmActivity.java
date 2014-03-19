package com.example.imj_display;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class RentalConfirmActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rental_confirm);
		Intent intent = getIntent();
		
		String value = intent.getStringExtra("rentalValue");
		TextView text = (TextView) findViewById(R.id.value);
		text.setText(value + "円になります");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rental_confirm, menu);
		return true;
	}

}
