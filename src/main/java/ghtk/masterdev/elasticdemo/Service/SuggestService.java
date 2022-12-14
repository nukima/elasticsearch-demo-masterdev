package ghtk.masterdev.elasticdemo.Service;

import ghtk.masterdev.elasticdemo.Config.Config;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Scope("prototype")
public class SuggestService {
    public HashMap<String,Integer>  getTitle(String input, int size) throws IOException {
        RestHighLevelClient client = new Config().client();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestionBuilder termSuggestionBuilder = SuggestBuilders.completionSuggestion("suggest").text(input).skipDuplicates(true).size(size);

        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("title-suggest", termSuggestionBuilder);

        searchSourceBuilder.suggest(suggestBuilder);

        SearchRequest searchRequest = new SearchRequest("title_manhnk9");
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        HashMap<String,Integer> rsMap = new HashMap<>();

        StreamSupport.stream(Spliterators.spliteratorUnknownSize(searchResponse.getSuggest().iterator(), Spliterator.ORDERED), false)
                .flatMap(suggestion -> suggestion.getEntries().get(0).getOptions().stream())
                .map((Suggest.Suggestion.Entry.Option option) -> rsMap.put(option.getText().toString(), (int) option.getScore()))
                .collect(Collectors.toList());

        //sort by score desc
        HashMap<String,Integer> sortedMap = rsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sortedMap;
    }
}
