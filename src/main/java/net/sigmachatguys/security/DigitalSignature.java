package net.sigmachatguys.security;

import net.sigmachatguys.messagemanage.MessageManage;
import java.security.*;
import java.util.Base64;
import java.util.Date;
import java.time.Instant;
import net.sigmachatguys.security.SignMessage;

public class DigitalSignature {


    private static KeyPair generateKeyPairWithRSA(int keySize) throws NoSuchAlgorithmException 
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static SignMessage signMessage(String message, PrivateKey privateKey) throws Exception {
        //Local de inserçãoda mensagem
        long timestamp = Instant.now().toEpochMilli();
        String userMenssage = message;
        String messageWithTime = message + "|" + timestamp;
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(messageWithTime.getBytes());
        byte[] assinatura = signature.sign();

        return new SignMessage(message, timestamp, Base64.getEncoder().encodeToString(assinatura));
    }


    public static boolean verifyMessage(SignMessage signMessage, PublicKey publicKey, long expirationMs) throws Exception {
        long now = Instant.now().toEpochMilli();
        long timestamp = signMessage.timestamp;
        String messageWithTime = signMessage.message + "|" + timestamp;

        if (now - timestamp > expirationMs) {
            System.out.println(" Tempo de verificação excedido. Verifique sua conexão.");
            return false;
        }


        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(messageWithTime.getBytes());
        byte[] signatureBytes = Base64.getDecoder().decode(signMessage.signature);
        boolean verifi = signature.verify(signatureBytes);

        if (verifi){
            System.out.println("Assinatura visualizada e confirmada");
        }else {
            System.out.println("Mensagem alterada");
        }
        return verifi;
    }
}

