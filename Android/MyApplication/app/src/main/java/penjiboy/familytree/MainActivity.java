package penjiboy.familytree;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button quitButton, loadExistingTreesButton, newTreeButton, currentTreeButton;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureButtons();

        /*
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        */
    }

    private void configureButtons() {
        //Assign each button from the UI onto variable
        quitButton = (Button) findViewById(R.id.quitButton);
        loadExistingTreesButton = (Button) findViewById(R.id.loadExistingTreesButton);
        newTreeButton = (Button) findViewById(R.id.newTreeButton);
        currentTreeButton = (Button) findViewById(R.id.currentTreeButton);

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something in response to button click
                //quitButton.setTextColor(255);
            }
        });


        newTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateNewTreeActivity.class));
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
