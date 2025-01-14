package com.example.movie;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsFetcher {
    private Context context;
    private RelativeLayout newsLayout;
    private TextView newsTextView;
    private ArrayList<SpannableString> newsList;

    public NewsFetcher(Context context, RelativeLayout newsLayout, TextView newsTextView) {
        this.context = context;
        this.newsLayout = newsLayout;
        this.newsTextView = newsTextView;
        this.newsList = new ArrayList<>();
    }

    public void fetchNews() {
        String url = "https://newsapi.org/v2/everything?q=India Movie&apiKey=50debed980f64365bfcb8a9c644f0627";

        Log.d("NewsFetcher", "Fetching news from URL: " + url);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("NewsFetcher", "Response received: " + response.toString());
                        try {
                            JSONArray articles = response.getJSONArray("articles");
                            newsList.clear();
                            for (int i = 0; i < articles.length(); i++) {
                                JSONObject article = articles.getJSONObject(i);
                                String title = article.getString("title");
                                String description = article.optString("description", "No description available");

                                // Create a SpannableString with bold title and regular description
                                SpannableString styledText = new SpannableString(title + "\n" + description);
                                styledText.setSpan(
                                        new StyleSpan(Typeface.BOLD),
                                        0,
                                        title.length(),
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                );

                                newsList.add(styledText);
                            }
                            displayNews();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error parsing news", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            String data = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            Log.e("NewsFetcher", "Error fetching news. HTTP Status Code: " + statusCode);
                            Log.e("NewsFetcher", "Error Response Body: " + data);
                        } else {
                            Log.e("NewsFetcher", "No network response");
                        }
                        Toast.makeText(context, "Error fetching news", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
        Log.d("NewsFetcher", "Request added to RequestQueue");
    }

    private void displayNews() {
        newsLayout.setVisibility(View.VISIBLE);

        SpannableStringBuilder finalText = new SpannableStringBuilder();

        for (SpannableString item : newsList) {
            finalText.append(item).append("\n\n");
        }

        newsTextView.setText(finalText);
    }
}
