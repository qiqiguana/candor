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
package dk.statsbiblioteket.summa.control.service;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.control.api.Service;
import dk.statsbiblioteket.summa.control.api.Status;
import junit.framework.TestCase;

import java.io.Serializable;

/**
 * Test cases for {@link ScriptService}
 */
public class ScriptServiceTest extends TestCase {

    Service service;
    Configuration conf;

    @Override
    public void setUp() {
        System.setProperty("summa.control.service.id", "testService");
    }

    @Override
    public void tearDown() {
        System.clearProperty("summa.control.service.id");
    }

    public static Service createScriptService(Serializable... args) {
        Configuration conf = Configuration.newMemoryBased(args);
        return Configuration.create(ScriptService.class, conf);
    }

    public void testEmptyInlineScript() throws Exception {
        service = createScriptService(ScriptService.CONF_SCRIPT_INLINE,
                                      "");
        assertEquals(Status.CODE.constructed, service.getStatus().getCode());
        service.start();
        assertNotSame(Status.CODE.constructed, service.getStatus().getCode());
        assertNotSame(Status.CODE.crashed, service.getStatus().getCode());

        service.stop();
        Thread.sleep(400); // Give a little bit o' grace time to change state
        assertEquals(Status.CODE.stopped, service.getStatus().getCode());
    }

    public void testSimpleInlineScript() throws Exception {
        service = createScriptService(ScriptService.CONF_SCRIPT_INLINE,
                                      "2 + 2");
        assertEquals(Status.CODE.constructed, service.getStatus().getCode());
        service.start();
        assertNotSame(Status.CODE.constructed, service.getStatus().getCode());
        assertNotSame(Status.CODE.crashed, service.getStatus().getCode());

        service.stop();
        Thread.sleep(400); // Give a little bit o' grace time to change state
        assertEquals(Status.CODE.stopped, service.getStatus().getCode());
    }

    public static void doTestBlockingScript(Service service)
                                                             throws Exception {
        assertEquals(Status.CODE.constructed, service.getStatus().getCode());
        service.start();
        Thread.sleep(200); // Let us sleep past the intermediate 'running' state
        assertEquals(Status.CODE.idle, service.getStatus().getCode());

        // Assert that the scrip indeed seems to be blocking
        Thread.sleep(3000);
        assertEquals(Status.CODE.idle, service.getStatus().getCode());

        service.stop();
        Thread.sleep(400); // Give a little bit o' grace time to change state
        assertEquals(Status.CODE.stopped, service.getStatus().getCode());
    }

    public void testInlineBlockingScript() throws Exception {
        service = createScriptService(ScriptService.CONF_SCRIPT_INLINE,
                                      "while (!stopped) {             \n" +
                                      "  java.lang.Thread.sleep(100); \n" +
                                      "  log.debug(\"Weeee - again!\");" +
                                      "}");
        doTestBlockingScript(service);
    }

    public void testExternalBlockingScript() {
        service = createScriptService(ScriptService.CONF_SCRIPT_URL,
                                      "control/script-service-test.js");
        try {
            doTestBlockingScript(service);
        } catch(Exception e) {
            fail("No exception is expected");
        }
    }
}