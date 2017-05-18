package xyz.bnayagrawal.android.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

public class stopwatch extends Activity {
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        //if activity was recreated due to any config change.
        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    //start the stopwatch running when the button is clicked
    public void onClickStart(View view) {
        running = true;
    }

    //stop the stopwatch
    public void onClickStop(View view) {
        running = false;
    }

    //reset the stopwatch
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    //updates the time
    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    //gets called before the activity being destroyed. We can save any values here.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    //gets called when the activity has lost its focus.
    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //gets called after the activity is visible to the user.
    @Override
    public void onResume() {
        super.onResume();
        running = wasRunning ? true : false;
    }
}
