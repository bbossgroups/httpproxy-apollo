package org.frameworkset.http.client;
/**
 * Copyright 2025 bboss
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.frameworkset.util.SimpleStringUtil;
import org.frameworkset.spi.remote.http.HttpRequestProxy;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author biaoping.yin
 * @Date 2025/7/21
 */
public class XinferenceRerankTest {
    private Logger logger = LoggerFactory.getLogger(XinferenceRerankTest.class);
    @Before
    public void init(){
        Map properties = new HashMap();

        //embedding_model为的向量模型服务数据源名称
        properties.put("http.poolNames","embedding_model");

        properties.put("embedding_model.http.hosts","172.24.176.18:9997");///设置向量模型服务地址(这里调用的xinference发布的模型服务),多个地址逗号分隔，可以实现点到点负载和容灾

        properties.put("embedding_model.http.timeoutSocket","60000");
        properties.put("embedding_model.http.timeoutConnection","40000");
        properties.put("embedding_model.http.connectionRequestTimeout","70000");
        properties.put("embedding_model.http.maxTotal","100");
        properties.put("embedding_model.http.defaultMaxPerRoute","100");
        HttpRequestProxy.startHttpPools(properties);
    }
    
    @Test
    public void rerank(){
        String requestBody =
                "{\"model\": \"bge-reranker-base\", " +
                "\"documents\": [\"A man is eating food.\",  " +
                        "\"A man is eating a piece of bread.\",  " +
                        "\"The girl is carrying a baby.\", " +
                        "\"A man is riding a horse.\",  " +
                        "\"A woman is playing violin.\"],  " +
                        "\"query\": \"A man is eating pasta.\"}";
        Map reponse = HttpRequestProxy.sendJsonBody("embedding_model",requestBody,"/v1/rerank",Map.class);
        logger.info(SimpleStringUtil.object2json(reponse));
        
    }

}
