package macaw.persistenceLayer;

import macaw.businessLayer.*;
import macaw.system.*;

import java.util.ArrayList;


/**
 * Provides a collection of fake records that can be used to initially populate
 * demonstration implementations of 
 * {@link macaw.businessLayer.MacawCurationAPI} and {@link macaw.businessLayer.MacawRetrievalAPI}.
 * <hr>
 * Copyright 2010 Medical Research Council Unit for Lifelong Health and Ageing
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  
 * <hr>
 * @author Kevin Garwood (kgarwood@users.sourceforge.net)
 * @version 1.0	
 */

/*
 * Code Road Map:
 * --------------
 * Code is organised into the following sections.  Wherever possible, 
 * methods are classified based on an order of precedence described in 
 * parentheses (..).  For example, if you're trying to find a method 
 * 'getName(...)' that is both an interface method and an accessor 
 * method, the order tells you it should appear under interface.
 * 
 * Order of 
 * Precedence     Section
 * ==========     ======
 * (1)            Section Constants
 * (2)            Section Properties
 * (3)            Section Construction
 * (7)            Section Accessors and Mutators
 * (6)            Section Errors and Validation
 * (5)            Section Interfaces
 * (4)            Section Overload
 *
 */

public class DummyDataProvider {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private MacawCurationAPI database;
	private User admin;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public DummyDataProvider(MacawCurationAPI database,
							 User admin) {
		this.database = database;
		this.admin = admin;
	}
		
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	public void populateDatabase(User user) throws MacawException {
		Category category1 = new Category("Unknown");
		database.addCategory(user, category1);
		Category category2 = new Category("Contact");
		database.addCategory(user, category2);
		Category category3 = new Category("Anthropometry");
		database.addCategory(user, category3);
		Category category4 = new Category("General Health");
		database.addCategory(user, category4);
		Category category5 = new Category("Wellbeing");
		database.addCategory(user, category5);
		Category category6 = new Category("Respiratory Health");
		database.addCategory(user, category6);
		Category category7 = new Category("Cardiovascular Health");
		database.addCategory(user, category7);
		Category category8 = new Category("Musculoskeletal Health and Physical Capability");
		database.addCategory(user, category8);
		Category category9 = new Category("Mental Health");
		database.addCategory(user, category9);
		Category category10 = new Category("Women's Health");
		database.addCategory(user, category10);
		Category category11 = new Category("Hospital Admission");
		database.addCategory(user, category11);
		Category category12 = new Category("Medication");
		database.addCategory(user, category12);
		Category category13 = new Category("Other Health Care");
		database.addCategory(user, category13);
		Category category14 = new Category("Mortality");
		database.addCategory(user, category14);
		Category category15 = new Category("Cognitive Function");
		database.addCategory(user, category15);
		Category category16 = new Category("Temperament/Personality");
		database.addCategory(user, category16);
		Category category17 = new Category("Nutrition");
		database.addCategory(user, category17);
		Category category18 = new Category("Other Health Behaviours");
		database.addCategory(user, category18);
		Category category19 = new Category("Puberty/Fertility History");
		database.addCategory(user, category19);
		Category category20 = new Category("Marital History");
		database.addCategory(user, category20);
		Category category21 = new Category("Household Structure");
		database.addCategory(user, category21);
		Category category22 = new Category("Education");
		database.addCategory(user, category22);
		Category category23 = new Category("Social Class");
		database.addCategory(user, category23);
		Category category24 = new Category("Employment History");
		database.addCategory(user, category24);
		Category category25 = new Category("Other Socioeconomic Circumstances");
		database.addCategory(user, category25);
		Category category26 = new Category("Social Support/Life Events");
		database.addCategory(user, category26);
		Category category27 = new Category("DNA Samples");
		database.addCategory(user, category27);
		Category category28 = new Category("Blood and Urine Samples");
		database.addCategory(user, category28);

		CleaningState cleaningState1 = new CleaningState("Unknown");
		database.addCleaningState(user, cleaningState1);
		CleaningState cleaningState2 = new CleaningState("Recommended variable Grade A");
		database.addCleaningState(user, cleaningState2);
		CleaningState cleaningState3 = new CleaningState("Variable Grade B");
		database.addCleaningState(user, cleaningState3);
		CleaningState cleaningState4 = new CleaningState("Not Fully Checked");
		database.addCleaningState(user, cleaningState4);

		AvailabilityState availabilityState1
			= new AvailabilityState("Unknown");
		database.addAvailabilityState(user, availabilityState1);
		AvailabilityState availabilityState2
			= new AvailabilityState("Facilitated collaboration; New clinic data");
		database.addAvailabilityState(user, availabilityState2);
		AvailabilityState availabilityState3
			= new AvailabilityState("Facilitated collaboration; Other specialist databases");
		database.addAvailabilityState(user, availabilityState3);
		AvailabilityState availabilityState4
			= new AvailabilityState("Facilitated collaboration; Key outcomes for LHA programmes");
		database.addAvailabilityState(user, availabilityState4);
		AvailabilityState availabilityState5
			= new AvailabilityState("Facilitated collaboration; Other biomedical data TBA");
		database.addAvailabilityState(user, availabilityState5);
		AvailabilityState availabilityState6
			= new AvailabilityState("On-site analysis");
		database.addAvailabilityState(user, availabilityState6);
		AvailabilityState availabilityState7
			= new AvailabilityState("Not available; death details of person/parent");
		database.addAvailabilityState(user, availabilityState7);
		AvailabilityState availabilityState8
			= new AvailabilityState("Not available; geographic variables");
		database.addAvailabilityState(user, availabilityState8);
		AvailabilityState availabilityState9
			= new AvailabilityState("Other disclosive sets of data");
		database.addAvailabilityState(user, availabilityState9);
		AvailabilityState availabilityState10
			= new AvailabilityState("Other sensitive data");
		database.addAvailabilityState(user, availabilityState10);
		AvailabilityState availabilityState11
			= new AvailabilityState("Unrestricted");
		database.addAvailabilityState(user, availabilityState11);

		AliasFilePath aliasFilePath1 
			= new AliasFilePath("b01",
								"$lb/b01/c.dat");
		database.addAliasFilePath(user, aliasFilePath1);
		AliasFilePath aliasFilePath2 
			= new AliasFilePath("c02",
								"$lb/c02/c.dat");
		database.addAliasFilePath(user, aliasFilePath2);
		AliasFilePath aliasFilePath3 
			= new AliasFilePath("dr04",
								"$lb/dr04/dr04.dat");
		database.addAliasFilePath(user, aliasFilePath3);
		AliasFilePath aliasFilePath4
			= new AliasFilePath("gr2",
								"$fav/gr2/bl.dat");
		database.addAliasFilePath(user, aliasFilePath4);

		DerivedVariable bmi61 = new DerivedVariable();
		bmi61.setCategory("Anthropometry");
		bmi61.setName("BMI-61");
		bmi61.setLabel("Body Mass Index 1961");
		bmi61.setCoded(true);
		bmi61.setAvailability("On-site analysis");
		bmi61.setAlias("b01");
		bmi61.setFilePath("$der/final/45pi.sv");
		bmi61.setCleaned(true);
		bmi61.setCleaningStatus("Variable Grade B");
		bmi61.setYear("1961");
		bmi61.setCleaningDescription("aaa");
		
		database.addDerivedVariable(user, bmi61);

		SupportingDocument supportingDocument1 = new SupportingDocument();
		supportingDocument1.setDocumentCode("LHA-doc1113");
		supportingDocument1.setFileName("ABC.txt");
		supportingDocument1.setFilePath("./myDirectory");
		supportingDocument1.setTitle("Body Mass Index Calculations");
		database.addSupportingDocument(user, supportingDocument1);
		int documentID1 =
				database.getSupportingDocumentIdentifier(user, supportingDocument1);
		supportingDocument1.setIdentifier(documentID1);
		
		ArrayList<SupportingDocument> supportingDocuments1 = new ArrayList<SupportingDocument>();
		supportingDocuments1.add(supportingDocument1);
		database.associateSupportingDocumentsWithVariable(user, bmi61, supportingDocuments1);
	
		SupportingDocument supportingDocument2 = new SupportingDocument();
		supportingDocument2.setDocumentCode("LHA-doc1112");
		supportingDocument2.setFileName("BMI-2009a.txt");
		supportingDocument2.setFilePath("./myDirectory");
		supportingDocument2.setTitle("Practical Guidance for BMI calculations");
		database.addSupportingDocument(user, supportingDocument2);
		int documentID2 =
				database.getSupportingDocumentIdentifier(user, supportingDocument2);
		supportingDocument2.setIdentifier(documentID2);

		ArrayList<SupportingDocument> supportingDocuments2 = new ArrayList<SupportingDocument>();
		supportingDocuments2.add(supportingDocument2);		
		database.associateSupportingDocumentsWithVariable(user,bmi61, supportingDocuments2);
	
		ArrayList<Variable> sourceVariables = new ArrayList<Variable>();

		RawVariable height = new RawVariable();
		height.setName("HT61");
		height.setCategory("Anthropometry");
		height.setCleaned(true);
		height.setCoded(true);
		height.setCleaningStatus("Recommended variable Grade A");
		height.setAvailability("Unrestricted");
		height.setLabel("height at the age of 15");
		height.setYear("1961");
		height.setForm("Form A");
		height.setQuestionNumber("34");
		height.setAlias("c02");
		height.setFilePath("$lb/d22/d.sv");
		height.setCodeBookNumber("5");
		height.setColumnStart("10");
		height.setColumnEnd("20");
						
		database.addRawVariable(user, height);		
		sourceVariables.add(height);

		ArrayList<OntologyTerm> termList1 = new ArrayList<OntologyTerm>();
		OntologyTerm ontologyTerm1 = new OntologyTerm();
		ontologyTerm1.setTerm("Dimension");
		ontologyTerm1.setDescription("A big word for size");
		termList1.add(ontologyTerm1);
		database.addOntologyTerm(user, ontologyTerm1);
		int ontologyTermID1 
			= database.getOntologyTermIdentifier(user, ontologyTerm1);
		ontologyTerm1.setIdentifier(ontologyTermID1);
				
		OntologyTerm ontologyTerm2 = new OntologyTerm();
		ontologyTerm2.setTerm("Physical Dimension");
		ontologyTerm2.setDescription("a vertical measurement");
		termList1.add(ontologyTerm2);
		database.addOntologyTerm(user, ontologyTerm2);
		int ontologyTermID2 
			= database.getOntologyTermIdentifier(user, ontologyTerm2);
		ontologyTerm2.setIdentifier(ontologyTermID2);
		
		OntologyTerm ontologyTerm3 = new OntologyTerm();
		ontologyTerm3.setTerm("Metres");
		ontologyTerm3.setDescription("unit of measurement");
		termList1.add(ontologyTerm3);
		database.addOntologyTerm(user, ontologyTerm3);
		int ontologyTermID3
			= database.getOntologyTermIdentifier(user, ontologyTerm3);
		ontologyTerm3.setIdentifier(ontologyTermID3);

		
		database.associateOntologyTermsWithVariable(user, 
									   	   			height, 
									   	   			termList1);
	
		RawVariable mass = new RawVariable();
		mass.setName("WT61");
		mass.setCategory("Anthropometry");
		mass.setCleaned(false);
		mass.setCoded(true);
		mass.setAvailability("On-site analysis");

		mass.setLabel("weight at the age of 15");
		mass.setYear("1961");
		mass.setCoded(true);
		mass.setForm("Form B");
		mass.setQuestionNumber("32");
		mass.setAlias("dr04");
		mass.setFilePath("$lb/d09/c.sv");
		mass.setCodeBookNumber("7");
		mass.setColumnStart("20");
		mass.setColumnEnd("30");
		
		database.addRawVariable(user, mass);
		sourceVariables.add(mass);

		ArrayList<OntologyTerm> termList2 = new ArrayList<OntologyTerm>();
		OntologyTerm ontologyTerm4 = new OntologyTerm();
		ontologyTerm4.setTerm("Mass");
		ontologyTerm4.setDescription("The mass of a person in kilograms");
		
		termList2.add(ontologyTerm4);
		database.addOntologyTerm(user, ontologyTerm4);
		int ontologyTermID4
			= database.getOntologyTermIdentifier(user, ontologyTerm4);
		ontologyTerm4.setIdentifier(ontologyTermID4);
	
		termList2.add(ontologyTerm2);
		OntologyTerm ontologyTerm6 = new OntologyTerm();
		ontologyTerm6.setTerm("kilograms");
		ontologyTerm6.setDescription("a unit of measurement for mass.");
		termList2.add(ontologyTerm6);
		database.addOntologyTerm(user, ontologyTerm6);
		int ontologyTermID6
			= database.getOntologyTermIdentifier(user, ontologyTerm6);
		ontologyTerm6.setIdentifier(ontologyTermID6);
		
		database.associateSourceVariables(user, 
							 	 		  bmi61, 
							 	 		  sourceVariables);

		database.associateOntologyTermsWithVariable(user, 
													mass, 
													termList2);
	
		ArrayList<ValueLabel> valueLabelsForBMI61 = new ArrayList<ValueLabel>();
		ValueLabel label1 = new ValueLabel();
		label1.setValue("1");
		label1.setLabel("Male");
		label1.setMissingValue(false);
		valueLabelsForBMI61.add(label1);

		ValueLabel label2 = new ValueLabel();
		label2.setValue("2");
		label2.setLabel("Female");
		label2.setMissingValue(false);
		valueLabelsForBMI61.add(label2);

		ValueLabel label3 = new ValueLabel();
		label3.setValue("3");
		label3.setLabel("Hermaphrodite");
		label3.setMissingValue(false);
		valueLabelsForBMI61.add(label3);

		ValueLabel label4 = new ValueLabel();
		label4.setValue("4");
		label4.setLabel("Unknown");
		label4.setMissingValue(true);
		valueLabelsForBMI61.add(label4);

		database.addValueLabels(user, bmi61, valueLabelsForBMI61);

		DerivedVariable xyz82 = new DerivedVariable();
		xyz82.setCategory("Anthropometry");
		xyz82.setName("XYZ-82");
		xyz82.setLabel("Measure of XYZ in 1982");
		xyz82.setCoded(true);
		xyz82.setAvailability("On-site analysis");
		xyz82.setYear("1982");
		xyz82.setAlias("gr2");
		xyz82.setFilePath("$lb/d33/fff.sv");
		xyz82.setCleaned(true);
		xyz82.setCleaningStatus("Variable Grade B");
		xyz82.setCleaningDescription("aaa");
		database.addDerivedVariable(user, xyz82);
			
		//now clear the changeEvents
		database.clearAllChanges(admin);			
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================

}

