package com.yahoo.vespa.hbase;

import com.xiaomi.infra.pegasus.client.PegasusClientFactory;
import com.xiaomi.infra.pegasus.client.PegasusClientInterface;
import com.xiaomi.miliao.zookeeper.EnvironmentType;
import com.xiaomi.miliao.zookeeper.ZKFacade;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PegasusHandler {

  private static final String RESOURCE_PATH = "/databases/pegasus/vespa";
  private static final String ZK_PREFIX = "zk://";
  private static final String TABLE_NAME = "vespa_r_duokan";
  private static Logger logger = LoggerFactory.getLogger(PegasusHandler.class);
  private static PegasusHandler pegasusHandler;
  private PegasusClientInterface client;

  private PegasusHandler(EnvironmentType env) {
    try{
      logger.info("Creating Pegasus Client");
      ZKFacade.getZKSettings().setEnviromentType(env);
      String zkServer = ZKFacade.getZKSettings().getZKServers();
      logger.info("Pegasus init, env:{}, zkServer:{}", env, zkServer);
      client = PegasusClientFactory.getSingletonClient(ZK_PREFIX + zkServer + RESOURCE_PATH);
      logger.info("Create Pegasus client successfully");
    } catch (Exception e) {
      logger.error("Failed to create Pegasus client. {}", ExceptionUtils.getFullStackTrace(e));
    }
  }

  public static PegasusHandler getInstance(EnvironmentType env) {
    if (pegasusHandler == null) {
      synchronized (PegasusHandler.class) {
        if (pegasusHandler == null) {
          pegasusHandler = new PegasusHandler(env);
        }
      }
    }
    return pegasusHandler;
  }

  public void close(){
    if(client != null){
      client.close();
    }
  }

  public JSONObject getDocument(String key) throws Exception {
    byte[] hashKey = key.getBytes();
    byte[] value = client.get(TABLE_NAME, hashKey, null);
    JSONObject docJSONObject = new JSONObject();
    if(value != null) {
      docJSONObject = new JSONObject(new String(value));
    }
    return docJSONObject;
  }

  public void putDocument(String key, JSONObject doc) throws Exception {
    byte[] hashKeys = key.getBytes();
    client.set(TABLE_NAME, hashKeys, null, doc.toString().getBytes());
  }

}
