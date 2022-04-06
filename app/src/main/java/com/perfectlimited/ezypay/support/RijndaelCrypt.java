//package com.perfectlimited.easyshare.support;
//
///**
// * Created by akash on 8/16/2016.
// */
//
//import android.util.Base64;
//import android.util.Log;
//
//import com.perfectlimited.easyshare.global.Global;
//
//import java.io.UnsupportedEncodingException;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//public class RijndaelCrypt {
//
//    public static final String TAG = "YourAppName";
//
//    private static String TRANSFORMATION = "AES/CBC/PKCS7Padding";
//    private static String ALGORITHM = "AES";
//    private static String DIGEST = "MD5";
//
//    private static Cipher _cipher;
//    private static SecretKey _password;
//    private static IvParameterSpec _IVParamSpec;
//
//    //16-byte private key
////    private static byte[] IV = "ThisIsUrPassword".getBytes();
//
//
//    public static  byte[] sessionKey = {
//            123, ((byte)217), 19, 11, 24, 26, 85, 45, 114,(byte) 184, 27,(byte) 162, 37,
//            112,(byte) 222, (byte)209, (byte)241, 24,(byte) 175, (byte)144, (byte)173, 53,(byte) 196,
//            29, 24, 26, 17, (byte)218,(byte) 131,(byte) 236, 53, (byte)209 };
//
//
//    public static byte[] IV = { (byte)146, 64, (byte)191, 111, 23, 3, 113, 119,(byte) 231,
//            121, 105, 112, 79, 32, 114,(byte) 156 };
//
//
//
//    /**
//     Constructor
//     @password Public key
//
//     */
//    public RijndaelCrypt(String password) {
//
//        try {
//
//            //Encode digest
//            MessageDigest digest;
//            digest = MessageDigest.getInstance(DIGEST);
//            _password = new SecretKeySpec(digest.digest(password.getBytes()), ALGORITHM);
//
//
//
//            Log.e("_password lenght",_password.getAlgorithm().length()+"");
//            //Initialize objects
//            _cipher = Cipher.getInstance(TRANSFORMATION);
//            _IVParamSpec = new IvParameterSpec(IV);
//
//        } catch (NoSuchAlgorithmException e) {
//            Log.e(TAG, "No such algorithm " + ALGORITHM, e);
//        } catch (NoSuchPaddingException e) {
//            Log.e(TAG, "No such padding PKCS7", e);
//        }
//    }
//
//    /**
//     Encryptor.
//
//     @text String to be encrypted
//     @return Base64 encrypted text
//
//     */
//    public String encrypt(byte[] text) {
//
//        byte[] encryptedData;
//
//        try {
//
//            _cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
//            encryptedData = _cipher.doFinal(text);
//
//        } catch (InvalidKeyException e) {
//            Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
//            return null;
//        } catch (InvalidAlgorithmParameterException e) {
//            Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
//            return null;
//        } catch (IllegalBlockSizeException e) {
//            Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
//            return null;
//        } catch (BadPaddingException e) {
//            Log.e(TAG, "The input data but the data is not padded properly.", e);
//            return null;
//        }
//
//        return Base64.encodeToString(encryptedData,Base64.DEFAULT);
//
//    }
//
//    /**
//     Decryptor.
//
//     @text Base64 string to be decrypted
//     @return decrypted text
//
//     */
//    public String decrypt(String text) {
//
//        try {
//            _cipher.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec);
//
//            byte[] decodedValue = Base64.decode(text.getBytes("UTF-8"), Base64.DEFAULT);
//
//            if(Global.DEBUG) Log.e("decoded value",new String(decodedValue)+"");
//
//            byte[] decryptedVal = _cipher.doFinal(decodedValue);
//            return new String(decryptedVal);
//
//
//        } catch (InvalidKeyException e) {
//            Log.e(TAG, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
//            return null;
//        } catch (InvalidAlgorithmParameterException e) {
//            Log.e(TAG, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
//            return null;
//        } catch (IllegalBlockSizeException e) {
//            Log.e(TAG, "The length of data provided to a block cipher is incorrect", e);
//            return null;
//        } catch (BadPaddingException e) {
//            Log.e(TAG, "The input data but the data is not padded properly.", e);
//            return null;
//        } catch (UnsupportedEncodingException e) {
//            Log.e(TAG, "UnsupportedEncodingException.", e);
//            return null;
//        }
//
//    }
//
//}