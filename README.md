## GitHub Explorer

GitHub Explorer is an Android application built using Kotlin that allows users to search for GitHub profiles and view details such as followers, following, and repositories. The app uses the GitHub API to fetch data in real time.

Features

Search Users: Quickly find GitHub users by their username.

View Profile Details: Display user details like name, bio, and location.

Followers and Following: See a list of followers and users the profile is following.

Repositories: Browse through the user's public repositories.

Material Design: Intuitive and user-friendly interface.

Screenshots

Include screenshots of your app's UI here. This helps users understand the app's functionality and design.

Installation

Prerequisites

Android Studio (Arctic Fox or later)

Kotlin version 1.5 or above

GitHub Personal Access Token (optional, for authenticated requests)

## Steps

Clone the repository:

git clone https://github.com/your-username/github-explorer.git

Open the project in Android Studio.

Build the project to install dependencies.

Run the app on an emulator or a physical device.

## Usage

Launch the app.

Enter the username of a GitHub profile in the search bar.

Tap on a profile to view details such as followers, following, and repositories.

Explore repositories to see descriptions, stars, and other details.

Technologies Used

Kotlin: For app development.

Retrofit: For API requests.

Coroutines: For asynchronous operations.

ViewModel and LiveData: For managing UI-related data.

Material Design Components: For an engaging UI/UX.

Picasso/Glide: For image loading (e.g., profile pictures).

## API Reference

This app uses the GitHub REST API.

Example Endpoints:

Search Users: https://api.github.com/search/users?q={username}

User Details: https://api.github.com/users/{username}

User Repos: https://api.github.com/users/{username}/repos

Followers: https://api.github.com/users/{username}/followers
