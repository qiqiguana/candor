/*
* Copyright (C) 2005  John D. Heintz
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU Library General Public License
* as published by the Free Software Foundation; either version 2.1
* of the License.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Library General Public License for more details.
*
* John D. Heintz can be reached at: jheintz@pobox.com 
*/
package dash.examples.producerConsumer;

import dash.Build;
import dash.Component;


/**
 * @author jheintz
 *
 */
@Component
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main().run();
	}

	String message;
	
	@Build
	Producer producer() {
		return new Producer();
	}
	
	@Build(offerAsKey={"comp/consumer"})
	Consumer consumer() {
		return new Consumer() {

			public void receive(String msg) {
				message = msg;
			}
			
		};
	}
	
	void run() {
		producer().produce("example message");
		
		if (message == null || !message.equals("example message"))
			throw new IllegalStateException();
	}

}
