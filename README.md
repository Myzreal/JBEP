# JBEP
Java BattlEye RCon Protocol basic implementation

Provides methods to work with the BattlEye RCon Protocol.

Official description of the protocol can be found on battleye.com.
http://www.battleye.com/downloads/BERConProtocol.txt

Features:
* Connect to a BE RCon server
* Send a login packet with a specified password
* Receive packets as raw bytes
* Send command packets with any supported commands
* Set the timeout of packet receiving
* Optionally discard packets of type MSG
* Automatically handle packet merging in case of large packets being fragmented
* Some debugging options for better control

A simple example of how to connect to a server and receive a list of players is provided.

The code is well commented, both the protocol class and the example.
