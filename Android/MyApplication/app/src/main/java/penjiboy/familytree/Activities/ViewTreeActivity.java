package penjiboy.familytree.Activities;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import penjiboy.familytree.R;
import penjiboy.familytree.ViewModels.ViewTreeVM;

public class ViewTreeActivity extends AppCompatActivity {
    private ViewTreeVM viewmodel;
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tree);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        headerText = (TextView) findViewById(R.id.viewTreeHeaderText);
        Application test = getApplication();
        System.out.println(ViewTreeVM.class);
        viewmodel = ViewTreeVM.getInstance(getApplication());


//        viewmodel.currentTreeName.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                headerText.setText(s);
//            }
//        });
        viewmodel.currentTreeName.observe(this, (s) -> headerText.setText(s));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
