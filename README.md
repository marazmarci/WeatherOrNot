# Architecture

The app follows the MVVM architecture pattern and is built with testability in mind.

These are the architectural layers of the app:

- View (Fragment)
- ViewModel
- Domain (Interactor)
- Repository
- Data sources
    - local, using Room
    - remote, using Retrofit

The data is delivered to the Fragment via LiveData instances. The architecture relies heavily on coroutine constructs including Flow and it's variants: StateFlow and SharedFlow. For dependency injection I used Hilt to minimalize boilerplate code.

# Libraries used

see [app/build.gradle.kts](https://github.com/marazmarci/WeatherOrNot/blob/main/app/build.gradle.kts)

# Cache implementation

> Maintain a cache of the results and reuse it when the app is opened. It should be invalid after 1 minute from the last update.

My goal was to provide the best user experience while following to these instructions. I interpreted "invalid" cache as a cache that needs an update, but it still stores the data. This means when you kill the app and reopen it more than 1 minute after the last update, it will perform an automatic refresh on startup. The user can initiate further updates by swiping down. I decided to use the swipe refresh indicator as an indicator for automatic updates too.

There's two levels of "cache" storage: Room persistence, and the state stored in a LiveData instance. The latter is retained in memory across configuration changes, so no IO operations are needed.

# Extra features

## Forecasts

The app stores all the weather data it receives from the webapi, including future predictions. When there's a refresh, the app selects the weather data according to the current date, and displays it with appending the suffix "(forecast)", indicating that it's not an up-to-date measurement.

## Continuous data age counter

The data age time indicator label increments every second. It shows hours, minutes and seconds. It can be helpful for testing the cache's correct behavior.

## Separate layout for landscape orientation