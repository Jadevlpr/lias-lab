package ma.fsbm.lias.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {

    private PasswordUtil() {
    }

    public static String chiffrer(String motDePasse) {
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt());
    }

    public static boolean verifier(String motDePasse, String motDePasseChiffre) {
        return BCrypt.checkpw(motDePasse, motDePasseChiffre);
    }
}