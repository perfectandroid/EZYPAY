//package com.perfectlimited.easyshare.support;
//
//
//
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.cert.Certificate;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.net.ssl.X509TrustManager;
//import javax.security.cert.CertificateException;
//import javax.security.cert.X509Certificate;
//
///**
// * Created by akash on 8/29/2016.
// */
//public class ExplicitTrustManager implements X509TrustManager {
//    final private Map certificateMap = new HashMap();
//
//    public ExplicitTrustManager(KeyStore trustStore) throws GeneralSecurityException {
//        for (String alias : Collections.list(trustStore.aliases())) {
//            Certificate certificate =   trustStore.getCertificate(alias);
//            String commonName = Certificates.getCommonName(certificate.getSubjectDN());
//            certificateMap.put(commonName, certificate); } }
//
//    @Override
//    private void checkTrusted(X509Certificate[] chain) throws CertificateException {
//
//        if (chain.length == 0) { throw new CertificateException("Invalid cert chain length");
//        } X509Certificate trustedCertificate = certificateMap.get( Certificates.getCommonName(chain[0].getSubjectDN()));
//        if (trustedCertificate == null) { throw new CertificateException("Untrusted peer certificate"); }
//        if (!Arrays.equals(chain[0].getPublicKey().getEncoded(), trustedCertificate.getPublicKey().getEncoded())) { throw new CertificateException("Invalid peer certificate"); } trustedCertificate.checkValidity(); }
//
//    @Override
//    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//    }
//
//    @Override
//    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//        return new java.security.cert.X509Certificate[0];
//    }
//}