package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.StationInfo;
public class StationInfoListHandler extends DefaultHandler {
	private List<StationInfo> stationInfoList = null;
	private StationInfo stationInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (stationInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("stationId".equals(tempString)) 
            	stationInfo.setStationId(new Integer(valueString).intValue());
            else if ("stationName".equals(tempString)) 
            	stationInfo.setStationName(valueString); 
            else if ("connectPerson".equals(tempString)) 
            	stationInfo.setConnectPerson(valueString); 
            else if ("telephone".equals(tempString)) 
            	stationInfo.setTelephone(valueString); 
            else if ("postcode".equals(tempString)) 
            	stationInfo.setPostcode(valueString); 
            else if ("address".equals(tempString)) 
            	stationInfo.setAddress(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("StationInfo".equals(localName)&&stationInfo!=null){
			stationInfoList.add(stationInfo);
			stationInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		stationInfoList = new ArrayList<StationInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("StationInfo".equals(localName)) {
            stationInfo = new StationInfo(); 
        }
        tempString = localName; 
	}

	public List<StationInfo> getStationInfoList() {
		return this.stationInfoList;
	}
}
