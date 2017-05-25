package com.example.silentspaceship.contactmanager;

/**
 * Created by Silent PIYUSH KUMAR on 3/19/2016.
 */

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Contact_list extends ListActivity {
    public void display(Context context)
    {
        String[] FName = {"Sai", "Piyush", "kartheek", "Mike", "Harvey"};
        String[] LName = {"Chakra", "Kumar", "Anumolu", "Ross", "Specter"};
        String[] Mobile = {"22325", "6595468", "4547862", "78712", "121457"};

        List<String> alist = Arrays.asList(FName);    // FName is our own array.
        Collections.sort(alist);   // for alpabetically asending
        //Collections.sort(alist, Collections.reverseOrder());   for alpabetically desending.

        ListView list = (ListView) findViewById(R.id.ContactList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(list.getContext(), android.R.layout.simple_list_item_1, alist);
        list.setAdapter(adapter);

    }
}
