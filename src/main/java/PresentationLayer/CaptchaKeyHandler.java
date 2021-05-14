/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author gamma
 */
public class CaptchaKeyHandler {

    private static String publicKey = null;
    private static String secretKey = null;

    public static void setPublicCaptchaKey(HttpServletRequest request) {
        if (publicKey == null) {
            if (System.getenv("publicKey") != null) {
                publicKey = System.getenv("publicKey");
            } else {
                //Default testing key which always returns true
                publicKey = "6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI";
            }
        }
        request.setAttribute("key", publicKey);
    }

    public static String getSecretCaptchaKey() {
        if (secretKey == null) {
            if (System.getenv("secretKey") != null) {
                secretKey = System.getenv("secretKey");
            } else {
                //Default testing key which always returns true
                secretKey = "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe";
            }
        }
        return secretKey;
    }
}
