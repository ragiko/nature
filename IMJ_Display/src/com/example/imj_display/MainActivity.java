package com.example.imj_display;

import java.io.UnsupportedEncodingException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Window;
import android.widget.Toast;

import com.db.DBTools;
import com.db.DatabaseHelper;

public class MainActivity extends Activity {

	SQLiteDatabase db;
	private NfcWriter nfcWriter = null;
	public static String receivedMessage;
	public String mode;
	public static String currentClassName = "";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar action = getActionBar();
	
		action.setDisplayShowHomeEnabled(false);
		action.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

		this.nfcWriter = new NfcWriter(this);
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		db = dbHelper.getWritableDatabase();
		DBTools tool = new DBTools();
		ContentValues val = new ContentValues();
	
		//insert sampel productOutline
//		val.put("id", 1);
//		val.put("title", "Evangelion3.33");
//		val.put("category", "anime");
//		val.put("version", "新作");
		
		// insert sample productDetail data
//		val.put("id", 1);
//		val.put("title", "Evangelion3.33");
//		val.put("introduction", "破から10年がすぎた世界で、シンジ君とカヲル君がいちゃいちゃするお話");
//		val.put("direction", "庵野秀明");
//		val.put("cast", "宮村優子, 林原めぐみ");
//		val.put("original_author", "GAINAX");
		
		//insert sample information data
//		val.put("id",1);
//		val.put("release", "0311");
//		val.put("time", 105);
//		val.put("rating", "PG-00");
//		val.put("dayOfStartRental", "2014年02月02日");
		
		//insert sample measure data
//		val.put("id", 1);
//		val.put("score", 5);
//		val.put("review", 1);
	
//		db.insert("productOutline", null, val);
//		db.insert("measure", null, val);
//		db.insert("information", null, val);
//		db.insert("productDetail", null, val);
//		tool.searchData(db, "productDetail");


		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar
				.newTab()
				.setText("レンタルする")
				.setTabListener(
						new MainTabListener<rentalActivity>(this, "f1", rentalActivity.class)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("DVDの情報を見る")
				.setTabListener(new MainTabListener<InformationActivity>(this, "f2",
								InformationActivity.class)));
	}
	public void callNextActivity(String className, String param) {
		Intent intent;
		if(className == "rentalActivity") {
		 	intent = new Intent(MainActivity.this, OfferFormActivity.class);
		} else {
			intent = new Intent(MainActivity.this, DvdInformationActivity.class);
		}
		intent.putExtra("title", param);
		startActivity(intent);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			Parcelable[] raws = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefMessage[] msgs = new NdefMessage[raws.length];
			for (int i = 0; i < raws.length; i++) {
				msgs[i] = (NdefMessage) raws[i];
				for (NdefRecord record : msgs[i].getRecords()) {
					byte[] payload = record.getPayload();
					if (payload == null)
						break;
					try {
						Toast.makeText(this, String.valueOf(payload.length), Toast.LENGTH_SHORT).show();
						String message = new String(payload, "UTF-8");
						receivedMessage = message.substring(5, message.length() - 7);
						
						Toast.makeText(this, receivedMessage, Toast.LENGTH_SHORT).show();
						Toast.makeText(this, currentClassName, Toast.LENGTH_SHORT).show();
						
						callNextActivity(currentClassName, receivedMessage);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@SuppressLint("NewApi")
	public static class MainTabListener<T extends Fragment> implements TabListener {

		private Fragment fragment;
		private final Activity activity;
		private final String tag;
		private final Class<T> cls;

		public MainTabListener(Activity activity, String tag, Class<T> cls) {
			this.activity = activity;
			this.tag = tag;
			this.cls = cls;
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			MainActivity ma = new MainActivity();
			if (fragment == null) {
				fragment = Fragment.instantiate(activity, cls.getName());
				ft.add(android.R.id.content, fragment, tag);
			} else {
				ft.attach(fragment);
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (fragment != null) {
				ft.detach(fragment);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		PendingIntent pendingIntent = createPendingIntent();
		this.nfcWriter.enable(this, pendingIntent);
	}

	private PendingIntent createPendingIntent() {
		Intent i = new Intent(this, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		return PendingIntent.getActivity(this, 0, i, 0);
	}

}
