package com.wcs.gcaule.galaxian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText playerName = (EditText) findViewById(R.id.playerName);
        final EditText playerScore = (EditText) findViewById(R.id.playerScore);
        final EditText password = (EditText) findViewById(R.id.password);

        final TextView bestPlayerName = (TextView) findViewById(R.id.bestPlayerName);
        final TextView bestPlayerScore = (TextView) findViewById(R.id.bestPlayerScore);

        final FirebaseDatabase playerBoard = FirebaseDatabase.getInstance();
        final DatabaseReference playerRef = playerBoard.getReference("player");

        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (playerName.getText().toString().trim().length() == 0 ||
                        playerScore.getText().toString().trim().length() == 0 ||
                        password.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(MainActivity.this, R.string.emptyFields, Toast.LENGTH_SHORT).show();
                } else {
                    Player player = new Player(playerName.getText().toString(),
                            Integer.parseInt(playerScore.getText().toString()),
                            password.getText().toString());

                    playerRef.push().setValue(player);

                    Toast.makeText(MainActivity.this, R.string.playerCreated, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button seeBestPlayer = (Button) findViewById(R.id.seeBestPlayer);
        seeBestPlayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playerRef.orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                                Player player = playerSnapshot.getValue(Player.class);
                                bestPlayerName.setText(player.getName());
                                bestPlayerScore.setText(Integer.toString(player.getScore()));

                                //Toast.makeText(MainActivity.this, player.getName() + R.string.isBest,
                                        //Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, R.string.emptyDatabase, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {}
                });
            }
        });
    }
}
