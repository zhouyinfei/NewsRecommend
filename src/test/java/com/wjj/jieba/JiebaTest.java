package com.wjj.jieba;
 

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.CharArrayMap.EntrySet;

import com.huaban.analysis.jieba.JiebaSegmenter;
 
public class JiebaTest{
	public static void main(String[] args) {
		//分词
		JiebaSegmenter segmenter = new JiebaSegmenter();
	    String sentences = "他来到了网易杭研大厦, 网易杭研大厦";
	    List<String> wordList = segmenter.sentenceProcess(sentences);
	    System.out.println(wordList);
	    
	    //计算每个单词次数
	    Map<String,Integer> amountWord=new HashMap<String,Integer>();
        for (String string : wordList) {
            if(!amountWord.containsKey(string)){
                amountWord.put(string,1);
            }else{
                amountWord.put(string, amountWord.get(string).intValue()+1);
            }
        }
        System.out.println(amountWord);
        
        // 按值排序降序
        Map<String, Integer> sorted = amountWord
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
 
        System.out.println("降序按值排序后的map: " + sorted);
        
        
        //计算词频TF
        Map<String, Double> tfWord=new HashMap<String,Double>();
        for (Entry<String, Integer> string : amountWord.entrySet()) {
            tfWord.put(string.getKey(), Double.valueOf(string.getValue())/wordList.size());
        }
        System.out.println(tfWord);
	}
	
    
}
