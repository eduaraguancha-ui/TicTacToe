package game;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import game.GameBean.GamePlayer;

public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        GameBean game = (GameBean) request.getSession(true).getAttribute("gameBean");
        if (game == null) {
            
            game = new GameBean();
            request.getSession(true).setAttribute("gameBean", game);
        }

       
        String lineStr = request.getParameter("Line");
        String colStr = request.getParameter("Col");

        if (lineStr != null && colStr != null) {
            int line = Integer.parseInt(lineStr);
            int col = Integer.parseInt(colStr);

            game.playPlayerTurn(line, col);
        }

        GamePlayer winner = game.getWinner();

        // Si aún nadie gana, juega el computador
        if (winner == GamePlayer.NOBODY) {
            if (game.hasEmptyCell()) {
                game.playComputerTurn();
                winner = game.getWinner();
            }
        }

        
        if (winner == GamePlayer.USER) {
            request.setAttribute("winner", "ERES EL GANADOR");
        } else if (winner == GamePlayer.COMPUTER) {
            request.setAttribute("winner", "EL ORDENADOR");
        } else {
           
            if (!game.hasEmptyCell()) {
                request.setAttribute("winner", "NADIE");
            }
        }

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