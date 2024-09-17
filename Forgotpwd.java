package com.onlinebanking;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Forgotpwd extends AppCompatActivity {

	EditText etUsername, mno;
	String strName,mno1;
	SQLiteDatabase db;
	boolean status, status2;

	int count = 0, count2 = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpwd);
		db = openOrCreateDatabase( "onlinebanking", android.content.Context.MODE_PRIVATE, null );
		setTitle("Forgot Password");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		etUsername = (EditText) findViewById(R.id.unm);
		mno = (EditText) findViewById(R.id.mno);


	}
	/**
	 * Retriving the username and mobile number values.
	 * Performing the form validations.
	 * @param v
	 */
	public void getPWD(View v) {
		db.execSQL( "create table if not exists saving_account(name varchar,userid varchar,passwrd varchar,panno varchar,contact varchar,amount varchar)" );
		db.execSQL( "create table if not exists current_account(name varchar,userid varchar,passwrd varchar,office_name varchar,contact varchar,amount varchar)" );


		strName = etUsername.getText().toString();
			 mno1 = mno.getText().toString();

				if (null == strName || strName.trim().length() == 0) {
					etUsername.setError("Enter User Name");
					etUsername.requestFocus();
				}
					else if (null ==mno1 || mno1.trim().length() == 0) {
						mno.setError("Enter  Mobile No.");
						mno.requestFocus();                       }
					else if (mno1.length()!=10) {
						mno.setError("Invalid Mobile Number.");
						mno.requestFocus();
					} else {
					try{
						Cursor cursor = db.rawQuery( "select *from saving_account where  userid='" + strName + "' and contact='" + mno1 + "'", null );

						if (cursor != null) {

							if (cursor.moveToFirst()) {
								do {
									count++;
								} while (cursor.moveToNext());

							}

							if (count > 0) {

								status = true;

							} else {

								status = false;

							}


						}

						Cursor cursor2 = db.rawQuery( "select *from current_account where  userid='" + strName + "' and contact='" + mno1 + "'", null );

						if (cursor2 != null) {

							if (cursor2.moveToFirst()) {
								do {
									count2++;
								} while (cursor2.moveToNext());

							}

							if (count2 > 0) {

								status2 = true;

							} else {

								status2 = false;

							}

						}


						if(status){

							Intent i = new Intent(Forgotpwd.this, Changepwd.class );
							i.putExtra( "unm", strName );
							i.putExtra( "acnt","saving_account" );
							startActivity( i );
							finish();

						} else if (status2) {

							Intent i = new Intent( Forgotpwd.this, Changepwd.class );
							i.putExtra( "unm", strName );
							i.putExtra( "acnt","current_account" );
							startActivity( i );
							finish();

						}else{


							Toast.makeText( Forgotpwd.this, "Incorrect User name or Mobile no", Toast.LENGTH_LONG ).show();

						}






					}
				catch(Exception e){
					
					Log.e("Exception","Exception",e);
				}	
				}
				
			}

	/**
	 * Executing the specific operations based on chosen of particular switch case
	 * @param item
	 * @return
	 */
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











