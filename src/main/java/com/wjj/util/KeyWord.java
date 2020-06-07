package com.wjj.util;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.huaban.analysis.jieba.JiebaSegmenter;


public class KeyWord {
	
	/**
	 * 从一段文字中提取关键字,取频率前5名的词,用","拼接成字符串
	 * @param text
	 * @return
	 */
	public static String getKeyWord(String sentences){
		//分词
		JiebaSegmenter segmenter = new JiebaSegmenter();
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
        
        //删除语气词
        amountWord.remove("，");
        amountWord.remove(",");
        amountWord.remove("。");
        amountWord.remove("的");
        amountWord.remove("是");
        amountWord.remove("和");
        amountWord.remove("“");
        amountWord.remove("”");
        // 按值排序降序
        Map<String, Integer> sorted = amountWord
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
 
        System.out.println("降序按值排序后的map: " + sorted);
		        
        int i = 0;
        StringBuffer sb = new StringBuffer();
        for (String string : sorted.keySet()) {
        	if (i >= 5) {
				break;
			}
        	sb.append(string + ",");
        	i++;
		}
        
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String text = "近几周中国包括武汉和东北在内的一些地区出现了局部的聚集性疫情，钟南山说道：“我认为目前阶段，中国的抗疫形势并不比国外一些地方更乐观。大部分中国人仍然是新冠病毒的易感人群，因为没有获得足够的免疫。"
					+ "目前武汉正在对全民进行新冠病毒的检测，全面筛查无症状感染者。5月15日当日，全市核酸检测113609人次。"
					+ "钟南山院士说道，他对美国的感染和死亡病例数感到震惊，并称西方政府在疫情出现之处没有严肃对待。“一些国家把新冠病毒和流感病毒相提并论，这是错误的。”他表示。"
					+ "钟南山院士还透露，2月初中国疾控中心和相关部门在武汉病毒研究所石正丽实验室调查了两周时间，他们并没有任何发现。"
					+ "针对全球都在竞相开发的新冠疫苗，钟南山并不乐观。他表示，目前中国已经有多个疫苗正在进行临床试验，但是要找到“完美”的解决方案，可能还需要几年时间。"
					+ "“我们反复地测试不同的疫苗，但是现在要下结论还为时过早。我们还不知道应该用哪种疫苗来对抗新冠病毒。”钟南山院士表示，“所以我认为疫苗要最终获得批准时间会更长。"
					+ "目前中国和美国的两家企业康希诺和Moderna引领了全球疫苗的研发。上周，中国生物制药企业康希诺宣布与加拿大国家研究委员会（NRC）合作在加拿大进行疫苗的临床研究和未来的生产。"
					+ "加拿大国家研究委员会方面对第一财经记者表示：“如果疫苗被证明有效，那么今年夏天就能在加拿大进行生产，预计每月产能达7万至10万剂。”该机构还表示，这些产能将只用于满足加拿大人的需求。"
					+ "周五白宫的新闻发布会上，当被问到如果中国有新冠疫苗可用，美国会不会使用时，美国总统特朗普表示肯定。美国卫生部长也表示，美国政府计划存储数亿剂正在研发中的疫苗来应对疫情。"
					+ "美国卫生与公众服务部长亚历克斯·阿扎（Alex Azar）表示：“目前的候选疫苗已经达到100种，我们的目标是年底前做好一种或者多种疫苗的储备。”白宫的目标是，今年年底前获得3亿剂疫苗。但这些疫苗目前仍然没有一个被证明有效。"
					+ "美国国家过敏和传染病研究所（NIAID）所长安东尼·福奇（Anthony Fauci）认为，疫苗真正被大部分人用上还需要很长时间。福奇上周在国会发表证词时表示，疫苗即便是明年秋天可用，都是非常遥远的事情。"
					+ "钟南山院士表示，在疫苗还遥不可及时，保持健康的生活方式是增强免疫力的有效方法，他称自己现在还积极参加体育运动，并且避免吃太多。";
		String sentences = text.trim().replaceAll("，", "")
									  .replaceAll(",", "")
									  .replaceAll("。", "")
									  .replaceAll("的", "")
									  .replaceAll("和", "")
									  .replaceAll("是", "")
									  .replaceAll("“", "")
									  .replaceAll("”", "");
		System.out.println(getKeyWord(sentences));
	}

}
