# beam-me-up
Spigot plugin that allows players to teleport between predefined locations. Working for Paper/Spigot servers above 
version 1.14.4+. The locations are saved in a json file (`locations.json`) in the plugin resource folder. This is an 
example of a `locations.json` file.

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

This plugin SHOULD have multiverse support (saves worlds as UUID) however I have not tested it, so try at your own risk!

## [Changelog](CHANGELOG.md)

## Usage
```
/beam go <location>
/beam set <location>
/beam list
/beam remove <location>
```

### Set sub-command
The set command takes your current location as well as a name and saves it to the locations.json file in your plugin 
resource folder. 

Example usage: `/beam set sand`

### Go sub-command
The go command takes the location name argument provided, searches for a location saved under that name and then 
teleports the player using the command to that location.

Example usage: `/beam go pond`

### List sub-command
The list command lists all the names of locations currently saved in `locations.json`

Example usage: `/beam list`

### Remove sub-command
The remove command takes the location name argument provided and attempts to remove it from the `locations.json` file.

Example usage: `/beam remove sand`

## Permissions
```yaml
permissions:
  beam.command:
    description: Permission that allows usage of the base beam command
    default: op

  beam.*:
    description: Permission to allow all sub-commands
    default: op
    children:
      beam.go: true
      beam.set: true
      beam.list: true
      beam.remove: true

  beam.go:
    description: Permission to allow the go sub-command
    default: true
    children:
      beam.command: true

  beam.set:
    description: Permission to allow the set sub-command
    default: false
    children:
      beam.command: true

  beam.list:
    description: Permission to allow the list sub-command
    default: true
    children:
      beam.command: true

  beam.remove:
    description: Permission to allow the remove sub-command
    default: false
    children:
      beam.command: true
```
