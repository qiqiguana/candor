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

import dk.statsbiblioteket.summa.common.rpc.RemoteHelper;
import dk.statsbiblioteket.summa.common.shell.Command;
import dk.statsbiblioteket.summa.common.shell.ShellContext;
import dk.statsbiblioteket.summa.control.api.ClientConnection;
import dk.statsbiblioteket.summa.control.api.ControlConnection;
import dk.statsbiblioteket.summa.control.api.InvalidClientStateException;
import dk.statsbiblioteket.util.rpc.ConnectionContext;
import dk.statsbiblioteket.util.rpc.ConnectionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 *
 */
public class StopCommand extends Command {
    private Log log = LogFactory.getLog(StartCommand.class);

    private ConnectionManager<ControlConnection> cm;
    private String controlAddress;
    private String hostname;

    public StopCommand (ConnectionManager<ControlConnection> cm,
                         String controlAddress) {
        super ("stop", "Stop a client instance, halting its JVM and any " +
                       "running services");

        setUsage("stop <client-instance-id>");

        this.cm = cm;
        this.controlAddress = controlAddress;

        hostname = RemoteHelper.getHostname();
    }

    @Override
    public void invoke(ShellContext ctx) throws Exception {
        /* Extract and validate arguments */
        log.debug("Invoking StopCommand");
        String[] args = getArguments();
        if (args.length != 1) {
            ctx.error("You must provide exactly 1 argument. Found "
                      + args.length);
            return;
        }

        String instanceId = args[0];

        if (!isClientRunning(instanceId)) {
            ctx.info("Client '" + instanceId + "' is not running");
            return;
        }

        ConnectionContext<ControlConnection> connCtx = null;

        try {
            String msg = "Stopping client '" + instanceId +"' ... ";
            log.debug(msg);
            ctx.prompt(msg);

            connCtx = cm.get(controlAddress);
            if (connCtx == null) {
                ctx.error("Failed to connect to Control server at '"
                           + controlAddress + "'");
                return;
            }

            ControlConnection control = connCtx.getConnection();
            control.stopClient(instanceId);
            log.trace("Client '"+instanceId+"' stopped");
            ctx.info("OK");
        } finally {
            if (connCtx != null) {
                cm.release (connCtx);
            }
        }

    }

    private boolean isClientRunning(String instanceId) {
        ConnectionContext<ControlConnection> connCtx = cm.get(controlAddress);
        ControlConnection control = connCtx.getConnection();
        try {
            ClientConnection client = control.getClient(instanceId);

            if (client != null) {
                log.debug("Client is already running");
                return true;
            }
        } catch (InvalidClientStateException e){
            return false;
        } catch (IOException e) {
            throw new InvalidClientStateException(instanceId,
                                                  "Connection to '" + instanceId
                                                  + "' is broken: "
                                                  + e.getMessage(), e);
        } finally {
            connCtx.unref();
        }
        return false;
    }
}

