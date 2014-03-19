package com.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBTools {
	
    Cursor mCursor;
    List<String> anything;

    public DBTools() {
    	anything = new ArrayList<String>();
    	mCursor = null;
    }
    
	public static String searchData( SQLiteDatabase db, String Runner ){
        Cursor cursor = null;
        try{
            cursor = db.query( Runner, null, null, null, null, null, null );
            return readCursor(cursor);
        }
        finally{
            if( cursor != null ){
                cursor.close();
            }
        }
    }

    public static String readCursor( Cursor cursor ){
        String result = "";

        int name = cursor.getColumnIndex("title");
        int Runner = cursor.getColumnIndex( "category" );
        int EventName  = cursor.getColumnIndex( "version"  );
        int lapList = cursor.getColumnIndex("movie_path");

        while( cursor.moveToNext()){
        	String na = cursor.getString(name);
            int event  = cursor.getInt( EventName);
            String dis = cursor.getString(Runner);
            String lap = cursor.getString(lapList);
            
            result = na + "  " + dis + "m " + event+ "sd" + String.valueOf(lap);
            Log.d("test",result);
        }
        Log.d("check", "ok");
        return result;
    }
    public int getColumn(SQLiteDatabase db, String table) {
    	int maxColumn = 0;
        Cursor cursor = null;
        try{
        	cursor = db.query( table , null, null, null, null, null, null );
            while( cursor.moveToNext() )
            	maxColumn++;
        }
        finally{
            if( cursor != null )
                cursor.close();
        }
    	return maxColumn;
    }
    public List<String> getAnything(SQLiteDatabase db, String table, String element) {
    	mCursor = db.query(table, new String[]{ element}, null, null, null, null, null);
    	int index = mCursor.getColumnIndex(element);
    	while(mCursor.moveToNext())
    		anything.add(mCursor.getString(index));
    	mCursor.close();
    	return anything;
    }
    
    public List<String> search(SQLiteDatabase db, String table, String element,String key) {
    	anything = new ArrayList<String>();
    	mCursor = db.query(table, new String[] {element}, "title = ?", new String[]{key}, null, null, null);
    	
    	int any = mCursor.getColumnIndex(element);
    	Log.d("colu", String.valueOf(any));
    	while(mCursor.moveToNext()) {
    		anything.add(mCursor.getString(any));
    	}
    	mCursor.close();
    	Log.d("anything", anything.toString());
    	
    	return anything;
    }
    
    public List<String> getTitle(SQLiteDatabase db) {
    	List<String> title = new ArrayList<String>();
        mCursor = db.query( "topic", new String[]{ "name"}, null, null, null, null, null );
        int name = mCursor.getColumnIndex("name");
    	
        while( mCursor.moveToNext())
        	title.add(mCursor.getString(name));

        mCursor.close();

    	return title;
    }
    public List<String> getTime(SQLiteDatabase db) {
    	List<String> time = new ArrayList<String>();
    	mCursor = db.query("topic", new String[]{ "makeTime" }, null, null, null, null, null);
    	
    	int timeIndex = mCursor.getColumnIndex("makeTime");
    	while(mCursor.moveToNext()) {
    		time.add(mCursor.getString(timeIndex));
    	}
    
        mCursor.close();

    	return time;
    }
}