# Movies

Functional example RESTful API with two classes (Movie and Actor) in Java Spring Boot with JPA repositories.

Swagger available at `http://localhost:800/swagger-ui/index.html`

NB! Most requests produce similar results from the actor point of view (eg `GET v1/actors` instead of `GET v1/movies`, so they are omitted for simplicity and shortness.

### GET movies

localhost:800/v1/movies

```json
{
    "_embedded": {
        "movieList": [
            {
                "id": 1,
                "name": "Lord of the Rings",
                "actors": [],
                "_links": {
                    "self": {
                        "href": "http://localhost:800/v1/movies/1"
                    },
                    "movies": {
                        "href": "http://localhost:800/v1/movies"
                    },
                    "movieActors": {
                        "href": "http://localhost:800/v1/actors/1/movies"
                    }
                }
            },
            {
                "id": 2,
                "name": "Lord of the Kings",
                "actors": [],
                "_links": {
                    "self": {
                        "href": "http://localhost:800/v1/movies/2"
                    },
                    "movies": {
                        "href": "http://localhost:800/v1/movies"
                    },
                    "movieActors": {
                        "href": "http://localhost:800/v1/actors/2/movies"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:800/v1/movies"
        }
    }
}
```

### GET one actor
localhost:800/v1/actors/1

```json
{
    "id": 1,
    "name": "Asko Mägi",
    "movies": [],
    "_links": {
        "self": {
            "href": "http://localhost:800/v1/actors/1"
        },
        "actors": {
            "href": "http://localhost:800/v1/actors"
        },
        "actorMovies": {
            "href": "http://localhost:800/v1/movies/1/actors"
        }
    }
}
```

### POST movie
localhost:800/v1/movies

Body
```json
{
    "name": "Test"
}
```

Response
```json
{
    "id": 3,
    "name": "Test",
    "actors": [],
    "_links": {
        "self": {
            "href": "http://localhost:800/v1/movies/3"
        },
        "movies": {
            "href": "http://localhost:800/v1/movies"
        },
        "movieActors": {
            "href": "http://localhost:800/v1/actors/3/movies"
        }
    }
}
```

### PUT movie
localhost:800/v1/movies/1

#### NB!
Only supports adding existing actors! To add new actors use `POST v1/actors`!

Body
```json
{
    "name": "New Name Movie",
    "actors": [{"id": 1}, {"id": 2}]
}
```

Response
```json
{
    "id": 1,
    "name": "New Name Movie",
    "actors": [
        {
            "id": 1,
            "name": "Asko Mägi"
        },
        {
            "id": 2,
            "name": "Indrek Ott"
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:800/v1/movies/1"
        },
        "movies": {
            "href": "http://localhost:800/v1/movies"
        },
        "movieActors": {
            "href": "http://localhost:800/v1/actors/1/movies"
        }
    }
}
```

### DELETE
http://localhost:800/v1/movies/3

```
Movie deleted!
```
