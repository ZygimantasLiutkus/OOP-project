## Agenda

---

Date:           {February 15th 2022}\
Main focus:     {Getting started}\
Chair:          {Adelina Cazacu}\
Note taker:     {Zygimantas Liutkus}

In italics, you will find some additional explanations of the agenda points. You will find mostly the same points in the
next agendas.

# Opening

*Check that everybody is present.*

# Approval of the agenda

*Make sure everything that needs to be discussed is in the agenda or add it if something is missing.*

# Points of action

*The items below are things you should look into after the first meeting. During the meeting you can divide (some of)
the work between the team members, so that everybody has something to do afterwards.*

- Read Resources and Tools (on BrightSpace)
- Make sure you have a Mattermost channel (your TA will create one for you)
- It is your job that you and your team contribute (equally)
    - If there is a problem it is your job to notify the TA
- Create a planning and put it on GitLab
    - Implement all items on the todo list
- Discuss a cake rule: If you are late or not there at all, you have to bring cake (or something similar)
- Discuss code style!
- Divide the topics below for research and set a deadline to be done with it.

- Things to research:
    - Scrum (see lecture 1)
        - How are you going to use scrum in the team?
    - Git (see lecture 2)
        - How does it work?
        - What is branching?
    - Gradle (see lecture 4)
        - What does it do?
        - How do you add a library to the build file?
    - Code structure (see lecture 4)
        - How will you use packages?
        - What will your code structure look like? (UML)
    - GUI
        - OpenFX
    - IDE
        - Eclipse
        - IntelliJ

# Action points for next week (Scrum board)

*Every week you fill the Scrum board with new action points for that week. See the to do list for the items you should
implement.*

# Any other business

*If anybody has something that should be discussed but came up with that after the agenda was finalized (in point 2),
he/she should bring that up now so that it can be discussed after all.*

# Questions for the TA

*Your TA will visit you in the second half of the lab session. Note down all questions that you have so that you can ask
them then.*

- Do we have to add questions to the database or do we already have it done with a specific layout?
- What should the agenda look like?

# Question round

*If there are any questions, now is the time to ask them.*

# Session notes

- Scrum:
    - Have meetings each week discussing what we did and what were going to do in the upcoming scrum sprint.
- Git:
    - A branch is a individual workspace, separate from other branches, where changes on one branch don’t change
      anything on other branches.
    - Merging adds the contents of two branches into one of the branches. Possibly deletes the other branch.
    - Committing changes the files locally while pushing uploads the changes to GitLab.
    - Commit messages should be brief, specific, functional (add, fix, change, etc.).
- Gradle:
    - automatically builds source code from java class files (set of specifications). Dependency manager, when you use
      external files (JavaFX, etc.), put them in the gradle file so that the compiler considers them.
- Scrum board:
    - Make issue boards on GitLab every week so everyone knows what to do that week.
- Code structure:
    - Comment each method (brief description), use InteliJ checkstyle tool.
- Questions in the database:
    - There are integrated database functions in spring.
    - Depends on chosen design, can have a separate question repository or have questions with prepared answers. Images
      for questions can possibly be fetched from stored urls.
- Agenda:
    - The template used for this lab session was fine. Add checking (everyone talks about what they did that week).
    - After the meeting update the agenda with the notes about it in a separate point.
- Teamwork assignment (1b):
  Discussed the completion of the first teamwork assignment.
- Backlog:
    - Decrease the amount of must haves (e.g. Joker cards not necessary for the game to work, so they can be in should
      have).
    - Functioning products is worth more than a few extra functionalities.
    - Backlog should be formulated as user stories.
    - Have draft overview before making issues on GitLab.
- Deadlines:
    - HCI draft – next Friday.
    - Code of conduct – this Friday.
    - Backlog – this Friday.
    - Everyone put up a merge request before this Friday.
- Merge everything to main before the meeting so the TA can see the progress (Sunday evening).

# Closing

*Now you can start working on the project. Good luck!*
