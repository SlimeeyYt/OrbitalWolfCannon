# Orbital Wolf Cannon (Fabric)

A Minecraft Fabric mod that adds the **Orbital Wolf Rod**. Using the rod summons a circular strike pack of wolves around the player.

## Features

- Spawns exactly 48 wolves in an even circle (radius 6)
- Wolves spawn at player Y-level (ground-level summon, not sky drop)
- Wolves are tamed to the activating player (multiplayer-safe ownership)
- Wolves are set active (not sitting), equipped with wolf armor, and buffed with Strength II for 5 minutes
- Nearby hostile mobs are auto-targeted
- Activation particle ring + spawn bursts + powerful summon sound
- 60-second item cooldown with action bar warning when unavailable
- Summoned wolves auto-despawn after 5 minutes
- Tooltip, recipe book unlock, and crafting recipe included
- Rod appears in creative inventory search and shows enchanted glint
- **Fully configurable via JSON config file**

## Requirements

- Java 25
- Minecraft 26.1.2
- Fabric Loader + Fabric API

## Quick Start

```powershell
.\gradlew.bat build
.\gradlew.bat runClient
```

Then give yourself the rod:

```mcfunction
give @p orbital_wolf_cannon:orbital_wolf_rod
```

Recipe/advancement verification commands:

```mcfunction
/reload
/recipe give @p orbital_wolf_cannon:orbital_wolf_rod
/advancement grant @p only orbital_wolf_cannon:orbital_dog
```

## Configuration

Config file location: `.minecraft/config/orbital_wolf_cannon.json`

A default config file is created automatically on first launch with these options:

```json
{
  "wolf_count": 48,
  "circle_radius": 6.0,
  "cooldown_seconds": 60,
  "wolf_despawn_seconds": 300,
  "auto_despawn_enabled": true,
  "strength_duration_ticks": 6000,
  "strength_amplifier": 1,
  "summon_sound_volume": 1.8,
  "summon_sound_pitch": 0.85
}
```

### Config Options

- **wolf_count** (int, default: 48)
  - Number of wolves spawned in the circle. Min: 1

- **circle_radius** (double, default: 6.0)
  - Radius in blocks for the wolf spawn circle. Min: 0.5

- **cooldown_seconds** (int, default: 60)
  - Cooldown duration between rod uses in seconds. Min: 0

- **wolf_despawn_seconds** (int, default: 300)
  - Time in seconds until spawned wolves despawn automatically (5 minutes = 300 seconds). Min: 0

- **auto_despawn_enabled** (boolean, default: true)
  - Whether wolves automatically despawn after the configured time. Set to `false` to make wolves permanent.

- **strength_duration_ticks** (int, default: 6000)
  - Duration of Strength II effect on wolves in ticks (6000 ticks = 5 minutes). Min: 0

- **strength_amplifier** (int, default: 1)
  - Amplifier level for Strength effect (0 = Strength I, 1 = Strength II, etc.). Min: 0

- **summon_sound_volume** (float, default: 1.8)
  - Volume of the summon sound effect (1.0 = normal Minecraft volume). Min: 0.0

- **summon_sound_pitch** (float, default: 0.85)
  - Pitch of the summon sound effect (1.0 = normal pitch). Min: 0.1

### Example Configurations

**Summon more wolves with shorter cooldown:**
```json
{
  "wolf_count": 96,
  "cooldown_seconds": 30,
  "circle_radius": 8.0
}
```

**Make wolves permanent (no auto-despawn):**
```json
{
  "auto_despawn_enabled": false,
  "wolf_despawn_seconds": 999999
}
```

**Powerful short-lived wolves:**
```json
{
  "wolf_count": 64,
  "wolf_despawn_seconds": 60,
  "strength_amplifier": 2,
  "strength_duration_ticks": 12000
}
```

## Test Harness

A small JUnit test validates circle generation math:

- `src/test/java/com/slimeeystudios/orbitalwolfcannon/util/CirclePositionUtilTest.java`

Run tests:

```powershell
.\gradlew.bat test
```

## Project Structure

- `src/main/java/com/slimeeystudios/orbitalwolfcannon/` - mod code
  - `config/` - configuration loader
  - `entity/` - wolf spawning and management
  - `item/` - custom rod item
  - `recipe/` - recipe unlock system
  - `registry/` - item registration
  - `util/` - math utilities
- `src/main/resources/assets/orbital_wolf_cannon/` - lang + model
- `src/main/resources/data/orbital_wolf_cannon/` - recipes and advancements
