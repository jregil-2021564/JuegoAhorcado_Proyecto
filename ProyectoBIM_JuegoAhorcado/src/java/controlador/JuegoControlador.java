package Controlador;

import modelo.Palabras;
import modelo.PalabrasDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/JuegoControlador")
public class JuegoControlador extends HttpServlet {

    private PalabrasDAO palabrasDAO = new PalabrasDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la palabra aleatoria de la base de datos
        Palabras palabraJuego = palabrasDAO.obtenerPalabraAleatoria();
        
        HttpSession sesion = request.getSession();

        if (palabraJuego != null) {
            // If a word is found, store it in the session
            sesion.setAttribute("palabraJuego", palabraJuego);
            System.out.println("Palabra seleccionada: " + palabraJuego.getPalabra());

            // Redirect to the game page (MenuInicio.jsp) to start the game
            response.sendRedirect("MenuInicio.jsp");
        } else {
            // If no word is found, set an error attribute and redirect to the menu
            sesion.setAttribute("errorJuego", "No se encontraron palabras en la base de datos.");
            System.err.println("Error: No se encontraron palabras en la base de datos.");
            response.sendRedirect("MenuInicio.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // You can handle game logic (e.g., checking a letter) here if needed.
        // For this example, we'll just forward to the doGet method.
        doGet(request, response);
    }
}