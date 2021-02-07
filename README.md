


# Requirements  

  1_As a user, I want to be able to build a list of movies/series I want to watch.

## Example

Given: A feed of movies data are available

When: The user searches for a movie Cast Away

When: The user selects movie Cast Away

Then: The user’s movie will be saved in a view list.

## Example

Given: Movies data are available

When: The user/service searches for “gggrrrr,-wwgshdjjll”

Then: TvTracker will not return any results, and the user will not be able to save the 

movie.

  2_As a user, I want to be able to see in which streaming platform my movie is 

available.

## Example

Given: The user is logged in and has selected a previously-saved Cast Away movie

When: The user enters the streaming platform to watch the movie/serie

Then: The user can watch the movie. 

  3_As a user, I want to have a record of movies/series I watched.

## Example

Given: The user has a valid account and movies associated to that account.

When: The user marks the movies he/she watched by checking the box "already 

watched"

Then: The user will see the movies he/she already watched.

## Example

Given: The user has a valid account and movies associated to that account.

When: The user wants to delete Cast Away movie from the list

Then: By selecting Cast Away movie from the list and clicking the button DELETE, the user is able to remove this movie from the list.

4_As a user, I want to comment on movies I like it most.  

## Example

Given: The user watches a movie, he/she likes that movie 

When: The user is able to write a comment

Then: The user's feedback will be saved.
