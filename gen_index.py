import json
from pyvi import ViTokenizer

if __name__ == '__main__':
    fRead1 = open("data/data_1.json", "r", encoding="utf8")
    lines = fRead1.readlines()
    fRead1.close()
    fRead2 = open("data/data_2.json", "r", encoding="utf8")
    lines += fRead2.readlines()
    fRead2.close()
    fRead3 = open("data/data_3.json", "r", encoding="utf8")
    lines += fRead3.readlines()
    fRead3.close()
    fWrite = open("data/title_term.json", "a", encoding="utf8")
    count = 0
    dict_word = {}
    for line in lines:
        count += 1
        if count % 2 == 0 :
            json_object = json.loads(line.strip())
            arrayPhrase = ViTokenizer.tokenize(json_object["title"]).split()
            arrayPhrase = filter(lambda x: x.find("_") != -1, arrayPhrase)
            arrayPhrase = list(map(lambda x: x.replace("_", " "), arrayPhrase))
            arrayPhrase = list(map(lambda x: x.lower(), arrayPhrase))
            for element in arrayPhrase:
                if (element in dict_word):
                    dict_word[element] += 1
                else:
                    dict_word[element] = 1;
    cnt = 1
    for word, freq in dict_word.items():
        fWrite.write("{\"index\":{\"_index\":\"title_manhnk9\",\"_id\": " + str(cnt) + "}}\n")
        fWrite.write("{\"suggest\" :{\"input\": [\"" + word + "\"], \"weight\" : " + str(freq) + "}}\n")
        cnt += 1
    fWrite.close()