package com.mygdx.game.database;

import androidx.annotation.NonNull;

import com.badlogic.gdx.utils.Logger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.ScoreListener;
import com.mygdx.game.controlling.ScoreManager;
import com.mygdx.game.controlling.UserData;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements ScoreListener {

    private static String USER_ID;
    private static final Logger log = new Logger(DatabaseManager.class.getSimpleName(), Logger.DEBUG);

    private static final String HIGHSCORE_KEY = "highscores";
    private static final String NAME_KEY = "name";

    private ScoreManager scoreManager;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public DatabaseManager(AndroidGame game) {
        scoreManager = game.getScoreManager();
        scoreManager.addListener(this);
        USER_ID = scoreManager.getUserId();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(USER_ID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getNameFromDatabase(dataSnapshot);
                getHighscoresFromDatabase(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                log.debug("database error " + databaseError.getMessage());
            }
        });
    }

    private void getNameFromDatabase(@NonNull DataSnapshot dataSnapshot) {
        scoreManager.userData.name = dataSnapshot.child(USER_ID).child(NAME_KEY).getValue(String.class);
        if (scoreManager.userData.name == null) {
            scoreManager.userData.name = scoreManager.getUserId();
        }
    }

    private void getHighscoresFromDatabase(@NonNull DataSnapshot dataSnapshot) {
        int amount = (int) dataSnapshot.child(USER_ID).child(HIGHSCORE_KEY).getChildrenCount();
        scoreManager.userData.highscores.clear();

        for (int i = 0; i < amount; ++i) {
            Integer value = dataSnapshot.child(USER_ID).child(HIGHSCORE_KEY).child(String.valueOf(i)).getValue(Integer.class);
            if (value != null) {
                scoreManager.userData.highscores.add(value);
            }
        }

        if (scoreManager.userData.highscores == null) {
            scoreManager.userData.highscores = new ArrayList<>();
        }
    }

    @Override
    public void onUpdate() {
        sendHighscores();
    }

    private void sendHighscores() {
        reference.child("highscores").removeValue((databaseError, databaseReference) -> {
            if (databaseError == null)
                reference.child("highscores").setValue(scoreManager.userData.highscores);
        });
    }
}
