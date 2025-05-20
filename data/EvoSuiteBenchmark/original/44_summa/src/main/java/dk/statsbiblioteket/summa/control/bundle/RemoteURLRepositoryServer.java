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
package dk.statsbiblioteket.summa.control.bundle;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.rpc.RemoteHelper;
import dk.statsbiblioteket.summa.control.api.bundle.WritableBundleRepository;
import dk.statsbiblioteket.summa.control.api.bundle.rmi.RemoteRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * A {@link dk.statsbiblioteket.summa.control.api.bundle.BundleRepository}
 * exposing an RMI interface. It wraps a child {@link URLRepository} storing
 * the bundles.
 */
public class RemoteURLRepositoryServer extends UnicastRemoteObject
                                       implements RemoteRepository,
                                                  WritableBundleRepository {
    private static final long serialVersionUID = 1683681887L;
    private static final Log log =
                             LogFactory.getLog(RemoteURLRepositoryServer.class);
    private WritableBundleRepository localRepo;

    /**
     * Configuraion property defining the port on which RMI communications
     * with this repo should proceed. The default is 27045.
     */
    public static final String CONF_SERVICE_PORT =
                                        "summa.control.repository.service.port";

    public static final int DEFAULT_SERVICE_PORT = 27045;

    /**
     * Configuraion property defining the name which the repository should
     * register itself on the RMI registry with. The default is
     * {@code remoteURLRepository}
     */
    public static final String CONF_SERVICE_NAME =
                                        "summa.control.repository.service.name";

    public static final String DEFAULT_SERVICE_NAME = "remoteURLRepository";

    /**
     * Configuraion property defining the port on which RMI registry should run
     * or can be found. The default is 27000.
     */
    public static final String CONF_REGISTRY_PORT =
                                          "summa.control.repository.registry.port";

    public static final int DEFAULT_REGISTRY_PORT = 27000;

    /**
     * Create a new {@code RemoteURLRepository}. The passed in configuration
     * will be passed unmodified to an underlying {@link LocalURLRepository}.
     * <p></p>
     * The {@link #CONF_REPO_ADDRESS} will be passed down to an embedded
     *  
     *
     * @param conf configuration from which to read properties. This
     *             configuration will also be passed directly to the underlying
     *             {@link URLRepository}
     * @throws IOException if there is an error exporting the remote interface
     */
    public RemoteURLRepositoryServer (Configuration conf) throws IOException {
        super (getServicePort(conf));

        log.debug ("Creating RemoteURLRepositoryServer");

        localRepo = new LocalURLRepository(conf);

        log.debug ("Exporting remote interfaces");

        BundleUtils.prepareCodeBase(conf, this,
                                    "summa-common", "summa-control-api");        
        RemoteHelper.exportRemoteInterface(this,
                                           conf.getInt(CONF_REGISTRY_PORT,
                                                       DEFAULT_REGISTRY_PORT),
                                           conf.getString(CONF_SERVICE_NAME,
                                                          DEFAULT_SERVICE_NAME));
    }

    private static int getServicePort(Configuration conf) {
        return conf.getInt (CONF_SERVICE_PORT, 27045);
    }

    /**
     * Warning: This method returns the File object for the 'local' file
     * on the server side. This works if you are the Control server.
     * However the  {@link RemoteURLRepositoryClient} does not invoke
     * this method, but bypasses it with a {@link URLRepository#get}
     */
    @Override
    public File get(String bundleId) throws RemoteException {
        try {
            return localRepo.get(bundleId);
        } catch (IOException e) {
            throw new RemoteException("Error getting bundle file for '"
                                       + bundleId + "'", e);
        }
    }

    @Override
    public List<String> list(String regex) throws RemoteException {
        log.trace ("Got list() request: '" + regex + "'");
        try {
            return localRepo.list (regex);
        } catch (IOException e) {
            throw new RemoteException("Failed to list local repository: "
                                      + e.getMessage(), e);
        }
    }

     @Override
     public String expandApiUrl (String jarFileName) throws RemoteException {
        try {
            return localRepo.expandApiUrl(jarFileName);
        } catch (IOException e) {
            throw new RemoteException("Failed to expand API URL for '"
                                      + jarFileName + "'", e);
        }
    }

    // Note that this method is intentionally not exported over rmi
    // since the RemoteRepository interface does not declare it
    @Override
    public boolean installBundle(File bundle) throws IOException {
        return localRepo.installBundle(bundle);
    }

    // Note that this method is intentionally not exported over rmi
    // since the RemoteRepository interface does not declare it
    @Override
    public boolean installApi(File apiFile) throws IOException {
        return localRepo.installApi(apiFile);
    }
}




