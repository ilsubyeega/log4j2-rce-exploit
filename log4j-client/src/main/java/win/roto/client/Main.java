package win.roto.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(final String... args) {
        ExploitStatus status = checkExploitStatus();
        logger.info("Current java version is " + System.getProperty("java.version") + ".");
        if (status == ExploitStatus.UNAVAILABLE)
            logger.warn("Please notice that this exploit might not work with this.");

        logger.info("Enter the message and it will send back the message to console.");
        logger.info("Enter \"exit\" or Ctrl+C to exit this application.");

        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = null;
            try {
                line = buffer.readLine();
            } catch (Exception e) {

            }

            if (line != null) {
                if (line.equals("exit")) break;
                try {
                    logger.trace("INPUT: " + line);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private enum ExploitStatus {
        UNAVAILABLE, // Currently it might be unavailable for this run.
        VERSION, // The version were not patched. (TODO)
        PARAMETERS // Bypass the jvm patch with parameters
    }
    private static ExploitStatus checkExploitStatus() {

        String ldap = System.getProperty("com.sun.jndi.ldap.object.trustURLCodebase");
        String rmi = System.getProperty("com.sun.jndi.rmi.object.trustURLCodebase");
        String jndi = System.getProperty("com.sun.jndi.cosnaming.object.trustURLCodebase");
        if (ldap != null && ldap.equals("true")) return ExploitStatus.PARAMETERS;
        if (rmi != null && rmi.equals("true")) return ExploitStatus.PARAMETERS;
        if (jndi != null && jndi.equals("true")) return ExploitStatus.PARAMETERS;

        return ExploitStatus.UNAVAILABLE;
    }
}
