/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.control.service.shell;

import dk.statsbiblioteket.summa.control.api.Service;
import dk.statsbiblioteket.summa.common.shell.Core;
import dk.statsbiblioteket.summa.common.rpc.SummaRMIConnectionFactory;
import dk.statsbiblioteket.util.rpc.ConnectionManager;

/**
 * Created by IntelliJ IDEA. User: mikkel Date: Aug 7, 2008 Time: 1:35:13 PM To
 * change this template use File | Settings | File Templates.
 */
public class ServiceShell {
    private ConnectionManager<Service> connManager;
    private Core shell;

    public ServiceShell(String rmiAddress) throws Exception {
        shell = new Core ();
        shell.setPrompt ("service-shell> ");

        connManager = new ConnectionManager<Service> (
                new SummaRMIConnectionFactory<Service>(null));

        shell.installCommand(new PingCommand(connManager, rmiAddress));
        shell.installCommand(new IdCommand(connManager, rmiAddress));
        shell.installCommand(new StartCommand(connManager, rmiAddress));
        shell.installCommand(new StopCommand(connManager, rmiAddress));
        shell.installCommand(new StatusCommand(connManager, rmiAddress));
        shell.installCommand(new KillCommand(connManager, rmiAddress));

    }

    public void run () {
        // FIXME pass command line args to shell core
        shell.run(null);
        connManager.close();
    }

    public static void printUsage () {
        System.err.println ("USAGE:\n\tcontrol-shell <service-rmi-address>\n");
        System.err.println ("For example:\n\tcontrol-shell //localhost:27000/my-service");
    }

    public static void main (String[] args) {
        try {
            if (args.length != 1) {
                printUsage ();
                System.exit (1);
            }

            ServiceShell shell = new ServiceShell(args[0]);
            shell.run ();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}




