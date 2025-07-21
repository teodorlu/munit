# μnit

/A small library for arithmetic on numbers with units./
(Pronounced "munit")

## Design goals

- Unit systems other than SI are supported.
- ... though a unit system for is provided.
- Numbers with units are easy to work with from the REPL.

## Non-design goals

- We don't try to infer the intended target unit - you get quantities in terms of base units.
- We don't aim for fast numerical performance (currently).
  An idea for fast numerics is to do unit consistency once, then create a checkless pathway afterwards.

## Acknowledgements

- Gerald Sussman for unit and unit system code scmutils.
- Sam Ritchie for help understanding Sussman's intent, and his and Colin Smith's work on [Emmy] (previously sicmutils).
- Anteos for [broch], a nice unit library.

[scmutils]: https://groups.csail.mit.edu/mac/users/gjs/6946/refman.txt
[Emmy]: https://github.com/mentat-collective/emmy
[broch]: https://github.com/anteoas/broch

## Future ideas

I've had dataflow programming with quantities in the back of my head for about ten years.
Mathcad introduced me to the idea, and [Clerk] and Emmy showed all that was possible within a Clojure REPL.
μnit would probably be nicer if it was part of Emmy, but that requires some more work (I think).

[Clerk]: https://github.com/nextjournal/clerk
