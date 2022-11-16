import groovy.cli.Option

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
@Field Exclusive exclusive

//Sub arguments groups
class Data {
    @Parameters(index = '0', description = 'The input map cotent (.mc) file.') File input
    @Parameters(index = '1', description = 'The output image file.') File output
    @Option(names = ['-s','--show'],
            description = "Show the rendering result in a separated window. Window size can be set adding 'widthxheight' like '--show 300x240'.",
            arity = "0..1") String show
}
class Exclusive {
    @ArgGroup(exclusive = false)
    Data data
    @Option(names = '--mcHelp', description = 'Documentation about .mc file.') boolean mcHelp
}

//Script core
if(exclusive.mcHelp) println("""
The .mc file is the serialization of a map content under json format : 
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
""")
else {
    if (!exclusive.data.input) {
        println "Empty input"; return
    }
    def json = new JsonSlurper().parse(exclusive.data.input)
    CssTranslator translator = new CssTranslator()

    //MapContent
    def renderer = new StreamingRenderer()
    def hints = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
    renderer.java2DHints = hints
    def mc = new MapContent()
    mc.title = json.title
    if (!json.bbox) {
        println "No BBOX provided"; return
    }
    if (!json.crs) {
        println "No CRS provided"; return
    }
    def mapBounds = new ReferencedEnvelope(json.bbox[0], json.bbox[1], json.bbox[2], json.bbox[3], CRS.decode(json.crs))
    mc.viewport = new MapViewport(mapBounds)

    if (!json.layers) {
        println "No Layer"; return
    }
    json.layers.each { layer ->
        //Style
        def styles = []
        layer.styles.each {
            Stylesheet ss = CssParser.parse(new File(exclusive.data.input.parent, it.style).text)
            styles << translator.translate(ss)
        }

        //Feature collection
        def features
        def data = new File(exclusive.data.input.parent, layer.data)
        if (data.name.endsWith(".json") || data.name.endsWith(".geojson")) {
            features = new GeoJSONReader(data.text).features
        } else if (data.name.endsWith(".shp")) {
            def ds = DataStoreFinder.getDataStore(["url": data.toURI().toURL()])
            features = ds.getFeatureSource(ds.typeNames[0]).features
        } else println("Unsupported file format : '$data'")

        //Rendering
        styles.each {
            mc.addLayer(new FeatureLayer(features, it))
        }
    }

    renderer.mapContent = mc

    if (!json.w) {
        println "No output document width"; return
    }
    if (!json.h) {
        println "No output document height"; return
    }
    def image = [json.w, json.h, BufferedImage.TYPE_INT_RGB] as BufferedImage
    def imageBounds = [0, 0, json.w, json.h] as Rectangle

    def gr = image.createGraphics()
    gr.paint = Color.WHITE
    gr.fill imageBounds

    renderer.paint gr, imageBounds, mapBounds
    if (!exclusive.data.output) {
        println "No output provided"; return
    }
    ImageIO.write image, "png", exclusive.data.output
    println "File created at : ${exclusive.data.output.absoluteFile}"

    if (exclusive.data.show != null) {
        if(! (exclusive.data.show ==~ "\\d+x\\d+")) {
            println "size doesn't match the pattern 'widthxheight' 'like 120x340'";return
        }
        def (width, height) = exclusive.data.show ? exclusive.data.show.split("x") : [image.width, image.height]
        def pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g)
                g.drawImage(image, 0, 0, width as int, height as int, null)
            }
        }

        JFrame frame = new JFrame()
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.setSize width as int, height as int
        frame.visible = true
        frame.add(pane)
    }
}

