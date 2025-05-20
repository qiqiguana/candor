package org.asphodel.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.Token;

import java.io.Reader;
import java.io.IOException;
import java.nio.CharBuffer;

/**
 * @author : Sun Wenju
 *         Date: Jan 30, 2008 2:15:42 PM
 */
public class SimpleAnalyzer extends Analyzer {
    public TokenStream tokenStream(String fieldName, Reader reader) {
        System.out.print("----");
        return new SimpleTokenizer(reader);
    }


    class SimpleTokenizer extends Tokenizer {
        private int MAX_WORD_LENGTH = 255;

        private int startOffset = 0;

        private int bufferIndex = 0;
        private int dataLength = 0;

        private char[] charBuffer = new char[MAX_WORD_LENGTH];

        public SimpleTokenizer(Reader reader) {
            super(reader);
        }

        /*word by word*/
        public Token next() throws IOException {
            Token token = null;
            int start = startOffset;
            int length = 0;

            while (true) {
                startOffset++;

                char c = 0;

                if (bufferIndex >= dataLength) {
                    dataLength = input.read(charBuffer);
                }

                length = bufferIndex;
                c = charBuffer[bufferIndex++];

//                System.out.print(c);
                if (c == ' ')
                    break;
            }

/* todo this only works with higher version

            token = new Token();
            token.setTermText(new String(charBuffer, 0, length));
            token.setTermLength(length);
            token.setStartOffset(start);
            token.setEndOffset(start + length);
            token.setType("word");
*/

            return token;
        }
    }
}
