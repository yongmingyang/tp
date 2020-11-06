---
layout: page
title: Yong Ming Yang's Project Portfolio Page
---

## Project: Medmoriser

Medmoriser is a desktop app to help medical students organise, memorise and revise their content. The user interacts with it using a CLI, and it has a GUI created with JavaFX and is written in Java.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=yongmingyang&tabRepo=AY2021S1-CS2103T-W15-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Redefined the Find functionality
    * What it does: Allows the user to find based on 3 modes
        1. `find t/[tagname]` which allows the user to find QAndA based on the tagname(s).
        2. `find q/[keyword]` which allows the user to find QAndA which contains the specified keyword(s) in the question.
        3. `find [keyword]` which allows the user to find QAndA which contains the specified keyword(s) in the question or the answer.
    * Justification: Gives the user more flexibility to find relevant QAndA sets.
    * Added additional test cases to suit the redefined functionality.
    * Pull requests/Issues:
        1. [\#50]()
        2. [\#56]()
        3. [\#76]()
        4. [\#97]()
        5. [\#103]()
        6. [\#122]()
        7. [\#126]()
    * Credits: The find functionality makes use of code from the following links:
        1. https://stackoverflow.com/questions/36793397/check-if-string-contains-word-not-substring.

* **Documentation**:
  * User Guide:
    * Added documentation for the features `delete` and `find` [\#72]()
    * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
  * Developer Guide:
    * Added implementation details of the `delete` feature.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
  * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
  * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
  * Integrated a third party library (Natty) to the project ([\#42]())
  * Integrated a new Github plugin (CircleCI) to the team repo

* _{you can add/remove categories in the list above}_
