package com.dod.newsreader.author;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dod.newsreader.NewsReader;
import com.dod.newsreader.NewsReaderApplication;
import com.dod.newsreader.R;
import com.dod.newsreader.article.ArticleFragment;
import com.dod.newsreader.model.Author;
import com.dod.newsreader.model.Newspaper;
import com.dod.newsreader.newspaper.NewspaperFragment;
import com.dod.newsreader.utils.AsyncResult;
import com.dod.newsreader.utils.FontApplicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deniz on 17.08.2016.
 */
public class AuthorFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    public static String TARGET = "Author";
    private static final String LOG_TAG = "AuthorFragment";
    private ActionMode actionMode;
    private AsyncTask loadAuthorTask;
    private List<Author> authors = null;
    private AbsListView listView;
    private AuthorAdapter authorAdapter;
    public Newspaper targetNewspaper = null ;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Log.d(LOG_TAG, "Fragment created");

        setHasOptionsMenu(true);
        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(NewspaperFragment.TARGET)){
            targetNewspaper = (Newspaper)arguments.getSerializable(NewspaperFragment.TARGET);

        }
        loadAuthorList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_newspaper, container, false);
        this.listView = (AbsListView) view.findViewById(R.id.list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            listView.setFastScrollAlwaysVisible(true);
        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Object selectedObject = adapterView.getItemAtPosition(position);
        if (selectedObject instanceof Author) {
            if (actionMode == null) {
                Author selectedAuthor = (Author) selectedObject;
                navigateTo(selectedAuthor);
            } else {
                //toggleAuthorSelected((Author) selectedObject);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    void navigateTo(Author Author) {
        TARGET = Author.getName();
        NewsReader activity = (NewsReader) getActivity();
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putSerializable(TARGET, Author);
        fragment.setArguments(args);
        activity.showFragment(fragment);
    }

    NewsReaderApplication getApplication() {
        if (getActivity() == null) return null;
        return (NewsReaderApplication) getActivity().getApplication();
    }
    FontApplicator getFontApplicator()
    {
        NewsReader newsReader = (NewsReader) getActivity();
        return newsReader.getFontApplicator();
    }

    void showMessage(CharSequence message)
    {
        View view = getView();
        if (view != null)
        {
            getListView().setVisibility(View.GONE);
            view.findViewById(R.id.layoutMessage).setVisibility(View.VISIBLE);
            view.findViewById(R.id.progress).setVisibility(View.GONE);
            TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
            tvMessage.setText(message);

        }
    }

    private AbsListView getListView() {
        return this.listView;
    }

    void showMessage(int message)
    {
        showMessage(getString(message));
    }
    void loadAuthorList() {
        if (loadAuthorTask != null) {
            return;
        }

        this.loadAuthorTask = new AsyncTask<Newspaper, Void, AsyncResult<List<Author>>>() {
            @Override
            protected AsyncResult<List<Author>> doInBackground(Newspaper... newspaperParams) {
                try {
                    List<Author> authors = null ;
                    if(newspaperParams[0] != null){
                        authors = getApplication().getAuthorManager().getAuthorByNewspaper(newspaperParams[0]);
                    }else{
                        authors = getApplication().getAuthorManager().getAllAuthors();
                    }


                    if (authors == null)
                        throw new NullPointerException(getString(R.string.no_authors, R.string.no_authors));
                    if (isCancelled())
                        throw new Exception("Task cancelled");

                    return new AsyncResult<List<Author>>(authors);
                } catch (Exception e) {
                    Log.e(LOG_TAG,"LoadAuthorsTask" ,e);
                    return new AsyncResult<List<Author>>(e);
                }
            }

            @Override
            protected void onCancelled(AsyncResult<List<Author>> result) {
                loadAuthorTask = null;
            }



            @Override
            protected void onPostExecute(AsyncResult<List<Author>> result) {
                Log.d("Author fragment", "Task finished");
                loadAuthorTask = null;

                AuthorAdapter adapter;
                try {

                    authors = result.getResult();
                    if (authors.isEmpty()) {
                        showMessage(R.string.no_authors);
                        return;
                    }

                    adapter = new AuthorAdapter(getActivity(), authors);
                    adapter.notifyDataSetChanged();

                    //adapter.setFontApplicator(getFontApplicator());
                    setListAdapter(adapter);
                    Log.d("Author fragment", "List adapter set");
                } catch (Exception e) {
                    // exception was thrown while loading files
                    showMessage(e.getMessage());
                    adapter = new AuthorAdapter(getActivity(),new ArrayList<Author>());
                }

                getActivity().invalidateOptionsMenu();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,targetNewspaper);
    }
    private void setListAdapter(AuthorAdapter authorAdapter)
    {
        this.authorAdapter = authorAdapter;
        if (listView != null)
        {
            listView.setAdapter(authorAdapter);

            listView.invalidateViews();
            listView.refreshDrawableState();
            listView.setSelected(true);
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                   Object selectedObject = adapterView.getItemAtPosition(position);
                   if (selectedObject instanceof Author) {
                       if (actionMode == null) {
                           Author selectedAuthor = (Author) selectedObject;
                           navigateTo(selectedAuthor);
                       } else {
                           //toggleAuthorSelected((Author) selectedObject);
                       }
                   }
               }
           });
            getView().findViewById(R.id.layoutMessage).setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

        }
    }
}
