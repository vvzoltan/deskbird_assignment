# Notes

- Tech stack: Kotlin, Hilt, MVVM, Retrofit, Moshi Jetpack libraries(Compose, Navigation, Paging, DataStore), Flow/Coroutines
- I tried to used the latest technologies available to showcase my skills, not because I think were best suited for this task. For example I used Material 3 even though a lot of its features are still marked as experimental.
- I tried using paging on the favourites screen as well, but the API doesn't seem to work well with both the `page` and the `by_ids` filters (some breweries were sent in multiple pages that caused inconsistencies in the application so I have decided against paging in the filters screen)
- I am combining the main network call and another for the paging data in order to be able to use the placeholder feature of the paging library. In a production scenario I would either drop the placeholder feature or request that the API returns the needed data within one response.
- On the details page I have chosen to only dial the number instead of calling because I was short on time and this way I did not need to handle permissions as well.
- The favourite button on the details screen is below the rest of the buttons instead of the top right because I feel like the UX is much more consistent this way, however I did prepare the system bar state in a way that would support adding action buttons in the top bar as well.
- and lastly I am not a skilled designer, please keep that in mind when judging the UX :)

Thank you for your time! 
