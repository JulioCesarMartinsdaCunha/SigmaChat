package net.sigmachatguys;

import net.sigmachatguys.messagemanage.SMessageManage;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Rsa_1 {

    public static void main(String args[]) throws Exception {
        SMessageManage mn = new SMessageManage();

        //Local de inserçãoda mensagem

        String usermenssage =mn.getMessage(0).getMessage();



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

//Criando a assinatura dos btes da menssagem
        signature.update(usermenssage.getBytes());


        byte[] Assinatura = signature.sign();


        //Verficação

        signature.initVerify(pk);
        signature.update(usermenssage.getBytes());
        boolean verifi = signature.verify(Assinatura);

        if (verifi){
            System.out.println("Assinatura vizualizada e confirmada");
        }else {
            System.out.println("Menssagem alterada");
        }
    }

}
