package dev.wolveringer.bungeeutil.packets;

import dev.wolveringer.bungeeutil.packetlib.reader.PacketDataSerializer;
import dev.wolveringer.bungeeutil.packets.types.PacketPlayOut;
import dev.wolveringer.bungeeutil.position.BlockPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PacketPlayOutBlockChange extends Packet implements PacketPlayOut {
	private BlockPosition position;
	private int data;
	
	@Override
	public void read(PacketDataSerializer s) {
		position = s.readBlockPosition();
		data = s.readVarInt();
	}

	@Override
	public void write(PacketDataSerializer s) {
		s.writeBlockPosition(position);
		s.writeVarInt(data);
	}

	public int getTypeId(){ return data >> 4; }
	public int getMetaId(){ return data & 0x0F; }
}
