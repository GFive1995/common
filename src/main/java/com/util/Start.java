import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;

import utils.HttpUtils;
import utils.ObjectUtils;

public class Start {
	
	public static Map<String, String> JXZ_DATA_MAP = new HashMap<>();
	public static Map<String, String> TDZ_DATA_MAP = new HashMap<>();

	public static void main(String[] args) {
		while (true){
			Map<String, Object> dataMapJXZ = getFirstOrderInfo("jxz");
			if (dataMapJXZ!=null && dataMapJXZ.size()!=0) {
				String status = ObjectUtils.castString(dataMapJXZ.get("status"), "false");
				if (status!=null && status.equals("true")) {
					List<Map<String, String>> dataList = (List<Map<String, String>>) dataMapJXZ.get("rows");
					if (dataList!=null && dataList.size()!=0) {
						for (Map<String, String> map : dataList) {
							String id = ObjectUtils.castString(map.get("id"));
							Integer needNum = ObjectUtils.castInteger(map.get("need_num_0"), 0);
							String zdz = String.valueOf(needNum*30);
							String allpar = "喜马,点赞,"+needNum+","+zdz;
							Map<String, Object> dataMapTwo = sendDataToSecond(zdz, map.get("aa"), allpar);
							String orderId = ObjectUtils.castString(dataMapTwo.get("Data"));
							if (!JXZ_DATA_MAP.containsKey(id)) {
								JXZ_DATA_MAP.put(id, orderId);
							}
						}
					}
				}
			}
			System.out.println(JXZ_DATA_MAP);
			if (JXZ_DATA_MAP!=null && JXZ_DATA_MAP.size()!=0) {
				for (Entry<String, String> entry : JXZ_DATA_MAP.entrySet()) {
					String id = entry.getKey();
					String orderId = entry.getValue();
					Map<String, Object> dataMapThree = getSecondOrderInfo(orderId);
					if (dataMapThree!=null && dataMapThree.size()!=0) {
						List<Map<String, String>> dataList = (List<Map<String, String>>) dataMapThree.get("order");
						if (dataList!=null && dataList.size()!=0) {
							for (Map<String, String> map : dataList) {
								String start = ObjectUtils.castString(map.get("start"));
								String now = ObjectUtils.castString(map.get("now"));
								String status = ObjectUtils.castString(map.get("status"));
								if (status!=null) {
									sendDataToFirst(id, changeString(status), start, now);
									if (start.equals("已完成") || start.equals("已退单") || start.equals("订单异常")) {
										JXZ_DATA_MAP.remove(id);
									}
								}
							}
						}
					}
				}
			}
			List<String> redundIds = new ArrayList<>();
			Map<String, Object> dataMapTDZ = getFirstOrderInfo("tdz");
			if (dataMapTDZ!=null && dataMapTDZ.size()!=0) {
				String status = ObjectUtils.castString(dataMapTDZ.get("status"), "false");
				if (status!=null && status.equals("true")) {
					List<Map<String, String>> dataList = (List<Map<String, String>>) dataMapTDZ.get("rows");
					if (dataList!=null && dataList.size()!=0) {
						for (Map<String, String> map : dataList) {
							Integer needNum = ObjectUtils.castInteger(map.get("need_num_0"), 0);
							String zdz = String.valueOf(needNum*30);
							String allpar = "喜马,点赞,"+needNum+","+zdz;
							Map<String, Object> dataMapTwo = sendDataToSecond(zdz, map.get("aa"), allpar);
							String orderId = ObjectUtils.castString(dataMapTwo.get("Data"));
							redundIds.add(orderId);
						}
					}
				}
			}
			if (redundIds!=null && redundIds.size()!=0) {
				for (String orderId : redundIds) {
					refundSecondOrderInfo(orderId);
				}
			}
			try {
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//get请求获取订单列表
	public static Map<String, Object> getFirstOrderInfo(String status) {
		String url = "http://www.douyin199.com/admin_jiuwuxiaohun.php";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("m", "home");
		map.put("c", "api");
		map.put("a", "down_orders");
		map.put("goods_id", "46495");
		map.put("state", status);
		map.put("format", "json");
		map.put("apikey", "ceshizidingyi");
		String message = HttpUtils.getDataByGet(url, map);
		System.out.println("获取甲方"+status+"数据："+message);
		Map<String, Object> info = JSONArray.parseObject(message);
		return info;
	}
	
	//post提交乙方
	public static Map<String, Object> sendDataToSecond(String zdz, String IDBefore, String allpar) {
		String url = "http://www.weilai1188.com/person/addpackgeOrder";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("myappid", "19");
		map.put("zdz", zdz);
		map.put("IDBefore", IDBefore);
		map.put("appid", "");
		map.put("apcid", "");
		map.put("liuyanList", "");
		map.put("parm1", "https://www.iesdouyin.com/share/video/6703805853700590860/");
		map.put("oldNum", "0");
		map.put("jiangeS", "0");
		map.put("jiangeE", "0");
		map.put("meiciS", "0");
		map.put("meiciE", "0");
		map.put("ordertype", "1");
		map.put("allpar", allpar);
		String message = HttpUtils.getData(url, map);
		System.out.println("乙方返回数据："+message);
		Map<String, Object> info = JSONArray.parseObject(message);
		return info;
	}
	
	//get请求根據訂單號查詢狀態
	public static Map<String, Object> getSecondOrderInfo(String id) {
		String url = "http://order.weilai1188.com/Api.ashx";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "orderquery2019");
		map.put("do", "getOrderList");
		map.put("id", id);
		map.put("username", "douyin01");
		map.put("userpwd", "123456");	
		String message = HttpUtils.getDataByGet(url, map);
		System.out.println("查询乙方数据："+message);
		Map<String, Object> info = JSONArray.parseObject(message);
		return info;
	}
	
	//乙方退单
	public static Map<String, Object> refundSecondOrderInfo(String id) {
		String url = "http://order.weilai1188.com/Api.ashx";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "orderquery2019");
		map.put("do", "change");
		map.put("id", id);
		map.put("username", "douyin01");
		map.put("userpwd", "123456");	
		map.put("status", "5");	
		String message = HttpUtils.getDataByGet(url, map);
		System.out.println("乙方退单数据："+message);
		Map<String, Object> info = JSONArray.parseObject(message);
		return info;
	}
	
	//同步甲方状态
	public static String sendDataToFirst(String id, String orderStatus, String nowNum, String endNum) {
		String url = "http://www.douyin199.com/admin_jiuwuxiaohun.php";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("m", "home");
		map.put("c", "api");
		map.put("a", "edit");
		map.put("goods_id", "46495");
		map.put("order_id", id);
		map.put("order_state", orderStatus);
		map.put("now_num", nowNum);
		map.put("end_num", endNum);
		map.put("apikey", "ceshizidingyi");
		String message = HttpUtils.getDataByGet(url, map);
		System.out.println("同步甲方数据返回信息："+message);
		return message;
	}
	
	//状态转换
	public static String changeString(String status) {
		String result = "";
		switch (status) {
		case "列队中":
			result = "dlz";
			break;
		case "未开始":
			result = "wks";
			break;
		case "进行中":
			result = "jxz";
			break;
		case "已完成":
			result = "ywc";
			break;
		case "已退单":
			result = "ytd";
			break;
		case "退单中":
			result = "tdz";
			break;
		case "续费中":
			result = "xfz";
			break;
		case "补单中":
			result = "bdz";
			break;
		case "改密中":
			result = "gmz";
			break;
		case "登录失败":
			result = "dlsb";
			break;
		case "订单异常":
			result = "ytd";
			break;	
		default:
			break;
		}
		return result;
	}
}

