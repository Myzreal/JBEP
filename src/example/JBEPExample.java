package example;

import java.io.IOException;

import protocol.BEProtocol;

/**
 * --------------------------------------------------------------------
 * 
 * Java BattlEye RCon Protocol - a battleye protocol library.
    Copyright (C) 2015  Rados³aw Skupnik

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * --------------------------------------------------------------------
 * 
 * This is an example of how to use JBEP.
 * This simple class connects to a server and retrieves a list of players by
 * launching the "players" command.
 * 
 * @author Rados³aw "Myzreal" Skupnik
 *
 */
public class JBEPExample {
	
	private String host = "localhost";
	private int port = 2502;
	private String password = "legendary";
	private BEProtocol beprotocol;

	public JBEPExample() throws IOException {
		beprotocol = new BEProtocol(host, port);
		beprotocol.setDebug(false);
		beprotocol.setTimeout(2000);
		beprotocol.connect();
		if (beprotocol.isConnected()) {
			if (login(password)) {
				beprotocol.cmd("players");					// Execute the "players" command
				byte[] rcv = beprotocol.receive();			// Receive the response - it is probably fragmented if there are many players online but JBEP handles it
				
				/* Ignore the header bytes (9) */
				byte[] temp = new byte[rcv.length-9];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = rcv[i+9];
				}
				rcv = temp;
				
				String msgString = new String(rcv);			// Wrap the bytes into a string and print it
				System.out.println(msgString);
			} else {
				System.out.println("Error when logging in.");
			}
		} else {
			System.out.println("Error when connecting.");
		}
	}
	
	/**
	 * Sends a login packet and reads the response.
	 * Determines whether login process was successful based on the response.
	 * @return - true if logged in successfully.
	 */
	private boolean login(String password) {
		beprotocol.login(password);					// Send the login packet
		byte[] response = beprotocol.receive();		// Get the response
		if (response != null && response.length == 9) {	// Check if the response is not null and it's length is exactly 9 - that's the length of a login response
			if (response[8] == 1)		// The last byte determines wheter login process was successful
				return true;
			else return false;
		} else return false;
	}
	
	public static void main(String[] args) throws IOException {
		new JBEPExample();
	}
}
