# Starting template

This README will need to contain a description of your project, how to run it, how to set up the development
environment, and who worked on it. This information can be added throughout the course, except for the names of the
group members. Add your own name (do not add the names for others!) to the section below.

## Description of project

## Group members

| Profile Picture | Name | Email |
|---|---|---|
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4753/avatar.png?width=400) | Adelina Cazacu | A.Cazacu@student.tudelft.nl |
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4979/avatar.png?width=400) | Jesse Vleeschdraager | J.C.Vleeschdraager@student.tudelft.nl |
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4754/avatar.png?width=400) | Violeta-Mara Macsim | V.M.Macsim@student.tudelft.nl |
| ![](https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/4874/avatar.png?width=400)|Petar Koev| P.P.Koev@student.tudelft.nl|
| ![](https://en.gravatar.com/userimage/217262152/bc8d73f5a40430a7b47976564f0ee1a7.jpg?size=200) | Žygimantas Liutkus | Liutkus@student.tudelft.nl |
| ![](https://s.gravatar.com/avatar/3862bbbbfb42cf1f778a2c0e23bb187f?s=200) | Kees Blok | C.Blok-1@student.tudelft.nl |

## How to run it

## How to contribute to it

### Use Checkstyle

In this project checkstyle is used to automatically format the code and notify the programmer about inconsistencies in
the code and format. Before each commit the programmer should run CheckStyle on the whole project to make sure there
will be no inconsistencies in the code on GitLab. If the pipeline on GitLab fails because the code style was not
according to the CheckStyle, there should be no new commits to this branch other than trying to fix this issue until the
pipeline passes.

To enable checkstyle and auto formatting, follow the next steps

1. Install the plugin
    - Go to File -> Settings -> Plugins
    - Select the Marketplace tab
    - Search for CheckStyle-IDEA and install it
    - Press Restart IDE
    - Press Restart
2. Configure checkstyle
    - Go to File -> Settings -> Tools -> Checkstyle
    - Make sure you have Checkstyle version 9.3
    - Set the Scan Scope to All files in project
    - Enable 'Treat Checkstyle errors as warnings'
    - Click the + Symbol to add a configuration file
    - Enter a Description (e.g. Custom checkstyle rules)
    - Press Browse
    - Select the checkstyle.xml file in the root of the project
    - Enable 'Store relative to project location'
    - Press 'Next'
    - Press 'Next' again
    - Press 'Finish'
    - Activate the newly added checkstyle rules by pressing the checkbox of the corresponding configuration file
    - Press 'Apply' to save the changes
3. Update the code style of the editor
    - Go to File -> Settings -> Editor -> Code Style
    - Select Project as scheme
    - Press the settings icon
    - Press 'Import Scheme'
    - Press 'CheckStyle Configuration'
    - Choose the checkstyle.xml file that is in the root of the project
    - Press 'OK'
    - Press 'Apply'
4. Automate reformatting (recommended)
    - Go to File -> Settings -> Tools -> Actions on Save
    - Enable the following actions
        - Reformat code
        - Optimize imports
        - Rearrange code
    - Press Apply

## Copyright / License (opt.)
