package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.departamento.Departamento;
import dao.departamento.DepartamentoDAO;
import dao.departamento.DepartamentoImpl;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("action");
		if (op == null) {
			response.sendRedirect("NewFile.html");
		}
		if (op.equals("alta")) {
			response.sendRedirect("alta.jsp");
		} else if (op.equals("insertar")) {
			Departamento depart = (Departamento) request.getAttribute("depart");
			DepartamentoDAO depDAO = new DepartamentoImpl();
			depDAO.insertarDep(depart);
			response.sendRedirect("NewFile.html");
		} else if (op.equals("consultar")) {
			DepartamentoDAO depDAO = new DepartamentoImpl();
			Departamento depart = depDAO.consultarDep(1);
			System.out.println(depart.getDnombre());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
