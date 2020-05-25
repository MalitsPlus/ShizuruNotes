package com.github.malitsplus.shizurunotes.db;

import com.github.malitsplus.shizurunotes.data.EquipmentPiece;

public class RawEquipmentPiece {
    public int equipment_id;
    public String equipment_name;

    public EquipmentPiece getEquipmentPiece() {
        return new EquipmentPiece(equipment_id, equipment_name);
    }
}
