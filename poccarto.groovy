import groovy.cli.Option
import org.geotools.feature.FeatureCollection

import javax.swing.WindowConstants
import javax.swing.text.html.HTMLDocument
import java.awt.Rectangle

@Grab('info.picocli:picocli-groovy:4.6.3')
@GrabResolver(name='osgeo', root='https://repo.osgeo.org/repository/release/')
@Grab('org.geotools:gt-render:27.2')
@Grab('org.geotools:gt-css:27.2')
@Grab('org.geotools:gt-main:27.2')
@Grab('org.geotools:gt-geojson-core:27.2')
@Grab('org.geotools:gt-shapefile:27.2')

import static picocli.CommandLine.*
import picocli.CommandLine.Parameters
import picocli.CommandLine.Option
import groovy.transform.Field
import groovy.json.JsonSlurper
import groovy.swing.SwingBuilder
import org.geotools.styling.css.CssParser
import org.geotools.styling.css.Stylesheet
import org.geotools.styling.css.CssTranslator
import org.geotools.data.DataStoreFinder
import org.geotools.data.geojson.GeoJSONReader
import org.geotools.geometry.jts.ReferencedEnvelope
import org.geotools.map.FeatureLayer
import org.geotools.map.MapContent
import org.geotools.map.MapViewport
import org.geotools.referencing.CRS
import org.geotools.renderer.lite.StreamingRenderer

import javax.imageio.ImageIO
import java.awt.BorderLayout
import java.awt.Color
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import javax.swing.JPanel
import java.awt.Graphics
import java.awt.Image
import javax.swing.JFrame;

import static java.awt.RenderingHints.*


@Command(name = 'poccarto', mixinStandardHelpOptions = true, version = '0.1',
        description = """
Render input map content (.mc) file into the output image file.
For more information about .mc file, use option '--mcHelp'.
""")
@picocli.groovy.PicocliScript

//Root argument group
@ArgGroup(exclusive = true, multiplicity = "1")
@Field Exclusive excl

//Sub arguments groups
class Data {
    @Parameters(index = '0', description = 'The input map cotent (.mc) file.') File input
    @Parameters(index = '1', description = 'The output image file.') File output
    @Option(names = ['-s','--show'],
            description = "Show the rendering result in a separated window. Window size can be set adding 'widthxheight' like '--show 300x240'.",
            arity = "0..1") String show
    @Option(names = '--no-replace', 
            description = 'Avoid to replace existing output file by adding a number at the end : out.png -> out1.png -> out2.png ...')
        boolean noReplace
}
class Exclusive {
    @ArgGroup(exclusive = false)
    Data data
    @Option(names = '--mcHelp', description = 'Documentation about .mc file.') boolean mcHelp
}

//Script core
if(excl.mcHelp) println("""
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
else {
    //Check input
    if (!excl.data.input) {
        println "Empty input"; return
    }
    //Check output and rename it if needed
    if (!excl.data.output) {
        println "No output provided"; return
    }
    def outFile = excl.data.output as File
    if(outFile.exists() && excl.data.noReplace) {
        def start = excl.data.output.absolutePath.split("\\.")[0]
        def end = excl.data.output.absolutePath.split("\\.")[1]
        for(i in 1..Integer.MAX_VALUE) {
            def newOutput = start+i+"."+end as File
            if(!newOutput.exists()) {
                excl.data.output = newOutput
                break
            }
        }
    }
    //Parse JSON
    def json = new JsonSlurper().parse(excl.data.input)
    //Check document width
    def width
    if(!json.width) {
        if (!json.w) {
            println "No output document width"; return
        }
        else
            width = json.w
    }
    else
        width = json.width
    //Check document height
    def height
    if(!json.height) {
        if (!json.h) {
            println "No output document height"; return
        }
        else
            height = json.h
    }
    else
        height = json.height

    CssTranslator translator = new CssTranslator()
    //MapContent
    def renderer = new StreamingRenderer()
    def hints = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
    renderer.java2DHints = hints
    def mc = new MapContent()
    mc.title = json.title

    if (!json.layers) {
        println "No Layer"; return
    }
    def mapBounds
    if(json.bbox)
        mapBounds = new ReferencedEnvelope(json.bbox[0], json.bbox[1], json.bbox[2], json.bbox[3], CRS.decode(json.crs))

    json.layers.each { layer ->
        //Style
        def styles = []
        layer.styles.each {
            Stylesheet ss = CssParser.parse(new File(excl.data.input.parent, it.style).text)
            styles << translator.translate(ss)
        }

        //Feature collection
        def features
        def data = new File(excl.data.input.parent, layer.data)
        if (data.name.endsWith(".json") || data.name.endsWith(".geojson")) {
            features = new GeoJSONReader(data.text).features
        } else if (data.name.endsWith(".shp")) {
            def ds = DataStoreFinder.getDataStore(["url": data.toURI().toURL()])
            features = ds.getFeatureSource(ds.typeNames[0]).features
        } else println("Unsupported file format : '$data'")
        if(!json.bbox && features) {
            if (!mapBounds)
                mapBounds = features.getBounds()
            else
                mapBounds.expandToInclude(features.getBounds())
        }

        //Rendering
        styles.each {
            mc.addLayer(new FeatureLayer(features, it))
        }
    }

    mc.viewport = new MapViewport(mapBounds)

    renderer.mapContent = mc
    def image = [width, height, BufferedImage.TYPE_INT_RGB] as BufferedImage
    def imageBounds = [0, 0, width, height] as Rectangle

    def gr = image.createGraphics()
    gr.paint = Color.WHITE
    gr.fill imageBounds

    renderer.paint gr, imageBounds, mapBounds
    ImageIO.write image, "png", excl.data.output
    println "File created at : ${excl.data.output.absoluteFile}"

    if (excl.data.show != null) {
        if(!excl.data.show.isEmpty() && ! (excl.data.show ==~ "\\d+x\\d+")) {
            println "Size doesn't match the pattern 'widthxheight' 'like 120x340'";return
        }
        def (fWidth, fHeight) = excl.data.show ? excl.data.show.split("x") : [image.width, image.height]
        def pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g)
                g.drawImage(image, 0, 0, fWidth as int, fHeight as int, null)
            }
        }

        JFrame frame = new JFrame()
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.setSize fWidth as int, fHeight as int
        frame.visible = true
        frame.add(pane)
    }
}

