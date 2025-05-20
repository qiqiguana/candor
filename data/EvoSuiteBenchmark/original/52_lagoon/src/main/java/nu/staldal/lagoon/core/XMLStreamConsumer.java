/*
 * Copyright (c) 2001-2004, Mikael Ståldal
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 * notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimer in the 
 * documentation and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the author nor the names of its contributors 
 * may be used to endorse or promote products derived from this software 
 * without specific prior written permission. 
 * 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR 
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * 
 * Note: This is known as "the modified BSD license". It's an approved 
 * Open Source and Free Software license, see 
 * http://www.opensource.org/licenses/ 
 * and
 * http://www.gnu.org/philosophy/license-list.html
 */

package nu.staldal.lagoon.core;

/** 
 * A Procucer that consumes an XML stream.
 */
public abstract class XMLStreamConsumer extends Producer
{
    // Associations
    private XMLStreamProducer nextProducer;

    void doDestroy()
        throws java.io.IOException        
    {
        ProducerInterface prod = getNext();
        if (prod instanceof Producer)
        {
            ((Producer)prod).doDestroy();    
        }
        destroy();
    }

    void doBeforeBuild()
        throws java.io.IOException        
    {
        ProducerInterface prod = getNext();
        if (prod instanceof Producer)
        {
            ((Producer)prod).doBeforeBuild();    
        }
        beforeBuild();
    }


    void doAfterBuild()
        throws java.io.IOException        
    {
        ProducerInterface prod = getNext();
        if (prod instanceof Producer)
        {
            ((Producer)prod).doAfterBuild();    
        }
        afterBuild();
    }
    
    
    /** 
     * Set the next producer. 
     * Used during initialization.
     * 
     * @param next  the next Producer
     */
    void setNext(XMLStreamProducer next)
    {
        nextProducer = next;
    }

    /** 
     * @return  the next upstream producer
     */
    public XMLStreamProducer getNext()
    {
        return nextProducer;
    }        
    
}
