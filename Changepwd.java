package com.onlinebanking;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Changepwd extends AppCompatActivity {
	

	String unm;
	EditText newpwd,cnewpwd;
	String newpwd1,cnewpwd1,acnttype;
	SQLiteDatabase db;
	boolean sts;

	private static final String PREFRENCES_NAME = "myprefrences";
	/**
	 * Invoking when the User_Changepwd activity executes.
	 * Invoking the changepwd layout.
	 * Creating the objects for EditText of new password and confirm password.
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepwd);
		setTitle("Change Password");
		db = openOrCreateDatabase( "onlinebanking", android.content.Context.MODE_PRIVATE, null );
		Intent i1=getIntent();
		Bundle b=i1.getExtras();
		 unm=b.getString("unm");
		acnttype=b.getString("acnt");

		newpwd = (EditText) findViewById(R.id.newpwd);
		cnewpwd = (EditText) findViewById(R.id.cnewpwd);

	}


	/**
	 * Retriving the new password and confirm password values.
	 * Performing the form validations.
	 * @param v
	 */
	public void changePWD(View v) {


		newpwd1 = newpwd.getText().toString();
		cnewpwd1 = cnewpwd.getText().toString();

				  if (null ==newpwd1 || newpwd1.trim().length() == 0) {
					newpwd.setError("Enter New Password");
					newpwd.requestFocus();
				}
					else if (null ==cnewpwd1 || cnewpwd1.trim().length() == 0) {
					cnewpwd.setError("Enter Confirm Password");
					cnewpwd.requestFocus();
					} else if (!newpwd1.equals(cnewpwd1)) {
					Toast.makeText( Changepwd.this,"Passwords are not matched",Toast.LENGTH_LONG).show();
				}else
				 {//Toast.makeText( Changepwd.this,"acnttype="+acnttype,Toast.LENGTH_LONG).show();
					try{

						db.execSQL("update "+acnttype+" set passwrd='"+newpwd1+"' where userid='" + unm + "'" );
						Intent i = new Intent( Changepwd.this, MainActivity.class );
						startActivity( i );
						finish();
						Toast.makeText( Changepwd.this, "Password Updated Successfully.", Toast.LENGTH_LONG ).show();

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


