package org.frameworkset.http.client;
/**
 * Copyright 2022 bboss
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
import org.frameworkset.spi.remote.http.HttpRequestUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2020</p>
 * @Date 2022/4/25
 * @author biaoping.yin
 * @version 1.0
 */
public class IpregionTest {
	private static Logger logger = LoggerFactory.getLogger(IpregionTest.class);
	@Before
	public void start(){
		//启动连接池
		HttpRequestUtil.startHttpPools("application-ipregion.properties");
	}
	@Test
	public void testQueryIpregion(){
		Map ipInfo = HttpRequestProxy.httpGetforObject("/collector/ipregion/queryIpInfo.api?ip=218.104.155.137",Map.class);
		logger.info(SimpleStringUtil.object2json(ipInfo));

		ipInfo = HttpRequestProxy.httpGetforObject("/collector/ipregion/queryIpInfo.api?ip=222.222.21.38",Map.class);
		logger.info(SimpleStringUtil.object2json(ipInfo));

		ipInfo = HttpRequestProxy.httpGetforObject("/collector/ipregion/queryIpInfo.api?ip=117.22.144.208",Map.class);
		logger.info(SimpleStringUtil.object2json(ipInfo));

	}

}
