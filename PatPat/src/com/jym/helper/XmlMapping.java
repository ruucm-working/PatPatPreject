package com.jym.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class XmlMapping {
	public void XmlMapping(){}
	
	public static HashMap fieldMapping(String fileName, Context context, String fieldName){
		HashMap<String, Object> mapingXml = new HashMap<String, Object>();
		try{
			Resources res = context.getResources();
			int xmlResourceNumber = context.getResources()
										   .getIdentifier(fileName, "xml", context.getPackageName());
			XmlPullParser parsingXml = res.getXml(xmlResourceNumber);
			int eventType	= parsingXml.getEventType();
			boolean parsingStartFlag = false;
			boolean arrayFlag = false;
			String tagName  = null;
			String prevName = null;
			ArrayList<String> itemArray = null;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				eventType = parsingXml.getEventType();
				
				if(!parsingStartFlag){
					if(eventType == XmlPullParser.START_TAG){
						if(parsingXml.getName().equals(fieldName)){
							parsingStartFlag = true;
							tagName  = parsingXml.getName();
							prevName = tagName;
						}
					}//파싱시작 조건
				}else{
					switch(eventType){
						case XmlPullParser.START_TAG:
							tagName = parsingXml.getName();
							if(tagName.equals("item")){
								if(itemArray == null){
									itemArray = new ArrayList<String>();
									arrayFlag = true;
								}//item일경우 배열리스트를 생성
							}else{
								prevName = tagName;
							}
							
							break;
							
						case XmlPullParser.END_TAG:
							if(parsingXml.getName().equals(prevName) && arrayFlag){
								mapingXml.put(prevName, itemArray);
								itemArray = null;
								arrayFlag = false;
							}
							
							if(parsingXml.getName().equals(fieldName)){
								return mapingXml;
							}//파싱종료
							break;
							
						case XmlPullParser.TEXT:
							if(tagName.equals("item")){
								Log.d("bugfix", "아이템 추가 : " + parsingXml.getText());
								itemArray.add(parsingXml.getText());
							}else{
								mapingXml.put(tagName, parsingXml.getText());
							}
							
							break;
					}
				}
				
				parsingXml.next();
			}
		}catch(XmlPullParserException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static HashMap levelMapping(String fileName, Context context, int level){
		HashMap<String, Object> mapingXml = new HashMap<String, Object>();
		
		try{
			Resources res = context.getResources();
			int xmlResourceNumber = context.getResources()
										   .getIdentifier(fileName, "xml", context.getPackageName());
			XmlPullParser parsingXml = res.getXml(xmlResourceNumber);
			int eventType	= parsingXml.getEventType();
			boolean parsingStartFlag = false;
			boolean arrayFlag = false;
			String tagName  = null;
			String prevName = null;
			ArrayList<String> itemArray = null;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				eventType = parsingXml.getEventType();
				
				if(!parsingStartFlag){
					if(eventType == XmlPullParser.START_TAG){
						if(parsingXml.getName().equals("level")){
							if(parsingXml.getAttributeValue(0).equals("lavel_"+level)) parsingStartFlag = true;
						}
					}
				}else{
					switch(eventType){
						case XmlPullParser.START_TAG:
							tagName  = parsingXml.getName();
							if(tagName.equals("item")){
								if(itemArray == null){
									itemArray = new ArrayList<String>();
									arrayFlag = true;
								}
							}else{
								prevName = tagName;
							}
							
							break;
							
						case XmlPullParser.END_TAG:
							if(parsingXml.getName().equals(prevName) && arrayFlag){
								mapingXml.put(prevName, itemArray);
								itemArray = null;
								arrayFlag = false;
							}else if(parsingXml.getName().equals("level")){
								return mapingXml;
								//mappingEnd
							}
							break;
							
						case XmlPullParser.TEXT:
							if(tagName.equals("item")){
								itemArray.add(parsingXml.getText());
							}else{
								mapingXml.put(tagName, parsingXml.getText());
							}
							
							break;
					}
				}
				
				parsingXml.next();
			}
		}catch(XmlPullParserException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
}
