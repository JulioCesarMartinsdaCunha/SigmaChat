package net.sigmachatguys.security;

import net.sigmachatguys.messagemanage.MessageManage;
import java.security.*;
import java.util.Date;
import java.time.Instant;

public class DigitalSignature {

    private static KeyPair generateKeyPairWithRSA(int keySize) throws NoSuchAlgorithmException 
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static void signMessage sign(String message)  throws Exception
    {
        //Local de inserçãoda mensagem
        long timestamp = Instant.now().toEpochMilli();
        String userMenssage = message;
        String messageWithTime = message + "|" + timestamp;


        //Assinatura
        Signature signature =  Signature.getInstance("SHA256withRSA");

        //Inicliaziando a com a chave privada
        signature.initSign(pv);

        //Criando a assinatura dos bytes da menssagem
        signature.update(messageWithTime.getBytes());

        long expirationTimems = 5 * 60 * 1000; // minutos para milisegundos
        Long now  = Instant.now().toEpochMilli();
        
        if (now - timestamp > expirationTimems){
            System.out.println("Tempo de verificação excedido verifique sua conexão;");
            return;
        }

        byte[] assinatura = signature.sign();

        //Verficação
        signature.initVerify(pk);
        signature.update(messageWithTime.getBytes());
        boolean verifi = signature.verify(assinatura);

        if (verifi){
            System.out.println("Assinatura visualizada e confirmada");
        }else {
            System.out.println("Mensagem alterada");
        }
    }

    public static boolean verifyMessage(String message, byte[] bytes)
    {

    }
}
