package com.meteo.adapter.kafka;

import com.meteo.domain.model.GeometeoToken;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class MeteoDataDeserializer {
	public static byte deserialize(final ByteBuffer byteBuffer) {
		return deserialize(byteBuffer, 0);
	}

	/**
	 * @param byteBuffer the Explorer Data be deserialized from
	 * @param offset the offset to start deserializing from
	 * @return deserialized ExplorerData
	 */
	static byte deserialize(final ByteBuffer byteBuffer, final int offset) {
		byte[] data = new byte[1];
		byteBuffer.get(data, offset, data.length);

		return data[0];
	}
}
