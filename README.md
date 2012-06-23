# Trapunto

Trapunto is a "live" Clojure/ClojureScript/Quil/Processing image generation client & server. Write code that resembles Clojure and Quil on the left, get a rendered image on the right in a decent amount of time. Something like that.

This project was built during [Hacker School](https://www.hackerschool.com/) and was inspired by [Chris Granger](http://www.chris-granger.com/2012/02/26/connecting-to-your-creation/) and [Bret Victor](http://vimeo.com/36579366).

Why Trapunto? It's a method of quilting, and thus acts as a metaphor for this type of image creation, as well as being a pun on the library Quil that is being used.

## Usage

After cloning the repository, run

1. `lein deps` to install dependencies
2. `lein cljsbuild once` to build the ClojureScript stuff
3. `lein ring server` to build and run the Clojure server

Check out [Quil](https://github.com/quil/quil) and [Processing](http://processing.org/) for functions to use, example sketches, fun ideas, and all sorts of cool stuff.

## Possible Future Development

Roughly in order of importance:

1. Clojail for safe code execution
2. Better (or any) error messages to the client
3. Websockets instead of polling
4. Better design in general
5. Ability to save and display images, as well as the code associated with them
6. Image uploading to use patterns in the program
7. Ability to define functions for recurring patterns which could be used by others

## License

Copyright (C) 2012 oskarth & S714726

Distributed under the Eclipse Public License, the same as Clojure.
