package com.example.movie;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eightbitlab.com.blurview.BlurAlgorithm;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderEffectBlur;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends AppCompatActivity {

    PopupWindow popupWindow,popupWindow1;
    View v,collectionv;
    SearchView searchView;
    DAO dao_object;
    int searchflag=0,watchlistflag=0;
    boolean bflag=false;
    String query;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private BlurView bottomBlurView;
    private ViewGroup blurroot;
    boolean notify=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupBlurView();

        Button newsButton = findViewById(R.id.newsbutton);
        ScrollView scrollView = findViewById(R.id.scroll);
        RelativeLayout newsLayout = findViewById(R.id.newsLayout);
        TextView newsTextView = findViewById(R.id.newsTextView);

        NewsFetcher newsFetcher = new NewsFetcher(this, newsLayout, newsTextView);

        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.setVisibility(View.GONE);
                newsFetcher.fetchNews();
            }
        });


        loadData("Trending",R.id.trendingRVIEW);
        loadData("NetflixOriginals",R.id.netflixoriginalsRVIEW);
        loadData("TopRated",R.id.topratedRVIEW);
        loadData("ActionMovies",R.id.actionmoviesRVIEW);
        loadData("ComedyMovies",R.id.comedymoviesRVIEW);
        loadData("HorrorMovies",R.id.horrormoviesRVIEW);
        loadData("RomanceMovies",R.id.romancemoviesRVIEW);

        //AppDatabase.clearInstance(this);

        AppDatabase db = Room.databaseBuilder(MainActivity.this,
                AppDatabase.class, "AppDatabase").fallbackToDestructiveMigration().build();



        dao_object = db.dao();

        ImageButton profilepic=findViewById(R.id.profile);

        MaterialButton movie=findViewById(R.id.movies);
        MaterialButton tv=findViewById(R.id.tvshows);
        MaterialButton upcoming=findViewById(R.id.upcoming);

        movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=MainActivity.this;
                LayoutInflater layoutInflater=LayoutInflater.from(context);

                collectionv=layoutInflater.inflate(R.layout.collections,null);
                TextView t=collectionv.findViewById(R.id.collection);
                t.setText("Movies");

                popupWindow1=new PopupWindow(collectionv, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                popupWindow1.setEnterTransition(new Slide());
                popupWindow1.showAtLocation(collectionv, Gravity.BOTTOM,0,0);
                popupWindow1.setExitTransition(new Slide());
                int id=R.id.collectionRVIEW;
                loadData("Movie",id);

            }
        });



        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=MainActivity.this;
                LayoutInflater layoutInflater=LayoutInflater.from(context);

                collectionv=layoutInflater.inflate(R.layout.collections,null);
                TextView t=collectionv.findViewById(R.id.collection);
                t.setText("TV Shows");

                popupWindow1=new PopupWindow(collectionv, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                popupWindow1.setEnterTransition(new Slide());
                popupWindow1.showAtLocation(collectionv, Gravity.BOTTOM,0,0);
                popupWindow1.setExitTransition(new Slide());
                int id=R.id.collectionRVIEW;
                loadData("TV",id);
            }
        });

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=MainActivity.this;
                LayoutInflater layoutInflater=LayoutInflater.from(context);

                collectionv=layoutInflater.inflate(R.layout.collections,null);
                TextView t=collectionv.findViewById(R.id.collection);
                t.setText("Upcoming");

                popupWindow1=new PopupWindow(collectionv, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                popupWindow1.setEnterTransition(new Slide());
                popupWindow1.showAtLocation(collectionv, Gravity.BOTTOM,0,0);
                popupWindow1.setExitTransition(new Slide());
                int id=R.id.collectionRVIEW;
                loadData("Upcoming",id);
            }
        });


        ImageButton search=findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                v=inflater.inflate(R.layout.search,null);
                RelativeLayout root=findViewById(R.id.main);
                root.addView(v,RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                v.setVisibility(View.VISIBLE);
                searchflag=1;

                searchView=v.findViewById(R.id.searchbar);
                searchView.clearFocus(); //new_change
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String localquery) {
                        query=localquery;
                        loadData("Search",R.id.searchRVIEW);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String localquery) {
                        query=localquery;
                        loadData("Search",R.id.searchRVIEW);
                        return false;
                    }
                });

            }
        });

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context=MainActivity.this;
                LayoutInflater layoutInflater=LayoutInflater.from(context);

                collectionv=layoutInflater.inflate(R.layout.collections,null);
                TextView t=collectionv.findViewById(R.id.collection);
                t.setText("Watchlist");

                popupWindow1=new PopupWindow(collectionv, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                popupWindow1.setEnterTransition(new Slide());
                popupWindow1.showAtLocation(collectionv, Gravity.BOTTOM,0,0);
                popupWindow1.setExitTransition(new Slide());
                int id=R.id.collectionRVIEW;
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<DataClass> localData = dao_object.getAll();
                        ArrayList<DataClass> arrayList = new ArrayList<>(localData);
                        //loadLocalData();



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Adapter smalladapter = new Adapter(MainActivity.this,arrayList,R.layout.itemposter_small);
                                RecyclerView recyclerView = collectionv.findViewById(id);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                recyclerView.setAdapter(smalladapter);

                            }
                        });
                    }
                });


            }
        });

        TabLayout mainTab=findViewById(R.id.tabLayout);
        mainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.md_theme_light_primary);
                if (tab.getIcon() != null) {
                    tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.md_theme_light_onPrimary);
                if (tab.getIcon() != null) {
                    tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Perform any action when the tab is re-selected
            }
        });

        TabLayout.Tab homeTab = tabLayout.getTabAt(0);
        TabLayout.Tab watchlistTab = tabLayout.getTabAt(1);

        int initcolor = ContextCompat.getColor(MainActivity.this, R.color.md_theme_light_primary);
        if (homeTab.getIcon() != null) {
            homeTab.getIcon().setColorFilter(initcolor, PorterDuff.Mode.SRC_IN);
        }
        int initcolor1 = ContextCompat.getColor(MainActivity.this, R.color.md_theme_light_onPrimary);
        if (watchlistTab.getIcon() != null) {
            watchlistTab.getIcon().setColorFilter(initcolor1, PorterDuff.Mode.SRC_IN);
        }



        // Setting onclick for tab items
        homeTab.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Clicking on the first tab takes you back to the main activity
                if(watchlistflag==1)
                {
                    watchlistflag=0;
                    RelativeLayout relativeLayout=findViewById(R.id.watchlistview);
                    relativeLayout.setVisibility(View.GONE);
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (vb.hasVibrator()) {

                        VibrationEffect vibeEffect = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            vibeEffect = VibrationEffect.createOneShot(30, 50);
                            vb.vibrate(vibeEffect);
                        }

                    }


                }

            }
        });

        watchlistTab.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout relativeLayout=findViewById(R.id.watchlistview);
                relativeLayout.setVisibility(View.VISIBLE);
                watchlistflag=1;
                int id=R.id.watchlistRVIEW;
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vb.hasVibrator()) {

                    VibrationEffect vibeEffect = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        vibeEffect = VibrationEffect.createOneShot(30, 50);
                        vb.vibrate(vibeEffect);
                    }

                }
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<DataClass> localData = dao_object.getAll();
                        ArrayList<DataClass> arrayList = new ArrayList<>(localData);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Adapter smalladapter = new Adapter(MainActivity.this, arrayList, R.layout.itemposter_small);
                                RecyclerView recyclerView = relativeLayout.findViewById(id);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                recyclerView.setAdapter(smalladapter);
                                smalladapter.setOnItemClickListener(new Adapter.onItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Context context=MainActivity.this;
                                        Log.d("SmallAdapter", "Item clicked at position: " + position);
                                        //Toast.makeText(MainActivity.this, "Item clicked at position: " + position, Toast.LENGTH_SHORT).show();
                                        LayoutInflater layoutInflater=LayoutInflater.from(context);
                                        if(searchflag==1)
                                        {
                                            if (searchView != null) {
                                                searchView.clearFocus();
                                            }
                                        }
                                        View view=layoutInflater.inflate(R.layout.moviedetails,null);

                                        ImageView hero =view.findViewById(R.id.heroimage);
                                        TextView title=view.findViewById(R.id.title);
                                        TextView score=view.findViewById(R.id.score);
                                        TextView description=view.findViewById(R.id.description);
                                        String herourl=arrayList.get(position).getHerourl();

                                        MaterialButton button=view.findViewById(R.id.watchlist);
                                        //button.setEnabled(false);
                                        //button.setVisibility(View.INVISIBLE);
                                        //MaterialButton button=view.findViewById(R.id.watchlist);
                                        //DataClass data = arrayList.get(position);
                                        //Log.e("st1",arrayList.get(position).getUrl());
                                        if(herourl!=null&&hero!=null) {

                                            Glide.with(view)
                                                    .load(herourl)
                                                    .centerCrop()
                                                    .into(hero)
                                            ;
                                            title.setText(arrayList.get(position).getTitle());
                                            score.setText(arrayList.get(position).getScore());
                                            description.setText(arrayList.get(position).getDescription());
                                            DataClass currentData = arrayList.get(position);

                                            if(currentData.added==true)
                                            {
                                                button.setText("Added to Watchlist");
                                                int color = getResources().getColor(R.color.md_theme_light_background);
                                                int textcolor = getResources().getColor(R.color.md_theme_dark_errorContainer);
                                                button.setBackgroundTintList(ColorStateList.valueOf(color));
                                                button.setTextColor(textcolor);
                                                button.setIconResource(R.drawable.baseline_check_24);
                                                button.setIconTint(ColorStateList.valueOf(textcolor));
                                            }
                                            else{
                                                button.setText("Add to Watchlist");
                                                int color = getResources().getColor(R.color.md_theme_dark_errorContainer);
                                                int textcolor = getResources().getColor(R.color.md_theme_light_background);
                                                button.setBackgroundTintList(ColorStateList.valueOf(color));
                                                button.setTextColor(textcolor);
                                                button.setIconResource(R.drawable.baseline_star_24);
                                                button.setIconTint(ColorStateList.valueOf(textcolor));
                                            }
                                            button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    if(currentData.added==true)
                                                    {
                                                        ExecutorService service=Executors.newSingleThreadExecutor();
                                                        service.execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                dao_object.deleteDataById(currentData.key);
                                                                notify=true;

                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        currentData.added=false;
                                                                        button.setText("Add to Watchlist");
                                                                        int color = getResources().getColor(R.color.md_theme_dark_errorContainer);
                                                                        int textcolor = getResources().getColor(R.color.md_theme_light_background);
                                                                        button.setBackgroundTintList(ColorStateList.valueOf(color));
                                                                        button.setTextColor(textcolor);
                                                                        button.setIconResource(R.drawable.baseline_star_24);
                                                                        button.setIconTint(ColorStateList.valueOf(textcolor));
                                                                        loadData("Trending",R.id.trendingRVIEW);
                                                                        loadData("NetflixOriginals",R.id.netflixoriginalsRVIEW);
                                                                        loadData("TopRated",R.id.topratedRVIEW);
                                                                        loadData("ActionMovies",R.id.actionmoviesRVIEW);
                                                                        loadData("ComedyMovies",R.id.comedymoviesRVIEW);
                                                                        loadData("HorrorMovies",R.id.horrormoviesRVIEW);
                                                                        loadData("RomanceMovies",R.id.romancemoviesRVIEW);
                                                                        notify=false;

                                                                    }
                                                                });
                                                            }
                                                        });

                                                    }
                                                    else {
                                                        ExecutorService service=Executors.newSingleThreadExecutor();
                                                        service.execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                currentData.added = true;
                                                                dao_object.insertOrUpdate(currentData);
                                                                notify=true;
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        button.setText("Added to Watchlist");
                                                                        int color = getResources().getColor(R.color.md_theme_light_background);
                                                                        int textcolor = getResources().getColor(R.color.md_theme_dark_errorContainer);
                                                                        button.setBackgroundTintList(ColorStateList.valueOf(color));
                                                                        button.setTextColor(textcolor);
                                                                        button.setIconResource(R.drawable.baseline_check_24);
                                                                        button.setIconTint(ColorStateList.valueOf(textcolor));
                                                                        loadData("Trending",R.id.trendingRVIEW);
                                                                        loadData("NetflixOriginals",R.id.netflixoriginalsRVIEW);
                                                                        loadData("TopRated",R.id.topratedRVIEW);
                                                                        loadData("ActionMovies",R.id.actionmoviesRVIEW);
                                                                        loadData("ComedyMovies",R.id.comedymoviesRVIEW);
                                                                        loadData("HorrorMovies",R.id.horrormoviesRVIEW);
                                                                        loadData("RomanceMovies",R.id.romancemoviesRVIEW);
                                                                        notify=false;

                                                                    }
                                                                });
                                                            }
                                                        });

                                                    }



                                                }
                                            });


                                        }
                                        else
                                        {
                                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                                        }

                                        //title.setText(arrayList.get(position));

                                        popupWindow=new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                                        popupWindow.setEnterTransition(new Slide());
                                        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                                        popupWindow.setExitTransition(new Slide());

                                    }
                                });


                                smalladapter.notifyDataSetChanged();





                            }
                        });
                    }
                });

            }
        });
    }










    private void loadLocalData() {
        ArrayList<DataClass> arrayList=new ArrayList<>();
        List<DataClass> localData = dao_object.getAll();
        arrayList.addAll(localData);

    }

    private void loadData(String collection,int rviewid) {

// Instantiate the RequestQueue :.
        RequestQueue queue = Volley.newRequestQueue(this);
        String API_KEY= BuildConfig.API_KEY;
        ArrayList<DataClass> arrayList=new ArrayList<>();
        Adapter adapter = new Adapter(MainActivity.this,arrayList,R.layout.itemposter);
        Adapter smalladapter = new Adapter(MainActivity.this,arrayList,R.layout.itemposter_small);
        RecyclerView recyclerView;

        GetURL getURL=new GetURL();

        String url = getURL.fetch(collection);
        if(collection.equalsIgnoreCase("Search"))
        {
            //TextInputEditText query=v.findViewById(R.id.searchboxtext);
            String searchval=query.toString().trim();
            String[] words=searchval.split("\\s+");
            String joined= TextUtils.join("+",words);
            String newurl=url.replace("+insertquery+",joined);
            Log.e("srch",newurl);
            url=newurl;
            Log.e("srch",url);




            recyclerView=findViewById(rviewid);

            GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivity.this,3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(dy>0)
                    {
                        searchView.clearFocus();
                    }
                }
            });
            recyclerView.setAdapter(smalladapter);



        }



        else if (collection.equals("Movie")||collection.equals("TV")||collection.equals("Upcoming")) {
            recyclerView=collectionv.findViewById(rviewid);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivity.this,3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(smalladapter);


        }
        else {
            recyclerView=findViewById(rviewid);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }

        //recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new Adapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Context context=MainActivity.this;
                LayoutInflater layoutInflater=LayoutInflater.from(context);
                if(searchflag==1)
                {
                    if (searchView != null) {
                        searchView.clearFocus();
                    }

                }
                View view=layoutInflater.inflate(R.layout.moviedetails,null);

                ImageView hero =view.findViewById(R.id.heroimage);
                TextView title=view.findViewById(R.id.title);
                TextView score=view.findViewById(R.id.score);
                TextView description=view.findViewById(R.id.description);
                String herourl=arrayList.get(position).getHerourl();
                MaterialButton button=view.findViewById(R.id.watchlist);
                //MaterialButton button=view.findViewById(R.id.watchlist);
                //DataClass data = arrayList.get(position);
                //Log.e("st1",arrayList.get(position).getUrl());
                if(herourl!=null&&hero!=null) {

                    Glide.with(view)
                            .load(herourl)
                            .centerCrop()
                            .into(hero)
                    ;
                    title.setText(arrayList.get(position).getTitle());

                    Button info= view.findViewById(R.id.info);
                    info.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String movieTitle = arrayList.get(position).getTitle();
                            String searchUrl = "https://www.google.com/search?q=" + movieTitle;
                            Uri uri = Uri.parse(searchUrl);
                            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        }
                    });

                    Button watch= view.findViewById(R.id.watch);
                    watch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String movieTitle1 = arrayList.get(position).getTitle();
                            String searchUrl1 = "https://www.justwatch.com/in/search?q=" + movieTitle1;
                            Uri uri1 = Uri.parse(searchUrl1);
                            context.startActivity(new Intent(Intent.ACTION_VIEW, uri1));
                        }
                    });


                    score.setText(arrayList.get(position).getScore());
                    description.setText(arrayList.get(position).getDescription());
                    DataClass currentData = arrayList.get(position);
                    if(currentData.added==true)
                    {
                        button.setText("Added to Watchlist");
                        int color = getResources().getColor(R.color.md_theme_light_background);
                        int textcolor = getResources().getColor(R.color.md_theme_dark_errorContainer);
                        button.setBackgroundTintList(ColorStateList.valueOf(color));
                        button.setTextColor(textcolor);
                        button.setIconResource(R.drawable.baseline_check_24);
                        button.setIconTint(ColorStateList.valueOf(textcolor));
                    }
                    else{
                        button.setText("Add to Watchlist");
                        int color = getResources().getColor(R.color.md_theme_dark_errorContainer);
                        int textcolor = getResources().getColor(R.color.md_theme_light_background);
                        button.setBackgroundTintList(ColorStateList.valueOf(color));
                        button.setTextColor(textcolor);
                        button.setIconResource(R.drawable.baseline_star_24);
                        button.setIconTint(ColorStateList.valueOf(textcolor));
                    }
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(currentData.added==true)
                            {
                                ExecutorService service=Executors.newSingleThreadExecutor();
                                service.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        dao_object.deleteDataById(currentData.key);
                                        //notify=true;

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                currentData.added=false;
                                                button.setText("Add to Watchlist");
                                                int color = getResources().getColor(R.color.md_theme_dark_errorContainer);
                                                int textcolor = getResources().getColor(R.color.md_theme_light_background);
                                                button.setBackgroundTintList(ColorStateList.valueOf(color));
                                                button.setTextColor(textcolor);
                                                button.setIconResource(R.drawable.baseline_star_24);
                                                button.setIconTint(ColorStateList.valueOf(textcolor));
                                                loadData("Trending",R.id.trendingRVIEW);
                                                loadData("NetflixOriginals",R.id.netflixoriginalsRVIEW);
                                                loadData("TopRated",R.id.topratedRVIEW);
                                                loadData("ActionMovies",R.id.actionmoviesRVIEW);
                                                loadData("ComedyMovies",R.id.comedymoviesRVIEW);
                                                loadData("HorrorMovies",R.id.horrormoviesRVIEW);
                                                loadData("RomanceMovies",R.id.romancemoviesRVIEW);
                                                notify=false;

                                            }
                                        });
                                    }
                                });

                            }
                            else {
                                ExecutorService service=Executors.newSingleThreadExecutor();
                                service.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        currentData.added = true;
                                        dao_object.insertOrUpdate(currentData);
                                        //notify=true;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                button.setText("Added to Watchlist");
                                                int color = getResources().getColor(R.color.md_theme_light_background);
                                                int textcolor = getResources().getColor(R.color.md_theme_dark_errorContainer);
                                                button.setBackgroundTintList(ColorStateList.valueOf(color));
                                                button.setTextColor(textcolor);
                                                button.setIconResource(R.drawable.baseline_check_24);
                                                button.setIconTint(ColorStateList.valueOf(textcolor));
                                                loadData("Trending",R.id.trendingRVIEW);
                                                loadData("NetflixOriginals",R.id.netflixoriginalsRVIEW);
                                                loadData("TopRated",R.id.topratedRVIEW);
                                                loadData("ActionMovies",R.id.actionmoviesRVIEW);
                                                loadData("ComedyMovies",R.id.comedymoviesRVIEW);
                                                loadData("HorrorMovies",R.id.horrormoviesRVIEW);
                                                loadData("RomanceMovies",R.id.romancemoviesRVIEW);
                                                notify=false;

                                            }
                                        });
                                    }
                                });

                            }



                        }
                    });

                }
                else
                {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                }

                //title.setText(arrayList.get(position));

                popupWindow=new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                popupWindow.setEnterTransition(new Slide());
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                popupWindow.setExitTransition(new Slide());

            }
        });

        smalladapter.setOnItemClickListener(new Adapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Context context=MainActivity.this;
                LayoutInflater layoutInflater=LayoutInflater.from(context);
                if(searchflag==1)
                {
                    if (searchView != null) {
                        searchView.clearFocus();
                    }

                }
                View view=layoutInflater.inflate(R.layout.moviedetails,null);

                ImageView hero =view.findViewById(R.id.heroimage);
                TextView title=view.findViewById(R.id.title);
                TextView score=view.findViewById(R.id.score);
                TextView description=view.findViewById(R.id.description);
                String herourl=arrayList.get(position).getHerourl();
                MaterialButton button=view.findViewById(R.id.watchlist);
                //MaterialButton button=view.findViewById(R.id.watchlist);
                //DataClass data = arrayList.get(position);
                //Log.e("st1",arrayList.get(position).getUrl());
                if(herourl!=null&&hero!=null) {

                    Glide.with(view)
                            .load(herourl)
                            .centerCrop()
                            .into(hero)
                    ;
                    title.setText(arrayList.get(position).getTitle());
                    score.setText(arrayList.get(position).getScore());
                    description.setText(arrayList.get(position).getDescription());
                    DataClass currentData = arrayList.get(position);
                    if(currentData.added==true)
                    {
                        button.setText("Added to Watchlist");
                        int color = getResources().getColor(R.color.md_theme_light_background);
                        int textcolor = getResources().getColor(R.color.md_theme_dark_errorContainer);
                        button.setBackgroundTintList(ColorStateList.valueOf(color));
                        button.setTextColor(textcolor);
                        button.setIconResource(R.drawable.baseline_check_24);
                        button.setIconTint(ColorStateList.valueOf(textcolor));
                    }
                    else{
                        button.setText("Add to Watchlist");
                        int color = getResources().getColor(R.color.md_theme_dark_errorContainer);
                        int textcolor = getResources().getColor(R.color.md_theme_light_background);
                        button.setBackgroundTintList(ColorStateList.valueOf(color));
                        button.setTextColor(textcolor);
                        button.setIconResource(R.drawable.baseline_star_24);
                        button.setIconTint(ColorStateList.valueOf(textcolor));
                    }
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(currentData.added==true)
                            {
                                ExecutorService service=Executors.newSingleThreadExecutor();
                                service.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        dao_object.deleteDataById(currentData.key);
                                        notify=true;

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                currentData.added=false;
                                                button.setText("Add to Watchlist");
                                                int color = getResources().getColor(R.color.md_theme_dark_errorContainer);
                                                int textcolor = getResources().getColor(R.color.md_theme_light_background);
                                                button.setBackgroundTintList(ColorStateList.valueOf(color));
                                                button.setTextColor(textcolor);
                                                button.setIconResource(R.drawable.baseline_star_24);
                                                button.setIconTint(ColorStateList.valueOf(textcolor));
                                                loadData("Trending",R.id.trendingRVIEW);
                                                loadData("NetflixOriginals",R.id.netflixoriginalsRVIEW);
                                                loadData("TopRated",R.id.topratedRVIEW);
                                                loadData("ActionMovies",R.id.actionmoviesRVIEW);
                                                loadData("ComedyMovies",R.id.comedymoviesRVIEW);
                                                loadData("HorrorMovies",R.id.horrormoviesRVIEW);
                                                loadData("RomanceMovies",R.id.romancemoviesRVIEW);
                                                notify=false;

                                            }
                                        });
                                    }
                                });

                            }
                            else {
                                ExecutorService service=Executors.newSingleThreadExecutor();
                                service.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        currentData.added = true;
                                        dao_object.insertOrUpdate(currentData);
                                        notify=true;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                button.setText("Added to Watchlist");
                                                int color = getResources().getColor(R.color.md_theme_light_background);
                                                int textcolor = getResources().getColor(R.color.md_theme_dark_errorContainer);
                                                button.setBackgroundTintList(ColorStateList.valueOf(color));
                                                button.setTextColor(textcolor);
                                                button.setIconResource(R.drawable.baseline_check_24);
                                                button.setIconTint(ColorStateList.valueOf(textcolor));
                                                loadData("Trending",R.id.trendingRVIEW);
                                                loadData("NetflixOriginals",R.id.netflixoriginalsRVIEW);
                                                loadData("TopRated",R.id.topratedRVIEW);
                                                loadData("ActionMovies",R.id.actionmoviesRVIEW);
                                                loadData("ComedyMovies",R.id.comedymoviesRVIEW);
                                                loadData("HorrorMovies",R.id.horrormoviesRVIEW);
                                                loadData("RomanceMovies",R.id.romancemoviesRVIEW);
                                                notify=false;

                                            }
                                        });
                                    }
                                });

                            }



                        }
                    });

                }
                else
                {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                }

                //title.setText(arrayList.get(position));

                popupWindow=new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                popupWindow.setEnterTransition(new Slide());
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                popupWindow.setExitTransition(new Slide());

            }
        });










