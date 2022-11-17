package org.orbisgis.poccarto

import picocli.CommandLine

class Exclusive {
    @CommandLine.ArgGroup(exclusive = false)
    Data data
    @CommandLine.Option(names = '--mcHelp', description = 'Documentation about .mc file.') boolean mcHelp
}
