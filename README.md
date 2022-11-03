# POC-Carto
Proof of concept for cartographic document generation using GeoTools libraries.

This library need you to have a Java JRE 11 and Groovy installed.

## Command
Simple script: 
```bash 
groovy poccarto.groovy [-hV] (--mcHelp | [<input> <output>])
```

Render input map content (.mc) file into the output image file.
For more information about .mc file, use option `--mcHelp`.

`<input>`     The input map content (.mc) file.

`<output>`    The output image file.

`-h, --help`      Show this help message and exit.

`--mcHelp`    Documentation about .mc file.

`-V, --version`   Print version information and exit.

## MC file

The .mc file is the serialization of a map content under json format :

```json
{
    "title":"title of the map",
    "layers":[
        {
            "data":"path to your data file (.geojson format)", 
            "styles":[
                {"style":"path to your style file (.css format)"}, 
                {"style":"path to your style file (.css format)"},
                ...
            ]
        },
        {
            "data":"path to your second data file (.geojson format)", 
            "styles":[
                ...
            ]
        },
        ...
    ],
    "crs":"EPSG:4326",
    "bbox":[-0.1,2.1,50.0,48.4]
}
```