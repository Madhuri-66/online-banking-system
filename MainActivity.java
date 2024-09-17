package com.onlinebanking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;



import com.onlinebanking.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText unm, pwd;
    String unm1, pwd1;
    boolean status, status2;
    int count = 0, count2 = 0;
    TextView ftpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        db = openOrCreateDatabase( "onlinebanking", android.content.Context.MODE_PRIVATE, null );
        unm = (EditText) findViewById( R.id.username );
        pwd = (EditText) findViewById( R.id.password );

        //opening forgot password class
        ftpwd = (TextView) findViewById(R.id.ftpwd);

        ftpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Forgotpwd.class);

                startActivity(i);

            }
        });

    }

    public void logincheck(View view) {

        db.execSQL( "create table if not exists saving_account(name varchar,userid varchar,passwrd varchar,panno varchar,contact varchar,amount varchar)" );
        db.execSQL( "create table if not exists current_account(name varchar,userid varchar,passwrd varchar,office_name varchar,contact varchar,amount varchar)" );

        unm1 = unm.getText().toString();
        pwd1 = pwd.getText().toString();

        if (null == unm1 || unm1.trim().length() == 0) {
            unm.setError( "Enter User name" );
            unm.requestFocus();
        } else if (null == pwd1 || pwd1.trim().length() == 0) {
            pwd.setError( "Enter Password" );
            pwd.requestFocus();
        } else {

            //admin login
            if (unm1.equals( "admin" ) && pwd1.equals( "admin" )) {
                Intent i = new Intent( MainActivity.this, AdminDashboard.class );
                startActivity( i );
            }
            else {
                //saving account searching
                Cursor cursor = db.rawQuery( "select *from saving_account where  userid='" + unm1 + "' and passwrd='" + pwd1 + "'", null );

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
                //current account searching
                Cursor cursor2 = db.rawQuery( "select *from current_account where  userid='" + unm1 + "' and passwrd='" + pwd1 + "'", null );

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

                    Intent i = new Intent( MainActivity.this, UserDashboard.class );
                    i.putExtra( "unm", unm1 );
                    i.putExtra( "acnt","saving_account" );
                    startActivity( i );
                    finish();

                } else if (status2) {

                    Intent i = new Intent( MainActivity.this, UserDashboard.class );
                    i.putExtra( "unm", unm1 );
                    i.putExtra( "acnt","current_account" );
                    startActivity( i );
                    finish();

                }else{
                   Toast.makeText( MainActivity.this, "Invalid Login Credentials", Toast.LENGTH_LONG ).show();

                }
            }
        }
    }
}