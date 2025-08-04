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


        sign = Signature.getInstance("SHA256withRSA"); //Alterração para ser uma chave dupla criptorgafar uma chave dento de outra

        //chave dupla de rsa com aes
        sign.initSign(priv);
    }
    public byte[] assinarMensagem(String mensagem) throws Exception {//Colocar um timestamp para verificar o tempo de desbloqueio das mensagens
        sign.update(mensagem.getBytes());
        return sign.sign();
        //Assinar duplamente as mensagens e com od respectivos usuarios
        //após assinatura salvar em uma keystore as chaves
        //kestore temporaria
    }

    public boolean verificarAssinatura(String mensagem, byte[] assinatura) throws Exception {
        //Colocar um timestamp de verificação tempo medio apenas de 1 minuto no maximo para não haver altereções
        sign.initVerify(publi);
        sign.update(mensagem.getBytes());
        return sign.verify(assinatura);
    }


}

