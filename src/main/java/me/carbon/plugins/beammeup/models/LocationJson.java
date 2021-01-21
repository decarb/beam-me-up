package me.carbon.plugins.beammeup.models;

import java.util.UUID;

public class LocationJson {
    public final UUID world_uuid;
    public final double x;
    public final double y;
    public final double z;

    public LocationJson(UUID worldUuid, double x, double y, double z) {
        this.world_uuid = worldUuid;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}