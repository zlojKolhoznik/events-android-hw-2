package com.example.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventDetails extends BaseActivity {

    private Event event;

    public EventDetails() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        event = (Event) getIntent().getExtras().getSerializable("event");
        TextView title = findViewById(R.id.title);
        title.setText(event.getTitle());
        TextView date = findViewById(R.id.date);
        date.setText(event.getDate());
        TextView details = findViewById(R.id.details);
        details.setText(event.getDetails());
    }

    public void openMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}