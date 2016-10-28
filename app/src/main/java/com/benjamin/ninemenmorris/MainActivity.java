package com.benjamin.ninemenmorris;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gameView = new GameView(this);
        gameView.loadGame();
        LinearLayout container = (LinearLayout) findViewById(R.id.linear_layout);
        container.addView(gameView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.saveGame();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_game) {
            gameView.resetGame();
            Toast.makeText(this, "The game is reset!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_save_game) {
            gameView.saveGame();
            Toast.makeText(this, "Game is saved!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_load_game) {
            gameView.loadGame();
            Toast.makeText(this, "Game is loaded!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_close_game) {
            gameView.saveGame();
            finish();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
