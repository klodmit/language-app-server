package example.com.utils

import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

val mySalt = DotEnvCfg.getEnv("SALT")

fun String.toAESKey(salt: ByteArray): SecretKeySpec {
    val md = MessageDigest.getInstance("SHA-256")
    val combined = this.toByteArray() + salt
    val hash = md.digest(combined)
    return SecretKeySpec(hash, "AES")
}

fun String.encodePass(salt: String): String {
    val secretKeySpec = this.toAESKey(salt.toByteArray())

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)

    val iv = cipher.iv

    val encryptedValue = cipher.doFinal(this.toByteArray())
    val combined = iv + encryptedValue

    return Base64.getEncoder().encodeToString(combined)
}

fun decodePass(password: String, salt: String, encodedValue: String): String {
    val secretKeySpec = password.toAESKey(salt.toByteArray())

    val combined = Base64.getDecoder().decode(encodedValue)
    val iv = combined.sliceArray(0..11)
    val ciphertext = combined.sliceArray(12 until combined.size)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    val gcmSpec = GCMParameterSpec(128, iv)
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmSpec)

    val decryptedBytes = cipher.doFinal(ciphertext)

    return String(decryptedBytes)
}