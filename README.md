# POC-Carto
Proof of concept for cartographic document generation using GeoTools libraries.

This library need you to have a Java _JRE 11_ and Groovy 3.0.X installed.

It renders the input `.mc` file into the output image (like `.png` or `.jpg).

## Java JAR

The Java JAR can be found [here](https://nightly.link/orbisgis/POC-Carto/workflows/CI_snapshot/main/poccarto-command.zip) and shouldbe run as a bash command line :
```bash
java -jar poccarto-*-command.jar [-hV] (--mcHelp | [<input> [<output>] [-s[=<show>]] [--no-replace]])
# Very simple usage
java -jar poccarto-*-command.jar inputMCFile.mc outputImageFile.png
# To just display the result
java -jar poccarto-*-command.jar inputMCFile.mc
```

## Groovy Script

The groovy script [poccarto.groovy](https://raw.githubusercontent.com/orbisgis/POC-Carto/main/poccarto.groovy) should be run as a bash command line.
It renders the input `.mc` file into the output image (like `.png` or `.jpg).

```bash 
# General usage
groovy poccarto.groovy [-hV] (--mcHelp | [<input> [<output>] [-s[=<show>]] [--no-replace]])
# Very simple usage
groovy poccarto.groovy inputMCFile.mc outputImageFile.png
# To just display the result
groovy poccarto.groovy inputMCFile.mc
```

## Command usage
Render input map content (.mc) file into the output image file.
If the output file exists, it is replaced by the new content. To avoid that, the option `--no-replace` should be used.
For more information about .mc file, use option `--mcHelp`.

`<input>`     The input map content (.mc) file.

`[<output>]`    Optional output image file. If not set, the generated map whill be display (see `--show` option).

`-h, --help`      Show this help message and exit.

`--mcHelp`    Documentation about .mc file.

`--no-replace`      Avoid to replace existing output file by adding a number at the end : out.png -> out1.png -> out2.png ...


`-s, --show[=<show>]`   Show the rendering result in a separated window. Window size can be set adding 'widthxheight' like '--show 300x240'.


`-V, --version`   Print version information and exit.

## MC file

The .mc file is the serialization of a map content under json format.

The width can be defined using `"w"` or `"width"`.
The height can be defined using `"h"` or `"height"`.

```json
{
    "title":"title of the map",
    "w"/"width": 300,
    "h"/"height": 400,
    "layers":[
        {
            "data":"path to your data file (.geojson format)", 
            "styles":["path to your style file (.css format)", "path to your style file (.css format)", ...]
        },
        {
            "data":"path to your second data file (.geojson format)", 
            "styles":[...]
        },
        ...
    ],
    "crs":"EPSG:4326", //Optional, only if a bbox is defined
    "bbox":[-0.1,2.1,50.0,48.4] //Optional, is not set, use the data computed bbox.
}
```
