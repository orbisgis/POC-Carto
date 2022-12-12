package org.orbisgis.poccarto

import picocli.CommandLine

class Data {
    @CommandLine.Parameters(index = '0', description = 'The input map content (.mc) file.')
    File input
    @CommandLine.Parameters(index = '1', description = 'Optional output image file. If not set, the output will be show (see --show option)', arity = "0..1")
    File output
    @CommandLine.Option(names = ['-s', '--show'],
            description = "Show the rendering result in a separated window. Window size can be set adding 'widthxheight' like '--show 300x240'.",
            arity = "0..1")
    String show
    @CommandLine.Option(names = '--no-replace',
            description = 'Avoid to replace existing output file by adding a number at the end : out.png -> out1.png -> out2.png ...')
    boolean noReplace

}
