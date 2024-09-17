package com.onlinebanking;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class AdminDashboard extends AppCompatActivity {

    CardView saving_acnt,current_acnt,delete_acnt;
    Intent i1,i2,i3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.admin_home );
        setTitle( "Admin Dashboard" );

        saving_acnt = (CardView) findViewById( R.id.saving_acnt );
        current_acnt = (CardView) findViewById( R.id.current_acnt );
        delete_acnt = (CardView) findViewById( R.id.delete_acnt );

        //create saving account
        i1= new Intent(AdminDashboard.this,SavingAccount.class);
        saving_acnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i1);
            }
        });

        //create current account
        i2= new Intent(AdminDashboard.this,CurrentAccount.class);
        current_acnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
            }
        });

        //delete account
        i3= new Intent(AdminDashboard.this,ViewAccounts.class);
        delete_acnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i3);
            }
        });

    }
    //menu logout
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
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                break;

            default:
                break;

        }


        return true;
    }
}
