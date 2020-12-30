/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import me.hydos.lint.item.materialset.Enhanceable;
import me.hydos.lint.util.Power;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;

public class LintEnhancementComponent implements AutoSyncedComponent {
	public LintEnhancementComponent(ItemStack stack) {
		this.stack = stack;
	}

	private final ItemStack stack;
	private final Object2FloatMap<Power.Broad> enhancements = new Object2FloatArrayMap<>();

	/**
	 * @return the level of enhancement for the given power, with default return value of 0.
	 */
	public float getEnhancement(Power.Broad power) {
		return this.enhancements.getFloat(power);
	}

	public ObjectSet<Power.Broad> getEnhancements() {
		return this.enhancements.keySet();
	}

	/**
	 * Pls only call on the server it autosyncs ok thanks.
	 * @return the new power level of the power if successful. Otherwise, returns 0.
	 */
	public float enhance(Power.Broad power, float by) {
		final int powers = this.enhancements.keySet().size();

		if (powers > 0) { // if already has powers.
			switch (this.enhancements.keySet().iterator().next()) {
				case ALLOS:
				case MANOS:
					return 0; // either one major power
				default:
					if (powers > 1 || power == Power.Broad.ALLOS || power == Power.Broad.MANOS) { // or 2 minor powers
						return 0;
					}
					break;
			}
		}

		float result = this.enhancements.computeFloat(power, (pwr, current) -> current + by);
		Item item = this.stack.getItem();

		if (item instanceof Enhanceable) {
			((Enhanceable) item).update(this.stack, power, result);
		}

		Components.ITEM.sync(this.stack);
		return result;
	}

	@Override
	public boolean shouldSyncWith(ServerPlayerEntity player) {
		return player.inventory.contains(this.stack);
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		for (Power.Broad power : Power.Broad.values()) {
			String powerName = power.name();

			if (tag.contains(powerName)) {
				this.enhancements.put(power, tag.getFloat(powerName));
			}
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		this.enhancements.forEach((power, value) -> {
			tag.putFloat(power.name(), value);
		});
	}
}
