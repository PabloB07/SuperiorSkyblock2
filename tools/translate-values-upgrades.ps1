$ErrorActionPreference = 'Stop'
New-Item -ItemType Directory -Force -Path "src\main\resources\menus\es-ES" | Out-Null

# ============ values1_20.yml ============
$text = [IO.File]::ReadAllText((Resolve-Path "src\main\resources\menus\values1_20.yml").Path, [Text.Encoding]::UTF8)
$text = $text.Replace("`'{0}Previous Page`'", "'{0}Página anterior'")
$text = $text.Replace("`'{0}Next Page`'", "'{0}Página siguiente'")
$text = $text.Replace("name: '&aCurrent Page'", "name: '&aPágina actual'")
$text = $text.Replace("- '&7Page {0}'", "- '&7Página {0}'")
$text = $text.Replace("&e&lSPAWNERS ->", "&e&lGENERADORES ->")
$text = $text.Replace("&e&lITEMS ->", "&e&lOBJETOS ->")

$valuesNames = [ordered]@{
  "Spawner'" = "Generador'"
  "|placeholder|" = "|placeholder|"
}
$valuesNamesFull = [ordered]@{
  '&e&l[!] &7Iron Golem Spawner' = '&e&l[!] &7Generador de gólem de hierro'
  '&e&l[!] &7Blaze Spawner' = '&e&l[!] &7Generador de blazes'
  '&e&l[!] &7Spider Spawner' = '&e&l[!] &7Generador de arañas'
  '&e&l[!] &7Cow Spawner' = '&e&l[!] &7Generador de vacas'
  '&e&l[!] &7Zombie Spawner' = '&e&l[!] &7Generador de zombis'
  '&e&l[!] &7Skeleton Spawner' = '&e&l[!] &7Generador de esqueletos'
  '&e&l[!] &7Creeper Spawner' = '&e&l[!] &7Generador de creepers'
  '&e&l[!] &7Zombified Piglin Spawner' = '&e&l[!] &7Generador de piglins zombificados'
  '&e&l[!] &7Ghast Spawner' = '&e&l[!] &7Generador de ghasts'
  '&e&l[!] &7Slime Spawner' = '&e&l[!] &7Generador de slimes'
  '&e&l[!] &7Guardian Spawner' = '&e&l[!] &7Generador de guardianes'
  '&e&l[!] &7Squid Spawner' = '&e&l[!] &7Generador de calamares'
  '&e&l[!] &7Hopper' = '&e&l[!] &7Tolva'
  '&e&l[!] &7Beacon' = '&e&l[!] &7Faro'
  '&e&l[!] &7Iron Block' = '&e&l[!] &7Bloque de hierro'
  '&e&l[!] &7Gold Block' = '&e&l[!] &7Bloque de oro'
  '&e&l[!] &7Diamond Block' = '&e&l[!] &7Bloque de diamante'
  '&e&l[!] &7Emerald Block' = '&e&l[!] &7Bloque de esmeralda'
  '&e&l[!] &7Bedrock' = '&e&l[!] &7Bedrock'
  '&e&l[!] &7Ender Chest' = '&e&l[!] &7Cofre de ender'
  '&e&l[!] &7Dragon Egg' = '&e&l[!] &7Huevo de dragón'
  '&e&l[!] &7Trapped Chest' = '&e&l[!] &7Cofre trampa'
  '&e&l[!] &7Chest' = '&e&l[!] &7Cofre'
  '&e&l[!] &7Enchantment Table' = '&e&l[!] &7Mesa de encantamientos'
}
foreach ($en in $valuesNamesFull.Keys) {
  $text = $text.Replace($en, $valuesNamesFull[$en])
}
$text = $text.Replace("&6&l* &e&lQuantity &fx{0}", "&6&l* &e&lCantidad &fx{0}")
$text = $text.Replace("&6&l* &e&lWorth: &f`${1}", "&6&l* &e&lValor: &f`${1}")
$text = $text.Replace("&6&l* &e&lLevel: &f`${2}", "&6&l* &e&lNivel: &f`${2}")
[IO.File]::WriteAllText((Join-Path (Get-Location) "src\main\resources\menus\es-ES\values.yml"), $text, (New-Object Text.UTF8Encoding $false))

# ============ upgrades1_20.yml ============
$text = [IO.File]::ReadAllText((Resolve-Path "src\main\resources\menus\upgrades1_20.yml").Path, [Text.Encoding]::UTF8)
$text = $text.Replace("title: '&lIsland Upgrades'", "title: '&lMejoras de la isla'")
$text = $text.Replace("`'{0}Previous Page`'", "'{0}Página anterior'")
$text = $text.Replace("`'{0}Next Page`'", "'{0}Página siguiente'")
$text = $text.Replace("name: '&aCurrent Page'", "name: '&aPágina actual'")
$text = $text.Replace("- '&7Page {0}'", "- '&7Página {0}'")
$text = $text.Replace("(Available)", "(Disponible)")
$text = $text.Replace("(Unavailable)", "(No disponible)")
$text = $text.Replace("&c&lMAX LEVEL", "&c&lNIVEL MÁXIMO")

