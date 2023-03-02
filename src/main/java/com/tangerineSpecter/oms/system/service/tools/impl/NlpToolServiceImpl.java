package com.tangerinespecter.oms.system.service.tools.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.vo.tools.NlpInfoVo;
import com.tangerinespecter.oms.system.service.tools.INlpToolService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class NlpToolServiceImpl implements INlpToolService {

    @Override
    public NlpInfoVo analysis(MultipartFile file, String content) {
        Assert.isTrue(file != null || CharSequenceUtil.isNotBlank(content), () -> new BusinessException(RetCode.NLP_CONTENT_NOT_EXIST));
        NlpInfoVo result = new NlpInfoVo();
        String analysisContent = content;
        if (file != null) {
            //TODO待实现
        }
        result.setKeyword(HanLP.extractKeyword(analysisContent, 10));
        result.setSummary(HanLP.extractSummary(analysisContent, 10));
        result.setWordMap(this.segMore(content));
        return result;
    }

    public Map<String, Integer> segMore(String text) {
        Map<String, Integer> countMap = MapUtil.newHashMap();
        //标准分词封装
        final List<Term> terms = HanLP.segment(text);
        CollUtils.forEach(terms, term -> {
            //语义w表示标点符号，跳过
            if (term.nature.equals(Nature.w)) {
                return;
            }
            final String key = term.word;
            countMap.put(key, countMap.getOrDefault(key, 0) + 1);
        });
        return countMap;
    }

    public static void main(String[] args) {
        Map<String, Integer> countMap = MapUtil.newHashMap();
        countMap.putIfAbsent("1", +1);
        System.out.println(countMap);
        countMap.putIfAbsent("1", +1);
        System.out.println(countMap);
    }
}
