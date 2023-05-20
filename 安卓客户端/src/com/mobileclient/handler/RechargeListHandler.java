package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Recharge;
public class RechargeListHandler extends DefaultHandler {
	private List<Recharge> rechargeList = null;
	private Recharge recharge;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (recharge != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	recharge.setId(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	recharge.setUserObj(valueString); 
            else if ("money".equals(tempString)) 
            	recharge.setMoney(new Float(valueString).floatValue());
            else if ("chargeTime".equals(tempString)) 
            	recharge.setChargeTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Recharge".equals(localName)&&recharge!=null){
			rechargeList.add(recharge);
			recharge = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		rechargeList = new ArrayList<Recharge>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Recharge".equals(localName)) {
            recharge = new Recharge(); 
        }
        tempString = localName; 
	}

	public List<Recharge> getRechargeList() {
		return this.rechargeList;
	}
}
