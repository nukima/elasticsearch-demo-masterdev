# elasticsearch-demo-masterdev

* Kibana Devtools: http://172.17.80.26:5601/app/kibana#/dev_tools/console?_g=()
* Index file json
  ```
  curl -H "Content-Type: application/json" -XPOST "172.17.80.26:9200/dantri/_bulk?pretty&refresh" --data-binary "@data/data_1.json"
  ```
### 1. Query DSL đưa ra các văn bản liên quan nhất đến các keyword : "an toàn", "đường bộ", "đường sắt" trong năm 2013.
  ```
  GET dantri/_search
  {
    "query": {
      "bool" : {
        "must" : [
          {
            "multi_match" : {
              "query":  "an toàn",
              "type":   "phrase",
              "fields": [ "title", "description", "content"]
            }
          },
          {
            "multi_match" : {
              "query":  "đường bộ",
              "type":   "phrase",
              "fields": [ "title", "description", "content"]
            }
          },
          {
            "multi_match" : {
              "query":  "đường sắt",
              "type":   "phrase",
              "fields": [ "title", "description", "content"]
            }
          },
          {
            "range": {
              "time": {
                "gte": 1356998400,
                "lt": 1388534400
              }
            }
          }
        ]
      }
    }
  }
  ```

### 2. Query DSL đưa ra các văn bản có title bắt đầu bằng "Hà Nội", nhưng description lại không được phép chứa từ "Hà Nội", sắp xếp theo thời gian giảm dần (trường time).
  ```
  GET dantri/_search
  {
    "query":{
      "bool" : {
        "must" : {
          "prefix": {
            "title.keyword": "Hà Nội"
          }
        },
        "must_not" :{
          "match_phrase": {
            "description": {
              "query": "Hà Nội"
            }
          }
        },
        "sort": [
          {
            "time": {
              "order": "desc"
            }
          }
        ]
      }
    }
  }
  ```
3. API gợi ý search theo title (kết quả trả về sắp xếp theo số lần xuất hiện)
* gen_index.py để tách field title 
* Tạo index
```
PUT title_manhnk9
{
    "mappings": {
        "properties" : {
            "suggest" : {
                "type" : "completion"
            }
        }
    }
}
```

* GET Request: http://localhost:8080/search/{keyword}/{size}
* Ví dụ: GET Requeset: http://localhost:8080/search/ha/10  
  ![image](https://user-images.githubusercontent.com/67093353/182893870-52206b08-dbd6-470a-b6b8-8d69086e6b71.png)

