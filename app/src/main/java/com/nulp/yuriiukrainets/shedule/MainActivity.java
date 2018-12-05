package com.nulp.yuriiukrainets.shedule;

import android.content.Intent;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private InstGroupSelector selector;
    private Spinner spinner_inst;
    private Spinner spinner_group;
    protected static List<String> instNames;
    protected static List<String> groupNames;
    protected static String currentInst;
    protected static String currentGroup;
    protected static String currentURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selector = new InstGroupSelector(this, (ProgressBar) findViewById(R.id.progressBar));
        spinner_inst = (Spinner) findViewById(R.id.spinner_inst);
        spinner_group = (Spinner) findViewById(R.id.spinner_group);


        //Dynamic adding data to institute spinner
        try {
            instNames = selector.getFromSiteBySelector(
                    "http://lp.edu.ua/students_schedule?institutecode_selective=All&edugrupabr_selective=All",
                    "#edit-institutecode-selective");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if(instNames != null) {
            ArrayAdapter<String> spinnerAdapterInst = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, instNames);
            spinnerAdapterInst.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_inst.setAdapter(spinnerAdapterInst);
        } else {
            Toast.makeText(this.getApplicationContext(), "Не вдалось отримати дані з сайту :(" , Toast.LENGTH_LONG).show();
        }


        //Selected event listener
        spinner_inst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    groupNames = selector.getFromSiteBySelector("http://lp.edu.ua/students_schedule?institutecode_selective=" +
                            spinner_inst.getSelectedItem().toString() +
                            "&edugrupabr_selective=All", "#edit-edugrupabr-selective-wrapper");
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                if(groupNames != null) {
                    ArrayAdapter<String> spinnerAdapterGroup = new ArrayAdapter<String>(selectedItemView.getContext(), android.R.layout.simple_spinner_item, groupNames);
                    spinnerAdapterGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_group.setAdapter(spinnerAdapterGroup);
                } else {
                    Toast.makeText(selectedItemView.getContext(), "Не вдалось отримати дані з сайту :(" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinner_group.setEnabled(false);
            }
        });



    }

    protected void goToTabs(View view) throws IOException {
            currentInst = spinner_inst.getSelectedItem().toString();
            currentGroup = spinner_group.getSelectedItem().toString();
            currentURL = "http://lp.edu.ua/students_schedule?institutecode_selective=" +
                    MainActivity.currentInst +
                    "&edugrupabr_selective=" +
                    MainActivity.currentGroup;
            new LecturesParser().execute(currentURL);
            Intent intent = new Intent(MainActivity.this, TabsActivity.class);
            startActivity(intent);
        }
    }


