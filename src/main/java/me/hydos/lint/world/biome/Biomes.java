package me.hydos.lint.world.biome;

import me.hydos.lint.Lint;
import me.hydos.lint.block.Blocks;
import me.hydos.lint.entity.Entities;
import me.hydos.lint.sound.Sounds;
import me.hydos.lint.world.carver.LintConfiguredCarvers;
import me.hydos.lint.world.feature.Features;
import me.hydos.lint.world.gen.HaykamChunkGenerator;
import me.hydos.lint.world.structure.ConfiguredStructureFeatures;
import net.fabricmc.fabric.mixin.biome.BuiltinBiomesAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class Biomes {

	/**
	 * Fog Colours
	 */
	public static final int MYSTICAL_FOG_COLOUR = 0x8cfff5;
	public static final int CORRUPT_FOG_COLOUR = 0x916ec1;
	public static final int DAWN_FOG_COLOUR = 0xe5c14b;

	/**
	 * Biome Surface Builders
	 */
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> MF_SB = SurfaceBuilder.DEFAULT.withConfig(
			new TernarySurfaceConfig(
					Blocks.LIVELY_GRASS.getDefaultState(),
					Blocks.RICH_DIRT.getDefaultState(),
					Blocks.RICH_DIRT.getDefaultState()));
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CF_SB = SurfaceBuilder.DEFAULT.withConfig(
			new TernarySurfaceConfig(Blocks.CORRUPT_GRASS.getDefaultState(),
					Blocks.RICH_DIRT.getDefaultState(),
					Blocks.RICH_DIRT.getDefaultState()));
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> IN_SB = SurfaceBuilder.DEFAULT.withConfig(
			new TernarySurfaceConfig(Blocks.INDIGO_STONE.getDefaultState(),
					Blocks.INDIGO_STONE.getDefaultState(),
					Blocks.INDIGO_STONE.getDefaultState()));

	/**
	 * Spawn Configurations
	 */
	public static final SpawnSettings.Builder LINT_SPAWN_SETTINGS = new SpawnSettings.Builder()
			.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(Entities.TINY_POTATO, 1, 1, 3))
			.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(Entities.GHOST, 1, 1, 1))
			.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 1, 1, 1))
			.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CREEPER, 1, 1, 1));

	/**
	 * Biomes
	 */
	public static final Biome CORRUPT_FOREST = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.FOREST)
			.depth(0.125f)
			.scale(1)
			.temperature(0.8f)
			.downfall(1)
			.effects(new BiomeEffects.Builder()
					.waterColor(0x916ec1)
					.waterFogColor(0x916ec1)
					.fogColor(CORRUPT_FOG_COLOUR)
					.loopSound(Sounds.CORRUPT_FOREST)
					.skyColor(0x9c76c1)
					.build()
					)
			.spawnSettings(LINT_SPAWN_SETTINGS.build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(CF_SB)
					.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
					.feature(GenerationStep.Feature.RAW_GENERATION, Features.CONFIGURED_VERTICAL_SHAFT)
					.feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPT_TREES)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPT_STEMS)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.WILTED_FLOWERS)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
					.structureFeature(ConfiguredStructureFeatures.DUNGEON)
					.build()
					).build();
	public static final Biome MYSTICAL_FOREST = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.FOREST)
			.depth(0.125f)
			.scale(1)
			.temperature(0.8f)
			.downfall(0)
			.effects(new BiomeEffects.Builder()
					.waterColor(0)
					.waterColor(0x32e686)
					.waterFogColor(0x32e686)
					.fogColor(MYSTICAL_FOG_COLOUR)
					.loopSound(Sounds.MYSTICAL_FOREST)
					.skyColor(0x88dfea)
					.build())
			.spawnSettings(LINT_SPAWN_SETTINGS.build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(MF_SB)
					.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
					.feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_TREES)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_FLOWERS)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_STEMS)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
					.structureFeature(ConfiguredStructureFeatures.DUNGEON)
					.build())
			.build();

	public static final Biome OCEAN = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.OCEAN)
			.depth(-0.5f)
			.scale(1)
			.temperature(0.8f)
			.downfall(0)
			.effects(new BiomeEffects.Builder()
					.waterColor(0)
					.waterColor(0x4faad1)
					.waterFogColor(0x4faad1)
					.fogColor(0xC0D8FF)
					.loopSound(Sounds.OCEAN)
					.skyColor(0x88dfea)
					.build())
			.spawnSettings(LINT_SPAWN_SETTINGS.build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(MF_SB)
					.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
					.structureFeature(ConfiguredStructureFeatures.DUNGEON)
					.build())
			.build();

	public static final Biome INDIGO_RIDGES = new Biome.Builder()
            .precipitation(Biome.Precipitation.NONE)
            .category(Biome.Category.EXTREME_HILLS)
            .depth(1.5f)
            .scale(0.5f)
            .temperature(0.6f)
            .downfall(1)
            .effects(new BiomeEffects.Builder()
                    .waterColor(0x3f76e4)
                    .waterFogColor(0x050533)
                    .fogColor(0xc0d8ff)
                    .skyColor(0x77adff)
                    .build()
            )
            .spawnSettings(LINT_SPAWN_SETTINGS.build())
            .generationSettings(new GenerationSettings.Builder()
                    .surfaceBuilder(IN_SB)
                    .carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
                    .feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
                    .feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
                    .feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
                    .feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
                    .build())
            .build();

	/**
	 * Biome Keys
	 */
	public static final RegistryKey<Biome> MYSTICAL_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("mystical_forest"));
	public static final RegistryKey<Biome> CORRUPT_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("corrupt_forest"));
	public static final RegistryKey<Biome> OCEAN_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("ocean"));
	public static final RegistryKey<Biome> INDIGO_RIDGES_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("indigo_ridges"));

	public static void register() {
		Registry.register(Registry.CHUNK_GENERATOR, Lint.id("haykam_chunk_gen"), HaykamChunkGenerator.CODEC);

		registerBiome(MYSTICAL_FOREST_KEY, MYSTICAL_FOREST);
		registerBiome(CORRUPT_FOREST_KEY, CORRUPT_FOREST);
		registerBiome(OCEAN_KEY, OCEAN);
	}

	private static void registerBiome(RegistryKey<Biome> key, Biome biome) {
		Registry.register(BuiltinRegistries.BIOME, key.getValue(), biome);
		BuiltinBiomesAccessor.getBY_RAW_ID().put(BuiltinRegistries.BIOME.getRawId(biome), key);
	}
}
