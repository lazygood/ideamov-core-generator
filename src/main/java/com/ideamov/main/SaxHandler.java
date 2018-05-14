package com.ideamov.main;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ideamov.run.GeneratorCoreMap;

public class SaxHandler extends DefaultHandler {

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if ("property".equals(qName)) {

			String attr = attributes.getValue("name");

			GeneratorCoreMap.KEY.add(attr);

		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String content = new String(ch, start, length);

		content = content.trim();

		if (content.length() > 0) {

			GeneratorCoreMap.VALUE.add(content);

		}

	}

}
