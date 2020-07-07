# beam-me-up
Spigot plugin that allows players to teleport between predefined locations. Working for Paper/Spigot servers above version 1.14.4+.

## Usage
`/beam [set, go] <args>`

### Set sub-command
Set takes your current location as well as a name and saves it to the locations.json file in your plugin resource folder. 
Example usage: `/beam set Home`

### Go sub-command
Go takes the argument provided, searches for a location saved under that name and then teleports you to that location.
Example usage: `/beam go Home`

**NB!** This plugin does not check for permissions! This will also be added at a later stage.
