package net.sigmachatguys.kEY;

import java.security.*;


public class Assinatura {
    private Signature sign;
    private PrivateKey priv;
    private PublicKey publi;

    public Assinatura() throws Exception {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        kpg.initialize(2048, random);
        KeyPair kp = kpg.generateKeyPair();

        priv = kp.getPrivate();
        publi = kp.getPublic();


        sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(priv);
    }
    public byte[] assinarMensagem(String mensagem) throws Exception {
        sign.update(mensagem.getBytes());
        return sign.sign();
    }

    public boolean verificarAssinatura(String mensagem, byte[] assinatura) throws Exception {
        sign.initVerify(publi);
        sign.update(mensagem.getBytes());
        return sign.verify(assinatura);
    }


}

