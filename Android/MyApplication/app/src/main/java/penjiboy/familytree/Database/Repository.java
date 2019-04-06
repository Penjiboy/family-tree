package penjiboy.familytree.Database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {
    private AppDatabase database;
    private MemberDAO memberDAO;
    private TreeDAO treeDAO;

    public Repository(Application application) {
        database = AppDatabase.getsInstance(application.getApplicationContext());
        memberDAO = database.memberDAO();
        treeDAO = database.treeDAO();
    }

    public boolean insertTree(String name) {
        try {
            return new insertTreeAsyncTask(treeDAO).execute(name).get();
        } catch (ExecutionException ex) {
            return false;
        } catch (InterruptedException ex) {
            return false;
        }
    }

    private static class insertTreeAsyncTask extends AsyncTask<String, Void, Boolean> {
        private TreeDAO treeDAO;

        insertTreeAsyncTask(TreeDAO dao) {
            treeDAO = dao;
        }

        @Override
        protected Boolean doInBackground(String... names) {
            Tree tree = new Tree();
            tree.name = names[0];
            List<Tree> currentTrees = treeDAO.getAllTrees();
            boolean nameExists = false;
            for(Tree currTree: currentTrees) {
                if(currTree.name.equals(tree.name)) {
                    nameExists = true;
                }
            }

            if(!nameExists) {
                treeDAO.insertTrees(tree);
                return true;
            }

            return false;
        }
    }


}
