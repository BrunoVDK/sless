# Sless

*Basic structure* ; the DSL traits are implemented by a class (one class for the base functionalities, one that extends this class to implement the extensions). Both classes have an accompanying object which is used in the tests (all the tests use the `ExtendedBase` singleton). All components of a css sheet (including the sheet itself) are represented by a case class with suffix `Exp` because every component extends the `Expression` trait. The case classes extend a sealed trait, if any. All extensions were implemented and there are no mutables.

Every `Expression` represents a compilable entity. To compile a sheet, the `compile` member of `CSSExp` is called which forwards the call to its rules, which forward the call to their declarations, ... 

Sheets (`CSSExp`) have a list of rules (`RuleExp`) which in turn have a selector `SelectorExp` and a list of rules or declarations (`RuleOrDeclaration` = `Rule` + `Declaration`). A selector is a list selector which has a list of selector elements `SelectorElementExp` which could be combinators, modifiers, ... List selectors aren't nested as it isn't valid CSS, and it complicated matters needlessly. 

Extensions worth elaborating on ;  
- Merging : the intersection of selectors are determined. New rules are created based on whether the intersection is empty or not.
- Nesting : a `RuleExp` has a list of elements (rules or declarations). It has a lazy value `rules` which returns all the *flattened* rules in the very rule. It also has a lazy value `declarations` returning all declarations. 
- Extend : a `SelectorExp` keeps track of the selectors it may extend by keeping a map of `(selector_that_extends,selector_to_extend)` pairs. `CSSExp` has a member called `rules`, again a lazy value. It holds the sheet's rules, but after they've been flattened and after all extensions have been applied. To apply extensions, a member in `CSSExp` called `extend` is called which goes through selectors and tells each selector to apply its extensions on other selectors.

## Extensibility

To introduce new pseudo-classes it suffices to add case classes extending `SelectorElementExp` and implementing the `DSL` methods that allow the user to construct them.

As for @-rules, various implementations are possible depending on which one it is. For an import rule for example, `CSSExp` could be altered to function as a container of `RuleExp` as well as `CSSExp` sheets (much like what happens with the `RuleOrDeclaration` sum type). Then it can import its internal sheet's rules upon construction. Or an import could be a separate type of rule which is interpreted differently when it is added to a sheet.

Namespaces the way `less.js` does it is a matter of making full use of mixins.

## Extra

20 tests were added. Some of them test extensions further (they're implemented in the respective test files). Some others relate to combinations of extensions (e.g. merging, nesting, commenting and extending at the same time). They are implemented in `GeneralTest`.

I also quickly tested a map for parametric mixins.

## Better Values

I used case objects to represent various units and a `Length` case class representing a `CSS` length. Both the `Length` and `Auto` case classes extend the `Measure` trait. Implicit conversion is used to convert from a length to a margin width. A test class ('`BetterValuesTest`') is included which simply checks whether the values display right. While lengths can be summed or multiplied I did not make this feature available to users yet, as it is not clear how to do arithmetic with relative lengths.

## Improving original features

There's an extra lint rule to allow duplicate properties. More values than just specific margins can be aggregated, and it's possible to count occurences of any property.

## Custom Extension

I didn't implement a custom extension.

## Feedback on the Project 

Interesting project, learned something that's likely to be useful in the future. I like the language though I still have some trouble getting the hang of some of its features (like implicits).
