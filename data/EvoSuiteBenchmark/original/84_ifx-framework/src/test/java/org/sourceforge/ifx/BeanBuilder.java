/*
 * $Id: BeanBuilder.java,v 1.4 2004/03/05 02:17:24 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/test/org/sourceforge/ifx/BeanBuilder.java,v $
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.sourceforge.ifx;

import org.sourceforge.ifx.framework.simpletype.*;
import org.sourceforge.ifx.framework.complextype.*;
import org.sourceforge.ifx.framework.element.*;
import org.sourceforge.ifx.framework.interfaces.*;
import org.sourceforge.ifx.basetypes.*;

/**
 * Convenience builder for various IFX beans.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.4 $
 */
public class BeanBuilder {
    
    /**
     * Builds a BankSvcRq object for testing.
     * @return an IFX object containing a BankSvcRq request.
     */
    public static IFX buildBankSvcRq() {
        IFX ifx = new IFX();
          SignonRq signonRq = new SignonRq();
            ClientApp clientApp = new ClientApp();
              Name name = new Name();
              name.setString("Quicken 2020 Deluxe");
            clientApp.setName(name);
              Org org = new Org();
              org.setString("Quicken Corporation");
            clientApp.setOrg(org);
              Version version = new Version();
              version.setString("99.99");
            clientApp.setVersion(version);
          signonRq.setClientApp(clientApp);
            ClientDt clientDt = new ClientDt();
            clientDt.setString("2020-12-31T23:59:59.000000-00:00");
          signonRq.setClientDt(clientDt);
            CustLangPref custLangPref = new CustLangPref();
            custLangPref.setString("English");
          signonRq.setCustLangPref(custLangPref);
            EU_Cur euCur = new EU_Cur();
            euCur.setString("0");
          signonRq.setEU_Cur(euCur);
            IFXString id = new IFXString();
            id.setString("ID123456");
          signonRq.setId(id);
            SuppressEcho suppressEcho = new SuppressEcho();
            suppressEcho.setString("1");
          signonRq.setSuppressEcho(suppressEcho);
            SignonCert signonCert = new SignonCert();
              Certificate certificate = new Certificate();
                BinData binData = new BinData();
                binData.setString("0x00");
              certificate.setBinData(binData);
                BinLength binLength = new BinLength();
                binLength.setString("4");
              certificate.setBinLength(binLength);
                ContentType contentType = new ContentType();
                contentType.setString("Binary");
              certificate.setContentType(contentType);
            signonCert.setCertificate(certificate);
              CustId custId = new CustId();
                SPName spName = new SPName();
                spName.setString("superuser");
              custId.setSPName(spName);
            signonCert.setCustId(custId);
              GenSessKey genSessKey = new GenSessKey();
              genSessKey.setString("1");
            signonCert.setGenSessKey(genSessKey);
              SignonRole signonRole = new SignonRole();
              signonRole.setString("superuser");
            signonCert.setSignonRole(signonRole);
          signonRq.setSignonCert(signonCert);
        ifx.setSignonRq(signonRq);
          BankSvcRq bankSvcRq = new BankSvcRq();
            AcctInqRq acctInqRq = new AcctInqRq();
              RqUID rqUID = new RqUID();
              rqUID.setString("AB123456-1234-1234-1234-1234567890AB");
            acctInqRq.setRqUID(rqUID);
              CardAcctId cardAcctId = new CardAcctId();
                AcctId acctId = new AcctId();
                acctId.setString("ACC-123456-123456");
              cardAcctId.setAcctId(acctId);
                AcctType acctType = new AcctType();
                acctType.setString("Checking");
              cardAcctId.setAcctType(acctType);
            acctInqRq.setCardAcctId(cardAcctId);
              DeliveryMethod deliveryMethod = new DeliveryMethod();
            acctInqRq.setDeliveryMethod(deliveryMethod);
              IncBal incBal = new IncBal();
              incBal.setString("0");
            acctInqRq.setIncBal(incBal);
          bankSvcRq.setAcctInqRq(new AcctInqRq[] {acctInqRq});
          RqUID rqUID2 = new RqUID();
            rqUID2.setString("AB123456-1234-1234-1234-1234567890AB");
          bankSvcRq.setRqUID(rqUID2);
        ifx.setBankSvcRq(new BankSvcRq[] {bankSvcRq});
        return ifx;
    }

    /**
     * Same as buildBankSvcRq() method, but builds a new BankSvcRq IFX
     * request with multiple BankSvcRq elements.
     */
    public static IFX buildBankSvcRqMulti() {
        IFX ifx = buildBankSvcRq();
        BankSvcRq[] bankSvcRqOrig = ifx.getBankSvcRq();
        BankSvcRq[] bankSvcRqNew = new BankSvcRq[bankSvcRqOrig.length + 1];
        for (int i = 0; i < bankSvcRqOrig.length; i++) {
            bankSvcRqNew[i] = bankSvcRqOrig[i];
        }
        BankSvcRq anotherBankSvcRq = new BankSvcRq();
          AcctInqRq acctInqRq = new AcctInqRq();
            RqUID rqUID = new RqUID();
            rqUID.setString("AB123456-1234-1234-1234-1234567890CD");
          acctInqRq.setRqUID(rqUID);
            CardAcctId cardAcctId = new CardAcctId();
              AcctId acctId = new AcctId();
              acctId.setString("ACC-999999-123456");
            cardAcctId.setAcctId(acctId);
              AcctType acctType = new AcctType();
              acctType.setString("Checking");
            cardAcctId.setAcctType(acctType);
          acctInqRq.setCardAcctId(cardAcctId);
            DeliveryMethod deliveryMethod = new DeliveryMethod();
          acctInqRq.setDeliveryMethod(deliveryMethod);
            IncBal incBal = new IncBal();
            incBal.setString("0");
          acctInqRq.setIncBal(incBal);
        anotherBankSvcRq.setAcctInqRq(new AcctInqRq[] {acctInqRq});
          RqUID rqUID2 = new RqUID();
            rqUID2.setString("AB123456-1234-1234-1234-1234567890CD");
        anotherBankSvcRq.setRqUID(rqUID2);
        bankSvcRqNew[bankSvcRqOrig.length] = anotherBankSvcRq;
        ifx.setBankSvcRq(bankSvcRqNew);
        return ifx;
    }
}
