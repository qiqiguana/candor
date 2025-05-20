/*
 * Copyright (C) 2010-2011 VTT Technical Research Centre of Finland.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package fi.vtt.noen.mfw.unittests.agent.probe;

import fi.vtt.noen.mfw.bundle.common.Const;
import fi.vtt.noen.mfw.bundle.probe.shared.Probe;
import fi.vtt.noen.mfw.bundle.probe.shared.ProbeInformation;
import fi.vtt.noen.mfw.probes.ssh.SSHProbeAgent;
import fi.vtt.noen.mfw.probes.ssh.SSHProbeAgentActivator;
import fi.vtt.noen.mfw.unittests.MockBundleContext;
import fi.vtt.noen.mfw.unittests.TestUtils;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class SSHProbeTests {
  public void sendAndExecuteFile() {
    Properties props = new Properties();
    props.put(Const.PROBE_TARGET_NAME, "192.168.112.129");
    props.put(Const.PROBE_PRECISION, "1");
    props.put(Const.SSH_SCRIPT_FILENAME, "ssh_test_script");
    props.put(Const.SSH_SCRIPT_COMMAND, "bash");
    props.put(Const.SSH_USERNAME, "bob");
    props.put(Const.SSH_PASSWORD, "builder");
    props.put(Const.PROBE_BM_CLASS, "firewall");
    SSHProbeAgent ssh = new SSHProbeAgent();
    ssh.init(props);
    String measure = (String) ssh.measure().getMeasure();
    assertEquals("test file here\nline 2 of test file also here\n", measure);
  }

  @Test
  public void readConfiguration() {
    Properties props = new Properties();
    String targetName = "192.168.112.129";
    String targetType = "nice device";
    String bmClass = "firewall";
    String bmName = "bm name here";
    props.put(Const.PROBE_TARGET_NAME, targetName);
    props.put(Const.PROBE_TARGET_TYPE, targetType);
    props.put(Const.PROBE_PRECISION, "1");
    props.put(Const.SSH_SCRIPT_FILENAME, "ssh_test_script");
    props.put(Const.SSH_SCRIPT_COMMAND, "bash");
    props.put(Const.SSH_USERNAME, "bob");
    props.put(Const.SSH_PASSWORD, "builder");
    String probeDescription = "nice probe";
    props.put(Const.PROBE_NAME, probeDescription);
    props.put(Const.PROBE_BM_CLASS, bmClass);
    props.put(Const.PROBE_BM_NAME, bmName);
    SSHProbeAgent ssh = new SSHProbeAgent();
    ssh.init(props);
    ProbeInformation information = ssh.getInformation();
    assertEquals("Configured target type", targetType, information.getTargetType());
    assertEquals("Configured target name", targetName, information.getTargetName());
    assertEquals("Configured bm class", bmClass, information.getBmClass());
    assertEquals("Configured bm name", bmName, information.getBmName());
    assertEquals("Configured precision", 1, information.getPrecision());
    assertEquals("Configured description", probeDescription, information.getProbeName());
  }

  @Test
  public void registerSeveralSSHProbes() throws Exception {
    String configuration = "" +
            "server_agent_url=http://localhost:5555/xmlrpc\n" +
            "probe_agent_xmlrpc_port=5556\n" +
            "\n" +
            "ssh.probe1.target_name=192.168.112.129\n" +
            "ssh.probe1.target_type=linux server\n" +
            "ssh.probe1.precision=1\n" +
            "ssh.probe1.bm_name=echoecho\n" +
            "ssh.probe1.bm_class=example\n" +
            "ssh.probe1.description=SSH Example probe 1\n" +
            "\n" +
            "ssh.probe1.ssh_script_file=ssh_test_script\n" +
            "ssh.probe1.ssh_command=bash\n" +
            "ssh.probe1.ssh_username=bob\n" +
            "ssh.probe1.ssh_password=builder\n" +
            "\n" +
            "ssh.probe2.target_name=192.168.112.129\n" +
            "ssh.probe2.target_type=linux server\n" +
            "ssh.probe2.precision=1\n" +
            "ssh.probe2.bm_name=echoecho2\n" +
            "ssh.probe2.bm_class=example2\n" +
            "ssh.probe2.description=SSH Example probe 2\n" +
            "\n" +
            "ssh.probe2.ssh_script_file=ssh_test_script2\n" +
            "ssh.probe2.ssh_command=bash\n" +
            "ssh.probe2.ssh_username=bob\n" +
            "ssh.probe2.ssh_password=builder\n" +
            "";
    Properties props = new Properties();
    props.load(TestUtils.streamFor(configuration));
    SSHProbeAgentActivator sa = new SSHProbeAgentActivator(props);
    MockBundleContext bc = new MockBundleContext();
    sa.start(bc);
    Map<Integer,Probe> probes = sa.getProbes();
    Probe probe1 = probes.get(1);
    assertNotNull("Probe1 should be loaded and initialized", probe1);
    ProbeInformation pi = probe1.getInformation();
    assertEquals("Probe1 target name", "192.168.112.129", pi.getTargetName());
    assertEquals("Probe1 target type", "linux server", pi.getTargetType());
    assertEquals("Probe1 bm name", "echoecho", pi.getBmName());
    assertEquals("Probe1 bm class", "example", pi.getBmClass());
    assertEquals("Probe1 description", "SSH Example probe 1", pi.getProbeName());

    Probe probe2 = probes.get(2);
    pi = probe2.getInformation();
    assertNotNull("Probe2 should be loaded and initialized", probe2);
    assertEquals("Probe2 target name", "192.168.112.129", pi.getTargetName());
    assertEquals("Probe2 target type", "linux server", pi.getTargetType());
    assertEquals("Probe2 bm name", "echoecho2", pi.getBmName());
    assertEquals("Probe2 bm class", "example2", pi.getBmClass());
    assertEquals("Probe2 description", "SSH Example probe 2", pi.getProbeName());
  }
}
