<h1 align="center">XO Game – JavaFX Project</h1>   


<p align="center">
  <img width="550" height="350" src="https://github.com/MohRaouf/XO-Java/blob/master/Images/9.jpg">
</p>

<h3 align="center"> High Fidelity XO Multiplayer Game using JavaFX - Scene builder - MySQL - Networking </h3>   


## Methodology
We plan to make the game playable on a global scale by having a Multi-Threaded Server and Database Up and running, accepting players requests on a global scale, The Project consists of 2 main Components: The Server Program - The Client (Player)
At first, a player needs to register or login to play with other players, the Authentication is done by a unique username and an arbitrary password, no 2 players allowed to have the same username, the authorization process is expected to be as the following:

1. The player connects to the server through the Client Application and sends either the login or registration info as the following format

<p align="center">
  <img width="600" height="200" src="https://github.com/MohRaouf/XO-Java/blob/master/Images/1.png">
</p>

2. The Server is configured to accept all players requests on specified port number and hostname that are hardcoded in the player application, when the server identifies that the incoming request is an Authentication request, the request is processed and the player user and password is extracted from the incoming string to validate the player info from the DB and send back a message

<p align="center">
  <img width="500" height="400" src="https://github.com/MohRaouf/XO-Java/blob/master/Images/2.png">
</p>

When the Player receives #Done the game shows the Server Screen and ask the Server to send Info

<p align="center">
  <img width="550" height="200" src="https://github.com/MohRaouf/XO-Java/blob/master/Images/3.png">
</p>


3.	Then the player application populates the received data on the Server screen, the received data contains the available players, online players, and top players ordered by their score in the previous sessions than when for example Player1 want to play with player2, Player1 sends the username of the player he wants to play with to the server in our example its name is Player2, the server reverse the action and send the player1 username to player2 if he is online otherwise reply with $no, the application identifies the signature of the message and show the player a message box telling that Player1 wants to play with you, and on the accept the server send the game info to each of the 2 players to start the game as the following:

<p align="center">
  <img width="600" height="450" src="https://github.com/MohRaouf/XO-Java/blob/master/Images/4.png">
</p>

4.	when Player 2 clicks yes on the message box, the server sends back to both players the game info $yes,Player1,X,Player2,Y then both enter the game with the first turn of the first player in the game info then the game logic starts.

5.	The Game methodology is based on a stream of known characters between the 2 players and the server when a player makes a move the corresponding character is sent to the server which forward the game move to the player opponent and vice versa until either of the 2 players wins and the other lose or both draw, the player program sends =win on win and =lose on lose and =draw on a draw.

<p align="center">
  <img width="650" height="400" src="https://github.com/MohRaouf/XO-Java/blob/master/Images/5.png">
</p>


6.	The server takes an action corresponding to the game result either increase the player cumulative score in the DB or decrease it.

## Entity Relationship Diagram of the Database

<p align="center">
  <img width="300" height="150" src="https://github.com/MohRaouf/XO-Java/blob/master/Images/6.jpg">
</p>

•	We used a simple Database design consist of one table with 3 columns, defines the player username, password and score, the password is hashed with MD5 Algorithm, and stored in a varchar(255) when validating a user, the user password is hashed with the same algorithm, the hash is compared with the hash stored in the DB.

## Database Schema
<p align="center">
  <img width="400" height="250" src="https://github.com/MohRaouf/XO-Java/blob/master/Images/7.png">
</p>

## Game Screen

<p align="center">
  <img width="400" height="250" src="https://github.com/MohRaouf/XO-Java/blob/master/Images/8.jpg">
</p>


## Team Members and Assigned Tasks:
1.	Mohamed AbdelRaouf (Server Side)
*	Game Scenario 
*	Parallel Socket Server
*	Port Forwarding 
*	Database Connectivity
2.	Mohamed Bassiouny (Client Side)
*	GUI (Pre Game Screen)
*	Connect Scenes
*	Pass Data between Scenes
*	AI Difficulty
3.	Alaa Mohamed Habib (Client Side)
*	GUI Start Screen 
*	Game Logic
*	Socket Client
*	Game Senario
*	 login Logic
4.	Esraa Abou AlKassem (Client Side)
*	GUI Login Screen
*	GUI Server&Client Screen
*	Game initialization Login
5.	Phoebe Ezzat Gaballa (Client Side)
*	GUI Game Screen
*	Game Logic Offline
*	Game Login with PC 
*	Connect Scenes
