package com.task.kp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.kp.model.Data;
import com.task.kp.singleton.Singleton;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RestApiController {
	String tableHbase = "cyber-post-v5";
	String url;

	@RequestMapping(value = "/hbase/scan/{regexKey}", method = RequestMethod.GET)
	public ResponseEntity<?> scanRegexRowKey(@PathVariable("regexKey") String regexKey) 
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Data tw = new Data();
		List<Cell> result = Singleton.getInstance().getHbaseService().
							scanRegexRowKey(tableHbase, regexKey);
		
		if (null==result) {
			System.out.println("result is null");
		}
		else {
			for (Cell cell : result) {
				String x = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), 
										  cell.getValueLength());
				tw = objectMapper.readValue(x, Data.class);
			}
		}
		 return new ResponseEntity<Data>(tw, HttpStatus.OK);
	}
	@RequestMapping(value = "/hbase/scan1", method = RequestMethod.GET)
	public ResponseEntity<?> scanColumn(@RequestParam(value = "start") String start,
										@RequestParam(value = "end") String end,
										@RequestParam(value = "account") String account ) 
										throws JsonParseException, JsonMappingException, IOException, ParseException {
		ObjectMapper objectMapper = new ObjectMapper();
		Data tw = new Data();
		List<Data> list = new ArrayList<Data>();
		List<Cell> result = Singleton.getInstance().getHbaseService().
							scanRegexColumn(tableHbase, unixTimeStamp(start), unixTimeStamp(end));
		try {
			if (result.isEmpty()) {
				return new ResponseEntity<String>("NULL", HttpStatus.OK);
			}
			
			else {
				for (Cell cell : result) {
					String x = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
					tw = objectMapper.readValue(x, Data.class);
					list.add(tw);
				}
				Map<String,String> path = Singleton.getInstance().getFileService().labelValue(list,start, end);
				sendToTelegram(path, account);
				
				return new ResponseEntity<Map<String,String>>(path, HttpStatus.OK);
				}
		}catch(NullPointerException e) {
			return new ResponseEntity<String>("NULL", HttpStatus.OK);
		}
		
	}	
	
	private void sendToTelegram(Map<String, String> path, String account) {
		Map<String, String> requestParams = new HashMap<>();
		String message = " ";
		
		for ( String key : path.keySet() ) {
			message += key+"\\\\\\\\n"+"\\\\\\\\n"+path.get(key)+"\\\\\\\\n"+"\\\\\\\\n";
		}
	    requestParams.put("msg", message);
	    requestParams.put("to", account);
	    RestTemplate restTemplate = new RestTemplate();
	    url = restTemplate.getForObject("http://192.168.20.110:6969/send?msg={msg}&to={to}", 
	    											String.class, requestParams);
	}
	
	private long unixTimeStamp(String time) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyMMdd");
		Calendar cal = Calendar.getInstance();
		String date = df.format(cal.getTime())+time;
		Date date1=new SimpleDateFormat("yyMMddHHmm").parse(date);
		long unixTime = (long) date1.getTime()/ 1L;
		return unixTime;
	}
}
