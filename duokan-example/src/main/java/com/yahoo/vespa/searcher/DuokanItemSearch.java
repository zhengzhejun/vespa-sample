package com.yahoo.vespa.searcher;

import com.yahoo.prelude.query.AndItem;
import com.yahoo.prelude.query.CompositeItem;
import com.yahoo.prelude.query.Item;
import com.yahoo.prelude.query.OrItem;
import com.yahoo.prelude.query.WordItem;
import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.querytransform.QueryTreeUtil;
import com.yahoo.search.searchchain.Execution;

public class DuokanItemSearch extends Searcher{

  @Override
  public Result search(Query query, Execution execution) {
    String tagsString = query.properties().getString("tags");
    String andOr = query.properties().getString("andOr");
    String[] tags = tagsString.split(",");
    query.getModel().setRestrict("item");
    CompositeItem compositeItem = andOr.equals("and") ? new AndItem() : new OrItem();
    for(int i = 0; i < tags.length; i++){
      compositeItem.addItem(new WordItem(tags[i], "tag_list"));
    }
    QueryTreeUtil.andQueryItemWithRoot(query, compositeItem);

    return execution.search(query);
  }
}
