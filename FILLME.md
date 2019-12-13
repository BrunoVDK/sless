# Sless

All extensions were implemented. There are no mutables. Every type of `CSS` expression is represented by a case class (extending a sealed trait, if any). The DSL traits are implemented by a class (one class for the base functionalities, one that extends this class to implement the extensions). Both classes have an accompanying object which is used in the tests (all the tests use the `ExtendedBase` object).

Every `Expression` represents a compilable entity. To compile a sheet, the `compile` member of `CSSExp` is called which forwards the call to its rules, which forward the call to their declarations, ... I made the assumption that list selectors cannot be nested since it is not valid `CSS`, though it is possible to combine and modifty list selectors leading to a new list selector instead (selectors are flattened as much as possible).

One other assumption I made is that every selector extension pair `(selector_that_extends,selector_to_extend)` can only be applied once. This made my life easier, though other interpretations were also tried and not particularly difficult to implement.

## Extensibility

To introduce new pseudo-classes it suffices to add case classes extending `SelectorElementExp` and implementing the `DSL` methods that allow the user to construct them.

As for @-rules, various implementations are possible depending on which one it is. For an import rule for example, `CSSExp` could be altered to function as a container of `RuleExp` as well as `CSSExp` expressions (much like what happens with the `RuleOrDeclaration` sum type). Then it can import its internal expression's rules upon construction. Or an import could be a separate type of rule which is interpreted differently when it is added to a sheet.

Namespaces the way `less.js` does it is a matter of making full use of mixins.

## Extra

In `GeneralTest` a few custom tests were written to check for clashes between extensions. Some tests particular to certain extensions were added as well (always in the respective classes). `ExtendTest` is one example. I also quickly tested a map for parametric mixins.

## Better Values

I used case objects to represent various units and a `Length` case class representing a `CSS` length. Both the `Length` and `Auto` case classes extend the `Measure` trait. Implicit conversion is used to convert from a length to a margin width. A test class ('`BetterValuesTest`') is included which simply checks whether the values display right. While lengths can be summed or multiplied I did not make this feature available to users yet, as it is not clear how to do arithmetic with relative lengths.

## Improving original features

There's an extra lint rule to allow duplicate properties. More values than just specific margins can be aggregated, and it's possible to count occurences of any property.

## Custom Extension

I didn't implement a custom extension.

## Feedback on the Project 

Interesting project, learned something that's likely to be useful in the future. I like the language though I still have some trouble getting the hang of some of its features (like implicits).
