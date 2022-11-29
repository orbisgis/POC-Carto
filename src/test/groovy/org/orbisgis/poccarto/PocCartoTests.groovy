import org.orbisgis.poccarto.PocCartoCommand
import picocli.CommandLine



PocCartoCommand app = new PocCartoCommand()

def cmd = new CommandLine(app)
def sw = new StringWriter()
cmd.setOut(new PrintWriter(sw))
cmd.execute("../mapcontext/proportional_symbol.mc", "/tmp/test.png")
