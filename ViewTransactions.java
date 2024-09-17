package com.onlinebanking;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class ViewTransactions extends AppCompatActivity {
   /* private ArrayList dietList = new ArrayList();
    private ArrayList dietList2 = new ArrayList();
    private ListView listView;*/
    SQLiteDatabase db;
  /*  JSONArray json2 = null;*/
    TableLayout tl;
    //int sts;
    String unm;
    int textSize = 0, smallTextSize =0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_transactions);
        setTitle("View Transactions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db=openOrCreateDatabase("onlinebanking", android.content.Context.MODE_PRIVATE, null);
        Bundle b = getIntent().getExtras();
        unm = b.getString("unm");

        textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
        smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);


        tl = (TableLayout) findViewById(R.id.main_table);
        tl.setStretchAllColumns(true);


        TableRow tr_head = new TableRow(this);
        //tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));


        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv1.setGravity( Gravity.LEFT);
        tv1.setPadding(5, 15, 0, 15);
        tv1.setBackgroundColor(Color.parseColor("#f7f7f7"));
        tv1.setText("Date");
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);


        tr_head.addView(tv1);// add the column to the table row here

        TextView tv2 = new TextView(this);
       // define id that must be unique
        tv2.setText("Recipient"); // set the text for the header
         // set the color
        tv2.setBackgroundColor(Color.parseColor("#f0f0f0"));
        tv2.setPadding(5, 5, 0, 5);
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
        // set the padding (if required)
        tr_head.addView(tv2); // add the column to the table row here

        TextView tv3 = new TextView(this);
        tv3.setText("Amount");

        tv3.setBackgroundColor(Color.parseColor("#f7f7f7"));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
        tv1.setPadding(5, 5, 5, 5);
        tr_head.addView(tv3);// add the column to the table row here


        tl.addView(tr_head, new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        db.execSQL( "create table if not exists transactions(date varchar,recipients varchar,amount varchar,sender_uid varchar)" );


        Cursor cursor = db.rawQuery("select *from transactions where sender_uid='"+unm+"' ", null);
        Integer count=0;
        while (cursor.moveToNext()) {
            String date=cursor.getString(0);// get the first variable
            String recpnt = cursor.getString(1);
            String amount = cursor.getString(2);


            // Create the table row
            TableRow tr = new TableRow(this);
            tr.setBackgroundColor(Color.GRAY);
            tr.setId(100+count);
            tr.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));


            //Create two columns to add as table data
            // Create a TextView to add date
            TextView dat = new TextView(this);
            dat.setId(200+count);
            dat.setText(date);
            dat.setBackgroundColor(Color.parseColor("#ffffff"));
            dat.setTextColor(Color.parseColor("#000000"));
            dat.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            tr.addView(dat);

            TextView recipnt = new TextView(this);
            recipnt.setId(200+count);
            recipnt.setText(recpnt);
            recipnt.setBackgroundColor(Color.parseColor("#f8f8f8"));
            recipnt.setTextColor(Color.parseColor("#000000"));
            recipnt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            tr.addView(recipnt);

            TextView amnt = new TextView(this);
            amnt.setId(200+count);
            amnt.setText("Rs."+amount);
            amnt.setPadding(2, 0, 5, 0);
            amnt.setBackgroundColor(Color.parseColor("#ffffff"));
            amnt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            amnt.setTextColor(Color.parseColor("#000000"));
            tr.addView(amnt);

            // finally add this to the table row
            tl.addView(tr, new TableLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            count++;
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
                Intent i=new Intent(ViewTransactions.this,MainActivity.class);
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

