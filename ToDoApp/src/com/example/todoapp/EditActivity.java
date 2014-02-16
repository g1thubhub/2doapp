package com.example.todoapp;

import com.example.todoapp.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {

	private EditText changeItem;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		changeItem = (EditText) findViewById(id.textentry);
		changeItem.setFocusable(true); // set focus
		changeItem.requestFocus();
		String previous = getIntent().getStringExtra("prevtext");
		changeItem.setText(previous);
		changeItem.setSelection(changeItem.getText().length());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	public void onSubmit(View v) {
		changeItem = (EditText) findViewById(id.textentry);
		String afterEdit = changeItem.getText().toString();
		Intent data = new Intent();
		data.putExtra("editedText", afterEdit);
		setResult(RESULT_OK, data);
		finish(); 
	}

}
