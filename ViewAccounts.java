package com.onlinebanking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ViewAccounts extends AppCompatActivity implements AdapterView.OnItemClickListener {


	 String username;
	 String acnttype;
	private List<AccountsView> cList = new ArrayList<AccountsView>();
	private ListView listView;
	private ProgressDialog  pDialog1, pDialog2;
	private AccountListAdapter adapter;
	SQLiteDatabase db;
	String unm;

	TextView tv;

	int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountslist);
		db=openOrCreateDatabase("onlinebanking", MODE_PRIVATE, null);
		setTitle("Delete Accounts");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		tv=(TextView)findViewById(R.id.textView);
		tv.setVisibility(View.GONE);

		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);



		db.execSQL( "create table if not exists saving_account(name varchar,userid varchar,passwrd varchar,panno varchar,contact varchar,amount varchar)" );
		db.execSQL( "create table if not exists current_account(name varchar,userid varchar,passwrd varchar,office_name varchar,contact varchar,amount varchar)" );

		new accountlist().execute(  );


	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		AccountsView p = (AccountsView) cList.get(position);

		 username = p.getUsername();
		final String actype = p.getAccnttype();

		acnttype = actype.split(":")[1];

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewAccounts.this);
		// Setting Dialog Title

		// Setting Dialog Message
		alertDialog.setMessage("Are you sure, Want to Delete this Account?");
		// Setting Icon to Dialog
		// Setting "Yes" Button
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new delete().execute();

			}
		});
		// Setting  "NO" Button
		alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alertDialog.show();

		}

	class accountlist extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog(ViewAccounts.this);
			pDialog1.setMessage("Loading ...");
			pDialog1.setIndeterminate(false);
			pDialog1.setCancelable(false);
			pDialog1.show();
		}


		protected String doInBackground(String... args) {


			String selectQuery = "SELECT * FROM saving_account ";
			Cursor cursor = db.rawQuery(selectQuery, null);
			cList.clear();
			while (cursor.moveToNext()) {
				count=1;
				AccountsView item = new AccountsView();
				item.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
				item.setPancard("Pannumber:" + cursor.getString(cursor.getColumnIndexOrThrow("panno")));
				item.setPhoneno("Contact: " +cursor.getString(cursor.getColumnIndexOrThrow("contact")) );
				item.setAmount("Available Balance: Rs."+cursor.getString(cursor.getColumnIndexOrThrow("amount")));
				item.setAccnttype("Type of Account:Saving Account" );
				item.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("userid")));
				cList.add(item);
			}

			String selectQuery2 = "SELECT * FROM current_account ";
			Cursor cursor2= db.rawQuery(selectQuery2, null);

			while (cursor2.moveToNext()) {
				count=1;
				AccountsView item = new AccountsView();
				item.setName(cursor2.getString(cursor2.getColumnIndexOrThrow("name")));
				item.setPancard("Office name:" + cursor2.getString(cursor2.getColumnIndexOrThrow("office_name")));
				item.setPhoneno("Contact: " +cursor2.getString(cursor2.getColumnIndexOrThrow("contact")) );
				item.setAmount("Available Balance: Rs."+cursor2.getString(cursor2.getColumnIndexOrThrow("amount")));
				item.setAccnttype("Type of Account:Current Account" );
				item.setUsername(cursor2.getString(cursor2.getColumnIndexOrThrow("userid")));
				cList.add(item);
			}



			return null;
		}


		protected void onPostExecute(String file_url) {

			if(count>0){
				adapter = new AccountListAdapter(ViewAccounts.this, cList);
				listView.setAdapter(adapter);

			}else{
				tv.setVisibility(View.VISIBLE);
				tv.setText("No Records Found");
			}


			pDialog1.dismiss();

		}


	}





	class delete extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog2 = new ProgressDialog(ViewAccounts.this);
			pDialog2.setMessage("Processing ....");
			pDialog2.setIndeterminate(false);
			pDialog2.setCancelable(false);
			pDialog2.show();
		}


		protected String doInBackground(String... args) {

			if(acnttype.equals( "Saving Account" ))
			db.execSQL("delete from saving_account where userid='" + username + "' ");
			else
				db.execSQL("delete from current_account where userid='" + username + "' ");

			return null;
		}

		protected void onPostExecute(String file_url) {


				Toast.makeText(getApplicationContext(), "Account deleted successfully..!", Toast.LENGTH_SHORT).show();
				new accountlist().execute();
			pDialog2.dismiss();

		}


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
