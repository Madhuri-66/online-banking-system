package com.onlinebanking;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;


import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
public class MyProfile extends AppCompatActivity {
	//private ArrayList<String> itemPricelist = new ArrayList<String>();
	String name1,mno1;
	String unm,acnttype,panno1,acnttype1;
	SQLiteDatabase db;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db=openOrCreateDatabase("onlinebanking", android.content.Context.MODE_PRIVATE, null);
		//choosing layout
		Bundle b = getIntent().getExtras();
		unm = b.getString("unm");
		acnttype = b.getString("acnt");
		if(acnttype.equals("saving_account"))
			setContentView(R.layout.profile);
		else
			setContentView(R.layout.profile2);
		setTitle("My Profile");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView name= (TextView) findViewById(R.id.name);
		TextView mno= (TextView) findViewById(R.id.mno);
		TextView panno = (TextView) findViewById(R.id.panno);
		TextView tacnt = (TextView) findViewById(R.id.tacnt);
		if(acnttype.equals( "saving_account" ))
			acnttype1="Saving Account";
		else
			acnttype1="Current Account";
		db.execSQL( "create table if not exists saving_account(name varchar,userid varchar,passwrd varchar,panno varchar,contact varchar,amount varchar)" );
		db.execSQL( "create table if not exists current_account(name varchar,userid varchar,passwrd varchar,office_name varchar,contact varchar,amount varchar)" );
		if(acnttype.equals( "saving_account" )) {
			String sql2 = "SELECT * FROM " + acnttype + " where userid='" + unm + "' ";
			Cursor cursor = db.rawQuery( sql2, null );
			try {
				while (cursor.moveToNext()) {
					name1 = cursor.getString( cursor.getColumnIndexOrThrow( "name" ) );
					mno1 = cursor.getString( cursor.getColumnIndexOrThrow( "contact" ) );
					panno1 = cursor.getString( cursor.getColumnIndexOrThrow( "panno" ) );
				}
			} catch (Exception e) {
			}
		}else{
			String sql2 = "SELECT * FROM " + acnttype + " where userid='" + unm + "' ";
			Cursor cursor = db.rawQuery( sql2, null );
			try {
				while (cursor.moveToNext()) {
					name1 = cursor.getString( cursor.getColumnIndexOrThrow( "name" ) );
					mno1 = cursor.getString( cursor.getColumnIndexOrThrow( "contact" ) );
					panno1 = cursor.getString( cursor.getColumnIndexOrThrow( "office_name" ) );
				}
			} catch (Exception e) {
			}
		}
					name.setText(name1);
					mno.setText(mno1);
					panno.setText(panno1);
					tacnt.setText(acnttype1);
			}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
			default:
				break;
		}
		return true;
	}
}
