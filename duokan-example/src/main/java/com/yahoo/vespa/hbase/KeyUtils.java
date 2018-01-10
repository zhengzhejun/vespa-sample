package com.yahoo.vespa.hbase;

import org.apache.commons.codec.digest.DigestUtils;

public class KeyUtils {

  public static  String encodeKey(String key) {
    byte[] sha = DigestUtils.sha1(key);
    byte hash = sha[0];
    return String.format("%02x:%s", hash, key);
  }

  public static String decodeKey(String key) {
    String originKey = key.split(":", 2)[1];
    return originKey;
  }

}
