<h1><img width=80 src="https://github.com/MrQuackDuck/DeathDropPercent/assets/61251075/b6f9133b-86f6-45d1-a046-15686d956b87" /> <div>DeathDropPercent</div></h1>
<p>
  <a href="https://www.java.com/"><img src="https://img.shields.io/badge/Java-gray" /></a>
  <a href="https://hub.spigotmc.org/javadocs/spigot/"><img src="https://img.shields.io/badge/SpigotAPI-orange" /></a>
  <a href="https://github.com/vshymanskyy/StandWithUkraine"><img src="https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/badges/StandWithUkraine.svg"></a>
</p>
  
 **DeathDropPercent** is a **Spigot** plugin that changes the behavior of a player's death.

<p>After death, a player drops only a certain <b>percentage</b> of his inventory slots. Slots are chosen <b>randomly</b>.</p>

Example: if `percentToDrop` is set to 0.5 (which means 50%), the player will lose half of his inventory slots.

> [!NOTE]
> The `percentToDrop` can be edited in the `config.yml` file.
</p>

## Commands
- `/ddc info` — shows the current state of the plugin and the current percentage of items to drop after death.
- `/ddc enable` — enables the plugin.
- `/ddc disable` — disables the plugin.
- `/ddc reload` — reloads the config.

## Permissions

- `deathdroppercent.admin` - Allows to use `/ddc` command
- `deathdroppercent.drop` - Is the player **supposted** to drop items on death? (**true** by default)

## Default config

```yml
# When set to false, server will have default death behaviour
isEnabled: true
# Default percentage of inventory to drop on death (from 0.0 to 1.0)
# Inventory has 41 slots in total, including armor and second hand
percentToDrop: 0.5

# Overridden percentages for certain permissions
# IMPORTANT: If you want to enable custom percents for certain permissions,
# don't also forget to add these permissions to groups/players
# like that: deathdroppercent.custom.vip (and don't forget to uncomment the code below)
customPercents:
#  'vip': 0.4
#  'mvp': 0.2
```

## Getting started

> [!IMPORTANT]
> Before getting started, make sure that the plugin's version is **compatible** with your server version.

1. Download the plugin from <a href="https://github.com/MrQuackDuck/DeathDropPercent/releases">Releases</a> tab or from <a href="https://www.spigotmc.org/resources/deathdroppercent.114763/">Spigot</a> page.
1. Put downloaded `.jar` into `/plugins` folder of your server.
1. Restart your server or enter `reload` command.