$upgNames = [ordered]@{
  '&d&lHopper Increase' = '&d&lLímite de tolvas'
  '&e&lCrop Growth' = '&e&lCrecimiento de cultivos'
  '&b&lSpawner Boost' = '&b&lVelocidad de generadores'
  '&4&lPremium Drops' = '&4&lBotín premium'
  '&2&lMembers Size' = '&2&lTamaño de miembros'
  '&9&lBorder Size' = '&9&lTamaño del borde'
  '&3&lGenerator Boost' = '&3&lVelocidad de generadores'
  '&d&lMinecart Increase' = '&d&lLímite de vagones'
}
foreach ($en in $upgNames.Keys) {
  $text = $text.Replace($en, $upgNames[$en])
}

$upgLore = [ordered]@{
  "&7Purchasing the hopper upgrade" = "&7Comprar la mejora de tolvas"
  "&7will increase your island" = "&7aumentará el máximo"
  "&7max hoppers limit." = "&7de tolvas de tu isla."
  "&7Purchasing the growth upgrade" = "&7Comprar la mejora de crecimiento"
  "&7gives all crops within your island" = "&7hace que todos los cultivos de tu isla"
  "&7increased growth speed." = "&7crezcan más rápido."
  "&7Purchasing the spawner upgrade" = "&7Comprar la mejora de generadores"
  "&7gives all spawners within your island" = "&7hace que todos los generadores de tu isla"
  "&7increased spawn speed." = "&7aparezcan más rápido."
  "&7Purchasing the drops upgrade" = "&7Comprar la mejora de botín"
  "&7gives all mobs within your island" = "&7hace que todos los mobs de tu isla"
  "&7increased loot drops." = "&7suelten más botín."
  "&7Purchasing the members upgrade" = "&7Comprar la mejora de miembros"
  "&7will increase the maximum allowed" = "&7aumentará el número máximo"
  "&7members in your island." = "&7de miembros de tu isla."
  "&7Purchasing the border upgrade" = "&7Comprar la mejora del borde"
  "&7will increase the radius" = "&7aumentará el radio"
  "&7of your island." = "&7de tu isla."
  "&7Purchasing the generator upgrade" = "&7Comprar la mejora de generadores"
  "&7will increase rates of ores from" = "&7aumentará las tasas de minerales de los"
  "&7cobblestone generators in your island." = "&7generadores de piedra de tu isla."
  "&7Purchasing the minecart upgrade" = "&7Comprar la mejora de vagones"
  "&7max minecarts limit." = "&7de vagones de tu isla."
  "&7You have reached the maximum" = "&7Has alcanzado el nivel máximo"
  "&7level of the hoppers limit!" = "&7de tolvas permitidas!"
  "&7level of the crop growth!" = "&7de crecimiento de cultivos!"
  "&7level of the spawner rates!" = "&7de velocidad de generadores!"
  "&7level of the mob drops!" = "&7de botín de mobs!"
  "&7level of the members limit!" = "&7de miembros permitidos!"
  "&7level of the border size!" = "&7del tamaño del borde!"
  "&7level of the generator rates!" = "&7de tasas de generadores!"
  "&7level of the minecarts limit!" = "&7de vagones permitidos!"
  "&aClick to purchase upgrade." = "&aClic para comprar la mejora."
  "Amount:" = "Cantidad:"
  "Price:" = "Precio:"
  "x Hoppers" = "x Tolvas"
  "x Minecarts" = "x Vagones"
  " Members" = " Miembros"
  "x Drops" = "x Botín"
  "Growth: &f" = "Crecimiento: &f"
  "Boost: &f" = "Aumento: &f"
  "Radius: &f" = "Radio: &f"
  "&3Rates:" = "&3Tasas:"
  "x2 Speed" = "velocidad x2"
  "x3 Speed" = "velocidad x3"
  "x4 Speed" = "velocidad x4"
  "% Stone" = "% Piedra"
  "% Coal Ore" = "% Mineral de carbón"
  "% Redstone Ore" = "% Mineral de redstone"
  "% Lapis Ore" = "% Mineral de lapislázuli"
  "% Iron Ore" = "% Mineral de hierro"
  "% Gold Ore" = "% Mineral de oro"
  "% Diamond Ore" = "% Mineral de diamante"
  "% Emerald Ore" = "% Mineral de esmeralda"
  " Blocks" = " bloques"
}
foreach ($en in $upgLore.Keys) {
  $text = $text.Replace($en, $upgLore[$en])
}
[IO.File]::WriteAllText((Join-Path (Get-Location) "src\main\resources\menus\es-ES\upgrades.yml"), $text, (New-Object Text.UTF8Encoding $false))

"OK: values.yml and upgrades.yml translated"
