package curso.angular.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import curso.angular.dao.DaoImplementacao;
import curso.angular.dao.DaoInterface;
import curso.angular.model.Livro;

@Controller
@RequestMapping(value = "/livro")
public class LivroController extends DaoImplementacao<Livro> implements
		DaoInterface<Livro> {

	public LivroController(Class<Livro> persistenceClass) {
		super(persistenceClass);
	}

	 @RequestMapping(value="salvar", method= RequestMethod.POST)
	 @ResponseBody
	 public ResponseEntity salvar(@RequestBody String jsonLivro) throws Exception {
		 Livro livro = new Gson().fromJson(jsonLivro, Livro.class);
		 super.salvarOuAtualizar(livro);
		 return new ResponseEntity(HttpStatus.CREATED);
		 
	 }
	
	
	 /**
	  * Retorna a lista de livros cadastrados
	  * @return JSON String de Livros
	  * @throws Exception
	  */
	@RequestMapping(value="listar/{numeroPagina}", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public String listar(@PathVariable("numeroPagina") String numeroPagina) throws Exception {
		return new Gson().toJson(super.consultaPaginada(numeroPagina)); 
	}
	
	
	@RequestMapping(value="totalPagina", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public String totalPagina() throws Exception {
		return ""+super.quantidadePagina(); 
	}
	 
	/**
	 * Delete o livro informado
	 * @param codLivro
	 * @return String vazia como resposta
	 * @throws Exception
	 */
	@RequestMapping(value="deletar/{codLivro}", method=RequestMethod.DELETE)
	public  @ResponseBody String deletar (@PathVariable("codLivro") String codLivro) throws Exception {
		super.deletar(loadObjeto(Long.parseLong(codLivro)));
		return "";
	}
	
	
	/**
	 * Consulta e retorna o livro com o codigo informado
	 * @param codLivro
	 * @return JSON livro pesquisado
	 * @throws Exception
	 */
	@RequestMapping(value="buscarlivro/{codLivro}", method=RequestMethod.GET)
	public  @ResponseBody String buscarLivro (@PathVariable("codLivro") String codLivro) throws Exception {
		Livro objeto = super.loadObjeto(Long.parseLong(codLivro));
		if (objeto == null) {
			return "{}";
		}
		return new Gson().toJson(objeto);
	}
	

}
