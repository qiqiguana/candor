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
package dk.statsbiblioteket.summa.common.filter.object;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.filter.Filter;
import dk.statsbiblioteket.summa.common.filter.Payload;
import dk.statsbiblioteket.summa.common.util.RecordUtil;
import dk.statsbiblioteket.util.xml.XMLUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * A {@link Filter} piping record content or XML format to {@code stdout}
 */
public class StdOutFilter extends ObjectFilterImpl {

    private static final Log log = LogFactory.getLog(StdOutFilter.class);

    /**
     * Optional property defining if only the raw record content is to be
     * dumped. By default (when this property is {@code false}) records will
     * be wrapped in an XML envelope with full record metadata.
     */
    public static final String CONF_CONTENT_ONLY = "summa.filter.stdout.contentonly";

    /**
     * Default value for the {@link #CONF_CONTENT_ONLY} property
     */
    public static final boolean DEFAULT_CONTENT_ONLY = false;

    /**
     * Optional property defining if the written record content should be
     * XML-escaped. This affects the output regardless how
     * {@link #CONF_CONTENT_ONLY} is set
     */
    public static final String CONF_ESCAPE_CONTENT = "summa.filter.stdout.escapecontent";

    /**
     * Default value for the {@link #CONF_ESCAPE_CONTENT} property
     */
    public static final boolean DEFAULT_ESCAPE_CONTENT = false;

    private boolean contentOnly;
    private boolean escapeContent;

    public StdOutFilter(Configuration conf) {
        super(conf);
        contentOnly = conf.getBoolean(CONF_CONTENT_ONLY, DEFAULT_CONTENT_ONLY);
        escapeContent = conf.getBoolean(CONF_ESCAPE_CONTENT, DEFAULT_ESCAPE_CONTENT);
    }

    @Override
    protected boolean processPayload(Payload payload) throws PayloadException {
        if (payload.getRecord() == null) {
            throw new PayloadException("Payload has no record", payload);
        }

        if (contentOnly) {
            String content = payload.getRecord().getContentAsUTF8();
            System.out.println(escapeContent ? XMLUtil.encode(content) : content);
        } else {
            try {
                System.out.println(RecordUtil.toXML(payload.getRecord(), escapeContent));
            } catch (IOException e) {
                throw new PayloadException(
                        "Error parsing record content for " + payload.getId() + ": " + e.getMessage(), e);
            }
        }
        return true;
    }
}

