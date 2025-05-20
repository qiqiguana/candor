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
package dk.statsbiblioteket.summa.control.server.shell;

import dk.statsbiblioteket.summa.common.shell.Command;
import dk.statsbiblioteket.summa.common.shell.ShellContext;
import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.control.api.ClientConnection;
import dk.statsbiblioteket.summa.control.api.ClientDeployer;
import dk.statsbiblioteket.summa.control.api.ControlConnection;
import dk.statsbiblioteket.summa.control.api.InvalidClientStateException;
import dk.statsbiblioteket.summa.control.api.Status;
import dk.statsbiblioteket.util.qa.QAInfo;
import dk.statsbiblioteket.util.rpc.ConnectionManager;
import dk.statsbiblioteket.util.rpc.ConnectionContext;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
@QAInfo(author = "mke",
        level = QAInfo.Level.NORMAL,
        state = QAInfo.State.QA_NEEDED)
public class ClientsCommand extends Command {
    private ConnectionManager<ControlConnection> cm;
    private String controlAddress;

    /**
     * Creates a client command.
     * @param cm The control manager connection, used to obtain connection to
     * the control server.
     * @param controlAddress Address to the control server instance.
     */
    public ClientsCommand(ConnectionManager<ControlConnection> cm,
                           String controlAddress) {
        super("clients", "list all deployed clients");

        installOption("s", "status", false,
                                       "Look up status for each client (slow)");
        installOption("e", "extended", false,
                                   "Get deployment metadata about each client");

        this.cm = cm;
        this.controlAddress = controlAddress;
    }

    /**
     * {@inheritDoc}
     * @param ctx Context used to print messages and retrieve user feedback
     */
    @Override
    public void invoke(ShellContext ctx) throws Exception {
        // Connect to the Control and send getClients request
        boolean doStatus = hasOption("s");
        boolean doExtended = hasOption("e");

        ConnectionContext<ControlConnection> connCtx = null;
        try {
            connCtx = cm.get(controlAddress);
            if(connCtx == null) {
                ctx.error ("Failed to connect to Control server at '"
                           + controlAddress + "'");
                return;
            }

            ControlConnection control = connCtx.getConnection();
            List<String> clients = control.getClients();

            /* Header */
            String header = "Deployed clients";
            if(doStatus) {
                header += "\tStatus";
            }
            if(doExtended) {
                header += "\tBundle, Address";
            }
            ctx.info(header);

            // Generate report, sorting alphabetically by client id
            SortedSet<String> sortedClients = new TreeSet<String>(clients);
            for (String client : sortedClients) {
                String msg = "\t" + client;
                if (doStatus) {
                    try {
                        ClientConnection conn = control.getClient(client);

                        if (conn == null) {
                            msg += "\t"
                                    + new Status(Status.CODE.not_instantiated,
                                                 "No connection to client");
                        } else {
                            msg += "\t" + conn.getStatus();
                        }
                    } catch (InvalidClientStateException e) {
                        msg += "\t" + e.getMessage();
                    } catch (Exception e) {
                        msg += "\tConnection error: " + e.getMessage();                        
                    }
                }
                if (doExtended) {
                    try {
                        Configuration conf =
                                         control.getDeployConfiguration(client);

                        String bundleId = conf.getString(
                                        ClientDeployer.CONF_DEPLOYER_BUNDLE,
                                        "ERROR");
                        String host = conf.getString(
                                         ClientConnection.CONF_REGISTRY_HOST,
                                         "ERROR");
                        String port = conf.getString(
                                         ClientConnection.CONF_REGISTRY_PORT,
                                         "ERROR");
                        String address = "ERROR";
                        if (!"ERROR".equals(host) && !"ERROR".equals(port)) {
                            address = "//" + host + ":" + port + "/" + client;
                        }

                        msg += "\t" + bundleId + ", " + address;
                    } catch (Exception e) {
                        msg += "\tError";
                        ctx.error("When contacting '" + client + "': "
                                  +e.getMessage());
                    }
                }
                ctx.info(msg);
            }
        } finally {
            // Close connection
            if (connCtx != null) {
                cm.release(connCtx);
            }
        }
    }
}