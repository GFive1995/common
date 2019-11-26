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
							String allpar = "ϲ��,����,"+needNum+","+zdz;
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
									if (start.equals("�����") || start.equals("���˵�") || start.equals("�����쳣")) {
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
							String allpar = "ϲ��,����,"+needNum+","+zdz;
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
	
	//get�����ȡ�����б�
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
		System.out.println("��ȡ�׷�"+status+"���ݣ�"+message);
		Map<String, Object> info = JSONArray.parseObject(message);
		return info;
	}
	
	//post�ύ�ҷ�
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
		System.out.println("�ҷ��������ݣ�"+message);
		Map<String, Object> info = JSONArray.parseObject(message);
		return info;
	}
	
	//get�������ӆ��̖��ԃ��B
	public static Map<String, Object> getSecondOrderInfo(String id) {
		String url = "http://order.weilai1188.com/Api.ashx";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "orderquery2019");
		map.put("do", "getOrderList");
		map.put("id", id);
		map.put("username", "douyin01");
		map.put("userpwd", "123456");	
		String message = HttpUtils.getDataByGet(url, map);
		System.out.println("��ѯ�ҷ����ݣ�"+message);
		Map<String, Object> info = JSONArray.parseObject(message);
		return info;
	}
	
	//�ҷ��˵�
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
		System.out.println("�ҷ��˵����ݣ�"+message);
		Map<String, Object> info = JSONArray.parseObject(message);
		return info;
	}
	
	//ͬ���׷�״̬
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
		System.out.println("ͬ���׷����ݷ�����Ϣ��"+message);
		return message;
	}
	
	//״̬ת��
	public static String changeString(String status) {
		String result = "";
		switch (status) {
		case "�ж���":
			result = "dlz";
			break;
		case "δ��ʼ":
			result = "wks";
			break;
		case "������":
			result = "jxz";
			break;
		case "�����":
			result = "ywc";
			break;
		case "���˵�":
			result = "ytd";
			break;
		case "�˵���":
			result = "tdz";
			break;
		case "������":
			result = "xfz";
			break;
		case "������":
			result = "bdz";
			break;
		case "������":
			result = "gmz";
			break;
		case "��¼ʧ��":
			result = "dlsb";
			break;
		case "�����쳣":
			result = "ytd";
			break;	
		default:
			break;
		}
		return result;
	}
}

