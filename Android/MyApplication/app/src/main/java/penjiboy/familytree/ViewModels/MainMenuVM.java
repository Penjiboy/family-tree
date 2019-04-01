package penjiboy.familytree.ViewModels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;

import penjiboy.familytree.Database.AppDatabase;

public class MainMenuVM extends ViewModel {
    public AppDatabase database;

    public MainMenuVM(Application application) {
        //database = AppDatabase.getsInstance(getApplicationContext());
        database = AppDatabase.getsInstance(application.getApplicationContext());
    }
}
