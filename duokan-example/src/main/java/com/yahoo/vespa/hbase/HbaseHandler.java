package com.yahoo.vespa.hbase;

//import com.xiaomi.infra.hbase.client.HBaseClientFactory;
//import com.xiaomi.infra.hbase.client.HBaseClientInterface;
//import com.xiaomi.infra.hbase.client.HConfigUtil;
//import com.xiaomi.miliao.zookeeper.EnvironmentType;
//import com.xiaomi.miliao.zookeeper.ZKFacade;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.exception.ExceptionUtils;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class HbaseHandler {

//  private static final String ZK_HBASE_CONFIG = "sns/vespa";
//  private static final byte[] FAMILY_BYTES = "D".getBytes();
//  private static final byte[] COLUMN_BYTES = "".getBytes();
//  private static String TABLE_NAME = "vespa:vespadb_r_duokan";
//  private static Logger logger = LoggerFactory.getLogger(HbaseHandler.class);
//  private static HbaseHandler hbaseHandler;
//  private HBaseClientInterface client = null;
//
//  private HbaseHandler(EnvironmentType type) {
//    init(type);
//  }
//
//  public static HbaseHandler getInstance(EnvironmentType type) {
//    if (hbaseHandler == null) {
//      synchronized (HbaseHandler.class) {
//        if (hbaseHandler == null) {
//          hbaseHandler = new HbaseHandler(type);
//        }
//      }
//    }
//    return hbaseHandler;
//  }
//
//  public void close(){
//    if(client != null) {
//      try {
//        client.close();
//      }catch (Exception e){
//        e.printStackTrace();
//        logger.error("Hit Exception when close hbase client.", e);
//      }
//    }
//  }
//
//  private void init(EnvironmentType type) {
//    try {
//      logger.info("Creating Hbase client");
//      ZKFacade.getZKSettings().setEnviromentType(type);
//      String configPath = HConfigUtil.getBusinessConfigZkUri(ZK_HBASE_CONFIG);
//      logger.info("hbase config path : {}", configPath);
//      client = HBaseClientFactory.createClient(configPath);
//      logger.info("Create Hbase Client successfully");
//    } catch (Exception e) {
//      logger.error("Failed to create Hbase Client, {}", ExceptionUtils.getFullStackTrace(e));
//    }
//  }
//
//  public JSONObject getUser(String userId) throws JSONException{
//    String encodedKey = KeyUtils.encodeKey(userId);
//    Get get = new Get(Bytes.toBytes(encodedKey));
//    get.addColumn(FAMILY_BYTES, COLUMN_BYTES);
//    Result result = null;
//    try {
//      result = client.get(TABLE_NAME, get);
//    } catch (Exception e) {
//      logger.error("Error happend in getUser, ex:{}", ExceptionUtils.getFullStackTrace(e));
//    }
//    String resultString = "";
//    if(result != null && result.size() > 0) {
//      byte[] resultValue = result.getValue(FAMILY_BYTES, COLUMN_BYTES);
//      resultString = Bytes.toString(resultValue);
//      logger.info("GetUser result : {}", resultString);
//    }
//    if(StringUtils.isNotBlank(resultString)){
//      return new JSONObject(resultString);
//    }else{
//      return new JSONObject();
//    }
//  }
//
//  public void putUser(String userId, JSONObject userDocument) {
//    String encodedKey = KeyUtils.encodeKey(userId);
//    Put put = new Put(Bytes.toBytes(encodedKey));
//    put.add(FAMILY_BYTES, COLUMN_BYTES, Bytes.toBytes(userDocument.toString()));
//    try{
//      client.put(TABLE_NAME, put);
//    } catch (Exception e) {
//      logger.error("Error happend in putUser, ex:{}", ExceptionUtils.getFullStackTrace(e));
//    }
//  }
}
