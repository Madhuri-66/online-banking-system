package com.onlinebanking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;


public class CurrentAccount extends AppCompatActivity {



    SQLiteDatabase db;
    TextInputEditText name, uid, pwd, mno, amount, ofcename;
    String name1, uid1, pwd1, mno1, amount1, ofcename1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.current_account );
        setTitle("Current Account Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = openOrCreateDatabase( "onlinebanking", android.content.Context.MODE_PRIVATE, null );
        name = findViewById( R.id.name );
        uid = findViewById( R.id.uid );
        pwd = findViewById( R.id.pwd );
        ofcename = findViewById( R.id.ofcename );
        mno = findViewById( R.id.mno );
        amount = findViewById( R.id.amount );
    }

    public void add_current_account(View view) {

        db.execSQL( "create table if not exists current_account(name varchar,userid varchar,passwrd varchar,office_name varchar,contact varchar,amount varchar)" );
        name1 = name.getText().toString();
        uid1 = uid.getText().toString();
        pwd1 = pwd.getText().toString();
        mno1 = mno.getText().toString();
        ofcename1 = ofcename.getText().toString();
        amount1 = amount.getText().toString();


        //checking for all field inputs
        if (null == name1 || name1.trim().length() == 0) {
            name.setError( "Enter  Account holder name" );
            name.requestFocus();
        } else if (null == uid1 || uid1.trim().length() == 0) {
            uid.setError( "Enter User name " );
            uid.requestFocus();
        } else if (null == pwd1 || pwd1.trim().length() == 0) {
            pwd.setError( "Enter  Password" );
            pwd.requestFocus();
        } else if (null == ofcename1 || ofcename1.trim().length() == 0) {
            ofcename.setError( "Enter  Office name" );
            ofcename.requestFocus();
        } else if (null == mno1 || mno1.trim().length() == 0) {
            mno.setError( "Enter  Mobile number" );
            mno.requestFocus();
        } else if (mno1.length() != 10) {
            mno.setError("Invalid Mobile Number.");
            mno.requestFocus();
        }else if (null == amount1 || amount1.trim().length() == 0) {
            amount.setError( "Enter minimum amount" );
            amount.requestFocus();
        } else {

            //check if userid already exists
            Cursor cursor = db.rawQuery( "select *from current_account where userid='" + uid1 + "'", null );
            int count = 0;
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    do {
                        count++;
                    } while (cursor.moveToNext());

                }

                if (count > 0) {
                    Toast.makeText( CurrentAccount.this, "Username already exists", Toast.LENGTH_LONG ).show();

                }else{
                    //creating current account

                    db.execSQL("insert into current_account values ('" + name1 + "','" + uid1 + "','" + pwd1 + "','" + ofcename1 + "','" + mno1 +"','" + amount1 +"')");
                    Toast.makeText(getApplicationContext(), "Current Account Created Successfully..!", Toast.LENGTH_SHORT).show();

                    Intent i=new Intent( CurrentAccount.this,AdminDashboard.class);
                    startActivity(i);
                }
            }


        }
    }

    //back button
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