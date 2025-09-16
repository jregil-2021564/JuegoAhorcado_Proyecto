package Controlador;

import modelo.Usuarios;
import modelo.UsuariosDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Validar")
public class Validar extends HttpServlet {
    private UsuariosDAO usuariosDAO = new UsuariosDAO();
    private Usuarios usuario = new Usuarios();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println("Acción recibida: " + accion); 

        if ("Ingresar".equalsIgnoreCase(accion)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                System.out.println("Error: Campos de usuario o contraseña vacíos");
                request.setAttribute("errorLogin", "Por favor, ingrese usuario y contraseña");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            System.out.println("Intento de login - Username: " + username + ", Password: " + password);
            usuario = usuariosDAO.validar(username.trim(), password.trim());

            if (usuario != null) {
                System.out.println("Usuario validado: " + usuario.getUsername() + ", ID: " + usuario.getCodigoUsuario());
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", usuario);  
                response.sendRedirect("MenuInicio.jsp");  
            } else {
                System.out.println("Validación fallida: usuario o contraseña incorrectos");
                request.setAttribute("errorLogin", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        }
        else if ("Registrar".equalsIgnoreCase(accion)) {
            String username = request.getParameter("new-username");
            String password = request.getParameter("new-password");
            String confirmar = request.getParameter("confirm-password");

            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty() || 
                confirmar == null || confirmar.trim().isEmpty()) {
                System.out.println("Error: Campos de registro vacíos");
                request.setAttribute("errorRegistro", "Por favor, complete todos los campos");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            if (!password.equals(confirmar)) {
                System.out.println("Error: Las contraseñas no coinciden");
                request.setAttribute("errorRegistro", "Las contraseñas no coinciden");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            if (usuariosDAO.verificarUsuarioExistente(username.trim())) {
                System.out.println("Error: El usuario ya existe - " + username);
                request.setAttribute("errorRegistro", "El usuario ya existe");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            boolean registrado = usuariosDAO.registrar(username.trim(), password.trim());
            System.out.println("Resultado de registro: " + (registrado ? "Éxito" : "Fallo"));

            if (registrado) {
                usuario = usuariosDAO.validar(username.trim(), password.trim());
                if (usuario != null) {
                    System.out.println("Usuario registrado y validado: " + usuario.getUsername() + ", ID: " + usuario.getCodigoUsuario());
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("usuario", usuario); 
                    response.sendRedirect("MenuInicio.jsp");  
                } else {
                    System.out.println("Error: No se pudo validar el usuario tras registro");
                    request.setAttribute("errorRegistro", "Usuario registrado, pero error al iniciar sesión");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                System.out.println("Error: Fallo al registrar el usuario");
                request.setAttribute("errorRegistro", "Error al registrar el usuario");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } else {
            System.out.println("Acción no reconocida: " + accion);
            request.setAttribute("errorLogin", "Acción no válida");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
