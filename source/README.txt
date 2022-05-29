-Project Title
Chips Challenge Game


-How To Run
Run the Main.java file to play the game


-File Layout
All java and fxml files should be in the src folder. There should also be an Images folder within this.

Within the Images folder, there should be:
collectables folder, holding BlueKey, FireBoots, Flippers, GreenKey, RedKey and Token png's
doors folder, holding BlueDoor, GreenDoor, RedDoor and TokenDoor png's
environment folder, holding Fire, Goal, Ground, Teleporter, Wall and Water png's
moveableEntities folder, holding BlindEnemy, DumbEnemy, LineEnemy, Player, SmartEnemy and WallEnemy png's
tokenDoors folder, holding png's of TokenDoor[1-9]
death.png, menuBackgroundImage.jpg and tourism.jpg

There should be a GlobalFiles folder, holding a CustomFiles folder within it.
It should also have CustomFileNames, leaderboard and Profiles txt files, alongside any default level txt files

The third key folder there should be PlayerFolders. This can be empty by default

Since A2 we now have a custom files folder. As of A3 this now includes directory's for each user that logs in
to create a map.

I have now added another folder 'audio', which stores audio settings and sudio files.


-Image References
> Background Image - 
(n.d.). Retrieved from https://cdn2.artstation.com/p/assets/images/images/001/995/434/large/uros-sljivic-waters-of-brokilon.jpg?1455702895
Title - Waters of Brokilon
Artist - Uros Sljivic
Website - Artstation


> Some of the icons we used are from:
https://itch.io/game-assets/free/tag-icons
and we have edited most of them to suit our game.




> TO MAKE SURE TWITTER WORKS

For Eclipse-**
To import the jar file, right click on the project folder
then go to Properties>Java Build Path>Libraries>Add JARs
and locate the twitter4j-core-4.0.7.jar file. This will be in the src folder.

**For IntelliJ-**
To import the jar file, click on File in toolbar. Then click project structure, 
click on modules and then click on dependencies. On the right hand side you should
see a '+'. Then choose 'JARs or Directories' from the dropdown. Go into the src
folder and select the twitter4j-core jar. Then click ok and click apply.

The classes with the Twitter imports rely on a twitter4j core file and a .properties
file, so errors will occur without these files present.

DO NOT EDIT THE .PROPERTIES FILE
The contents of this file are made specifically to run the tweet feature.
This file was made so that the library can connect to the account and create
the tweet. The randomly generated strings are what specifies our account and 
authorises our code to post to it.

Consumer API Keys:
API Key - zmmnAFDNogCL3tzGw8N5LWZKv
API Secret Key - Va6KbpObdT6YZ5nrb2dDhByoJnVdhj5udgeGmTZheu5FEPDhZ2

Access Tokens:
Access Token - 1246409392016285697-LnoZLN70xr9NeEQJKUSO9rFgWiw7Nj
Access Token Secret - blMgQft105SDcdUw0LXd9M7XpXh51rNtBHnP9XmQmuYJu

Account Handle: @Seans_CS235_A3
Link - https://twitter.com/Seans_CS235_A3
