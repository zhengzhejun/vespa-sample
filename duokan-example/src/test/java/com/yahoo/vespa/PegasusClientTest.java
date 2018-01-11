package com.yahoo.vespa;

import com.xiaomi.miliao.zookeeper.EnvironmentType;
import com.yahoo.vespa.hbase.PegasusHandler;
import org.json.JSONObject;
import org.junit.Test;

public class PegasusClientTest {

  public static PegasusHandler pegasusHandler = PegasusHandler.getInstance(EnvironmentType.STAGING);

  @Test
  public void testGetDocument() throws Exception{
    System.out.println(pegasusHandler.getDocument("testUser"));
  }

  @Test
  public void testPutDocument() throws Exception{
    JSONObject json = new JSONObject();
    json.put("id", "testUser");
    pegasusHandler.putDocument("testUser", json);
  }

}
