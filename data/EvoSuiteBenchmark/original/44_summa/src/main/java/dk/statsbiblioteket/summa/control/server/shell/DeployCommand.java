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

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.rpc.RemoteHelper;
import dk.statsbiblioteket.summa.common.shell.Command;
import dk.statsbiblioteket.summa.common.shell.ShellContext;
import dk.statsbiblioteket.summa.control.api.ClientDeployer;
import dk.statsbiblioteket.summa.control.api.ControlConnection;
import dk.statsbiblioteket.summa.control.api.feedback.rmi.RemoteConsoleFeedback;
import dk.statsbiblioteket.summa.control.api.feedback.rmi.RemoteFeedback;
import dk.statsbiblioteket.summa.control.server.ControlUtils;
import dk.statsbiblioteket.util.qa.QAInfo;
import dk.statsbiblioteket.util.rpc.ConnectionContext;
import dk.statsbiblioteket.util.rpc.ConnectionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "mke")
public class DeployCommand extends Command {

    private Log log = LogFactory.getLog (DeployCommand.class);

    private ConnectionManager<ControlConnection> cm;
    private String controlAddress;
    private String hostname;
    private Configuration systemConf;

    public DeployCommand(ConnectionManager<ControlConnection> cm,
                         String controlAddress) {
        super("deploy", "Deploy a client bundle");

        setUsage("deploy [options] <bundle-id> <instance-id> [[user@]target-host[:port]]");

        installOption ("t", "transport", true,
                       "Which deployment transport to use. Allowed values are"
                       + " 'ssh' or 'local'. Default is ssh");

        installOption ("b", "basepath", true,
                       "What basepath to use for the client installation "
                       + "relative to the client user's home directory. "
                       + "Default is determined by the Control server");

        installOption ("c", "configuration", true,
                       "Url, RMI address or file path where the client can"
                       + " find its configuration. Default points at the "
                       + "Control configuration server");

        this.cm = cm;
        this.controlAddress = controlAddress;
        hostname = RemoteHelper.getHostname();
        systemConf = Configuration.getSystemConfiguration(true);
    }

    @Override
    public void invoke(ShellContext ctx) throws Exception {
        log.trace("invoke called");
        /* Extract and validate arguments */
        String[] args = getArguments();
        if (args.length < 2) {
            ctx.error("You must provide at least 2 arguments. Found "
                      + args.length);
            return;
        }
        String bundleId = args[0];
        String instanceId = args[1];
        String target = "localhost";

        if (args.length >= 3) {
            target = args[2];
        } else {
            ctx.info("No target host specified, defaulting to 'localhost´");
        }

        log.trace("invoke called with bundleId '" + bundleId
                  + "', instanceId '"
                  + instanceId + "' and target '" + target + "'");
        String transport = getOption("t") != null ? getOption("t") : "ssh";
        transport = ControlUtils.getDeployerClassName(transport);

        String basePath = getOption("b");
        String confLocation = getOption("c"); // This is allowed to be unset
                                    // - see ClientDeployer#CONF_CLIENT_CONF

        /* Set up a configuration for the deployment request */
        Configuration conf =
                Configuration.newMemoryBased(
                        ClientDeployer.CONF_DEPLOYER_BUNDLE,
                        bundleId,
                        ClientDeployer.CONF_INSTANCE_ID,
                        instanceId,
                        ClientDeployer.CONF_DEPLOYER_CLASS,
                        transport,
                        ClientDeployer.CONF_DEPLOYER_TARGET,
                        target);

        /* Check if we should configure a (remote) Feedback */
        RemoteConsoleFeedback remoteConsole = null;
        if (systemConf.valueExists(ClientDeployer.CONF_DEPLOYER_FEEDBACK)) {
            conf.set(ClientDeployer.CONF_DEPLOYER_FEEDBACK,
                     systemConf.getString(ClientDeployer.CONF_DEPLOYER_FEEDBACK));
            conf.set(RemoteFeedback.CONF_REGISTRY_HOST,
                     RemoteHelper.getHostname());
            conf.set(RemoteFeedback.CONF_REGISTRY_PORT,
                     systemConf.getInt(RemoteFeedback.CONF_REGISTRY_PORT,
                                       27000));
            conf.set(RemoteFeedback.CONF_SERVICE_NAME,
                     systemConf.getString(RemoteFeedback.CONF_SERVICE_NAME,
                                          "remoteConsole"));

            // FIXME: This is a hack - we shouldn't always create a
            // remote console, but otherwise the config would need to specify
            // both client- and server side feedback classes
            log.trace("invoke: Creating remoteConsole");
            remoteConsole = Configuration.create(RemoteConsoleFeedback.class,
                                                 conf);
        }

        if (confLocation != null){
 	        conf.set(ClientDeployer.CONF_CLIENT_CONF, confLocation);
        }
        if (basePath != null) {
            conf.set(ClientDeployer.CONF_BASEPATH, basePath);
        }

        log.trace ("Created deployment config:\n" + conf.dumpString());

        /* Connect to the Control and send the deployment request */
        ctx.prompt ("Deploying '" + instanceId + "' on '" + target + "' using "
                    + "transport '" + transport + "'... ");
        ConnectionContext<ControlConnection> connCtx = null;
        try {
            log.trace("invoke: Getting connCtx for controlAddress '"
                      + controlAddress + "'");
            connCtx = cm.get (controlAddress);
            if (connCtx == null) {
                ctx.error ("Failed to connect to Control server at '"
                           + controlAddress + "'");
                return;
            }

            log.trace("invoke: Getting control connection");
            ControlConnection control = connCtx.getConnection();
            log.trace("invoke: Calling deployClient");
            control.deployClient(conf);
            ctx.info ("OK");
        } finally {
            if (remoteConsole != null) {
                remoteConsole.close();
            }
            if (connCtx != null) {
                cm.release (connCtx);
            }
        }

    }
}




