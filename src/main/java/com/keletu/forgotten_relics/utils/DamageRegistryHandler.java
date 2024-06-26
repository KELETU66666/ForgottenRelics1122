package com.keletu.forgotten_relics.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageRegistryHandler {
	
	public static class DamageSourceTrueDamage extends EntityDamageSource {
        public DamageSourceTrueDamage(final Entity entity) {
            super("trueDamage", entity);
            this.setMagicDamage();
            this.setDamageIsAbsolute();
            this.setDamageBypassesArmor();
            this.setDamageAllowedInCreativeMode();
        }
	}
	
	public static class DamageSourceSoulDrain extends EntityDamageSource {
        public DamageSourceSoulDrain(final Entity entity) {
            super("soulAttack", entity);
            this.setMagicDamage();
            this.setDamageIsAbsolute();
            this.setDamageBypassesArmor();
        }
	}
	
	public static class DamageSourceTrueDamageUndef extends DamageSource {
		 public DamageSourceTrueDamageUndef() {
	            super("trueDamageUndef");
	            this.setMagicDamage();
	            this.setDamageIsAbsolute();
	            this.setDamageBypassesArmor();
	            this.setDamageAllowedInCreativeMode();
	        }
	}
	
	public static class DamageSourceOblivion extends DamageSource {
        public DamageSourceOblivion() {
            super("attackOblivion");
            this.setMagicDamage();
        }
	}
	
	public static class DamageSourceFate extends DamageSource {
        public DamageSourceFate() {
            super("attackFate");
            this.setMagicDamage();
            this.setDamageIsAbsolute();
            this.setDamageBypassesArmor();
            this.setDamageAllowedInCreativeMode();
        }
	}
	
	public static class DamageSourceTLightning extends EntityDamageSource {
        public DamageSourceTLightning(final Entity entity) {
            super("attackLightning", entity);
        }
	}
	
	public static class DamageSourceDarkMatter extends EntityDamageSource {
        public DamageSourceDarkMatter(final Entity entity) {
            super("attackDarkMatter", entity);
            this.setMagicDamage();
            this.setDamageBypassesArmor();
        }
	}
	
	public static class DamageSourceMagic extends EntityDamageSource {
        public DamageSourceMagic(final Entity entity) {
            super("forgottenMagic", entity);
            this.setMagicDamage();
        }
	}
	
	public static class DamageSourceSuperposition extends DamageSource {
        public DamageSourceSuperposition() {
            super("superpositionedDamage");
        }
	}
	
	public static class DamageSourceSuperpositionDefined extends EntityDamageSource {
        public DamageSourceSuperpositionDefined(Entity entity) {
            super("superpositionedDamage", entity);
        }
	}

}
