package penjiboy.familytree.ViewModels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;

import penjiboy.familytree.Database.Repository;

public class MainMenuVM extends ViewModel {
    private Repository repository;

    public MainMenuVM(Application application) {
        repository = new Repository(application);
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
