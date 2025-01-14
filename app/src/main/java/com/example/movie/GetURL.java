package com.example.movie;

public class GetURL {
    String component;
    String PLACEHOLDER_API_KEY=BuildConfig.API_KEY;

    public String fetch(String component) {
        this.component = component;
        String newurl="";
        switch (component)
        {
            case "Trending":{
                newurl= "https://api.themoviedb.org/3/trending/all/week?api_key=" + PLACEHOLDER_API_KEY + "&language=en-US";
                break;
            }
            case "NetflixOriginals":{
                newurl= "https://api.themoviedb.org/3/discover/tv?api_key=" + PLACEHOLDER_API_KEY + "&with_networks=213";
                break;
            }
            case "TopRated":{
                newurl=  "https://api.themoviedb.org/3/movie/top_rated?api_key=" + PLACEHOLDER_API_KEY + "&language=en-US";
                break;
            }
            case "ActionMovies":{
                newurl=  "https://api.themoviedb.org/3/discover/movie?api_key=" + PLACEHOLDER_API_KEY + "&with_genres=28";
                break;
            }
            case "ComedyMovies":{
                newurl= "https://api.themoviedb.org/3/discover/movie?api_key=" + PLACEHOLDER_API_KEY + "&with_genres=35";
                break;
            }
            case "HorrorMovies":{
                newurl=  "https://api.themoviedb.org/3/discover/movie?api_key=" + PLACEHOLDER_API_KEY + "&with_genres=27";
                break;
            }
            case "RomanceMovies":{
                newurl=  "https://api.themoviedb.org/3/discover/movie?api_key=" + PLACEHOLDER_API_KEY + "&with_genres=10749";
                break;
            }
            case "Search":{
                newurl="https://api.themoviedb.org/3/search/movie?query=+insertquery+&api_key="+PLACEHOLDER_API_KEY;
                break;
            }
            case "Movie":
            {
                newurl="https://api.themoviedb.org/3/discover/movie?api_key="+PLACEHOLDER_API_KEY;
                break;
            }
            case "TV":
            {
                newurl="https://api.themoviedb.org/3/discover/tv?api_key="+PLACEHOLDER_API_KEY;
                break;
            }
            case "Upcoming":
            {
                newurl="https://api.themoviedb.org/3/movie/upcoming?api_key="+PLACEHOLDER_API_KEY;
                break;
            }

        }

        return newurl;
    }
}
