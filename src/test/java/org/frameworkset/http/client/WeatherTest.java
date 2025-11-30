package org.frameworkset.http.client;
/**
 * Copyright 2024 bboss
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
import org.frameworkset.spi.remote.http.proxy.InvokeContext;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p></p>
 *
 * @author biaoping.yin
 * @Date 2024/3/1
 */
public class WeatherTest {
    private static Logger logger = LoggerFactory.getLogger(WeatherTest.class);
    @Before
    public void test(){
        //启动连接池
//        HttpRequestUtil.startHttpPools("application-ai.properties");
        Map<String,Object> configs = new HashMap<String,Object>();
//如果指定hosts那么就会采用配置的地址作为初始化地址清单
        configs.put("http.hosts","10.13.13.61:4397");
        configs.put("http.maxTotal",100);
        configs.put("http.defaultMaxPerRoute",100);
        configs.put("http.httpRequestInterceptors","org.frameworkset.http.client.AuthHttpRequestInterceptor");

        HttpRequestProxy.startHttpPools(configs);
    }
 


       

    @Test
    public void testListModel(){


        /**
         * {
         *     "data": [
         *         {
         *             "created": 1709149142,
         *             "id": "moonshot-v1-128k",
         *             "object": "model",
         *             "owned_by": "moonshot",
         *             "permission": [
         *                 {
         *                     "created": 0,
         *                     "id": "",
         *                     "object": "",
         *                     "allow_create_engine": false,
         *                     "allow_sampling": false,
         *                     "allow_logprobs": false,
         *                     "allow_search_indices": false,
         *                     "allow_view": false,
         *                     "allow_fine_tuning": false,
         *                     "organization": "public",
         *                     "group": "public",
         *                     "is_blocking": false
         *                 }
         *             ],
         *             "root": "",
         *             "parent": ""
         *         },
         *         {
         *             "created": 1709149142,
         *             "id": "moonshot-v1-8k",
         *             "object": "model",
         *             "owned_by": "moonshot",
         *             "permission": [
         *                 {
         *                     "created": 0,
         *                     "id": "",
         *                     "object": "",
         *                     "allow_create_engine": false,
         *                     "allow_sampling": false,
         *                     "allow_logprobs": false,
         *                     "allow_search_indices": false,
         *                     "allow_view": false,
         *                     "allow_fine_tuning": false,
         *                     "organization": "public",
         *                     "group": "public",
         *                     "is_blocking": false
         *                 }
         *             ],
         *             "root": "",
         *             "parent": ""
         *         },
         *         {
         *             "created": 1709149142,
         *             "id": "moonshot-v1-32k",
         *             "object": "model",
         *             "owned_by": "moonshot",
         *             "permission": [
         *                 {
         *                     "created": 0,
         *                     "id": "",
         *                     "object": "",
         *                     "allow_create_engine": false,
         *                     "allow_sampling": false,
         *                     "allow_logprobs": false,
         *                     "allow_search_indices": false,
         *                     "allow_view": false,
         *                     "allow_fine_tuning": false,
         *                     "organization": "public",
         *                     "group": "public",
         *                     "is_blocking": false
         *                 }
         *             ],
         *             "root": "",
         *             "parent": ""
         *         }
         *     ]
         * }
         */
        Map<String,String> params = new LinkedHashMap<>();
        params.put("city","北京");
        String res = HttpRequestProxy.sendJsonBody(params,"/weather",String.class);
        logger.info(res);




    }
}
