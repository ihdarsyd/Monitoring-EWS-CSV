package com.task.kp.service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.task.kp.model.Data;

public class FileService {
	public Map<String,String> labelValue(List<Data> listData, String startTime, String endTime) throws IOException {
		List<Data> listProOposisi = new ArrayList<Data>();
		List<Data> listProPemerintah = new ArrayList<Data>();
		Map<String, String> path = new HashMap<String, String>();
		
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String date = df.format(cal.getTime());
		
		for(Data  data: listData) {
			if(data.getLabel().equalsIgnoreCase("pro oposisi")) {
				listProOposisi.add(data);
			} else if(data.getLabel().equalsIgnoreCase("pro pemerintah")) {
				listProPemerintah.add(data);
			}
		}
		
		if(!listProPemerintah.isEmpty()) {
		path.put("CSV Report Pro Pemerintah "+date+"_"+startTime.substring(0,2)+"-"+endTime.substring(0,2), writetoCSV(listProPemerintah,
				"report_pro_permerintah"+"_"+date+"_"+startTime.substring(0,2)+"-"+endTime.substring(0,2)));
		}
		if(!listProOposisi.isEmpty()) {
		path.put("CSV Report Pro Oposisi "+date+"_"+startTime.substring(0,2)+"-"+endTime.substring(0,2), writetoCSV(listProOposisi,
				"report_pro_oposisi"+"_"+date+"_"+startTime.substring(0,2)+"-"+endTime.substring(0,2)));
		}
		
		return path;
	}

	private String writetoCSV(List<Data> listData, String nameFile) throws IOException {
		//String csvFile = "/home/ebdesk/"+nameFile+".csv";
		String csvFile = "/var/www/html/file/"+nameFile+".csv";
        FileWriter writer = new FileWriter(csvFile);
        
        CSVUtils.writeLine(writer, Arrays.asList("id, id_user, username, date_dt,sentiment, emotion, hashtag, post, type, typeMedia, url"));
        List<String> list = new ArrayList<String>();
        for(Data data : listData) {
        	list.add(data.toString());
        }
        CSVUtils.writeLine(writer, list);
        writer.flush();
        writer.close();
        
        return "http://patrol.blackeye.id/file/"+nameFile+".csv";
		
	}
}
