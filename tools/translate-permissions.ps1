$ErrorActionPreference = 'Stop'
$srcPath = "src\main\resources\menus\permissions1_21.yml"
$dstPath = "src\main\resources\menus\es-ES\permissions.yml"
$text = [IO.File]::ReadAllText((Resolve-Path $srcPath).Path, [Text.Encoding]::UTF8)

# Boilerplate
$text = $text.Replace("title: '&lPermissions Controller'", "title: '&lPermisos de la isla'")
$text = $text.Replace("`'{0}Previous Page`'", "'{0}Página anterior'")
$text = $text.Replace("`'{0}Next Page`'", "'{0}Página siguiente'")
$text = $text.Replace("name: '&aCurrent Page'", "name: '&aPágina actual'")
$text = $text.Replace("- '&7Page {0}'", "- '&7Página {0}'")
$text = $text.Replace("&7Currently &aENABLED&7.", "&7Actualmente &aACTIVADO&7.")
$text = $text.Replace("&7Currently &cDISABLED&7.", "&7Actualmente &cDESACTIVADO&7.")
$text = $text.Replace("&7Role: &e{}&7.", "&7Rol: &e{}&7.")

# Permission display names
$names = [ordered]@{
  'All' = 'Todos'
  'Allay Interact' = 'Interactuar con allays'
  'Animal Breed' = 'Cría de animales'
  'Animal Damage' = 'Dañar animales'
  'Animal Shear' = 'Esquilar animales'
  'Animal Spawn' = 'Invocar animales'
  'Ban Member' = 'Banear miembros'
  'Brush' = 'Cepillar'
  'Break' = 'Romper bloques'
  'Build' = 'Construir'
  'Change Name' = 'Cambiar nombre'
  'Chest Access' = 'Acceso a cofres'
  'Chorus Fruit' = 'Fruta chorus'
  'Locked Bypass' = 'Ignorar bloqueo'
  'Lock Island' = 'Cerrar isla'
  'Coop Member' = 'Añadir coop'
  'Copper Golem Interact' = 'Interactuar con gólem de cobre'
  'Delete Warp' = 'Eliminar warps'
  'Demote Members' = 'Degradar miembros'
  'Deposit Money' = 'Depositar dinero'
  'Disband Island' = 'Eliminar isla'
  'Discord Show' = 'Ver Discord'
  'Drop Items' = 'Soltar objetos'
  'Dye Sheep' = 'Teñir ovejas'
  'Ender Pearl' = 'Perla de ender'
  'Entity Ride' = 'Montar entidades'
  'Expel Bypass' = 'Ignorar expulsión'
  'Expel Players' = 'Expulsar jugadores'
  'Farm Tramping' = 'Pisotear cultivos'
  'Fertilize' = 'Fertilizar'
  'Fish' = 'Pescar'
  'Fly' = 'Volar'
  'Horse Interact' = 'Interactuar con caballos'
  'Ignite Creepers' = 'Encender creepers'
  'Interact' = 'Interactuar'
  'Invite Member' = 'Invitar miembros'
  'Community Chest' = 'Cofre comunitario'
  'Item Frame' = 'Marco'
  'Kick Member' = 'Expulsar miembros'
  'Leash Mobs' = 'Atar mobs'
  'Minecart Damage' = 'Dañar vagones'
  'Minecart Enter' = 'Entrar en vagones'
  'Minecart Open' = 'Abrir vagones'
  'Minecart Place' = 'Colocar vagones'
  'Monster Damage' = 'Dañar monstruos'
  'Monster Spawn' = 'Invocar monstruos'
  'Nautilus Interact' = 'Interactuar con nautilos'
  'Name Entity' = 'Nombrar entidades'
  'Open Island' = 'Abrir isla al público'
  'Painting' = 'Cuadros'
  'Paypal Show' = 'Ver PayPal'
  'Pickup Axolotls' = 'Recoger ajolotes'
  'Pickup Drops' = 'Recoger objetos'
  'Pickup Fish' = 'Recoger peces'
  'Pickup Lectern Book' = 'Recoger libro del atril'
  'Promote Members' = 'Ascender miembros'
  'Upgrades Rankup' = 'Mejorar isla'
  'Ratings Show' = 'Ver calificaciones'
  'Saddle Entity' = 'Poner silla'
  'Sculk Sensor' = 'Sensor sculk'
  'Set Biome' = 'Cambiar bioma'
  'Set Discord' = 'Cambiar Discord'
  'Set Home' = 'Cambiar hogar'
  'Set Paypal' = 'Cambiar PayPal'
  'Set Permission' = 'Cambiar permisos'
  'Set Role' = 'Cambiar rol'
  'Set Settings' = 'Cambiar ajustes'
  'Set Warp' = 'Crear warps'
  'Sign Interact' = 'Interactuar con carteles'
  'Spawner Break' = 'Romper generadores'
  'Turtle Eggs Tramping' = 'Pisotear huevos de tortuga'
  'Tamed Animal Damage' = 'Dañar animales domados'
  'Uncoop Member' = 'Quitar coop'
  'Use' = 'Usar'
  'Valuable Blocks Break' = 'Romper bloques valiosos'
  'Villagers Trading' = 'Comerciar con aldeanos'
  'Wind Charge' = 'Carga de viento'
  'Withdraw Money' = 'Retirar dinero'
}

