# MovieDatabase App

Welcome to the MovieDatabase App, your one-stop destination for exploring movies, TV shows, upcoming releases, and more! This app is designed to enhance your entertainment experience by providing seamless navigation, real-time news, and advanced search options. Here's everything you need to know about its features:

# Features

 Watch Button
Purpose: Makes it easier to find where you can stream or watch a particular movie.
How it works:
Tap the Watch button to trigger a search for the selected movie.
The app retrieves the movie title and creates a search URL.
It opens the JustWatch website in your browser to show available streaming options.

 Movies, TV Shows, and Upcoming
Purpose: Quickly explore different categories of content.
How it works:
At the top of the home screen, you'll find three Material Buttons: Movies, TV Shows, and Upcoming.
Tapping a button inflates a new layout that slides up smoothly from the bottom of the screen.
Content is displayed dynamically in a grid format with three columns.
The app fetches relevant data for the selected category and displays it inside a RecyclerView.

 News Section
Purpose: Stay updated with the latest movie news.
How it works:
Tap the News button to fetch live news articles using the NewsAPI.
The layout for news appears dynamically and displays articles in a scrollable view.
Articles include headlines and descriptions, helping you stay informed about the latest trends in the movie world.
The NewsFetcher class handles API calls and updates the layout with fetched data.

 Search Functionality
Purpose: Quickly find any movie, series, or more.
How it works:
Tap the Search button to open a search layout.
The search layout includes a SearchView for typing queries and a RecyclerView for displaying results.
As you type, the app sends queries to the TMDB API and updates the results dynamically.
Results are displayed in a grid format with CardViews, providing a polished look.

 Watchlist Management
Purpose: Easily manage your favorite movies and shows.
How it works:
Add items to your watchlist and view them anytime.
To remove an item, tap the Added to Watchlist button. The app will:
Run a background process to remove the item from the local database.
Instantly update the UI, changing the button back to Add to Watchlist.
Refresh the watchlist to verify changes.

 Additional Details
Smooth Transitions: Popup windows and layouts slide in and out for a user-friendly experience.
Stylish Design: Includes a stylish app logo and visually appealing layouts.
Responsive UI: Dynamic updates ensure smooth navigation and real-time content refresh.


# Thank you for checking out MovieDatabase App! 
