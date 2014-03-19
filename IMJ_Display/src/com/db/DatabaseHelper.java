package com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "rentalInformation.db";
	private final static int DB_VER = 1;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "";
		sql += "create table productOutline (";
		sql += "id integer";
		sql += ",title text";
		sql += ",category text";
		sql += ",version text";
		sql += ",movie_path text";
		sql += ")";
		
		String record = "";
		record += "create table productDetail(";
		record += "id integer";
		record += ",title text";
		record += ",introduction text";
		record += ",direction text";
		record += ",cast text";
		record += ",original_author text";
		record += ",FOREIGN KEY(id) REFERENCES productOutline(id)";
		record += ")";
		
		String profile = "";
		profile += "create table information (";
		profile += "id integer";
		profile += ",releace text";
		profile += ",time integer";
		profile += ",rating integer";
		profile += ",dayOfStartRental integer";
		profile += ",FOREIGN KEY(id) REFERENCES productOutline(id)";
		profile += ");";
		
		String measure = "";
		measure += "create table measure (";
		measure += "id integer";
		measure += ",score integer";
		measure += ",reviewId integer";
		measure += ",FOREIGN KEY(id) REFERENCES productOutline(id)";
		measure += ")";
		
		String review = "";
		review += "create table review (";
		review += "id integer";
		review += ",comments text";
		review += ",FOREIGN KEY(id) REFERENCES measure(id)";
		review += ")";

		db.execSQL(sql);
		db.execSQL(profile);
		db.execSQL(record);
		db.execSQL(measure);
		db.execSQL(review);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}