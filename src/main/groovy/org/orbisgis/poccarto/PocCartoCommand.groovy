package org.orbisgis.poccarto


import picocli.CommandLine

import java.util.concurrent.Callable

@CommandLine.Command(name = 'poccarto', mixinStandardHelpOptions = true, version = '0.1',
        description = """
Render input map content (.mc) file into the output image file.
For more information about .mc file, use option '--mcHelp'.
""")
class PocCartoCommand implements Callable<Integer> {

    //Root argument group
    @CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
    Exclusive excl

    Integer call() {
        //Script core
        if (excl.mcHelp) {
            println("""
The .mc file is the serialization of a map content under json format : 
{
    "title":"title of the map",
    "w"/"width": 300,
    "h"/"height": 400,
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
    "crs":"EPSG:4326",//Optional, only if a bbox is defined
    "bbox":[-0.1,2.1,50.0,48.4] //Optional, is not set, use the data computed bbox.
}
""")
        return 0
    }
        else {
            def result = PocCarto.run(excl)
            return result ? 0 : 1
        }
    }

    static void main(String[] args) {
        new CommandLine(new PocCartoCommand()).execute(args)
    }
}