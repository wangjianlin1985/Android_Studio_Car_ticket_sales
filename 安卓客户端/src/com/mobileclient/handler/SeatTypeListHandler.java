package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SeatType;
public class SeatTypeListHandler extends DefaultHandler {
	private List<SeatType> seatTypeList = null;
	private SeatType seatType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (seatType != null) { 
            String valueString = new String(ch, start, length); 
            if ("seatTypeId".equals(tempString)) 
            	seatType.setSeatTypeId(new Integer(valueString).intValue());
            else if ("seatTypeName".equals(tempString)) 
            	seatType.setSeatTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SeatType".equals(localName)&&seatType!=null){
			seatTypeList.add(seatType);
			seatType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		seatTypeList = new ArrayList<SeatType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SeatType".equals(localName)) {
            seatType = new SeatType(); 
        }
        tempString = localName; 
	}

	public List<SeatType> getSeatTypeList() {
		return this.seatTypeList;
	}
}
