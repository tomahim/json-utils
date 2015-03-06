package com.tomahim.jsonUtils.builders;

import java.util.HashMap;
import java.util.Map;

import com.tomahim.jsonUtils.common.StringUtil;

public final class JsonTreeBuilder {

	final static String DOT = ".";
	final static String DOT_SPLIT_REGEX = "\\.";

	private JsonTreeBuilder() {

	}

	public static JsonNode constructTreeFromMap(JsonNode node,
			Map<String, String> selection) {
		for (Map.Entry<String, String> entry : selection.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key.contains(DOT)) {
				String[] attributes = key.split(DOT_SPLIT_REGEX);
				JsonNode found = node.findNode(attributes[0]);
				Map<String, String> nextMap = new HashMap<String, String>();
				nextMap.put(StringUtil.getNextValue(key), value);
				if (found == null) {
					JsonNode newNode = new JsonNode(attributes[0]);
					node.addNode(newNode);
					constructTreeFromMap(newNode, nextMap);
				} else {
					constructTreeFromMap(found, nextMap);
				}
			} else {
				node.addNode(new JsonNode(key, value));
			}
		}
		return node;
	}

}
