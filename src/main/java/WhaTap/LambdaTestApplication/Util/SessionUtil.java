package WhaTap.LambdaTestApplication.Util;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private static final String SESSION_USER = "USER";

    public static void setUser(HttpSession session, String user) {
        if (session == null) {
            return;
        }
        session.setAttribute(SESSION_USER, user);
    }

    public static String getUser(HttpSession session) {
        if (session == null) {
            return null;
        }
        Object user = session.getAttribute(SESSION_USER);
        if (user instanceof String) {
            return (String) user;
        }
        return "";
    }
}
