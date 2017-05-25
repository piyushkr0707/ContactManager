package com.example.silentspaceship.contactmanager;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.silentspaceship.contactmanager.R.id.ContactList;

public class MainActivity extends AppCompatActivity {

    public  List <String> FName = new ArrayList<String>();
    public  List <String> LName = new ArrayList<String>();
    public  List <String> alist;  // Strings read from file are added.
    public  ArrayAdapter<String> adapter;
    public  ListView list; // alist is addted to this before sending to the adapter.

    String filename = "Content.txt";
    String mode ="";
    int c ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Contact_list contact_list_obj = new Contact_list();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        c = 0;// mrmorry resetter
        String message= "";
        message +=   "sai"   +"\t"   +"chakra"   +"\t"   +"4698777504"   + "\t"  +"sai@gmail.com"    +"\t"   +"03-19-2016";
        message += "\n";
        message +=   "piyush"+ "\t"  +"kumar"    + "\t"  +"469877518"    + "\t"  +"piy@gmail.com"    + "\t"  +"03-18-2016";
        message += "\n";

        if (c==0)
        {overWrite(message);
            c++; }

        display();  // to display the list view



        // creting on click listner for LIST VIEW to retrive the item selected.
        final ListView list = (ListView) findViewById(ContactList);
        assert list != null;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {                // when item is clicked in the list
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                // selected item
                String selected =(String) (list.getItemAtPosition(myItemInt));
                String First_name_retrived [] = selected.split(" ");
               // Toast.makeText(getApplicationContext(), First_name_retrived[0] , Toast.LENGTH_SHORT).show();

                // jump to next page , show details but disable the add button so that user can only view but not change.

                jumptoNextActivity(First_name_retrived[0]);
            }
        });

    }

    public void jumptoNextActivity(String fn )      // Back button is deleted now.
    {
/* // it passes only one value
        Intent i = new Intent(this, AndroidActivity.class);  // (present class to , next activity).
        String mode = "view";
        i.putExtra("qString", mode);
        startActivity(i);

*/      mode = "view";

        Bundle bund = new Bundle();
        bund.putString("name_Clicked_on",fn);
        bund.putString("qString",mode);

        Intent i = new Intent(this, AndroidActivity.class);
        i.putExtras(bund);
        startActivity(i);

    }
    protected void onRestart() {
        super.onRestart();
        display();
    }


    public void display()
    {
        /*String[] FName = {"Sai", "Piyush", "kartheek", "Mike", "Harvey"};
        String[] LName = {"Chakra", "Kumar", "Anumolu", "Ross", "Specter"};
        String[] Mobile = {"22325", "6595468", "4547862", "78712", "121457"};
*/

        read_FromFile_toListView(); // reading from the file and intiallizing the Arraylist Fname and Lname



        String [] Name = new String[FName.size()];
        for(int i=0 ; i< FName.size() ;i++ )
        {
           Name[i] = FName.get(i) +" "+ LName.get(i);
        }



         alist = Arrays.asList(Name);    // FName is our own array.
        Collections.sort(alist);   // for alpabetically asending
        //Collections.sort(alist, Collections.reverseOrder());   for alpabetically desending.

         list = (ListView) findViewById(R.id.ContactList);
        adapter = new ArrayAdapter<String>(list.getContext(), android.R.layout.simple_list_item_1, alist);
        list.setAdapter(adapter);
        registerForContextMenu(list);      // linking the context menu to a particular list that we are using in activity.

    }

    public void read_FromFile_toListView()
    {   FName.clear();
        LName.clear();

        String x="";

        try {
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            // InputStreamReader isr = getAssets().openFileInput(filename);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();

            while ((x = br.readLine())!= null) {



                String [] line = x.split("\n");
                for(String each_line :line )
                {
                    String [] data = each_line.split("\t");
                    sb.append(data[0]+"\t"+ data[4] + "\n");
                    FName.add(data[0]);
                    LName.add(data[1]);
                }


            }

            //textView.setText(sb.toString());
            //textView.setText("ok!");
            //textView.setVisibility(View.VISIBLE);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //********************** overWrites the file content.***************************
    public void overWrite(String newContent)
    {
        try {

            FileOutputStream fos   =  getApplicationContext().openFileOutput(filename, MODE_PRIVATE);        //MODE_PRIVATE it rewrites the whole file.
            fos.write(newContent.getBytes());
            fos.close();
        }

        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    // ****************************** Deleting the name selected in the list via context menu.
    public void delete(String fn)
    {
        // Reading the file . each line except the which contains String Fn that is first name of the contact we need to delete.
        //Reading starts
        String newContent="";


        try {
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);


             String x ="";
            while ((x = br.readLine()) != null)  // Reading until the end of the file
            {       String[] line=x.split("\n");   // Splitting the file by \n
                    for(String each_line: line)
                    {
                         String data[] = each_line.split("\t");

                        if(fn.equals(data[0]))
                         { // do nothing and so that we can skip reading that whole line

                              }
                        else
                        newContent = newContent+each_line+"\r\n" ;

                    }
            }
            br.close();
            newContent=newContent.trim();  // removing extra line space from the end of the file .

        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

       // now we are going to overwite on the same file with newContent.
        //Toast.makeText(getApplicationContext(),newContent, Toast.LENGTH_LONG).show();
        overWrite(newContent);
        display();   // refreshing the listView.
        Toast.makeText(getApplicationContext(),"Contact deleted", Toast.LENGTH_LONG).show();
    }




//  ***********************  For the icon on action var ***************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_id) {
            mode= "";
            Intent i = new Intent(this, AndroidActivity.class);
            i.putExtra("qString", mode);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
  //********************************************************************************

  // For context menu which will appear on long pressing the list item.

    // Step 1 . inflating the menu hence making it visible.
    @Override

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu , v , menuInfo );

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_context_menu, menu);
    }

    // Step 2. Choosing what to do when the option on menu is selected .

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId())
        {
            case R.id.edit_id  : mode = "edit";
                String selected =(String) (list.getItemAtPosition(info.position));  // info.position contains the integer id of the list item that I clicked
                String First_name_retrived [] = selected.split(" ");
               // Toast.makeText(getApplicationContext(),First_name_retrived [0] , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, AndroidActivity.class);
                i.putExtra("qString", First_name_retrived [0]);  // sending the first name of the contact to be updated.
                startActivity(i);

                return true;
            case R.id.delete_id : mode = "delete";

                 selected =(String) (list.getItemAtPosition(info.position));  // info.position contains the integer id of the list item that I clicked
                 First_name_retrived = selected.split(" ");

                 delete(First_name_retrived [0]);
                return true;

            default:
                return super.onContextItemSelected(item);
        }

       // return super.onContextItemSelected(item);
    }


    //******************************************************************************

}

