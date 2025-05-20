/*
 * (c) Copyright 2010-2011 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.jdbacl.model.xml;

import static org.junit.Assert.*;

import java.io.File;

import org.databene.commons.tree.TreeLogger;
import org.databene.jdbacl.model.AbstractModelTest;
import org.databene.jdbacl.model.DBTreeModel;
import org.databene.jdbacl.model.Database;
import org.junit.Test;

/**
 * Tests the {@link XMLModelImporter}.<br/><br/>
 * Created: 28.11.2010 18:23:12
 * @since 0.6.4
 * @author Volker Bergmann
 */
public class XMLModelImporterTest extends AbstractModelTest {

	@Test
	public void test() throws Exception {
		File file = new File("target/test-classes", TEST_MODEL_FILENAME);
		XMLModelImporter importer = new XMLModelImporter(file);
		Database actual = importer.importDatabase();
		new TreeLogger().log(new DBTreeModel(actual));
		Database expected = createTestModel();
		assertTrue(expected.isIdentical(actual));
	}
	
}
