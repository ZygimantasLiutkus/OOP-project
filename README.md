## Description of project

*Quizzzz* is a single-player & multiplayer quiz game focused on discovering the energy consumption of everyday
activities in order to bring awareness to the masses of people on the global waste of resources. While this is a fun way
of playing with friends, either "online or offline", using the *Activity Panel* feature is a great way of educating
yourself on how resourceful some activities actually are.

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

There are 2 main components that are needed in order to be able to play the game: the **server** and the **client**.

Before starting the server, make sure that you have a *.json* file that contains every activity that yow want to have
featured inside the questions. In order for the file to be read by the computer, create a file called *Activities.json*
and place it in  **server > src > main > resources**. You can use a pre-made list that was used by the developers when
creating the game by accessing [this *Google Drive* link](
https://drive.google.com/file/d/1D6_m38rXbag36BSluRgsEMZvfCHo304d/view?usp=sharing) or create your own one. The JSON
file has to be in the following format:

```json
[
  {
    "id": "00-shower",
    "image_path": "00/shower.png",
    "title": "Taking a hot shower for 6 minutes",
    "consumption_in_wh": 4000,
    "source": "https://www.quora.com/How-can-I-estimate-the-kWh-of-electricity-when-I-take-a-shower"
  },
  {
    "id": "00-smartphone",
    "image_path": "00/smartphone.png",
    "title": "Charging your smartphone at night",
    "consumption_in_wh": 10,
    "source": "https://9to5mac.com/2021/09/16/iphone-13-battery-life/"
  },
  {
    "id": "...",
    "image_path": "xx/image.jpg",
    "title": "other questions follow in a similar way",
    "consumption_in_wh": 42,
    "source": "https://cse1105.pages.ewi.tudelft.nl/2021-2022/course-website"
  }
]
```

If you do not want to add a photo, you can just leave empty brackets inside that field. Though, if you want a more
aesthetic game experience, after you have entered a file name for the photo, you will need to add it respecting the file
path that you have submitted. Open **server > src > main > resources > images** and add your photo according to the
specified image path.

The server is the main "computer" that will host the games and will facilitate playing. For that to be started, you will
need to run Gradle. You can do this in 2 ways:

- write `./gradlew bootRun` in the terminal
- manually run the server side with *bootRun* inside the chosen Java IDE - i.e. Quizzzz:server [bootRun] (*Quizzzz*
  might be different depending on the file name)

From there you will have a running server. What is left to do is to start a client. The client is the actual game where
you interact with the questions and players. In order to open up a client you can use the terminal, or you can manually
open it through gradle.

To use the terminal, just enter `./gradlew run`. To start it manually you will open a Java IDE and look for the [run]
button inside the Gradle executable list.

After starting your client (the game itself), you need to connect to a host (
aka the server). Ask for a friend's IP and type it in the text box. Either port share using any available app online or
ask for a public IP. In order to find the IP on Windows, select **Start >
Settings > Network & internet > Wi-Fi >
Properties**. Scroll down and look for the **IPv4 address**. On Mac, click the *Apple* logo in the top left corner,
select **System Preferences > Network > Wi-Fi > Advanced > TCP/IP** and look for **IPv4 Address**. The person that has
opened the server should look for the IP and those who want to join to his server need to fill up the box with the
respective IP, along with **:8080** (this is the default port) at the end of the IP. After successfully joining a host,
you can now play with your friends or alone.

**NOTE:** You can play on your own server. If you do want to do so, just leave **localhost:8080** in the text box after
you have opened the server, and you will now become your own host.

**NOTE FOR TEACHERS:** The *.json* file that we have used for testing the game was edited (some activities were removed)
due to long resources links and very high consumptions on the given activities, so it will not be exactly the same as
the one inside the *Activity Bank*. What we have used can be accessed using [this *Google Drive*
link](https://drive.google.com/file/d/1D6_m38rXbag36BSluRgsEMZvfCHo304d/view?usp=sharing)

## How to contribute to it

If you have used the already pre-made list of activities, you can add new ones manually by editing the file, but you can
take advantage of the *Admin Panel* feature that is more user-friendly.

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
