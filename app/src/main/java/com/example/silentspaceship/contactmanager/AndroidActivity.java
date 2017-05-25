package com.example.silentspaceship.contactmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AndroidActivity extends AppCompatActivity {

    String filename = "Content.txt";
    String qString;
    TextView textView;

    EditText FN;
    String fn;
    EditText LN;
    String ln;
    EditText PhNo;
    String phno;
    EditText Email;
    String email;
    EditText Datee;
    String datee;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // getting then data from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras == null)
            return;
        qString = extras.getString("qString");
        String name_Clicked_on = extras.getString("name_Clicked_on");
        if (!qString.equals("") && !qString.equals("view")) {     // it means its in edit mode

            read_FromFile_toTextBx(qString);

        }

        if (qString.equals("view")) {
            read_FromFile_toTextBx(name_Clicked_on);
        }

        if(qString.equals("")) {
            CurrentDateSetter();
        }

        FN = (EditText) findViewById(R.id.FNtxtbx);
        fn = FN.getText().toString();
        LN = (EditText) findViewById(R.id.LNtxtbx);
        ln = LN.getText().toString();
        PhNo = (EditText) findViewById(R.id.PhNotxtbx);
        phno = PhNo.getText().toString();
        Email = (EditText) findViewById(R.id.Emailtxtbx);
        email = Email.getText().toString();
        Datee = (EditText) findViewById(R.id.Datetxbx);
        datee = Datee.getText().toString();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



/*
    public void BackButnOnClick(View view)      // Back button is deleted now.
    {

        Intent i = new Intent(this, MainActivity.class);
        String myString = "Hey there ";
        i.putExtra("qString", myString);
        startActivity(i);


    }
*/

    public void CurrentDateSetter() {           // sets the date to current date.

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");          // Getting the system date
        Date date = new Date();
        String CurrentDate =df.format(date);

        Datee = (EditText) findViewById(R.id.Datetxbx);
        Datee.setText(CurrentDate);
    }

    public int DateValidation() {                // if the date is correct or intered in the correct format and is in future.
        int fault_count=0;
        Datee                           = (EditText) findViewById(R.id.Datetxbx);
        String datefromTxtbx            =Datee.getText().toString().trim();
        String [] datefromTxtBx_array   = datefromTxtbx.split("-");
        int month                       = Integer.parseInt(datefromTxtBx_array[0]);
        int day                         = Integer.parseInt(datefromTxtBx_array[1]);
        int year                        = Integer.parseInt(datefromTxtBx_array[2]);

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");          // Getting the system date
        Date date = new Date();
        String CurrentDate =df.format(date);
        String [] CurrentDate_array  = CurrentDate.split("-");
        int Cur_month                       = Integer.parseInt(CurrentDate_array[0]);
        int Cur_day                         = Integer.parseInt(CurrentDate_array[1]);
        int Cur_year                        = Integer.parseInt(CurrentDate_array[2]);

        if(year>Cur_year)
            return 1;   // future date .
        else if (year<Cur_year)
            return 0;   // ok date
        else
        {
            if(month>Cur_month)
                return 1;
            else if(month<Cur_month)
                return 0;
            else
            {
                if(day>Cur_day)
                    return 1;
                else
                    return 0;
            }

        }



    }

    public void writeMessage() {
        EditText F = (EditText) findViewById(R.id.FNtxtbx);


      /*  String message= "";
       message +=   "sai"   +"\t"   +"chakra"   +"\t"   +"4698777504"   + "\t"  +"sai@gmail.com"    +"\t"   +"03-19-2016";
        message += "\n";
        message +=   "piyush"+ "\t"  +"kumar"    + "\t"  +"469877518"    + "\t"  +"piy@gmail.com"    + "\t"  +"03-18-2016";
        message += "\n";
        */
        String newContact = "";
        newContact += FN.getText().toString() + "\t" + LN.getText().toString() + "\t" + PhNo.getText().toString() + "\t" + Email.getText().toString() + "\t" + Datee.getText().toString() + "\n";

        // Toast.makeText(getApplicationContext(),"I have added: "+ newContact , Toast.LENGTH_SHORT).show();


        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(filename, MODE_APPEND);
            // FileOutputStream fos   =  getApplicationContext().openFileOutput(filename, MODE_PRIVATE);        //MODE_PRIVATE it rewrites the whole file.
            fos.write(newContact.getBytes());
            fos.close();
            Toast.makeText(getApplicationContext(), "New Contact added", Toast.LENGTH_LONG).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void read_FromFile_toTextBx(String fn) {

        FN = (EditText) findViewById(R.id.FNtxtbx);
        LN = (EditText) findViewById(R.id.LNtxtbx);
        PhNo = (EditText) findViewById(R.id.PhNotxtbx);
        Email = (EditText) findViewById(R.id.Emailtxtbx);
        Datee = (EditText) findViewById(R.id.Datetxbx);


        // start reading .

        try {
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);


            String x = "";
            while ((x = br.readLine()) != null)  // Reading until the end of the file
            {
                String[] line = x.split("\n");   // Splitting the file by \n

                for (String each_line : line) {
                    String data[] = each_line.split("\t");
                    fn = fn.trim();
                    data[0] = data[0].trim();


                    if (fn.equals(data[0])) {

                        FN.setText(data[0]);
                        //FN.setEnabled(false);  // disabling the TextView.
                        LN.setText(data[1]);
                        PhNo.setText(data[2]);
                        Email.setText(data[3]);
                        Datee.setText(data[4]);

                        break;  // so that it don't need to check all other records.
                    }


                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void overWrite(String newContent)    // overwrites the file with new content.
    {
        try {

            FileOutputStream fos = getApplicationContext().openFileOutput(filename, MODE_PRIVATE);        //MODE_PRIVATE it rewrites the whole file.
            fos.write(newContent.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void edit() {
        String newline = "";
        newline += FN.getText().toString().trim() + "\t" + LN.getText().toString().trim() + "\t" + PhNo.getText().toString().trim() + "\t" + Email.getText().toString().trim() + "\t" + Datee.getText().toString().trim() + "\n";

        // Reading the file . each line except the which contains String Fn that is first name of the contact we need to delete.
        //Reading starts
        String newContent = "";


        try {
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);


            String x = "";
            while ((x = br.readLine()) != null)  // Reading until the end of the file
            {
                String[] line = x.split("\n");   // Splitting the file by \n
                for (String each_line : line) {
                    String data[] = each_line.split("\t");
                    fn = fn.trim();
                    data[0] = data[0].trim();

                    if (fn.equals(data[0])) {
                        newContent = newContent + newline + "\r\n";

                    } else
                        newContent = newContent + each_line + "\r\n";

                }
            }
            br.close();
            newContent = newContent.trim();  // removing extra line space from the end of the file .

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // now we are going to overwite on the same file with newContent.
        //Toast.makeText(getApplicationContext(),newContent, Toast.LENGTH_LONG).show();
        overWrite(newContent);
        //display();   // refreshing the listView.
        Toast.makeText(getApplicationContext(), "Contact Updated", Toast.LENGTH_LONG).show();


    }

    public void readFileContent() {
        String x = "";

        try {
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            // InputStreamReader isr = getAssets().openFileInput(filename);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();

            while ((x = br.readLine()) != null) {


                String[] line = x.split("\n");
                for (String each_line : line) {
                    String[] data = each_line.split("\t");
                    sb.append(each_line + "\n");

                }


            }
            Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  ***********************  For the icon on action var ***************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!qString.equals("view"))   // to make sure that if in view mode the save button on action bar doesnot app
        {
            getMenuInflater().inflate(R.menu.save_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int count = DateValidation();
        //noinspection SimplifiableIfStatement
        if (id == R.id.save_id) {
            if (!qString.equals("") && !qString.equals("view")) {     // it means its in edit mode

                if(count !=0)
                { Toast.makeText(getApplicationContext(), "Date should not be in future", Toast.LENGTH_SHORT).show();
                }
                else {
                    edit();
                    //readFileContent();
                }
            }

            if (qString.equals("")) {

                if(count !=0)
                { Toast.makeText(getApplicationContext(), "Date should not be in future", Toast.LENGTH_SHORT).show();
                }
               else {
                    writeMessage();
                    //readFileContent();

                }


            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Android Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.silentspaceship.contactmanager/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Android Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.silentspaceship.contactmanager/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
