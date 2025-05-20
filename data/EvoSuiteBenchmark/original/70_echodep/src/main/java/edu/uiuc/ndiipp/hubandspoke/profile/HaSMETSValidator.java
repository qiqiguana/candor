/**
 * HaSMETSValidator.java
 * 
 * $Revision: 846 $
 * 
 * $Date: 2009-04-22 18:45:17 +0100 (Wed, 22 Apr 2009) $
 * 
 * Copyright (c) 2009, University Library, University of Illinois at 
 * Urbana-Champaign. All rights reserved. 
 * 
 * Developed by: The Hub and Spoke Project Group 
 *               University of Illinois Urbana-Champaign Library
 *               http://dli.grainger.uiuc.edu/echodep/hands/ 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to 
 * the following conditions: 
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimers. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimers in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the names of The Hub and Spoke Project Group, University of 
 * Illinois Urbana-Champaign Library, nor the names of its contributors may 
 * be used to endorse or promote products derived from this Software 
 * without specific prior written permission. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE. 
 */

package edu.uiuc.ndiipp.hubandspoke.profile;

import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PackageType;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSChecksummer;
import gov.loc.mets.MetsAmdSecType;
import gov.loc.mets.MetsDivType;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsMetsDocument;
import gov.loc.mets.MetsMetsType;
import gov.loc.mets.MetsStructLinkType;
import gov.loc.mets.MetsStructMapType;
import gov.loc.mods.ModsAccessConditionType;
import gov.loc.mods.ModsBaseTitleInfoType;
import gov.loc.mods.ModsClassificationType;
import gov.loc.mods.ModsCodeOrText;
import gov.loc.mods.ModsDateType;
import gov.loc.mods.ModsGenreType;
import gov.loc.mods.ModsIdentifierType;
import gov.loc.mods.ModsLanguageType;
import gov.loc.mods.ModsLocationType;
import gov.loc.mods.ModsModsDocument;
import gov.loc.mods.ModsModsType;
import gov.loc.mods.ModsNameType;
import gov.loc.mods.ModsOriginInfoType;
import gov.loc.mods.ModsPhysicalDescriptionType;
import gov.loc.mods.ModsPlaceTermType;
import gov.loc.mods.ModsPlaceType;
import gov.loc.mods.ModsRecordInfoType;
import gov.loc.mods.ModsRelatedItemType;
import gov.loc.mods.ModsSubjectType;
import gov.loc.mods.ModsUrlType;
import gov.loc.premis.PremisEventDocument;
import gov.loc.premis.PremisObjectDocument;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.zip.CheckedInputStream;

import javax.xml.namespace.QName;


import org.apache.log4j.Logger;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlHexBinary;
import org.apache.xmlbeans.XmlIDREF;
import org.apache.xmlbeans.XmlIDREFS;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlString;
import org.w3c.dom.Node;

/**
 * Class with methods used to validate whether a METS
 * document conforms to the Hub and Spoke METS profile or not
 * 
 * @author thabing
 *
 */

