package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoCompratoBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.ProdottoCompratoDao;
import dao.ProdottoCompratoDaoImpl;

/**
 * Servlet implementation class Fattura
 */
@WebServlet("/fattura")
public class Fattura extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoCompratoDao daoProdottoC;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println(getServletContext().getAttributeNames());
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto applicativo.");
        }
        daoProdottoC = new ProdottoCompratoDaoImpl(ds);
    }
    
    public Fattura() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/csv; charset=UTF-8");
		int id = (int)request.getSession().getAttribute("idOrdine");
		response.setHeader("Content-Disposition", "attachment; filename=\"fattura"+id+".csv\"");
		int tot = 0;
		try {			
			List<ProdottoCompratoBean> prod = daoProdottoC.retrieveByIdOrdine(id);
			PrintWriter out = response.getWriter();
			out.println(";;Maxx-Cards\n");
			out.println("Ordine #"+id+"\n");
			out.println(";Nome;Quantità;Note;Prezzo\n");
			
			for(ProdottoCompratoBean bean : prod) {
				out.println(bean.toFattura());
				tot=tot+(bean.getPrezzo()*bean.getQnt());
			}
			out.println(";;;Totale: "+Integer.toString(tot/100)+"\n");
		}catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Errore nell'ottenimento delle informazioni dell'ordine dal database!");
			response.sendError(500);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
