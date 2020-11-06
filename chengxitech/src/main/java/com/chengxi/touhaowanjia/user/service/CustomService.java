package com.chengxi.touhaowanjia.user.service;

import com.chengxi.touhaowanjia.user.domain.CustomDomain;
import com.chengxi.touhaowanjia.user.repo.CustomRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.go.basetool.APIResultCode;
import com.go.basetool.utils.JsonDtoWrapper;
import com.go.basetool.wxutil.WXHttpCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CustomService {
    @Autowired
    private CustomRepo customRepo;

    public JsonDtoWrapper login(String code) throws JsonProcessingException {
        JsonDtoWrapper j = new JsonDtoWrapper();
        if(StringUtils.isEmpty(code)){
            j.setCodeMsg(APIResultCode.FAILURE);
            return j;
        }
        String data = WXHttpCilent.getData(code);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);
        String openid = jsonNode.path("openid").asText();

        CustomDomain custom = customRepo.findByCustomID(customID);
        if(custom==null){
            j.setCodeMsg(APIResultCode.CUSTOM_NOT_REGISTERED);
            return j;
        }
        j.setCodeMsg(APIResultCode.SUCCESS);
        j.setData();
    }


}
