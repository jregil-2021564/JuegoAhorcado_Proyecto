package Controlador;

import modelo.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Validar")
public class Validar extends HttpServlet {
    UsuariosDAO usuariosDAO = new UsuariosDAO();
    Usuarios usuario = new Usuarios();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if ("Ingresar".equalsIgnoreCase(accion)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            usuario = usuariosDAO.validar(username, password);
            
            if (usuario != null) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", usuario);
                response.sendRedirect("MenuInicio.jsp");
            } else {
                request.setAttribute("errorLogin", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            
        } else if ("Registrar".equalsIgnoreCase(accion)) {
            String username = request.getParameter("new-username");
            String password = request.getParameter("new-password");
            String confirmar = request.getParameter("confirm-password");
            
            // Validar que las contraseñas coincidan
            if (!password.equals(confirmar)) {
                request.setAttribute("errorRegistro", "Las contraseñas no coinciden");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
            
            // Verificar si el usuario ya existe
            if (usuariosDAO.verificarUsuarioExistente(username)) {
                request.setAttribute("errorRegistro", "El usuario ya existe");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
            
            // Registrar el nuevo usuario
            boolean registrado = usuariosDAO.registrar(username, password);
            
            if (registrado) {
                request.setAttribute("mensajeExito", "Usuario registrado con éxito, ahora puedes iniciar sesión");
            } else {
                request.setAttribute("errorRegistro", "Error al registrar el usuario");
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}