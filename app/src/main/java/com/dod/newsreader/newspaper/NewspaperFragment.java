package com.dod.newsreader.newspaper;

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
import com.dod.newsreader.author.AuthorFragment;
import com.dod.newsreader.model.Newspaper;
import com.dod.newsreader.utils.AsyncResult;
import com.dod.newsreader.utils.FontApplicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deniz on 17.08.2016.
 */
public class NewspaperFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    public static String TARGET = "newspaper";
    private static final String LOG_TAG = "NewspaperFragment";
    private ActionMode actionMode;
    private AsyncTask loadNewspaperTask;
    private List<Newspaper> newspapers = null;
    private AbsListView listView;
    private NewspaperAdapter newspaperAdapter;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Log.d(LOG_TAG, "Fragment created");

        setHasOptionsMenu(true);

        loadNewspaperList();
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
        if (selectedObject instanceof Newspaper) {
            if (actionMode == null) {
                Newspaper selectedNewspaper = (Newspaper) selectedObject;
                navigateTo(selectedNewspaper);
            } else {
                //toggleNewsPaperSelected((Newspaper) selectedObject);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    void navigateTo(Newspaper newspaper) {
        TARGET = newspaper.getName();
        NewsReader activity = (NewsReader) getActivity();
        AuthorFragment fragment = new AuthorFragment();
        Bundle args = new Bundle();
        args.putSerializable(TARGET, newspaper);
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
    void loadNewspaperList() {
        if (loadNewspaperTask != null) {
            return;
        }
        this.loadNewspaperTask = new AsyncTask<Newspaper, Void, AsyncResult<List<Newspaper>>>() {
            @Override
            protected AsyncResult<List<Newspaper>> doInBackground(Newspaper... newspaperParams) {
                try {

                   List<Newspaper> newspapers = getApplication().getNewspaperManager().getNewspapers();

                    if (newspapers == null)
                        throw new NullPointerException(getString(R.string.no_newspapers, R.string.no_newspapers));
                    if (isCancelled())
                        throw new Exception("Task cancelled");

                    return new AsyncResult<List<Newspaper>>(newspapers);
                } catch (Exception e) {
                    Log.e(LOG_TAG,"LoadNewspapersTask" ,e);
                    return new AsyncResult<List<Newspaper>>(e);
                }
            }

            @Override
            protected void onCancelled(AsyncResult<List<Newspaper>> result) {
                loadNewspaperTask = null;
            }



            @Override
            protected void onPostExecute(AsyncResult<List<Newspaper>> result) {
                Log.d("Newspaper fragment", "Task finished");
                loadNewspaperTask = null;

                NewspaperAdapter adapter;
                try {

                    newspapers = result.getResult();
                    if (newspapers.isEmpty()) {
                        showMessage(R.string.no_newspapers);
                        return;
                    }

                    adapter = new NewspaperAdapter(getActivity(), newspapers);
                    adapter.notifyDataSetChanged();

                    //adapter.setFontApplicator(getFontApplicator());
                    setListAdapter(adapter);
                    Log.d("Newspaper fragment", "List adapter set");
                } catch (Exception e) {
                    // exception was thrown while loading files
                    showMessage(e.getMessage());
                    adapter = new NewspaperAdapter(getActivity(),new ArrayList<Newspaper>());
                }

                getActivity().invalidateOptionsMenu();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,new Newspaper(-1,"Olmadı",-1));
    }
    private void setListAdapter(NewspaperAdapter newspaperAdapter)
    {
        this.newspaperAdapter = newspaperAdapter;
        if (listView != null)
        {
            listView.setAdapter(newspaperAdapter);

            listView.invalidateViews();
            listView.refreshDrawableState();
            listView.setSelected(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Object selectedObject = adapterView.getItemAtPosition(position);
                    if (selectedObject instanceof Newspaper) {
                        if (actionMode == null) {
                            Newspaper selectedNewspaper = (Newspaper) selectedObject;
                            navigateTo(selectedNewspaper);
                        } else {
                            //toggleNewsPaperSelected((Newspaper) selectedObject);
                        }
                    }
                }
            });

            getView().findViewById(R.id.layoutMessage).setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

        }
    }
}
