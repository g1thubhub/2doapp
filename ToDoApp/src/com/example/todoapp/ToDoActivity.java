package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.example.todoapp.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ToDoActivity extends Activity {

	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText edNewItem;
	private final int REQUEST_CODE = 11;
	private static int currentPos = -1; // stores position of edited element

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		edNewItem = (EditText) findViewById(id.textentry);
		lvItems = (ListView) findViewById(R.id.lselements);
		readItems();
		//        populateArrayItems();
		todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(todoAdapter);
		//        todoAdapter.add("Element 4");
		setupListViewListener();
	}


	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View itemclicked, int position, long id) {
				todoItems.remove(position);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}

		});
		// normal click => edit element's content
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View itemclicked, int position, long id) {
				Intent i = new Intent(ToDoActivity.this, EditActivity.class);
				TextView temp = (TextView) itemclicked;
				String itemText = temp.getText().toString();
				i.putExtra("prevtext", itemText);
				//			i.putExtra("position", position);
				currentPos = position;
				//			startActivity(i);
				startActivityForResult(i, REQUEST_CODE);
				writeItems();

			}			
		});
	}

	
	public void onAddedItem(View v) {
		String itemText = edNewItem.getText().toString();
		todoAdapter.add(itemText);
		edNewItem.setText("");
		writeItems();
	}


	private void populateArrayItems() {
		todoItems = new ArrayList<String>();
		todoItems.add("Element 1");
		todoItems.add("Element 2");
		todoItems.add("Element 3");
	}

	private void readItems() {
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch(IOException e) { todoItems = new ArrayList<String>(); }
	}

	private void writeItems() {
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");    	
		try {
			FileUtils.writeLines(todoFile, todoItems);  // automatically serializes
		} catch(IOException e) {e.printStackTrace(); }
	}

	// retrieves edited text from second activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && currentPos > -1) {
			String newContent = data.getExtras().getString("editedText");
			todoItems.remove(currentPos);
			todoItems.add(currentPos, newContent);
			todoAdapter.notifyDataSetChanged();
			currentPos = -1;
			//         Toast.makeText(this, newContent, Toast.LENGTH_SHORT).show();
			writeItems();
		}
	} 




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

}
