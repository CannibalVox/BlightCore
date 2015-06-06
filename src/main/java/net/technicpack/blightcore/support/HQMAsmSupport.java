package net.technicpack.blightcore.support;

import hardcorequesting.QuestingData;
import net.minecraft.entity.player.EntityPlayer;

public class HQMAsmSupport {
    public static void removeTenRep(QuestingData questingData) {
        questingData.getTeam().setReputation(0, questingData.getTeam().getReputation(0) - 10);
    }
}
