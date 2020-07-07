# beam-me-up
Spigot plugin that allows players to teleport between predefined locations. Working for Paper/Spigot servers above version 1.14.4+. Locations are saved in a a json file (`locations.json`) in the plugin resource folder. This is an example of a `locations.json` file.

```json
[
   {
      "world_uuid":"3a38f6ee-5609-4b45-b229-aa9e04fad0c7",
      "name":"pond",
      "x":391.86925637553327,
      "y":75.0,
      "z":-158.3667701447505
   },
   {
      "world_uuid":"3a38f6ee-5609-4b45-b229-aa9e04fad0c7",
      "name":"sand",
      "x":318.0848559182283,
      "y":63.0,
      "z":-119.50045447121425
   }
]
```

## Usage
`/beam [set, go] <args>`

### Set sub-command
Set takes your current location as well as a name and saves it to the locations.json file in your plugin resource folder. 
Example usage: `/beam set sand`

### Go sub-command
Go takes the argument provided, searches for a location saved under that name and then teleports you to that location.
Example usage: `/beam go pond`

**NB!** This plugin does not check for permissions! This will also be added at a later stage.
