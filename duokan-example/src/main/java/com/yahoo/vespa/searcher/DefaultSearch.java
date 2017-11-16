package com.yahoo.vespa.searcher;

import com.yahoo.prelude.query.AndItem;
import com.yahoo.prelude.query.CompositeItem;
import com.yahoo.prelude.query.WordItem;
import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.query.QueryTree;
import com.yahoo.search.searchchain.Execution;


public class DefaultSearch extends Searcher {

  @Override
  public Result search(Query query, Execution execution) {
    String userId = query.properties().getString("userId", "");
    if(userId.length() > 0) {
      QueryTree queryTree = query.getModel().getQueryTree();
      CompositeItem compositeItem = new AndItem();
      compositeItem.addItem(queryTree.getRoot());
      compositeItem.addItem(new WordItem(userId, "user_id"));
      queryTree.setRoot(compositeItem);
    }

    return execution.search(query);
//    return null;
  }

}
