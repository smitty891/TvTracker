# it4045c-project  
# Introduction  

(Application name) allows users to build a list of movies/series they want to watch.

they can search for movies by titles and save it to a view list. Users are able to see 

in which streaming platformes such as Netflix, Hulu, IMDB, Amazon Prime Videos, 

Youtube, etc the movie is available.

Users can have a record of the movies/series they watched by marking those  

"already watched". They have the ability to add comments as well.

## Class Diagram

![TvTrackerDiagram](https://github.com/smitty891/it4045c-project/blob/master/TvTrackerUML.png?raw=true)

### Class Diagram Description
TO-DO: add short class descriptions

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