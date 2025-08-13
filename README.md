# μnit

*A small library for arithmetic on numbers with units.* Pronounced "munit". If
you don't like the greek μ for "very small" ("micro"), think "magnitude 'n
unit".

Status: pre-alpha, expect breaking changes.

## Installation

Currently only by Git SHA.

```clojure
io.github.teodorlu/munit {:git/sha "..."}
```

With Neil,

    neil dep add io.github.teodorlu/munit

## Usage quickstart

Munit represents numbers with units as Clojure data.

| Example             | Interpretation                         |
|---------------------|----------------------------------------|
| `44`                | Unitless quantity                      |
| `'m`                | One meter                              |
| `{'m 2}`            | One square meter                       |
| `[44 'm]`           | 44 meters                              |
| `[44 {'m 2}]`       | 44 square meters                       |
| `[9.81 'm {'s -2}]` | 9.81 kilogram-meters per square second |

Here's a small example:

```clojure
(ns your-calculation
  (:refer-clojure :exclude [* / + -])
  (:require [munit.prefix :refer [k M]]
            [munit.si :refer [kg m s]]
            [munit.units :refer [* / + - measure-in]]))

(def g "gravitational accelleration at the earth's surface"
  [9.81 m {s -2}])

;; Gravitational force of 200 kg
(* [200 kg] g)
;; => [1962.0 kg m {s -2}]

;; Let's measure that in kilo-newtons.
(def N [kg m {s -2}])
(def kN [k N])

(measure-in (* [200 kg] g) kN)
;; => 1.962
```

Note:

- `*`, `/`, `+` and `-` are from `munit.units`, not `clojure.core`.
- Base units like `m`, `kg` and `s` are just symbols - `munit.si` is pure
  convenience

A second example can be found in the presentation notes for the macroexpand-2
lightning talk [The force, the notation and the limitation].

[The force, the notation and the limitation]: https://play.teod.eu/the-force-the-notation-and-the-limitation/

## Design goals

- Unit systems other than SI are supported.
- ... though a unit system for SI is provided.
- Numbers with units are easy to work with from the REPL.

## Non-design goals

- Don't try to infer the intended target unit, just work on base units.
- Don't aim for numerical performance for now.

## Acknowledgements

- Gerald Sussman for unit and unit system code scmutils.
- Sam Ritchie for help understanding Sussman's intent, and his and Colin Smith's work on [Emmy] (previously sicmutils).
- Anteo AS for [broch], a nice unit library.

[scmutils]: https://groups.csail.mit.edu/mac/users/gjs/6946/refman.txt
[Emmy]: https://github.com/mentat-collective/emmy
[broch]: https://github.com/anteoas/broch

## Future ideas

I've had dataflow programming with quantities in the back of my head for about ten years.
Mathcad introduced me to the idea, and [Clerk] and Emmy showed all that was possible within a Clojure REPL.
μnit would probably be nicer if it was part of Emmy, but that requires some more work (I think).

[Clerk]: https://github.com/nextjournal/clerk

After using μnit briefly, I also found I wanted tables of numbers where the rows had units.
Is that possible with [Tablecloth]?
For units to work, we need to store a unit along with each column, and hook into the column operations (+, -, *, /).

[Tablecloth]: https://github.com/scicloj/tablecloth
