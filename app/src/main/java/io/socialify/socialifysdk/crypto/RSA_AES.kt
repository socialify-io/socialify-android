import android.os.Build
import androidx.annotation.RequiresApi
import java.security.spec.PKCS8EncodedKeySpec
import javax.crypto.Cipher
import java.security.spec.X509EncodedKeySpec
import java.io.IOException
import java.security.*
import java.util.*

/*
 * RSA Key Size: 1024
 * Cipher Type: RSA/ECB/PKCS1Padding
 */


// convert String publickey to Key object
@RequiresApi(Build.VERSION_CODES.O)
@Throws(GeneralSecurityException::class, IOException::class)
fun loadPublicKey(stored: String): Key {
    val data: ByteArray = Base64.getDecoder().
    decode(stored.toByteArray())
    val spec = X509EncodedKeySpec(data)
    val fact = KeyFactory.getInstance("RSA")
    return fact.generatePublic(spec)
}

// Encrypt using publickey
@RequiresApi(Build.VERSION_CODES.O)
@Throws(Exception::class)
fun encryptMessage(plainText: String, publickey: String): String {
    val cipher = Cipher.getInstance("RSA/ECB/OAEPPadding")
    cipher.init(Cipher.ENCRYPT_MODE, loadPublicKey(publickey))
    return Base64.getEncoder().encodeToString(cipher.doFinal

        (plainText.toByteArray()))
}

// Decrypt using privatekey
@RequiresApi(Build.VERSION_CODES.O)
@Throws(Exception::class)
fun decryptMessage(encryptedText: String?, privatekey: String):
        String {
    val cipher = Cipher.getInstance("RSA/ECB/OAEPPadding")
    cipher.init(Cipher.DECRYPT_MODE, loadPrivateKey(privatekey))
    return String(cipher.
    doFinal(Base64.getDecoder().decode(encryptedText)))
}

// Convert String private key to privateKey object
@RequiresApi(Build.VERSION_CODES.O)
@Throws(GeneralSecurityException::class)
fun loadPrivateKey(key64: String): PrivateKey {
    val clear: ByteArray = Base64.getDecoder().
    decode(key64.toByteArray())
    val keySpec = PKCS8EncodedKeySpec(clear)
    val fact = KeyFactory.getInstance("RSA")
    val priv = fact.generatePrivate(keySpec)
    Arrays.fill(clear, 0.toByte())
    return priv
}
