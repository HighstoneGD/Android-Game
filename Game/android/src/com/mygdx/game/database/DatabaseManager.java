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
import com.mygdx.game.controlling.scores.ScoreManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DatabaseManager implements ScoreListener {

    private static final Logger log = new Logger(DatabaseManager.class.getSimpleName(), Logger.DEBUG);
    private static String userId;

    private AndroidGame game;
    private ScoreManager scoreManager;

    private FirebaseDatabase database;
    private DatabaseReference userReference;
    private DatabaseReference topPlayersReference;

    public DatabaseManager(AndroidGame game) {
        this.game = game;
        initScoreManager();

        database = FirebaseDatabase.getInstance();
        initUserReference();
        initTopPlayersReference();
    }

    private void initScoreManager() {
        scoreManager = game.getScoreManager();
        scoreManager.addListener(this);
        userId = scoreManager.getUserId();
    }

    private void initUserReference() {
        userReference = database.getReference("users").child(userId);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                retrieveNameFromDatabase(dataSnapshot);
                retrieveHighscoresFromDatabase(dataSnapshot);
                logResult();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                log.debug("database error " + databaseError.getMessage());
            }
        });
    }

    private void initTopPlayersReference() {
        topPlayersReference = database.getReference("top10");
        topPlayersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                log.debug(databaseError.getMessage());
            }
        });
    }

    private void retrieveNameFromDatabase(@NonNull DataSnapshot dataSnapshot) {
        scoreManager.userData.name = dataSnapshot.child(DatabaseManagerUtils.NAME_KEY).getValue(String.class);
        if (scoreManager.userData.name == null) {
            userReference.child(DatabaseManagerUtils.NAME_KEY).setValue(userId);
        }
    }

    private void retrieveHighscoresFromDatabase(@NonNull DataSnapshot dataSnapshot) {
        scoreManager.userData.highscores.clear();
        List<Long> values = (List<Long>) dataSnapshot.child(DatabaseManagerUtils.HIGHSCORE_KEY).getValue();
        addToUserData(values);
    }

    private void addToUserData(List<Long> values) {
        try {
            scoreManager.userData.highscores.addAll(values);
            Collections.sort(scoreManager.userData.highscores);
        } catch (NullPointerException e) {
            e.getStackTrace();
        }

        if (scoreManager.userData.highscores == null) {
            scoreManager.userData.highscores = new ArrayList<>();
        }
    }

    private void logResult() {
        log.debug("username: " + scoreManager.userData.name);
        log.debug("highscores: " + scoreManager.userData.highscores);
    }

    @Override
    public void onUpdate() {
        sendHighscores();
    }

    private void sendHighscores() {
        userReference.child(DatabaseManagerUtils.HIGHSCORE_KEY).setValue(scoreManager.userData.highscores)
                .addOnFailureListener((e) -> log.debug(e.getMessage()));
    }
}
