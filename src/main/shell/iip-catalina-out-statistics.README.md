# Usage
>运行工具（目前仅支持海云采编的 catalina.out 中的错误信息统计）

`sh iip-catalina-out-statistics.sh`  

>请输入要统计的 catalina.out 日志的全路径：

_/Users/linchen/Downloads/catalina3.out._

~~~~  
ready...go...
统计结果： /Users/linchen/Downloads/catalina3.out.statistics
统计结果+堆栈信息： /Users/linchen/Downloads/catalina3.out.statistics.details
done!!!
~~~~  

>查看统计结果

`cat /Users/linchen/Downloads/catalina3.out.statistics.details`

~~~~  
========  215 次 ========

[WCMLOG] 2022-07-07 09:31:57,779 ERROR[http-nio-8080-exec-3] com.trs.gov.common.servlet.GovServiceControler.service(GovServiceControler.java:248) - Failt to execute[gov_wxsucai.getSucaiList]Parameters=[{PAGEINDEX=1, WXID=wx_成都拓尔思大数据, CHANNELID=736, SITEID=293, TYPE=news, PAGESIZE=4}]
请求微信中间件失败,失败原因：JSONObject["access_token"] not found.
	at com.trs.gov.editorcenter.weixin.TrsWechatBridge.getSucaiList(TrsWechatBridge.java:639)
	at com.trs.gov.editorcenter.weixin.service.WXSucaiServiceProvider.getSucaiList(WXSucaiServiceProvider.java:139)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.trs.webframework.WebServiceHelper.execute0(WebServiceHelper.java:259)
	at com.trs.webframework.WebServiceHelper.execute(WebServiceHelper.java:92)
	at com.trs.webframework.WebServiceHelper.execute(WebServiceHelper.java:57)
	at com.trs.webframework.xmlserver.server.RequestParamsProcessor.excute(RequestParamsProcessor.java:90)
	at com.trs.gov.common.servlet.GovServiceControler.execute(GovServiceControler.java:296)
	at com.trs.gov.common.servlet.GovServiceControler.service(GovServiceControler.java:205)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)


========  55 次 ========

[WCMLOG] 2022-07-07 09:35:44,967 ERROR[http-nio-8080-exec-6] com.trs.infra.util.ImgFileHelperForReadImg.convert(ImgFileHelperForReadImg.java:109) - 文件不存在！[/TRS/HyCloud/IIP/data/WCMData/webpic/W0202204/W020220425/W020220425523575938200_ORIGIN.jpg]
java.lang.Exception: who call me?
	at com.trs.infra.util.ImgFileHelperForReadImg.convert(ImgFileHelperForReadImg.java:109)
	at com.trs.infra.util.ImgFileHelperForReadImg.convert(ImgFileHelperForReadImg.java:42)
	at com.trs.infra.WCMServiceHelper.getReadImgURL(WCMServiceHelper.java:197)
	at com.trs.gov.editorcenter.weixin.json.SpecialJSONPropertyForWXMessage.dowithDocCoverPic(SpecialJSONPropertyForWXMessage.java:182)
	at com.trs.gov.editorcenter.weixin.json.SpecialJSONPropertyForWXMessage.getMessageContents(SpecialJSONPropertyForWXMessage.java:164)
	at com.trs.gov.editorcenter.weixin.json.SpecialJSONPropertyForWXMessage.dowithWXMessage(SpecialJSONPropertyForWXMessage.java:128)
	at com.trs.gov.editorcenter.weixin.json.SpecialJSONPropertyForWXMessage.transformSpecialPropertyMap(SpecialJSONPropertyForWXMessage.java:52)
	at com.trs.ajaxservice.jsonconvertors.SpecialPropertyOfBaseObjForJSON.transformSpecialPropertyMap(SpecialPropertyOfBaseObjForJSON.java:84)
	at com.trs.ajaxservice.jsonconvertors.WCMJsonUtil.baseObjPropertiesToMap(WCMJsonUtil.java:468)
	at com.trs.ajaxservice.jsonconvertors.WCMJsonUtil.baseObjToMap(WCMJsonUtil.java:94)
	at com.trs.ajaxservice.jsonconvertors.WCMJsonUtil.baseObjsToMapsWithoutPager(WCMJsonUtil.java:157)
	at com.trs.ajaxservice.jsonconvertors.WCMJsonUtil.baseObjsToMapWithPager(WCMJsonUtil.java:117)
	at com.trs.ajaxservice.jsonconvertors.WCMJsonUtil.toMapOrMaps(WCMJsonUtil.java:280)
	at com.trs.ajaxservice.jsonconvertors.WCMJsonUtil.apiResultToMap(WCMJsonUtil.java:238)
	at com.trs.ajaxservice.jsonconvertors.WCMJsonUtil.toJSON(WCMJsonUtil.java:72)
	at org.apache.jsp.gov.common.to_005fjson_jsp._jspService(to_005fjson_jsp.java:124)
	at org.apache.jasper.runtime.HttpJspBase.service(HttpJspBase.java:70)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)


========  53 次 ========

[WCMLOG] 2022-07-07 09:40:00,346 ERROR[iip_Worker-2] com.trs.gov.operationcenter.systemmonitor.job.SystemMonitorProblemMonitor.execute(SystemMonitorProblemMonitor.java:67) - 实时同步系统监测警报信息失败...连接zabbix服务器超时...
[WCMLOG] 2022-07-07 09:41:28,764 ERROR[http-nio-8080-exec-13] com.trs.gov.common.servlet.GovServiceControler.service(GovServiceControler.java:248) - Failt to execute[gov_site.findSiteById]Parameters=[{MODULEID=40, CURRUSERNAME=admin, SITEID=1130}]
com.trs.infra.common.ParameterError: 参数SITEID不合法！
	at com.trs.gov.editorcenter.site.SiteServiceProvider.findSiteById(SiteServiceProvider.java:3950)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.trs.webframework.WebServiceHelper.execute0(WebServiceHelper.java:259)
	at com.trs.webframework.WebServiceHelper.execute(WebServiceHelper.java:92)
	at com.trs.webframework.WebServiceHelper.execute(WebServiceHelper.java:57)
	at com.trs.webframework.xmlserver.server.RequestParamsProcessor.excute(RequestParamsProcessor.java:90)
	at com.trs.gov.common.servlet.GovServiceControler.execute(GovServiceControler.java:296)
	at com.trs.gov.common.servlet.GovServiceControler.service(GovServiceControler.java:205)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)

...
...
...
~~~~  