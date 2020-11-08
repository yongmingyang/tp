---
layout: page
title: Teng Jian Ling's Project Portfolio Page
---

### Project: Medmoriser

Medmoriser is a desktop app to help medical students organise, memorise and revise their content. The user interacts with it using a CLI, and it has a GUI created with JavaFX and is written in Java.

Given below are my contributions to the project.

- **Code contributed:** [RepoSense Link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=tengjianling)

- **New feature:** Added the ability quiz the user based on the keyword specified.
  - Note: this feature was implemented as a team of 2, by Joshua and I.
  - What it does: allows the user to quiz himself  by displaying a randomly selected question  from the question book that matches the keyword or tag specified without showing the answer. The answer previously stored in the question book will be displayed after the user enters his own answer.
  - Justification: Active recall is a tried and tested study technique known to be very effective for memorising content. This feature provides a convenient way for the user to implement active recall into their revision, as they are able to test themselves through a simple quiz command.
  - Highlights: The implementation of this enhancement was challenging due to the following reasons:
    - It required an in-depth analysis of design alternatives in order to find the best design choice.
    - It  requires new commands to be created, such as the `quiz`,  `answer` and `endquiz` commands.
    - The creation of these new commands means that new test classes also have to be created for each of these commands and its parser class.
    - The `quiz` and `answer` commands are very different from the existing commands available. Thus, we cannot merely refer and make slight changes based on existing classes when creating `QuizCommand` and `AnswerCommand`, they have to be written ourselves.
    - Additional fields have to be added for keeping track of the state of the application (i.e. whether the user is currently doing a quiz)

- **New feature:** Added the feature to allow user to be quized on the next question in the quiz.
  - What it does: allows you to be tested on another question based on the same keywords you provided for the quiz, without having to end the quiz first.
  - Justification: It may be troublesome for the user to have to go through the process of ending the quiz and typing the whole quiz command again if he wants to quiz himself with the same keywords, and he may get the same exact same question as before even if it is randomised. This `next` command provides a convenient way for users to continually quiz themselves.
  - Highlights: The implementation of this enhancement was challenging as additional fields have to be created to keep track of which questions to randomise from, since we do not want the `qAndA` obtained from the `next` command to be the same as the current `qAndA` that the user is seeing.

- **Enhancements to Existing features:**
  - Changed the usage message in `edit` command to the context of questions and answers: [#41](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/41)
  - Renamed all uses of `QuestionSet` and `questionSet` to `QAndA` and `qAndA` to avoid confusion between sets and lists: [#43](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/43)
  - Changed restrictions on `question`, `answer`, and `tag` fields in `add` command: [#66](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/66)
  - Changed implementation of `QuizCommand` and `QuizCommandParser` to include ability to quiz based on multiple keywords or tags: [#73](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/73)
  - Wrote the following additional tests: `QuizCommandTest`, `QuizCommandParserTest`, `ExitCommandTest`, `ExitCommandParserTest`, `AnswerCommandTest` and `AnswerCommandParserTest`: [#73](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/73)
    - increased code coverage by 2.96%
  - Wrote the following addtional tests: `AnswerCommandTest` and `AnswerCommandParserTest`: [#157](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/157)
    - increased code coverage by  1.19%
  - Updated the GUI for quiz to be centralised and remove index number: [#83](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/83)


- **Documentation:**
  - User Guide:
    - Added documentation for the features `add` , `delete`, `edit` and `next`: [#18](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/18), [#83](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/83), [#153](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/153)
  - Developer Guide:
    - Added use cases for Medmoriser: [#18](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/18)
    - Added implementation details of the `delete` feature, including a sequence diagram and activity diagram: [#78](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/78)

- **Community**:
  - PRs reviewed with non-trivial review comments: [#71](https://github.com/AY2021S1-CS2103T-W15-1/tp/pull/71)
  - Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2021S1-CS2103T-W16-3/tp/issues/236), [2](https://github.com/AY2021S1-CS2103T-W16-3/tp/issues/237))

