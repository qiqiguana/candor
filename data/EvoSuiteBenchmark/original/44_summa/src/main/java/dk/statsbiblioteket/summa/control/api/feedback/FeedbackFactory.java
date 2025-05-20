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
package dk.statsbiblioteket.summa.control.api.feedback;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.control.api.ClientDeployer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Helper class used to instantiate {@link Feedback} objects of the right type.
 */
public class FeedbackFactory {

    private static final Log log = LogFactory.getLog(FeedbackFactory.class);

    /**
     * Instantiate a new {@link Feedback} of the class defined in
     * {@link dk.statsbiblioteket.summa.control.api.ClientDeployer#CONF_DEPLOYER_FEEDBACK}. If this property
     * is not defined the factory will default to a {@link ConsoleFeedback}.
     *
     * @param conf the configuration used to look up the value of the
     *             {@link dk.statsbiblioteket.summa.control.api.ClientDeployer#CONF_DEPLOYER_FEEDBACK} property
     * @return a newly created {@code Feedvback} instance
     */
    public static Feedback createFeedback(Configuration conf) {
        log.debug("Creating deployer feedback from class: "
                  + conf.getString(ClientDeployer.CONF_DEPLOYER_FEEDBACK, VoidFeedback.class.getSimpleName()));
        Class<? extends Feedback> feedbackClass = conf.getClass(
                ClientDeployer.CONF_DEPLOYER_FEEDBACK, Feedback.class, VoidFeedback.class);

        return Configuration.create(feedbackClass, conf);
    }
}