// Request a string response from the provided URL.

        Log.e("api1",url);
        String baseimageurl="https://image.tmdb.org/t/p/original";

        Log.e("stringurl",url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("api1",response.toString());


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("results");
                            for (int i = 0; i <jsonObject.getJSONArray("results").length(); i++) {

                                JSONObject movies=jsonArray.getJSONObject(i);
                                int key= movies.getInt("id");

                                String imgurl=baseimageurl+movies.getString("poster_path");
                                String herourl=baseimageurl+movies.getString("backdrop_path");
                                //Log.e("api2",imgurl);
                                String title="";
                                String score=movies.getString("vote_average");
                                if(movies.has("title"))
                                {
                                    title=movies.getString("title");
                                } else if (movies.has("name")) {
                                    title=movies.getString("name");
                                } else if (movies.has("original_title")) {
                                    title=movies.getString("original_title");
                                }
                                String description=movies.getString("overview");

                                DataClass data=new DataClass(key,imgurl,herourl,title,score,description,false);
                                ExecutorService service = Executors.newSingleThreadExecutor();
                                service.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        DataClass duplicate = dao_object.checkduplicateById(data.key);
                                        if(duplicate!=null)
                                        {
                                            data.setAdded(true);
                                            //Log.e("test",data.title+data.added);
                                        }

                                    }
                                });



                                arrayList.add(data);
                                adapter.notifyDataSetChanged();
                                smalladapter.notifyDataSetChanged();//took me 3hrs to find this error broo!!





                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                        //Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);



    }

    @Override
    public void onBackPressed() {
        RelativeLayout newsLayout = findViewById(R.id.newsLayout);
        RelativeLayout homeLayout = findViewById(R.id.main);
        RelativeLayout watchlistLayout = findViewById(R.id.watchlistview);

        if (newsLayout.getVisibility() == View.VISIBLE) {
            // Hide the news layout and show the home layout
            newsLayout.setVisibility(View.GONE);
            homeLayout.setVisibility(View.VISIBLE);
            ScrollView scrollView = findViewById(R.id.scroll);
            scrollView.setVisibility(View.VISIBLE); // Add this line
        }         // Handle other UI components


        else if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else if (searchflag == 1 && searchView.hasFocus()) {
            searchView.clearFocus();
        } else if (searchflag == 1) {
            ((ViewGroup)v.getParent()).removeView(v);
            searchflag = 0;
        } else if (watchlistflag == 1) {
            watchlistflag = 0;
            RelativeLayout relativeLayout = findViewById(R.id.watchlistview);
            relativeLayout.setVisibility(View.INVISIBLE);
            TabLayout.Tab home = tabLayout.getTabAt(0);
            if (home != null) {
                home.select();
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.md_theme_light_primary);
                if (home.getIcon() != null) {
                    home.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                }
            }
        } else if (popupWindow1 != null && popupWindow1.isShowing()) {
            popupWindow1.dismiss();
        } else {
            // Default case of back
            super.onBackPressed();
        }
    }

    @NonNull
    private BlurAlgorithm getBlurAlgorithm() {
        BlurAlgorithm algorithm;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            algorithm = new RenderEffectBlur();
        } else {
            algorithm = new RenderScriptBlur(this);
        }
        return algorithm;
    }


    private void initView() {
        //viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        bottomBlurView = findViewById(R.id.bottomBlurView);




        blurroot = findViewById(R.id.main);
    }


    //for blur view
    private void setupBlurView() {
        final float radius = 25f;
        final float minBlurRadius = 4f;
        final float step = 4f;

        //set background, if your root layout doesn't have one
        final Drawable windowBackground = getWindow().getDecorView().getBackground();
        BlurAlgorithm algorithm = getBlurAlgorithm();


        bottomBlurView.setupWith(blurroot, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);


        int initialProgress = (int) (radius * step);

    }

    public void hideKeyboardAndClearFocus() {
        searchView.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }




}


