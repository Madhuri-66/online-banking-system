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


public class SavingAccount extends AppCompatActivity {



    SQLiteDatabase db;
    TextInputEditText name, uid, pwd, mno, amount, panno;
    String name1, uid1, pwd1, mno1, amount1, panno1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.saving_account );
        setTitle("Saving Account Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = openOrCreateDatabase( "onlinebanking", android.content.Context.MODE_PRIVATE, null );
        name = findViewById( R.id.name );
        uid = findViewById( R.id.uid );
        pwd = findViewById( R.id.pwd );
        panno = findViewById( R.id.panno );
        mno = findViewById( R.id.mno );
        amount = findViewById( R.id.amount );
    }

    public void add_saving_account(View view)
    {

        db.execSQL( "create table if not exists saving_account(name varchar,userid varchar,passwrd varchar,panno varchar,contact varchar,amount varchar)" );
        name1 = name.getText().toString();
        uid1 = uid.getText().toString();
        pwd1 = pwd.getText().toString();
        mno1 = mno.getText().toString();
        panno1 = panno.getText().toString();
        amount1 = amount.getText().toString();
        //checking if all values are entered

        if (null == name1 || name1.trim().length() == 0) {
            name.setError( "Enter  Account holder name" );
            name.requestFocus();
        } else if (null == uid1 || uid1.trim().length() == 0) {
            uid.setError( "Enter User name " );
            uid.requestFocus();
        } else if (null == pwd1 || pwd1.trim().length() == 0) {
            pwd.setError( "Enter  Password" );
            pwd.requestFocus();
        } else if (null == panno1 || panno1.trim().length() == 0) {
            panno.setError( "Enter  Pancard number" );
            panno.requestFocus();
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

            //if userid already exists
            Cursor cursor = db.rawQuery( "select *from saving_account where userid='" + uid1 + "'", null );
            int count = 0;
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    do {
                        count++;
                    } while (cursor.moveToNext());

                }

                if (count > 0) {
                    Toast.makeText( SavingAccount.this, "Username already exists", Toast.LENGTH_LONG ).show();

                }else{

                    //creating account

                    db.execSQL("insert into saving_account values ('" + name1 + "','" + uid1 + "','" + pwd1 + "','" + panno1 + "','" + mno1 +"','" + amount1 +"')");
                    Toast.makeText(getApplicationContext(), "Saving Account Created Successfully..!", Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(SavingAccount.this,AdminDashboard.class);
                    startActivity(i);
                }
            }


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