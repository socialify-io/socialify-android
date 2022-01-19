import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.spec.PKCS8EncodedKeySpec
import javax.crypto.Cipher
import java.security.spec.X509EncodedKeySpec
import java.io.IOException
import java.math.BigInteger
import java.security.*
import java.util.*

/*
 * RSA Key Size: 1024
 * Cipher Type: RSA/ECB/PKCS1Padding
 */

// Getting fingerprint with SHA1 algorithm
fun getFingerprint(privateKey: String): String {
    val md = MessageDigest.getInstance("SHA-1")
    val digest = md.digest(privateKey.toByteArray())
    val no = BigInteger(1, digest)

    return no.toString(16)
}


// Generate RSA keypair
@RequiresApi(Build.VERSION_CODES.M)
fun generateKeyPair(): KeyPair {
//    val generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA)
//
//    generator.initialize(2048, SecureRandom())
//    return generator.genKeyPair()

    val keyPair: KeyPairGenerator = KeyPairGenerator.getInstance(
        KeyProperties.KEY_ALGORITHM_RSA,
        "AndroidKeyStore"
    )

    val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
        "DeviceSignKeypair",
        KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY)
        .setDigests(KeyProperties.DIGEST_SHA1)
        .setKeySize(2048)
        .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
        .build()

    keyPair.initialize(parameterSpec, SecureRandom())

    return keyPair.generateKeyPair()
}

// Convert String publickey to Key object
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
