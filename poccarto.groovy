import org.geotools.geometry.jts.ReferencedEnvelope
import org.geotools.map.FeatureLayer
import org.geotools.map.MapContent
import org.geotools.map.MapViewport
import org.geotools.referencing.CRS
import org.geotools.renderer.lite.StreamingRenderer

import javax.imageio.ImageIO
import java.awt.Color
import java.awt.RenderingHints
import java.awt.image.BufferedImage

import static java.awt.RenderingHints.*
@Grab('info.picocli:picocli-groovy:4.6.3')
@GrabResolver(name='osgeo', root='https://repo.osgeo.org/repository/release/')
@Grab('org.geotools:gt-render:27.2')
@Grab('org.geotools:gt-css:27.2')
@Grab('org.geotools:gt-main:27.2')
@Grab('org.geotools:gt-geojson-core:27.2')
@Grab('org.geotools:gt-shapefile:27.2')

import static picocli.CommandLine.*
import groovy.transform.Field
import groovy.json.JsonSlurper
import org.geotools.styling.css.CssParser
import org.geotools.styling.css.Stylesheet
import org.geotools.styling.css.CssTranslator
import org.geotools.data.DataStoreFinder
import org.geotools.data.geojson.GeoJSONReader

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
    if(!exclusive.data.input) {println "Empty input";return}
    def json = new JsonSlurper().parse(exclusive.data.input)
    CssTranslator translator = new CssTranslator()

    //MapContent
    def renderer = new StreamingRenderer()
    def hints = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
    renderer.java2DHints = hints
    def mc = new MapContent()
    mc.title = json.title
    if(!json.bbox) {println "No BBOX provided";return}
    if(!json.crs) {println "No CRS provided";return}
    def mapBounds = new ReferencedEnvelope(json.bbox[0], json.bbox[1], json.bbox[2], json.bbox[3], CRS.decode(json.crs))
    mc.viewport = new MapViewport(mapBounds)

    if(!json.layers) {println "No Layer";return}
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
            features = new GeoJSONReader(data.text).getFeatures()
        } else if (data.name.endsWith(".shp")) {
            def ds = DataStoreFinder.getDataStore(["url": data.toURI().toURL()])
            features = ds.getFeatureSource(ds.getTypeNames()[0]).getFeatures()
        } else println("Unsupported file format : '$data'")

        //Rendering
        styles.each {
            mc.addLayer(new FeatureLayer(features, it))
        }
    }

    renderer.mapContent = mc

    if(!json.w) {println "No output document width";return}
    if(!json.h) {println "No output document height";return}
    BufferedImage image = new BufferedImage(json.w, json.h, BufferedImage.TYPE_INT_RGB)
    def imageBounds = new java.awt.Rectangle(0, 0, json.w, json.h)

    def gr = image.createGraphics()
    gr.setPaint(Color.WHITE)
    gr.fill(imageBounds)

    renderer.paint(gr, imageBounds, mapBounds)
    if(!exclusive.data.output) {println "No output provided";return}
    ImageIO.write(image, "png", exclusive.data.output)
    println("File created at : ${exclusive.data.output.absoluteFile}")
}