package com.example.events;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private TableLayout table;
    private TextView errorMessage;
    private ArrayList<Event> events;

    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table = findViewById(R.id.table);
        errorMessage = findViewById(R.id.errorMessage);

        Cursor cursor = db.rawQuery("SELECT * FROM events", null);
        events = Event.parseList(cursor);
        if (events == null) {
            errorMessage.setText("No events found");
            return;
        }


        showEvents();

        cursor.close();
    }

    public void filterEvents(View view) {
        String query = ((EditText)findViewById(R.id.searchInput)).getText().toString();
        Cursor cursor = db.rawQuery("SELECT * FROM events WHERE name LIKE '%" + query + "%'", null);
        if (!cursor.moveToFirst()) {
            errorMessage.setText("No events found");
            return;
        }

        events = Event.parseList(cursor);
        showEvents();

        cursor.close();
    }

    private void showEvents() {
        table.removeAllViews();
        for (Event event : events) {
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0, 20, 0, 20);
            row.setLayoutParams(rowParams);

            TextView titleView = new TextView(this);
            titleView.setText(event.getTitle());
            TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewParams.setMargins(10, 0, 30, 0);
            titleView.setLayoutParams(textViewParams);
            row.addView(titleView);

            TextView dateView = new TextView(this);
            dateView.setText(event.getDate());
            dateView.setLayoutParams(textViewParams);
            row.addView(dateView);

            Button goToDetails = new Button(this);
            goToDetails.setText("Details");
            goToDetails.setOnClickListener(view -> {
                Intent intent = new Intent(this, EventDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("event", event);
                startActivity(intent);
            });
            goToDetails.setLayoutParams(textViewParams);
            row.addView(goToDetails);

            table.addView(row);
        }
    }

    public void openAdmin(View view) {
        Intent intent = new Intent(this, AdminPanel.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}