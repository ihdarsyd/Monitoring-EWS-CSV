package com.task.kp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

@Service("hbaseService")
public class HbaseService {
	private final static String HMASTER = "namenode004.cluster02.bt";
	private final static String ZOOKEEPER = "master001.cluster02.bt,namenode004.cluster02.bt,namenode005.cluster02.bt";
	Configuration configuration = HBaseConfiguration.create();{
		configuration.set("hbase.master", HMASTER);
		configuration.setInt("timeout", 5000);
		configuration.set("hbase.zookeeper.quorum", ZOOKEEPER);
		configuration.set("zookeeper.znode.parent", "/hbase-unsecure2");		
	};
	
	public List<Cell> scanRegexRowKey(String tableName, String regexKey) {
		try (Connection connection = ConnectionFactory.createConnection(configuration);
		    Table table = connection.getTable(TableName.valueOf(tableName))) {
				Scan scan = new Scan();
				scan.setFilter(byRowKey(regexKey));
				ResultScanner rs = table.getScanner(scan);
				for (Result r : rs) {
					return r.listCells();
				}
		} catch (IOException e) {
				e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Cell> scanRegexColumn(String tableName, long startTime, long endTime) {
		try (Connection connection = ConnectionFactory.createConnection(configuration);
		    Table table = connection.getTable(TableName.valueOf(tableName))) {	
				List<Cell> kvList = new ArrayList<Cell>();
				Result r;
				Scan scan = new Scan();
				ResultScanner rs = table.getScanner(scan.addFamily("4".getBytes()).setTimeRange(startTime, endTime));
				while ((r = rs.next()) != null) {
					for (Cell kv : r.listCells()) {
						if((Bytes.toString(kv.getQualifierArray(), kv.getQualifierOffset(), kv.getQualifierLength()).equals("data"))) {
							kvList.add(kv);
						}
					}
				}
			  return kvList;
		} catch (IOException e) {
				e.printStackTrace();
		}
		
		return null;
	}
	
	private Filter byRowKey(String regexKey) {
		return new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("tw_"+regexKey));
	}

}
