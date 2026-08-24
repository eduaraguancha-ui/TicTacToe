package game;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class EntryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        GameBean game = (GameBean) session.getAttribute("gameBean");
        if (game == null) {
            game = new GameBean();
            session.setAttribute("gameBean", game);
        }

        // Si el usuario presiona "User", ese parámetro existe; si presiona "Computer", no existe "User"
        boolean userFirst = (request.getParameter("User") != null);

        game.setStartByUser(userFirst);
        game.startGame();

        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}