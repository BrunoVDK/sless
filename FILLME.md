# Sless

What follows is a brief overview of the implementation. A style sheet (`CSSExp`) consists of expressions which can be rules (`RuleExp`), selectors (`SelectorExp`), declarations (`DeclarationExp`), properties (`PropertyExp`) and values (`ValueExp`). Some of the expressions extend the `Commentable` trait, all extend the `Expression` trait which represents a compilable entity (with two members ; `compile` and `pretty`).

Turning a `CSS` sheet into a string is done by calling the `compile` or `pretty` member of a `CSSExp`. 

All extensions were implemented and there are no mutable variables. Extensions worth elaborating on include :
 - *Merging* : done by determining the intersection of selectors and creating new rules based on the result.
 - *Nesting* : a rule holds a list of elements (either rules or declarations, `RuleOrDeclaration`). It has a lazy value named `rules` which represents the flattened rules contained by the very rule. It also has a lazy value `declarations` for the declarations. A `CSSExp` immediately flattens the rules it is given upon construction. Flattening a rule means  any occurrences of parent selectors are replaced, or selectors are appended (in case the parent isn't referenced).
 - *Extend* : a selector keeps track of any selectors it extends within its `extensions` parameter. It's the `CSSExp` that first fetches all extension pairs such that it can filter out duplicates, after which it applies the extensions by calling the `extend` member of selectors. The reason I filter out duplicates is that a user may keep a selector in a variable which he uses multiple times throughout the sheet. If that selector extends some other selector, the extension will be applied multiple times. Additionally, it does not make any sense to apply the same extension twice so it may be interpreted as some form of linting. Parent selectors can extend and can be extended.

When two sheets are merged, extensions of selectors are applied *before* merging. I also implemented it such that it's the other way around (extensions applied *after* merging) though it made a little less sense to me.

N.B. The first version of the code assumed that list selectors can't be nested but I relaxed this later on (it had both advantages and disadvantages to consider everything a list selector).

## Extensibility

To introduce new pseudo-classes it suffices to :
- extend the `SelectorExp` appropriately (usually just a matter of adding a case class or turning a case class in an abstract class and extending it).
 - implement the relevant selector methods (only the ones relating to immutability such as creating a copy are necessary).
 - implement the `DSL` methods that allow the user to construct them.

As for @-rules, various implementations are possible depending on which one we're talking about. For an import rule `CSSExp` could be altered to function as a container of `RuleExp` as well as `CSSExp` sheets (much like what happens with the `RuleOrDeclaration` sum type). Then it can import its internal sheet's rules upon construction (the merge functionality can be reused). 
A font-face @-rule could be an other type of rule, which means one can decompose the `RuleExp` case class (into an abstract class with two case classes extending it). Or yet an other type of expression like `FontFaceExp` (a case class) could be defined.

Namespaces the way `less.js` does it is a matter of making full use of mixins (there's one example test in `MixinTest`).

## Extra

25 tests were added. Some of them test extensions further (they're implemented in the corresponding test files). Some others relate to combinations of extensions (e.g. merging, nesting, commenting and extending at the same time). They are implemented in `GeneralTest`.

## Better Values

I used case objects to represent various units and a `Length` case class representing a `CSS` length. Both the `Length` and `Auto` case classes extend the `Measure` trait. Implicit conversion is used to convert from a length to a margin width. A test class ('`BetterValuesTest`') is included which simply checks whether the values display right. While lengths can be summed or multiplied I did not make this feature available to users yet, as it is not clear how to do arithmetic with relative lengths.

## Improving original features

There's an extra lint rule to allow duplicate properties. More values than just specific margins can be aggregated, and it's possible to count occurences of any property.

## Custom Extension

None implemented.

## Feedback on the Project 

Interesting project, learned something that's likely to be useful in the future. I like the language though I still have some trouble getting the hang of some of its features (like implicits).
