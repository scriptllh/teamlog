package com.wiseach.teamlog.web.extensions;

import net.sourceforge.stripes.localization.DefaultLocalizationBundleFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * User: Arlen Tan
 * 12-8-10 下午5:00
 */
public class TeamlogLocalizationBundleFactory extends DefaultLocalizationBundleFactory {

    public ResourceBundle getTeamLogMessageBundle(String bundleKey, Locale locale) throws MissingResourceException {
        String fileName = getConfiguration().getBootstrapPropertyResolver().getProperty(bundleKey);
        try {
            if (locale == null) {
                return ResourceBundle.getBundle(fileName);
            }
            else {
                return ResourceBundle.getBundle(fileName, locale);
            }
        }
        catch (MissingResourceException mre) {
            MissingResourceException mre2 = new MissingResourceException(
                    "Could not find the error message resource bundle needed by TeamLog. This " +
                            "almost certainly means that a properties file called '" +
                             fileName + ".properties' could not be found in the classpath. " +
                            "This properties file is needed to lookup validation error messages. Please " +
                            "ensure the file exists in WEB-INF/classes or elsewhere in your classpath.",
                    fileName, "");
            mre2.setStackTrace(mre.getStackTrace());
            throw mre2;
        }
    }
}
