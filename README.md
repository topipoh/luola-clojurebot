# bot

A Clojure bot for https://github.com/aoh/luola

## Usage

Run with default options

`lein run`

Print available options

`lein run -- --help`

## License

Copyright © 2018 Topi Pohjosaho

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

## TODO
* fix dijkstra performance problem
* cleanup dijkstra implementation
* print board with costs
* print board with paths

## DONE
* use dijkstra result to choose action
* implement dijkstra
* add bot to server
* always walk to the right
* write a test
* REPL
* parse the board
* get neighboring tiles
* calculate costs for tiles
