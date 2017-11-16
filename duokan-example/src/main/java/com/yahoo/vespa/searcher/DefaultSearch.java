package com.yahoo.vespa.searcher;

import com.yahoo.prelude.query.AndItem;
import com.yahoo.prelude.query.CompositeItem;
import com.yahoo.prelude.query.Item;
import com.yahoo.prelude.query.WordItem;
import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.query.QueryTree;
import com.yahoo.search.searchchain.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultSearch extends Searcher {

  private static Logger logger = LoggerFactory.getLogger(DefaultSearch.class);

  @Override
  public Result search(Query query, Execution execution) {
    logger.info("start search");
    String userId = query.properties().getString("userId", "");
    logger.info("user id : {}", userId);
    if(userId.length() > 0) {
      QueryTree queryTree = query.getModel().getQueryTree();
      addAndItem(queryTree, userId);
    }

    return execution.search(query);
//    return null;
  }

  private void addAndItem(QueryTree q, String term) {
    Item root = q.getRoot();
    logger.info("QueryTree root type is type:{}", root.getItemType());
    CompositeItem compositeRoot;
    if (root instanceof AndItem) {
      compositeRoot = (CompositeItem) root;
      logger.info("compositeRoot count:{}", compositeRoot.getItemCount());
    } else {
      compositeRoot = new AndItem();
      compositeRoot.addItem(root);
      q.setRoot(compositeRoot);
    }
    WordItem wordItem = new WordItem(term);
    logger.info("wordItem {}", wordItem.getItemType());
    compositeRoot.addItem(new WordItem(term));
  }

}
