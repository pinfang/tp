# Walter Kong - Project Portfolio Page

## Overview
Cent Wise Dollar Wise is a desktop application for money manager, optimised for use via a Command Line Interface (CLI). 
It is specially designed for the hostel students in NUS which aims to help these students to manage their 
financial situation and track their spending efficiently. 

### Summary of Contributions
* Code contribution: General
  1. `Data` class
  1. `Command` superclass, to standardise implementation and guarantee behaviour
  1. `Command#execute` refactor, to pass `SpendingList`, `Budget`, `RepaymentList` to commands
  1. `DukeException#getMessage` refactor, to use with Java `Exception`
  1. `Parser` superclass, to standardise implementation and guarantee behaviour
  1. `Parser` static methods, to reuse common arguments and parsing
  1. `Storage` class
  1. `Storage` methods within `SpendingList`, `Budget`, `RepaymentList`
  1. `Item#getClass` method, as a comparator
  1. `RepaymentList` methods
  1. `SpendingList` methods
  
* Code contribution: Parser
  1. `AddParser` class
  1. `ClearParser` class
  1. `ConvertParser` class
  1. `DateParser` superclass, to reflect `DateCommand` subclass
  1. `EditParser` class
  1. `RepayParser` class
  1. `SetParser` class
  1. `SpendingListParser` class
  1. `Spending` class, as wrapper for `symbol` and `amount`
  1. `YearMonth` class, as wrapper for `year` and `month`

* Code contribution: Command
  1. `DateCommand` abstract class, to guarantee input format for `DrawCommand`, `SpendingListCommand`, `Summarycommand`
  1. `MultipleCommand` wrapper, for `Command`s
  1. `SpendingListCommand` class
  
* Code contribution: Test
  1. `ParserTest` all `Parser`, by abstracting tests
  1. `SpendingListTest`
  1. `StoragePathTest`
  1. `StorageTest`
  
  Refer this link for more information: 
  [code contributions](https://nus-cs2113-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=k-walter&sort=groupTitle&sortWithin=title&since=2020-09-27&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=k-walter&tabRepo=AY2021S1-CS2113T-F14-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code~test-code~other)

* UG documentation contributions:
  1. [Section 3 Command Features interpretation, examples and summary of command line specification](https://github.com/AY2021S1-CS2113T-F14-2/tp/blob/master/docs/UserGuide.md#3-command-features)
  1. [Section 3.5 Repayment List and its screenshot](https://github.com/AY2021S1-CS2113T-F14-2/tp/blob/master/docs/UserGuide.md#35-viewing-repayment-list-summary-repayment-list)
  1. [Section 3.6 Spending List, examples and its screenshot](https://github.com/AY2021S1-CS2113T-F14-2/tp/blob/master/docs/UserGuide.md#36-viewing-spending-list-summary-spending-list)
  
* DG documentation contributions:
  1. [Section 3.1 Edit Architecture UML](https://github.com/AY2021S1-CS2113T-F14-2/tp/blob/master/docs/DeveloperGuide.md#31-architecture)
  1. [Section 3.3 Edit Parser component](https://github.com/AY2021S1-CS2113T-F14-2/tp/blob/master/docs/DeveloperGuide.md#33-parser-component)
  1. [Section 3.5.1 Edit Spending List class diagram](https://github.com/AY2021S1-CS2113T-F14-2/tp/blob/master/docs/DeveloperGuide.md#351-spending-list)
  1. [Section 3.5.2 Edit Repayment List class diagram](https://github.com/AY2021S1-CS2113T-F14-2/tp/blob/master/docs/DeveloperGuide.md#352-repayment-list)
  1. [Section 3.5.3 Edit Budget and its class diagram](https://github.com/AY2021S1-CS2113T-F14-2/tp/blob/master/docs/DeveloperGuide.md#353-budget)
  1. [Section 3.6 Edit Storage component and its class diagram](https://github.com/AY2021S1-CS2113T-F14-2/tp/blob/master/docs/DeveloperGuide.md#36-storage-component)
  
* Team-based tasks contributions:
  1. Code quality enhancements, eg SLAP, happy path, reusing code, `stream`
  1. Fix bugs and closed [>20 issues](https://github.com/AY2021S1-CS2113T-F14-2/tp/issues?q=is%3Aclosed+is%3Aissue+assignee%3Ak-walter+)
  1. TDD approach of red-green-refactor, increasing coverage and preventing breakage during refactors
  1. Multiple abstraction and refactor of major methods
  1. Debugging of logical errors during refactoring
  1. Proofread DG

* Review/mentoring contributions:
  1. Reviews PRs: [Link to PRs Reviewed](https://github.com/AY2021S1-CS2113T-F14-2/tp/pulls?q=is%3Apr+reviewed-by%3Ak-walter+)
  1. Tests and reports bugs
  1. Zoom debugging.

* Contributions beyond the project team:
  1. Bugs reported in PE dry run: [Link to PE dry run](https://github.com/k-walter/ped/issues) 
  2. DG reviews