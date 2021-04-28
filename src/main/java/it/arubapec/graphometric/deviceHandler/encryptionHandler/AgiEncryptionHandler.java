/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.arubapec.graphometric.deviceHandler.encryptionHandler;

import com.WacomGSS.STU.Protocol.DHbase;
import com.WacomGSS.STU.Protocol.DHprime;
import com.WacomGSS.STU.Protocol.PublicKey;
import com.WacomGSS.STU.Tablet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Vincenzo Mangiapanello <vincenzo.mangiapanello@staff.aruba.it>
 */
public class AgiEncryptionHandler implements Tablet.IEncryptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AgiEncryptionHandler.class);

    private BigInteger p;
    private BigInteger g;
    private BigInteger privateKey;
    private Cipher aesCipher;

    @Override
    public void reset() {
        clearKeys();
        this.p = this.g = null;
    }

    @Override
    public void clearKeys() {
        this.privateKey = null;
        this.aesCipher = null;
    }

    @Override
    public boolean requireDH() {
        return this.p == null || this.g == null;
    }

    @Override
    public void setDH(DHprime dhPrime, DHbase dhBase) {
        this.p = new BigInteger(1, dhPrime.getValue());
        this.g = new BigInteger(1, dhBase.getValue());
    }

    @Override
    public PublicKey generateHostPublicKey() {
        this.privateKey = new BigInteger("F965BC2C949B91938787D5973C94856C", 16); // should be randomly chosen according to DH rules.

        BigInteger publicKey_bi = this.g.modPow(this.privateKey, this.p);
        try {
            PublicKey publicKey = new PublicKey(publicKey_bi.toByteArray());
            return publicKey;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void computeSharedKey(PublicKey devicePublicKey) {
        BigInteger devicePublicKey_bi = new BigInteger(devicePublicKey.getValue());
        BigInteger sharedKey = devicePublicKey_bi.modPow(this.privateKey, this.p);

        byte[] array = sharedKey.toByteArray();
        if (array[0] == 0) {
            byte[] tmp = new byte[array.length - 1];
            System.arraycopy(array, 1, tmp, 0, tmp.length);
            array = tmp;
        }

        try {
            Key aesKey = new SecretKeySpec(array, "AES");

            this.aesCipher = Cipher.getInstance("AES/ECB/NoPadding");
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
            return;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            logger.error(e.getMessage(), e);
        }
        this.aesCipher = null;
    }

    @Override
    public byte[] decrypt(byte[] data) {
        try {
            byte[] decryptedData = this.aesCipher.doFinal(data);
            return decryptedData;
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
