package com.yahoo.vespa.searcher;

import com.yahoo.data.access.Inspectable;
import com.yahoo.data.access.Inspector;
import com.yahoo.prelude.query.WordItem;
import com.yahoo.processing.request.CompoundName;
import com.yahoo.search.Query;
import com.yahoo.search.Result;
import com.yahoo.search.Searcher;
import com.yahoo.search.result.Hit;
import com.yahoo.search.searchchain.Execution;
import com.yahoo.search.searchchain.SearchChain;
import com.yahoo.tensor.Tensor;
import com.yahoo.tensor.TensorType;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTensorSearch extends Searcher{

  private static final Logger logger = LoggerFactory.getLogger(UserTensorSearch.class);

  @Override
  public Result search(Query query, Execution execution) {

    String userId = query.properties().getString("user_id", "");
//    String tagId = query.properties().getString("tag_id", "");
    if(userId.length() > 0) {
      Hit userHit = callDefaultSearch("user", "user_id", userId, execution);
//      Hit tagHit = callDefaultSearch("tag", "tag_id", tagId, execution);
      addUserProfileTensorToQuery(query, userHit);
    }

    return execution.search(query);

  }

  private Hit callDefaultSearch(String sdName, String indexName, String queryString, Execution execution) {
    Query query = new Query();
    query.getModel().setRestrict("user");
    query.getModel().getQueryTree().setRoot(new WordItem(queryString, "user_id"));
    query.setHits(1);

    SearchChain defaultSearchChain = execution.searchChainRegistry().getComponent("default");
    Result result = new Execution(defaultSearchChain, execution.context()).search(query);
    execution.fill(result);
    Iterator<Hit> hiterator = result.hits().deepIterator();
    return hiterator.hasNext() ? hiterator.next() : null;
  }

  private void addUserProfileTensorToQuery(Query query, Hit userProfile) {
    Object userItemCf = userProfile.getField("user_vector");
    if (userItemCf != null && userItemCf instanceof Inspectable) {
      Tensor.Builder tensorBuilder = Tensor.Builder.of(new TensorType.Builder().indexed("x", 10).build());
      Inspector cells = ((Inspectable)userItemCf).inspect().field("cells");
      for (Inspector cell : cells.entries()) {
        Tensor.Builder.CellBuilder cellBuilder = tensorBuilder.cell();

        Inspector address = cell.field("address");
        for (Map.Entry<String, Inspector> entry : address.fields()) {
          String dim = entry.getKey();
          String label = entry.getValue().asString();
          logger.info("dim:{}, label:{}", dim, label);
          cellBuilder.label(dim, label);
        }

        Inspector value = cell.field("value");
        logger.info("value:{}", value);
        cellBuilder.value(value.asDouble());
      }
      query.properties().set(new CompoundName("user_vector"), tensorBuilder.build());
    }
  }


}
