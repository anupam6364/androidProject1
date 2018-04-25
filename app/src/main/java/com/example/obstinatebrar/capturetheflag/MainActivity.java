package com.example.obstinatebrar.capturetheflag;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText name;
    FirebaseDatabase db;
    DatabaseReference rootNode;
    private String team = "";
    RadioGroup radioGroup;
    RadioButton teamBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        // setup the database
        db = FirebaseDatabase.getInstance();
        rootNode = db.getReference();
        teamBtn=(RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        // [EDIT END]
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.teamA:
                        team = "Team A";
                        break;
                    case R.id.teamB:
                        team = "Team B";
                        break;
                }
                //team = teamBtn.getText().toString();
            }
        });

    }
    public void startGame(View v) {

        String name1 = name.getText().toString();
        //User user = new User(name1, team);
        String  id = rootNode.child("Users").child(team).push().getKey();

        //rootNode.child("Users").child(id).setValue(user);
        name.setText(" ");
        Intent intent = new Intent(MainActivity.this,LocationActivity.class);
        intent.putExtra("personName", name1);
        intent.putExtra("team", team);
        intent.putExtra("id", id);
        startActivity(intent);

    }

    public void observerActivity(View v){
        Intent intent = new Intent(MainActivity.this,ObserverActivity.class);
        startActivity(intent);
    }
}
