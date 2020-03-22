package cn.itcast.travel.test;

import cn.itcast.travel.domain.ResultInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class JsonTest {
    @Test
    public void test() throws JsonProcessingException {
        ResultInfo resultInfo = new ResultInfo(true,"hello","error");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(resultInfo));
    }
}
