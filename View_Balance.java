package com.onlinebanking;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
public class View_Balance extends AppCompatActivity {
	String name1,mno1;
	String unm,acnttype,amnt1,acnttype1;
	SQLiteDatabase db;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_balance);
		db=openOrCreateDatabase("onlinebanking", android.content.Context.MODE_PRIVATE, null);
		Bundle b = getIntent().getExtras();
		unm = b.getString("unm");
		acnttype = b.getString("acnt");
		setTitle("View Balance");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView bal= (TextView) findViewById(R.id.balance);
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
					amnt1 = cursor.getString( cursor.getColumnIndexOrThrow( "amount" ) );
				}
			} catch (Exception e) {
			}
		}else{
			String sql2 = "SELECT * FROM " + acnttype + " where userid='" + unm + "' ";
			Cursor cursor = db.rawQuery( sql2, null );
			try {
				while (cursor.moveToNext()) {
					amnt1 = cursor.getString( cursor.getColumnIndexOrThrow( "amount" ) );
				}
			} catch (Exception e) {
			}
		}
				tacnt.setText(acnttype1);
					bal.setText("Rs."+amnt1);
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
