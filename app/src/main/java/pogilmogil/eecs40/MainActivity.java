package pogilmogil.eecs40;

/**
 *  The SimpleCanvas app demonstrates how to create and draw on a Canvas,
 *  and display the result in an ImageView.
 *
 *  When the user taps the screen, shapes and text are drawn.
 *  All the canvas interaction is implemented in the click handler,
 *  and you do not need a custom view.
 *
 *  Note:
 *  For simplicity, app does not use a custom view.
 *  This app does not teach you to draw.
 * */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mBtLaunchActivity = findViewById(R.id.bt_launch_activity);

        mBtLaunchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity();
            }
        });
    }

    private void launchActivity() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
}