//@GrabResolver(name='orbisgis', root='https://oss.sonatype.org/content/repositories/snapshots/')
//@Grab(group='org.orbisgis', module='poccarto', version='0.1-SNAPSHOT')

import org.apache.commons.io.FilenameUtils
import org.orbisgis.poccarto.PocCartoCommand
import picocli.CommandLine


def mapContextFolder =  "/home/ebocher/Autres/codes/POC-Carto/mapcontext";

if(!mapContextFolder){
    println("Please set a valid mapcontext folder")
}

def folder_mc = new File(mapContextFolder)

if(!folder_mc.directory){
    println("Please set a valid mapcontext folder")
}
def mapcontextFiles =[:]
folder_mc.eachFileRecurse groovy.io.FileType.FILES,  { file ->
    def fileName= file.name.toLowerCase()
    if (fileName.endsWith(".mc")) {
        def fileNameWithoutExt = FilenameUtils.removeExtension(fileName)
        mapcontextFiles.put(fileNameWithoutExt, file.getAbsolutePath())
    }
}


PocCartoCommand app = new PocCartoCommand()

def cmd = new CommandLine(app)
def sw = new StringWriter()
cmd.setOut(new PrintWriter(sw))

mapcontextFiles.each {
    cmd.execute(it.value, "${folder_mc.absolutePath+File.separator+it.key}.png")
}