foreach ($en in $names.Keys) {
  $text = $text.Replace("name: '&6$en'", "name: '&6" + $names[$en] + "'")
}

# Permission descriptions
$lores = [ordered]@{
  "- '&7Access to all the abilities of the island.'" = "- '&7Acceso a todas las funciones de la isla.'"
  "- '&7Access to interact with allays inside the island.'" = "- '&7Acceso a interactuar con allays en la isla.'"
  "- '&7Access to breed animals inside the island.'" = "- '&7Acceso a criar animales en la isla.'"
  "- '&7Access to damage animals inside the island.'" = "- '&7Acceso a dañar animales en la isla.'"
  "- '&7Access to shear animals inside the island.'" = "- '&7Acceso a esquilar animales en la isla.'"
  "- '&7Access to spawn animals inside the island.'" = "- '&7Acceso a invocar animales en la isla.'"
  "- '&7Access to ban members from the island.'" = "- '&7Acceso a banear miembros de la isla.'"
  "- '&7Access to break blocks inside the island.'" = "- '&7Acceso a romper bloques en la isla.'"
  "- '&7Access to brush suspicious blocks inside the island.'" = "- '&7Acceso a cepillar bloques sospechosos en la isla.'"
  "- '&7Access to place blocks inside the island.'" = "- '&7Acceso a colocar bloques en la isla.'"
  "- '&7Access to change the island''s name.'" = "- '&7Acceso a cambiar el nombre de la isla.'"
  "- '&7Access to open chests inside the island.'" = "- '&7Acceso a abrir cofres en la isla.'"
  "- '&7Access to consume chorus fruits inside the island.'" = "- '&7Acceso a comer frutas chorus en la isla.'"
  "- '&7Access to bypass the island''s lock.'" = "- '&7Acceso a ignorar el bloqueo de la isla.'"
  "- '&7Access to lock the island from public.'" = "- '&7Acceso a cerrar la isla al público.'"
  "- '&7Access to add a player as a co-op member.'" = "- '&7Acceso a añadir un jugador como coop.'"
  "- '&7Access to take items from copper golems.'" = "- '&7Acceso a quitar objetos de los gólems de cobre.'"
  "- '&7Access to delete island warps.'" = "- '&7Acceso a eliminar warps de la isla.'"
  "- '&7Access to demote members inside the island.'" = "- '&7Acceso a degradar miembros de la isla.'"
  "- '&7Access to deposit money into the island''s bank.'" = "- '&7Acceso a depositar dinero en el banco de la isla.'"
  "- '&7Access to disband the island.'" = "- '&7Acceso a eliminar la isla.'"
  "- '&7Access to check the island''s linked discord.'" = "- '&7Acceso a ver el Discord vinculado de la isla.'"
  "- '&7Access to drop items inside the island.'" = "- '&7Acceso a soltar objetos en la isla.'"
  "- '&7Access to dye sheep inside the island.'" = "- '&7Acceso a teñir ovejas en la isla.'"
  "- '&7Access to use ender pearls inside the island.'" = "- '&7Acceso a usar perlas de ender en la isla.'"
  "- '&7Access to ride entities inside the island.'" = "- '&7Acceso a montar entidades en la isla.'"
  "- '&7Access to bypass from being expelled.'" = "- '&7Acceso a ignorar las expulsiones.'"
  "- '&7Access to expel players from the island.'" = "- '&7Acceso a expulsar jugadores de la isla.'"
  "- '&7Access to tramp farmlands inside the island.'" = "- '&7Acceso a pisotear tierras de cultivo en la isla.'"
  "- '&7Access to fertilize blocks around the island.'" = "- '&7Acceso a fertilizar bloques en la isla.'"
  "- '&7Access fishing around the island.'" = "- '&7Acceso a pescar en la isla.'"
  "- '&7Access fly around the island.'" = "- '&7Acceso a volar en la isla.'"
  "- '&7Access to interact with horses inside the island.'" = "- '&7Acceso a interactuar con caballos en la isla.'"
  "- '&7Access to ignite creepers with flint and steel inside the island.'" = "- '&7Acceso a encender creepers con mechero en la isla.'"
  "- '&7Access to interact with blocks inside the island.'" = "- '&7Acceso a interactuar con bloques en la isla.'"
  "- '&7Access to invite new members to the island.'" = "- '&7Acceso a invitar nuevos miembros a la isla.'"
  "- '&7Access to the community chest of the island.'" = "- '&7Acceso al cofre comunitario de la isla.'"
  "- '&7Access to interact with item frames inside the island.'" = "- '&7Acceso a interactuar con marcos en la isla.'"
  "- '&7Access to kick members from the island.'" = "- '&7Acceso a expulsar miembros de la isla.'"
  "- '&7Access to leash mobs inside the island.'" = "- '&7Acceso a atar mobs en la isla.'"
  "- '&7Access to damage minecarts inside the island.'" = "- '&7Acceso a dañar vagones en la isla.'"
  "- '&7Access to enter minecarts inside the island.'" = "- '&7Acceso a entrar en vagones en la isla.'"
  "- '&7Access to open minecarts inside the island.'" = "- '&7Acceso a abrir vagones en la isla.'"
  "- '&7Access to place minecarts inside the island.'" = "- '&7Acceso a colocar vagones en la isla.'"
  "- '&7Access to damage monsters inside the island.'" = "- '&7Acceso a dañar monstruos en la isla.'"
  "- '&7Access to spawn monsters inside the island.'" = "- '&7Acceso a invocar monstruos en la isla.'"
  "- '&7Access to interact with nautiluses inside the island.'" = "- '&7Acceso a interactuar con nautilos en la isla.'"
  "- '&7Access to name-tag entities inside the island.'" = "- '&7Acceso a nombrar entidades en la isla.'"
  "- '&7Access to open the island to the public.'" = "- '&7Acceso a abrir la isla al público.'"
  "- '&7Access to interact with paintings inside the island.'" = "- '&7Acceso a interactuar con cuadros en la isla.'"
  "- '&7Access to check the island''s linked paypal.'" = "- '&7Acceso a ver el PayPal vinculado de la isla.'"
  "- '&7Access to pickup axolotls using a water bucket inside the island.'" = "- '&7Acceso a recoger ajolotes con un cubo en la isla.'"
  "- '&7Access to pickup items inside the island.'" = "- '&7Acceso a recoger objetos en la isla.'"
  "- '&7Access to pickup fish using buckets inside the island.'" = "- '&7Acceso a recoger peces con cubos en la isla.'"
  "- '&7Access to pickup books out of lecterns inside the island.'" = "- '&7Acceso a recoger libros de atriles en la isla.'"
  "- '&7Access to promote members inside the island.'" = "- '&7Acceso a ascender miembros en la isla.'"
  "- '&7Access to rankup island''s upgrades.'" = "- '&7Acceso a mejorar las estadísticas de la isla.'"
  "- '&7Access to see all island''s ratings.'" = "- '&7Acceso a ver las calificaciones de la isla.'"
  "- '&7Access to add saddle to entities inside the island.'" = "- '&7Acceso a poner sillas a entidades en la isla.'"
  "- '&7Access to trigger sculk sensors inside the island.'" = "- '&7Acceso a activar sensores sculk en la isla.'"
  "- '&7Access to change the island''s biome.'" = "- '&7Acceso a cambiar el bioma de la isla.'"
  "- '&7Access to change the discord linked to the island.'" = "- '&7Acceso a cambiar el Discord vinculado a la isla.'"
  "- '&7Access to change the home location of the island.'" = "- '&7Acceso a cambiar el punto de aparición de la isla.'"
  "- '&7Access to change the paypal linked to the island.'" = "- '&7Acceso a cambiar el PayPal vinculado a la isla.'"
  "- '&7Access to change permissions for island members.'" = "- '&7Acceso a cambiar permisos de los miembros.'"
  "- '&7Access to change roles for island members.'" = "- '&7Acceso a cambiar roles de los miembros.'"
  "- '&7Access to change settings for islands.'" = "- '&7Acceso a cambiar los ajustes de la isla.'"
  "- '&7Access to set new warps for the island.'" = "- '&7Acceso a crear nuevos warps en la isla.'"
  "- '&7Access to interact with signs inside the island.'" = "- '&7Acceso a interactuar con carteles en la isla.'"
  "- '&7Access to break spawners inside the island.'" = "- '&7Acceso a romper generadores en la isla.'"
  "- '&7Access to tramp turtle eggs inside the island.'" = "- '&7Acceso a pisotear huevos de tortuga en la isla.'"
  "- '&7Access to damage tamed animals inside the island.'" = "- '&7Acceso a dañar animales domados en la isla.'"
  "- '&7Access to remove co-op members from the island.'" = "- '&7Acceso a quitar miembros coop de la isla.'"
  "- '&7Access to use different blocks inside the island.'" = "- '&7Acceso a usar distintos bloques en la isla.'"
  "- '&7Access to break valuable blocks inside the island.'" = "- '&7Acceso a romper bloques valiosos en la isla.'"
  "- '&7Access to trade with villagers inside the island.'" = "- '&7Acceso a comerciar con aldeanos en la isla.'"
  "- '&7Access to use wind charges inside the island.'" = "- '&7Acceso a usar cargas de viento en la isla.'"
  "- '&7Access to withdraw money from the island''s bank.'" = "- '&7Acceso a retirar dinero del banco de la isla.'"
}

foreach ($en in $lores.Keys) {
  $text = $text.Replace($en, $lores[$en])
}

New-Item -ItemType Directory -Force -Path "src\main\resources\menus\es-ES" | Out-Null
[IO.File]::WriteAllText((Join-Path (Get-Location) $dstPath), $text, (New-Object Text.UTF8Encoding $false))

# Report anything left in English (heuristic: lines that still contain common EN words inside quotes)
$leftover = Select-String -Path $dstPath -Pattern "&7(?!Actualmente|Rol:)" | Select-String -Pattern "Access to|Currently" 
if ($leftover) { "LEFTOVER:"; $leftover } else { "OK: all permission texts translated" }
