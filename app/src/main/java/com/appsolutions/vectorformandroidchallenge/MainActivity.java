package com.appsolutions.vectorformandroidchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appsolutions.vectorformandroidchallenge.Fragments.InfoFragment;
import com.appsolutions.vectorformandroidchallenge.Fragments.SelectFragment;
import com.appsolutions.vectorformandroidchallenge.Util.AppSingleton;
import com.appsolutions.vectorformandroidchallenge.Util.CardAdapter;
import com.appsolutions.vectorformandroidchallenge.Util.MovieModel;
import com.appsolutions.vectorformandroidchallenge.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    CardAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ProgressBar progressBar;
    ArrayList<MovieModel> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        mAdapter = new CardAdapter(movies);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (movies.size() > 0 & mRecyclerView != null) {
            mRecyclerView.setAdapter(mAdapter);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        MovieModel movieModel = movies.get(position);

                        Fragment fragment = SelectFragment.newInstance(movieModel.getOverview(),
                                movieModel.getPoster());
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                    @Override public void onLongItemClick(View view, int position) { }
                })
        );
    }

    @Override
    public void onStart(){
        super.onStart();
        JsonRequester();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_info) {
            Fragment fragment = InfoFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void JsonRequester() {


    String url = "https://api.themoviedb.org/3/movie/popular?api_key=e6dfd67cca79e834c3c68f729e937f64&language=en-US&page=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Main", response.getJSONArray("results").toString());

                    JSONArray results = response.getJSONArray("results");

                    for (int i = 0; i <= results.length()-1; i++){
                        MovieModel item = new MovieModel();
                        JSONObject jsonObject = results.getJSONObject(i);
                        item.setId(jsonObject.getString("id"));
                        item.setTitle(jsonObject.getString("title"));
                        item.setOverview(jsonObject.getString("overview"));
                        item.setPopularity(jsonObject.getString("popularity"));
                        item.setPoster(jsonObject.getString("poster_path"));
                        item.setReleaseDate(jsonObject.getString("release_date"));
                        item.setVote(jsonObject.getString("vote_average"));

                        movies.add(item);
                    }

                    progressBar.setVisibility(View.GONE);

                }
                catch (JSONException e){
                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
