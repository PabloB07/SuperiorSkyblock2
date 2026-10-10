$ErrorActionPreference = 'Stop'
$root = "src\main\resources\sift\datapack"
$ns = "sift"

function Write-Json($relPath, $obj) {
  $path = Join-Path $root $relPath
  New-Item -ItemType Directory -Force -Path (Split-Path $path) | Out-Null
  $json = $obj | ConvertTo-Json -Depth 20
  Set-Content -Path $path -Value $json -Encoding UTF8
}

function Hex($value) { [Convert]::ToInt32($value, 16) }

Write-Json "pack.mcmeta" @{
  pack = @{
    pack_format = 80
    description = "The Sift dimension (SuperiorSkyblock2)"
  }
}

# Dimension type shared by the Sift dimension.
Write-Json "data\$ns\dimension_type\sift.json" @{
  ambient_light = 0.1
  bed_works = $true
  coordinate_scale = 1.0
  effects = "minecraft:overworld"
  has_ceiling = $false
  has_raids = $true
  has_skylight = $true
  height = 384
  infiniburn = "#minecraft:infiniburn_overworld"
  logical_height = 384
  min_y = -64
  monster_spawn_block_light_limit = 0
  monster_spawn_light_level = @{
    type = "minecraft:uniform"
    min_inclusive = 0
    max_inclusive = 7
  }
  natural = $true
  piglin_safe = $false
  respawn_anchor_works = $false
  ultrawarm = $false
}

# Biomes: colours from the Sift concept (turquoise sky, coloured fog and water).
$biomes = @(
  @{ id = "pink_meadows";     sky = "7FE8E0"; fog = "B8F2E6"; water = "3AB8C9"; waterFog = "1C6E7A"; grass = "FF9FD0"; foliage = "FF8ABF"; temp = @(0.2, 0.8);   humidity = @(-0.2, 0.2); cont = @(-0.2, 0.5); erosion = @(-0.5, 0.5); weirdness = @(-0.3, 0.3) },
  @{ id = "frost_forest";     sky = "5FD6D0"; fog = "D8FFFB"; water = "2FA7C7"; waterFog = "17525E"; grass = "B8F3FF"; foliage = "A0E8FF"; temp = @(-0.8, -0.2); humidity = @(0.0, 0.6);  cont = @(-0.2, 0.6); erosion = @(-0.5, 0.5); weirdness = @(-0.3, 0.3) },
  @{ id = "red_spires";       sky = "3ED2C4"; fog = "E8B7A0"; water = "2A9CB0"; waterFog = "163F48"; grass = "C6A36B"; foliage = "B0874A"; temp = @(0.6, 1.0);   humidity = @(-0.6, -0.1); cont = @(0.0, 1.0);  erosion = @(-1.0, -0.3); weirdness = @(-0.3, 0.3) },
  @{ id = "shattered_peaks";  sky = "4FC9C0"; fog = "C9F5F0"; water = "3BB3C8"; waterFog = "1E5F6E"; grass = "8FD3C7"; foliage = "6FB8AE"; temp = @(-0.3, 0.3);  humidity = @(-0.3, 0.3); cont = @(0.4, 1.0);  erosion = @(-1.0, -0.5); weirdness = @(-0.3, 0.3) }
)

foreach ($b in $biomes) {
  Write-Json "data\$ns\worldgen\biome\$($b.id).json" @{
    has_precipitation = $false
    temperature = [math]::Round(($b.temp[0] + $b.temp[1]) / 2, 2)
    downfall = 0.5
    effects = @{
      sky_color = (Hex $b.sky)
      fog_color = (Hex $b.fog)
      water_color = (Hex $b.water)
      water_fog_color = (Hex $b.waterFog)
      grass_color = (Hex $b.grass)
      foliage_color = (Hex $b.foliage)
    }
  }
}

# Dimension: multi-noise biome source with the four Sift biomes.
$entries = @()
foreach ($b in $biomes) {
  $entries += @{
    biome = "$ns`:$($b.id)"
    parameters = @{
      temperature = $b.temp
      humidity = $b.humidity
      continentalness = $b.cont
      erosion = $b.erosion
      weirdness = $b.weirdness
      depth = @(0.0, 0.0)
      offset = @(0.0, 0.0)
    }
  }
}

Write-Json "data\$ns\dimension\sift.json" @{
  type = "$ns`:sift"
  generator = @{
    type = "minecraft:noise"
    settings = "minecraft:overworld"
    biome_source = @{
      type = "minecraft:multi_noise"
      biomes = $entries
    }
  }
}

"OK: datapack written to $root"
