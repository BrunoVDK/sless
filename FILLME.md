# Sless

## Extensibility

Briefly explain which files you have to change to introduce a new sless feature, e.g. add a new pseudo-class/-element, add support for namespaces, add at-rules, etc. 
Hint: if this lists all the files in your project you should reevaluate your implementation.

A few concrete examples of what would need to happen when extending the DSL any further :

- Adding a new pseudo-class or pseudo-element : 
- Adding support for namespaces :
- Adding @-rules : 

## Extra

Write which files, if any, contain extra self-written tests. If you did something extra impressive let us know here as well!

In `GeneralTest` a few custom tests were written to check for clashes between extensions. Some tests particular to certain extensions were added as well (always in the respective classes).

## Better Values

I used objects to represent various units and a `Length` case class representing a `CSS` length. Both `Length` and the `Auto` extend the `Measure` trait. A test class ('`BetterValuesTest`') is included.

## Improving original features

If you chose to extend some of the original features as an extensions, properly document what you did here.

There are a few extra lint rules in the source code ; the removal of duplicate properties from rules

## Custom Extension

If you came up with your own extension, properly document it here. Explain its
intended behavior and showcase by example.

???????????

## Feedback on the Project 

Interesting project, learned something that's likely to be useful in the future. 
