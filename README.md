# it4045c-project  
## Introduction  

TvTracker allows users to build a list of movies/series they want to watch.

they can search for movies by titles and save it to a view list. Users are able to see 

in which streaming platformes such as Netflix, Hulu, IMDB, Amazon Prime Videos, 

Youtube, etc the movie is available.

Users can have a record of the movies/series they watched by marking those  

"already watched". They have the ability to add comments as well.

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