package com.yahoo.vespa.searcher;

import com.xiaomi.miliao.zookeeper.EnvironmentType;
import com.yahoo.prelude.query.AndItem;
import com.yahoo.prelude.query.CompositeItem;
import com.yahoo.prelude.query.Item;
import com.yahoo.prelude.query.WordItem;
import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.query.QueryTree;
import com.yahoo.search.searchchain.Execution;
import com.yahoo.vespa.hbase.HbaseHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultSearch extends Searcher {

  private static Logger logger = LoggerFactory.getLogger(DefaultSearch.class);
  private static HbaseHandler hbaseHandler = HbaseHandler.getInstance(EnvironmentType.STAGING);

  @Override
  public Result search(Query query, Execution execution) {
    logger.info("start DefaultSearch, query:{}", query);
    String userId = query.properties().getString("userId", "");
    if(StringUtils.isNotBlank(userId)) {
      logger.info("userId is {}", userId);
      JSONObject userObject;
      try{
        userObject = hbaseHandler.getUser(userId);
        logger.info("hbase user document is {}", userObject.toString());
      } catch (Exception e) {
        logger.error("Error happend in search, ex:{}", ExceptionUtils.getFullStackTrace(e));
      }
    }
    return execution.search(query);

  }

}
