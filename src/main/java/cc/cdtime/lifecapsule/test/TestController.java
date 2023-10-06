package cc.cdtime.lifecapsule.test;

import cc.cdtime.lifecapsule.framework.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/lifecapsule_api/test")
public class TestController {
    @ResponseBody
    @PostMapping("/cryptAll")
    public Response cryptAll(@RequestBody TestRequest request) {
        Response response = new Response();

        String secretKey = request.getSecretKey();
        String content = request.getContent();
        String iv = request.getIv();
        try {
            String ss = decrypt(secretKey, iv, content);
            response.setData(ss);
        } catch (Exception ex) {
            response.setCode(10001);
            System.out.println("error:" + ex.getMessage());
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/cryptAll2")
    public Response cryptAll2(@RequestBody TestRequest request) {
        Response response = new Response();

        String secretKey = request.getSecretKey();
        String content = request.getContent();
        String iv = request.getIv();
        try {
            String ss = decrypt2(secretKey, iv, content);
            response.setData(ss);
        } catch (Exception ex) {
            response.setCode(10001);
            System.out.println("error:" + ex.getMessage());
        }
        return response;
    }

    private String decrypt(String key, String initVector, String encrypted) throws Exception {
//        byte[] ivBytes = new BigInteger(initVector, 16).toByteArray();
//        if (ivBytes.length < 16) {  // Need to prepend zeros
//            byte[] tmp = new byte[16];
//            System.arraycopy(ivBytes, 0, tmp, 16 - ivBytes.length, ivBytes.length);
//            ivBytes = tmp;
//        }

//        byte[] ivBytes = new BigInteger(initVector, 16).toByteArray();

// BigInteger.toByteArray might prepend additional 0x00, remove it
//        if (ivBytes[0] == 0) {
//            ivBytes = Arrays.copyOfRange(ivBytes, 1, ivBytes.length);
//        }

        byte[] keyBytes = hexStringToByteArray(key);
        byte[] ivBytes = hexStringToByteArray(initVector);

        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

//        byte[] cipherTextBytes = Base64.getDecoder().decode(encrypted);
//        byte[] original = cipher.doFinal(cipherTextBytes);

        byte[] cipherTextBytes = Base64.getDecoder().decode(encrypted);
        byte[] original = cipher.doFinal(cipherTextBytes);

        System.out.println(new String(original));


//        IvParameterSpec iv = new IvParameterSpec(ivBytes);
//        Security.addProvider(new BouncyCastleProvider());
//        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
//        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

//        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

        return new String(original);
    }

    private String decrypt2(String key, String initVector, String encrypted) throws Exception {
        // The key and iv from your JS encryption, encoded in base64
        String keyBase64 = key;
        String ivBase64 = initVector;
        // The cipher text from your JS encryption, also encoded in base64
        String cipherTextBase64 = encrypted;

        byte[] keyBytes = Base64.getDecoder().decode(keyBase64);
        byte[] ivBytesFull = Base64.getDecoder().decode(ivBase64);
        byte[] ivBytes = Arrays.copyOfRange(ivBytesFull, 0, 16); // use only the first 16 bytes
        byte[] cipherTextBytes = Base64.getDecoder().decode(cipherTextBase64);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

//        byte[] original = cipher.doFinal(cipherTextBytes);

        byte[] original = cipher.doFinal(cipherTextBytes);

        String ss = new String(original);
        System.out.println(ss);

        return ss;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
