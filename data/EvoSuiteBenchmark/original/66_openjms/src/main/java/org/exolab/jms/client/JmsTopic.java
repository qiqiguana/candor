/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 1999 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsTopic.java,v 1.1 2004/11/26 01:50:40 tanderson Exp $
 *
 * Date         Author  Changes
 * 3/21/2000    jima    Created
 */
package org.exolab.jms.client;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.StringTokenizer;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;


/**
 * A topic is a destination specific for the puiblish-subscribe messaging
 * model. OpenJMS also supports topic hierarchy and wild carding.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:40 $
 * @author      <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @author      <a href="mailto:mourikis@exoffice.com">Jim Mourikis</a>
 * @see         org.exolab.jms.client.JmsDestination
 **/
public class JmsTopic
    extends JmsDestination
    implements Topic, Externalizable, Referenceable {

    /**
     * Used for serialization
     */
    static final long serialVersionUID = 1;

    // The wildcard character.
    public static final String WILDCARD = "*";

    //Wilcard for this and subsequent levels
    public static final String ALL_WILDCARD = "**";

    // The separator character for each topic level.
    public static final String SEPARATOR = ".";


    /**
     * Need a default constructor for the serialization
     */
    public JmsTopic() {
    }

    /**
     * Instantiate an instance of this object with the specified string
     *
     * @param       name            name of the queue
     */
    public JmsTopic(String name) {
        super(name);
    }

    /**
     * Return the name of the topic
     *
     * @return      name        name of  the topic
     * @exception   JMSException
     */
    public String getTopicName()
        throws JMSException {
        return getName();
    }


    // implementation of Object.equals(Object)
    public boolean equals(Object object) {
        boolean result = false;

        if ((object instanceof JmsTopic) &&
            (((JmsTopic) object).getName().equals(this.getName()))) {
            result = true;
        }

        return result;
    }

    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput stream)
        throws IOException {
        stream.writeLong(serialVersionUID);
        super.writeExternal(stream);
    }

    // implementation of Externalizable.writeExternal
    public void readExternal(ObjectInput stream)
        throws IOException, ClassNotFoundException {
        long version = stream.readLong();
        if (version == serialVersionUID) {
            super.readExternal(stream);
        } else {
            throw new IOException("JmsTopic with version " +
                version + " is not supported.");
        }
    }

    // implementation of Object.hashCode
    public int hashCode() {
        return getName().hashCode();
    }

    /**
     * Check whether this topic represents a wildcard expression.
     *
     * @return boolean true if the topic contains wildcards
     */
    public boolean isWildCard() {
        return isWildCard(this.getName());
    }

    /**
     * A static method which checks a topic to determine whether or not it
     * complies to a wildcard definition.
     *
     * @param topic - the topic to check
     * @return boolean - true if it does
     */
    public static boolean isWildCard(String topic) {
        // if the topic contains the "**" wildcard ensure that it is
        // the last item, and no further tokens exist after it.
        int pos = topic.indexOf(ALL_WILDCARD);

        if (pos >= 0 && (pos != topic.length() - 2)) {
            return false;
        }

        pos = topic.indexOf(WILDCARD);

        // if we have any wildcards, tokenize them and ensure that a
        // "*" or "**" appear on their own with no characters mixed in.
        if (pos >= 0) {
            StringTokenizer tokens = new StringTokenizer(topic, SEPARATOR);
            String token = null;

            while (tokens.hasMoreTokens()) {
                token = tokens.nextToken();
                // contains a wildcard
                if (token.indexOf(WILDCARD) >= 0) {
                    if (!(token.equals(WILDCARD) ||
                        token.equals(ALL_WILDCARD))) {
                        return false;
                    }
                }
            }
        }

        return (pos >= 0);
    }

    /**
     * If it is a wildcard check to see that it matches the specified topic.
     *
     * if wildcard is **, its a an imediate match for every topic.
     * else
     *  tokenize both the wildcard and the topic.
     *  if the wildcard has less or equal no of tokens than the topic and
     *      ends in "**" check tokens
     *  if both wildcard and topic contain the same no of tokens check tokens
     *  Otherwise topic and wildcard do not match so return false.
     *
     * <P>Note we treat "a.b.c.*.*" and "a.b.c" as not a match at this stage,
     * since the wildcard is attempting to match more levels than exist in the
     * topic. if this proves to be unpopular with the masses, its a very
     * trivial change below to fix this problem.
     *
     * <P>Tokens are compared and must either be identical or the wildcard
     * token must be a "*" to match at this level. Once a mismatch is detected
     * the comparison is stopped and a false returned.
     *
     * <P>NOTE: This check assumes both the topic and wildcard topic have both
     * already been validated. if the topics are inavlid this test can return
     * arbitrary results.
     *
     * @param destination The specific topic to match to
     * @return True if the wildcard matches.
     */
    public boolean match(JmsTopic destination) {
        boolean matches = false;
        String topic = destination.getName();
        String wildcard = this.getName();
        if (wildcard.equals(ALL_WILDCARD)) {
            // Every topic match.
            matches = true;
        } else {
            StringTokenizer wildTokens =
                new StringTokenizer(wildcard, SEPARATOR);
            StringTokenizer topicTokens =
                new StringTokenizer(topic, SEPARATOR);
            String wildToken = null;
            String topicToken = null;
            int tokenCountDiff =
                topicTokens.countTokens() - wildTokens.countTokens();
            if ((tokenCountDiff == 0) ||
                (tokenCountDiff == -1) ||
                (tokenCountDiff > 0 && wildcard.indexOf(ALL_WILDCARD) >= 0)) {
                while (wildTokens.hasMoreTokens() &&
                    topicTokens.hasMoreTokens()) {
                    wildToken = wildTokens.nextToken();
                    topicToken = topicTokens.nextToken();
                    if (wildToken.equals(ALL_WILDCARD)) {
                        // we have a match.
                        matches = true;
                        break;
                    } else if (wildToken.equals(WILDCARD)) {
                        // this token matches.
                        matches = true;
                        continue;
                    } else if (wildToken.equals(topicToken)) {
                        // this token matches.
                        matches = true;
                        continue;
                    } else {
                        // no match. No point continuing further.
                        matches = false;
                        break;
                    }
                }
            }
        }

        return matches;
    }

    // implementation of Referenceable.getReference
    public Reference getReference() {
        Reference reference = null;

        // create the reference
        reference = new Reference(JmsTopic.class.getName(),
            new StringRefAddr("name", getName()),
            JmsDestinationFactory.class.getName(), null);

        // add the persistence attribute
        reference.add(new StringRefAddr("persistent",
            (getPersistent() ? "true" : "false")));

        return reference;
    }
}

