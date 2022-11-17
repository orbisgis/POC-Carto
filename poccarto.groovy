import org.orbisgis.poccarto.PocCartoCommand

@Grab('org.orbisgis:poccarto:0.1-SNAPSHOT')
@Grab('info.picocli:picocli-groovy:4.7.0')
@Grab('org.geotools:gt-shapefile:27.2')
@Grab('org.geotools:gt-geojson-core:27.2')
@Grab('org.geotools:gt-main:27.2')
@Grab('org.geotools:gt-css:27.2')
@Grab('org.geotools:gt-render:27.2')

import static picocli.CommandLine.*
import groovy.transform.Field
import org.orbisgis.poccarto.Exclusive
import org.orbisgis.poccarto.PocCarto

@Command(name = 'poccarto', mixinStandardHelpOptions = true, version = '0.1',
        description = """
Render input map content (.mc) file into the output image file.
For more information about .mc file, use option '--mcHelp'.
""")
@picocli.groovy.PicocliScript

//Root argument group
@ArgGroup(exclusive = true, multiplicity = "1")
@Field Exclusive excl

PocCartoCommand.main(args)
