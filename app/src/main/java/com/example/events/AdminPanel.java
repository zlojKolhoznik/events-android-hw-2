package com.example.events;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminPanel extends BaseActivity {

    private ArrayList<Event> events;
    private TableLayout table;

    public AdminPanel() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        Cursor cursor = db.rawQuery("SELECT * FROM events", null);
        events = Event.parseList(cursor);
        cursor.close();
        table = findViewById(R.id.table);
        if (events.size() == 0) {
            TextView errorMessage = findViewById(R.id.errorMessage);
            errorMessage.setText("No events yet");
            return;
        }

        showEvents();
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
            TableRow.LayoutParams rowChildrenParams = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rowChildrenParams.setMargins(10, 0, 30, 0);
            titleView.setLayoutParams(rowChildrenParams);
            row.addView(titleView);

            Button editButton = new Button(this);
            editButton.setText("Edit");
            editButton.setLayoutParams(rowChildrenParams);
            editButton.setOnClickListener(view -> {
                Intent intent = new Intent(this, UpsertEventActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("event", event);
                startActivity(intent);
            });
            row.addView(editButton);

            Button deleteButton = new Button(this);
            deleteButton.setText("Delete");
            deleteButton.setLayoutParams(rowChildrenParams);
            deleteButton.setOnClickListener(view -> {
                db.delete("events", "id=?", new String[]{Integer.toString(event.getId())});
                showEvents();
            });
            row.addView(deleteButton);
            table.addView(row);
        }
    }

    public void openMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void addEvent(View view) {
        Intent intent = new Intent(this, UpsertEventActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Event event = new Event(null, null, null, 0);
        intent.putExtra("event", event);
        startActivity(intent);
    }
}