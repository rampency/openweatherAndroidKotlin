# OpenWeather Forecasting App


### Description
- Implemented a simple Android App that shows the current weather in New York City and after a click on the Weather Forecast button, a new detailed screen displays detailed information such as  date, tempeature for that date, and high and low tempeature. 
- The app will have a presentation layer that contains an activity and fragment and a ViewModel. I  used Coroutines for retrofit API call, storing the result in LiveData observables and Data Binding to the presentation layer.  I am also using a recyclerview to display the Forecast data for the week.

### Future Work
- Get the current location and ask for the right permissions instead of hard coding NYC coordinates
- implement better UI with loading image icon of current weather via Picasso 
- add no network error cases in the event of no network
- persist data locally in a room database in case of no network, currently all we do is a Get request


### Screenshots

![Alt text](/screenshots/main.jpg?raw=true "Main Screen")
![Alt text](/screenshots/forecast.jpg?raw=true "Forecast screen")
