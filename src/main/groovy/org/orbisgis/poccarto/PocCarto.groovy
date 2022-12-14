package org.orbisgis.poccarto

import groovy.json.JsonSlurper
import org.apache.commons.io.FilenameUtils
import org.geotools.data.DataStoreFinder
import org.geotools.data.geojson.GeoJSONReader
import org.geotools.factory.CommonFactoryFinder
import org.geotools.geometry.jts.ReferencedEnvelope
import org.geotools.map.FeatureLayer
import org.geotools.map.MapContent
import org.geotools.map.MapViewport
import org.geotools.referencing.CRS
import org.geotools.renderer.lite.StreamingRenderer
import org.geotools.styling.StyleFactory
import org.geotools.styling.StyleFactoryImpl
import org.geotools.styling.css.CssParser
import org.geotools.styling.css.CssTranslator
import org.geotools.styling.css.Stylesheet
import org.geotools.xml.styling.SLDParser
import org.geotools.xml.styling.SLDTransformer
import picocli.CommandLine

import javax.imageio.ImageIO
import javax.swing.*
import java.awt.*
import java.awt.image.BufferedImage

import static java.awt.RenderingHints.KEY_ANTIALIASING
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON

class PocCarto {
    static String run(Exclusive excl) {
        //Script core

        //Check input
        if (!excl.data.input) {
            println "Empty input"; return null
        }
        //Check output and rename it if needed
        if (!excl.data.output && !excl.data.show) {
            excl.data.show = ""
        }
        if (excl.data.output) {
            def outFile = excl.data.output as File
            if (outFile.exists() && excl.data.noReplace) {
                def (start, end) = excl.data.output.absolutePath.split("\\.")
                for (i in 1..Integer.MAX_VALUE) {
                    def newOutput = (start + i + "." + end) as File
                    if (!newOutput.exists()) {
                        excl.data.output = newOutput
                        break
                    }
                }
            }
        }
        //Parse JSON
        def json = new JsonSlurper().parse(excl.data.input)
        //Check document width
        def width
        if (!json.width) {
            if (!json.w) {
                println "No output document width"; return null
            } else
                width = json.w
        } else
            width = json.width
        //Check document height
        def height
        if (!json.height) {
            if (!json.h) {
                println "No output document height"; return null
            } else
                height = json.h
        } else
            height = json.height

        CssTranslator translator = new CssTranslator()
        StyleFactory styleFactory = new StyleFactoryImpl()

        def tmp_dir = System.getProperty("java.io.tmpdir")+ File.separator+"poc_carto"

        //MapContent
        def renderer = new StreamingRenderer()
        def hints = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
        renderer.java2DHints = hints
        MapContent mc = new MapContent()
        mc.title = json.title

        if (!json.layers) {
            println "No Layer"; return null
        }
        ReferencedEnvelope mapBounds
        if (json.bbox)
            mapBounds = new ReferencedEnvelope(json.bbox[0], json.bbox[1], json.bbox[2], json.bbox[3], CRS.decode(json.crs))

        json.layers.each { layer ->
            //Style
            def styles = []
            layer.styles.each {
                //Load the style
                def stylePath= it.toLowerCase()
                if (stylePath.endsWith(".css")) {
                    Stylesheet ss = CssParser.parse(new File(it).text)
                    def css_style =translator.translate(ss)
                    styles << translator.translate(ss)
                    if(true){
                        def fileNameWithoutExt = FilenameUtils.removeExtension(it)
                        SLDTransformer sldTransformer = new SLDTransformer()
                        sldTransformer.setIndentation(4)
                        sldTransformer.transform(css_style, new FileOutputStream(new File(tmp_dir+File.separator+"${fileNameWithoutExt}.sld")))
                    }
                }
                else if(stylePath.endsWith(".sld")){
                    SLDParser stylereader = new SLDParser(styleFactory, new File(it))
                    styles << stylereader.readXML()[0]
                }
            }

            //Feature collection
            def features
            def data = new File(layer.data)
            def ds = DataStoreFinder.getDataStore(["url": data.toURI().toURL()])
            if(!ds) {
                println("Unsupported file format : '$data'")
            }
            else {
                features = ds.getFeatureSource(ds.typeNames[0]).features
                if (!json.bbox && features) {
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
        }

        MapViewport viewport = mc.getViewport()
        viewport.setBounds(  mapBounds )
        viewport.setScreenArea(new Rectangle(width, height));
        mc.viewport = viewport
        renderer.mapContent = mc
        def image = [width, height, BufferedImage.TYPE_INT_RGB] as BufferedImage
        def imageBounds = [0, 0, width, height] as Rectangle

        def gr = image.createGraphics()
        gr.paint = Color.WHITE
        gr.fill imageBounds

        renderer.paint gr, viewport.getScreenArea(), viewport.getBounds()
        if(excl.data.output) {
            ImageIO.write image, "png", excl.data.output
            println "File created at : ${excl.data.output.absoluteFile}"
        }
        mc.dispose()

        if (excl.data.show != null) {
            if (!excl.data.show.isEmpty() && !(excl.data.show ==~ "\\d+x\\d+")) {
                println "Size doesn't match the pattern 'widthxheight' 'like 120x340'"; return excl.data.output
            }
            def (fWidth, fHeight) = excl.data.show ? excl.data.show.split("x") : [image.width, image.height]
            def pane = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g)
                    g.drawImage(image, 0, 0, fWidth as int, fHeight as int, null)
                }
            }

            def frame = new JFrame()
            frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            frame.setLayout(new BorderLayout())
            frame.add(pane, BorderLayout.CENTER)
            frame.visible = true
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                void run() {
                    frame.setSize fWidth as int, (fHeight as int + frame.insets.top)
                }
            })
        }
        return excl.data.output
    }
}