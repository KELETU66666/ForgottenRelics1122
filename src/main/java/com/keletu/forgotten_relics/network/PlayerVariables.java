package com.keletu.forgotten_relics.network;

import net.minecraft.nbt.NBTTagCompound;


public class PlayerVariables {

        public double ability_cd = 0.0D;

        public double skyrider_ab_dur = 0.0D;

        public double dark_iron_sword_ab_dur = 0.0D;

        public boolean dark_iron_sword_active = false;

        public double the_flute_ab_stack = 0.0D;

        public double the_flute_ab_dur = 0.0D;

        public double harmonics_destroy = 0.0D;

        public double prototype_rancour_stack = 0.0D;

        public double prototype_rancour_dur = 0.0D;

        public double prototype_rancour_cd = 0.0D;

        public double blackcliff_stacks = 0.0D;

        public double blackcliff_st1 = 0.0D;

        public double blackcliff_st2 = 0.0D;

        public double blackcliff_st3 = 0.0D;

        public double royal_stacks = 0.0D;

        public void saveNBTData(NBTTagCompound nbt) {
            nbt.setDouble("ability_cd", this.ability_cd);
            nbt.setDouble("skyrider_ab_dur", this.skyrider_ab_dur);
            nbt.setDouble("dark_iron_sword_ab_dur", this.dark_iron_sword_ab_dur);
            nbt.setBoolean("dark_iron_sword_active", this.dark_iron_sword_active);
            nbt.setDouble("the_flute_ab_stack", this.the_flute_ab_stack);
            nbt.setDouble("the_flute_ab_dur", this.the_flute_ab_dur);
            nbt.setDouble("harmonics_destroy", this.harmonics_destroy);
            nbt.setDouble("prototype_rancour_stack", this.prototype_rancour_stack);
            nbt.setDouble("prototype_rancour_dur", this.prototype_rancour_dur);
            nbt.setDouble("prototype_rancour_cd", this.prototype_rancour_cd);
            nbt.setDouble("blackcliff_stacks", this.blackcliff_stacks);
            nbt.setDouble("blackcliff_st1", this.blackcliff_st1);
            nbt.setDouble("blackcliff_st2", this.blackcliff_st2);
            nbt.setDouble("blackcliff_st3", this.blackcliff_st3);
            nbt.setDouble("royal_stacks", this.royal_stacks);
        }

        public void loadNBTData(NBTTagCompound tag) {
            this.ability_cd = tag.getDouble("ability_cd");
            this.skyrider_ab_dur = tag.getDouble("skyrider_ab_dur");
            this.dark_iron_sword_ab_dur = tag.getDouble("dark_iron_sword_ab_dur");
            this.dark_iron_sword_active = tag.getBoolean("dark_iron_sword_active");
            this.the_flute_ab_stack = tag.getDouble("the_flute_ab_stack");
            this.the_flute_ab_dur = tag.getDouble("the_flute_ab_dur");
            this.harmonics_destroy = tag.getDouble("harmonics_destroy");
            this.prototype_rancour_stack = tag.getDouble("prototype_rancour_stack");
            this.prototype_rancour_dur = tag.getDouble("prototype_rancour_dur");
            this.prototype_rancour_cd = tag.getDouble("prototype_rancour_cd");
            this.blackcliff_stacks = tag.getDouble("blackcliff_stacks");
            this.blackcliff_st1 = tag.getDouble("blackcliff_st1");
            this.blackcliff_st2 = tag.getDouble("blackcliff_st2");
            this.blackcliff_st3 = tag.getDouble("blackcliff_st3");
            this.royal_stacks = tag.getDouble("royal_stacks");
        }

    public void onUpdate() {
        if (this.ability_cd > 0)
        {
            this.ability_cd -= 1;
        }
        if (this.prototype_rancour_dur > 0)
        {
            this.prototype_rancour_dur -= 1;
        }
        if (this.prototype_rancour_dur == 0)
        {
            this.prototype_rancour_stack = 0;
        }
        if (this.prototype_rancour_cd > 0)
        {
            this.prototype_rancour_cd -= 1;
        }
        if (this.dark_iron_sword_ab_dur > 0)
        {
            this.dark_iron_sword_ab_dur -= 1;
        }
        if (this.blackcliff_st1 > 0)
        {
            this.blackcliff_st1 -= 1;
        }
        if (this.blackcliff_st2 > 0)
        {
            this.blackcliff_st2 -= 1;
        }
        if (this.blackcliff_st3 > 0)
        {
            this.blackcliff_st3 -= 1;
        }
        if (this.skyrider_ab_dur > 0)
        {
            this.skyrider_ab_dur -= 1;
        }
        if (this.the_flute_ab_dur > 0)
        {
            this.the_flute_ab_dur -= 1;
        }
    }
}
