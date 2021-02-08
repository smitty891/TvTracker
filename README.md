# IT4045c-project (TvTracker)  
## Introduction  

TvTracker allows users to build a list of movies/series they want to watch.

they can search for movies by titles and save it to a view list. Users are able to see 

in which streaming platformes such as Netflix, Hulu, IMDB, Amazon Prime Videos, 

Youtube, etc the movie is available.

Users can have a record of the movies/series they watched by marking those  

"already watched". They have the ability to add comments as well.

## Storyboard
### Home/Browse page
![homebrowse](https://user-images.githubusercontent.com/59851587/107153567-36023280-693c-11eb-8ffe-2ddce05dfc25.PNG)
### Favorites
![Favorites](https://user-images.githubusercontent.com/59851587/107153591-58944b80-693c-11eb-8148-779743f70550.jpg)
### Login
![login](https://user-images.githubusercontent.com/59851587/107153614-79f53780-693c-11eb-977a-89bf3adbb9fc.PNG)
### Signup
![signup](https://user-images.githubusercontent.com/59851587/107153628-972a0600-693c-11eb-80cb-893c4b5f92b9.PNG)

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

## Class Diagram

![TvTrackerDiagram](https://github.com/smitty891/it4045c-project/blob/master/TvTrackerUML.png?raw=true)

### Class Diagram Description
**TvTrackerController** - contains all REST endpoints necessary for our user interface.

**IUserAccountService** - interface declaring all necessary methods for UserAccount related functionality.

**UserAccountService** - contains implementation for all the methods in IUserAccountService.

**UserAccountServiceStub** - implements IUserAccountService's methods with hardcoded return values for initial ui development.

**IMediaEntryService** - interface declaring all necessary methods for MediaEntry related functionality.

**MediaEntryService** - contains implementation for all the methods in IMediaEntryService.

**MediaEntryServiceStub** - implements IMediaEntryService's methods with hardcoded return values for initial ui development.

**UserAccount** - carries UserAccount data between processes.

**MediaEntry** -  carries MediaEntry data between processes.

**IUserAccountDAO** - interface declaring the methods needed for UserAccount's data access object.

**UserAccountDAO** - implements IUserAccountDAO allowing access to UserAccount records in our underlying database.

**IMediaEntryDAO** - interface declaring the methods needed for MediaEntry's data access object.

**MediaEntryDAO** - implements IMediaEntryDAO allowing access to MediaEntry records in our underlying database.


## JSON Schema

This is what we plan to export to another app.

>{
>  "type" : "object",
>  "properties" : {
>    "description" : {
>      "type" : "string"
>    },
>    "type" : {
>      "type" : "string"
>    },
>    "itemId" : {
>      "type" : "integer"
>    },
>    "platform" : {
>      "type" : "string"
>    },
>    "title" : {
>      "type" : "string"
>    },
>    "username" : {
>      "type" : "string"
>    },
>    "imageUrl" : {
>      "type" : "string"
>    },
>    "watched" : {
>      "type" : "boolean"
>    }
>  }
>}

## Team Members and Roles

UI Specialists:
Chris Meyer,
Jeremy Mazurowski

Business Logic/Persistence:
Cheghali Elkhalili,
Tate Weber

Product Owner/GitHub/Scrum:
Ryan Smith

## Milestones

[Milestones](https://github.com/users/smitty891/projects/1)

## Weekly Stand-up

[Sundays @ 8:00PM](https://teams.microsoft.com/l/meetup-join/19%3ameeting_OGY0MjQ3NjYtNTY3Ni00ZmQzLWJmNmEtNDRhMmRiZGYxOGEy%40thread.v2/0?context=%7b%22Tid%22%3a%22f5222e6c-5fc6-48eb-8f03-73db18203b63%22%2c%22Oid%22%3a%22cde19e27-29a9-4f05-b2cb-65028bb3508e%22%7d)
