package penjiboy.familytree;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button loadExistingTreesButton, newTreeButton, currentTreeButton;

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
        loadExistingTreesButton = (Button) findViewById(R.id.loadExistingTreesButton);
        newTreeButton = (Button) findViewById(R.id.newTreeButton);
        currentTreeButton = (Button) findViewById(R.id.currentTreeButton);

        newTreeButton.setOnClickListener(newTreeButtonListener);

        currentTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, CreateNewTreeActivity.class));

            }
        });
    }

    //setup alertDialog builder for newTreeButton
    private AlertDialog.Builder builder = new AlertDialog.Builder(this);
    private String newTreeName = "";

    private void setupAlertDialog() {
        //Taken from https://stackoverflow.com/questions/10903754/input-text-dialog-android
        //START
        builder.setTitle("New Tree Name");

        //setup the input
        final EditText input = new EditText(this);
        //specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        //setup the buttons
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newTreeName = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //END
    }

    //Create an anonymous implementation of OnClickListener for the newTreeButton
    private View.OnClickListener newTreeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: Implement this
            setupAlertDialog();
            builder.show();
            if(!addDatabaseEntry(newTreeName)) {
                Context context = getApplicationContext();
                CharSequence text = "Failed to create a new tree, please try again";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                Toast.makeText(getApplicationContext(),"Succesfully created a new tree", Toast.LENGTH_SHORT)
                        .show();

                //Now switch to the activity responsible for adding members to a tree.
            }
        }

        private boolean addDatabaseEntry(String name) {
            //TODO: Implement this method
            return false; //change this
        }
    };

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
