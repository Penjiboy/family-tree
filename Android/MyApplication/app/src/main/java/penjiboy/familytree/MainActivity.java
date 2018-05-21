package penjiboy.familytree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        //Assign each button from the UI onto variable
        quitButton = (Button) findViewById(R.id.quitButton);
        loadExistingTreesButton = (Button) findViewById(R.id.loadExistingTreesButton);
        newTreeButton = (Button) findViewById(R.id.newTreeButton);
        currentTreeButton = (Button) findViewById(R.id.currentTreeButton);

        /*
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        */
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
