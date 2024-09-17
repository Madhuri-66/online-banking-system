package com.onlinebanking;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class UserDashboard extends AppCompatActivity {

    CardView profile,fundtransfer,viewbalnce,view_transactn;
    Intent i1,i2,i3,i4;
    String unm,acnttype;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.user_home );
        setTitle( "User Dashboard" );

        profile = (CardView) findViewById( R.id.profile );
        viewbalnce = (CardView) findViewById( R.id.balance );
        fundtransfer = (CardView) findViewById( R.id.fund_transfer );
        view_transactn = (CardView) findViewById( R.id.transactn );

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        unm = b.getString("unm");
        acnttype = b.getString("acnt");

        i1= new Intent( UserDashboard.this,MyProfile.class);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1.putExtra( "unm",unm );
                i1.putExtra( "acnt",acnttype);
                startActivity(i1);
            }
        });

    i2= new Intent( UserDashboard.this,View_Balance.class);
        viewbalnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra( "unm",unm );
                i2.putExtra( "acnt",acnttype);
                startActivity(i2);
            }
        });

            i3= new Intent( UserDashboard.this,FundTransfer.class);
        fundtransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i3.putExtra( "unm",unm );
                i3.putExtra( "acnt",acnttype);
                startActivity(i3);
            }
        });

        i4= new Intent( UserDashboard.this,ViewTransactions.class);
        view_transactn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i4.putExtra( "unm",unm );
                i4.putExtra( "acnt",acnttype);
                startActivity(i4);
            }
        });


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
