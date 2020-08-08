package org.frameworkset.http.client;
/**
 * Copyright 2020 bboss
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

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.frameworkset.spi.remote.http.HttpHost;
import org.frameworkset.spi.remote.http.proxy.HttpProxyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2020</p>
 * @Date 2020/8/2 20:07
 * @author biaoping.yin
 * @version 1.0
 */
public class AddressConfigChangeListener implements ConfigChangeListener {
	private static Logger logger = LoggerFactory.getLogger(AddressConfigChangeListener.class);
	private void handleDiscoverHosts(String _hosts,String poolName,String changeRouting){
		if(_hosts != null && !_hosts.equals("")){
			String[] hosts = _hosts.split(",");
			List<HttpHost> httpHosts = new ArrayList<HttpHost>();
			HttpHost host = null;
			for(int i = 0; i < hosts.length; i ++){
				host = new HttpHost(hosts[i]);
				httpHosts.add(host);
			}
			//将被动获取到的地址清单加入服务地址组report中
			HttpProxyUtil.handleDiscoverHosts(poolName,httpHosts,changeRouting);
		}
	}
	/**
	 * //模拟被动获取监听地址清单
	 * List<HttpHost> hosts = new ArrayList<HttpHost>();
	 * // https服务必须带https://协议头,例如https://192.168.137.1:808
	 * HttpHost host = new HttpHost("192.168.137.1:808");
	 * hosts.add(host);
	 *
	 *    host = new HttpHost("192.168.137.1:809");
	 *    hosts.add(host);
	 *
	 * host = new HttpHost("192.168.137.1:810");
	 * hosts.add(host);
	 * //将被动获取到的地址清单加入服务地址组report中
	 * HttpProxyUtil.handleDiscoverHosts("schedule",hosts);
	 */
	public void onChange(ConfigChangeEvent changeEvent) {
		logger.info("Changes for namespace {}", changeEvent.getNamespace());
		ConfigChange defaultHostsChange = null;
		ConfigChange scheduleHostsChange = null;

		ConfigChange scheduleRoutingChange = null;
		for (String key : changeEvent.changedKeys()) {
			if(key.equals("schedule.http.hosts")){//schedule集群
				scheduleHostsChange = changeEvent.getChange(key);


			}
			else if(key.equals("http.hosts")){//default集群
				defaultHostsChange = changeEvent.getChange(key);



			}
			else if(key.equals("schedule.http.routing")){
				scheduleRoutingChange = changeEvent.getChange(key);
			}

		}
		if(scheduleHostsChange != null && scheduleHostsChange.getChangeType() == PropertyChangeType.MODIFIED){
			logger.info("Found change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
					scheduleHostsChange.getPropertyName(), scheduleHostsChange.getOldValue(),
					scheduleHostsChange.getNewValue(), scheduleHostsChange.getChangeType());

				String _hosts = scheduleHostsChange.getNewValue();
				//连通host和rounting一同更新
				handleDiscoverHosts(_hosts, "schedule",
						scheduleRoutingChange != null && scheduleRoutingChange.getChangeType() == PropertyChangeType.MODIFIED?scheduleRoutingChange.getNewValue():null);

		} else if(scheduleRoutingChange != null && scheduleRoutingChange.getChangeType() == PropertyChangeType.MODIFIED){//切换路由组
			HttpProxyUtil.changeRouting("schedule",scheduleRoutingChange.getNewValue());
		}
		if(defaultHostsChange != null && defaultHostsChange.getChangeType() == PropertyChangeType.MODIFIED){
			logger.info("Found change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
					defaultHostsChange.getPropertyName(), defaultHostsChange.getOldValue(),
					defaultHostsChange.getNewValue(), defaultHostsChange.getChangeType());

			String _hosts = defaultHostsChange.getNewValue();
			handleDiscoverHosts(_hosts, "default",null);


		}
	}
}
