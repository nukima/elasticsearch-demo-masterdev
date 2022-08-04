package ghtk.masterdev.elasticdemo.Controller;


import ghtk.masterdev.elasticdemo.Service.SuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class SuggestController {
    @Autowired
    private ApplicationContext context;

    @GetMapping(value = "/search/{keyword}/{size}")
    public HashMap<String,Integer> getTitle(@PathVariable String keyword, @PathVariable int size) throws IOException {
        SuggestService suggestService = context.getBean(SuggestService.class);
        return suggestService.getTitle(keyword, size);
    }
}
