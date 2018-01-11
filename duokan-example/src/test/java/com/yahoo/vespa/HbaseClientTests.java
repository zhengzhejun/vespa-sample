package com.yahoo.vespa;

import com.xiaomi.miliao.zookeeper.EnvironmentType;
import com.yahoo.vespa.hbase.HbaseHandler;
import org.json.JSONObject;
import org.junit.Test;

public class HbaseClientTests {

//  public static HbaseHandler hbaseHandler = HbaseHandler.getInstance(EnvironmentType.STAGING);

  @Test
  public void testPutDocument() throws Exception{
    JSONObject json = new JSONObject();
    json.put("id", "testUser");
//    hbaseHandler.putUser("testUser", json);
  }

  @Test
  public void testGetDocument() throws Exception{
//    System.out.println(hbaseHandler.getUser("testUser").toString());
  }

}
