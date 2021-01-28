/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.housepower.buffer;

import com.github.housepower.log.Logger;
import com.github.housepower.log.LoggerFactory;
import com.github.housepower.serde.BinarySerializer;
import com.github.housepower.settings.ClickHouseDefines;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class ColumnWriterBuffer {

    private static Logger LOG = LoggerFactory.getLogger(ColumnWriterBuffer.class);

    private final ByteArrayWriter columnWriter;

    public BinarySerializer column;

    public ColumnWriterBuffer() {
        this.columnWriter = new ByteArrayWriter(ClickHouseDefines.COLUMN_BUFFER_BYTES);
        this.column = new BinarySerializer(columnWriter, false);
    }

    public void writeTo(BinarySerializer serializer) throws IOException {
        ByteBuf buf = columnWriter.getBuf();
        LOG.warn("write bytes size: {}", buf.readableBytes());
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        serializer.writeBytes(bytes);
        buf.release();
    }
}
