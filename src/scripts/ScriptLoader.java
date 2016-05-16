package scripts;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 *
 * @author Gi
 */
public class ScriptLoader {

    private static final ScriptEngineManager sem = new ScriptEngineManager();

    public static <E extends Script> E load(final File file, final Class<E> script) throws RuntimeException {
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            ScriptEngine engine = sem.getEngineByName("javascript");
            engine.eval(reader);
            return ((Invocable) engine).getInterface(script);
        } catch (final Throwable t) {
            throw new RuntimeException(t);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException ignore) {
                }
            }
        }
    }
}
