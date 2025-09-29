package net.sigmachatguys.security;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.security.cert.X509Certificate;
public class Keystore {
    public  static final String KEYSTORE_TYPE = "JKS";
    private static final final String KEYSTORE_FILE = "keystore.jks";
    private static final char[] password = "Sigma".toCharArray();




}
