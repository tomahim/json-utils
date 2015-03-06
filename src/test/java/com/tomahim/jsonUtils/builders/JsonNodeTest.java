package com.tomahim.jsonUtils.builders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JsonNodeTest {

	public JsonNode node;

	@Before
	public void initData() {
		node = new JsonNode();
		node.addNode(new JsonNode("key", "value"));
	}

	@Test
	public void testRootNode() {
		JsonNode root = new JsonNode();
		assertTrue(root.isRoot());
	}

	@Test
	public void testLeaf() {
		assertTrue(node.getNodes().size() == 1);
		assertTrue(node.getNodes().get(0).isLeaf());
	}

	@Test
	public void testFindNode() {
		assertNotNull(node.findNode("key"));
		assertNull(node.findNode("value"));
	}

}
