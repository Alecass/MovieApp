package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] nameproducts = new String[] { "Product1", "Product2", "Product3" ,"Product1", "Product2", "Product3","Product1", "Product2", "Product3","Product1", "Product2", "Product3","Product1", "Product2", "Product3"};

        final ArrayList<String> listp = new ArrayList<String>();
        for (int i = 0; i < nameproducts.length; ++i) {
            listp.add(nameproducts[i]);
        }
        final ListView mylist = findViewById(R.id.listView);

        // creo e istruisco l'adattatore
        final ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, listp);

        mylist.setAdapter(adapter);
    }
}
