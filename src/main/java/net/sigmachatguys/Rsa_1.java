package net.sigmachatguys;

import net.sigmachatguys.guiscreen.SMainConsole;
import net.sigmachatguys.messagemanage.SMessageManage;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Rsa_1 {

    public static void Criptografar(String usermessage) throws Exception {
        //Geração do par de keys

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PrivateKey pv = keyPair.getPrivate();
        PublicKey pk = keyPair.getPublic();

        //Assinatura
        Signature signature =  Signature.getInstance("SHA256withRSA");

//Inicliaziando a com a chave privada
        signature.initSign(pv);

//Criando a assinatura dos btes da mensagem
        signature.update(usermessage.getBytes());

        byte[] Assinatura = signature.sign();

        //Verficação
        signature.initVerify(pk);
        signature.update(usermessage.getBytes());

        boolean verifi = signature.verify(Assinatura);
        if (verifi){
            System.out.println("Assinatura vizualizada e confirmada");
        }else {
            System.out.println("Menssagem alterada");
        }
    }

}