public class HaSMETSValidator {
	//log4j logger is used for all validation errors
	static Logger logger = Logger.getLogger(HaSMETSValidator.class);
	private static HaSMETSAppender VALIDATION_APPENDER = null;
	/**
	 * Validate the primary dmdSec MODS against the Aquifer MODS profile
	 * @param metsP
	 * @param pt
	 * @param modsDoc
	 */
	public boolean validateAquiferModsDocument(HaSMETSProfile metsP,PackageType pt,
			ModsModsDocument modsDoc) {
		HaSMETSAppender app = new HaSMETSAppender("ValidateAquiferModsDocument");
		logger.addAppender(app);
		
		//TODO:  This needs some more with respect to language codes

		// MODS does not use any IDREF or IDREFS attributes, so you should be
		// able to safely
		// validate it when it is detached from the surrounding context
		XmlOptions validateOptions = new XmlOptions();
		ArrayList<XmlError> errorList = new ArrayList<XmlError>();
		validateOptions.setErrorListener(errorList);
		if(!modsDoc.validate(validateOptions)){
			for (int i = 0; i < errorList.size(); i++) {
				if (errorList.get(i).getSeverity() == XmlError.SEVERITY_ERROR)
					logger.error(errorList.get(i));
				else if (errorList.get(i).getSeverity() == XmlError.SEVERITY_WARNING)
					logger.warn(errorList.get(i));
				else
					logger.info(errorList.get(i));
			}
		} else {

			ModsModsType mods = modsDoc.getMods();
			ModsBaseTitleInfoType mtitlei[] = mods.getTitleInfoArray();
			if (mtitlei == null || mtitlei.length == 0) {
				logger.error(XmlError.forObject(
						"The PRIMARY_DMDSEC is missing a titleInfo element", mods));
			}
			for (int i = 0; i < mtitlei.length; i++) {
				if (mtitlei[i].getTitleArray().length == 0) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC titleInfo element is missing a title sub-element",
											mtitlei[i]));
				}
			}
	
			ModsNameType mname[] = mods.getNameArray();
			for (int i = 0; i < mname.length; i++) {
				if (mname[i].getNamePartArray().length == 0) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC name element is missing a namePart sub-element",
											mname[i]));
				}
			}
	
			if (mods.getTypeOfResourceArray().length == 0) {
				logger.error(XmlError.forObject(
						"The PRIMARY_DMDSEC is missing a typeOfResource element",
						mods));
			}
	
			ModsGenreType mgen[] = mods.getGenreArray();
			for (int i = 0; i < mgen.length; i++) {
				if (!mgen[i].isSetAuthority()) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC genre element is missing an authority attribute",
											mgen[i]));
				}
			}
	
			ModsOriginInfoType morigin[] = mods.getOriginInfoArray();
			if (morigin == null || morigin.length == 0) {
				logger.error(XmlError
								.forObject(
										"The PRIMARY_DMDSEC is missing an originInfo element",
										mods));
			}
	
			ModsDateType mdate[] = null;
			boolean foundKeyDate = false;
			for (int i = 0; i < morigin.length; i++) {
				// make sure that place elements have the required children
				ModsPlaceType mplace[] = morigin[i].getPlaceArray();
				for (int k = 0; k < mplace.length; k++) {
					ModsPlaceTermType mplaceterm[] = mplace[k].getPlaceTermArray();
					if (mplaceterm.length == 0) {
						logger.error(XmlError
										.forObject(
												"The PRIMARY_DMDSEC originInfo place sub-element is missing a placeTerm sub-sub-element",
												mplace[k]));
					}
					boolean foundTypeText=false;
					for (int l = 0; l < mplaceterm.length; l++) {
						if (!mplaceterm[l].isSetType()) {
							logger.error(XmlError
											.forObject(
													"The PRIMARY_DMDSEC placeTerm element is missing a type attribute",
													mplaceterm[l]));
						}
						if(mplaceterm[l].getType() == ModsCodeOrText.TEXT){
							foundTypeText=true;
						}
						if (mplaceterm[l].getType() == ModsCodeOrText.CODE
								&& !mplaceterm[l].isSetAuthority()) {
							logger.error(XmlError
											.forObject(
													"The PRIMARY_DMDSEC placeTerm element with type='code' is missing an authority attribute",
													mplaceterm[l]));
						}
					}
					if(!foundTypeText){
						logger.error(XmlError
								.forObject(
										"The PRIMARY_DMDSEC place element does not have a placeTerm sub-element with a type='text' attribute",
										mplace[k]));
					}
				}
	
				// make sure there is at least one date type element
				if (morigin[i].getCopyrightDateArray().length == 0
						&& morigin[i].getDateCapturedArray().length == 0
						&& morigin[i].getDateCreatedArray().length == 0
						&& morigin[i].getDateIssuedArray().length == 0
						&& morigin[i].getDateModifiedArray().length == 0
						&& morigin[i].getDateOtherArray().length == 0
						&& morigin[i].getDateValidArray().length == 0) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC originInfo element is missing a dateXXX sub-element",
											morigin[i]));
				}
				// make sure that at least one of them has a keyDate='yes' attribute
				mdate = morigin[i].getCopyrightDateArray();
				for (int j = 0; j < mdate.length; j++) {
					if (mdate[j].isSetKeyDate()) {
						foundKeyDate = true;
						break;
					}
				}
				if (foundKeyDate)
					break;
				mdate = morigin[i].getDateCreatedArray();
				for (int j = 0; j < mdate.length; j++) {
					if (mdate[j].isSetKeyDate()) {
						foundKeyDate = true;
						break;
					}
				}
				if (foundKeyDate)
					break;
				mdate = morigin[i].getDateIssuedArray();
				for (int j = 0; j < mdate.length; j++) {
					if (mdate[j].isSetKeyDate()) {
						foundKeyDate = true;
						break;
					}
				}
				if (foundKeyDate)
					break;
				mdate = morigin[i].getDateCapturedArray();
				for (int j = 0; j < mdate.length; j++) {
					if (mdate[j].isSetKeyDate()) {
						foundKeyDate = true;
						break;
					}
				}
				if (foundKeyDate)
					break;
				mdate = morigin[i].getDateValidArray();
				for (int j = 0; j < mdate.length; j++) {
					if (mdate[j].isSetKeyDate()) {
						foundKeyDate = true;
						break;
					}
				}
				if (foundKeyDate)
					break;
				mdate = morigin[i].getDateModifiedArray();
				for (int j = 0; j < mdate.length; j++) {
					if (mdate[j].isSetKeyDate()) {
						foundKeyDate = true;
						break;
					}
				}
				if (foundKeyDate)
					break;
				mdate = morigin[i].getDateOtherArray();
				for (int j = 0; j < mdate.length; j++) {
					if (mdate[j].isSetKeyDate()) {
						foundKeyDate = true;
						break;
					}
				}
				if (!foundKeyDate) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC originInfo element does not have a dateXXX sub-element with a keyDate attribute",
											morigin[i]));
				}
			}
	
			ModsLanguageType mlang[] = mods.getLanguageArray();
			for (int i = 0; i < mlang.length; i++) {
				if (mlang[i].getLanguageTermArray().length == 0) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC language element is missing a languageTerm sub-element",
											mlang[i]));
				}
				ModsLanguageType.LanguageTerm mlangterm[] = mlang[i]
						.getLanguageTermArray();
				boolean foundCode=false;
				boolean foundText=false;
				for (int l = 0; l < mlangterm.length; l++) {
					if (!mlangterm[l].isSetType()) {
						logger.error(XmlError
										.forObject(
												"The PRIMARY_DMDSEC languageTerm element is missing a type attribute",
												mlangterm[l]));
					} else if (mlangterm[l].getType().equals(ModsCodeOrText.CODE)){
						foundCode=true;
					} else if (mlangterm[l].getType().equals(ModsCodeOrText.TEXT)){
						foundText=true;
					}
					if (mlangterm[l].getType() == ModsCodeOrText.CODE
							&& !mlangterm[l].isSetAuthority()) {
						logger.error(XmlError
										.forObject(
												"The PRIMARY_DMDSEC languageTerm with type='code' is missing an authority attribute",
												mlangterm[l]));
					}
				}
				if(!foundCode || !foundText){
					logger.error(XmlError
							.forObject(
									"The PRIMARY_DMDSEC language does not have languageTerm with type='code' and a languageTerm with type='text'",
									mlang[i]));
				}
			}
	
			ModsPhysicalDescriptionType mphys[] = mods
					.getPhysicalDescriptionArray();
			if (mphys.length ==0) {
				logger.error(XmlError
								.forObject(
										"The PRIMARY_DMDSEC does not have exactly one physicalDescription element",
										mods));
			} else if (mphys.length >1 ){
				logger.error(XmlError
						.forObject(
								"The PRIMARY_DMDSEC has more than one physicalDescription element",
								mods));
			} else {
				if (mphys[0].getDigitalOriginArray().length != 1) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC physicalDescription element does not have exactly one digitalOrigin sub-element",
											mphys[0]));
				}
				if (mphys[0].getInternetMediaTypeArray().length == 0) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC physicalDescription element is missing an internetMediaType sub-element",
											mphys[0]));
				}
			}
	
			ModsSubjectType msubj[] = mods.getSubjectArray();
			for (int i = 0; i < msubj.length; i++) {
				XmlObject xobj[] = msubj[i].selectPath("./*");
				if (xobj.length == 0) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC subject element is missing a sub-element",
											msubj[i]));
				}
			}
	
			ModsClassificationType mclass[] = mods.getClassificationArray();
			for (int i = 0; i < mclass.length; i++) {
				if (!mclass[i].isSetAuthority()) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC classification element is missing an authority attribute",
											mclass[i]));
				}
			}
	
			ModsRelatedItemType mrelat[] = mods.getRelatedItemArray();
			for (int i = 0; i < mrelat.length; i++) {
				if (!mrelat[i].isSetType()) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC relatedItem element is missing a type attribute",
											mrelat[i]));
				}
			}
	
			ModsIdentifierType mident[] = mods.getIdentifierArray();
			for (int i = 0; i < mident.length; i++) {
				if (!mident[i].isSetType()) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC identifier element is missing a type attribute",
											mident[i]));
				}
			}
			int primDisplayCnt = 0;
			ModsLocationType mloc[] = mods.getLocationArray();
			if (mloc.length == 0) {
				logger.error(XmlError.forObject(
						"The PRIMARY_DMDSEC is missing a location element", mods));
			}
			for (int i = 0; i < mloc.length; i++) {
				ModsUrlType murl[] = mloc[i].getUrlArray();
				if (murl.length == 0) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC location element is missing a url sub-element",
											mloc[i]));
				}
				for (int j = 0; j < murl.length; j++) {
					if (murl[j].isSetUsage()
							&& murl[j].getUsage() == ModsUrlType.Usage.PRIMARY_DISPLAY)
						primDisplayCnt++;
				}
			}
			if (primDisplayCnt != 1) {
				logger.error(XmlError
								.forObject(
										"The PRIMARY_DMDSEC does not have exactly one location element with a url sub-element with a usage attribute of 'primary display'",
										mods));
			}
	
			ModsAccessConditionType macc[] = mods.getAccessConditionArray();
			boolean useAndRepoAttr = false;
			if (macc.length == 0) {
				logger.error(XmlError.forObject(
						"The PRIMARY_DMDSEC is missing an accessCondition element",
						mods));
			}
			for (int i = 0; i < macc.length; i++) {
				if (macc[i].isSetType2()
						&& macc[i].getType2().compareTo("useAndReproduction") == 0) {
					useAndRepoAttr = true;
					break;
				}
			}
			if (!useAndRepoAttr) {
				logger.error(XmlError
								.forObject(
										"The PRIMARY_DMDSEC is missing an accessCondition element with a type attribute of 'useAndReproduction'",
										mods));
			}
	
			ModsRecordInfoType minfo[] = mods.getRecordInfoArray();
			if (minfo.length != 1) {
				logger.error(XmlError
								.forObject(
										"The PRIMARY_DMDSEC does not have exactly one recordInfo element",
										mods));
			} else {
				ModsLanguageType minfolang[] = minfo[0]
						.getLanguageOfCatalogingArray();
				if (minfolang.length == 0) {
					logger.error(XmlError
									.forObject(
											"The PRIMARY_DMDSEC recordInfo element is missing a languageOfCataloging sub-element",
											minfo[0]));
				} else {
					ModsLanguageType.LanguageTerm minfolangterm[] = minfolang[0]
							.getLanguageTermArray();
					if (minfolangterm.length == 0) {
						logger.error(XmlError
										.forObject(
												"The PRIMARY_DMDSEC recordInfo languageOfCataloging sub-element is missing a languageTerm sub-sub-element",
												minfolang[0]));
					} else if (!minfolangterm[0].isSetAuthority()) {
						logger.error(XmlError
										.forObject(
												"The PRIMARY_DMDSEC recordInfo languageOfCataloging languageTerm sub-sub-element is missing an authority attribute",
												minfolangterm[0]));
					}
				}
			}
		}
		
		logger.removeAppender(app);
		return (!app.hasErrors());
	}
	
	/**
	 * Validate the METS document against the requirements of the METS HaS
	 * Profile
	 * 
	 * @param pt
	 *            The package type for the METS document, AIP, SIP, or DIP
	 * @return true if it is valid, else false
	 */
	public boolean validateProfile(HaSMETSProfile metsP,PackageType pt
			) {
		return this.validateProfile(metsP,pt, true);
	}
	
	/**
	 * Validate the techMD Premis object for a structMap against the
	 * requirements of the METS HaS Profile
	 * 
	 * @param smap
	 *            the MetsStructMapType for the structMap which references the
	 *            techMD
	 * @return true if it is valid, else false
	 */
	public boolean validateTechMDPremisObjectForStructMap(HaSMETSProfile metsP,PackageType pt,
			MetsStructMapType smap) {
		final MetsMetsDocument metsDoc = metsP.getMetsDocument();

		HaSMETSAppender app = new HaSMETSAppender("ValidateTechMDPremisObjectForStructMap");
		logger.addAppender(app);

		// construct an xpath to the techMD premis object
		String xql = null;

		if (!smap.getDiv().isSetADMID()) {
			if (smap.getDiv().isSetTYPE()
					&& smap.getDiv().getTYPE().compareTo(
							HaSMETSProfile.StructMapType.PRIMARY_STRUCTMAP
									.toString()) == 0) {
				logger.error(XmlError
								.forObject(
										"The PRIMARY_STRUCTMAP root div element does not contain an ADMID attribute",
										smap.getDiv()));
			}
		} else {
			XmlIDREFS adms = smap.getDiv().xgetADMID();
			String orID = "";
			for (Iterator it = adms.getListValue().iterator(); it.hasNext();) {
				if (orID.length() > 0) {
					orID = orID + " or @ID='" + it.next() + "'";
				} else {
					orID = "@ID='" + it.next() + "'";
				}
			}
			// NOTE: Right now the profile does not say that these cannot be
			// referenced via th mdRef element, but
			// I assume they are not for purposes of this test
			xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS
					+ "'; declare namespace m='" + HaSMETSProfile.METS_NS + "'; .//m:techMD["
					+ orID + "]//m:xmlData[p:object]";
			XmlObject xobj[] = metsDoc.selectPath(xql);
			for (int i = 0; i < xobj.length; i++) {
				Node n2 = metsP
						.getXmlDataAny((MetsMdSecType.MdWrap.XmlData) xobj[i]);
				PremisObjectDocument pobjdoc = null;
				try {
					pobjdoc = (PremisObjectDocument) metsP.getAnyXmlObject(n2);
				} catch (HaSMETSProfileException e) {
					logger.error(XmlError
									.forObject(
											"The techMD referenced by the file does not contain a valid a Premis object element",
											xobj[i]));
					logger.error(XmlError.forObject(e.getMessage(), xobj[i]));
				}

				if (pobjdoc != null
						&& pobjdoc
								.getObject()
								.getObjectCategory()
								.compareTo(
										HaSMETSProfile.PREMISObjectCategory.REPRESENTATION
												.toString()) != 0) {
					logger.error(XmlError
									.forObject(
											"The objectCategory of the Premis object referenced from the structMap is not 'REPRESENTATION'",
											pobjdoc.getObject()
													.xgetObjectCategory()));
				}
			}
		}

		logger.removeAppender(app);
		return (!app.hasErrors());
	}
	
	/**
	 * Validate the techMD Premis object for a file against the requirements of
	 * the METS HaS Profile
	 * 
	 * @param ft
	 *            the MetsFileType for the file which references the techMD
	 * @return true if it is valid, else false
	 */
	public boolean validateTechMDPremisObjectForFile(HaSMETSProfile metsP,PackageType pt,
			MetsFileType ft) {
		final MetsMetsDocument metsDoc = metsP.getMetsDocument();
		
		HaSMETSAppender app = new HaSMETSAppender("ValidateTechMDPremisObjectForFile");
		logger.addAppender(app);

		// construct an xpath to the techMD premis object
		String xql = null;
		XmlIDREFS adms = ft.xgetADMID();
		String orID = "";
		for (Iterator it = adms.getListValue().iterator(); it.hasNext();) {
			if (orID.length() > 0) {
				orID = orID + " or @ID='" + it.next() + "'";
			} else {
				orID = "@ID='" + it.next() + "'";
			}
		}
		xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS + "'; declare namespace m='"
				+ HaSMETSProfile.METS_NS + "'; .//m:techMD[" + orID + "]//m:xmlData[p:object]";
		XmlObject xobj[] = metsDoc.selectPath(xql);
		if (xobj.length == 0 && pt != HaSMETSProfile.PackageType.SIP) {
			logger.error(XmlError
							.forObject(
									"The file does not reference a techMD with a Premis object element",
									ft));
		} else {
			Node n2 = metsP
					.getXmlDataAny((MetsMdSecType.MdWrap.XmlData) xobj[0]);
			PremisObjectDocument pobjdoc = null;
			try {
				pobjdoc = (PremisObjectDocument) metsP.getAnyXmlObject(n2);
			} catch (HaSMETSProfileException e) {
				logger.error(XmlError
								.forObject(
										"The techMD referenced by the file does not contain a valid a Premis object element",
										ft));
				logger.error(XmlError.forObject(e.getMessage(), xobj[0]));
			}

			// check out the Premis object
			if (ft.isSetOWNERID()) {
				// make sure there is a premis object identifier with this value
				PremisObjectDocument.Object.ObjectIdentifier objids[] = pobjdoc
						.getObject().getObjectIdentifierArray();
				boolean objIDFound = false;
				for (int j = 0; j < objids.length; j++) {
					if (objids[j].getObjectIdentifierValue().compareTo(
							ft.getOWNERID()) == 0) {
						objIDFound = true;
						break;
					}
				}
				if (!objIDFound) {
					logger.error(XmlError
									.forObject(
											"The file OWNERID attribute does not occur as an objectIdentiferValue in the referenced Premis object element",
											ft));
				}
			}

			// check the objectCategory
			String objCat = pobjdoc.getObject().getObjectCategory();
			if (objCat.compareTo(HaSMETSProfile.PREMISObjectCategory.FILE
					.toString()) != 0
					&& objCat
							.compareTo(HaSMETSProfile.PREMISObjectCategory.BITSTREAM
									.toString()) != 0) {
				logger.error(XmlError
								.forObject(
										"The Premis objectCategory for the file is not 'FILE' or 'BITSTREAM'",
										ft));
			}

			// check the object characteristics
			PremisObjectDocument.Object.ObjectCharacteristics objchar[] = pobjdoc
					.getObject().getObjectCharacteristicsArray();
			if (objchar.length != 1) {
				logger.error(XmlError
								.forObject(
										"The Premis object element for the file does not have exactly one objectCharacteristics sub-element",
										ft));
			} else {
				if (objchar[0].getCompositionLevel().intValue() != 0) {
					logger.error(XmlError
									.forObject(
											"The Premis compositionLevel for the file is not '0'",
											ft));
				}

				// check the fixity
				String chksum = ft.getCHECKSUM();
				boolean foundChkSum = false;
				PremisObjectDocument.Object.ObjectCharacteristics.Fixity fix[] = objchar[0]
						.getFixityArray();
				for (int j = 0; j < fix.length; j++) {
					if (chksum != null
							&& fix[j].getMessageDigestAlgorithm().compareTo(
									"SHA-1") == 0
							&& fix[j].getMessageDigest().compareToIgnoreCase(
									chksum) == 0) {
						foundChkSum = true;
						break;
					}
				}
				if (!foundChkSum) {
					logger.error(XmlError
									.forObject(
											"The file CHECKSUM does not occur as an SHA-1 messageDigest in the referenced Premis object element",
											ft));
				}

				// check the size
				if (!objchar[0].isSetSize()
						|| objchar[0].getSize() != ft.getSIZE()) {
					logger.error(XmlError
									.forObject(
											"The file SIZE does not occur as an objectCharacteristics size in the referenced Premis object element",
											ft));
				}

				// check the format
				if (!objchar[0].isSetFormat()
						|| !objchar[0].getFormat().isSetFormatDesignation()
						|| (ft.isSetMIMETYPE() && objchar[0].getFormat()
								.getFormatDesignation().getFormatName()
								.compareToIgnoreCase(ft.getMIMETYPE()) != 0)) {
					logger.error(XmlError
									.forObject(
											"The file MIMETYPE does not occur as an objectCharacteristics formatName in the referenced Premis object element",
											ft));
				}
			}
			// check for MIMETYPE application special sections
			if (pt!=HaSMETSProfile.PackageType.SIP && ft.isSetMIMETYPE()
					&& ft.getMIMETYPE().toLowerCase().startsWith("application")) {
				if (pobjdoc.getObject().getCreatingApplicationArray().length == 0) {
					logger.error(XmlError
									.forObject(
											"The creatingApplication element is missing from the Premis object referenced from the file with a MIMETYPE of 'application'",
											ft));
				}
				PremisObjectDocument.Object.Environment env[] = pobjdoc
						.getObject().getEnvironmentArray();
				if (env.length == 0) {
					logger.error(XmlError
									.forObject(
											"The environment element is missing from the Premis object referenced from the file with a MIMETYPE of 'application'",
											ft));
				} else {
					boolean foundSW = false;
					for (int j = 0; j < env.length; j++) {
						if (env[j].getSoftwareArray().length > 0) {
							foundSW = true;
							break;
						}
					}
					if (!foundSW) {
						logger.error(XmlError
										.forObject(
												"The environment software sub-element is missing from the Premis object referenced from the file with a MIMETYPE of 'application'",
												ft));
					}
				}
			}
		}
		
		logger.removeAppender(app);
		return (!app.hasErrors());
	}	
	
	/**
	 * Validate that the div of the PRIMARY_STRUCTMAP contains a valid fptr This
	 * always returns true because the base profile has no specific requirements
	 * for the primary structMap Fptrs
	 * 
	 * @param div
	 *            the div whose fptr is neing validated
	 * @return true if it is valid, else false
	 */
	public boolean validatePrimaryStructMapFptr(HaSMETSProfile metsP,PackageType pt,
			MetsDivType div) {
		return true;
	}
	
	/**
	 * Validate the primary structMap against the requirements of the METS HaS
	 * Profile This always returns true because the base profile has no specific
	 * requirements for the primary structMap
	 * 
	 * @return true if it is valid, else false
	 */
	public boolean validatePrimaryStructMap(HaSMETSProfile metsP,PackageType pt) {
		return true;
	}
	
	/**
	 * Validate the file elements against the requirements of the METS HaS
	 * Profile
	 * 
	 * @return true if it is valid, else false
	 */
	public boolean validateFiles(HaSMETSProfile metsP,PackageType pt,
			boolean openEachFile) {
		final MetsMetsDocument metsDoc = metsP.getMetsDocument();

		HaSMETSAppender app = new HaSMETSAppender("ValidateFiles");
		logger.addAppender(app);

		// check all the files
		MetsFileType fls[] = metsP.getAllFiles();
		for (int i = 0; i < fls.length; i++) {
			if (fls[i].getFLocatArray().length > 0 && fls[i].isSetFContent()) {
				logger.error(XmlError
								.forObject(
										"The file element has both FLocat and FContent sub-elements",
										fls[i]));
			}
			if (fls[i].getFLocatArray().length > 1) {
				logger.error(XmlError.forObject(
						"The file element has mutliple FLocat sub-elements",
						fls[i]));
			}
			// if there is an FLocat check that it is relative to the METS
			// document
			MetsFileType.FLocat flocat[] = fls[i].getFLocatArray();
			if (flocat.length != 1 && !fls[i].isSetFContent()) {
				logger.error(XmlError
								.forObject(
										"The file does not have exactly one FLocat or exactly one FContent sub-element",
										fls[i]));
			} else if (flocat.length == 1) {
				if (flocat[0].getLOCTYPE() != MetsFileType.FLocat.LOCTYPE.URL) {
					logger.error(XmlError
									.forObject(
											"The FLocat does not have a LOCTYPE attribute with a value 'URL'",
											flocat[0]));
				}
				if (!flocat[0].isSetHref()) {
					logger.error(XmlError.forObject(
							"The FLocat is missing an xlink:href attribute",
							flocat[0]));
				} else {
					URI href = null;
					try {
						href = new URI(flocat[0].getHref());
					} catch (URISyntaxException e) {
						logger.error(XmlError.forObject(
								"The xlink:href is not a valid URI", flocat[0]
										.xgetHref()));
					}
					href = href.normalize();
					URI lnk = metsP.getBaseURI().resolve(href).normalize();
					String commonBase = metsP.getBaseURI().normalize().toString();
					commonBase = commonBase.substring(0, commonBase
							.lastIndexOf("/"));
					if (!lnk.toString().toLowerCase().startsWith(
							commonBase.toLowerCase())) {
						logger.error(XmlError
										.forObject(
												"This xlink:href value is not relative to the base location of the METS document",
												flocat[0].xgetHref()));
					}
				}
			}
			// get the input stream of each file and calculate its size and
			// checksum
			long actualSize = 0;
			String actualChecksum = "";
			boolean fileAccessed = false;
			if (openEachFile) {
				try {
					InputStream inpStrm = metsP.getFileInputStream(fls[i]);
					HaSChecksummer summer = new HaSChecksummer();
					CheckedInputStream chkStrm = new CheckedInputStream(
							inpStrm, summer);
					while (chkStrm.read() != -1)
						actualSize++;
					chkStrm.close();
					actualChecksum = summer.getHexEncodedSHA1();
					fileAccessed = true;
				} catch (HaSMETSProfileException e) {
					logger.error(XmlError.forObject(
							"The InputStream associated with the file with ID='"
									+ fls[i].getID() + "' could not be opened",
							fls[i]));
					logger.error(XmlError.forObject(e.getMessage(), fls[i]));
				} catch (IOException e) {
					logger.error(XmlError.forObject(
							"The InputStream associated with the file with ID='"
									+ fls[i].getID() + "' could not be read",
							fls[i]));
					logger.error(XmlError.forObject(e.getMessage(), fls[i]));
				}
			}

			// check for MIMETYPE
			if (!fls[i].isSetMIMETYPE()) {
				logger.error(XmlError.forObject(
						"The file is missing a MIMETYPE attribute", fls[i]));
			} else if (!fls[i].getMIMETYPE().toLowerCase().startsWith("application")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("audio")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("example")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("image")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("message")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("model")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("multipart")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("text")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("video")) {
				logger.error(XmlError
								.forObject(
										"The MIMETYPE attribute does not start with a recognized value",
										fls[i].xgetMIMETYPE()));
			}

			// check for SIZE
			if (!fls[i].isSetSIZE()) {
				logger.error(XmlError.forObject(
						"The file is missing a SIZE attribute", fls[i]));
			} else if (fls[i].getSIZE() <= 0) {
				logger.error(XmlError.forObject(
						"This SIZE attribute has a negative or zero value",
						fls[i].xgetSIZE()));
			} else {
				if (fls[i].isSetFContent()
						&& fls[i].getFContent().isSetXmlData()) {
					logger.warn(XmlError
									.forObject(
											"The SIZE attribute for FContent embedded as xmlData cannot currently be validated",
											XmlError.SEVERITY_WARNING, fls[i]
													.xgetCHECKSUM()));
				} else if (fileAccessed && fls[i].getSIZE() != actualSize) {
					logger.error(XmlError.forObject(
							"This SIZE attribute does not match the actual size of the file which is "
									+ String.valueOf(actualSize), fls[i]
									.xgetSIZE()));
				}
			}
			// check for CREATED
			if (!fls[i].isSetCREATED()) {
				logger.error(XmlError.forObject(
						"The file is missing a CREATED attribute", fls[i]));
			} else {
				if (fls[i].getCREATED().compareTo(
						metsDoc.getMets().getMetsHdr().getLASTMODDATE()) > 0) {
					logger.warn(XmlError
									.forObject(
											"The CREATED date is later than the LASTMODDATE date for this METS document",
											XmlError.SEVERITY_WARNING, fls[i]
													.xgetCREATED()));
				}
				if (fls[i].getCREATED().compareTo(Calendar.getInstance()) > 0) {
					logger.warn(XmlError.forObject(
							"The CREATED date is later than the current date",
							XmlError.SEVERITY_WARNING, fls[i].xgetCREATED()));
				}
			}
			// check for checksum
			if (!fls[i].isSetCHECKSUM()) {
				if (pt != HaSMETSProfile.PackageType.SIP) {
					// TODO: This may be come required for all types
					logger.error(XmlError.forObject(
									"The file is missing a CHECKSUM attribute",
									fls[i]));
				}
			} else if (!fls[i].isSetCHECKSUMTYPE()
					|| fls[i].getCHECKSUMTYPE() != MetsFileType.CHECKSUMTYPE.SHA_1) {
				logger.error(XmlError
								.forObject(
										"The CHECKSUMTYPE attribute does not have a value of 'SHA-1'",
										fls[i].xgetCHECKSUMTYPE()));
			} else if (fls[i].getCHECKSUM().trim().length() != 40) {
				logger.error(XmlError
								.forObject(
										"The CHECKSUM attribute is not a 40-digit hexadecimal number",
										fls[i].xgetCHECKSUM()));
			} else {
				try {
					XmlHexBinary hex = XmlHexBinary.Factory
							.parse("<xml-fragment>" + fls[i].getCHECKSUM()
									+ "</xml-fragment>");
					if (!hex.validate()) {
						logger.error(XmlError
										.forObject(
												"The CHECKSUM attribute is not a properly encoded hexadecimal number",
												fls[i].xgetCHECKSUM()));
					}
				} catch (XmlException e) {
					logger.error(XmlError
									.forObject(
											"The CHECKSUM attribute is not valid",
											fls[i].xgetCHECKSUM()));
				}
				if (fls[i].isSetFContent()
						&& fls[i].getFContent().isSetXmlData()) {
					logger.warn(XmlError
									.forObject(
											"The CHECKSUM attribute for FContent embedded as xmlData cannot currently be validated",
											XmlError.SEVERITY_WARNING, fls[i]
													.xgetCHECKSUM()));
				} else if (fileAccessed
						&& fls[i].getCHECKSUM().compareToIgnoreCase(
								actualChecksum) != 0) {
					logger.error(XmlError
									.forObject(
											"This CHECKSUM attribute does not match the actual checksum of the file which is "
													+ actualChecksum, fls[i]
													.xgetCHECKSUM()));
				}
			}

			// check that each file references a techMD premis object
			if (!fls[i].isSetADMID()) {
				if (pt != HaSMETSProfile.PackageType.SIP) {
					logger.error(XmlError.forObject(
							"The file is missing an ADMID attribute", fls[i]));
				}
			} else {
				this.validateTechMDPremisObjectForFile(metsP,pt, fls[i]);
				this.validateDigiprovMDPremisEventForFile(metsP,pt, fls[i]);
			}

		}

		logger.removeAppender(app);
		return (!app.hasErrors());
	}	
	
	/**
	 * Validate the digiprovMD Premis event for a structMap against the
	 * requirements of the METS HaS Profile
	 * 
	 * @param smap
	 *            the MetsStructMapType for the structMap which references the
	 *            techMD
	 * @return true if it is valid, else false
	 */
	public boolean validateDigiprovMDPremisEventForStructMap(HaSMETSProfile metsP, PackageType pt,
			MetsStructMapType smap) {
		final MetsMetsDocument metsDoc = metsP.getMetsDocument();

		HaSMETSAppender app = new HaSMETSAppender("ValidateDigiprovMDPremisEventForStructMap");
		logger.addAppender(app);

		// construct an xpath to the techMD premis object
		String xql = null;

		if (smap.getDiv().isSetADMID()
				&& smap.getDiv().xgetADMID().getStringValue().trim().length() > 0) {

			XmlIDREFS adms = smap.getDiv().xgetADMID();
			String orID = "";

			for (Iterator it = adms.getListValue().iterator(); it.hasNext();) {
				if (orID.length() > 0) {
					orID = orID + " or @ID='" + it.next() + "'";
				} else {
					orID = "@ID='" + it.next() + "'";
				}
			}

			// look for referenced digiprovMD which is not allowed
			xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS
					+ "'; declare namespace m='" + HaSMETSProfile.METS_NS
					+ "'; .//m:digiprovMD[" + orID + "]/m:mdRef";
			XmlObject xobj0[] = metsDoc.selectPath(xql);
			if (xobj0.length > 0) {
				logger.error(XmlError
								.forObject(
										"The structMap root div element references a digiprovMD containing an mdRef sub-element",
										smap.getDiv()));
			}

			xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS
					+ "'; declare namespace m='" + HaSMETSProfile.METS_NS
					+ "'; .//m:digiprovMD[" + orID + "]//m:xmlData[p:event]";
			XmlObject xobj[] = metsDoc.selectPath(xql);
			for (int i = 0; i < xobj.length; i++) {
				Node n2 = metsP
						.getXmlDataAny((MetsMdSecType.MdWrap.XmlData) xobj[i]);
				PremisEventDocument pobjdoc = null;
				try {
					pobjdoc = (PremisEventDocument) metsP.getAnyXmlObject(n2);
				} catch (HaSMETSProfileException e) {
					logger.error(XmlError
									.forObject(
											"The digiprovMD referenced by the file does not contain a valid a Premis event element",
											xobj[i]));
					logger.error(XmlError.forObject(e.getMessage(), xobj[i]));
				}
				// check eventType
				String evType = pobjdoc.getEvent().getEventType();
				if (evType
						.compareTo(HaSMETSProfile.PREMISEventType.STRUCTMAP_CREATION
								.toString()) != 0
						&& evType
								.compareTo(HaSMETSProfile.PREMISEventType.STRUCTMAP_DELETION
										.toString()) != 0
						&& evType
								.compareTo(HaSMETSProfile.PREMISEventType.STRUCTMAP_MODIFICATION
										.toString()) != 0
						&& evType
								.compareTo(HaSMETSProfile.PREMISEventType.STRUCTMAP_TRANSFORMATION
										.toString()) != 0) {
					logger.error(XmlError
									.forObject(
											"The digiprovMD Premis event referenced by the structMap div does not have a valid eventType",
											pobjdoc.getEvent().xgetEventType()));
				}

				// check eventDateTime
				if (pobjdoc.getEvent().getEventDateTime().compareTo(
						Calendar.getInstance()) > 0) {
					logger.error(XmlError
									.forObject(
											"The digiprovMD Premis event referenced by the structMap div has an eventDateTime later than the current date",
											pobjdoc.getEvent()
													.xgetEventDateTime()));
				}
				if (metsDoc.getMets().isSetMetsHdr()
						&& metsDoc.getMets().getMetsHdr().isSetLASTMODDATE()
						&& pobjdoc.getEvent().getEventDateTime()
								.compareTo(
										metsDoc.getMets().getMetsHdr()
												.getLASTMODDATE()) > 0) {
					logger.error(XmlError
									.forObject(
											"The digiprovMD Premis event referenced by the structMap div has an eventDateTime later than the LASTMODIFIED date of the METS document",
											pobjdoc.getEvent()
													.xgetEventDateTime()));
				}
				if (metsDoc.getMets().isSetMetsHdr()
						&& metsDoc.getMets().getMetsHdr().isSetCREATEDATE()
						&& pobjdoc.getEvent().getEventDateTime().compareTo(
								metsDoc.getMets().getMetsHdr().getCREATEDATE()) < 0) {
					logger.warn(XmlError
									.forObject(
											"The digiprovMD Premis event referenced by the structMap div has an eventDateTime earlier than the CREATEDDATE date of the METS document",
											XmlError.SEVERITY_WARNING, pobjdoc
													.getEvent()
													.xgetEventDateTime()));
				}
			}
		}

		logger.removeAppender(app);
		return (!app.hasErrors());
	}
	
	/**
	 * Validate the digiprovMD Premis event for a file against the requirements
	 * of the METS HaS Profile
	 * 
	 * @param ft
	 *            the MetsFileType for the file which references the techMD
	 * @return true if it is valid, else false
	 */
	public boolean validateDigiprovMDPremisEventForFile(HaSMETSProfile metsP, PackageType pt,
			MetsFileType ft) {
		final MetsMetsDocument metsDoc = metsP.getMetsDocument();

		HaSMETSAppender app = new HaSMETSAppender("ValidateDigiprovMDPremisEventForFile");
		logger.addAppender(app);

		// construct an xpath to the techMD premis object
		String xql = null;
		XmlIDREFS adms = ft.xgetADMID();
		String orID = "";
		for (Iterator it = adms.getListValue().iterator(); it.hasNext();) {
			if (orID.length() > 0) {
				orID = orID + " or @ID='" + it.next() + "'";
			} else {
				orID = "@ID='" + it.next() + "'";
			}
		}

		// look for referenced digiprovMD which is not allowed
		xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS + "'; declare namespace m='"
				+ HaSMETSProfile.METS_NS + "'; .//m:digiprovMD[" + orID + "]/m:mdRef";
		XmlObject xobj0[] = metsDoc.selectPath(xql);
		if (xobj0.length > 0) {
			logger.error(XmlError
							.forObject(
									"The file reference a digiprovMD containing an mdRef sub-element",
									ft));
		}

		xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS + "'; declare namespace m='"
				+ HaSMETSProfile.METS_NS + "'; .//m:digiprovMD[" + orID
				+ "]//m:xmlData[p:event]";
		XmlObject xobj[] = metsDoc.selectPath(xql);
		for (int i = 0; i < xobj.length; i++) {
			Node n2 = metsP
					.getXmlDataAny((MetsMdSecType.MdWrap.XmlData) xobj[i]);
			PremisEventDocument pobjdoc = null;
			try {
				pobjdoc = (PremisEventDocument) metsP.getAnyXmlObject(n2);
			} catch (HaSMETSProfileException e) {
				logger.error(XmlError
								.forObject(
										"The digiprovMD referenced by the file does not contain a valid a Premis event element",
										xobj[i]));
				logger.error(XmlError.forObject(e.getMessage(), xobj[i]));
			}
			// check eventDateTime
			if (pobjdoc.getEvent().getEventDateTime().compareTo(
					Calendar.getInstance()) > 0) {
				logger.warn(XmlError
								.forObject(
										"The digiprovMD Premis event referenced by the file has an eventDateTime later than the current date",
										XmlError.SEVERITY_WARNING, pobjdoc
												.getEvent().xgetEventDateTime()));
			}
			if (metsDoc.getMets().isSetMetsHdr()
					&& metsDoc.getMets().getMetsHdr().isSetLASTMODDATE()
					&& pobjdoc.getEvent().getEventDateTime().compareTo(
							metsDoc.getMets().getMetsHdr().getLASTMODDATE()) > 0) {
				logger.warn(XmlError
								.forObject(
										"The digiprovMD Premis event referenced by the file has an eventDateTime later than the LASTMODIFIED date of the METS document",
										XmlError.SEVERITY_WARNING, pobjdoc
												.getEvent().xgetEventDateTime()));
			}
			if (metsDoc.getMets().isSetMetsHdr()
					&& metsDoc.getMets().getMetsHdr().isSetCREATEDATE()
					&& pobjdoc.getEvent().getEventDateTime().compareTo(
							metsDoc.getMets().getMetsHdr().getCREATEDATE()) < 0) {
				logger.warn(XmlError
								.forObject(
										"The digiprovMD Premis event referenced by the file has an eventDateTime earlier than the CREATEDDATE date of the METS document",
										XmlError.SEVERITY_WARNING, pobjdoc
												.getEvent().xgetEventDateTime()));
			}
			if (ft.isSetCREATED()
					&& pobjdoc.getEvent().getEventDateTime().compareTo(
							ft.getCREATED()) < 0) {
				logger.warn(XmlError
								.forObject(
										"The digiprovMD Premis event referenced by the file has an eventDateTime earlier than the CREATED date of the file element",
										XmlError.SEVERITY_WARNING, pobjdoc
												.getEvent().xgetEventDateTime()));
			}
		}

		logger.removeAppender(app);
		return (!app.hasErrors());
	}
	
	/**
	 * Validate the digiprovMD Premis event for a dmdSec against the
	 * requirements of the METS HaS Profile
	 * 
	 * @param dmd
	 *            the MetsMdSecType for the dmdSec which references the
	 *            digiprovMD
	 * @return true if it is valid, else false
	 */
	public boolean validateDigiprovMDPremisEventForDmdSec(HaSMETSProfile metsP,PackageType pt,
			MetsMdSecType dmd) {
		final MetsMetsDocument metsDoc = metsP.getMetsDocument();

		HaSMETSAppender app = new HaSMETSAppender("ValidateDigiprovMDPremisEventForDmdSec");
		logger.addAppender(app);

		// construct an xpath to the digiprovMD premis event

		String xql = null;
		XmlIDREFS adms = dmd.xgetADMID();
		if (adms != null && adms.getStringValue().trim().length() > 0) {
			String orID = "";
			for (Iterator it = adms.getListValue().iterator(); it.hasNext();) {
				if (orID.length() > 0) {
					orID = orID + " or @ID='" + it.next() + "'";
				} else {
					orID = "@ID='" + it.next() + "'";
				}
			}
			// look for referenced digiprovMD which is not allowed
			xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS
					+ "'; declare namespace m='" + HaSMETSProfile.METS_NS
					+ "'; .//m:digiprovMD[" + orID + "]/m:mdRef";
			XmlObject xobj0[] = metsDoc.selectPath(xql);
			if (xobj0.length > 0) {
				logger.error(XmlError
								.forObject(
										"The dmdSec reference a digiprovMD containing an mdRef sub-element",
										dmd));
			}

			// look for wrapped digiprov
			xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS
					+ "'; declare namespace m='" + HaSMETSProfile.METS_NS
					+ "'; .//m:digiprovMD[" + orID + "]//m:xmlData[p:event]";
			XmlObject xobj[] = metsDoc.selectPath(xql);
			if (xobj.length == 0) {
				logger.error(XmlError
								.forObject(
										"The dmdSec does not reference a digiprovMD with a Premis event element",
										dmd));
			} else {
				for (int i = 0; i < xobj.length; i++) {
					Node n2 = metsP
							.getXmlDataAny((MetsMdSecType.MdWrap.XmlData) xobj[i]);
					PremisEventDocument pobjdoc = null;
					try {
						pobjdoc = (PremisEventDocument) metsP
								.getAnyXmlObject(n2);
					} catch (HaSMETSProfileException e) {
						logger.error(XmlError
										.forObject(
												"The digiprovMD referenced by the dmdSec does not contain a valid a Premis event element",
												xobj[i]));
						logger.error(XmlError.forObject(e.getMessage(),
								xobj[i]));
					}

					// check eventType
					String evType = pobjdoc.getEvent().getEventType();
					if (evType
							.compareTo(HaSMETSProfile.PREMISEventType.METADATA_CREATION
									.toString()) != 0
							&& evType
									.compareTo(HaSMETSProfile.PREMISEventType.METADATA_DELETION
											.toString()) != 0
							&& evType
									.compareTo(HaSMETSProfile.PREMISEventType.METADATA_MODIFICATION
											.toString()) != 0
							&& evType
									.compareTo(HaSMETSProfile.PREMISEventType.METADATA_TRANSFORMATION
											.toString()) != 0) {
						logger.error(XmlError
										.forObject(
												"The digiprovMD Premis event referenced by the dmdSec does not have a valid eventType",
												pobjdoc.getEvent()
														.xgetEventType()));
					}

					// check eventDateTime
					if (pobjdoc.getEvent().getEventDateTime().compareTo(
							Calendar.getInstance()) > 0) {
						logger.warn(XmlError
										.forObject(
												"The digiprovMD Premis event referenced by the dmdSec has an eventDateTime later than the current date",
												XmlError.SEVERITY_WARNING,
												pobjdoc.getEvent()
														.xgetEventDateTime()));
					}
					if (metsDoc.getMets().isSetMetsHdr()
							&& metsDoc.getMets().getMetsHdr()
									.isSetLASTMODDATE()
							&& metsP.isDateAfterLastModDate(pobjdoc.getEvent()
									.getEventDateTime())) {
						logger.warn(XmlError
										.forObject(
												"The digiprovMD Premis event referenced by the dmdSec has an eventDateTime later than the LASTMODIFIED date of the METS document",
												XmlError.SEVERITY_WARNING,
												pobjdoc.getEvent()
														.xgetEventDateTime()));
					}
					if (metsDoc.getMets().isSetMetsHdr()
							&& metsDoc.getMets().getMetsHdr().isSetCREATEDATE()
							&& metsP.isDateBeforeCreateDate(pobjdoc.getEvent()
									.getEventDateTime())) {
						logger.warn(XmlError
										.forObject(
												"The digiprovMD Premis event referenced by the dmdSec has an eventDateTime earlier than the CREATEDDATE date of the METS document",
												XmlError.SEVERITY_WARNING,
												pobjdoc.getEvent()
														.xgetEventDateTime()));
					}
					if (dmd.isSetCREATED()
							&& metsP.isDateBeforeDate(pobjdoc.getEvent()
									.getEventDateTime(), dmd.getCREATED())) {
						logger.warn(XmlError
										.forObject(
												"The digiprovMD Premis event referenced by the dmdSec has an eventDateTime earlier than the CREATED date of the dmdSec element",
												XmlError.SEVERITY_WARNING,
												pobjdoc.getEvent()
														.xgetEventDateTime()));
					}
				}
			}

		} else {
			logger.error(XmlError.forObject(
					"The dmdSec is missing an ADMID attribute", dmd));
		}

		logger.removeAppender(app);
		return (!app.hasErrors());
	}
	
	/**
	 * Validate the METS document against the requirements of the METS HaS
	 * Profile
	 * 
	 * @param pt
	 *            The package type for the METS document, AIP, SIP, or DIP
	 * @return true if it is valid, else false
	 */
	public boolean validateProfile(HaSMETSProfile metsP, PackageType pt, boolean openEachFile) {
		
		final MetsMetsDocument metsDoc = metsP.getMetsDocument();
		/**
		 * Inner class for keeping track of the structLinks
		 */
		class StructLinks {
			Hashtable<MetsStructMapType,ArrayList<String>> structLinks= new Hashtable<MetsStructMapType,ArrayList<String>>();
			
			StructLinks(){
				MetsStructMapType smaps[] = metsDoc.getMets().getStructMapArray();
				for(int i =0;i<smaps.length;i++){
					this.addXLabels(smaps[i], smaps[i].getDiv());
				}
			}
			
			/**
			 * Populate the structLinks Hashtable with all of the xlink:labels for each structMap
			 * @param smap
			 * @param div
			 */
			private void addXLabels(MetsStructMapType smap, MetsDivType div){
				if(!structLinks.containsKey(smap)){
					structLinks.put(smap,new ArrayList<String>());
				}
				if(div!=null){
					if(div.isSetLabel()){
						structLinks.get(smap).add(div.getLabel());
					}
					MetsDivType divs[] = div.getDivArray();
					for(int i = 0;i<divs.length;i++){
						addXLabels(smap,divs[i]);
					}
				}
				for(Enumeration en = structLinks.keys();en.hasMoreElements();){
					ArrayList<String> labels = structLinks.get(en.nextElement());
					Collections.sort(labels);
				}
			}
			
			/**
			 * Return an array of structMaps which have divs with the given xlink:label
			 * @param label
			 */
			MetsStructMapType[] getStructMaps(String label){
				ArrayList<MetsStructMapType> smaps = new ArrayList<MetsStructMapType>();
				for(Enumeration<MetsStructMapType> en = structLinks.keys();en.hasMoreElements();){
					MetsStructMapType smap = en.nextElement();
					ArrayList<String> labels = structLinks.get(smap);
					int i = Collections.binarySearch(labels, label);
					if(i>=0 && !smaps.contains(smap)){
						smaps.add(smap);
					}
				}
				return smaps.toArray(new MetsStructMapType[0]);
			}
			
			/**
			 * Return a count of the number of times that the given xlink:label 
			 * occurs (should be just one unless there is an error)
			 * @param label
			 * @return
			 */
			int getCount(String label){
				int count=0;
				for(Enumeration<MetsStructMapType> en = structLinks.keys();en.hasMoreElements();){
					MetsStructMapType smap = en.nextElement();
					ArrayList<String> labels = structLinks.get(smap);
					count = count + Collections.frequency(labels, label);
				}
				return count;
			}
			
			String[] getAllLabels(){
				ArrayList<String> allLabels = new ArrayList<String>();
				for(Enumeration<ArrayList<String>> en = structLinks.elements();en.hasMoreElements();){
					allLabels.addAll(en.nextElement());
				}
				return allLabels.toArray(new String[0]);
			}
		}
		
		HaSMETSAppender app = new HaSMETSAppender("ValidateProfile");
		this.setValidationProfileAppender(app);
		logger.addAppender(app);

		// TODO: Check Premis identifierValue/identifierType for uniqueness
		// within this document

		// TODO: Check for orphaned MdSecType elements (warning)

		if (metsDoc == null) {
			throw new RuntimeException("There is no METS document to validate");
		} else {
			XmlOptions validateOptions = new XmlOptions();
			ArrayList<XmlError> errorList = new ArrayList<XmlError>();
			validateOptions.setErrorListener(errorList);

			if (!metsDoc.validate(validateOptions)) {
				for (int i = 0; i < errorList.size(); i++) {
					if (errorList.get(i).getSeverity() == XmlError.SEVERITY_ERROR)
						logger.error(errorList.get(i));
					else if (errorList.get(i).getSeverity() == XmlError.SEVERITY_WARNING)
						logger.warn(errorList.get(i));
					else
						logger.info(errorList.get(i));
				}
			} else {

				MetsMetsDocument.Mets m = metsDoc.getMets();

				// get some of the primary required sections from the document
				MetsMdSecType primaryRep = null;
				PremisObjectDocument primaryRepObj = null;
				try {
					primaryRep = metsP.getPrimaryRepresentation();
					primaryRepObj = (PremisObjectDocument) metsP
							.getMDSecXmlObject(primaryRep);
				} catch (HaSMETSProfileException e1) {
					if (pt != HaSMETSProfile.PackageType.SIP) {
						logger.error(XmlError.forObject(e1.getMessage(), m));
					}
				}

				MetsMdSecType primaryDmdSec = null;
				ModsModsDocument primaryDmdSecMods = null;
				try {
					primaryDmdSec = metsP.getPrimaryDmdSec();
					primaryDmdSecMods = (ModsModsDocument) metsP
							.getMDSecXmlObject(primaryDmdSec);
				} catch (HaSMETSProfileException e1) {
					logger.error(XmlError.forObject(e1.getMessage(), m));
				}

				MetsStructMapType primaryStructMap = null;
				try {
					primaryStructMap = metsP.getPrimaryStructMap();
				} catch (HaSMETSProfileException e1) {
					logger.error(XmlError.forObject(e1.getMessage(), m));
				}

				// check the root element
				if (pt != HaSMETSProfile.PackageType.SIP
						&& (!m.isSetOBJID() || m.getOBJID().length() == 0)) {
					logger.error(XmlError
									.forObject(
											"The mets element is missing an OBJID attribute",
											m));
				} else if (primaryRepObj != null) {
					// check that the primary representation techMD also
					// contains the same OBJID
					String objid = m.getOBJID();
					if (objid != null) {
						PremisObjectDocument.Object.ObjectIdentifier ids[] = primaryRepObj
								.getObject().getObjectIdentifierArray();
						boolean foundId = false;
						for (int i = 0; i < ids.length; i++) {
							if (ids[i].getObjectIdentifierValue().compareTo(
									objid) == 0) {
								foundId = true;
								break;
							}
						}
						if (!foundId) {
							logger.error(XmlError
											.forObject(
													"The PRIMARY_REPRESENTATION is missing an objectIdentifierValue that matches the OBJID attribute",
													primaryRepObj.getObject()));
						}
					} else {
						logger.error(XmlError
										.forObject(
												"This document contains a PRIMARY_REPRESENTATION, but the mets element is missing an OBJID attribute",
												m));
					}
				}

				if (!m.isSetLABEL() || m.getLABEL().length() == 0) {
					logger.error(XmlError
									.forObject(
											"The mets element is missing a LABEL attribute",
											m));
				}
				if (!m.isSetPROFILE()
						|| (m.getPROFILE().compareTo(metsP.PROFILE_URI) != 0 && !metsP.isSubclassOfThisProfile(m
								.getPROFILE()))) {
					logger.error(XmlError
									.forObject(
											"The mets element does not have a PROFILE attribute containing one of official 'Hub and Spoke' URIs registered with the Library of Congess",
											m));
				}
				// if there is an OBJID attribute there must be a metsHdr with
				// dates
				if (m.isSetOBJID() && m.getOBJID().length() > 0
						&& m.isSetMetsHdr()) {
					if (!m.getMetsHdr().isSetCREATEDATE()) {
						logger.error(XmlError
										.forObject(
												"The metsHdr is missing a CREATEDATE attribute",
												m.getMetsHdr()));
					} else if (!m.getMetsHdr().isSetLASTMODDATE()) {
						logger.error(XmlError
										.forObject(
												"The metsHdr is missing a LASTMODDATE attribute",
												m.getMetsHdr()));
					} else {
						if (m.getMetsHdr().getLASTMODDATE().compareTo(
								m.getMetsHdr().getCREATEDATE()) < 0) {
							logger.error(XmlError.forObject(
									"The LASTMODDATE is before the CREATEDATE",
									m.getMetsHdr().xgetLASTMODDATE()));
						}
						if (m.getMetsHdr().getCREATEDATE().compareTo(
								Calendar.getInstance()) > 0) {
							logger.warn(XmlError
											.forObject(
													"The CREATEDATE is later than the current date",
													XmlError.SEVERITY_WARNING,
													m.getMetsHdr()
															.xgetCREATEDATE()));
						}
						if (m.getMetsHdr().getLASTMODDATE().compareTo(
								Calendar.getInstance()) > 0) {
							logger.warn(XmlError
											.forObject(
													"The LASTMODDATE is later than the current date",
													XmlError.SEVERITY_WARNING,
													m.getMetsHdr()
															.xgetLASTMODDATE()));
						}
					}
				} else if (m.isSetOBJID()) {
					logger.error(XmlError
									.forObject(
											"The mets element has an OBJID attribute, but the mets element is missing a metsHdr sub-element",
											m));
				}

				// check that if there is a altRecordID section all the alt
				// record ids are also listed
				// in the primary representation
				if (primaryRepObj != null && m.isSetMetsHdr()
						&& m.getMetsHdr().getAltRecordIDArray().length > 0) {
					MetsMetsType.MetsHdr.AltRecordID altid[] = m.getMetsHdr()
							.getAltRecordIDArray();
					PremisObjectDocument.Object.ObjectIdentifier ids[] = primaryRepObj
							.getObject().getObjectIdentifierArray();
					for (int i = 0; i < altid.length; i++) {
						String objid = altid[i].getStringValue();
						String typ = altid[i].getTYPE();
						boolean foundId = false;
						for (int j = 0; j < ids.length; j++) {
							if (ids[j].getObjectIdentifierValue().compareTo(
									objid) == 0
									&& ids[j].getObjectIdentifierType()
											.compareTo(typ) == 0) {
								foundId = true;
								break;
							}
						}
						if (!foundId) {
							logger.error(XmlError
											.forObject(
													"The PRIMARY_REPRESENTATION is missing an objectIdentifierValue element that matches an altRecordID value in the metsHdr: "
															+ typ + "-" + objid,
													primaryRepObj.getObject()));
						}
					}
				}

				// check that the primary representation is referenced from the
				// primary structMap
				if (primaryStructMap != null && primaryRep != null
						&& primaryRepObj != null) {
					MetsDivType rootDiv = primaryStructMap.getDiv();
					if (!rootDiv.isSetADMID()
							|| !rootDiv.getADMID().contains(primaryRep.getID())) {
						logger.error(XmlError
										.forObject(
												"The root div element of the PRIMARY_STRUCTMAP does not reference the PRIMARY_REPRESENTATION",
												primaryStructMap));
					}
					if (primaryRepObj
							.getObject()
							.getObjectCategory()
							.compareTo(
									HaSMETSProfile.PREMISObjectCategory.REPRESENTATION
											.toString()) != 0) {
						logger.error(XmlError
										.forObject(
												"The PRIMARY_REPRESENTATION does not have an objectCategory of 'REPRESENTATION'",
												primaryRepObj.getObject()));
					}
				}
				// check for linking versus embedding
				MetsMdSecType dmdSecs[] = m.getDmdSecArray();
				for (int i = 0; i < dmdSecs.length; i++) {
					if (dmdSecs[i].isSetMdRef() && dmdSecs[i].isSetMdWrap()) {
						logger.error(XmlError
										.forObject(
												"The dmdSec element has both mdWrap and mdRef sub-elements",
												dmdSecs[i]));
					}
				}
				MetsAmdSecType amdSecs[] = m.getAmdSecArray();
				for (int i = 0; i < amdSecs.length; i++) {
					MetsMdSecType mds[] = amdSecs[i].getDigiprovMDArray();
					for (int j = 0; j < mds.length; j++) {
						if (mds[j].isSetMdRef() && mds[j].isSetMdWrap()) {
							logger.error(XmlError
											.forObject(
													"The digiprovMD element has both mdWrap and mdRef sub-elements",
													mds[j]));
						}
					}
					mds = amdSecs[i].getRightsMDArray();
					for (int j = 0; j < mds.length; j++) {
						if (mds[j].isSetMdRef() && mds[j].isSetMdWrap()) {
							logger.error(XmlError
											.forObject(
													"The rightsMD element has both mdWrap and mdRef sub-elements",
													mds[j]));
						}
					}
					mds = amdSecs[i].getSourceMDArray();
					for (int j = 0; j < mds.length; j++) {
						if (mds[j].isSetMdRef() && mds[j].isSetMdWrap()) {
							logger.error(XmlError
											.forObject(
													"The sourceMD element has both mdWrap and mdRef sub-elements",
													mds[j]));
						}
					}
					mds = amdSecs[i].getTechMDArray();
					for (int j = 0; j < mds.length; j++) {
						if (mds[j].isSetMdRef() && mds[j].isSetMdWrap()) {
							logger.error(XmlError
											.forObject(
													"The techMD element has both mdWrap and mdRef sub-elements",
													mds[j]));
						}
					}
				}

				// check the primary dmdSec
				if (primaryDmdSec != null) {
					if (!primaryDmdSec.isSetMdWrap()
							|| !primaryDmdSec.getMdWrap().isSetXmlData()) {
						logger.error(XmlError
										.forObject(
												"The PRIMARY_DMDSEC is missing mdWrap and xmlData sub-elements",
												primaryDmdSec));
					}
					if (!primaryDmdSec.isSetCREATED()) {
						logger.error(XmlError
										.forObject(
												"The PRIMARY_DMDSEC is missing a CREATED attribute",
												primaryDmdSec));
					} else {
						if (primaryDmdSec.getCREATED().compareTo(
								Calendar.getInstance()) > 0) {
							logger.warn(XmlError
											.forObject(
													"The PRIMARY_DMDSEC CREATED date is later than the current date",
													XmlError.SEVERITY_WARNING,
													primaryDmdSec.xgetCREATED()));
						}
						if (m.isSetMetsHdr()
								&& m.getMetsHdr().isSetLASTMODDATE()
								&& metsP.isDateAfterLastModDate(primaryDmdSec
										.getCREATED())) {
							logger.warn(XmlError
											.forObject(
													"The PRIMARY_DMDSEC CREATED date is later than the LASTMODDATE date of the METS document",
													XmlError.SEVERITY_WARNING,
													primaryDmdSec.xgetCREATED()));
						}
					}

					if (primaryDmdSec.isSetMdWrap()
							&& primaryDmdSec.getMdWrap().isSetXmlData()) {
						Node n = null;
						n = metsP.getXmlDataAny(primaryDmdSec.getMdWrap()
								.getXmlData());
						QName qn = metsP.getQName(n);
						if (n == null) {
							logger.error(XmlError
											.forObject(
													"The PRIMARY_DMDSEC xmlData sub-element is empty",
													primaryDmdSec.getMdWrap()
															.getXmlData()));
						}
						if (qn.getLocalPart().compareTo("mods") != 0
								|| qn.getNamespaceURI().compareTo(HaSMETSProfile.MODS_NS) != 0) {
							logger.error(XmlError
											.forObject(
													"The PRIMARY_DMDSEC does not contain MODS metadata",
													primaryDmdSec.getMdWrap()
															.getXmlData()));
						}

						// check that the primary dmdSec MODS minimimally
						// conforms to the Aquifer profile
						// http://www.diglib.org/aquifer/dlfmodsimplementationguidelines_finalnov2006.pdf
						// SIPs are not required to conform to the Aquifer
						// profile
						if (primaryDmdSecMods != null
								&& pt != HaSMETSProfile.PackageType.SIP) {
							this.validateAquiferModsDocument(metsP,pt,
									primaryDmdSecMods);
						}
					}

					// check whether the digiprovMD referenced by the primary
					// dmdSec is valid
					this.validateDigiprovMDPremisEventForDmdSec(metsP,pt,
							primaryDmdSec);

					// check that the primary dmdSec is referenced from each
					// structMap
					MetsStructMapType smaps[] = m.getStructMapArray();
					for (int i = 0; i < smaps.length; i++) {
						if (!smaps[i].getDiv().isSetDMDID()
								|| !smaps[i].getDiv().getDMDID().contains(
										primaryDmdSec.getID())) {
							logger.error(XmlError
											.forObject(
													"The structMap element div sub-element does not reference the PRIMARY_DMDSEC",
													smaps[i].getDiv()));
						}
					}
				}

				// check each alternate dmdSec
				MetsMdSecType altdmds[] = metsP.getAlternateDmdSecArray();
				for (int i = 0; i < altdmds.length; i++) {
					// check for CREATED
					if (!altdmds[i].isSetCREATED()) {
						logger.error(XmlError
										.forObject(
												"The ALTERNATE_DMDSEC  is missing a CREATED attribute",
												altdmds[i]));
					} else {
						if (altdmds[i].getCREATED().compareTo(
								Calendar.getInstance()) > 0) {
							logger.warn(XmlError
											.forObject(
													"The ALTERNATE_DMDSEC CREATED date is later than the current date",
													XmlError.SEVERITY_WARNING,
													altdmds[i].xgetCREATED()));
						}
						if (m.isSetMetsHdr()
								&& m.getMetsHdr().isSetLASTMODDATE()
								&& metsP.isDateAfterLastModDate(altdmds[i]
										.getCREATED())) {
							logger.warn(XmlError
											.forObject(
													"The ALTERNATE_DMDSEC CREATED date is later than the LASTMODDATE date of the METS document",
													XmlError.SEVERITY_WARNING,
													altdmds[i].xgetCREATED()));
						}
					}

					this.validateDigiprovMDPremisEventForDmdSec(metsP,pt, altdmds[i]);

					// check that the alternate dmdSecs are referenced from each
					// structMap
					MetsStructMapType smaps[] = m.getStructMapArray();
					for (int j = 0; j < smaps.length; j++) {
						if (!smaps[j].getDiv().isSetDMDID()
								|| !smaps[j].getDiv().getDMDID().contains(
										altdmds[i].getID())) {
							logger.error(XmlError
											.forObject(
													"The structMap element div sub-element does not reference the ALTERNATE_DMDSEC",
													smaps[j].getDiv()));
						}
					}
				}

				// check that ADMID attributes only reference
				// techMD,sourceMD,rightsMD, or digiprovMD elements
				String xql = "declare namespace m='" + HaSMETSProfile.METS_NS
						+ "';.//m:*/@ADMID";
				XmlObject idrs[] = m.selectPath(xql);
				for (int i = 0; i < idrs.length; i++) {
					XmlIDREFS refs = (XmlIDREFS) idrs[i];
					for (Iterator it = refs.getListValue().iterator(); it
							.hasNext();) {
						XmlObject xobj = metsP.getXmlObject((String) it.next());
						QName qn = metsP.getQName(xobj.getDomNode());
						if (qn.getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) != 0
								|| (qn.getLocalPart().compareTo("techMD") != 0
										&& qn.getLocalPart().compareTo(
												"sourceMD") != 0
										&& qn.getLocalPart().compareTo(
												"rightsMD") != 0 && qn
										.getLocalPart().compareTo("digiprovMD") != 0)) {
							logger.error(XmlError
											.forObject(
													"The ADMID attribute references an element which is not a techMD, sourceMD, rightsMD, or digiprovMD element",
													refs));
						}
						// for digiprovMD the only allowable child is a Premis
						// event element
						if (qn.getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) == 0
								&& qn.getLocalPart().compareTo("digiprovMD") == 0) {
							try {
								XmlObject xobj2 = metsP
										.getMDSecXmlObject((MetsMdSecType) xobj);
								qn = metsP.getQName(metsP
										.getRootElementFromXmlData(xobj2));
								if (qn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0
										|| qn.getLocalPart().compareTo("event") != 0) {
									logger.error(XmlError
													.forObject(
															"The digiprovMD element does not contain a Premis event element",
															xobj2));
								}
							} catch (HaSMETSProfileException e) {
								logger.error(XmlError.forObject(
										e.getMessage(), xobj));
							}
						}
					}
				}

				// check that the PREMIS premis element is never used in the
				// amdSec
				xql = "declare namespace m='" + HaSMETSProfile.METS_NS
						+ "';declare namespace p='" + HaSMETSProfile.PREMIS_NS
						+ "';.//m:amdSec//p:premis";
				XmlObject premis[] = m.selectPath(xql);
				if (premis.length > 0) {
					logger.error(XmlError
									.forObject(
											"The PREMIS root element 'premis' is not allowed by this profile",
											premis[0]));
				}

				// check that xmlData sections contain only a single child root
				// element
				xql = "declare namespace m='" + HaSMETSProfile.METS_NS
						+ "';.//m:amdSec//m:xmlData";
				XmlObject xmldata[] = m.selectPath(xql);
				for (int i = 0; i < xmldata.length; i++) {
					XmlObject[] ns = xmldata[i].selectPath("*");
					if (ns.length > 1) {
						logger.error(XmlError
										.forObject(
												"The xmlData element contains multiple children sub-elements",
												xmldata[i]));
					}
				}

				// check the linkages between premis entities
				// any @RelObjectXmlID must reference techMD
				xql = "declare namespace p='"
						+ HaSMETSProfile.PREMIS_NS
						+ "'; declare namespace m='"
						+ HaSMETSProfile.METS_NS
						+ "'; .//m:amdSec//p:relatedObjectIdentification/@RelObjectXmlID";
				XmlObject idrefs[] = m.selectPath(xql);
				for (int i = 0; i < idrefs.length; i++) {
					XmlIDREF idref = (XmlIDREF) idrefs[i];
					XmlObject r = metsP.getXmlObject(idref.getStringValue());
					if (r.getDomNode().getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) != 0
							|| r.getDomNode().getLocalName()
									.compareTo("techMD") != 0) {
						logger.error(XmlError
										.forObject(
												"The Premis RelObjectXmlID attribute does not reference a techMD element",
												idref));
					}
					try {
						XmlObject xobj = metsP
								.getMDSecXmlObject((MetsMdSecType) r);
						Node nn = metsP.getRootElementFromXmlData(xobj);
						if (nn.getLocalName().compareTo("object") != 0
								|| nn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0) {
							logger.error(XmlError
											.forObject(
													"The techMD element referenced by the Premis RelObjectXmlID attribute does not contina Premis object element",
													r));
						}
					} catch (HaSMETSProfileException e) {
						logger.error(XmlError.forObject(e.getMessage(), r));
					}
				}
				// any @RelEventXmlID must reference digiprovMD
				xql = "declare namespace p='"
						+ HaSMETSProfile.PREMIS_NS
						+ "'; declare namespace m='"
						+ HaSMETSProfile.METS_NS
						+ "'; .//m:amdSec//p:relatedEventIdentification/@RelEventXmlID";
				idrefs = m.selectPath(xql);
				for (int i = 0; i < idrefs.length; i++) {
					XmlIDREF idref = (XmlIDREF) idrefs[i];
					XmlObject r = metsP.getXmlObject(idref.getStringValue());
					if (r.getDomNode().getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) != 0
							|| r.getDomNode().getLocalName().compareTo(
									"digiprovMD") != 0) {
						logger.error(XmlError
										.forObject(
												"The Premis RelEventXmlID attribute does not reference a digiprovMD element",
												idref));
					}
					try {
						XmlObject xobj = metsP
								.getMDSecXmlObject((MetsMdSecType) r);
						Node nn = metsP.getRootElementFromXmlData(xobj);
						if (nn.getLocalName().compareTo("event") != 0
								|| nn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0) {
							logger.error(XmlError
											.forObject(
													"The digiprovMD element referenced by the Premis RelEventXmlID attribute does not contain a Premis event element",
													r));
						}
					} catch (HaSMETSProfileException e) {
						logger.error(XmlError.forObject(e.getMessage(), r));
					}
				}
				// any @LinkEventXmlID must reference digiprovMD
				xql = "declare namespace p='"
						+ HaSMETSProfile.PREMIS_NS
						+ "'; declare namespace m='"
						+ HaSMETSProfile.METS_NS
						+ "'; .//m:amdSec//p:linkingEventIdentifier/@LinkEventXmlID";
				idrefs = m.selectPath(xql);
				for (int i = 0; i < idrefs.length; i++) {
					XmlIDREF idref = (XmlIDREF) idrefs[i];
					XmlObject r = metsP.getXmlObject(idref.getStringValue());
					if (r.getDomNode().getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) != 0
							|| r.getDomNode().getLocalName().compareTo(
									"digiprovMD") != 0) {
						logger.error(XmlError
										.forObject(
												"The Premis LinkEventXmlID attribute does not reference a digiprovMD element",
												idref));
					}
					try {
						XmlObject xobj = metsP
								.getMDSecXmlObject((MetsMdSecType) r);
						Node nn = metsP.getRootElementFromXmlData(xobj);
						if (nn.getLocalName().compareTo("event") != 0
								|| nn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0) {
							logger.error(XmlError
											.forObject(
													"The digiprovMD element referenced by the Premis LinkEventXmlID attribute does not contain a Premis event element",
													r));
						}
					} catch (HaSMETSProfileException e) {
						logger.error(XmlError.forObject(e.getMessage(), r));
					}
				}
				// any @LinkPermissionStatementXmlID must reference digiprovMD
				xql = "declare namespace p='"
						+ HaSMETSProfile.PREMIS_NS
						+ "'; declare namespace m='"
						+ HaSMETSProfile.METS_NS
						+ "'; .//m:amdSec//p:linkingPermissionStatementIdentifier/@LinkPermissionStatementXmlID";
				idrefs = m.selectPath(xql);
				for (int i = 0; i < idrefs.length; i++) {
					XmlIDREF idref = (XmlIDREF) idrefs[i];
					XmlObject r = metsP.getXmlObject(idref.getStringValue());
					if (r.getDomNode().getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) != 0
							|| r.getDomNode().getLocalName().compareTo(
									"rightsMD") != 0) {
						logger.error(XmlError
										.forObject(
												"The Premis LinkPermissionStatementXmlID attribute does not reference a rightsMD element",
												idref));
					}
					try {
						XmlObject xobj = metsP
								.getMDSecXmlObject((MetsMdSecType) r);
						Node nn = metsP.getRootElementFromXmlData(xobj);
						if (nn.getLocalName().compareTo("rights") != 0
								|| nn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0) {
							logger.error(XmlError
											.forObject(
													"The rightsMD element referenced by the Premis LinkPermissionStatementXmlID attribute does not contain a Premis rights element",
													r));
						}
					} catch (HaSMETSProfileException e) {
						logger.error(XmlError.forObject(e.getMessage(), r));
					}
				}
				// any @LinkAgentXmlID must reference digiprovMD or rightsMD
				xql = "declare namespace p='"
						+ HaSMETSProfile.PREMIS_NS
						+ "'; declare namespace m='"
						+ HaSMETSProfile.METS_NS
						+ "'; .//m:amdSec//p:linkingAgentIdentifier/@LinkAgentXmlID";
				idrefs = m.selectPath(xql);
				for (int i = 0; i < idrefs.length; i++) {
					XmlIDREF idref = (XmlIDREF) idrefs[i];
					XmlObject r = metsP.getXmlObject(idref.getStringValue());
					if (r.getDomNode().getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) != 0
							|| (r.getDomNode().getLocalName().compareTo(
									"rightsMD") != 0 && r.getDomNode()
									.getLocalName().compareTo("digiprovMD") != 0)) {
						logger.error(XmlError
										.forObject(
												"The Premis LinkAgentXmlID attribute does not reference a digiprovMD or rightsMD element",
												idref));
					}
					try {
						XmlObject xobj = metsP
								.getMDSecXmlObject((MetsMdSecType) r);
						Node nn = metsP.getRootElementFromXmlData(xobj);
						if (nn.getLocalName().compareTo("agent") != 0
								|| nn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0) {
							logger.error(XmlError
											.forObject(
													"The digiprovMD or rightsMD element referenced by the Premis LinkAgentXmlID attribute does not contain a Premis agent element",
													r));
						}
					} catch (HaSMETSProfileException e) {
						logger.error(XmlError.forObject(e.getMessage(), r));
					}
				}
				// any @LinkObjectXmlID must reference techMD
				xql = "declare namespace p='"
						+ HaSMETSProfile.PREMIS_NS
						+ "'; declare namespace m='"
						+ HaSMETSProfile.METS_NS
						+ "'; .//m:amdSec//p:linkingObjectIdentifier/@LinkObjectXmlID";
				idrefs = m.selectPath(xql);
				for (int i = 0; i < idrefs.length; i++) {
					XmlIDREF idref = (XmlIDREF) idrefs[i];
					XmlObject r = metsP.getXmlObject(idref.getStringValue());
					if (r.getDomNode().getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) != 0
							|| r.getDomNode().getLocalName()
									.compareTo("techMD") != 0) {
						logger.error(XmlError
										.forObject(
												"The Premis LinkObjectXmlID attribute does not reference a techMD element",
												idref));
					}
					try {
						XmlObject xobj = metsP
								.getMDSecXmlObject((MetsMdSecType) r);
						Node nn = metsP.getRootElementFromXmlData(xobj);
						if (nn.getLocalName().compareTo("object") != 0
								|| nn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0) {
							logger.error(XmlError
											.forObject(
													"The techMD element referenced by the Premis LinkObjectXmlID attribute does not contain a Premis object element",
													r));
						}
					} catch (HaSMETSProfileException e) {
						logger.error(XmlError.forObject(e.getMessage(), r));
					}
				}
				// any @linkingObjectXmlID must reference techMD
				xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS
						+ "'; declare namespace m='" + HaSMETSProfile.METS_NS
						+ "'; .//m:amdSec//p:linkingObject/@linkingObjectXmlID";
				idrefs = m.selectPath(xql);
				for (int i = 0; i < idrefs.length; i++) {
					XmlIDREF idref = (XmlIDREF) idrefs[i];
					XmlObject r = metsP.getXmlObject(idref.getStringValue());
					if (r.getDomNode().getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) != 0
							|| r.getDomNode().getLocalName()
									.compareTo("techMD") != 0) {
						logger.error(XmlError
										.forObject(
												"The Premis linkingObjectXmlID attribute does not reference a techMD element",
												idref));
					}
					try {
						XmlObject xobj = metsP
								.getMDSecXmlObject((MetsMdSecType) r);
						Node nn = metsP.getRootElementFromXmlData(xobj);
						if (nn.getLocalName().compareTo("object") != 0
								|| nn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0) {
							logger.error(XmlError
											.forObject(
													"The techMD element referenced by the Premis linkingObjectXmlID attribute does not contain a Premis object element",
													r));
						}
					} catch (HaSMETSProfileException e) {
						logger.error(XmlError.forObject(e.getMessage(), r));
					}
				}
				// any @GrantAgentXmlID must reference digiprovMD or rightsMD
				xql = "declare namespace p='" + HaSMETSProfile.PREMIS_NS
						+ "'; declare namespace m='" + HaSMETSProfile.METS_NS
						+ "'; .//m:amdSec//p:grantingAgent/@GrantAgentXmlID";
				idrefs = m.selectPath(xql);
				for (int i = 0; i < idrefs.length; i++) {
					XmlIDREF idref = (XmlIDREF) idrefs[i];
					XmlObject r = metsP.getXmlObject(idref.getStringValue());
					if (r.getDomNode().getNamespaceURI().compareTo(HaSMETSProfile.METS_NS) != 0
							|| (r.getDomNode().getLocalName().compareTo(
									"digiprovMD") != 0 && r.getDomNode()
									.getLocalName().compareTo("rightsMD") != 0)) {
						logger.error(XmlError
										.forObject(
												"The Premis GrantAgentXmlID attribute does not reference a digiprovMD or rightsMD element",
												idref));
					}
					try {
						XmlObject xobj = metsP
								.getMDSecXmlObject((MetsMdSecType) r);
						Node nn = metsP.getRootElementFromXmlData(xobj);
						if (nn.getLocalName().compareTo("agent") != 0
								|| nn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0) {
							logger.error(XmlError
											.forObject(
													"The digiprovMD or rightsMD element referenced by the Premis GrantAgentXmlID attribute does not contain a Premis agent element",
													r));
						}
					} catch (HaSMETSProfileException e) {
						logger.error(XmlError.forObject(e.getMessage(), r));
					}
				}

				// check the files
				this.validateFiles(metsP,pt, openEachFile);

				// check all the structMaps
				MetsStructMapType smaps[] = m.getStructMapArray();
				for (int i = 0; i < smaps.length; i++) {
					this.validateDigiprovMDPremisEventForStructMap(metsP,pt,
							smaps[i]);
					this.validateTechMDPremisObjectForStructMap(metsP,pt, smaps[i]);
				}

				// check that agents referenced from events or rights are
				// correctly wrapped in xmlData elements
				xql = "declare namespace p='"
						+ HaSMETSProfile.PREMIS_NS
						+ "'; .//p:event/p:linkingAgentIdentifier/@LinkAgentXmlID | .//p:rights/p:grantingAgent/@GrantAgentXmlID";
				XmlObject agentIDs[] = metsDoc.selectPath(xql);
				for (int i = 0; i < agentIDs.length; i++) {
					XmlObject md = metsP.getXmlObject(((XmlIDREF) agentIDs[i])
							.getStringValue());
					if (md instanceof MetsMdSecType) {
						MetsMdSecType mdsec = (MetsMdSecType) md;
						if (!mdsec.isSetMdWrap()
								|| !mdsec.getMdWrap().isSetXmlData()) {
							logger.error(XmlError
											.forObject(
													"The MetsMdSecType which is being referenced as a Premis agent does not wrap a Premis agent element",
													mdsec));
						} else {
							Node n = metsP.getRootElementFromXmlData(mdsec
									.getMdWrap().getXmlData());
							QName qn = metsP.getQName(n);
							if (qn.getNamespaceURI().compareTo(HaSMETSProfile.PREMIS_NS) != 0
									|| qn.getLocalPart().compareTo("agent") != 0) {
								logger.error(XmlError
												.forObject(
														"The XmlData which is being referenced as a Premis agent does not wrap a Premis agent element",
														mdsec));

							}
						}
					} else {
						logger.error(XmlError
										.forObject(
												"The Premis IDREF attribute does not reference a valid Mets amdSec sub-element",
												agentIDs[i]));
					}
				}

				// check for possible duplicate agents
				xql = "declare namespace p='"
						+ HaSMETSProfile.PREMIS_NS
						+ "'; .//p:agent/p:agentIdentifier/p:agentIdentifierValue";
				XmlObject agents[] = metsDoc.selectPath(xql);
				ArrayList<String> allAgentIDs = new ArrayList<String>();
				for (int i = 0; i < agents.length; i++) {
					if (allAgentIDs.contains(((XmlString) agents[i])
							.getStringValue())) {
						logger.warn(XmlError.forObject(
								"Duplicate Premis agent identifier was found",
								XmlError.SEVERITY_WARNING, agents[i]));
					} else {
						allAgentIDs.add(((XmlString) agents[i])
								.getStringValue());
					}
				}
				
				
				//get ready to process structLinks
				StructLinks structLinks = new StructLinks();

				// check for the uniqueness of xlink:label attributes across all div elements
				String labels[] = structLinks.getAllLabels();
				for (int i = 0; i < labels.length; i++) {
					if(structLinks.getCount(labels[i])>1){
						logger.error(XmlError.forObject("Duplicate xlink:label='" + labels[i] + "' in div elements",metsDoc));
					}
				}

				// check structLinks, make sure that both the xlink:from and
				// xlink:to point to a xlink:label
				// in the same structMap
				if(metsDoc.getMets().isSetStructLink()){
					MetsStructLinkType.SmLink smlnks[] = metsDoc.getMets().getStructLink().getSmLinkArray();	
					for(int i =0;i<smlnks.length;i++){
						MetsStructMapType smapFrom[] = structLinks.getStructMaps(smlnks[i].getFrom());
						MetsStructMapType smapTo[] = structLinks.getStructMaps(smlnks[i].getTo());
						
						if(smapFrom.length==0){
							logger.error(XmlError.forObject(
								"The smLink xlink:from='" + smlnks[i].getFrom() + "' attribute references an xlink:label attribute that does not exist",
								smlnks[i]));
						} else if(smapFrom.length>1){
							logger.error(XmlError.forObject(
									"The smLink xlink:from='" + smlnks[i].getFrom() + "' attribute references duplicate xlink:label attribute in different structMap elements",
									smlnks[i]));
						} else if(smapTo.length==0){
							logger.error(XmlError.forObject(
									"The smLink xlink:to='" + smlnks[i].getTo() + "' attribute references an xlink:label attribute that does not exist",
									smlnks[i]));
						} else if(smapTo.length>1){
							logger.error(XmlError.forObject(
									"The smLink xlink:to='" + smlnks[i].getTo() + "' attribute references duplicate xlink:label attribute in different structMap elements",
									smlnks[i]));
						} else if(smapFrom[0]!=smapTo[0]){
							logger.error(XmlError.forObject(
									"The smLink xlink:from='" + smlnks[i].getFrom() + "' and xlink:to='" + smlnks[i].getTo() + "' attribute reference xlink:label attributes in two different structMap elements",
									smlnks[i]));
						}
					}
				}
			}
		}
		logger.removeAppender(app);
		return (!app.hasErrors());
	}
	
	public void setValidationProfileAppender(HaSMETSAppender app){
		VALIDATION_APPENDER = app;
	}
	
	public HaSMETSAppender getValidationProfileAppender(){
		return VALIDATION_APPENDER;
	}
}
