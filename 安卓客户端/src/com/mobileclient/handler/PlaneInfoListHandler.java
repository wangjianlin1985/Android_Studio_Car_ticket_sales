package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.PlaneInfo;
public class PlaneInfoListHandler extends DefaultHandler {
	private List<PlaneInfo> planeInfoList = null;
	private PlaneInfo planeInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (planeInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("seatId".equals(tempString)) 
            	planeInfo.setSeatId(new Integer(valueString).intValue());
            else if ("planeNumber".equals(tempString)) 
            	planeInfo.setPlaneNumber(valueString); 
            else if ("startStation".equals(tempString)) 
            	planeInfo.setStartStation(new Integer(valueString).intValue());
            else if ("endStation".equals(tempString)) 
            	planeInfo.setEndStation(new Integer(valueString).intValue());
            else if ("startDate".equals(tempString)) 
            	planeInfo.setStartDate(Timestamp.valueOf(valueString));
            else if ("seatType".equals(tempString)) 
            	planeInfo.setSeatType(new Integer(valueString).intValue());
            else if ("price".equals(tempString)) 
            	planeInfo.setPrice(new Float(valueString).floatValue());
            else if ("seatNumber".equals(tempString)) 
            	planeInfo.setSeatNumber(new Integer(valueString).intValue());
            else if ("leftSeatNumber".equals(tempString)) 
            	planeInfo.setLeftSeatNumber(new Integer(valueString).intValue());
            else if ("startTime".equals(tempString)) 
            	planeInfo.setStartTime(valueString); 
            else if ("endTime".equals(tempString)) 
            	planeInfo.setEndTime(valueString); 
            else if ("totalTime".equals(tempString)) 
            	planeInfo.setTotalTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("PlaneInfo".equals(localName)&&planeInfo!=null){
			planeInfoList.add(planeInfo);
			planeInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		planeInfoList = new ArrayList<PlaneInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("PlaneInfo".equals(localName)) {
            planeInfo = new PlaneInfo(); 
        }
        tempString = localName; 
	}

	public List<PlaneInfo> getPlaneInfoList() {
		return this.planeInfoList;
	}
}
