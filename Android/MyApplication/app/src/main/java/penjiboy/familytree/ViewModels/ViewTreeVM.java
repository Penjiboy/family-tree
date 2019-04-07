package penjiboy.familytree.ViewModels;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import penjiboy.familytree.BuildConfig;
import penjiboy.familytree.Database.Repository;
import penjiboy.familytree.Database.Tree;

public class ViewTreeVM extends ViewModel {
    private Repository repository;
    private static ViewTreeVM sInstance;
    public MutableLiveData<String> currentTreeName;
    private Tree currentTree;

    private ViewTreeVM(Application application) {
        repository = new Repository(application);
        currentTree = repository.getAllTrees().get(repository.getAllTrees().size() -1);
        try {
            currentTreeName = new MutableLiveData<>();
            currentTreeName.setValue(currentTree.name);
        } catch (NullPointerException ex) {
            throw ex;
        }
    }

    public static ViewTreeVM getInstance(Application application) {
        if(sInstance == null) {
            sInstance = new ViewTreeVM(application);
        }

        return sInstance;
    }
}
