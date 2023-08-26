package com.example.events;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpsertEventActivity extends BaseActivity {

    EditText titleInput;
    EditText dateInput;
    EditText detailsInput;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upsert_event);
        titleInput = findViewById(R.id.titleInput);
        dateInput = findViewById(R.id.dateInput);
        detailsInput = findViewById(R.id.detailsInput);
        event = (Event) getIntent().getExtras().getSerializable("event");
        if (event.getId() != 0) {
            titleInput.setText(event.getTitle());
            dateInput.setText(event.getDate());
            detailsInput.setText(event.getDetails());
            Button addButton = findViewById(R.id.addButton);
            addButton.setText("Edit");
        }
    }

    public void addEvent(View view) {
        ContentValues values = new ContentValues();
        values.put("name", titleInput.getText().toString());
        values.put("date", dateInput.getText().toString());
        values.put("details", detailsInput.getText().toString());
        if (event.getId() != 0) {
            db.update("events", values, "id=?", new String[]{Integer.toString(event.getId())});
        } else {
            db.insert("events", null, values);
        }
        openAdminPanel();
    }

    public void cancel(View view) {
        openAdminPanel();
    }

    private void openAdminPanel() {
        Intent intent = new Intent(this, AdminPanel.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}