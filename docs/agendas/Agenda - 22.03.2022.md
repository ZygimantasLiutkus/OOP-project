## Agenda 22.03.2022

---

Date:           22.03.2022\
Main focus:     Progress Check\
Chair:          Zygimantas Liutkus\
Note taker:     Adelina Cazacu

# Opening

*Here you check that everybody is present.*

# Approval of the agenda

*Make sure everything that needs to be discussed is in the agenda or add it if something is missing.*

# Points of action

## Progress check

- Does anyone have any remaining work?
- Is there any place where we need to hurry up?

## Planning

- All the must-have issues should be finished by today (end of sprint 4/7)
- Set and divide tasks for sprint 5
- Bug fixing
	- Discuss if anyone has encountered any bugs. Make issues/incidents to fix them
- Merge everyone's issues to Development by the end of this week.

# Action points for next week (Scrum board)

*Every week you fill the Scrum board with new action points for that week. See the to do list for the items you should
implement.*

# Any other business

*If anybody has something that should be discussed but came up with that after the agenda was finalized (in point 2),
he/she should bring that up now so that it can be discussed after all.*

# Questions for the TA

- Is it bad to have high cyclomatic complexity?
- How to correctly import activities?
- Should files stored locally also be committed to main (json/images)?

# Tips / tops round

*Here everyone gives a tip and a top to another group member or the group as a whole.*

# Question round

*If there are any questions, now is the time to ask them.*

# Closing

- Next week
    - Chair: Adelina
    - Note taker: Zygimantas

*Now you can start working on the project. Good luck!*

# Notes

- progress: finished all issues for sprint 4 on time
- on track
- we should start paying attention to bug fixing
- maybe make a list of bugs? to discuss and fix them
- final requirements: server does not crash, is it close to what we designed (why (not)?)
- filter out activities with long names: preferably automatically (if we delete them from the json file we have to document that somewhere)
- filtering for integer overflow: perfectly fine <br/><br/>

- high cyclomatic complexity: only matters within a method
  git inspector calculates it for a whole file
- importing the activities: local copy, not allowed to put them on git
  specify in the readme what files need to be stored where
  we need to make a request to the server to get the image <br/><br/>

- good test coverage
- good javadoc
- overall doing really well! :)
