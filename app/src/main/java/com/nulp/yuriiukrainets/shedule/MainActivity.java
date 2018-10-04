package com.nulp.yuriiukrainets.shedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner_inst;
    private Spinner spinner_group;
    protected static String currentInst;
    protected static String currentGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner_inst = (Spinner) findViewById(R.id.spinner_inst);
        spinner_group = (Spinner) findViewById(R.id.spinner_group);

        // Create an ArrayAdapter using the string array and a default spinner_inst layout
        ArrayAdapter<CharSequence> adapter_inst_array = ArrayAdapter.createFromResource(this,
                R.array.inst_names_arr, android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter_group_array = ArrayAdapter.createFromResource(this,
                R.array.group_names_arr, android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        adapter_inst_array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_group_array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner_inst
        spinner_inst.setAdapter(adapter_inst_array);
        spinner_group.setAdapter(adapter_group_array);




    }

    protected void goToTabs(View view) throws IOException {
        currentInst = spinner_inst.getSelectedItem().toString();
        currentGroup = spinner_group.getSelectedItem().toString();
        Parser.DownloadFromUrl(this.getApplicationContext(),"MyFile");
        Intent intent = new Intent(MainActivity.this, TabsActivity.class);
        startActivity(intent);
    }


}
