package penjiboy.familytree.ViewModels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;

import penjiboy.familytree.Database.Repository;

public class MainMenuVM extends ViewModel {
    private Repository repository;
    private static MainMenuVM sInstance;

    private MainMenuVM(Application application) {
        repository = new Repository(application);
    }

    public static MainMenuVM getInstance(Application application) {
        if(sInstance == null) {
            sInstance = new MainMenuVM(application);
        }

        return sInstance;
    }

    /**
     * Create a tree with the given name and add it to the database
     * @param name
     * @return true if tree has been successfully added,
     * false otherwise (i.e. another tree with the same name already exists)
     */
    public boolean addTree(String name) {
        return repository.insertTree(name);
    }


}
