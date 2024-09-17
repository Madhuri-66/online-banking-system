package com.onlinebanking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class FundTransfer extends AppCompatActivity {



    SQLiteDatabase db;
    TextInputEditText name, uid, pwd, mno, amount, panno;
    String name1, uid1, pwd1, mno1, amount1, recipient;
    AutoCompleteTextView acnt_type,userslist;
    String unm,acnttype,acnttype1="",ava_balance,rava_balance,selected_acnt;
    //ArrayList<String> ulists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.fund_transfer );
        setTitle("FundTransfer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = openOrCreateDatabase( "onlinebanking", android.content.Context.MODE_PRIVATE, null );
        db.execSQL( "create table if not exists saving_account(name varchar,userid varchar,passwrd varchar,panno varchar,contact varchar,amount varchar)" );
        db.execSQL( "create table if not exists current_account(name varchar,userid varchar,passwrd varchar,office_name varchar,contact varchar,amount varchar)" );


        acnt_type = findViewById(R.id.acnt_type);
        userslist = findViewById(R.id.userslist);
        amount = findViewById( R.id.amount );

        Bundle b = getIntent().getExtras();
        unm = b.getString("unm");
        acnttype = b.getString("acnt");


        // create list of customer
        ArrayList<String> customerList = getAccountstype();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FundTransfer.this, android.R.layout.simple_spinner_item, customerList);

        //Set adapter
        acnt_type.setAdapter(adapter);

        acnt_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {
                acnttype1=arg0.getAdapter().getItem(arg2).toString();
               getUsersList(acnttype1);
            }
        });

        //getting user balance
        String sql3 = "SELECT * FROM "+acnttype+" where userid='" + unm + "' ";
        Cursor cursor3 = db.rawQuery( sql3, null );
        try {
            while (cursor3.moveToNext()) {
                ava_balance = cursor3.getString( cursor3.getColumnIndexOrThrow( "amount" ) );
            }
        } catch (Exception e) {
        }
    }

    private ArrayList<String> getAccountstype()
    {
        ArrayList<String> acnt = new ArrayList<>();
        acnt.add("Saving Account");
        acnt.add("Current Account");

        return acnt;
    }

    //userlist
    private void getUsersList(String acnt_type)
    {
        ArrayList<String> usrlist = new ArrayList<>();
        if(acnt_type.equals( "Saving Account" )) {
            String sql2 = "SELECT * FROM saving_account where userid!='" + unm + "' ";
            Cursor cursor = db.rawQuery( sql2, null );
            try {
            selected_acnt="saving_account";
                while (cursor.moveToNext()) {
                    String userid = cursor.getString( cursor.getColumnIndexOrThrow( "userid" ) );
                    usrlist.add(userid);

                }
            } catch (Exception e) {
            }

        }else{

            String sql2 = "SELECT * FROM current_account where userid!='" + unm + "' ";
            Cursor cursor = db.rawQuery( sql2, null );
            try {
                selected_acnt="current_account";
                while (cursor.moveToNext()) {
                    String userid  = cursor.getString( cursor.getColumnIndexOrThrow( "userid" ) );
                    usrlist.add(userid);
                }
            } catch (Exception e) {
            }

        }
        //Create adapter
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(FundTransfer.this, android.R.layout.simple_spinner_item, usrlist);

        //Set adapter
        userslist.setAdapter(adapter2);


    }
    public void transfer_amount(View view) {

        db.execSQL( "create table if not exists transactions(date varchar,recipients varchar,amount varchar,sender_uid varchar)" );


        recipient = userslist.getText().toString();
        amount1 = amount.getText().toString();


        if (acnttype1.trim().length() == 0) {
            acnt_type.setError( "Select type of account" );
            acnt_type.requestFocus();
        } else if (null == recipient || recipient.trim().length() == 0) {
            userslist.setError( "Select Recipient Account " );
            userslist.requestFocus();
        } else if (null == amount1 || amount1.trim().length() == 0) {
            amount.setError( "Enter minimum amount" );
            amount.requestFocus();
        } else {
            int bal = Integer.parseInt( ava_balance );
            int amnt = Integer.parseInt( amount1 );


            if (amnt <= bal) {

                String sql4 = "SELECT * FROM "+selected_acnt+" where userid='" + recipient + "' ";
                Cursor cursor4 = db.rawQuery( sql4, null );
                try {

                    while (cursor4.moveToNext()) {
                        rava_balance = cursor4.getString( cursor4.getColumnIndexOrThrow( "amount" ) );

                    }
                } catch (Exception e) {
                }


                db.execSQL( "insert into transactions values (DATE(),'" + recipient + "','" + amount1 + "','" + unm + "')" );
                Toast.makeText( getApplicationContext(), "Transaction Completed Successfully.", Toast.LENGTH_SHORT ).show();

                int rbal = Integer.parseInt( rava_balance );
                int tot_rbal=rbal+amnt;

                db.execSQL( "update "+selected_acnt+" set amount='"+tot_rbal+"' where userid='" + recipient + "'" );

                int tot_bal=bal-amnt;

                db.execSQL( "update "+acnttype+" set amount='"+tot_bal+"' where userid='" + unm + "'" );

                Intent i = new Intent( FundTransfer.this, UserDashboard.class );
                i.putExtra( "unm", unm );
                i.putExtra( "acnt", acnttype );
                startActivity( i );
            }else{
                Toast.makeText( getApplicationContext(), "Insufficient Balance.", Toast.LENGTH_SHORT ).show();


            }
        }




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                Intent i=new Intent(FundTransfer.this,MainActivity.class);
                startActivity(i);
                finish();
                break;

            case android.R.id.home:
                onBackPressed();
            default:
                break;

        }
        return true;
    }
  }

