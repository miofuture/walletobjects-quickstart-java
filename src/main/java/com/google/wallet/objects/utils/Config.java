package com.google.wallet.objects.utils;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pying on 2/7/14.
 */
public class Config {

  private static Config config;

  private static Map<String, WobCredentials> credentials;

  private Config() {
  }

  public static Config getInstance() {
    if (config == null) {

      config = new Config();
      credentials = new HashMap<String, WobCredentials>();
    }
    return config;
  }

  public WobCredentials getCredentials(String serviceEmail, String privateKeyPath, String applicationName, String issuer)
      throws IOException, GeneralSecurityException {
    String key = issuer + privateKeyPath;

    WobCredentials credential = credentials.get(key);

    if (credential == null) {
      credential = new WobCredentials(
          serviceEmail,
          privateKeyPath,
          applicationName,
          issuer
      );
      credentials.put(key, credential);
    }
    return credential;
  }
  public WobCredentials getCredentials(ServletContext context) throws IOException, GeneralSecurityException {
    return getCredentials(context.getInitParameter("ServiceAccountEmailAddress"),
        context.getInitParameter("ServiceAccountPrivateKey"),
        context.getInitParameter("ApplicationName"),
        context.getInitParameter("IssuerId"));
  }
}
