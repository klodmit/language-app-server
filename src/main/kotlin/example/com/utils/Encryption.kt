import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest
import java.util.Base64
import javax.crypto.spec.GCMParameterSpec
val mysalt = "frhajdlfuje945314]afsdhu"

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

    val iv = cipher.iv // Получить IV из шифра после инициализации

    val encryptedValue = cipher.doFinal(this.toByteArray())
    val combined = iv + encryptedValue // Объединить IV и зашифрованный текст

    return Base64.getEncoder().encodeToString(combined)
}

fun String.decodePass(password: String, salt: String, encodedValue: String): String {
    val secretKeySpec = password.toAESKey(salt.toByteArray())

    val combined = Base64.getDecoder().decode(encodedValue)
    val iv = combined.sliceArray(0..11) // Извлечь IV из объединенных данных
    val ciphertext = combined.sliceArray(12 until combined.size) // Извлечь зашифрованный текст из объединенных данных

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    val gcmSpec = GCMParameterSpec(128, iv)
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmSpec)

    val decryptedBytes = cipher.doFinal(ciphertext)

    return String(decryptedBytes)
}