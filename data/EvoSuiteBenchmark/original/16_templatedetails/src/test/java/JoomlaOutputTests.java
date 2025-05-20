/*
 * File: JoomlaOutputTests.java
 * 
 * Class: com.rakegroup.JoomlaOutputTests
 * 
 * Copyright 2009 Thomas W. Rake
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.rakegroup;
import org.apache.tools.ant.BuildFileTest;

public class JoomlaOutputTests extends BuildFileTest {

	public JoomlaOutputTests(String name) {
		super(name);
	}
	
	public void setUp() {
		configureProject("build.xml");
	}
	
	public void testReplacement() {
		executeTarget("test-substitution");
		assertOutputContaining("xyzzy");
	}
	
	public void testFileSet() {
		executeTarget("test-fileset");
		assertOutputContaining("build.xml");
		assertOutputContaining("JoomlaOutput.jar");
	}
	

	
	public void testErrorFileSet() {
		try {
			executeTarget("test-error-fileset");
			fail("No error: FileSet used in filename mode");
		} catch (Exception e) {
			String expected = "FileSet used in filename mode";
			assertEquals("Wrong exception messasge.", expected, e.getMessage());
		}
	}
	

}
