package com.example.bgustafson37.tasklist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> list;
    private ArrayAdapter<String> listAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lvListTask);
        readFromFile();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(listAdapter);
        setupLongClickListener();

    }

    private  void  setupLongClickListener(){
        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        list.remove(position);
                        listAdapter.notifyDataSetChanged();
                        return false;
                    }
                });
    }

    public void onAddTask(View v) {
        EditText etNewTask = (EditText) findViewById(R.id.etNewTask);
        String strNewTask = etNewTask.getText().toString();
        if (!strNewTask.isEmpty()) {
            listAdapter.add(strNewTask);
            etNewTask.setText("");
            saveToFile();
        }
    }

    private void saveToFile(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "tasklist.txt");
        try{
            FileUtils.writeLines(todoFile,list);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readFromFile(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "tasklist.txt");
        try{
            list = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            list = new ArrayList<String>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
