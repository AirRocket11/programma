package util.lazy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import scripts.DataScript;
import scripts.FormatterScript;
import scripts.PhysicsScript;
import scripts.Script;
import scripts.ScriptLoader;

/**
 *
 * @author Gi
 */
public class LazyFactories {

    public static final LazyFactory<DataScript> DATA = scriptFactory(DataScript.class, new File("scripts/data.js"));
    public static final LazyFactory<PhysicsScript> PHYSICS = scriptFactory(PhysicsScript.class, new File("scripts/physics.js"));

    public static final LazyFactory<FormatterScript> FORMATTER_RANGE_IN = scriptFactory(FormatterScript.class, new File("scripts/formatters/afstand_in.js"));
    public static final LazyFactory<FormatterScript> FORMATTER_PRESSURE_OUT = scriptFactory(FormatterScript.class, new File("scripts/formatters/druk_out.js"));
    public static final LazyFactory<FormatterScript> FORMATTER_ANGLE_OUT = scriptFactory(FormatterScript.class, new File("scripts/formatters/hoek_out.js"));
    public static final LazyFactory<FormatterScript> FORMATTER_SPEED_OUT = scriptFactory(FormatterScript.class, new File("scripts/formatters/snelheid_out.js"));
    
    public static final LazyFactory<Properties> PROPERTIES_TEXT = propertiesFactory(new File("properties/text.properties"));
    public static final LazyFactory<Properties> PROPERTIES_UI = propertiesFactory(new File("properties/ui.properties"));
    
    public static void reload() {
        DATA.reload();
        PHYSICS.reload();
        
        FORMATTER_ANGLE_OUT.reload();
        FORMATTER_PRESSURE_OUT.reload();
        FORMATTER_RANGE_IN.reload();
        FORMATTER_SPEED_OUT.reload();
        
        PROPERTIES_TEXT.reload();
        PROPERTIES_UI.reload();
    }
    
    private static <S extends Script> LazyFactory<S> scriptFactory(final Class<S> clazz, final File file) {
        return scriptFactory(clazz, file, null);
    }
    
    private static <S extends Script> LazyFactory<S> scriptFactory(final Class<S> clazz, final File file, LazyInitializer<S> initializer) {
        return new LazyFactory<S>(new LazyLoader<S>(initializer) {
            @Override
            public S create() {
                return ScriptLoader.load(file, clazz);
            }
        });
    }

    private static LazyFactory<Properties> propertiesFactory(final File file) {
        return new LazyFactory<Properties>(new LazyLoader<Properties>() {
            @Override
            public Properties create() {
                final Properties props = new Properties();
                FileReader fr = null;
                try {
                    fr = new FileReader(file);
                    props.load(fr);
                } catch (final IOException io) {
                    main.Main.LOGGER.log(Level.SEVERE, null, io);
                } finally {
                    if (fr != null) {
                        try {
                            fr.close();
                        } catch (final IOException ignore) {
                        }
                    }
                }
                return props;
            }
        });
    }

    private LazyFactories() {
        // No contrsuctor, static utility class for LazyFactory
    }
}
