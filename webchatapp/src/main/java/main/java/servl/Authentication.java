package main.java.servl;

import javax.servlet.annotation.WebServlet;
import main.java.models.AccountStorage;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@WebServlet(value= "/chat")
public class Authentication extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pass");
        try {
            String pHash = encryptPassword(password);
            AccountStorage loginStorage = new AccountStorage();
            loginStorage.loadAccount();

            if (loginStorage.isExist(login, pHash)){
                RequestDispatcher requestDispatcher=getServletContext().getRequestDispatcher("/pages/homepage.html");
                requestDispatcher.forward(req,resp);
            }
            else {
                RequestDispatcher requestDispatcher=getServletContext().getRequestDispatcher("/pages/login.jsp");
                req.setAttribute("errorMsg", "Unable to log in.\n" +
                        "Please check that you have entered your login and password correctly. ");
                requestDispatcher.forward(req,resp);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        } catch(UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String encryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String sha1;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(password.getBytes("UTF-8"));
        Formatter formatter = new Formatter();
        for (byte b : crypt.digest()) {
            formatter.format("%02x", b);
        }
        sha1 = formatter.toString();
        formatter.close();
        return sha1;
    }
}
