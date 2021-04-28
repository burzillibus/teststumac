/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.arubapec.graphometric.deviceHandler.encryptionHandler;

import com.WacomGSS.STU.Protocol.AsymmetricKeyType;
import com.WacomGSS.STU.Protocol.AsymmetricPaddingType;
import com.WacomGSS.STU.Protocol.SymmetricKeyType;
import com.WacomGSS.STU.Tablet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 *
 * @author Vincenzo Mangiapanello <vincenzo.mangiapanello@staff.aruba.it>
 */
public class AgiEncryptionHandler2 implements Tablet.IEncryptionHandler2 {
    private static final Logger logger = LoggerFactory.getLogger(AgiEncryptionHandler2.class);

        private BigInteger n;
        private BigInteger d;
        private BigInteger e;
        private Cipher aesCipher;
        private Key privateKey;

        public AgiEncryptionHandler2() {
            try {
                this.e = BigInteger.valueOf(new SecureRandom().nextInt(65537)).nextProbablePrime();
            } catch (ExceptionInInitializerError ex) {
                this.e = BigInteger.valueOf(65537);
            }
        }

        @Override
        public void reset() {
            clearKeys();
            this.d = null;
            this.n = null;
        }

        public void clearKeys() {
            this.aesCipher = null;
        }

        @Override
        public SymmetricKeyType getSymmetricKeyType() {
            return SymmetricKeyType.AES256;
            // return SymmetricKeyType.AES256; // requires
            // "Java Crypotography Extension (JCE) Unlimited Strength Jurisdiction Policy Files"
        }

        @Override
        public AsymmetricPaddingType getAsymmetricPaddingType() {
            return AsymmetricPaddingType.None; // not recommended
            //return AsymmetricPaddingType.OAEP;
        }

        @Override
        public AsymmetricKeyType getAsymmetricKeyType() {
            return AsymmetricKeyType.RSA2048;
        }

        public String toHex(byte[] arr) {
            StringBuilder sb = new StringBuilder(arr.length * 2);
            java.util.Formatter formatter = new java.util.Formatter(sb);
            for (byte b : arr) {
                formatter.format("%02x", b);
            }
            formatter.close();
            return sb.toString();
        }

        @Override
        public byte[] getPublicExponent() {
            byte[] ea = this.e.toByteArray();

            return ea;
        }

        @Override
        public byte[] generatePublicKey() {
            if (this.n != null) {
                return n.toByteArray();
            }

            try {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(2048, e); // the size define in AsymmetricKeyType
                kpg.initialize(spec);
                KeyPair kp = kpg.generateKeyPair();
                privateKey = kp.getPrivate();

                KeyFactory fact = KeyFactory.getInstance("RSA");
                RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
                RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);

                BigInteger pkey = pub.getModulus();

                this.n = pkey;
                this.d = priv.getPrivateExponent();

                return pkey.toByteArray();

            } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException ex) {
                logger.error(ex.getMessage(), ex);
            }

            return null;
        }

        @Override
        public void computeSessionKey(byte[] data) {

            //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            byte[] nkey = null;
            //byte[] nkey2 = null;

            //try {
            //Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding");
            //cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //nkey = cipher.doFinal(data);
            //nkey2 = new byte[nkey.length - 1];
            //if (nkey[0] == 0) {
            //for (int i = 1; i < nkey.length; i++) {
            //nkey2[i - 1] = nkey[i];
            //}
            //}
            //} catch (Exception e1) {
            //e1.printStackTrace();
            //}
            BigInteger c = new BigInteger(1, data);

            BigInteger m = c.modPow(this.d, this.n);

            int keySizeBytes = 256 / 8;

            byte[] k = m.toByteArray();
            if (k.length != keySizeBytes) {
                byte[] k2 = new byte[keySizeBytes];
                System.arraycopy(k, k.length > keySizeBytes ? k.length
                        - keySizeBytes : 0, k2, 0, k2.length);
                k = k2;
            }

            Key aesKey = new SecretKeySpec(k, "AES");
            //Key aesKey = new SecretKeySpec(nkey2, "AES");

            try {
                this.aesCipher = Cipher.getInstance("AES/ECB/NoPadding");
                this.aesCipher.init(Cipher.DECRYPT_MODE, aesKey);

                return;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            this.aesCipher = null;
        }

        @Override
        public byte[] decrypt(byte[] data) {
            try {
                byte[] decryptedData = this.aesCipher.doFinal(data);
                return decryptedData;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        }
    }
