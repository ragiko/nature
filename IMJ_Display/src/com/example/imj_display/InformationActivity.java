package com.example.imj_display;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("NewApi")
public class InformationActivity extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		MainActivity.currentClassName = "InformationActivity";
		Log.d("test", MainActivity.currentClassName);
		
		return inflater.inflate(R.layout.information_activity, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		Button information = (Button) getActivity().findViewById(R.id.information);

		information.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity ma = new MainActivity();
				Intent intent = new Intent(getActivity(), DvdInformationActivity.class);
				intent.putExtra("title", MainActivity.receivedMessage);
				Log.d("title", MainActivity.receivedMessage);
				Log.d("class", MainActivity.currentClassName);
				if(MainActivity.currentClassName.contains("InformationActivity")) {
					startActivity(intent);					
				} 
			}
		});
	}

}